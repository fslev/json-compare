package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue4Test {

    @Test
    public void testIssue1() {
        String expected = """
                {
                  "/events/ote_oneandone.*": {
                    "payload": {
                      "type": "CREATE",
                      "context": {
                        "status": "FINISHED",
                        "resourceType": "DOMAIN",
                        "name": "test-create-1545209424099.com"
                      }
                    }
                  }
                }
                """;
        String actual = """
                {
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52": {
                    "payload": {
                      "type": "CREATE",
                      "eventDate": "2018-12-19T08:50:25.283Z",
                      "context": {
                        "status": "STARTED",
                        "resourceType": "DOMAIN",
                        "id": "dc3fac05-f3f1-42ca-93b8-30738b49f08a",
                        "name": "test-create-1545209424099.com"
                      }
                    },
                    "properties": {
                      "X_ProcessCorrelationID": "1545209424133",
                      "X_TenantID": "ote_oneandone",
                      "X_ExtRequestID": "1545209424133",
                      "type": "CREATE",
                      "X_RequestID": "bcb099d5-cfb7-47c8-8f98-66050fcebd0f",
                      "X_ClientID": "TEST"
                    },
                    "id": "tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52"
                  },
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79": {
                    "payload": {
                      "type": "CREATE",
                      "eventDate": "2018-12-19T08:50:38.989Z",
                      "context": {
                        "status": "FINISHED",
                        "resourceType": "DOMAIN",
                        "id": "dc3fac05-f3f1-42ca-93b8-30738b49f08a",
                        "name": "test-create-1545209424099.com"
                      }
                    },
                    "properties": {
                      "X_ProcessCorrelationID": "1545209424133",
                      "X_TenantID": "ote_oneandone",
                      "X_ExtRequestID": "1545209424133",
                      "type": "CREATE",
                      "X_RequestID": "bcb099d5-cfb7-47c8-8f98-66050fcebd0f",
                      "X_ClientID": "TEST"
                    },
                    "id": "tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79"
                  },
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18": {
                    "payload": {
                      "type": "EMAIL_VERIFICATION",
                      "eventDate": "2018-12-19T08:50:40.166Z",
                      "context": {
                        "status": "STARTED",
                        "resourceType": "DOMAIN",
                        "name": "test-create-1545209424099.com",
                        "data": {
                          "registrarPageUrl": "https://registrar.tenant.org/main/7694bfb4a00a9eea5e7098e209d991b2cdd8f08beb6a5cc5c3d115351da3fbc8-ffa1931216e1ccd24a3d99e2d2c2cd1d63b632e6ebfbb588da4f9eff9b3a0f64-b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec",
                          "hash": "b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec",
                          "sendEmail": true,
                          "processEndDate": "2019-01-03T08:50:39.732Z",
                          "contacts": {
                            "regc": {
                              "id": "INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264",
                              "contactItemId": "INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264",
                              "postalInfo": {
                                "name": "First Last",
                                "firstName": "First",
                                "lastName": "Last",
                                "salutation": "f",
                                "address": {
                                  "streets": [
                                    "Herschel Street"
                                  ],
                                  "countryCode": "GB",
                                  "postalCode": "SL1 1XS",
                                  "city": "SLOUGH"
                                }
                              },
                              "voice": "+49.7211234567",
                              "email": "domain1545209424133@1und1.de",
                              "createDate": "2018-12-19T08:50:30.312Z",
                              "authInfo": "bwj!9$iw"
                            }
                          }
                        }
                      }
                    },
                    "properties": {
                      "type": "EMAIL_VERIFICATION",
                      "X_TenantID": "ote_oneandone"
                    },
                    "id": "tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18"
                  }
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void testIssue1_negative() {
        String expected = """
                {
                  "/events/ote_oneandone.*": {
                    "payload": {
                      "type": "CREATE",
                      "context": {
                        "status": "FINISHED",
                        "resourceType": "DOMAIN",
                        "name": "test-create-1545209424099.com"
                      }
                    }
                  }
                }
                """;
        String actual = """
                {
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52": {
                    "payload": {
                      "type": "CREATE",
                      "eventDate": "2018-12-19T08:50:25.283Z",
                      "context": {
                        "status": "STARTED",
                        "resourceType": "DOMAIN",
                        "id": "dc3fac05-f3f1-42ca-93b8-30738b49f08a",
                        "name": "test-create-1545209424099.com"
                      }
                    },
                    "properties": {
                      "X_ProcessCorrelationID": "1545209424133",
                      "X_TenantID": "ote_oneandone",
                      "X_ExtRequestID": "1545209424133",
                      "type": "CREATE",
                      "X_RequestID": "bcb099d5-cfb7-47c8-8f98-66050fcebd0f",
                      "X_ClientID": "TEST"
                    },
                    "id": "tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52"
                  },
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79": {
                    "payload": {
                      "type": "CREATE",
                      "eventDate": "2018-12-19T08:50:38.989Z",
                      "context": {
                        "status": "FINISHED_NOT",
                        "resourceType": "DOMAIN",
                        "id": "dc3fac05-f3f1-42ca-93b8-30738b49f08a",
                        "name": "test-create-1545209424099.com"
                      }
                    },
                    "properties": {
                      "X_ProcessCorrelationID": "1545209424133",
                      "X_TenantID": "ote_oneandone",
                      "X_ExtRequestID": "1545209424133",
                      "type": "CREATE",
                      "X_RequestID": "bcb099d5-cfb7-47c8-8f98-66050fcebd0f",
                      "X_ClientID": "TEST"
                    },
                    "id": "tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79"
                  },
                  "/events/ote_oneandone/tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18": {
                    "payload": {
                      "type": "EMAIL_VERIFICATION",
                      "eventDate": "2018-12-19T08:50:40.166Z",
                      "context": {
                        "status": "STARTED",
                        "resourceType": "DOMAIN",
                        "name": "test-create-1545209424099.com",
                        "data": {
                          "registrarPageUrl": "https://registrar.tenant.org/main/7694bfb4a00a9eea5e7098e209d991b2cdd8f08beb6a5cc5c3d115351da3fbc8-ffa1931216e1ccd24a3d99e2d2c2cd1d63b632e6ebfbb588da4f9eff9b3a0f64-b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec",
                          "hash": "b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec",
                          "sendEmail": true,
                          "processEndDate": "2019-01-03T08:50:39.732Z",
                          "contacts": {
                            "regc": {
                              "id": "INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264",
                              "contactItemId": "INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264",
                              "postalInfo": {
                                "name": "First Last",
                                "firstName": "First",
                                "lastName": "Last",
                                "salutation": "f",
                                "address": {
                                  "streets": [
                                    "Herschel Street"
                                  ],
                                  "countryCode": "GB",
                                  "postalCode": "SL1 1XS",
                                  "city": "SLOUGH"
                                }
                              },
                              "voice": "+49.7211234567",
                              "email": "domain1545209424133@1und1.de",
                              "createDate": "2018-12-19T08:50:30.312Z",
                              "authInfo": "bwj!9$iw"
                            }
                          }
                        }
                      }
                    },
                    "properties": {
                      "type": "EMAIL_VERIFICATION",
                      "X_TenantID": "ote_oneandone"
                    },
                    "id": "tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18"
                  }
                }
                """;
        assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
    }
}
