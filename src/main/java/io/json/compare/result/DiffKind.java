package io.json.compare.result;

/**
 * Categorizes a {@link Diff} for programmatic filtering. The kind is inferred
 * from the diff message at the public API boundary; it is a best-effort hint,
 * not authoritative — rely on {@link Diff#description()} or
 * {@link Diff#raw()} for the full human-readable detail.
 */
public enum DiffKind {

    /** A scalar value (or its type) did not match. */
    VALUE_MISMATCH,

    /** An expected field is absent from the actual JSON object. */
    MISSING_FIELD,

    /** An actual JSON object has fields the expected side forbids. */
    EXTRA_FIELD,

    /** An expected array element is absent from the actual JSON array. */
    MISSING_ARRAY_ELEMENT,

    /** An actual JSON array has elements the expected side forbids. */
    EXTRA_ARRAY_ELEMENT,

    /** The expected and actual sides are of different JSON types. */
    TYPE_MISMATCH,

    /** A DSL condition ({@code .*}, {@code !.*}) was not satisfied. */
    CONDITION_NOT_MET,

    /** A {@code DO_NOT_MATCH} ({@code !…}) was violated — i.e. something matched that should not have. */
    UNEXPECTED_MATCH,

    /** An embedded JSON path expression failed (path missing or result mismatched). */
    JSON_PATH_ERROR,

    /** Catch-all for diffs whose kind could not be classified. */
    OTHER
}
