package org.mifosplatform.portfolio.loanaccount.exception;

import org.mifosplatform.infrastructure.core.exception.AbstractPlatformDomainRuleException;

/**
 * {@link AbstractPlatformDomainRuleException} thrown an action to transition a loan from one state to another violates a domain rule.
 */
public class InvalidLoanStateTransitionException extends AbstractPlatformDomainRuleException {

	public InvalidLoanStateTransitionException(final String action, String postFix, String defaultUserMessage, Object... defaultUserMessageArgs) {
		super("error.msg.loan." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
	}
}