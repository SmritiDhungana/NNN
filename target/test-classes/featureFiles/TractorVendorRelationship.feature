Feature: Tractor Vendor Relationship Process Feature

  @Regression @TractorVendorRelationshipUnitNumber
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Unit Number
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Unit Number <unitNumber> and click on Search
   And Validate the Total Records Returned with database Records <environment> <tableName> <unitNumber>
  ##   And Validate the Total Records Returned with database Records DRIVERthreesixty <environment1> <tableName1>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors
    And Validate the Excel Search result Report with database Records <environment> <tableName> <unitNumber>
    And Click on View
  #  And Click on Edit
   # And Select the Edit button on Unit No that has Status as COMPLETED
  #  And Click on New
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | ownerId | vendor | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                           |
      | "172270"   | ""      | ""     | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[EQOwner]" |




  @Regression @TractorVendorRelationshipVendorCode
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Vendor Code
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Unit Number <unitNumber> and click on Search
    And Validate the Total Records Returned with database Records <environment> <tableName> <unitNumber>
  ##   And Validate the Total Records Returned with database Records DRIVERthreesixty <environment1> <tableName1>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors
    And Validate the Excel Search result Report with database Records <environment> <tableName> <unitNumber>
    And Click on View
  #  And Click on Edit
   # And Select the Edit button on Unit No that has Status as COMPLETED
  #  And Click on New
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | ownerId | vendorCode | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                           |
      | "172270"   | ""      | ""     | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[EQOwner]" |


  @Regression @TractorVendorRelationshipOwnerId
  Scenario Outline: (TSET-375) Validate Tractor Vendor Relationship with Owner Id
    Given Run Test for Environment <environment> on Browser <browser> for EBH Tractors and Enter the url
    And Login to the EBH with username <username> and password <password> for EBH Tractors
    And Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors
    Given Navigate to Tractor Vendor Relationship
    And Enter Unit Number <unitNumber> and click on Search
   And Validate the Total Records Returned with database Records <environment> <tableName> <unitNumber>
  ##  And Validate the Total Records Returned with database Records DRIVERthreesixty <environment1> <tableName1>
    And Click on Report Button and on SEARCH RESULTS
    And Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors
    And Validate the Excel Search result Report with database Records <environment> <tableName> <unitNumber>
    And Click on View
  #  And Click on Edit
   # And Select the Edit button on Unit No that has Status as COMPLETED
  #  And Click on New
    Then Close all open Browsers on EBH for Tractors
    Examples:
      | unitNumber | ownerId | vendor | environment  | environment1       | browser  | username     | password      | tableName                      | tableName1                           |
      | "172270"   | ""      | ""     | "ebhstaging" | "driver360staging" | "chrome" | "SmritiTest" | "Legendary@1" | "[ebh].[dbo].[TRACTOR_VENDOR]" | "[Driver360Staging].[dbo].[EQOwner]" |








