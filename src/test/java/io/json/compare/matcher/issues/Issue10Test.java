package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class Issue10Test {

    @Test
    public void testMultipleNegationInJsonArray() {
        String expected = "{\"statuses\":[ \"!a.*\",\"!b.*\" ]}";
        String actual = "{\"statuses\":[\"abc\",\"cde\"]}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void testFieldNameDoesntExist() {
        String expected = "{\"(?!autoRenew).*\":true}";
        String actual = "{\"autoRene\": true}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testFieldNameDoesntExist_negative() {
        String expected = "{\"(?!autoRenew).*\":true}";
        String actual = "{\"autoRenew\": true}";
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonArraysInsideJsonObject() {
        String expected = "{\n" +
                "  \"name\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "  \"type\" : \"NATIVE\",\n" +
                "  \"id\" : \"8e529591-1cd5-11ea-a124-0a586444b21a\",\n" +
                "  \"records\" : [ {\n" +
                "    \"!recordId\" : \".*\",\n" +
                "    \"id\" : \".{36}\",\n" +
                "    \"name\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "    \"type\" : \"MX\",\n" +
                "    \"content\" : \".*\",\n" +
                "    \"disabled\" : false\n" +
                "  }, {\n" +
                "    \"!recordId\" : \".*\",\n" +
                "    \"id\" : \".{36}\",\n" +
                "    \"name\" : \"mail.e2e-customer-api-dns-test2.de\",\n" +
                "    \"type\" : \"MX\",\n" +
                "    \"content\" : \".*\",\n" +
                "    \"disabled\" : false\n" +
                "  }, \"!.*\" ]\n" +
                "}";
        String actual = "{\n" +
                "  \"name\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "  \"id\" : \"8e529591-1cd5-11ea-a124-0a586444b21a\",\n" +
                "  \"type\" : \"NATIVE\",\n" +
                "  \"records\" : [ {\n" +
                "    \"name\" : \"mail.e2e-customer-api-dns-test2.de\",\n" +
                "    \"rootName\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "    \"type\" : \"MX\",\n" +
                "    \"content\" : \"mx.sub.updated.server.lan\",\n" +
                "    \"changeDate\" : \"2019-12-19T13:54:25.000Z\",\n" +
                "    \"ttl\" : 60,\n" +
                "    \"prio\" : 11,\n" +
                "    \"disabled\" : false,\n" +
                "    \"id\" : \"9954cc0f-0ac8-b589-37a5-987da68734d0\"\n" +
                "  }, {\n" +
                "    \"name\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "    \"rootName\" : \"e2e-customer-api-dns-test2.de\",\n" +
                "    \"type\" : \"MX\",\n" +
                "    \"content\" : \"mx.patched.server.lan\",\n" +
                "    \"changeDate\" : \"2019-12-19T13:54:27.000Z\",\n" +
                "    \"ttl\" : 3000,\n" +
                "    \"prio\" : 100,\n" +
                "    \"disabled\" : false,\n" +
                "    \"id\" : \"74e7cacd-7062-7f0d-36a6-7f0b6d3a733c\"\n" +
                "  } ]\n" +
                "}";
        JSONCompare.assertMatches(expected, actual);
    }
}
