Feature: AAR Agent Management File Process Feature

    #22
  @Regression @AARAgentManagementFile
  Scenario Outline: Validate AAR Agent Management File
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Click on Search Icon on Agent Code
    And Get the details of Agent Code as <AgentCode>
    And Validate the records returned for AAR Agent Management with Database Record <environment> and <tableName> <AgentCode>
    And Click on edit on Action field
    And Validate you are on AAR Agent Management Detail Page
    And Validate Agent Code and Vendor Code
    And Click on Cancel
    And Validate the records returned for Active Agents not Assigned with Database Record <environment> and <tableName1> <AgentCode>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | AgentCode | AgentCode | environment  | browser  | username     | password      | tableName                                           | tableName1                                    |
   #   | "ANC"     | "ACV"   | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[usp_GetAARagentManagementDetailInfo]" | "[EBHLaunch].[dbo].[usp_GetAARagentManagementData]" |
      | "ANC"     | "ACV"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[usp_GetAARagentManagementDetailInfo]" | "[EBH].[dbo].[usp_GetAARagentManagementData]" |



  #23
  @AARReassignment
  Scenario Outline: [JIRA] (SET-2195) Create Automation - V1.10 - Add Logic to Check Settling Table Upon AAR Reassignment
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Determine an agent to Reassign is on the Settling Table with the Current Settling Week <environment> <tableName> and <VendorCode> <AcctingWk>
    And Click on Search Icon on Vendor Code
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
    And Validate the AAR Login <aarLogin1> for Vendor Code <VendorCode> on AAR Agent Management Detail Page
    And Click on edit on Action field
    And Reassign AAR Login to your login from the AAR Login <aarLogin> dropdown
    And Select CANCEL
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
    And Check to make sure the Vendor Code <VendorCode> did not change AAR assignment <aarLogin1> upon a CANCEL
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a CANCEL
    And Click on edit on Action field
    And Reassign AAR Login to your Login from the AAR Login <aarLogin> dropdown
    And Select SAVE, Click on NO
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
    And Check to make sure the Vendor Code <VendorCode> did not change AAR assignment <aarLogin1> upon a SAVE, NO
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a SAVE, NO
    And Click on edit on Action field
    And Reassign AAR Login to your Login from the AAR Login <aarLogin> dropdown
    And Select SAVE, Click on YES
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
    And Check to make sure the Vendor Code <VendorCode> did change AAR assignment <aarLogin> upon a SAVE, YES
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a SAVE, YES
    And Select Settlements in the Breadcrumbs
  #  And Choose Agent Settling and enter password.  Choose Continue.  Select OK <Password>
    And Ensure the new Agent is included in the Settlement Summary <VendorCode>
    And Choose Agent Settling and enter password.  Choose Reset.  Choose Current or Previous Week (whatever week you did not check above in Continue).  Select OK <Password>
    And Ensure the new Agent is included in the Settlement Summary <VendorCode>
    And Return to the AAR Agent Management form and check filtering to make sure it retains filters when selecting different options <VendorCode> <aarLogin>
    And REVERT back AAR reassignment back to original AAR before completing testing <aarLogin1>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | VendorCode | aarLogin         | aarLogin1 | AcctingWk | Password | environment | tableName                            | browser  | username         | password      |
    #  |   "10080"   | "SMRITIDHUNGANA" |  "TAWTEST" | "17" | "settle" |  "ebhlaunch"   | "[EBHLaunch].[dbo].[SETTLING_TABLE]" | "chrome" |  "SMRITIDHUNGANA" |  "Legendary@1" |
    #  | "30027"    | "SMRITIDHUNGANA" | "SJAGGI"  | "39"      | "settle" | "ebhlaunch" | "[EBHLaunch].[dbo].[SETTLING_TABLE]" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |
      | "7517"     | "SMRITIDHUNGANA" | "SJAGGI"  | "33"      | "settle" | "ebhlaunch" | "[EBHLaunch].[dbo].[SETTLING_TABLE]" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" |



    #24
  @Regressions @AARReassignmentAgentThatSharesVendorCodeWithOtherAgents
  Scenario Outline: [JIRA] (SET-2298) Automation Update for V1.10 - Add Logic to Check Settling Table Upon AAR Reassignment (Agent that shares a Vendor Code with other Agents)
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Determine an agent to Reassign is on the Settling Table with the Current Settling Week <environment> <tableName> and <VendorCode> <AcctingWk>
    And Click on Search Icon on Vendor Code
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
  #  And Validate the AAR Login for Vendor Code <VendorCode> on AAR Agent Management Detail Page
    And Validate the AAR Login <aarLogin1> for Vendor Code <VendorCode> on AAR Agent Management Detail Page
    And Click on edit on Action field
    And Reassign AAR Login to your login from the AAR Login <aarLogin> dropdown
    And Select CANCEL
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
    And Check to make sure the Vendor Code <VendorCode> did not change AAR assignment <aarLogin1> upon a CANCEL
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a CANCEL
    And Click on edit on Action field
    And Reassign AAR Login to your Login from the AAR Login <aarLogin> dropdown
    And Select SAVE, Click on NO
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
  #  And Check to make sure the Vendor Code <VendorCode> did not change AAR assignment <aarLogin1> upon a SAVE, NO
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a SAVE, NO
    And Click on edit on Action field
    And Reassign AAR Login to your Login from the AAR Login <aarLogin> dropdown
    And Select SAVE, Click on YES on Confirmation
    And Get the details of Vendor Code as <VendorCode> on AAR Agent Management
  #  And Check to make sure the Vendor Code <VendorCode> did change AAR assignment <aarLogin> upon a SAVE, YES
    And Update Vendor Code and run the following SQL <environment> and <tableName> <VendorCode> upon a SAVE, YES
    And Select Settlements in the Breadcrumbs
    And Choose Agent Settling and enter password, Choose Continue, Select OK <Password>
    And Ensure the new Agent is included in the Settlement Summary <VendorCode>
    And Choose Agent Settling and enter password.  Choose Reset.  Choose Current or Previous Week (whatever week you did not check above in Continue).  Select OK <Password>
    And Ensure the new Agent is included in the Settlement Summary <VendorCode>
    And Return to the AAR Agent Management form and check filtering to make sure it retains filters when selecting different options <VendorCode> <aarLogin>
    And REVERT back AAR reassignment back to original AAR Login before completing testing <aarLogin1>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | VendorCode | aarLogin         | aarLogin1 | AcctingWk | Password | environment | browser  | username         | password      | tableName                            |
      | "7517"     | "SMRITIDHUNGANA" | "SJAGGI"  | "33"      | "settle" | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[SETTLING_TABLE]" |
    #  | "7517"     | "SMRITIDHUNGANA" | "KUNAL"  | "31"      | "settle" | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[SETTLING_TABLE]" |


    # 25
  @Regression @AARAgentManagementWeeklyCutoffDay/Time
  Scenario Outline: Validate records selecting CUTOFF DAY/TIME in AAR Agent Management Table
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Click on Search Icon on Weekly Cutoff Day as <WeeklyCutoffDay>
    And Click on Search Icon on Weekly Cutoff Time as <WeeklyCutoffTime>
    And Get the details based on Weekly Cutoff Day and Time on AAR Agent Management
    And Validate Records Returned with Database Setting Table <environment> and <tableName> <WeeklyCutoffDay> <WeeklyCutoffTime>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | WeeklyCutoffDay | WeeklyCutoffTime | environment | browser  | username         | password      | tableName                                   |
      | "WEDNESDAY"     | "06:00 AM"       | "ebhlaunch" | "chrome" | "SMRITIDHUNGANA" | "Legendary@1" | "[EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]" |




       # 55
  @Regression @TractorAARLogin
  Scenario Outline: (SET-2479) Validate, Addition of AGENT_STL_TRAC_AAR for AAR Tractor Assignment
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Confirm Tractor AAR Login column is displayed and the data represented in the Tractor AAR Login is correct by running the following SQL <environment> and <tableName>
    And Select Tractor AAR Login Agent Code <agentCode> and Click on Edit
    And Upon Edit, Confirm Tractor AAR Login column is displayed, Confirm Tractor AAR Login is not blank, Verify Information to the right of the Tractor AAR Login Dropdown when selected
    And Confirm choices for Tractor AAR Login display an intuitive drop down and contains the following data Run SQL <environment> and <tableName>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | agentCode | environment  | browser  | username     | password      | tableName                             | tableName1                             |
      | "SAV"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_SETTLING_TABLE]" |

    # 56
  @Regression @TractorAARLoginNoOneIsCurrentlySettling
  Scenario Outline: (SET-2479) No-one else is currently settling agent, Tractor AAR Login
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Select Tractor AAR Login Agent Code <agentCodeNoOneIsCurrentlySettling> and Click on Edit
    And TEST No-one else is currently settling agent, Update Tractor AAR Login <tractorAARLoginUpdate>, click Save
    And Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, regardless if the agent is verified or not, Run the SQL <environment> <tableName1> <agentCodeNoOneIsCurrentlySettling> <tableName> and update TRACTOR_SETTLING_AGENT_CODE with the Agent Code you are testing with
    And Revert Tractor AAR assignment back to original assignment <tractorAARLogin>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | tractorAARLogin | tractorAARLoginUpdate | agentCodeNoOneIsCurrentlySettling | environment  | browser  | username     | password      | tableName                             | tableName1                             |
      | "TESTTINA1"     | "SMRITITEST"          | "AWG"                             | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_SETTLING_TABLE]" |


     # 57
  @Regression @TractorAARLoginAnotherAARisSettlingTheAgentVerified
  Scenario Outline: (SET-2479) Another AAR is settling the agent, the AGENT is VERIFIED
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Select Tractor AAR Login Agent Code <agentCode> and Click on Edit
   # And TEST Another AAR is settling the agent, the AGENT is VERIFIED, Update Tractor AAR Login <tractorAARLoginUpdate>, click Save, Select No
   # And Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is verified, Run SQL <environment> <tableName1> <agentCode> <tableName>
    And TEST Another AAR is settling the agent, the AGENT is VERIFIED, Update Tractor AAR Login <tractorAARLoginUpdate>, click Save, Select Yes
    And Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is verified, Run SQL <environment> <tableName1> <agentCode> <tableName>
    And Revert Tractor AAR Assignment back to original assignment, the AGENT is VERIFIED <tractorAARLogin>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | tractorAARLogin | tractorAARLoginUpdate | agentCode | environment  | browser  | username     | password      | tableName                             | tableName1                             |
      | "TESTTINA1"     | "SMRITITEST"          | "SAV"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_SETTLING_TABLE]" |


     # 58
  @Regression @TractorAARLoginAnotherAARisSettlingTheAgentNOTVerified
  Scenario Outline: (SET-2479) Another AAR is settling the agent, the AGENT is NOT VERIFIED
    Given Run Test for <environment> on Browser <browser> for AAR Agent Management and Enter the url
    And Login to the Agents Portal with username <username> and password <password> for AAR Agent Management
    And Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management
    And Navigate to the AAR Agent Management File page
    And Select Tractor AAR Login Agent Code <agentCode> and Click on Edit
  #  And TEST Another AAR is settling, the agent is not verified, Update Tractor AAR Login <tractorAARLoginUpdate>, click Save, Select No
  #  And Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is not verified, Run SQL <environment> <tableName1> <agentCode> <tableName>
    And TEST Another AAR is settling, the agent is not verified, Update Tractor AAR Login <tractorAARLoginUpdate>, click Save, Select Yes
    And Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is not verified, Run SQL <environment> <tableName1> <agentCode> <tableName>
    And Revert Tractor AAR Assignment back to original assignment, the agent is not verified <tractorAARLogin>
    Then Close all open Browsers on EBH for AAR Agent Management
    Examples:
      | tractorAARLogin | tractorAARLoginUpdate | agentCode | environment  | browser  | username     | password      | tableName                             | tableName1                             |
      | "ANIL"          | "SMRITITEST"          | "BCO"     | "ebhstaging" | "chrome" | "SmritiTest" | "Legendary@1" | "[EBH].[dbo].[AGENT_SETTLEMENT_INFO]" | "[EBH].[dbo].[TRACTOR_SETTLING_TABLE]" |
















