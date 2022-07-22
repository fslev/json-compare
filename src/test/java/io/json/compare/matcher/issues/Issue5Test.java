package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue5Test {

    @Test
    public void testIssue1() {
        String expected =
                "{\n" +
                        "    \"records\": [\n" +
                        "      {\n" +
                        "        \"name\": \"autodiscover.test.fr\",\n" +
                        "        \"type\": \"CNAME\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"!name\": \".*\",\n" +
                        "        \"!type\": \".*\",\n" +
                        "        \"!content\": \".*\",\n" +
                        "        \"ttl\": \".*\"" +
                        "      }," +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"MX\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"prio\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"MX\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"prio\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"NS\",\n" +
                        "        \"content\": \"^ns(101[6-9]|10[2-9][0-9]|11[0-1][0-9]|112[0-6]).ui-dns.(biz|com|org|de)$\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"TXT\",\n" +
                        "        \"content\": \"\\\"zone-ownership-verification-[A-Fa-f0-9]{64}\\\"\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      \"!.*\"" +
                        "    ]\n" +
                        "  }";
        String actual =
                "{\n" +
                        "  \"records\" : [ {\n" +
                        "    \"name\" : \"autodiscover.test.fr\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  },{\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx00.kundenserver.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 10,\n" +
                        "    \"data\" : 10\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.kundenserver.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 10\n" +
                        "  }," +
                        "  {\n" +
                        "        \"lol\": \"_domainconnect.#[domainName]\",\n" +
                        "        \"abc\": \"CNAME\",\n" +
                        "        \"ttl\": \".*\"\n" +
                        "    }, {\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1094.ui-dns.com\",\n" +
                        "    \"ttl\" : 86400\n" +
                        "  },{\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"zone-ownership-verification-1f3084631d3f396d4df4b07df37c94d7cb8b569cfa03a899f343381021fcf112\\\"\",\n" +
                        "    \"ttl\" : 60\n" +
                        "  }]}";
        JSONCompare.assertMatches(expected, actual);
    }

    @Test
    public void testIssue1_negative() {
        String expected =
                "{\n" +
                        "    \"records\": [\n" +
                        "      {\n" +
                        "        \"name\": \"autodiscover.test.fr\",\n" +
                        "        \"type\": \"CNAME\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"!name\": \".*\",\n" +
                        "        \"!type\": \".*\",\n" +
                        "        \"!content\": \".*\",\n" +
                        "        \"!ttl\": \".*\"" +
                        "      }," +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"MX\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"prio\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"MX\",\n" +
                        "        \"content\": \".*\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"prio\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"NS\",\n" +
                        "        \"content\": \"^ns(101[6-9]|10[2-9][0-9]|11[0-1][0-9]|112[0-6]).ui-dns.(biz|com|org|de)$\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"test.fr\",\n" +
                        "        \"type\": \"TXT\",\n" +
                        "        \"content\": \"\\\"zone-ownership-verification-[A-Fa-f0-9]{64}\\\"\",\n" +
                        "        \"ttl\": \".*\",\n" +
                        "        \"!.*\": \".*\"\n" +
                        "      },\n" +
                        "      \"!.*\"" +
                        "    ]\n" +
                        "  }";
        String actual =
                "{\n" +
                        "  \"records\" : [ {\n" +
                        "    \"name\" : \"autodiscover.test.fr\",\n" +
                        "    \"type\" : \"CNAME\",\n" +
                        "    \"content\" : \"adsredir.1and1.info\",\n" +
                        "    \"ttl\" : 3600\n" +
                        "  },{\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx00.kundenserver.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 10,\n" +
                        "    \"data\" : 10\n" +
                        "  }, {\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"MX\",\n" +
                        "    \"content\" : \"mx01.kundenserver.de\",\n" +
                        "    \"ttl\" : 3600,\n" +
                        "    \"prio\" : 10\n" +
                        "  }," +
                        "  {\n" +
                        "        \"lol\": \"_domainconnect.#[domainName]\",\n" +
                        "        \"abc\": \"CNAME\",\n" +
                        "        \"ttl\": \".*\"\n" +
                        "    }, {\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"NS\",\n" +
                        "    \"content\" : \"ns1094.ui-dns.com\",\n" +
                        "    \"ttl\" : 86400\n" +
                        "  },{\n" +
                        "    \"name\" : \"test.fr\",\n" +
                        "    \"type\" : \"TXT\",\n" +
                        "    \"content\" : \"\\\"zone-ownership-verification-1f3084631d3f396d4df4b07df37c94d7cb8b569cfa03a899f343381021fcf112\\\"\",\n" +
                        "    \"ttl\" : 60\n" +
                        "  }]}";
        assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
    }
}
