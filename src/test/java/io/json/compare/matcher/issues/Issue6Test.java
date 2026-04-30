package io.json.compare.matcher.issues;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Issue6Test {

    @Test
    public void testIssue1() {
        String expected = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "name": "!division1",
                    "vlan": "!115"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "name": "division2",
                    "vlan": "117"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }


    @Test
    public void testIssue1a() {
        String expected = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "!name": "division1",
                    "!vlan": "115"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "a": "division2",
                    "b": "114"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void testIssue1a_negative() {
        String expected = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "!name": "division1",
                    "!vlan": "115"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "a": "division2",
                    "vlan": "114"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void testIssue1_negative() {
        String expected = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "!name": "division1",
                    "!vlan": "115"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "name": "division1",
                    "vlan": "116"
                  },
                  {
                    "name": "division1",
                    "vlan": "115"
                  }
                ]
                """;
        assertThrows(AssertionError.class, () -> JSONCompare.compare(expected, actual).assertMatches());
    }


    @Test
    public void testIssue2() {
        String expected = """
                [
                  {
                    "!name": "division1",
                    "!vlan": "115"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "names": "division1",
                    "vlan": "116"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }
}
