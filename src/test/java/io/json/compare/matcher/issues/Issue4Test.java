package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue4Test {

    @Test
    public void testIssue1() {
        String expected =
                "{\n" +
                        "  \"/events/ote_oneandone.*\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"FINISHED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        String actual =
                "{\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:25.283Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"STARTED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"id\": \"dc3fac05-f3f1-42ca-93b8-30738b49f08a\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"X_ProcessCorrelationID\": \"1545209424133\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\",\n" +
                        "      \"X_ExtRequestID\": \"1545209424133\",\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"X_RequestID\": \"bcb099d5-cfb7-47c8-8f98-66050fcebd0f\",\n" +
                        "      \"X_ClientID\": \"TEST\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52\"\n" +
                        "  },\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:38.989Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"FINISHED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"id\": \"dc3fac05-f3f1-42ca-93b8-30738b49f08a\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"X_ProcessCorrelationID\": \"1545209424133\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\",\n" +
                        "      \"X_ExtRequestID\": \"1545209424133\",\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"X_RequestID\": \"bcb099d5-cfb7-47c8-8f98-66050fcebd0f\",\n" +
                        "      \"X_ClientID\": \"TEST\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79\"\n" +
                        "  },\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"EMAIL_VERIFICATION\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:40.166Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"STARTED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\",\n" +
                        "        \"data\": {\n" +
                        "          \"registrarPageUrl\": \"https://registrar.tenant.org/main/7694bfb4a00a9eea5e7098e209d991b2cdd8f08beb6a5cc5c3d115351da3fbc8-ffa1931216e1ccd24a3d99e2d2c2cd1d63b632e6ebfbb588da4f9eff9b3a0f64-b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec\",\n" +
                        "          \"hash\": \"b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec\",\n" +
                        "          \"sendEmail\": true,\n" +
                        "          \"processEndDate\": \"2019-01-03T08:50:39.732Z\",\n" +
                        "          \"contacts\": {\n" +
                        "            \"regc\": {\n" +
                        "              \"id\": \"INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264\",\n" +
                        "              \"contactItemId\": \"INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264\",\n" +
                        "              \"postalInfo\": {\n" +
                        "                \"name\": \"First Last\",\n" +
                        "                \"firstName\": \"First\",\n" +
                        "                \"lastName\": \"Last\",\n" +
                        "                \"salutation\": \"f\",\n" +
                        "                \"address\": {\n" +
                        "                  \"streets\": [\n" +
                        "                    \"Herschel Street\"\n" +
                        "                  ],\n" +
                        "                  \"countryCode\": \"GB\",\n" +
                        "                  \"postalCode\": \"SL1 1XS\",\n" +
                        "                  \"city\": \"SLOUGH\"\n" +
                        "                }\n" +
                        "              },\n" +
                        "              \"voice\": \"+49.7211234567\",\n" +
                        "              \"email\": \"domain1545209424133@1und1.de\",\n" +
                        "              \"createDate\": \"2018-12-19T08:50:30.312Z\",\n" +
                        "              \"authInfo\": \"bwj!9$iw\"\n" +
                        "            }\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"type\": \"EMAIL_VERIFICATION\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18\"\n" +
                        "  }\n" +
                        "}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testIssue1_negative() {
        String expected =
                "{\n" +
                        "  \"/events/ote_oneandone.*\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"FINISHED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        String actual =
                "{\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:25.283Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"STARTED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"id\": \"dc3fac05-f3f1-42ca-93b8-30738b49f08a\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"X_ProcessCorrelationID\": \"1545209424133\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\",\n" +
                        "      \"X_ExtRequestID\": \"1545209424133\",\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"X_RequestID\": \"bcb099d5-cfb7-47c8-8f98-66050fcebd0f\",\n" +
                        "      \"X_ClientID\": \"TEST\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253161-1545209425288_567eaab7a5f09777da08fd9a72d849540e1e593841bf59ccddea0ae6d081ad52\"\n" +
                        "  },\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:38.989Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"FINISHED_NOT\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"id\": \"dc3fac05-f3f1-42ca-93b8-30738b49f08a\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"X_ProcessCorrelationID\": \"1545209424133\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\",\n" +
                        "      \"X_ExtRequestID\": \"1545209424133\",\n" +
                        "      \"type\": \"CREATE\",\n" +
                        "      \"X_RequestID\": \"bcb099d5-cfb7-47c8-8f98-66050fcebd0f\",\n" +
                        "      \"X_ClientID\": \"TEST\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253162-1545209438992_2d94910ead721b9ec36b9033937fd08287329d77bb5e7e8cc20ce5282edf2d79\"\n" +
                        "  },\n" +
                        "  \"/events/ote_oneandone/tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18\": {\n" +
                        "    \"payload\": {\n" +
                        "      \"type\": \"EMAIL_VERIFICATION\",\n" +
                        "      \"eventDate\": \"2018-12-19T08:50:40.166Z\",\n" +
                        "      \"context\": {\n" +
                        "        \"status\": \"STARTED\",\n" +
                        "        \"resourceType\": \"DOMAIN\",\n" +
                        "        \"name\": \"test-create-1545209424099.com\",\n" +
                        "        \"data\": {\n" +
                        "          \"registrarPageUrl\": \"https://registrar.tenant.org/main/7694bfb4a00a9eea5e7098e209d991b2cdd8f08beb6a5cc5c3d115351da3fbc8-ffa1931216e1ccd24a3d99e2d2c2cd1d63b632e6ebfbb588da4f9eff9b3a0f64-b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec\",\n" +
                        "          \"hash\": \"b2e67cc69e006d66738b5481e356a2b7d81f2c8625b76af11f7bcc533c2a32ec\",\n" +
                        "          \"sendEmail\": true,\n" +
                        "          \"processEndDate\": \"2019-01-03T08:50:39.732Z\",\n" +
                        "          \"contacts\": {\n" +
                        "            \"regc\": {\n" +
                        "              \"id\": \"INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264\",\n" +
                        "              \"contactItemId\": \"INT-275ac8a9-2dda-4182-b739-8dc8b2ad4264\",\n" +
                        "              \"postalInfo\": {\n" +
                        "                \"name\": \"First Last\",\n" +
                        "                \"firstName\": \"First\",\n" +
                        "                \"lastName\": \"Last\",\n" +
                        "                \"salutation\": \"f\",\n" +
                        "                \"address\": {\n" +
                        "                  \"streets\": [\n" +
                        "                    \"Herschel Street\"\n" +
                        "                  ],\n" +
                        "                  \"countryCode\": \"GB\",\n" +
                        "                  \"postalCode\": \"SL1 1XS\",\n" +
                        "                  \"city\": \"SLOUGH\"\n" +
                        "                }\n" +
                        "              },\n" +
                        "              \"voice\": \"+49.7211234567\",\n" +
                        "              \"email\": \"domain1545209424133@1und1.de\",\n" +
                        "              \"createDate\": \"2018-12-19T08:50:30.312Z\",\n" +
                        "              \"authInfo\": \"bwj!9$iw\"\n" +
                        "            }\n" +
                        "          }\n" +
                        "        }\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"properties\": {\n" +
                        "      \"type\": \"EMAIL_VERIFICATION\",\n" +
                        "      \"X_TenantID\": \"ote_oneandone\"\n" +
                        "    },\n" +
                        "    \"id\": \"tenant.common.archive.domains-13-1253163-1545209440169_da66beaf2766c82d179e3b55f7ae94d6a1c5a1e37b3913bb9fbc08b316443a18\"\n" +
                        "  }\n" +
                        "}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }
}
