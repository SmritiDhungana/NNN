Feature: Equipment Console Tractor Adjustments Process Feature


    #     #SQL#1 #
  @IdentifyINVOICEforTractorReimbursement
  Scenario Outline: (EMGR-244) Identify INVOICE, for Tractor Reimbursement Process on EQUIPMENT CONSOLE
    Given Locate a Record from Database for Tractor Reimbursement, Agent Status must have a Status of Agent Review or Corp Review <Environment> <TableName> <TableName1> <TableName2> <TableName3> <TableName4> <TableName5>
    Examples:
      | Environment  | TableName                               | TableName1                             | TableName2                                  | TableName3                              | TableName4                   | TableName5                      |
   #   | "ebhlaunch"  | "[Evans].[dbo].[InvoiceRegisterRecord]" | "[EBHLaunch].[dbo].[Agent_Pay_Matrix]" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBHLaunch].[dbo].[Agent_Settlements]" | "[EBHLaunch].[dbo].[Orders]" | "[EBHLaunch].[dbo].[Locations]" |
      | "ebhstaging" | "[Evans].[dbo].[InvoiceRegisterRecord]"       | "[EBH].[dbo].[Agent_Pay_Matrix]"       | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]"       | "[EBH].[dbo].[Agent_Settlements]"       | "[EBH].[dbo].[Orders]"       | "[EBH].[dbo].[Locations]"       |


    # 37b    #SQL#3 #
  @Regression  @TractorReimbursement
  Scenario Outline: (EMGR-244) Verify Tractor Reimbursement Process on EQUIPMENT CONSOLE
    Given Run Test for <Environment1> on Browser <Browser> and Enter the url for Tractor
    And Login to the Portal with USERNAME <Username> and PASSWORD <Password> for Tractor
    And Click NO on alert
    And Enter Invoice Number <InvoiceNo> in Search Criteria on Equipment Console Interface for Tractor
    And Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review <AgentStatus> for Tractor
    And Select Agent <Agent> and ProNo <ProNum> for Tractor
    And Select Driver Reimbursement on Status <Status>
   # And Enter Amount OR # of Days, Effective Date = Todays Date <Amount> <EffectiveDate>
  #  And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column
  #  And Enter something into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No <Notes>
  #  And Verify previously entered data remained the same, Select Go, Select Yes, Main Form appears
  #  And Query Data in Agent Adjustments Table, There should be no record in this table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
  #  Then Click on Clear Filters
   # And Select Corp Status = Corp Review for that same record that has Agent Status = Agent Reimbursement and Corp Status = Corp Review <CorpStatus> <AgentStatus1> <Agent> <ProNum>
  #  And Select Agent Reimbursement on CropReview
  #  And The Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date <OfDays>
  #  And Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the Notes Column
  #  And Enter something into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No <Notes1>
  #  And Verify previously entered data remained the same, Select Go, Select Yes, Main Form Appears
  #  And Verify Agent Status and Corp Status = Agent Reimbursement in Main Form
  #  And Query Data in Agent_Adjustments SQL Table, There should be one record on the Agent_Adjustments table for this transaction <Environment> <TableName> <CreatedDate> <OrderNum>
    Then Close all open Browsers on Equipment Console
    Examples:
      | InvoiceNo  | AgentStatus   | Agent | ProNum   | OrderNum    | Status               | Amount | EffectiveDate | Notes                 | CreatedDate  | CorpStatus   | AgentStatus1         | OfDays | Notes1                       | Environment | TableName | Environment1 | Browser  | Username | Password |
      | "10050843" | "AgentReview" | "AAR" | "192477" | "AAR192477" | "DriverReimbursement" | "400"  | "09-07-2022"  | "Automation Testing," | "09-07-2022" | "CorpReview" | "DriverReimbursement" | "3"    | "   Automation Testing !!!," | ""          | ""        | "ecstaging"  | "chrome" | "eqpa"   | "spring" |


