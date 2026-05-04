package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue5Test {

    @Test
    public void testIssue1() {
        String expected = """
                {
                  "records": [
                    {
                      "name": "autodiscover.test.fr",
                      "type": "CNAME",
                      "content": ".*",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    {
                      "!name": ".*",
                      "!type": ".*",
                      "!content": ".*",
                      "ttl": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": ".*",
                      "ttl": ".*",
                      "prio": ".*",
                      "!.*": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": ".*",
                      "ttl": ".*",
                      "prio": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "NS",
                      "content": "^ns(101[6-9]|10[2-9][0-9]|11[0-1][0-9]|112[0-6]).ui-dns.(biz|com|org|de)$",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "TXT",
                      "content": "\\"zone-ownership-verification-[A-Fa-f0-9]{64}\\"",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    "!.*"
                  ]
                }
                """;
        String actual = """
                {
                  "records": [
                    {
                      "name": "autodiscover.test.fr",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "ttl": 3600
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": "mx00.kundenserver.de",
                      "ttl": 3600,
                      "prio": 10,
                      "data": 10
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": "mx01.kundenserver.de",
                      "ttl": 3600,
                      "prio": 10
                    },
                    {
                      "lol": "_domainconnect.#[domainName]",
                      "abc": "CNAME",
                      "ttl": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "NS",
                      "content": "ns1094.ui-dns.com",
                      "ttl": 86400
                    },
                    {
                      "name": "test.fr",
                      "type": "TXT",
                      "content": "\\"zone-ownership-verification-1f3084631d3f396d4df4b07df37c94d7cb8b569cfa03a899f343381021fcf112\\"",
                      "ttl": 60
                    }
                  ]
                }
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void testIssue1_negative() {
        String expected = """
                {
                  "records": [
                    {
                      "name": "autodiscover.test.fr",
                      "type": "CNAME",
                      "content": ".*",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    {
                      "!name": ".*",
                      "!type": ".*",
                      "!content": ".*",
                      "!ttl": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": ".*",
                      "ttl": ".*",
                      "prio": ".*",
                      "!.*": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": ".*",
                      "ttl": ".*",
                      "prio": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "NS",
                      "content": "^ns(101[6-9]|10[2-9][0-9]|11[0-1][0-9]|112[0-6]).ui-dns.(biz|com|org|de)$",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "TXT",
                      "content": "\\"zone-ownership-verification-[A-Fa-f0-9]{64}\\"",
                      "ttl": ".*",
                      "!.*": ".*"
                    },
                    "!.*"
                  ]
                }
                """;
        String actual = """
                {
                  "records": [
                    {
                      "name": "autodiscover.test.fr",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "ttl": 3600
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": "mx00.kundenserver.de",
                      "ttl": 3600,
                      "prio": 10,
                      "data": 10
                    },
                    {
                      "name": "test.fr",
                      "type": "MX",
                      "content": "mx01.kundenserver.de",
                      "ttl": 3600,
                      "prio": 10
                    },
                    {
                      "lol": "_domainconnect.#[domainName]",
                      "abc": "CNAME",
                      "ttl": ".*"
                    },
                    {
                      "name": "test.fr",
                      "type": "NS",
                      "content": "ns1094.ui-dns.com",
                      "ttl": 86400
                    },
                    {
                      "name": "test.fr",
                      "type": "TXT",
                      "content": "\\"zone-ownership-verification-1f3084631d3f396d4df4b07df37c94d7cb8b569cfa03a899f343381021fcf112\\"",
                      "ttl": 60
                    }
                  ]
                }
                """;
        assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
    }
}
