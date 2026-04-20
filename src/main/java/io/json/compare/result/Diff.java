package io.json.compare.result;

/**
 * A single, immutable difference produced by comparing two JSONs.
 *
 * <p>Paths follow the same JSONPath-like syntax the library has always emitted
 * — e.g. {@code $.users[0].name}, {@code $.#($.store..isbn)[1]}, or simply
 * {@code $} for a root-level difference.
 *
 * @param path        JSONPath locating the difference inside the actual JSON.
 * @param kind        Category hint; see {@link DiffKind}.
 * @param description Human-readable explanation (the portion after the path).
 * @param raw         The original, unparsed diff string — identical to what
 *                    {@code io.json.compare.JSONCompare#diffs(Object, Object)}
 *                    returns.
 */
public record Diff(String path, DiffKind kind, String description, String raw) {

    public static Diff parse(String rawDiff) {
        String path = extractPath(rawDiff);
        String description = rawDiff.length() > path.length() ? rawDiff.substring(path.length()) : "";
        return new Diff(path, inferKind(path, description), description.strip(), rawDiff);
    }

    private static String extractPath(String diff) {
        int depth = 0;
        for (int i = 0; i < diff.length(); i++) {
            char c = diff.charAt(i);
            if (depth > 0) {
                if (c == '(' || c == '[') depth++;
                else if (c == ')' || c == ']') depth--;
                continue;
            }
            if (c == '(' || c == '[') {
                depth++;
                continue;
            }
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                return diff.substring(0, i);
            }
        }
        return diff;
    }

    private static DiffKind inferKind(String path, String description) {
        if (description.contains("Different JSON types")) return DiffKind.TYPE_MISMATCH;
        if (description.contains("Json path")) return DiffKind.JSON_PATH_ERROR;
        if (description.contains("condition was not met") || description.contains("no extra elements")) {
            return DiffKind.CONDITION_NOT_MET;
        }
        if (description.contains("Actual JSON OBJECT has extra fields")) return DiffKind.EXTRA_FIELD;
        if (description.contains("Actual JSON ARRAY has extra elements")) return DiffKind.EXTRA_ARRAY_ELEMENT;
        if (description.contains("was not found")) {
            return path.endsWith("]") ? DiffKind.MISSING_ARRAY_ELEMENT : DiffKind.MISSING_FIELD;
        }
        if (description.contains("was found")) return DiffKind.UNEXPECTED_MATCH;
        if (description.contains("Expected ")) return DiffKind.VALUE_MISMATCH;
        return DiffKind.OTHER;
    }
}
