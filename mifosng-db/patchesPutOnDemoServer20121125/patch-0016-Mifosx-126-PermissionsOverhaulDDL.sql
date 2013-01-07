ALTER TABLE m_permission
 DROP COLUMN `order_in_grouping` , 
 ADD COLUMN `entity_name` VARCHAR(100) NULL  AFTER `default_name` , 
 ADD COLUMN `action_name` VARCHAR(100) NULL  AFTER `entity_name` ;

/*
this scripts removes all current m_role_permission and m_permission entries
and then inserts new m_permission entries and just one m_role_permission entry
which gives the role (id 1 - super user) an ALL_FUNCTIONS permission

If you had other roles set up with specific permissions you will have to set up their permissions again.
*/

delete from m_role_permission;
delete from m_permission;

/* System Supplied Permissons */
INSERT INTO `m_permission` VALUES (1,'special','USER_ADMINISTRATION_SUPER_USER','An application user will have permission to execute all tasks related to user administration.','User administration ALL',NULL,NULL),(2,'special','ORGANISATION_ADMINISTRATION_SUPER_USER','An application user will have permission to execute all tasks related to organisation administration.','Organisation adminsitration ALL',NULL,NULL),(3,'special','PORTFOLIO_MANAGEMENT_SUPER_USER','An application user will have permission to execute all tasks related to portfolio management.','Portfolio management ALL',NULL,NULL),(4,'special','REPORTING_SUPER_USER','An application user will have permission to execute and view all reports.','Reporting ALL',NULL,NULL),(5,'portfolio','CREATE_LOAN','Allows an application user to sumit new loan application.','Can submit new loan application','LOAN','CREATE'),(6,'portfolio','CREATEHISTORIC_LOAN','Allows an application user to sumit new loan application where the submitted on date is in the past.','Can submit historic loan application','LOAN','CREATEHISTORIC'),(7,'transaction_loan','APPROVE_LOAN','Allows an application user to approve a loan application.','Can approve loan application','LOAN','APPROVE'),(8,'transaction_loan','APPROVEINPAST_LOAN','Allows an application user to approve a loan application where the approval date is in the past.','Can approve loan application in the past','LOAN','APPROVEINPAST'),(9,'transaction_loan','REJECT_LOAN','Allows an application user to reject a loan application.','Can reject loan application','LOAN','REJECT'),(10,'transaction_loan','REJECTINPAST_LOAN','Allows an application user to reject a loan application where the rejected date is in the past.','Can reject loan application in the past','LOAN','REJECTINPAST'),
(11,'transaction_loan','WITHDRAW_LOAN','Allows an application user to mark loan application as withdrawn by client.','Can withdraw loan application','LOAN','WITHDRAW'),(12,'transaction_loan','WITHDRAWINPAST_LOAN','Allows an application user to mark loan application as withdrawn by client where the withdran on date is in the past.','Can withdraw loan application in the past','LOAN','WITHDRAWINPAST'),(13,'portfolio','DELETE_LOAN','Allows an application user to complete delete the loan application if it is submitted but not approved.','Can delete submitted loan application','LOAN','DELETE'),(14,'transaction_loan','APPROVALUNDO_LOAN','Allows an application user to undo a loan approval.','Can undo loan approval','LOAN','APPROVALUNDO'),(15,'transaction_loan','DISBURSE_LOAN','Allows an application user to disburse a loan application.','Can disburse loan','LOAN','DISBURSE'),(16,'transaction_loan','DISBURSEINPAST_LOAN','Allows an application user to disburse a loan where the disbursement date is in the past.','Can disburse loan in the past','LOAN','DISBURSEINPAST'),(17,'transaction_loan','DISBURSALUNDO_LOAN','Allows an application user to undo a loan disbursal if not payments already made.','Can undo loan disbursal','LOAN','DISBURSALUNDO'),(18,'transaction_loan','REPAYMENT_LOAN','Allows an application user to enter a repayment on the loan.','Can enter a repayment against a loan','LOAN','REPAYMENT'),(19,'transaction_loan','REPAYMENTINPAST_LOAN','Allows an application user to enter a repayment on the loan where the repayment date is in the past.','Can enter a repayment against a loan in the past','LOAN','REPAYMENTINPAST'),(20,'portfolio','CREATE_CLIENT','Allows an application user to add a new client.','Can add a new client.','CLIENT','CREATE'),
(42,'special','ALL_FUNCTIONS','An application user will have permission to execute all tasks.','ALL',NULL,NULL),(43,'special','ALL_FUNCTIONS_READ','An application user will have permission to execute all read tasks.','ALL READ',NULL,NULL),(112,'organisation','CREATE_CHARGE','Create a Charge','Create a Charge','CHARGE','CREATE'),(113,'organisation','READ_CHARGE','Read Charges','Read Charges','CHARGE','READ'),(114,'organisation','UPDATE_CHARGE','Update a Charge','Update a Charge','CHARGE','UPDATE'),(115,'organisation','DELETE_CHARGE','Delete a Charge','Delete a Charge','CHARGE','DELETE'),(120,'portfolio','READ_CLIENT','Read Clients','Read Clients','CLIENT','READ'),(121,'portfolio','UPDATE_CLIENT','Update a Client','Update a Client','CLIENT','UPDATE'),(122,'portfolio','DELETE_CLIENT','Delete a Client','Delete a Client','CLIENT','DELETE'),(123,'portfolio','CREATE_CLIENTIMAGE','Create/Update Client Image','Create/Update Client Image','CLIENTIMAGE','CREATE'),(124,'portfolio','READ_CLIENTIMAGE','Read Client Images','Read Client Images','CLIENTIMAGE','READ'),(126,'portfolio','DELETE_CLIENTIMAGE','Delete Client Image','Delete Client Image','CLIENTIMAGE','DELETE'),(127,'portfolio','CREATE_CLIENTNOTE','Create a Client Note','Create a Client Note','CLIENTNOTE','CREATE'),(128,'portfolio','READ_CLIENTNOTE','Read Client Notes','Read Client Notes','CLIENTNOTE','READ'),(129,'portfolio','UPDATE_CLIENTNOTE','Update a Client Note','Update a Client Note','CLIENTNOTE','UPDATE'),(130,'portfolio','DELETE_CLIENTNOTE','Delete a Client Note','Delete a Client Note','CLIENTNOTE','DELETE'),(131,'portfolio','CREATE_CLIENTIDENTIFIER','Create a Client Identifier','Create a Client Identifier','CLIENTIDENTIFIER','CREATE'),
(132,'portfolio','READ_CLIENTIDENTIFIER','Read Client Identifiers','Read Client Identifiers','CLIENTIDENTIFIER','READ'),(133,'portfolio','UPDATE_CLIENTIDENTIFIER','Update a Client Identifier','Update a Client Identifier','CLIENTIDENTIFIER','UPDATE'),(134,'portfolio','DELETE_CLIENTIDENTIFIER','Delete a Client Identifier','Delete a Client Identifier','CLIENTIDENTIFIER','DELETE'),(135,'configuration','CREATE_CODE','Create a Code','Create a Code','CODE','CREATE'),(136,'configuration','READ_CODE','Read Codes','Read Codes','CODE','READ'),(137,'configuration','UPDATE_CODE','Update a Code','Update a Code','CODE','UPDATE'),(138,'configuration','DELETE_CODE','Delete a Code','Delete a Code','CODE','DELETE'),(139,'configuration','READ_CURRENCY','Read Currencies','Read Currencies','CURRENCY','READ'),(140,'configuration','UPDATE_CURRENCY','Update Currencies','Update Currencies','CURRENCY','UPDATE'),(141,'portfolio','CREATE_DOCUMENT','Create and Upload a Document','Create and Upload a Document','DOCUMENT','CREATE'),(142,'portfolio','READ_DOCUMENT','Read Documents','Read Documents','DOCUMENT','READ'),(143,'portfolio','UPDATE_DOCUMENT','Update and Upload a Document','Update and Upload a Document','DOCUMENT','UPDATE'),(144,'portfolio','DELETE_DOCUMENT','Delete a Document','Delete a Document','DOCUMENT','DELETE'),(145,'organisation','CREATE_FUND','Create a Fund','Create a Fund','FUND','CREATE'),(146,'organisation','READ_FUND','Read Funds','Read Funds','FUND','READ'),(147,'organisation','UPDATE_FUND','Update a Fund','Update a Fund','FUND','UPDATE'),(148,'organisation','DELETE_FUND','Delete Fund','Delete Fund','FUND','DELETE'),(149,'portfolio','CREATE_GROUP','Create a Group','Create a Group','GROUP','CREATE'),
(150,'portfolio','READ_GROUP','Read Groups','Read Groups','GROUP','READ'),(151,'portfolio','UPDATE_GROUP','Update a Group','Update a Group','GROUP','UPDATE'),(152,'portfolio','DELETE_GROUP','Delete a Group','Delete a Group','GROUP','DELETE'),(153,'organisation','CREATE_LOANPRODUCT','Create a Loan Product','Create a Loan Product','LOANPRODUCT','CREATE'),(154,'organisation','READ_LOANPRODUCT','Read Loan Products','Read Loan Products','LOANPRODUCT','READ'),(155,'organisation','UPDATE_LOANPRODUCT','Update a Loan Product','Update a Loan Product','LOANPRODUCT','UPDATE'),(156,'organisation','DELETE_LOANPRODUCT','Delete a Loan Product','Delete a Loan Product','LOANPRODUCT','DELETE'),(157,'portfolio','READ_LOAN','Read Loans','Read Loans','LOAN','READ'),(158,'portfolio','UPDATE_LOAN','Update a Loan','Update a Loan','LOAN','UPDATE'),(159,'portfolio','UPDATEHISTORIC_LOAN','Update a Loan that was Created Historically','Update a Loan that was Created Historically','LOAN','UPDATEHISTORIC'),(160,'portfolio','CREATE_LOANCHARGE','Create a Loan Charge','Create a Loan Charge','LOANCHARGE','CREATE'),(161,'portfolio','UPDATE_LOANCHARGE','Update a Loan Charge','Update a Loan Charge','LOANCHARGE','UPDATE'),(162,'portfolio','DELETE_LOANCHARGE','Delete a Loan Charge','Delete a Loan Charge','LOANCHARGE','DELETE'),(163,'portfolio','WAIVE_LOANCHARGE','Waive a Loan Charge','Waive a Loan Charge','LOANCHARGE','WAIVE'),(164,'transaction_loan','BULKREASSIGN_LOAN','Bulk Reassign Loans','Bulk Reassign Loans','LOAN','BULKREASSIGN'),(165,'transaction_loan','ADJUST_LOAN','Adjust a Loan Transaction','Adjust a Loan Transaction','LOAN','ADJUST'),(166,'transaction_loan','WAIVEINTERESTPORTION_LOAN','Waive Portion of Loan Interest','Waive Portion of Loan Interest','LOAN','WAIVEINTERESTPORTION'),
(167,'transaction_loan','WRITEOFF_LOAN','Write-Off a Loan','Write-Off a Loan','LOAN','WRITEOFF'),(168,'transaction_loan','CLOSE_LOAN','Close a Loan','Close a Loan','LOAN','CLOSE'),(169,'transaction_loan','CLOSEASRESCHEDULED_LOAN','Close a Loan (having been Rescheduled (Mifos Legacy Need)','Close a Loan (having been Rescheduled (Mifos Legacy Need)','LOAN','CLOSEASRESCHEDULED'),(170,'organisation','READ_MAKERCHECKER','Read Maker-Checker Entries','Read Maker-Checker Entries','MAKERCHECKER','READ'),(171,'organisation','CREATE_OFFICE','Create an Office','Create an Office','OFFICE','CREATE'),(172,'organisation','READ_OFFICE','Read Offices','Read Offices','OFFICE','READ'),(173,'organisation','UPDATE_OFFICE','Update an Office','Update an Office','OFFICE','UPDATE'),(174,'organisation','DELETE_OFFICE','Delete an Office','Delete an Office','OFFICE','DELETE'),(175,'organisation','READ_OFFICETRANSACTION','Read Office Transactions','Read Office Transactions','OFFICETRANSACTION','READ'),(176,'organisation','CREATE_OFFICETRANSACTION','Create an Office Transaction','Create an Office Transaction','OFFICETRANSACTION','CREATE'),(177,'authorisation','READ_PERMISSION','Read Permissions','Read Permissions','PERMISSION','READ'),(178,'authorisation','CREATE_ROLE','Create a Role','Create a Role','ROLE','CREATE'),(179,'authorisation','READ_ROLE','Read Roles','Read Roles','ROLE','READ'),(180,'authorisation','UPDATE_ROLE','Update a Role','Update a Role','ROLE','UPDATE'),(181,'authorisation','DELETE_ROLE','Delete a Role','Delete a Role','ROLE','DELETE'),(182,'authorisation','CREATE_USER','Create a User','Create a User','USER','CREATE'),(183,'authorisation','READ_USER','Read Users','Read Users','USER','READ'),(184,'authorisation','UPDATE_USER','Update a User','Update a User','USER','UPDATE'),
(185,'authorisation','DELETE_USER','Create a User','Create a User','USER','DELETE'),(186,'organisation','CREATE_STAFF','Create a Staff Member','Create a Staff Member','STAFF','CREATE'),(187,'organisation','READ_STAFF','Read Staff','Read Staff','STAFF','READ'),(188,'organisation','UPDATE_STAFF','Update a Staff Member','Update a Staff Member','STAFF','UPDATE'),(189,'organisation','DELETE_STAFF','Delete a Staff Member','Delete a Staff Member','STAFF','DELETE'),(190,'organisation','CREATE_SAVINGSPRODUCT','Create a Savings Product','Create a Savings Product','SAVINGSPRODUCT','CREATE'),(191,'organisation','READ_SAVINGSPRODUCT','Read Savings Products','Read Savings Products','SAVINGSPRODUCT','READ'),(192,'organisation','UPDATE_SAVINGSPRODUCT','Update a Savings Product','Update a Savings Product','SAVINGSPRODUCT','UPDATE'),(193,'organisation','DELETE_SAVINGSPRODUCT','Delete a Savings Product','Delete a Savings Product','SAVINGSPRODUCT','DELETE'),(194,'organisation','CREATE_DEPOSITPRODUCT','Create a Deposit Product','Create a Deposit Product','DEPOSITPRODUCT','CREATE'),(195,'organisation','READ_DEPOSITPRODUCT','Read Deposit Products','Read Deposit Products','DEPOSITPRODUCT','READ'),(196,'organisation','UPDATE_DEPOSITPRODUCT','Update a Deposit Product','Update a Deposit Product','DEPOSITPRODUCT','UPDATE'),(197,'organisation','DELETE_DEPOSITPRODUCT','Delete a Deposit Product','Delete a Deposit Product','DEPOSITPRODUCT','DELETE'),(198,'portfolio','CREATE_DEPOSITACCOUNT','Create a Deposit Account','Create a Deposit Account','DEPOSITACCOUNT','CREATE'),(199,'portfolio','READ_DEPOSITACCOUNT','Read Deposit Accounts','Read Deposit Accounts','DEPOSITACCOUNT','READ'),(200,'portfolio','UPDATE_DEPOSITACCOUNT','Update a Deposit Account','Update a Deposit Account','DEPOSITACCOUNT','UPDATE'),
(201,'portfolio','DELETE_DEPOSITACCOUNT','Delete a Deposit Account','Delete a Deposit Account','DEPOSITACCOUNT','DELETE'),(202,'transaction_deposit','APPROVE_DEPOSITACCOUNT','Approve a Deposit Account','Approve a Deposit Account','DEPOSITACCOUNT','APPROVE'),(203,'transaction_deposit','REJECT_DEPOSITACCOUNT','Reject Deposit Account','Reject Deposit Account','DEPOSITACCOUNT','REJECT'),(204,'transaction_deposit','WITHDRAW_DEPOSITACCOUNT','Withdraw Deposit Account','Withdraw Deposit Account','DEPOSITACCOUNT','WITHDRAW'),(205,'transaction_deposit','APPROVALUNDO_DEPOSITACCOUNT','Undo Approval of Deposit Account','Undo Approval of Deposit Account','DEPOSITACCOUNT','APPROVALUNDO'),(206,'transaction_deposit','WITHDRAWAL_DEPOSITACCOUNT','Make a Withdrawal From Deposit Account','Make a Withdrawal From Deposit Account','DEPOSITACCOUNT','WITHDRAWAL'),(207,'transaction_deposit','INTEREST_DEPOSITACCOUNT','Apply Interest to Deposit Accounts','Apply Interest to Deposit Accounts','DEPOSITACCOUNT','INTEREST'),(208,'transaction_deposit','RENEW_DEPOSITACCOUNT','Renew Deposit Account','Renew Deposit Account','DEPOSITACCOUNT','RENEW'),(209,'portfolio','CREATE_SAVINGSACCOUNT','Create a Savings Account','Create a Savings Account','SAVINGSACCOUNT','CREATE'),(210,'portfolio','READ_SAVINGSACCOUNT','Read Savings Accounts','Read Savings Accounts','SAVINGSACCOUNT','READ'),(211,'portfolio','UPDATE_SAVINGSACCOUNT','Update a Savings Account','Update a Savings Account','SAVINGSACCOUNT','UPDATE'),(212,'portfolio','DELETE_SAVINGSACCOUNT','Delete a Savings Account','Delete a Savings Account','SAVINGSACCOUNT','DELETE'),(213,'authorisation','PERMISSIONS_ROLE','Manage Permissions for a Role','Manage Permissions for a Role','ROLE','PERMISSIONS');

/* role 1 is super user, give it ALL_FUNCTIONS */
INSERT INTO m_role_permission(role_id, permission_id)
select 1, id
from m_permission
where code = 'ALL_FUNCTIONS';

/* add a read permission for each defined report */
insert into m_permission(grouping, `code`, default_description, default_name, entity_name, action_name)
select 'report', concat('READ_', sr.report_name), concat('READ_', sr.report_name), concat('READ_', sr.report_name), sr.report_name, 'READ'
from stretchy_report sr;

/* add a create, read, update and delete permission for each registered datatable */
insert into m_permission(grouping, `code`, default_description, default_name, entity_name, action_name)
select 'datatable', concat('CREATE_', r.registered_table_name), concat('CREATE_', r.registered_table_name), concat('CREATE_', r.registered_table_name), r.registered_table_name, 'CREATE'
from x_registered_table r;

insert into m_permission(grouping, `code`, default_description, default_name, entity_name, action_name)
select 'datatable', concat('READ_', r.registered_table_name), concat('READ_', r.registered_table_name), concat('READ_', r.registered_table_name), r.registered_table_name, 'READ'
from x_registered_table r;

insert into m_permission(grouping, `code`, default_description, default_name, entity_name, action_name)
select 'datatable', concat('UPDATE_', r.registered_table_name), concat('UPDATE_', r.registered_table_name), concat('UPDATE_', r.registered_table_name), r.registered_table_name, 'UPDATE'
from x_registered_table r;

insert into m_permission(grouping, `code`, default_description, default_name, entity_name, action_name)
select 'datatable', concat('DELETE_', r.registered_table_name), concat('DELETE_', r.registered_table_name), concat('DELETE_', r.registered_table_name), r.registered_table_name, 'DELETE'
from x_registered_table r;


