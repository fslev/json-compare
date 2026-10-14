package io.json.compare.matcher.diffs;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonRealWorldDiffTests {

    @Test
    void compareJsonObjectsAndCheckForDifferences() {
        String expected = """
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
        String actual = """
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

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 4 DIFFERENCE.*" +
                "\\Q$.caught\\E.*Expected value: false But got: true.*" +
                "\\Q$.pain.range[1]\\E was not found.*\"blue\".*" +
                "\\Q$.pain.range[2]\\E was not found.*-2059921070.*" +
                "\\Q$.pain.not_anyone\\E was not found.*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForClosestUnmatchedElementOfNotFoundObject() {
        String expected = """
                {
                  "users": [ { "id": 42, "name": "Alice", "zip": "10115" } ]
                }
                """;
        String actual = """
                {
                  "users": [
                    { "id": 43, "name": "Carol", "zip": "10115" },
                    { "id": 42, "name": "Alice", "zip": "10117" }
                  ]
                }
                """;

        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "EXPECTED \\Q$.users[0]\\E was not found.*" +
                "Closest unmatched ACTUAL element \\Q$.users[1]\\E differs by:.*" +
                "EXPECTED \\Q$.users[0].zip\\E.*Expected value: \"10115\" But got: \"10117\".*"));
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    void compareJsonArraysAndCheckForClosestUnmatchedElementNesterArrays() {
        String expected = """
                {
                   "zone": "test-zone.com",
                   "records": [
                     {
                       "name": "test-zone.com",
                       "type": "MX",
                       "content": "mail-server1.com",
                       "templates": [
                         {
                           "serviceId": "O365",
                           "providerId": "abc.com"
                         },
                         {
                           "serviceId": "Microsoft_Exchange",
                           "providerId": "microsoft.com"
                         }
                       ]
                     },
                     {
                       "name": "www.test-zone.com",
                       "type": "A",
                       "content": "2.2.2.2",
                       "templates": [
                         {
                           "serviceId": "IP",
                           "providerId": "abc.com"
                         },
                         {
                           "serviceId": "Microsoft_Exchange",
                           "providerId": "microsoft.com"
                         }
                       ]
                     }
                   ]
                 }
                """;
        String actual = """
                {
                   "zone": "test-zone.com",
                   "records": [
                     {
                       "name": "www.test-zone.com",
                       "type": "A",
                       "content": "2.2.2.2",
                       "templates": [
                         {
                           "serviceId": "Microsoft_Exchange",
                           "providerId": "microsoft-updated.com"
                         }, 
                         {
                           "serviceId": "IP",
                           "providerId": "abc.com"
                         },
                         {
                           "serviceId": "zzz",
                           "providerId": "zzz.com"
                         }                         
                       ]
                     }, 
                     {
                       "name": "test-zone.com",
                       "type": "MX",
                       "content": "mail-server1.com",
                       "templates": [
                         {
                           "serviceId": "O365",
                           "providerId": "abc.com"
                         },
                         {
                           "serviceId": "Microsoft_Exchange",
                           "providerId": "microsoft.com"
                         }
                       ]
                     }                     
                   ]
                 }
                """;

        JSONCompare.compare(expected, actual).assertMatches();
    }
}
