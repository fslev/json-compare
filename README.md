# JSONCompare

A Java library for comparing JSON objects

## Summary
Write tests that compare two JSON objects and check the differences between them.

## Dependencies

1. JUnit
2. Jackson

## Examples

With JSONCompare you assert by default that the expected JSON is included within the actual JSON

```javascript
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertEquals(expected, actual);
```

But you can use the special comparators: JSON_OBJECT_NON_EXTENSIBLE or JSON_ARRAY_NON_EXTENSIBLE for exact match

```javascript
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_OBJECT_NON_EXTENSIBLE);
```

You can use regular expressions on values
```javascript
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertEquals(expected, actual);
```
, or on JSON object fields

```javascript
String expected = "{\".*oba.*\":\"some value\"}";
String actual = "{\"foobar\":\"some value\"}";
JSONCompare.assertEquals(expected, actual);
```

You can ignore regular expression compare
```javascript
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertNotEquals(expected, actual, DO_NOT_USE_REGEX);
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

## More examples
You can also use the "!" DO NOT MATCH option, in order to negate the comparison between JSON values

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

With JSONCompare you can check for extra JSON values or fields
```javascript
String expected = "{\"b\":\"val1\",\"a\":\"val2\",\"!.*\":\".*\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertEquals(expected, actual);

String expected = "[1,\"test\",4,\"!.*\"]";
String actual = "[4,1,\"test\"]";
JSONCompare.assertEquals(expected, actual);

String expected = "[1,\"test\",4,\".*\"]";
String actual = "[4,1,\"test\"]";
JSONCompare.assertNotEquals(expected, actual);    
        
```

Use the JSON_ARRAY_STRICT_ORDER:
```
String expected = "[1,\"test\",4]";
String actual = "[4,1,\"test\"]";
JSONCompare.assertNotEquals(expected, actual, CompareMode.JSON_ARRAY_STRICT_ORDER);    
        
```

      