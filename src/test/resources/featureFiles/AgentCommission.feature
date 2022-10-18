Feature: Agent Settlement Adjustments Process Feature


  #18
  @Smoke @AgentCommissionMaintenance @bb
  Scenario Outline: Validate Agent Commission Maintenance in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to the Agent Commission Maintenance page
    And Popup Menu arises insert password "ecomat"
    And Click on ok btn
    And Enter Agent/Location Code as <AgentCode> and click
    And Validate Agent Code Description and Agent is active
  #  And Get Records of Agent Commission Maintenance
    And Validate Assert Values On Agent Commission Maintenance with Database Record <environment> and <tableName> <AgentCode> <assertValue> <assertValue1> <assertValue2>
    Then Close all open Browsers
    Examples:
      | AgentCode | environment | tableName                                           | assertValue                        | assertValue1                        | assertValue2              | browser  | username         | password      |
      | "YNNYNN"     | "ebhlaunch" | "[EBHLaunch].[dbo].[usp_GetAgentCommissionDetails]" | "DCC Daily Chas Chrg DCC RESIDUAL" | "BR BROKER COMMISSION BR ALL SPLIT" | "YARD Yard Pull YARD NET" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |


    #19
  @Regression @AgentCommissionCalculation
  Scenario Outline: Validate Agent Commission Calculation in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH
    And Login to the Agents Portal with username <username> and password <password>
    And Navigate to the Corporate Page on Main Menu and to the Settlements page
    And Navigate to Agent Commission Calculation
    And Validate you are on Agent Commission Calculation page
    And Enter Pro Number as <ProNumber>
    And Click on Calculate Commission
    And Validate Vendor Code and Total Commission
    And Validate the records returned on Agent Commission Calculation with Database Record <environment> and <tableName> <AgentCode> <OrderNum>
    And Click on Display Matrix
    And Popup Menu arises insert password "ecomat"
    And Click on Ok button
    And Validate Primary Vendor Name and Primary Vendor Code
    Then Close all open Browsers
    Examples:
      | ProNumber   | AgentCode | OrderNum | environment | browser  | username         | password      | tableName                                         |
      | "CVA193110" | "CVA"     | "193110" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAgentCommissionFinal]" |


















