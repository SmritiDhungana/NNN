Feature: Equipment Console Process Feature

    #....................................../ (EMGR-196) Test Agent Adjustments - Weekly Adjustment Records /....................................#
    #............................................./ (EMGR-158) Test #2 - Agent Reimbursements - EBH /...........................................#
    # 37a    #SQL#1 #





  @Smoke @EquipmentConsole @IdentifyINVOICEforAgentReimbursement @aa
  Scenario Outline: Identify INVOICE, for Agent Reimbursement Process on EQUIPMENT CONSOLE
    Given Locate a Record from Database, Agent Status must have a Status of Agent Review or Corp Review <Environment> <TableName1> <CreatedDate>
    Examples:
      | Environment | TableName1                              | CreatedDate  |
      | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "01/01/2022" |


    # 37b    #SQL#3 #
  @EquipmentConsole  @AgentReimbursement  @aa @Smoke
  Scenario Outline: Verify Agent Reimbursement Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus>
   # And Select Agent <Agent> and ProNo <ProNum>
   # And Select Agent Reimbursement on Status <Status>
   # And Enter Amount OR # of Days, Effective Date = Todays Date <Amount> <EffectiveDate>
   # And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column
   # And Enter something into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
  #  And Verify previously entered data remained the same, Select Go, Select Yes, Main Form appears
  #  And Query Data in Agent Adjustments Table, There should be no record in this table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
    Then Click on Clear Filters
    And Select Corp Status = Corp Review for that same record that has Agent Status = Agent Reimbursement and Corp Status = Corp Review <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select Agent Reimbursement on CropReview
    And The Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the Notes Column
    And Enter something into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
    And Verify previously entered data remained the same, Select Go, Select Yes, Main Form Appears
    And Verify Agent Status and Corp Status = Agent Reimbursement in Main Form
    And Query Data in Agent_Adjustments SQL Table, There should be one record on the Agent_Adjustments table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo        | AgentStatus   | Agent | ProNum  | OrderNum   | Status               | Amount | EffectiveDate | Notes                 | CreatedDate  | CorpStatus   | AgentStatus1         | OfDays | Notes1                       | Environment | TableName                               | Environment1 | Browser  | Username | Password |
    #  | "JH202204071545DM"  | "AgentReview" | "HCI" | "112814" | "HCI112814" | "AgentReimbursement" |  "400" |  "06-13-2022" | "Automation Testing," | "06-13-2022" |"CorpReview"| "AgentReimbursement" |  "3"  |"   Automation Testing !!!," |   "launch"  | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "JH202204071545DM"  | "AgentReview" | "MCM" | "535404" | "MCM535404" | "AgentReimbursement" |  "400" |  "06-13-2022" | "Automation Testing," | "06-13-2022" |"CorpReview"| "AgentReimbursement" |  "3"  |"   Automation Testing !!!," |   "launch"  | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |  "eclaunch"  | "chrome" |  "eqpb" |  "taffy"  |
    #  | "JH202204071515DM"  | "AgentReview" | "HCI" | "112814" | "HCI112814" | "AgentReimbursement" |  "400" |  "06-13-2022" | "Automation Testing," | "06-13-2022" |"CorpReview"| "AgentReimbursement" |  "3"  |"   Automation Testing !!!," |   "launch"  | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "JH202204071545DM"  | "AgentReview" | "HCI" | "112786" | "HCI112786" | "AgentReimbursement" |  "400" |  "07-15-2022" | "Automation Testing," | "07-15-2022" |"CorpReview"| "AgentReimbursement" |  "3"  |"   Automation Testing !!!," |   "launch"  | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"  | "chrome" |  "eqpb" |  "taffy"  |
   #   | "JH202204071515DM" | "AgentReview" | "HCI" | "120252" | "HCI120252" | "AgentReimbursement" | "400"  | "08-29-2022"  | "Automation Testing," | "08-29-2022" | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   #   | "JH2209011055DM" | "AgentReview" | "AJD" | "113476" | "AJD113476" | "AgentReimbursement" | "400"  | "08-29-2022"  | "Automation Testing," | "08-29-2022" | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
      | "JH2209011055DM" | "AgentReview" | "EDM" | "57978" | "EDM57978" | "AgentReimbursement" | "400"  | "09-02-2022"  | "Automation Testing," | "09-02-2022" | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
  ##    | "JH2209011055DM" | "AgentReview" | "JFL" | "701577" | "JFL701577" | "AgentReimbursement" | "400"  | "08-29-2022"  | "Automation Testing," | "08-29-2022" | "CorpReview" | "AgentReimbursement" | "3"    | "   Automation Testing !!!," | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |



    # 38a   #SQL#2 #
  @Regression @EquipmentConsole  @IdentifyINVOICEforAgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable
  Scenario Outline: Identify INVOICE, Test for Agent without a record on the Agent_Settlement_Info Table, Agent Reimbursements
    Given Locate a Record to test from Database, Must have a Corp Status of Agent Review or Corp Review <Environment> <TableName2> <CreatedDate>
    Examples:
      | Environment | TableName2                              | CreatedDate  |
      | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "01/01/2022" |


      # 38b
  @Regression @EquipmentConsole  @AgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable
  Scenario Outline: Test for Agent without a record on the Agent_Settlement_Info table, Agent Reimbursements
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface and Click Refresh
    And Select a record that has a Corp Status = Corp Review or Agent Review <CorpStatus>
  #  And Select Agent Reimbursement
    And Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the notes column
    And Enter Amount OR Days, Effective Date = Todays Date <Amount> <EffectiveDate>
    And Enter something into the Notes Column <Notes> and Select Go, Select Yes, Select OK
    And Select X to Close Agent Reimbursement Form
    And Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the same
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo          | Amount | EffectiveDate | CorpStatus   | Notes                     | Environment1 | Browser  | Username | Password |
      | "JH202204081615DM" | "300"  | "07-15-2022"  | "CorpReview" | "Automation Testing Done" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |




   #................................................../ (EMGR-159) Test #3 - Agent Deductions - EBH /....................................................#
    # 39a   # SQL#1 #
  @Regression @EquipmentConsole @IdentifyINVOICEforAgentDeduction
  Scenario Outline: Identify INVOICE, for Agent Deduction Process
    Given Locate a Record from Database for Agent Deduction Process, Agent Status must have a Status of Agent Review <Environment> <TableName1> <CreatedDate>
    Examples:
      | Environment | TableName1                              | CreatedDate  |
      | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "01/01/2022" |


    # 39b   # SQL#2 #
  @EquipmentConsole  @AgentDeduction
  Scenario Outline: Verify Agent Deduction Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter the Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select Agent Status Button for a record that has Agent Status = Agent Review or Corp Review <AgentStatus>
    And Select the Agent <Agent> and ProNo <ProNum>
    And Select Agent Deduct on Agent Review Status <Status>
    And Enter Amount Or # of Days, Effective Date = Todays Date, Splits 1 <Amount> <EffectiveDate>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the Notes Column
    And Enter Notes into the Notes Column and make sure to enter Comma, Select Ok, Select Go, Select No <Notes>
    And Verify previously entered data remained Same, Select Go, Select Yes, Main Form appears
    And Query Data in Agent Adjustments Table, There should be no Record in this table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
    Then Click on Clear Filters
    And Select Corp Status = Corp Review for the Same Record, that has the Agent Status = Agent Deduct and Corp Status = Corp Review <CorpStatus> <AgentStatus1> <Agent> <ProNum>
    And Select Agent Deduct on Corp Review Status <Status>
    And The Days, Amount and Notes Column are filled in with the same information that was previously entered. Enter different Amount, Days or Effective Date <OfDays> <Splits> <EffectiveDate1>
    And Verify Notes Column has the Customer Number, Chassis No and Container No, already prefilled in the Notes Column
    And Enter Notes <Notes1> into the Notes Column, make sure to enter a Comma, Select Ok, Select Go, Select No
    And Verify previously entered data remained the Same, Select Go, Select Yes, Main Form Appears
    And Verify Agent Status and Corp Status = Agent Deduct in Main Form
    And Query Data in Agent_Adjustments SQL Table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo        | AgentStatus   | Agent | ProNum   | OrderNum    | Status        | Amount | EffectiveDate | EffectiveDate1 | Notes                 | CreatedDate  | CorpStatus   | AgentStatus1  | OfDays | Notes1                     | Splits | Environment | TableName                               | Environment1 | Browser  | Username | Password |
    #  | "JH202204071500CH" | "AgentReview" | "MPA" | "174451" | "MPA174451" | "AgentDeduct" |  "800" |  "06-01-2022" |  "05-20-2022"  | "Automation Testing," | "06/01/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "JH202204071545DM" | "AgentReview" | "MPA" | "174607" | "MPA174607" | "AgentDeduct" |  "800" |  "06-01-2022" |  "05-20-2022"  | "Automation Testing," | "06/01/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "JH202204071545DM" | "AgentReview" | "HCI" | "120255" | "HCI120255" | "AgentDeduct" |  "800" |  "06-13-2022" |  "05-20-2022"  | "Automation Testing," | "06/13/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  | "JH202204071545DM" | "AgentReview" | "HCI" | "120254" | "HCI120254" | "AgentDeduct" |  "800" |  "06-13-2022" |  "05-20-2022"  | "Automation Testing," | "06/13/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   | "JH202204071515DM" | "AgentReview" | "HCI" | "120253" | "HCI120253" | "AgentDeduct" |  "800" |  "06-13-2022" |  "06-20-2022"  | "Automation Testing," | "06/13/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "launch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   | "JH202204071515DM" | "AgentReview" | "HCI" | "112814" | "HCI112814" | "AgentDeduct" |  "800" |  "06-21-2022" |  "06-20-2022"  | "Automation Testing," | "06/21/2022" |"CorpReview"| "AgentDeduct" |   "4"  | " Automation Testing !!!," | "5"   | "ebhlaunch"    | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |  "eclaunch"  | "chrome" |  "eqpb" |  "taffy"  |
  #    | "JH202204071545DM" | "AgentReview" | "HCI" | "112776" | "HCI112776" | "AgentDeduct" | "800"  | "08-11-2022"  | "08-11-2022"   | "Automation Testing," | "08/11/2022" | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
      | "JH2209011055DM" | "AgentReview" | "EDM" | "57978"  | "EDM57978"  | "AgentDeduct" | "800"  | "08-11-2022"  | "08-11-2022"   | "Automation Testing," | "08/11/2022" | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |
   ##   | "JH2209011055DM" | "AgentReview" | "LOH" | "193936" | "LOH193936" | "AgentDeduct" | "800"  | "08-11-2022"  | "08-11-2022"   | "Automation Testing," | "08/11/2022" | "CorpReview" | "AgentDeduct" | "4"    | " Automation Testing !!!," | "5"    | "ebhlaunch" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |




     # 40a   # SQL#2
  @Regression @EquipmentConsole  @IdentifyINVOICEforAgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable
  Scenario Outline: Identify INVOICE, Test for Agent without a record on the Agent_Settlement_Info table, Agent Deduction
    Given Locate a Record to test from Database, select a record that has Corp Status = Corp Review <Environment> <TableName2> <CreatedDate>
    Examples:
      | Environment | TableName2                              | CreatedDate  |
      | "ebhlaunch" | "[Evans].[dbo].[InvoiceRegisterRecord]" | "01/01/2022" |


    # 40b
  @Regression @EquipmentConsole  @AgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable
  Scenario Outline: Test for Agent without a record on the Agent_Settlement_Info table, Agent Deduction
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console and Click Refresh
    And Select a record that has a Corp Status = Corp Review or Agent Review <CorpStatus>
    And Select Agent Deduct
    And Verify Notes Column has the Customer No, Chassis No and Container No, Prefilled in the Notes Column
    And Enter Amount OR No of Days, Effective Date = Todays Date <Amount> <EffectiveDate>
    And Enter some notes into the Notes Column <Notes> and Select Go, Select Yes, Select OK
    And Select X to Close Agent Deduction Form
    And Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the Same
    Then Close all open Browsers for Equipment Console

    Examples:
      | InvoiceNo          | Amount | EffectiveDate | CorpStatus   | Notes                     | Environment1 | Browser  | Username | Password |
   #   | "JH202204081615DM" | "234"  |  "05-27-2022" |"CorpReview"| "Automation Testing Done" |
    #  | "JH202204071515DM" | "234"  |  "05-27-2022" |"CorpReview"| "Automation Testing Done" |
      | "JH202204081630CH" | "450"  | "05-31-2022"  | "CorpReview" | "Automation Testing Done" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |








   #........................./ (EMGR-132) Test #2 - Bill Customer and Create Order with a Suffix /....................................#

    # 41a
  @Regression @EquipmentConsole  @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICEforBillCustomer
  Scenario Outline: Verify TAX and ADMIN BILLING CODES have been setup and Identify INVOICE for Bill Customer on EQUIPMENT CONSOLE
    Given Locate a Record to Verify TAX and ADMIN BILLING CODES have been setup for Bill Customer from Database SQL-One <Environment> <TableName1>
    Then Locate a Record to Identify INVOICE for Bill Customer from Database SQL-Two <Environment> <TableName2>
    Examples:
      | Environment | TableName1                          | TableName2                        |
      | "ebhlaunch" | "[EBHLaunch].[dbo].[Billing_Codes]" | "[Evans].[dbo].[InvoiceRegister]" |


    # 41b
  @EquipmentConsole  @BillCustomer
  Scenario Outline: Bill Customer with Different Billing Codes, Multiple Billing Line Items, Tax, Admin Fee's and Change the Bill To on the Form to a different Bill To Number, and Create Order with a Suffix on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Equipment Console
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Equipment Console
    And Click on NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface
    And Select a Record that has a Agent Status and Corp Status <AgentStatus> <CorpStatus> = Corp Review or Agent Review
    And Select the Agent <Agent> and ProNo <ProNum>
    And Select Bill Customer on Corp Review Status <Status>
    And Enter all the information in Bill Customer Form, Select Go, Select Yes <BilledToID> <BillingCode> <NoofDays> <Rate> <NoofDays2> <Rate2> <AdminFee> <Notes> <BillDetails>
    And Verify Bill is created for ProNo <ProNum>
    Then Close all open Browsers for Equipment Console
    Examples:
      | InvoiceNo        | AgentStatus   | CorpStatus    | Agent | ProNum   | Status         | BilledToID | BillingCode | NoofDays | Rate | NoofDays2 | Rate2 | AdminFee | Notes                    | BillDetails   | Environment1 | Browser  | Username | Password |
   #   | "JH2112010906CH" | "AgentReview" | "AgentReview" | "HCI" | "120105" | "BillCustomer" | "FEDBRCA" |  "DCC"     |    "7"   |  "8" |    "6"    |  "6"  |   "18"   | "Automation Testing !!!" | "Testing !!!" |  "eclaunch"  | "chrome" |  "eqpb" |  "taffy"  |
      | "JH2109291100DM" | "AgentReview" | "AgentReview" | "AAR" | "192758" | "BillCustomer" | "FEDBRCA"  | "DCC"       | "7"      | "8"  | "6"       | "6"   | "18"     | "Automation Testing !!!" | "Testing !!!" | "eclaunch"   | "chrome" | "eqpb"   | "taffy"  |




    # 41c
  @Regression @EquipmentConsole  @RetrieveNewOrderPRo
  Scenario Outline: Retrieve the NEW ORDER PRO (Order w/Suffix) from Chassis Bill Customer Record
    Given Verify Order Pro is created in Chassis Bill Customer Record, and Retrieve the NEW ORDER PRO from Database SQL-Three <Environment> <TableName3> <OriginalProNumber>
    Examples:
      | Environment | TableName3                                  | OriginalProNumber |
   #   | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" |    "HCI120105%"   |
      | "ebhlaunch" | "[Evans].[dbo].[ChassisBillCustomerRecord]" | "AAR192758%"      |

    # 41d
  @Regression @EquipmentConsole  @VerifyDataInTables
  Scenario Outline: Verify Data in Tables
    Given Verify Data on Orders Table, Enter ORIGINAL PRO and NEW PRO and Retrieve records from Database SQL-Four <Environment> <TableName> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Billing Table, Enter ORDER ID from NEW PRO NUMBER and Retrieve records from Database SQL-Five <Environment> <TableName> <ProNumberWithSuffix>
    And Verify Data on Order_Refs Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and Retrieve records from Database SQL-Six <Environment> <TableName> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Ops Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER then Retrieve records from Database SQL-Seven <Environment> <TableName> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Misc Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and then Retrieve records from Database SQL-Eight <Environment> <TableName> <OriginalProNumber> <ProNumberWithSuffix>
    And Verify Data on Order_Action_History Table, Enter ORDER ID from NEW PRO NUMBER, and Retrieve Records from Database SQL-Nine <Environment> <TableName> <ProNumberWithSuffix>
    And Verify Data on Order_Notes Table, Enter ORDER ID from NEW PRO NUMBER and then Retrieve records from Database SQL-Ten <Environment> <TableName> <ProNumberWithSuffix>
    And Verify Data on Order_Billing_Com Table, Enter ORDER ID from NEW PRO NUMBER then Retrieve records from Database SQL-Eleven <Environment> <TableName> <ProNumberWithSuffix>
    And Verify Record was added to Agent_Stl_Trans Table, Enter NEW PRO NUMBER and Retrieve records from Database SQL-Twelve <Environment> <TableName1> <ProNumberWithSuffix>
    And Verify Record was added to the Agent_Settlements Table and Verify Calculations are correct, Enter NEW PRO NUMBER, then Retrieve records from Database SQL-Thirteen <Environment> <TableName2> <ProNumberWithSuffix>
    Examples:
      | OriginalProNumber | ProNumberWithSuffix | Environment | TableName                    | TableName1                            | TableName2                              |
   #   |    "HCI120105"    |     "HCI120105B"    |   "ebhlaunch"  | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" |
      | "AAR192758"       | "AAR192758A"        | "ebhlaunch" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" | "[EBHLaunch].[dbo].[AGENT_Settlements]" |





