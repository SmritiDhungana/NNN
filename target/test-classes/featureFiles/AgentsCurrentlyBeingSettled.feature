Feature: Agent Settlement Adjustments Process Feature


   #16
  @Regression @AgentsCurrentlyBeingSettledVendorCode
  Scenario Outline: Validate Vendors Currently Being Settled in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agents Currently Being Settled
    And Validate the records returned on Agents Currently Being Settled
    And Click on Filter Icon on Settling Vendor Code
    And Enter Vendor Code as <VendorCode>
    And Validate total Records returned for Vendor Code with Database Record <environment> and <tableName> <VendorCode>
    Then Close all open Browsers
    Examples:
      | VendorCode | environment | browser  | username         | password      | tableName                            |
      | "21006"    | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[SETTLING_TABLE]" |



    #17
  @Regression @AgentsCurrentlyBeingSettledAgentCode
  Scenario Outline: Validate Agents Currently Being Settled in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agents Currently Being Settled
    And Validate the records returned on Agents Currently Being Settled
    And Click on Filter Icon on Settling Agent Code
    And Enter Agent Code as <AgentCode>
    And Validate total Records returned for Agent Code with Database Record <environment> and <tableName> <AgentCode>
    Then Close all open Browsers
    Examples:
      | AgentCode | environment | browser  | username         | password      | tableName                            |
      | "ABC"     | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[SETTLING_TABLE]" |

















