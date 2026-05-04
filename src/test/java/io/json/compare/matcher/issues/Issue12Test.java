package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class Issue12Test {

    @Test
    public void testNegation() {
        String expected = """
                {
                  "domainConnect": {
                    "ionos.com": {
                      "temporaryRedirectWithwww": {
                        "@": {
                          "startDate": "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z",
                          "endDate": "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z"
                        },
                        "host": {
                          "startDate": "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z",
                          "!endDate": ".*"
                        }
                      },
                      "frameRedirectWithwww": {
                        "@": {
                          "startDate": "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}.\\\\d{3}Z",
                          "!endDate": ".*"
                        }
                      }
                    }
                  }
                }
                """;
        String actual = """
                {
                  "domainConnect": {
                    "ionos.com": {
                      "temporaryRedirectWithwww": {
                        "@": {
                          "startDate": "2020-04-21T09:56:18.960Z",
                          "endDate": "2020-04-21T09:56:23.609Z"
                        },
                        "host": {
                          "startDate": "2020-04-21T09:56:18.960Z"
                        }
                      },
                      "frameRedirectWithwww": {
                        "@": {
                          "startDate": "2020-04-21T09:56:23.576Z"
                        }
                      }
                    }
                  }
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }
}
