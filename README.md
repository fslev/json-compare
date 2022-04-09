# JSON Compare <sup>[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://vshymanskyy.github.io/StandWithUkraine)
</sup>

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fslev/json-compare.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.fslev%22%20AND%20a:%22json-compare%22)
![Build status](https://github.com/fslev/json-compare/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/fslev/json-compare/badge.svg?branch=master)](https://coveralls.io/github/fslev/json-compare?branch=master)

A Java library for comparing JSONs, with some tweaks !

## Summary
Compare two JSONs and check detailed differences between them.  
The library contains some tweaks which help you make assertions by writing less code.   

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

## Examples

Assert that expected JSON is included within the actual JSON:

```javascript
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertEquals(expected, actual);
```
JSON inclusion is checked by default, but you can use the special compare modes:
* JSON_OBJECT_NON_EXTENSIBLE
* JSON_ARRAY_NON_EXTENSIBLE
* JSON_ARRAY_STRICT_ORDER


```javascript
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE, CompareMode.JSON_ARRAY_NON_EXTENSIBLE);
                            
String expected = "[false,\"test\",4]";
String actual = "[4,false,\"test\"]";
JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_ARRAY_STRICT_ORDER);                            
```

You can use regular expressions on JSON values:
```javascript
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertEquals(expected, actual);
```
, but also on JSON object fields:

```javascript
String expected = "{\".*oba.*\":\"some value\"}";
String actual = "{\"foobar\":\"some value\"}";
JSONCompare.assertEquals(expected, actual);
```

JSONCompare by default matches JSON fields and values using regular expressions.  
If you have special regex characters inside either expected values or expected fields, but you literally want to match them, then you can quote them:  

```javascript
String expected = "{\"a\":\"\\\\Q\\\\d+\\\\E\"}";
String actual = "{\"a\":\"\\\\d+\"}";
JSONCompare.assertEquals(expected, actual);
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
JSONCompare.assertEquals(expected, actual, new JsonComparator() {
            public boolean compareValues(Object expected, Object actual) {
                return expected.equals(actual);
            }

            public boolean compareFields(String expected, String actual) {
                return expected.equals(actual);
            }
        });
// should fail
```

## Error messages
```javascript
String expected = "{\"b\":{\"x\":\"val1\",\"y\":\"val2\"},\"a\":{\"t\":\"val3\",\"z\":\"val1\"}}";
String actual = "{\"a\":{\"t\":\"val3\",\"z\":\"val4\"},\"b\":{\"x\":\"val1\",\"y\":\"val2\"}}";
JSONCompare.assertEquals(expected, actual);

Output:

java.lang.AssertionError: Expected ["val1"] but found ["val4"] <- field "z" <- field "a"
Expected:
{
  "b" : {
    "x" : "val1",
    "y" : "val2"
  },
  "a" : {
    "t" : "val3",
    "z" : "val1"
  }
}
But got:
{
  "a" : {
    "t" : "val3",
    "z" : "val4"
  },
  "b" : {
    "x" : "val1",
    "y" : "val2"
  }
}
```

## Matching with some tweaks
By using the `!` DO NOT MATCH option, the comparison between JSON values will be negated:    

```javascript
String expected = "{\"a\":\"!test\"}";
String actual = "{\"a\":\"testing\"}";
JSONCompare.assertEquals(expected, actual);
```
or, between JSON object fields
```javascript
String expected = "{\"!a\":\"value does not matter\"}";
String actual = "{\"ab\":\"value does not matter\"}";
JSONCompare.assertEquals(expected, actual);
```

Check for extra JSON values or fields by using the power of `regex` and `DO NOT MATCH` use case  
```javascript
String expected = "{\"b\":\"val1\",\"a\":\"val2\",\"!.*\":\".*\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertEquals(expected, actual);

String expected = "[false,\"test\",4,\"!.*\"]";
String actual = "[4,false,\"test\"]";
JSONCompare.assertEquals(expected, actual);

String expected = "[false,\"test\",4,\".*\"]";
String actual = "[4,false,\"test\"]";
JSONCompare.assertNotEquals(expected, actual);
```
### Embedded json path expression
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
JSONCompare.assertEquals(expected, actual);
```
