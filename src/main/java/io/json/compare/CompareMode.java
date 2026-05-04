package io.json.compare;

/**
 * Flags that tighten the default (intentionally lenient) comparison behavior.
 * Multiple modes may be combined.
 */
public enum CompareMode {

    /** Actual JSON objects must not have any fields beyond those in expected. */
    JSON_OBJECT_NON_EXTENSIBLE,

    /** Actual JSON arrays must not have any elements beyond those in expected. */
    JSON_ARRAY_NON_EXTENSIBLE,

    /** Array elements must match position-for-position (expected[i] ↔ actual[i]). */
    JSON_ARRAY_STRICT_ORDER,

    /**
     * Disable the default regex interpretation — expected scalar values and
     * field names are compared by literal equality instead.
     */
    REGEX_DISABLED
}
