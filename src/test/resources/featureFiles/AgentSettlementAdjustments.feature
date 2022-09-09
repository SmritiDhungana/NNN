Feature: Agent Settlement Adjustments Process Feature

    #26
  @Regression  @AgentSettlementAdjustmentsVendorId
  Scenario Outline: Validate Agent Settlement Adjustments with VendorID in Launch Environment
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor ID as <VendorID> and click
    And Validate VendorID, Vendor Code Description of <VendorID>
    And Click on Recurring Only and Validate Total Records Returned and Total Record
    And Validate Total Records Returned on Recurring Only with Database Record <environment> and <tableName> <VendorID>
    And Validate Pay Code contains ES and ME
    And Click on EFS and Validate total records Returned
    And Validate Total Records Returned on EFS with Database Record <environment> and <tableName> <VendorID>
    And Click on Include Complete and Validate total records Returned
    And Validate Total Records Returned on Include Complete with Database Record <environment> and <tableName> <VendorID>
    And Click on View on Action column at first
    And Validate Vendor Code
    And Click on crossIconPopup
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | environment | browser  | username         | password      | VendorID | tableName                                      |
      | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "9859"   | "[EBHLaunch].[dbo].[usp_GetVendorCodeDetails]" |
   #   |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|   ""   |     "[EBH].[dbo].[usp_GetVendorCodeDetails]"   |


    #27
  @Regression @AgentSettlementAdjustmentsAgentCode
  Scenario Outline: Validate Agent Settlement Adjustments with AgentCode in Launch Environment
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Agent Code as <AgentCode> and click
    And Validate VendorID, Vendor Code Description of <AgentCode>
    And Click on Recurring Only and Validate Total Records Returned and Total Record
    And Validate Total Records Returned for Agent Code on Recurring Only with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    And Validate Pay Code contains ES and ME
    And Click on EFS and Validate total records Returned
    And Validate Total Records Returned for Agent Code on EFS with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    And Click on Include Complete and Validate total records Returned
    And Validate Total Records Returned for Agent Code on Include Complete with Database Record <environment> and <tableName> <VendorID> <AgentCode>
    And Click on View on Action column at first
    And Validate Vendor Code
    And Click on crossIconPopup
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorID | AgentCode | environment | browser  | username         | password      | tableName                                      |
      | "10008"  | "YNN"     | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetVendorCodeDetails]" |
    #  |   ""   |    ""   |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[usp_GetVendorCodeDetails]"   |




  # [JIRA] (SET-2295) Agent Adjustment Form v1.31 - Develop Automated testing scripts as deemed applicable
  # (SET-2255) #
    #28
  @Regression @ScenarioActiveCompleteActive
  Scenario Outline: Validate on EDIT - ACTIVE to COMPLETE and back to ACTIVE on Agent Settlement Adjustments in Launch Environment (SET_2255)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click
    And Get the Records Returned on Agent Settlement Adjustments Table
    And Select the First Edit Button on Action Column
    And Change ACTIVE status to COMPLETE, Select NO Button, Confirm Status remains as ACTIVE <Status>
    And Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode>
    And Select the First Edit Button on Action Column
    And Change ACTIVE status to COMPLETE, Select YES Button, Confirm Status changes to COMPLETE <Status>
    And Validate the Records Returned, WHEN ACTIVE TO COMPLETE with Database Record <environment> <tableName> and <VendorCode> <PayCode>
    And Select the First Edit Button on Action Column
    And Change COMPLETE status to ACTIVE, Select YES Button, Confirm ACTIVE Status was accepted <Status1>
    And Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Status     | Status1  | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "AA"    | "COMPLETE" | "ACTIVE" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
   #   |  ""   |  ""   | "COMPLETE" | "ACTIVE" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |


    #29
  @Regression @ScenarioOnNewDATE
  Scenario Outline: Validate on NEW - START DATE and END DATE on Agent Settlement Adjustments Detail in Launch Environment (SET_2255)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click
    And Select the New Button, Enter required fields <PayCode> <Status> <Frequency> <Amount> <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past, Select the Save button <StartDate1> <EndDate>
    And De-select Recurring Only
    And Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode> <StartDate>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Status   | Frequency | Amount | ApplytoAgent | StartDate    | StartDate1   | EndDate      | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "DM"    | "ACTIVE" | "S"       | "2.50" | "BAL"        | "2022-08-09" | "08/09/2022" | "09/28/2022" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  |   "10000"  |   "AB"  | "ACTIVE" |    "S"    | "2.50" |     "BAL"    | "2022-08-09" | "06/28/2022" |"07/28/2022" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"| "[EBH].[dbo].[AGENT_ADJUSTMENTS]" |


  #30
  @Regression @ScenarioOnEditMaxLimitandTotaltoDate
  Scenario Outline: Validate on edit - MAX LIMIT and TOTAL TO DATE on Agent Settlement Adjustments Detail in Launch Environment (SET_2255)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click
    And Select the New Button, Enter the required fields <PayCode> <Status> <Frequency> <Amount> <ApplytoAgent>
    And Enter an End Date that is after the Start Date and not in the past <StartDate> <EndDate>
    And Enter the Max Limit to a value Less Than current Total to Date, Select the Save button <MaxLimit1> <TotalToDate>
  #  And De-select Recurring Only
  #  And Validate the Records Returned when Max Limit is LESS THAN with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount>
  #  And Select the First Edit Button on Action Column on Include Complete
    And Change the Max Limit to a value = Current Total to Date, Select the Save button <MaxLimit2>
    And Validate the Records Returned when Max Limit is EQUALS TO with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount>
    And Select the First Edit Button on Action Column on Include Complete again
    And Change the Max Limit to a Value Greater Than > current Total to Date, Select the Save button <MaxLimit3>
    And Validate the Records Returned when Max Limit is GREATER THAN with Database Record <environment> <tableName> and <VendorCode> <PayCode> <ApplytoAgent> <Frequency> <Amount>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Status   | Frequency | Amount | ApplytoAgent | MaxLimit1 | TotalToDate | MaxLimit2 | MaxLimit3 | StartDate    | EndDate      | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "AD"    | "ACTIVE" | "W"       | "7.00" | "BAL"        | "1.50"    | "2.50"      | "3.50"    | "4.50"    | "09/02/2022" | "12/28/2022" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  |  "10000"   |   "AA"  | "ACTIVE" |     "W"   | "5.00" |     "BAL"    |   "1.00"  |    "2.00"   |   "2.00"  |   "2.50"  | "06/26/2022" |"07/28/2022" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |



       #31
  @Regression @ScenarioAdvancedSearchVendorCode
  Scenario Outline: Validate on ADVANDED SEARCH - VENDOR CODE, PAY CODE, ORDER NUMBER in Agent Settlement Adjustments in Launch Environment
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Select the Advanced Search button
    And Enter Vendor Code <VendorCode> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode>
    And Enter Vendor Code, enter Pay Code <PayCode> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and <VendorCode> <PayCode1>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and <VendorCode> <PayCode1>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1>
    And Enter Vendor Code, Pay Code and Order Number <OrderNo> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and <VendorCode> <PayCode1> <OrderNo>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode                          | PayCode1 | OrderNo     | environment | browser  | username         | password      | tableName                               |
      | "10008"    | "DM - PER DIEM/M & R DEDUCTIONS" | "DM"     | "YNN123456" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  |   "10008"    |       ""        |   ""   | "" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |


    #32
  @Regression @ScenarioAdvancedSearchAgentCode
  Scenario Outline: Validate on ADVANDED SEARCH - AGENT CODE, PAY CODE, ORDER NUMBER in Agent Settlement Adjustments in Launch Environment
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Select the Advanced Search button
    And Enter Agent Code <AgentCode> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode>
    And Enter Agent Code, enter Pay Code <PayCode> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1>
    And Get Records when RECURRING ONLY is SELECTED, Validate Records Returned for Advanced Search with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1>
    And Enter Agent Code, Pay Code and Order Number <OrderNo> and Click on Search Button
    And Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo>
    And Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned for Advanced Search with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo>
    And Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record <environment> <tableName> and Agent Code <AgentCode> <PayCode1> <OrderNo>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | AgentCode | PayCode                          | PayCode1 | OrderNo     | environment | browser  | username         | password      | tableName                               |
      | "YNN"     | "DM - PER DIEM/M & R DEDUCTIONS" | "DM"     | "YNN123456" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  |   " "   |  "CH - CHASSIS USAGE DEDUCTION"  |   "CH"   | "         " |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |


    #33
  @Regression @ScenarioViewButton
  Scenario Outline: Validate records on VIEW - on Agent Settlement Adjustments in Launch Environment (SET_2283)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Select the Advanced Search button
    And Enter Vendor Code <VendorCode> and Click on Search Button
    And Select the First View Button on Action Column
    And Confirm the company code, vendor code, all locations and vendor name are displayed in header in blue font <CompanyCodeVendorCodeAllLoactionVendorName>
    And Select the 'X' on form
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | CompanyCodeVendorCodeAllLoactionVendorName              | environment | browser  | username         | password      |
      | "10000"    | "10000 (EVA) - BAL,HMT,PBJ,WCC,WCL,YLS - JMT LOGISTICS" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
   #   |   "     "   |       "                                        "       |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|



     #34
  @Regression @ScenarioDeleteButton
  Scenario Outline: Validate records on DELETE - on Agent Settlement Adjustments in Launch Environment (SET_2283)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Select the Advanced Search button
    And Enter Vendor Code <VendorCode> and Click on Search Button
    And Click on Status, Inactive <Status>
    And Validate the Records Returned for Delete with Database Record <environment> <tableName> and <VendorCode> <Status> <ApplyToAgent>
  #  And Select the First Delete Button on Action Column
  #  And Validate the Records Returned for Delete on Hold with Database Record <environment> <tableName> and <VendorCode> <Status1> <ApplyToAgent>
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | Status     | ApplyToAgent | Status1 | environment | browser  | username         | password      | tableName                               |
      | "10000"    | "INACTIVE" | "WCC"        | "HOLD"  | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" |
    #  |  ""   | "INACTIVE" |    ""     |  "HOLD" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |


    #35
  @Regression @ScenarioReport
  Scenario Outline: Validate records on REPORT - on Agent Settlement Adjustments in Launch Environment
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click
    And Select the Report button, SEARCH RESULTS button
    And Get SEARCH RESULTS Excel Report from Downloads
    And Validate Excel Report with Database Record <environment> <tableName> and <VendorCode> <tableName1>
    And De-select the Include Complete button, Select the Report button, SEARCH RESULTS button
    And Get SEARCH RESULTS Excel Report from Downloads when Include Complete De-selected
    And Validate SEARCH RESULTS Excel Report when Include Complete is De-selected with Database Record <environment> <tableName> and <VendorCode> <tableName1>
    And De-select the Recurring Only button, Select the Report button, SEARCH RESULTS button
    And Get SEARCH RESULTS Excel Report from Downloads when Recurring Only De-selected
    And Validate SEARCH RESULTS Excel Report when Recurring Only is De-selected with Database Record <environment> <tableName> and <VendorCode> <tableName1>
    Then Close all open Browsers for Agent Settlement Adjustments
    Examples:
      | VendorCode | environment | browser  | username         | password      | tableName                               | tableName1                                       |
      | "10000"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]" | "[EBHLaunch].[dbo].[usp_GetAgentRecordForExcel]" |
    #  |  "    "   |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|     "[EBH].[dbo].[AGENT_ADJUSTMENTS]"   |    "[EBH].[dbo].[usp_GetAgentRecordForExcel]"    |


     #36
  @Regression @ScenarioQuickEntry
  Scenario Outline: Validate QUICK ENTRY on Agent Settlement Adjustments Detail in Launch Environment (SET_2255)
    Given Run Test for <environment> on Browser <browser> for EBH Agent Settlement Adjustments
    And Enter the url for EBH Agent Settlement Adjustments
    And Login to the Agents Portal with username <username> and password <password> for EBH Agent Settlement Adjustments
    And Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments
    And Navigate to the Settlements page for Agent Settlement Adjustments
    And Navigate to Agent Settlement Adjustments
    And Enter Vendor Code <VendorCode> and Click
    And Select Quick Entry
    And Enter the required fields <PayCode> <Amount> <ApplytoAgent> <StartDate> <OrderNo> <Notes>
    And Select Save, Click Yes on Conformation
    Then Close all open Browsers on EBH for Agent Settlement Adjustments
    Examples:
      | VendorCode | PayCode | Amount | ApplytoAgent | StartDate    | OrderNo     | Notes              | environment | browser  | username         | password      |
      | "10008"    | "AA"    | "5.00" | "YNN"        | "06/26/2022" | "YNN128901" | "AB45678901234XYZ" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
  #    |   "    "    |   "AC"  | "5.00" |     "   "    | "06/26/2022" | "YNN128902" |"AB45678901234XYZ" |  "ebhprod"  | "chrome" |  "SMRITIDHUNGANA" | "Smr1tiD@2022"|