# Changelog

## 5.2-SNAPSHOT

## [5.1] (2022-10-05)
- refactor DO_NOT_MATCH_ANY use case behaviour for JSON arrays

## [5.0] (2022-10-04)
- Differences support

## 4.5
Do not include values inside assertion error message if JSONs are equal  

## 4.4
Enable assertion error diff support:   
  - Refactor fail() with AssertionFailureBuilder  

## 4.3
Refactor JsonUtils pretty print  

## 4.2
Add and use JsonUtils     
Update dependencies  

## 4.1
Upgrade Junit Jupiter API - 5.9.0    

## 4.0
Renamed base package name to 'io.json.compare'  
Refactored JSON paths from assertion error message  
Failed JSON conversion now throws a RuntimeException enhanced with Jackson exception message

## 3.0
Renamed JSONCompare.assertEquals() and .assertNotEquals() to .assertMatches() and .assertNotMatches()  
JSONCompare now accepts JSON convertible Objects as arguments  
Update dependencies  

## 2.43
Refactor Hint message for unintentional regexes  

## 2.42
Update dependencies  

## 2.41
AssertionError message contains hint for unintentional regexes only when default comparator is used  

## 2.40
Enhance AssertionError message with hint for unintentional regexes  

## 2.39
Update dependencies  

## 2.38
Update Jackson dependencies    

## 2.37
Update dependency  

## 2.36
Use Junit Jupiter API  

## 2.35
Bugfix - Compare empty JSON Objects  

## 2.34
Json path support   

## 2.33
Match missing nodes    

## 2.32
Code refactoring. Small performance improvement  

## 2.31
Upgrade dependencies  

## 2.30
Upgrade dependencies  

## 2.29
Upgrade dependencies    
Refactor error message    

## 2.28
Fix message formatting  

## 2.27
Fix message formatting  

## 2.26
Refactor message formatting  

## 2.25
Enhance message formatting  

## 2.24
Update dependencies  

## 2.23
Update dependencies  

## 2.22
Crop error message larger than 65535 characters  

## 2.21
Dependencies update  

## 2.20
Upgrade Junit    

## 2.19
Upgrade dependencies  

## 2.18  
Refactoring  
Java 8 support  

## 2.17
Change assertion error message order      

## 2.16
Refactoring  

## 2.15      
Enhance error message  
Update Jackson dependencies  

## 2.14        
Update Jackson dependencies  

## 2.13  
Update Jackson dependencies  

## 2.12  
Fix thread safety bug        

## 2.11
Java 8 compatible    

## 2.10
Bug fixes       

## 2.0.9
Bug fixes   

## 2.0.7
Bug fix    

## 2.0.6
Bug fix  
Internal refactoring   

## 2.0.5
Correct usage of custom message 

## 2.0.4
Log improvement

## 2.0.3
Bug fixing

## 2.0.2
Add more methods to compare JSONs

## 2.0.1
Optimization

## 2.0.0
Add custom comparator :)
Remove DO_NOT_USE_REGEX and CASE_INSENSITIVE compare modes
BY default, the string matching is done case insensitive

## 1.1.2
Bug fixes


      
