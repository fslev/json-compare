package io.json.compare.matcher.issues;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

class Issue11Test {

    @Test
    void testSimpleJsonArrayStrictOrderWithRegexFieldsThrowsCorrectMessage() {
        String expected = """
                {
                  ".*": {
                    "eventLogs": [
                      {
                        "id": 2
                      },
                      {
                        "id": 4
                      },
                      {
                        "id": 1
                      },
                      {
                        "id": 3
                      }
                    ]
                  }
                }
                """;
        String actual = """
                {
                  "_embedded": {
                    "eventLogs": [
                      {
                        "id": 1
                      },
                      {
                        "id": 2
                      },
                      {
                        "id": 3
                      },
                      {
                        "id": 4
                      }
                    ]
                  }
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("$..*.eventLogs[0].id"));
        }
    }

    @Test
    void testJsonArrayStrictOrderThrowsCorrectMessage() {
        String expected = """
                {
                  "_embedded": {
                    "eventLogs": [
                      {
                        "id": 2
                      },
                      {
                        "id": 4
                      },
                      {
                        "id": 1
                      },
                      {
                        "id": 3
                      }
                    ]
                  }
                }
                """;
        String actual = """
                {
                  "_embedded": {
                    "eventLogs": [
                      {
                        "id": 1,
                        "session_id": "session id 1",
                        "date": "2019-10-01T10:40:16.000+0000",
                        "entity": "USER",
                        "action": "CREATE",
                        "data": {
                          "some data for session id": 1
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 1",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/1"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/1"
                          }
                        }
                      },
                      {
                        "id": 2,
                        "session_id": "session id 2",
                        "date": "2019-10-01T10:40:16.000+0000",
                        "entity": "GROUP",
                        "action": "UPDATE",
                        "data": {
                          "some data for session id": 2
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 2",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/2"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/2"
                          }
                        }
                      },
                      {
                        "id": 3,
                        "session_id": "session id 2",
                        "date": "2019-10-02T10:40:16.000+0000",
                        "entity": "USER",
                        "action": "REMOVE",
                        "data": {
                          "some other data for session id": 2
                        },
                        "outcome": "SUCCESS",
                        "message": "some other for session 2",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/3"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/3"
                          }
                        }
                      },
                      {
                        "id": 4,
                        "session_id": "session id 3",
                        "date": "2019-10-03T10:40:16.000+0000",
                        "entity": "GROUP",
                        "action": "ADD_TO_GROUP",
                        "data": {
                          "some data for session id": 3
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 3",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/4"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/4"
                          }
                        }
                      }
                    ]
                  },
                  "_links": {
                    "self": {
                      "href": "http://clouddashboardqa01.ops.server.lan:38351/event/search/by?start=2019-10-01T00%3A00%3A00.000-00%3A00&end=2019-10-05T23%3A59%3A59.000-00%3A00&page=0&size=20&sort=date,asc"
                    }
                  },
                  "page": {
                    "size": 20,
                    "totalElements": 4,
                    "totalPages": 1,
                    "number": 0
                  }
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("$._embedded.eventLogs[0].id"));
        }
    }

    @Test
    void testJsonArrayStrictOrderWithRegexFieldsThrowsCorrectMessage() {
        String expected = """
                {
                  ".*": {
                    "eventLogs": [
                      {
                        "id": 2
                      },
                      {
                        "id": 4
                      },
                      {
                        "id": 1
                      },
                      {
                        "id": 3
                      }
                    ]
                  }
                }
                """;
        String actual = """
                {
                  "_embedded": {
                    "eventLogs": [
                      {
                        "id": 1,
                        "session_id": "session id 1",
                        "date": "2019-10-01T10:40:16.000+0000",
                        "entity": "USER",
                        "action": "CREATE",
                        "data": {
                          "some data for session id": 1
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 1",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/1"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/1"
                          }
                        }
                      },
                      {
                        "id": 2,
                        "session_id": "session id 2",
                        "date": "2019-10-01T10:40:16.000+0000",
                        "entity": "GROUP",
                        "action": "UPDATE",
                        "data": {
                          "some data for session id": 2
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 2",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/2"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/2"
                          }
                        }
                      },
                      {
                        "id": 3,
                        "session_id": "session id 2",
                        "date": "2019-10-02T10:40:16.000+0000",
                        "entity": "USER",
                        "action": "REMOVE",
                        "data": {
                          "some other data for session id": 2
                        },
                        "outcome": "SUCCESS",
                        "message": "some other for session 2",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/3"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/3"
                          }
                        }
                      },
                      {
                        "id": 4,
                        "session_id": "session id 3",
                        "date": "2019-10-03T10:40:16.000+0000",
                        "entity": "GROUP",
                        "action": "ADD_TO_GROUP",
                        "data": {
                          "some data for session id": 3
                        },
                        "outcome": "SUCCESS",
                        "message": "some message for session 3",
                        "_links": {
                          "self": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/4"
                          },
                          "eventLog": {
                            "href": "http://clouddashboardqa01.ops.server.lan:38351/event/4"
                          }
                        }
                      }
                    ]
                  },
                  "_links": {
                    "self": {
                      "href": "http://clouddashboardqa01.ops.server.lan:38351/event/search/by?start=2019-10-01T00%3A00%3A00.000-00%3A00&end=2019-10-05T23%3A59%3A59.000-00%3A00&page=0&size=20&sort=date,asc"
                    }
                  },
                  "page": {
                    "size": 20,
                    "totalElements": 4,
                    "totalPages": 1,
                    "number": 0
                  }
                }
                """;
        try {
            JSONCompare.compare(expected, actual).modes(CompareMode.JSON_ARRAY_STRICT_ORDER).assertMatches();
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("was not found"));
        }
    }
}
