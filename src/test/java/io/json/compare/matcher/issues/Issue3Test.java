package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue3Test {

    @Test
    public void testIssue1() {
        String expected =
                "{\n" +
                        "  \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "  \"type\" : \"NATIVE\",\n" +
                        "  \"records\" : [ {\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"A\",\n" +
                        "    \"content\" : \"1.2.3.4\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"AAAA\",\n" +
                        "    \"content\" : \"2001:db8:0:0:0:0:0:1\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"cname.test6.net\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.1and1.com\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 0,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.org\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.biz\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"SOA\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600\",\n" +
                        "    \"ttl\" : 86400,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"text-record\\\"\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, \"!.*\" ]\n" +
                        "}";
        String actual =
                "{\n" +
                        "  \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "  \"type\" : \"NATIVE\",\n" +
                        "  \"records\" : [ {\n" +
                        "    \"recordId\" : 1024005736,\n" +
                        "    \"name\" : \"www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005738,\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"A\",\n" +
                        "    \"content\" : \"1.2.3.4\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005739,\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"AAAA\",\n" +
                        "    \"content\" : \"2001:db8:0:0:0:0:0:1\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005740,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.1and1.com\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 0\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005741,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005742,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.de\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005743,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.org\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005744,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.biz\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005745,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"SOA\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 86400\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005746,\n" +
                        "    \"name\" : \"autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"cname.test6.net\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005747,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"text-record\\\"\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  } ]\n" +
                        "}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testIssue1_negative() {
        String expected =
                "{\n" +
                        "  \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "  \"type\" : \"NATIVE\",\n" +
                        "  \"records\" : [ {\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"A\",\n" +
                        "    \"content\" : \"1.2.3.4\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"AAAA\",\n" +
                        "    \"content\" : \"2001:db8:0:0:0:0:0:1\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"cname.test6.net\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.1and1.com\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 0,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"content\" : \"!mx00.1and1.com\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.org\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.biz\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"SOA\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600\",\n" +
                        "    \"ttl\" : 86400,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"text-record\\\"\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  }, {\n" +
                        "    \"name\" : \"!sip.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\"\n" +
                        "  } ]\n" +
                        "}";
        String actual =
                "{\n" +
                        "  \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "  \"type\" : \"NATIVE\",\n" +
                        "  \"records\" : [ {\n" +
                        "    \"recordId\" : 1024005736,\n" +
                        "    \"name\" : \"www2.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005738,\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"A\",\n" +
                        "    \"content\" : \"1.2.3.4\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005739,\n" +
                        "    \"name\" : \"www.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"AAAA\",\n" +
                        "    \"content\" : \"2001:db8:0:0:0:0:0:1\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005740,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.1and1.com\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 0\n" +
                        "  },{\n" +
                        "    \"recordId\" : 1024005741,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005742,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.de\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005743,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.org\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005744,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.biz\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005745,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"SOA\",\n" +
                        "    \"content\" : \"ns1240.ui-dns.com hostmaster.1and1.com 2017080900 28800 7200 604800 600\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 86400\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005746,\n" +
                        "    \"name\" : \"autodiscover.test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"cname.test6.net\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  },{\n" +
                        "    \"recordId\" : 1024005740,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx00.1and1.com\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 0\n" +
                        "  }, {\n" +
                        "    \"recordId\" : 1024005747,\n" +
                        "    \"name\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"rootName\" : \"test-apply-template-0d108962-d854-4ae0-a84e-d687dea1b69a.health\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"text-record\\\"\",\n" +
                        "    \"changeDate\" : \"2018-12-18T20:02:08.000Z\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  } ]\n" +
                        "}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }
}
