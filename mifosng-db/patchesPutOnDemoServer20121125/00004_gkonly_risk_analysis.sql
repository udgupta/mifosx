 
/* table used as an additional table for GK risk analysis 
Also putting in default database as well for demos but not used for other tenants*/

DROP TABLE IF EXISTS `risk_analysis`;
CREATE TABLE `risk_analysis` (
  `client_id` bigint(20) NOT NULL,
  `proposed_loan_amount` decimal(19,6) DEFAULT NULL,
  `assets_cash` decimal(19,6) DEFAULT NULL,
  `assets_bank_accounts` decimal(19,6) DEFAULT NULL,
  `assets_accounts_receivable` decimal(19,6) DEFAULT NULL,
  `assets_inventory` decimal(19,6) DEFAULT NULL,
  `assets_total_fixed_business` decimal(19,6) DEFAULT NULL,
  `assets_total_business` decimal(19,6) DEFAULT NULL,
  `assets_total_household` decimal(19,6) DEFAULT NULL,

  `liabilities_accounts_payable` decimal(19,6) DEFAULT NULL,
  `liabilities_business_debts` decimal(19,6) DEFAULT NULL,
  `liabilities_total_business` decimal(19,6) DEFAULT NULL,
  `liabilities_equity_working_capital` decimal(19,6) DEFAULT NULL,
  `liabilities_total_household` decimal(19,6) DEFAULT NULL,
  `liabilities_household_equity` decimal(19,6) DEFAULT NULL,

  `cashflow_cash_sales` decimal(19,6) DEFAULT NULL,
  `cashflow_cash_sales2` decimal(19,6) DEFAULT NULL,
  `cashflow_cost_goods_sold` decimal(19,6) DEFAULT NULL,
  `cashflow_cost_goods_sold2` decimal(19,6) DEFAULT NULL,
  `cashflow_gross_profit` decimal(19,6) DEFAULT NULL,
  `cashflow_other_income1` decimal(19,6) DEFAULT NULL,
  `cashflow_total_income2` decimal(19,6) DEFAULT NULL,
  `cashflow_household_expense` decimal(19,6) DEFAULT NULL,
  `cashflow_payments_to_savings` decimal(19,6) DEFAULT NULL,
  `cashflow_operational_expenses` decimal(19,6) DEFAULT NULL,
  `cashflow_disposable_income` decimal(19,6) DEFAULT NULL,
  `cashflow_amount_loan_installment` decimal(19,6) DEFAULT NULL,
  `cashflow_available_surplus` decimal(19,6) DEFAULT NULL,

  `fi_inventory_turnover` decimal(19,6) DEFAULT NULL,
  `fi_gross_margin` decimal(19,6) DEFAULT NULL,
  `fi_indebtedness` decimal(19,6) DEFAULT NULL,
  `fi_loan_recommendation` decimal(19,6) DEFAULT NULL,
  `fi_roe` decimal(19,6) DEFAULT NULL,
  `fi_repayment_capacity` decimal(19,6) DEFAULT NULL,


  PRIMARY KEY (`client_id`),
  CONSTRAINT `FK_risk_analysis_1` FOREIGN KEY (`client_id`) REFERENCES `m_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

