package org.mifosplatform.infrastructure.codes.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

/**
 * A {@link RuntimeException} thrown when a code is not found.
 */
public class CodeNotFoundException extends AbstractPlatformResourceNotFoundException {

    public CodeNotFoundException(final String name) {
        super("error.msg.code.not.found", "Code with name `" + name + "` does not exist", name);
    }

    public CodeNotFoundException(final Long codeId) {
        super("error.msg.code.identifier.not.found", "Code with identifier `" + codeId + "` does not exist", codeId);
    }
}