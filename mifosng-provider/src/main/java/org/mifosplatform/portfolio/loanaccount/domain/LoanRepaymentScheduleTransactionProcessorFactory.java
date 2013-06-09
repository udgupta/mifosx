/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.loanaccount.domain;

import org.mifosplatform.portfolio.loanproduct.domain.LoanTransactionProcessingStrategy;
import org.springframework.stereotype.Component;

@Component
public class LoanRepaymentScheduleTransactionProcessorFactory {

    public LoanRepaymentScheduleTransactionProcessor determineProcessor(
            final LoanTransactionProcessingStrategy transactionProcessingStrategy) {

        LoanRepaymentScheduleTransactionProcessor processor = new MifosStyleLoanRepaymentScheduleTransactionProcessor();

        if (transactionProcessingStrategy != null) {

            if (transactionProcessingStrategy.isStandardMifosStrategy()) {
                processor = new MifosStyleLoanRepaymentScheduleTransactionProcessor();
            }

            if (transactionProcessingStrategy.isHeavensfamilyStrategy()) {
                processor = new HeavensFamilyLoanRepaymentScheduleTransactionProcessor();
            }

            if (transactionProcessingStrategy.isCreocoreStrategy()) {
                processor = new CreocoreLoanRepaymentScheduleTransactionProcessor();
            }

            if (transactionProcessingStrategy.isIndianRBIStrategy()) {
                processor = new RBILoanRepaymentScheduleTransactionProcessor();
            }
        }

        return processor;
    }
}