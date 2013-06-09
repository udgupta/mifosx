/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.savings.domain;

import java.math.BigDecimal;
import java.util.List;

public interface SavingsCompoundInterestCalculator {

    BigDecimal calculate(List<SavingsAccountDailyBalance> dailyBalances, Integer numberOfDays);

}