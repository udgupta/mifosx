package org.mifosplatform.infrastructure.codes.data;

/**
 * Immutable data object representing a code.
 */
public class CodeData {

    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final boolean systemDefined;

    public CodeData(final Long id, final String name, final boolean systemDefined) {
        this.id = id;
        this.name = name;
        this.systemDefined = systemDefined;
    }
}