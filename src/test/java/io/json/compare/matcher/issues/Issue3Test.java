package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue3Test {

    @Test
    public void testIssue1() {
        String expected = """
                {
                  "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                  "type": "NATIVE",
                  "records": [
                    {
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "A",
                      "content": "1.2.3.4",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "AAAA",
                      "content": "2001:db8:0:0:0:0:0:1",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "cname.test6.net",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "MX",
                      "content": "mx01.1and1.com",
                      "ttl": 3600,
                      "prio": 0,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.com",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.de",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.org",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.biz",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "SOA",
                      "content": "ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600",
                      "ttl": 86400,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "TXT",
                      "content": "\\"text-record\\"",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    "!.*"
                  ]
                }
                """;
        String actual = """
                {
                  "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                  "type": "NATIVE",
                  "records": [
                    {
                      "recordId": 1024005736,
                      "name": "www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005738,
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "A",
                      "content": "1.2.3.4",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005739,
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "AAAA",
                      "content": "2001:db8:0:0:0:0:0:1",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005740,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "MX",
                      "content": "mx01.1and1.com",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600,
                      "prio": 0
                    },
                    {
                      "recordId": 1024005741,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.com",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005742,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.de",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005743,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.org",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005744,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.biz",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005745,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "SOA",
                      "content": "ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 86400
                    },
                    {
                      "recordId": 1024005746,
                      "name": "autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "cname.test6.net",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005747,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "TXT",
                      "content": "\\"text-record\\"",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
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
                  "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                  "type": "NATIVE",
                  "records": [
                    {
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "A",
                      "content": "1.2.3.4",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "AAAA",
                      "content": "2001:db8:0:0:0:0:0:1",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "cname.test6.net",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "MX",
                      "content": "mx01.1and1.com",
                      "ttl": 3600,
                      "prio": 0,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "content": "!mx00.1and1.com"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.com",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.de",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.org",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.biz",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "SOA",
                      "content": "ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600",
                      "ttl": 86400,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "TXT",
                      "content": "\\"text-record\\"",
                      "ttl": 3600,
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    },
                    {
                      "name": "!sip.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health"
                    }
                  ]
                }
                """;
        String actual = """
                {
                  "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                  "type": "NATIVE",
                  "records": [
                    {
                      "recordId": 1024005736,
                      "name": "www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "adsredir.1and1.info",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005738,
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "A",
                      "content": "1.2.3.4",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005739,
                      "name": "www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "AAAA",
                      "content": "2001:db8:0:0:0:0:0:1",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005740,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "MX",
                      "content": "mx01.1and1.com",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600,
                      "prio": 0
                    },
                    {
                      "recordId": 1024005741,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.com",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005742,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.de",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005743,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.org",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005744,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "NS",
                      "content": "ns1240.ui-dns.biz",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005745,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "SOA",
                      "content": "ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 86400
                    },
                    {
                      "recordId": 1024005746,
                      "name": "autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "CNAME",
                      "content": "cname.test6.net",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    },
                    {
                      "recordId": 1024005740,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "MX",
                      "content": "mx00.1and1.com",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600,
                      "prio": 0
                    },
                    {
                      "recordId": 1024005747,
                      "name": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "rootName": "test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health",
                      "type": "TXT",
                      "content": "\\"text-record\\"",
                      "changeDate": "2018-12-18T20:02:08.000Z",
                      "ttl": 3600
                    }
                  ]
                }
                """;
        assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
    }
}
