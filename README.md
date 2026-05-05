# JSON Compare

> A Java library for matching JSONs, with regex, JSONPath, and embedded match tweaks — no glue code required.

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fslev/json-compare.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.fslev%22%20AND%20a:%22json-compare%22)
![Build status](https://github.com/fslev/json-compare/actions/workflows/maven.yml/badge.svg?branch=main)
[![Coverage Status](https://coveralls.io/repos/github/fslev/json-compare/badge.svg?branch=main)](https://coveralls.io/github/fslev/json-compare?branch=main)

Compare any two JSON-convertible Java objects and report every difference on
mismatch. The expected JSON is matched leniently by default — sizes and array
order are ignored, scalar values are matched as Java regular expressions, and
match logic can be tuned with operators (`!`, `!.*`, `.*`, `#(...)`) embedded
directly inside the expected JSON.

## 1. Features

- Fluent builder API: `JSONCompare.compare(expected, actual).modes(...).assertMatches()`
- Accepts any JSON-convertible input: `JsonNode`, JSON `String`, `Map`, `List`, POJO
- Regex matching on field names and values, with `\Q…\E` quoting to escape literals
- Embedded match operators: negation (`!`), forbid-extras (`!.*`), require-extras (`.*`)
- Embedded JSONPath assertions via `#(<jsonpath>)` keys, powered by [Jayway JsonPath](https://github.com/json-path/JsonPath)
- Soft assertions: every mismatch is reported, not just the first
- Pluggable `JsonComparator` for custom field/value matching strategies
- Throws `org.opentest4j.AssertionFailedError` — works with JUnit 5, TestNG, and any opentest4j-aware runner

## 2. Requirements

- Java 17 or later

## 3. Installation

Maven:

```xml
<dependency>
    <groupId>com.github.fslev</groupId>
    <artifactId>json-compare</artifactId>
    <version>${version.from.maven.central}</version>
</dependency>
```

Gradle:

```groovy
testImplementation 'com.github.fslev:json-compare:<version.from.maven.central>'
```

## 4. Quickstart

```java
String expected = """
        {
          "string": "I'm on a seafood diet. I see food and I eat it!",
          "number": "\\\\d+.\\\\d+",
          "object": {
            "pun": "\\\\QWhy don't skeletons fight each other? They don't have the guts!\\\\E"
          },
          "array": [".*", "\\\\d+", true, null],
          "boolean": "true|false"
        }
        """;
String actual = """
        {
          "string": "I'm on a seafood diet. I see food and I eat it!",
          "number": 0.99,
          "object": {
            "pun": "Why don't skeletons fight each other? They don't have the guts!"
          },
          "array": ["pancake", 18, true, null],
          "boolean": true
        }
        """;
JSONCompare.compare(expected, actual).assertMatches(); // passes
```

Either side may be any JSON-convertible object — Jackson handles the conversion:

```java
Map<String, Object> actualMap = new HashMap<>();
actualMap.put("a", 1);
actualMap.put("b", Arrays.asList("ipsum", 4, 5));
actualMap.put("c", true);

String expected = """
        { "a": 1, "b": [4, "ipsum", "\\\\d+"] }
        """;
JSONCompare.compare(expected, actualMap).assertMatches(); // passes
```

## 5. API

The fluent builder is the only entry point. Configuration methods return
`this`; the comparison runs only when a terminal method is invoked.

| Builder method                     | Purpose                                                 |
|------------------------------------|---------------------------------------------------------|
| `.modes(CompareMode... \| Set<…>)` | Tighten default lenient comparison (see below)          |
| `.comparator(JsonComparator)`      | Override the default regex-based field/value comparator |
| `.message(String)`                 | Append a custom note to assertion failure output        |
| `.assertMatches()` *(terminal)*    | Throw `AssertionFailedError` if any difference is found |
| `.assertNotMatches()` *(terminal)* | Throw `AssertionFailedError` if the JSONs do match      |
| `.diffs()` *(terminal)*            | Return a `List<String>` of differences (empty on match) |

The static `JSONCompare.assertMatches(...)` / `assertNotMatches(...)` /
`diffs(...)` overloads still work but are deprecated since 8.0; they delegate
to the builder.

## 6. Compare modes

By default, expected JSON only has to be a *subset* of actual — extra fields
and array elements are tolerated and array order is ignored. The
`CompareMode` enum tightens this:

- `JSON_OBJECT_NON_EXTENSIBLE` — actual objects must not have extra fields
- `JSON_ARRAY_NON_EXTENSIBLE` — actual arrays must not have extra elements
- `JSON_ARRAY_STRICT_ORDER` — array elements must match position-for-position
- `REGEX_DISABLED` — compare values and field names by literal equality

```java
String expected = """{ "b": "val1" }""";
String actual   = """{ "a": "val2", "b": "val1" }""";

JSONCompare.compare(expected, actual).assertMatches();                                  // passes (subset)
JSONCompare.compare(expected, actual)
        .modes(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
        .assertNotMatches();                                                            // passes (extras present)
```

```java
String expected = """[ "lorem", 2, false ]""";
String actual   = """[ false, 2, "lorem", 5, 4 ]""";

JSONCompare.compare(expected, actual).assertMatches();                                  // passes (order ignored)
JSONCompare.compare(expected, actual)
        .modes(CompareMode.JSON_ARRAY_STRICT_ORDER)
        .assertNotMatches();                                                            // passes (order differs)
```

## 7. Regex matching

Expected scalar values and field names are interpreted as Java regular
expressions (with `DOTALL | MULTILINE`). Patterns that fail to compile fall
back to literal equality.

```java
JSONCompare.compare("""{ "a": ".*me.*" }""", """{ "a": "some text" }""")
        .assertMatches();                                                                // passes

JSONCompare.compare("""{ ".*oba.*": "some value" }""", """{ "foobar": "some value" }""")
        .assertMatches();                                                                // passes
```

Quote unintentional regex characters with `\Q…\E`:

```java
JSONCompare.compare("""{ "a": "\\\\Qd+\\\\E" }""", """{ "a": "d+" }""")
        .assertMatches();                                                                // passes
```

For case-insensitivity, use the inline `(?i)` / `(?-i)` flags.

To disable regex globally, use `CompareMode.REGEX_DISABLED` or supply a custom
`JsonComparator`:

```java
JSONCompare.compare(expected, actual)
        .comparator(new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) { return expected.equals(actual); }
            public boolean compareFields(String expected, String actual) { return expected.equals(actual); }
        })
        .assertMatches();
```

## 8. Embedded match tweaks

Match logic can be embedded directly inside the expected JSON, so assertions
can express conditions without writing any matcher code:

| Operator       | Meaning                                                        |
|----------------|----------------------------------------------------------------|
| `!<regex>`     | **DO NOT MATCH** — negate the comparison for this value/field  |
| `!.*`          | **DO NOT MATCH ANY** — actual must NOT have extra fields/items |
| `.*`           | **MATCH ANY** — actual MUST have at least one extra field/item |
| `#(<jsonpath>)`| Apply a JSONPath query and match the result against the value  |

### 8.1 Negation — `!`

```java
JSONCompare.compare("""{ "a": "!test" }""", """{ "a": "testing" }""")
        .assertMatches();                                                                // passes — value does not match "test"

JSONCompare.compare("""{ "!a": "value does not matter" }""", """{ "b": "anything" }""")
        .assertMatches();                                                                // passes — actual has no field "a"
```

When negating a field name, the value side is ignored: it asserts only that
no actual field with the given name exists at that level.

### 8.2 Forbid extras — `!.*`

```java
String expected = """{ "b": "val1", "!.*": ".*" }""";
String actual   = """{ "a": "val2", "b": "val1" }""";
JSONCompare.compare(expected, actual).assertNotMatches();                                // actual has extra fields
```

```java
String expected = """[ false, "test", 4, "!.*" ]""";
String actual   = """[ 4, false, "test", 1 ]""";
JSONCompare.compare(expected, actual).assertNotMatches();                                // actual has an extra element
```

### 8.3 Require extras — `.*`

```java
String expected = """{ "b": "val1", ".*": ".*" }""";
String actual   = """{ "b": "val1" }""";
JSONCompare.compare(expected, actual).assertNotMatches();                                // actual has NO extra fields
```

### 8.4 JSONPath — `#(...)`

A field whose name has the form `#(<jsonpath>)` runs the path against actual
and matches the result against the field's value:

```java
String expected = """
        {
          "#($.store..isbn)": [ "0-395-19395-8", "0-553-21311-3", "!.*" ]
        }
        """;
// actual: a typical { "store": { "book": [ ... ] } } document
JSONCompare.compare(expected, actual).assertMatches();
```

The example asserts the store contains exactly two specific ISBNs — the
trailing `"!.*"` forbids any further results.

## 9. Differences

Matching is soft: every mismatch is collected, then reported together.

```java
List<String> diffs = JSONCompare.compare(expected, actual).diffs(); // empty on match
```

`assertMatches()` formats the same diffs into an `AssertionFailedError`:

```
org.opentest4j.AssertionFailedError: FOUND 4 DIFFERENCE(S):

_________________________DIFF__________________________
$.caught
Expected value: false But got: true

_________________________DIFF__________________________
$.pain.range[1] was not found:
"blue"

_________________________DIFF__________________________
$.pain.range[2] was not found:
-2059921070

_________________________DIFF__________________________
$.pain.not_anyone was not found
```

Add a custom note with `.message("…")` — it is appended to the failure
output.

## 10. Related

- [JTest-Utils](https://github.com/fslev/jtest-utils) — uses json-compare and
  adds data capture support.

## 11. License

Released under the [Apache License 2.0](LICENSE).
