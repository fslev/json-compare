# JSON Compare <sup>[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://vshymanskyy.github.io/StandWithUkraine)</sup>

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fslev/json-compare.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.fslev%22%20AND%20a:%22json-compare%22)
![Build status](https://github.com/fslev/json-compare/actions/workflows/maven.yml/badge.svg?branch=main)
[![Coverage Status](https://coveralls.io/repos/github/fslev/json-compare/badge.svg?branch=main)](https://coveralls.io/github/fslev/json-compare?branch=main)



A Java library for matching JSONs, with some tweaks !

## Brief
Compare any JSON convertible Java objects and check the differences between them when matching fails.  
The library has some tweaks which helps you make assertions without writing any code at all.  

# Features
- **[Compare modes](#compare-modes)**
- **[Regex support](#regex)**
- **[Differences](#differences)**
- **[Tweaks](#tweaks)**
- **[Json Path support](#json-path)**
- **[Extended](#extended)**
## Based on

1. Junit Jupiter API
2. Jackson
3. Jayway json-path

## Central Repository

#### Apache Maven
```
<dependency>
    <groupId>com.github.fslev</groupId>
    <artifactId>json-compare</artifactId>
    <version>${version.from.maven.central}</version>
</dependency>
```
#### Gradle/Grails
```
compile 'com.github.fslev:json-compare:<version.from.maven.central>'
```

### Match JSONs
JSONCompare will automatically try to convert any given expected or actual Java objects to [Jackson JsonNode](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/JsonNode.html)s and match them.  
```javascript
// expected as String with regex
String expectedString = 
"""
    {
      "string": "I'm on a seafood diet. I see food and I eat it!",
      "number": "\\\\d+.\\\\d+",
      "object": {
        "pun": "\\\\QWhy don't skeletons fight each other? They don't have the guts!\\\\E"
      },
      "array": [".*", "\\\\d+"],
      "boolean": "true|false"
    }
""";

// actual as String
String actualString = 
"""
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

JSONCompare.assertMatches(expectedString, actualString); // assertion passes
```

Both expected and actual can be represented as any JSON convertible object:  
```javascript
String expectedString = "{\"a\":1, \"b\": [4, \"ipsum\", \"\\\\d+\"]}";

// actual represented as Map
Map<String, Object> actualMap = new HashMap<>();
actualMap.put("a", 1);
actualMap.put("b", Arrays.asList("ipsum", 4, 5));
actualMap.put("c", true);

JSONCompare.assertMatches(expectedString, actualMap); // assertion passes
```

# <a name="compare-modes"></a>Compare modes

By default, [JSONCompare](https://github.com/fslev/json-compare) rules out the Json sizes and also the order of elements from an array. 
This behaviour can be overridden by using the following compare modes:  
* JSON_OBJECT_NON_EXTENSIBLE
* JSON_ARRAY_NON_EXTENSIBLE
* JSON_ARRAY_STRICT_ORDER

_Example of **JSON_OBJECT_NON_EXTENSIBLE**:_
```javascript
// Expected Json is included in actual Json
String expected = "{\"b\": \"val1\"}";
String actual = "{\"a\":\"val2\", \"b\":\"val1\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes

// JSON objects MUST have same sizes
String expected1 = "{\"b\": \"val1\"}";
String actual1 = "{\"a\":\"val2\", \"b\":\"val1\"}";
JSONCompare.assertNotMatches(expected1, actual1, Set.of(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)); // assertion passes
JSONCompare.assertMatches(expected1, actual1, Set.of(CompareMode.JSON_OBJECT_NON_EXTENSIBLE)); // assertion fails

==>                             
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$ -> Actual JSON OBJECT has extra fields
```
...same for **JSON_ARRAY_NON_EXTENSIBLE**  

_Example of **JSON_ARRAY_STRICT_ORDER**:_
```javascript
// JSON array strict order is by default ignored
String expected = "[\"lorem\", 2, false]";
String actual = "[false, 2, \"lorem\", 5, 4]";
JSONCompare.assertMatches(expected, actual); // assertion passes

// Check JSON array strict order
String expected1 = "[\"lorem\", 2, false]";
String actual1 = "[false, 2, \"lorem\", 5, 4]";
JSONCompare.assertNotMatches(expected1, actual1, Set.of(CompareMode.JSON_ARRAY_STRICT_ORDER)); // assertion passes
JSONCompare.assertMatches(expected1, actual1, Set.of(CompareMode.JSON_ARRAY_STRICT_ORDER)); // assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 2 DIFFERENCE(S):


_________________________DIFF__________________________
$[0]
Expected value: "lorem" But got: false

_________________________DIFF__________________________
$[2]
Expected boolean: false But got: "lorem"
```

# <a name="regex"></a> Regular expression support

You can use regular expressions on expected JSON values:
```javascript
String expected = "{\"a\": \".*me.*\"}";
String actual = "{\"a\": \"some text\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```
, but also on expected JSON object fields:

```javascript
String expected = "{\".*oba.*\": \"some value\"}";
String actual = "{\"foobar\": \"some value\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```

[JSONCompare](https://github.com/fslev/json-compare) is by default case-sensitive and supports regular expressions on JSON fields and values.  
If you have unintentional regex characters inside either expected values or expected fields, then you can quote them:  

```javascript
String expected = "{\"a\":\"\\\\Qd+\\\\E\"}";
String actual = "{\"a\":\"d+\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```

By quoting special characters, using \Q and \E, you disable the regex mechanism for that corresponding sequence.  
From Java _Pattern_ docs:  
```
\Q	Nothing, but quotes all characters until \E
\E	Nothing, but ends quoting started by \Q
```

If you want to enable case-insensitivity, then use `(?i)` and `(?-i)` modifiers.  
However, you can ignore the default regular expression compare mode, by using a ***custom comparator***
```javascript
String expected = "{\"a\": \"\\\\d+\"}";
String actual = "{\"a\": \"\\\\d+\"}";
JSONCompare.assertMatches(expected, actual, new JsonComparator() {
    public boolean compareValues(Object expected, Object actual) {
        return expected.equals(actual);
    }

    public boolean compareFields(String expected, String actual) {
        return expected.equals(actual);
    }
}); // assertion passes
```
OR, use CompareMode.REGEX_DISABLED:
```javascript
String expected = "{\"a\":\"(some value)\"}";
String actual = "{\"a\":\"(some value)\"}";
JSONCompare.assertMatches(expected, actual, new HashSet<>(Collections.singletonList(CompareMode.REGEX_DISABLED))); // assertion passes
JSONCompare.assertMatches(expected, actual); // assertion fails
```

# <a name="differences"></a>Differences
Matching is based on soft assertion. It does not stop at first encountered difference, but it continues until expected JSON is depleted. 
All differences are displayed via the AssertionError message from `JSONCompare.assertMatches()` or can be obtained as a List of Strings by using `JSONCompare.diffs()`:    
## AssertionError message
```javascript
String expected = 
    """
    {
      "caught": false,
      "pain": {
        "range": [
          "bell",
          "blue",
          -2059921070
        ],
        "not_anyone": -1760889549.4041045,
        "flat": -2099670336
      }
    }
    """;
        
String actual = 
    """
    {
      "caught": true,
      "pain": {
        "range": [
          "bell",
          "red",
          -2059921075
        ],
        "anyone": -1760889549.4041045,
        "flat": -2099670336
      },
      "broad": "invented"
    }
    """;

JSONCompare.assertMatches(expected, actual); // assertion fails
```
#### Output
```javascript
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


Json matching by default uses regular expressions.
In case expected json contains any unintentional regexes, then quote them between \Q and \E delimiters or use a custom comparator.
 ==> 
<Click to see difference>
```
## Differences as a list of Strings  
```
List<String> diffs = JSONCompare.diffs(expected, actual);
```

# <a name="tweaks"></a>Matching with some tweaks

[JSONCompare](https://github.com/fslev/json-compare) has some tweaks which help you to _fine tune_ the matching mechanism.  
These tweaks can be directly **embedded inside the expected JSON**, thus <ins>you don't have to write any code at all<ins>:  

- DO_NOT_MATCH  `!`
- DO_NOT_MATCH_ANY  `!.*`
- MATCH_ANY  `.*`
- JSON PATH expressions `#(..)`

### DO NOT MATCH `!`
By using the `!` **DO NOT MATCH** option, the comparison between JSON values will be negated:    

```javascript
String expected = "{\"a\": \"!test\"}";
String actual = "{\"a\": \"testing\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```
or, between JSON object fields
```javascript
String expected = "{\"!a\": \"value does not matter\"}";
String actual = "{\"b\": \"of course value does not matter\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```

Negating a field name, it means that the actual JSON object should not have any field with same name on same level.
**In this particular case, field values are ignored**.  

Of course, you can use negative lookahead or lookbehind regular expressions
```javascript
String expected = "{\"(?!lorem.*).*\": \"valorem\"}";
String actual = "{\"ipsum\": \"valorem\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes
```
The assertion will pass if the actual JSON has a field which does not contain _'lorem'_ and which points to value _'valorem'_.  
Same goes for JSON arrays.  

### DO NOT MATCH ANY `!.*`
Check for extra JSON values or fields by using the power of `regex` and **DO_NOT_MATCH_ANY** use case  
```javascript
// actual JSON object should NOT contain any extra fields
String expected = "{\"b\": \"val1\", \"!.*\": \".*\"}";
String actual = "{\"a\": \"val2\", \"b\": \"val1\"}";
JSONCompare.assertMatches(expected, actual); //assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$."!.*" condition was not met. Actual JSON OBJECT has extra fields
```
```javascript
// actual JSON array should NOT contain any extra elements
String expected = "[false, \"test\", 4, \"!.*\"]";
String actual = "[4, false, \"test\", 1]";
JSONCompare.assertNotMatches(expected, actual); // assertion fails
==>
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$[3] -> Expected condition "!.*" was not met. Actual JSON ARRAY has extra elements
```

### MATCH ANY `.*`
Check actual Json should have extra fields or elements.  
```javascript
// Actual JSON object should have extra fields
String expected = "{\"b\": \"val1\", \".*\": \".*\"}";
String actual = "{\"b\": \"val1\"}";
JSONCompare.assertMatches(expected, actual); // assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$..* was not found
```
```javascript
// Actual JSON array should have extra elements
String expected = "[false, \"test\", 4, \".*\"]";
String actual = "[4, false, \"test\"]";
JSONCompare.assertMatches(expected, actual); // assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$[3] -> Expected condition ".*" was not met. Actual JSON ARRAY has no extra elements
```
# <a name="json-path"></a>Embedded json path expression
Powered by [JsonPath](https://github.com/json-path/JsonPath)  
The expected JSON can contain json path expressions delimited by `#(` and `)` together with the expected results:  
```javascript
// Select the 'isbn' values of store books and match with expected ones  
String expected = "{\"#($.store..isbn)\":[\"0-395-19395-8\", \"0-553-21311-1\", \"!.*\"]}";

String actual = "{\n" +
    "    \"store\": {\n" +
    "        \"book\": [\n" +
    "            {\n" +
    "                \"category\": \"reference\",\n" +
    "                \"author\": \"Nigel Rees\",\n" +
    "                \"title\": \"Sayings of the Century\",\n" +
    "                \"price\": 8.95\n" +
    "            },\n" +
    "            {\n" +
    "                \"category\": \"fiction\",\n" +
    "                \"author\": \"Herman Melville\",\n" +
    "                \"title\": \"Moby Dick\",\n" +
    "                \"isbn\": \"0-553-21311-3\",\n" +
    "                \"price\": 8.99\n" +
    "            },\n" +
    "            {\n" +
    "                \"category\": \"fiction\",\n" +
    "                \"author\": \"J. R. R. Tolkien\",\n" +
    "                \"title\": \"The Lord of the Rings\",\n" +
    "                \"isbn\": \"0-395-19395-8\",\n" +
    "                \"price\": 22.99\n" +
    "            }\n" +
    "        ]\n" +
    "    }\n" +
    "}";
JSONCompare.assertMatches(expected, actual); // assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):


_________________________DIFF__________________________
$.#($.store..isbn)[1] was not found:
"0-553-21311-1"
Expected json path result:
["0-395-19395-8","0-553-21311-1","!.*"]
But got:
["0-553-21311-3","0-395-19395-8"]
```

# <a name="extended"></a> Extended
You might be also interested in looking into [JTest-Utils](https://github.com/fslev/jtest-utils) which uses JSONCompare with data capture support: 
https://github.com/fslev/jtest-utils?tab=readme-ov-file#17-match-and-capture

## Website
https://fslev.github.io/json-compare  
