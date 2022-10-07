# JSON Compare <sup>[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://vshymanskyy.github.io/StandWithUkraine)</sup>

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fslev/json-compare.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.fslev%22%20AND%20a:%22json-compare%22)
![Build status](https://github.com/fslev/json-compare/workflows/Java%20CI%20with%20Maven/badge.svg?branch=main)
[![Coverage Status](https://coveralls.io/repos/github/fslev/json-compare/badge.svg?branch=main)](https://coveralls.io/github/fslev/json-compare?branch=main)

A Java library for comparing JSONs, with some tweaks !

## Brief
Compare any JSON convertible Java objects and check detailed differences between them.  
The library has some tweaks which helps you make assertions without writing any code at all.  

# Features
- **[Compare modes](#compare-modes)**
- **[Regular expression support](#regex)**
- **[Differences](#differences)**
- **[Tweaks](#tweaks)**
- **[Json Path support](#json-path)**

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

### Match any JSON convertible Java objects
```javascript
String expectedString = "{\"a\":1, \"b\": [4, 2, \"\\\\d+\"]}";
String actualString = "{\"a\":1, \"b\":[4, 2, 5], \"c\":3}";
JSONCompare.assertMatches(expectedString, actualString); // assertion passes

// actual represented as Map
Map<String, Object> actualMap = new HashMap<>();
actualMap.put("a", 1);
actualMap.put("b", Arrays.asList(4, 2, 5));
actualMap.put("c", 3);
JSONCompare.assertMatches(expectedString, actualMap); // assertion passes

// Failed assertion
String anotherActualString = "{\"a\":10, \"b\":[4, 20, 5], \"c\":3}";
JSONCompare.assertNotMatches(expectedString, anotherActualString); // assertion passes
JSONCompare.assertMatches(expectedString, anotherActualString); // assertion fails

==>

org.opentest4j.AssertionFailedError: FOUND 2 DIFFERENCE(S):


_________________________DIFF__________________________
a -> 
Expected value: 1 But got: 10

_________________________DIFF__________________________
b -> 
Expected element from position 2 was NOT FOUND:
2
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
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertMatches(expected, actual); // assertion passes

// JSON objects MUST have same sizes
String expected1 = "{\"b\":\"val1\"}";
String actual1 = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertNotMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))); // assertion passes
JSONCompare.assertMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))); // assertion fails

==>                             
org.opentest4j.AssertionFailedError: FOUND 1 DIFFERENCE(S):

_________________________DIFF__________________________
Actual JSON OBJECT has extra fields
```
...same for **JSON_ARRAY_NON_EXTENSIBLE**  

_Example of **JSON_ARRAY_STRICT_ORDER**:_
```javascript
// JSON Array strict order is by default ignored
String expected = "[1, 2, 3]";
String actual = "[3, 2, 1, 5, 4]";
JSONCompare.assertMatches(expected, actual); // assertion passes

// Check JSON Array strict order
String expected1 = "[1, 2, 3]";
String actual1 = "[3, 2, 1, 5, 4]";
JSONCompare.assertNotMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER))); // assertion passes
JSONCompare.assertMatches(expected1, actual1, new HashSet<>(Arrays.asList(CompareMode.JSON_ARRAY_STRICT_ORDER))); // assertion fails

==>
org.opentest4j.AssertionFailedError: FOUND 2 DIFFERENCE(S):


_________________________DIFF__________________________
JSON ARRAY elements differ at position 1:
1
________diffs________

Expected value: 1 But got: 3

_________________________DIFF__________________________
JSON ARRAY elements differ at position 3:
3
________diffs________

Expected value: 3 But got: 1
```

# <a name="regex"></a> Regular expression support

You can use regular expressions on JSON values:
```javascript
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertMatches(expected, actual);
```
, but also on JSON object fields:

```javascript
String expected = "{\".*oba.*\":\"some value\"}";
String actual = "{\"foobar\":\"some value\"}";
JSONCompare.assertMatches(expected, actual);
```

JSONCompare by default matches JSON fields and values using regular expressions.  
If you have unintentional regex characters inside either expected values or expected fields, then you can quote them:  

```javascript
String expected = "{\"a\":\"\\\\Qd+\\\\E\"}";
String actual = "{\"a\":\"d+\"}";
JSONCompare.assertMatches(expected, actual);
```

By quoting special characters, using \Q and \E, you disable the regex mechanism for that corresponding sequence.  
From Java _Pattern_ docs:  
```
\Q	Nothing, but quotes all characters until \E
\E	Nothing, but ends quoting started by \Q
```

However, you can ignore the default regular expression compare mode, by using a ***custom comparator***
```javascript
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertMatches(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
// should fail
```

# <a name="differences"></a>Differences
```javascript
String expected = "{\n" +
        "  \"caught\": false,\n" +
        "  \"pain\": {\n" +
        "    \"range\": [\n" +
        "      \"bell\",\n" +
        "      \"blue\",\n" +
        "      -2059921070\n" +
        "    ],\n" +
        "    \"not_anyone\": -1760889549.4041045,\n" +
        "    \"flat\": -2099670336\n" +
        "  }\n" +
        "}";
        
String actual = "{\n" +
        "  \"caught\": true,\n" +
        "  \"pain\": {\n" +
        "    \"range\": [\n" +
        "      \"bell\",\n" +
        "      \"red\",\n" +
        "      -2059921075\n" +
        "    ],\n" +
        "    \"anyone\": -1760889549.4041045,\n" +
        "    \"flat\": -2099670336\n" +
        "  },\n" +
        "  \"broad\": \"invented\"\n" +
        "}";
        
JSONCompare.assertMatches(expected, actual);
```
#### Output
```javascript
org.opentest4j.AssertionFailedError: FOUND 4 DIFFERENCE(S):


_________________________DIFF__________________________
caught -> 
Expected value: false But got: true

_________________________DIFF__________________________
pain -> range -> 
Expected element from position 2 was NOT FOUND:
"blue"

_________________________DIFF__________________________
pain -> range -> 
Expected element from position 3 was NOT FOUND:
-2059921070

_________________________DIFF__________________________
pain -> Field 'not_anyone' was NOT FOUND


Json matching by default uses regular expressions.
In case expected json contains any unintentional regexes, then quote them between \Q and \E delimiters or use a custom comparator.
 ==> 
<Click to see difference>
```

# <a name="tweaks"></a>Matching with some tweaks
By using the `!` DO NOT MATCH option, the comparison between JSON values will be negated:    

```javascript
String expected = "{\"a\":\"!test\"}";
String actual = "{\"a\":\"testing\"}";
JSONCompare.assertMatches(expected, actual);
```
or, between JSON object fields
```javascript
String expected = "{\"!a\":\"value does not matter\"}";
String actual = "{\"b\":\"value does not matter\"}";
JSONCompare.assertMatches(expected, actual);
```

Negating a field name, it means that the actual JSON object should not have any field with same name on same level.
In this particular case, field values are ignored.  

Of course, you can use negative lookahead or lookbehind regular expressions
```javascript
String expected = "{\"(?!lorem.*).*\":\"valorem\"}";
String actual = "{\"ipsum\":\"valorem\"}";
JSONCompare.assertMatches(expected, actual);
```
The assertion will pass if the actual JSON has a field which does not contain 'lorem' and which points to value 'valorem'.  


Check for extra JSON values or fields by using the power of `regex` and `DO NOT MATCH` use case  
```javascript
// actual Json should NOT contain any extra fields

String expected = "{\"b\":\"val1\",\"a\":\"val2\",\"!.*\":\".*\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertMatches(expected, actual);

String expected = "[false,\"test\",4,\"!.*\"]";
String actual = "[4,false,\"test\"]";
JSONCompare.assertMatches(expected, actual);

// actual Json array should contain extra elements
String expected = "[false,\"test\",4,\".*\"]";
String actual = "[4,false,\"test\"]";
JSONCompare.assertNotMatches(expected, actual);
```
# <a name="json-path"></a>Embedded json path expression
Powered by [JsonPath](https://github.com/json-path/JsonPath)  
The expected JSON can contain json path expressions delimited by __'#('__ and __')'__ together with the expected results:  
```javascript
String expected = "{\"#($.store..isbn)\":[\"0-395-19395-8\",\"0-553-21311-3\",\"!.*\"]}";
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
JSONCompare.assertMatches(expected, actual);
```
