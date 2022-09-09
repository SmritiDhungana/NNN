Feature: Fuel Purchase Maintenance Validation Feature

  #45
  @Regression @FPMValidationTractorID  @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Earliest Date, Latest Date, State and Company is selected
  Validate Fuel Purchase Maintenance when Tractor Id, Earliest Date and Latest Date is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> Latest Date <latestDate> State <state> Company <company>
    And Verify the Total Records Returned with Database Record <environment> <tableName> <tractorID> <earliestDateDB> <latestDateDB> <state> <company> for TractorID
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |  latestDate  |earliestDateDB| latestDateDB | state | company | environment  | browser  | username     | password      | tableName                |
    #  | "55922"   | "08/10/2022" | "08/17/2022" | "2022-08-10" | "2022-08-17" | "PA"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
      | "11084"   | "08/16/2022" | "08/17/2022" | "2022-08-16" | "2022-08-17" | "AK"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |


  #46
  @Regression @FPMValidationTractorIDEarliestDate @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Earliest Date, State and Company is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> State <state> Company <company>
    And Verify the Total Records Returned with Database Record <environment> <tableName> <tractorID> Earliest Date <earliestDateDB> <state> <company> for TractorID
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |earliestDateDB| state | company | environment  | browser  | username     | password      | tableName                |
      | "55922"   | "08/10/2022" | "2022-08-10" | "PA"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
    #  | "11084"   | "08/16/2022" | "2022-08-16" | "AK"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |


   #47
  @Regression @FPMValidationTractorIDLatestDate @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Latest Date, State and Company is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Latest Date <latestDate> State <state> Company <company>
    And Verify the Total Records Returned with Database Record <environment> <tableName> <tractorID> Latest Date <latestDateDB> <state> <company> for TractorID
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID |  latestDate  | latestDateDB | state | company | environment  | browser  | username     | password      | tableName                |
      | "55922"   | "08/17/2022" | "2022-08-17" | "PA"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
    #  | "11084"   | "08/17/2022" | "2022-08-17" | "AK"  | "EVA"   | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |


    #48
  @Regression @FPMValidationIFTA @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Earliest Date, Latest Date, State, Company and IFTA is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> Latest Date <latestDate> State <state> Company <company> IFTA <iFTA>
    And Verify The Total Records Returned with Database Record <environment> <tableName> <earliestDateDB> <latestDateDB> <state> <company> <iFTA> for IFTA
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |  latestDate  |earliestDateDB| latestDateDB | state | company |  iFTA  | environment  | browser  | username     | password      | tableName                |
    #  | "55922"   | "08/10/2022" | "08/17/2022" | "2022-08-10" | "2022-08-17" | "NJ"  | "EVA"   |  "OO"  | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
      | "57763"   | "08/01/2022" | "08/22/2022" | "2022-08-01" | "2022-08-22" | "AK"  | "EVA"   | "IFTA" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

     #49
  @Regression @FPMValidationIFTAEarliestDate @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Earliest Date, State, Company and IFTA is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> State <state> Company <company> IFTA <iFTA>
    And Verify The Total Records Returned with Database Record <environment> <tableName> Earliest Date <earliestDateDB> <state> <company> <iFTA> for IFTA
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |earliestDateDB| state | company |  iFTA  | environment  | browser  | username     | password      | tableName                |
   #   | "57763"   | "08/01/2022" | "2022-08-01" | "AK"  | "EVA"   | "IFTA" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
      | "V99998"  | "08/22/2022" | "2022-08-22" | "TX"  | "WST"   | "IFTA" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

     #50
  @Regression @FPMValidationIFTALatestDate @q
  Scenario Outline: (FANDM-77) Validation of Fuel Purchase Maintenance when Tractor Id, Latest Date, State, Company and IFTA is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Latest Date <latestDate> State <state> Company <company> IFTA <iFTA>
    And Verify The Total Records Returned with Database Record <environment> <tableName> Latest Date <latestDateDB> <state> <company> <iFTA> for IFTA
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID |  latestDate  | latestDateDB | state | company |  iFTA  | environment  | browser  | username     | password      | tableName                |
      | "55922"   | "08/17/2022" | "2022-08-17" | "NJ"  | "EVA"   |  "OO"  | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
    #  | "57763"   | "08/22/2022" | "2022-08-22" | "AK"  | "EVA"   | "IFTA" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

    #51
  @Regression @FPMScenarioNew @q
  Scenario Outline: (FANDM-77) Validation of NEW on Fuel Purchase Maintenance Page
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Select the New Button, Enter required fields <company> <tractorID> <date> <location> <state> <gallons> <amount>
    And Click Cancel on Add Fuel Purchase
    And Re-Enter required fields <company> <tractorID> <date> <location> <state> <gallons> <amount>
    And Click Save on Add Fuel Purchase
    And Select <company> <tractorID> <date> <location> <state> <gallons> <amount> from dropdown
    And Validate the Records Returned with Database Record <environment> <tableName> and <tractorID> <company> <date> <location> <state> <gallons> <amount>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | company | tractorID | date         | location | state | gallons | amount | environment  | browser  | username     | password      | tableName                |
      |  "EVA"  |  "11084"  | "08/22/2022" |   "HCI"  |  "AK" |   "10"   | "3.2" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
    #  | "EVA"   | "55922"   | "08/17/2022" | "LKY"    | "PA"  | "4"     | "2"    | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

     #52
  @Regression @FPMScenarioEdit @q
  Scenario Outline: (FANDM-77) Validation of EDIT on Fuel Purchase Maintenance Page
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
   # And Click on Search Button
    And Select <company> <tractorID> <date> <location> <state> <gallons> <amount> from dropdown
    And Click on Edit, and edit the fields <location1> <gallons1> <amount1>
    And Click Save on Edit Fuel Purchase
    And Validate the Newly Edited Record with Database Record <environment> <tableName> and <tractorID> <company> <date> <location1> <state> <gallons1> <amount1>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | company | tractorID | date         | location | state | gallons | amount | location1 | gallons1 | amount1 | environment  | browser  | username     | password      | tableName                |
      |  "EVA"  |  "55922"  | "08/17/2022" |   "HCI"  | "PA"  |   "8"   | "4"    |    "AK"   |   "10"   |  "3.5"  | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
     # | "EVA"   | "55922"   | "08/17/2022" | "AK"     | "PA"  | "10"    | "3.5"  |   "HCI"   |   "8"    | "4"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

    #53
  @Regression @FPMScenarioReportTractorID  @q
  Scenario Outline: (FANDM-77) Validation of REPORT on Fuel Purchase Maintenance Page when Tractor ID is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
    #And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> Latest Date <latestDate> State <state> Company <company>
    And Click on Report Button and Click on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for FPM
    And Validate SEARCH RESULTS Excel Report with Database Record <environment> <tableName> <tractorID> <earliestDateDB> <latestDateDB> <state> <company>
  #  And Click on Report Button, and Click on ALL RECORDS
   # And Get ALL RECORDS Excel Report from Downloads for FPM
  #  And Validate ALL RECORDS Excel Report with Database Record <environment> <tableName>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |  latestDate  | earliestDateDB | latestDateDB |state | company | environment  | browser  | username     | password      | tableName                |
      |  "55922"  | "08/01/2022" | "08/22/2022" |  "2022-08-01"  | "2022-08-22" | "PA" |  "EVA"  | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |

    #54
  @Regression @FPMScenarioReportIFTA @q
  Scenario Outline: (FANDM-77) Validation of REPORT on Fuel Purchase Maintenance Page when IFTA is selected
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors
    And Navigate to Fuel Purchase Maintenance Page
  #  And Click on Search Button
    And Enter Tractor Id <tractorID> Earliest Date <earliestDate> Latest Date <latestDate> State <state> Company <company> IFTA <iFTA>
    And Click on Report Button and Click on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for FPM
    And Validate SEARCH RESULTS Excel Report with Database Record for IFTA <environment> <tableName> <earliestDateDB> <latestDateDB> <state> <company> <iFTA>
  #  And Click on Report Button, and Click on ALL RECORDS
  #  And Get ALL RECORDS Excel Report from Downloads for FPM
  #  And Validate ALL RECORDS Excel Report with Database Record <environment> <tableName>
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | tractorID | earliestDate |  latestDate  | earliestDateDB | latestDateDB | state | company | iFTA | environment  | browser  | username     | password      | tableName                |
    #  | "55922"   | "08/10/2022" | "08/17/2022" |  "2022-08-10"  | "2022-08-17" | "NJ"  | "EVA"   | "OO" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |
      | "57763"   | "08/01/2022" | "08/22/2022" |  "2022-08-01"  | "2022-08-22" | "AK"  | "EVA"   |"IFTA"| "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[FUEL_PUR]" |