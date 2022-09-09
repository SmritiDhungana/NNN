Feature: Agent Settlement Inquiry Process Feature

     #13
  @Regression @AgentSettlementInquiryAgent
  Scenario Outline: Validate Agent on Agent Settlement Inquiry in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agent Settlement Inquiry
    And Enter Agent as <Agent> and click
    And Click on Search Button for Agent
    And Click on COM on Trans Type and validate total records returned on Agent as <Agent>
    And Validate Records Returned on Agent when COM on Trans Type with Database Record <environment> and <tableName> <Agent> <DateRangeBegin> <DateRangeEnd>
    And Click on ADJ on Trans Type and validate total records returned on Agent as <Agent>
    And Validate Records Returned on Agent when ADJ on Trans Type with Database Record <environment> and <tableName> <Agent> <DateRangeBegin> <DateRangeEnd>
    And Click on All on Trans Type and validate total records returned on Agent as <Agent>
    And Validate Records Returned on Agent when All on Trans Type with Database Record <environment> and <tableName> <Agent> <DateRangeBegin> <DateRangeEnd>
    Then Close all open Browsers
    Examples:
      | Agent | DateRangeBegin | DateRangeEnd | environment | browser  | username         | password      | tableName                                                     |
      | "YNN" | "2022-07-04"   | "2022-07-11" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAgentSettlementInquiryDataSearch]" |


     #14
  @Regression @AgentSettlementInquiryVendor
  Scenario Outline: Validate Vendor on Agent Settlement Inquiry in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agent Settlement Inquiry
    And Enter Vendor as <VendorCode> and click
    And Click on Search Button for Vendor
    And Click on COM on Trans Type and validate total records returned on Vendor as <VendorCode>
    And Validate Records Returned on Vendor when COM on Trans Type with Database Record <environment> and <tableName> <VendorCode> <DateRangeBegin> <DateRangeEnd>
    And Click on ADJ on Trans Type and validate total records returned on Vendor as <VendorCode>
    And Validate Records Returned on Vendor when ADJ on Trans Type with Database Record <environment> and <tableName> <VendorCode> <DateRangeBegin> <DateRangeEnd>
    And Click on All on Trans Type and validate total records returned on Vendor as <VendorCode>
    And Validate Records Returned on Vendor when All on Trans Type with Database Record <environment> and <tableName> <VendorCode> <DateRangeBegin> <DateRangeEnd>
    Then Close all open Browsers
    Examples:
      | VendorCode | DateRangeBegin | DateRangeEnd | environment | browser  | username         | password      | tableName                                                     |
      | "10008"    | "2022-07-04"   | "2022-07-11" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAgentSettlementInquiryDataSearch]" |


    #15
  @Regression @AgentSettlementInquiryPayCodes
  Scenario Outline: Validate Pay Codes on Agent Settlement Inquiry in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agent Settlement Inquiry
    And Enter Pay Codes as <PayCodes> and click
    And Click on Search Button for Pay Codes
    And Click on COM on Trans Type and validate total records returned on Pay Codes as <PayCodes>
    And Validate Records Returned on Pay Codes when COM on Trans Type with Database Record <environment> and <tableName> <PayCodes> <DateRangeBegin> <DateRangeEnd>
    And Click on ADJ on Trans Type and validate total records returned on Pay Codes as <PayCodes>
    And Validate Records Returned on Pay Codes when ADJ on Trans Type with Database Record <environment> and <tableName> <PayCodes> <DateRangeBegin> <DateRangeEnd>
    And Click on All on Trans Type and validate total records returned on Pay Codes as <PayCodes>
    And Validate Records Returned on Pay Codes when All on Trans Type with Database Record <environment> and <tableName> <PayCodes> <DateRangeBegin> <DateRangeEnd>
    Then Close all open Browsers
    Examples:
      | PayCodes | DateRangeBegin | DateRangeEnd | environment | browser  | username         | password      | tableName                                                     |
      | "AA"     | "2022-07-04"   | "2022-07-11" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAgentSettlementInquiryDataSearch]" |

























