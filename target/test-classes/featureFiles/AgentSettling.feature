Feature: Agent Settling Transaction Process Feature


    #1
  @Regression @OWTBFContinue
  Scenario Outline: Finalize OWTBF (continue) on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click NO on drop down item list and validate the color of OWTBF at the left end of the page
    And Click YES on drop down item list for OWTBF as <OWTBF> and validate the color of OWTBF at the left end of the page
    And Click Orders Waiting To Be Finalized Button at the left end of the page
    And Verify Agent/Location Code and Record Returned on Orders Waiting To Be Finalized Page for <AgentCode>
    And Validate Records Returned with Database Record <environment> and <tableName> for <AgentCode> <VendorCode>
    And Click Finalize on Orders Waiting To Be Finalized Page and Close
    Then Close all open Browsers
    Examples:
      | OWTBF | AgentCode | VendorCode | environment | browser  | username         | password      | tableName                                               |
      | "YES" | "PSS"     | "9859"     | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetOrdersWaitingToBeFinalized]" |
   #   | "YES" |   "PSS"   |   "9859"   |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022" | "[EBHLaunch].[dbo].[usp_GetOrdersWaitingToBeFinalized]" |


 #2
  @Regressions @OWTBFReset
  Scenario Outline: Finalize OWTBF (reset) on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Reset
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click NO on drop down item list and validate the color of OWTBF at the left end of the page
    And Click YES on drop down item list for OWTBF as <OWTBF> and validate the color of OWTBF at the left end of the page
    And Click Orders Waiting To Be Finalized Button at the left end of the page
    And Verify Agent/Location Code and Record Returned on Orders Waiting To Be Finalized Page for <AgentCode>
    And Validate Records Returned with Database Record <environment> and <tableName> for <AgentCode> <VendorCode>
  #  And Click Finalize on Orders Waiting To Be Finalized Page and Close
    Then Close all open Browsers
    Examples:
      | OWTBF | AgentCode | VendorCode | environment | browser  | username         | password      | tableName                                               |
      | "YES" | "PSS"     | "9859"     | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetOrdersWaitingToBeFinalized]" |


   #3
  @Regressions  @SettleYourAgent&ViewEpicorExcelReportsContinue
  Scenario Outline: Settle Your Agent (Continue) and validate View Epicor Excel Reports with Total Settlement on Final Agent Settlement Summary Table in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click YES on drop down item list of OWTBF as <OWTBF>
    And Toggle On the Verified Box
  #  And Toggle On the Verified Box of Agent Code as <AgentCode>
    And Click on Settle Your Agents on Agent Settling Page
    And Validate Final Agent Settlement Summary Table and Negative Settlements for <VendorCode>
    And Validate Final Agent Settlement Summary Table and Negative Settlements with Database Record <environment> and <tableName> <UserLogIn> <AcctingWk> <AcctingDt>
    And Click on View Epicor Details
    And Get the File Name of downloaded Excel View Epicor Details and Add the Total Amount <VRow> <VColumn>
    And Get Excel Report of View Epicor Details from Downloads
    And Validate Amount in View Epicor Details <VRow> <VColumn> with Total Settlement on Final Agent Settlement Summary Table
    And Click on View Epicor Detail Summary
    And Get the File Name of downloaded Excel View Epicor Detail Summary and Add the Total Amount <VRow1> <VColumn1>
    And Get Excel Report of View Epicor Detail Summary from Downloads
    And Validate Amount in View Epicor Detail Summary <VRow1> <VColumn1> with Total Settlement on Final Agent Settlement Summary Table
    And Click on View Epicor Header
    And Get the File Name of downloaded Excel View Epicor Header, Get Excel Report from Downloads and Validate Amount in View Epicor Header <VRow2> <VColumn2> with Total Settlement on Final Agent Settlement Summary Table
  #  And Validate Amount in View Epicor Header <VRow2> <VColumn2> with Total Settlement on Final Agent Settlement Summary Table
    And Click on Confirm and Settle on Final Agent Settlement Summary Page
    Then Close all open Browsers
    Examples:
      | OWTBF | VendorCode | AgentCode | UserLogIn        | AcctingWk | AcctingDt    | VRow | VColumn | VRow1 | VColumn1 | VRow2 | VColumn2 | environment | browser  | username         | password      | tableName                                                      |
      | "YES" | "9859"     | "PSS"     | "SMRITIDHUNGANA" | "27"      | "07/12/2022" | "10" | "9"     | "10"  | "9"      | "10"  | "9"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetFinalAgentSettlementSummaryDetail]" |
    #  | "YES" |   "10008"   |   "YNN"   | "SMRITIDHUNGANA" |    "13"   | "04/05/2022" | "21"  |   "9"   | "21"  |   "8"   | "1"  |   "8"   | "ebhlaunch" | "chrome" |  "SMRITIDHUNGANA" |  "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetFinalAgentSettlementSummaryDetail]"


    #4
  @Regressions @AgentSettling  @SettleYourAgent&ViewEpicorExcelReportsReset
  Scenario Outline: Settle Your Agent (Reset) and validate View Epicor Excel Reports with Total Settlement on Final Agent Settlement Summary Table in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Reset
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click YES on drop down item list of OWTBF as <OWTBF>
    And Toggle On the Verified Box
  #  And Toggle On the Verified Box of Agent Code as <AgentCode>
    And Click on Settle Your Agents on Agent Settling Page
    And Validate Final Agent Settlement Summary Table and Negative Settlements for <VendorCode>
    And Validate Final Agent Settlement Summary Table and Negative Settlements with Database Record <environment> and <tableName> <UserLogIn> <AcctingWk> <AcctingDt>
    And Click on View Epicor Details
    And Get the File Name of downloaded Excel View Epicor Details and Add the Total Amount <VRow> <VColumn>
    And Get Excel Report of View Epicor Details from Downloads
    And Validate Amount in View Epicor Details <VRow> <VColumn> with Total Settlement on Final Agent Settlement Summary Table
    And Click on View Epicor Detail Summary
    And Get the File Name of downloaded Excel View Epicor Detail Summary and Add the Total Amount <VRow1> <VColumn1>
    And Get Excel Report of View Epicor Detail Summary from Downloads
    And Validate Amount in View Epicor Detail Summary <VRow1> <VColumn1> with Total Settlement on Final Agent Settlement Summary Table
    And Click on View Epicor Header
    And Get the File Name of downloaded Excel View Epicor Header, Get Excel Report from Downloads and Validate Amount in View Epicor Header <VRow2> <VColumn2> with Total Settlement on Final Agent Settlement Summary Table
  #  And Validate Amount in View Epicor Header <VRow2> <VColumn2> with Total Settlement on Final Agent Settlement Summary Table
    And Click on Confirm and Settle on Final Agent Settlement Summary Page
    Then Close all open Browsers
    Examples:
      | OWTBF | VendorCode | AgentCode | UserLogIn        | AcctingWk | AcctingDt    | VRow | VColumn | VRow1 | VColumn1 | VRow2 | VColumn2 | environment | browser  | username         | password      | tableName                                                      |
      | "YES" | "9859"     | "PSS"     | "SMRITIDHUNGANA" | "27"      | "07/12/2022" | "10" | "9"     | "10"  | "9"      | "10"  | "9"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetFinalAgentSettlementSummaryDetail]" |
    #  | "YES" |   "10008"   |   "YNN"   | "SMRITIDHUNGANA" |    "23"   | "06/14/2022" | "21"  |   "9"   | "21"  |   "8"   | "1"  |   "8"   | "ebhlaunch" | "chrome" |  "SMRITIDHUNGANA" |  "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetFinalAgentSettlementSummaryDetail]"



   #5
  @Regressions  @SettlementOrderDetailsContinue
  Scenario Outline: Validate Settlement Order Details (continue) on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click YES on drop down item list of OWTBF as <OWTBF>
    And Click on Show Details of agent code on Action field
    And Validate Settlement Summary Totals and Settlement Order Details All Totals
    And Validate the records returned on Settlement Order Details with Database Record <environment> and <tableName> <ProNum> <VendorCode> <AcctingDt> <AcctingWk>
    And Click on Show Only Order with Flags, Get the records returned and click again on Show Only Order with Flags
    And Click on Show Next Week Orders, Get the records returned and click again on Show Next Week Orders
    And Validate Records Returned on Show Next Week Orders with Database Record <environment> and <tableName> <ProNum1>
    And Click on Show Previous Week Orders, Get the records returned and click again on Show Previous Week Orders
    Then Close all open Browsers
    Examples:
      | OWTBF | ProNum      | ProNum1     | VendorCode | AcctingDt    | AcctingWk | environment | browser  | username         | password      | tableName                             |
      | "YES" | "YNN129857" | "WAL111063" | "10008"    | "03/30/2022" | "12"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" |

    #  Settlement Order Details :
  # [dbo].[AGENT_STL_TRANS]04
 #  usp_GetSettleOrderReviewDetails


    #6
  @Regressions @SettlementOrderDetailsReset
  Scenario Outline: Validate Settlement Order Details (reset) on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Reset
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click YES on drop down item list of OWTBF as <OWTBF>
    And Click on Show Details of agent code on Action field
    And Validate the records returned on Settlement Order Details with Database Record <environment> and <tableName> <ProNum> <VendorCode> <AcctingDt> <AcctingWk>
    And Click on Show Only Order with Flags, Get the records returned and click again on Show Only Order with Flags
    And Click on Show Next Week Orders, Get the records returned and click again on Show Next Week Orders
    And Validate Records Returned on Show Next Week Orders with Database Record <environment> and <tableName> <ProNum1>
    And Click on Show Previous Week Orders, Get the records returned and click again on Show Previous Week Orders
    Then Close all open Browsers
    Examples:
      | OWTBF | ProNum      | ProNum1     | VendorCode | AcctingDt    | AcctingWk | environment | browser  | username         | password      | tableName                             |
      | "YES" | "YNN129857" | "WAL111063" | "10008"    | "07/12/2022" | "27"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" |


    #7
  @Regression @AgentsYoureSettlingAddedRemoved
  Scenario Outline: Verify Vendor is Added and Removed from Agents You're Settling on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Add Vendor Code as <vendorCode> in Agent's You're Settling, Vendor Code Column then Click on Add
    And Click Yes on Conformation PopUp
    And Verify Vendor <vendorCode> is present in Agent's You're Settling Table
    And Navigate to the Settlements page on Agent Settlement
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Reset
    And Verify Vendor <vendorCode> added before is removed from Agent's You're Settling, Vendor Code Column
    And Close all open Browsers
    Examples:
      | vendorCode | environment | browser  | username         | password      |
      | "10000"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |


  #8
  @Regressions  @AdjustmentsDetailsAndAdjustmentsOnHold
  Scenario Outline: Validate Adjustment's Details and Adjustments on Hold on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Click Filter Icon on Orders Waiting To Be Finalized
    And Click YES on drop down item list of OWTBF as <OWTBF>
    And Click on Show Details of agent code on Action field
    And Get the values of Adjustment's Details Table
    And Validate Adjustment's Details with Database Record <environment> and <tableName> <VendorCode> <OrderNo>
    And Validate Adjustments on Hold
    Then Close all open Browsers
    Examples:
      | OWTBF | VendorCode | OrderNo     | environment | browser  | username         | password      | tableName                               |
      | "YES" | "9859"     | "PSS101276" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |


  #9
  @Regression  @MyActiveAgentsNotSettled
  Scenario Outline: Validate My Active Agents Not Settled on Agent Settling Page in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settling page
    And Pop Up Menu arises insert password "settle"
    And Click on Ok
    And Conformation pop up menu arises Reset or Continue, click Continue
    And Verify you are on Agent Settling Page
    And Click on Reload All My Active Agents Not Settled
    And Validate My Active Agents Not Settled for <VendorCode>
    And Validate My Active Agents Not Settled with Database Record <environment> and <tableName> <UserId> <AcctingDt> <AcctingWk> <VendorCode>
    Then Close all open Browsers
    Examples:
      | VendorCode | UserId           | AcctingDt    | AcctingWk | environment | browser  | username         | password      | tableName                                            |
      | "9859"     | "SMRITIDHUNGANA" | "07/12/2022" | "27"      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetSettlementSummaryDetail]" |


    #22
  @Regression @AgentSettlementFlagMaintenance
  Scenario Outline: Validate Agent Settlement Flag Maintenance in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Settlement Flag Maintenance
    And Validate total records returned
    And Validate total records returned with Database Record <environment> and <tableName>
    Then Close all open Browsers
    Examples:
      | environment | browser  | username         | password      | tableName                                            |
      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAgentSettlementFlagData]" |



    #23
  @Regression @DBAgentSettlements&AgentStlTrans
  Scenario Outline: Validate AGENT_SETTLEMENTS and AGENT_STL_TRANS on Database EBHLaunch
    Given Connect to <environment> and <tableName> and <tableName1> <OrderNum> and Validate the records for AGENT SETTLEMENTS and AGENT_STL_TRANS
    Examples:
      | OrderNum    | environment | tableName                               | tableName1                            |
      | "YNN129857" | "ebhlaunch" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENTS]" | "[EBHLaunch].[dbo].[AGENT_STL_TRANS]" |



     #24
  @Regression @DBOrderDocs&TfDocDataD4
  Scenario Outline: Validate ORDER_DOCS and tf_doc_data_d4 on Database
    Given Connect to <environment> and <tableName> <OrdLoc> <OrdNum> and Validate the records for ORDER_DOCS and Connect to <environment1> <tableName1> and Validate the records for tf_doc_data
    Examples:
      | OrdLoc | OrdNum   | environment | tableName                        | environment1 | tableName1                          |
    #  |  "BCO" | "104815" |  "ebhlaunch" | "[EBHLaunch].[dbo].[ORDER_DOCS]" |  "transflo"  |"[TRANSFLO].[dbo].[tf_doc_data_d4]"|
    #  |  "PSS" | "101296" | "ebhlaunch" | "[EBHLaunch].[dbo].[ORDER_DOCS]" |  "transflo"  |"[TRANSFLO].[dbo].[tf_doc_data_d4]"|
    #  |  "YNN" | "129818" | "ebhlaunch" | "[EBHLaunch].[dbo].[ORDER_DOCS]" |  "transflo"  |"[TRANSFLO].[dbo].[tf_doc_data_d4]"|
      | "YNN"  | "129857" | "ebhlaunch" | "[EBHLaunch].[dbo].[ORDER_DOCS]" | "transflo"   | "[TRANSFLO].[dbo].[tf_doc_data_d4]" |




    # DatabaseConnect
  @databaseconnect
  Scenario Outline: Connect to databaseEBH
    Given Connect to <environment> and <tableName>
    Examples:
      | environment | tableName                                  |
      | "launch"    | "[EBHLaunch].[dbo].[ACCOUNTS_CLASS_CODES]" |
























