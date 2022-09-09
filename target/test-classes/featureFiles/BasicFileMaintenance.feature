Feature: Basic File Maintenance Process Feature

    #20
  @Regression @AccountFile
  Scenario Outline: Validate Account File in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH Basic File Maintenance
    And Login to the Agents Portal with username <username> and password <password> for EBH Basic File Maintenance
    And Navigate to the Corporate Page on Main Menu and to the Basic File Maintenance page for EBH Basic File Maintenance
    And Navigate to the Account File page
    And Enter Agent/Location Code as <Agent/LocationCode> in Search In Box and click
    And Verify Bill-To is Checked on and get the Records Returned for <Agent/LocationCode>
    And Validate Records Returned on Bill-To with Database Record <environment> and <tableName> <AccountName> <AssertValue>
    And Click on SH/CON, Verify SH/CON is Checked on and get the Records Returned for <Agent/LocationCode>
    And Validate Records Returned on SH/CON with Database Record <environment> and <tableName> <AccountName> <AssertValue2>
    And Click Select of first column
    And Validate Table Header and Name on Account File Maintenance
    Then Close all open Browsers on EBH
    Examples:
      | Agent/LocationCode | environment | tableName                      | AccountName | AssertValue     | AssertValue2 | browser  | username         | password      |
    #  |      "AMAZON"      |  "ebhlaunch"   | "[EBHLaunch].[dbo].[ACCOUNTS]" |  "AMAZON %"  | "AMAZON BACKYARD LLC" |  "JRW01046"  | "chrome" |  "SMRITIDHUNGANA" | "Legendary@1" |
      | "BAY"              | "ebhlaunch" | "[EBHLaunch].[dbo].[ACCOUNTS]" | "BAY %"     | "BAY METAL INC" | "BJW00632"   | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |



       #21
  @Regression @LocationFile
  Scenario Outline: Validate Location File in Launch Environment
    Given Run Test for <environment> on Browser <browser> and Enter the url for EBH Basic File Maintenance
    And Login to the Agents Portal with username <username> and password <password> for EBH Basic File Maintenance
    And Navigate to the Corporate Page on Main Menu and to the Basic File Maintenance page for EBH Basic File Maintenance
    And Navigate to the Location File page
    And Enter Part of Location Name as <partOfLocationName> and click
    And Validate Records Returned for Location File Maintenance with Database Record <environment> and <tableName> <partOfLocationName1>
    And Click on Select and Validate Name, Status and AAR Rep
    Then Close all open Browsers on EBH
    Examples:
      | partOfLocationName | environment | tableName                       | partOfLocationName1 | browser  | username         | password      |
      | "MACAR"            | "ebhlaunch" | "[EBHLaunch].[dbo].[LOCATIONS]" | "%MACAR%"           | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
    #  |        "CLASS"     | "ebhlaunch" | "[EBHLaunch].[dbo].[LOCATIONS]" |      "%CLASS%"      | "chrome" |  "SMRITIDHUNGANA" | "Legendary@1" |




















