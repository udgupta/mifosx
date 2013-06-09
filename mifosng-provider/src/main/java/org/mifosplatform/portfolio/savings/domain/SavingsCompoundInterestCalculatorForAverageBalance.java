/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.savings.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * Calculates interest on a savings account using the <b>Average Balance</b>
 * method.
 */
public class SavingsCompoundInterestCalculatorForAverageBalance implements SavingsCompoundInterestCalculator {

    private final BigDecimal periodsInOneYearAsFraction;
    private final BigDecimal annualInterestRateAsFraction;

    public SavingsCompoundInterestCalculatorForAverageBalance(final BigDecimal periodsInOneYearAsFraction,
            final BigDecimal annualInterestRateAsFraction) {
        this.periodsInOneYearAsFraction = periodsInOneYearAsFraction;
        this.annualInterestRateAsFraction = annualInterestRateAsFraction;
    }

    @Override
    public BigDecimal calculate(final List<SavingsAccountDailyBalance> dailyBalances, final Integer numberOfDays) {

        final MathContext mc = new MathContext(10, RoundingMode.HALF_EVEN);
        final BigDecimal dailyInterestRate = annualInterestRateAsFraction.multiply(periodsInOneYearAsFraction, mc);
        final BigDecimal periodicInterestRate = dailyInterestRate.multiply(BigDecimal.valueOf(numberOfDays.longValue()));

        final BigDecimal numberOfDaysBigDecimal = BigDecimal.valueOf(numberOfDays.longValue());

        BigDecimal totalCumulativeBalanceForPeriod = BigDecimal.ZERO;
        for (SavingsAccountDailyBalance balance : dailyBalances) {
            if (balance.isGreaterThanZero()) {
                final BigDecimal cumulativeBalance = balance.cumulativeBalance();
                totalCumulativeBalanceForPeriod = totalCumulativeBalanceForPeriod.add(cumulativeBalance);
            }
        }

        final BigDecimal averageDailyBalance = totalCumulativeBalanceForPeriod.divide(numberOfDaysBigDecimal, mc);

        return averageDailyBalance.multiply(periodicInterestRate, mc);
    }
}