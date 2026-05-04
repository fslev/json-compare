package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

public class Issue10Test {

    @Test
    public void testMultipleNegationInJsonArray() {
        String expected = """
                {
                  "statuses": [
                    "!a.*",
                    "!b.*"
                  ]
                }
                """;
        String actual = """
                {
                  "statuses": [
                    "abc",
                    "cde"
                  ]
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void testFieldNameDoesntExist() {
        String expected = """
                {
                  "(?!autoRenew).*": true
                }
                """;
        String actual = """
                {
                  "autoRene": true
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void testFieldNameDoesntExist_negative() {
        String expected = """
                {
                  "(?!autoRenew).*": true
                }
                """;
        String actual = """
                {
                  "autoRenew": true
                }
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void compareJsonArraysInsideJsonObject() {
        String expected = """
                {
                  "name": "e2e-customer-api-dns-test2.de",
                  "type": "NATIVE",
                  "id": "8e529591-1cd5-11ea-a124-0a586444b21a",
                  "records": [
                    {
                      "!recordId": ".*",
                      "id": ".{36}",
                      "name": "e2e-customer-api-dns-test2.de",
                      "type": "MX",
                      "content": ".*",
                      "disabled": false
                    },
                    {
                      "!recordId": ".*",
                      "id": ".{36}",
                      "name": "mail.e2e-customer-api-dns-test2.de",
                      "type": "MX",
                      "content": ".*",
                      "disabled": false
                    },
                    "!.*"
                  ]
                }
                """;
        String actual = """
                {
                  "name": "e2e-customer-api-dns-test2.de",
                  "id": "8e529591-1cd5-11ea-a124-0a586444b21a",
                  "type": "NATIVE",
                  "records": [
                    {
                      "name": "mail.e2e-customer-api-dns-test2.de",
                      "rootName": "e2e-customer-api-dns-test2.de",
                      "type": "MX",
                      "content": "mx.sub.updated.server.lan",
                      "changeDate": "2019-12-19T13:54:25.000Z",
                      "ttl": 60,
                      "prio": 11,
                      "disabled": false,
                      "id": "9954cc0f-0ac8-b589-37a5-987da68734d0"
                    },
                    {
                      "name": "e2e-customer-api-dns-test2.de",
                      "rootName": "e2e-customer-api-dns-test2.de",
                      "type": "MX",
                      "content": "mx.patched.server.lan",
                      "changeDate": "2019-12-19T13:54:27.000Z",
                      "ttl": 3000,
                      "prio": 100,
                      "disabled": false,
                      "id": "74e7cacd-7062-7f0d-36a6-7f0b6d3a733c"
                    }
                  ]
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }
}
