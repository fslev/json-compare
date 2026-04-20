package io.json.compare.result;

import java.util.List;
import java.util.stream.Stream;

/**
 * Structured outcome of a JSON comparison. Exposes both the raw diff strings
 * (for users who want the legacy format) and a typed
 * {@link java.util.List List&lt;Diff&gt;} for programmatic filtering.
 *
 * <p>Instances are created by the
 * {@code io.json.compare.JSONCompare#compare(Object, Object)} builder.
 *
 * @param diffs Structured diffs. Empty if and only if the comparison matched.
 */
public record ComparisonResult(List<Diff> diffs) {

    public ComparisonResult {
        diffs = List.copyOf(diffs);
    }

    public static ComparisonResult of(List<String> rawDiffs) {
        return new ComparisonResult(rawDiffs.stream().map(Diff::parse).toList());
    }

    /** {@code true} iff there are no differences — i.e. the comparison matched. */
    public boolean matches() {
        return diffs.isEmpty();
    }

    /** Convenience: number of diffs. */
    public int diffCount() {
        return diffs.size();
    }

    /** Stream the diffs that are of the given {@link DiffKind}. */
    public Stream<Diff> diffsOfKind(DiffKind kind) {
        return diffs.stream().filter(d -> d.kind() == kind);
    }

    /** Raw legacy diff strings (each prefixed with {@code $}). */
    public List<String> rawDiffs() {
        return diffs.stream().map(Diff::raw).toList();
    }
}
