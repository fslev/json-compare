# Changelog

## 6.12-SNAPSHOT

## 6.11 (2023-09-15)
- #### Changed
  - Update dependencies (junit-jupiter-api to v5.10.0)  
  
## 6.10 (2023-06-03)
- #### Changed
  - Added support to retrieve the differences as a List of Strings
    - `List<String> diffs = JSONCompare.diffs(expected, actual);`
  - Update dependencies (Jackson 2.15.2)


## 6.9 (2023-04-27)
- #### Changed
  - Use Integer.MAX_VALUE for ObjectMapper max number, nesting and String length  

## 6.8 (2023-04-26)
- #### Changed
  - Update dependencies (junit jupiter 5.9.3, Jackson 2.15.0, JsonPath 2.8.0) 

## 6.7 (2023-01-10)
- #### Changed
  - Update dependencies (junit jupiter 5.9.2)

## 6.6 (2022-12-31)
- #### Changed
  - Defined private constructors for utility classes  
  - Code refactoring  

## 6.5 (2022-12-22)
- #### Fixed
  - Case-sensitive match of JSON values and fields containing invalid regexes  

## 6.4 (2022-12-19)
- #### Changed
  - Added new compare mode: CompareMode.REGEX_DISABLED
    - JSONs can be matched without using regular expressions: any present regexes will be considered as normal text   
  - Refactoring - optimize imports  
  
## 6.3 (2022-11-24)
- #### Changed
  - Update dependencies (Jackson 2.14.1)

## 6.2 (2022-11-15)
- #### Changed
  - Describe the default case-sensitive matching behaviour inside the AssertionError Hint message  
  
## 6.1 (2022-11-14)
- #### Fixed
  - Matched JSON fields are also case-sensitive, by default  

## 6.0 (2022-11-14)
- #### Changed
  - By default, matched JSONs are case-sensitive

## 5.3 (2022-11-11)
- #### Changed
  - Small code refactoring
  - Jackson dependencies update
  
## 5.2 (2022-10-31)
- #### Changed
  - refactor AssertionError messages
- #### Fixed
  - MATCH_ANY and DO_NOT_MATCH_ANY use cases now work as expected while using a custom comparator  
  - DO_NOT_MATCH_ANY use case now works in all cases

## 5.1 (2022-10-05)
- #### Changed
  - refactor DO_NOT_MATCH_ANY use case behaviour for JSON arrays

## 5.0 (2022-10-04)
- #### Added
  - Differences support
