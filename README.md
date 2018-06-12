# JSONCompare

A Java library for comparing JSON objects

## Summary
Write tests that compare two JSON objects and check the differences between them.

## Examples

With JSONCompare you assert by default that the expected JSON is included within the actual JSON

```python
String expected = "{\"b\":\"val1\"}";
String actual = "{\"a\":\"val2\",\"b\":\"val1\"}";
JSONCompare.assertEquals(expected, actual);
```

You can use regular expressions on values:
```python
String expected = "{\"a\":\".*me.*\"}";
String actual = "{\"a\":\"some text\"}";
JSONCompare.assertEquals(expected, actual);
```
, or on JSON Object fields

```python
String expected = "{\".*oba.*\":\"some value\"}";
String actual = "{\"foobar\":\"some value\"}";
JSONCompare.assertEquals(expected, actual);
```