Feature: Billing Feature

  @Regressions @BookLoad
  Scenario: Book a Load through Agents Portal in Staging Environment
    Given run test for "staging" on browser "chrome"
    And enter the url
    And login on the agents portal with username "hcic" and password "legendary"
    And navigate to the bookload page
    And enter billto as "ICARBGA" from as "HCI02147" and to as "HCI02257"
    And in references tab enter container as "fsd" chasis as "asf" and empty container as "xbc"
    And in references tab enter referenceOne as "bxc" referenceTwo as "bbn" and referenceThree as "nbvvc"
    And in financial information - customer charges tab enter freight charges as "1000"
    And in financial information - independent contractor pay tab enter freight charges as "100"
    And in financial information - customer charges tab enter fuel charges quantity as "1" and rate as "100"
    And in financial information - independent contractor pay tab enter fuel surcharges as "10"
    And in financial information - customer charges tab enter daily chasis charges quantity as "1" and rate as "100"
    And in financial information - independent contractor pay tab enter daily chasis charges as "10"
    And in operations information - pickup appointment enter date as "08/25/2021" and time as "10:00"
    And in operations information - actual enter date as "08/26/2021" and time as "10:00"
    Then click on book load and validate if the booking number got generated
    Then retrieve the load with loadnumber and validate the fields
    Then close all open browsers

    #42
  @Regression @BookLoadScenarioOutLine
  Scenario Outline: Book a Load through Agents Portal in Staging Environment
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
  # And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
   # And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
   # And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
   # And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
   # And in financial information - customer charges tab enter fuel charges quantity as <CustomerChargesFuelChargesQuantity> and rate as <CustomerChargesFuelChargesRate>
  #  And in financial information - independent contractor pay tab enter fuel surcharges as <IndependentContractorFuelSurCharges>
  #  And in financial information - customer charges tab enter daily chasis charges quantity as <CustomerChargesDailyChasisChargesQuantity> and rate as <CustomerChargesDailyChasisChargesRate>
  #  And in financial information - independent contractor pay tab enter daily chasis charges as <IndependentContractorDailyChasisCharges>
  #  And in operations information - pickup appointment enter date as <PickUpAppointmentDate> and time as <PickUpAppointmentTime>
  #  And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
  #  Then click on book load
  #  And click yes on alert
  #  And validate if the booking number got generated
  #  Then click on book load and validate if the booking number got generated
   # Then retrieve the load with loadnumber and validate the fields
    Then close all open browsers
    Examples:
      | environment | browser  | username | password | billto    | from        | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | CustomerChargesFuelChargesQuantity | CustomerChargesFuelChargesRate | IndependentContractorFuelSurCharges | CustomerChargesDailyChasisChargesQuantity | CustomerChargesDailyChasisChargesRate | IndependentContractorDailyChasisCharges | PickUpAppointmentDate | PickUpAppointmentTime | ActualDate   | ActualTime |
    # | "staging"   | "chrome"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "09/29/2021"          | "10:00"               | "09/30/2021" | "10:00"    |
    #  | "launch"   | "chrome"  | "pss"   | "taffy" | "ICARBGA" | "PSS00741 " | "PSS00742" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "01/25/2022"          | "03:00"               | "01/26/2022" | "03:00"    |
      | "launch"    | "chrome" | "pss"    | "taffy"  | "ICARBGA" | "WCH00741 " | "WCH00742" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "01/25/2022"          | "03:00"               | "01/26/2022" | "03:00"    |


  @Regressions @SuccessfullyCreatedBCPD
  Scenario Outline: Successfully Created BCPD Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @SuccessfullyCreatedPD
  Scenario Outline: Successfully Created PD Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @SuccessfullyCreatedBC
  Scenario Outline: Successfully Created BC Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer only and validate if the booking number got generated
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @ActualDateAndTimeBCPDErrorHandling
  Scenario Outline: Actual Date And Time BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    Then click on bill customer pay driver and validate if it shows actual date and time error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" |

  @Regressions @ActualDateAndTimeBCErrorHandling
  Scenario Outline: Actual Date And Time BC Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    Then click on bill customer only and validate if it shows actual date and time error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" |


  @Regressions @TractorOneChargeBCPDErrorHandling
  Scenario Outline: Tractor One Charge BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if it shows the tractor one charge error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |

  @Regressions @TractorOneChargePDErrorHandling
  Scenario Outline: Tractor One Charge PD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if it shows the tractor one charge error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @TractorOneIdBCPDErrorHandling
  Scenario Outline: Tractor One Id BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if it shows the tractor one id error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "09/26/2021" | "10:00"    |

  @Regressions @TractorOneIdPDErrorHandling
  Scenario Outline: Tractor One Id PD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if it shows the tractor one id error
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "09/26/2021" | "10:00"    |


  @Regressions @AcceptRiskWhenAcceptingOrderBCPDErrorHandling
  Scenario Outline: Accept Risk When Accepting Order BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @Regressions @AcceptRiskWhenAcceptingOrderBCPDErrorHandling
  Scenario Outline: Accept Risk When Accepting Order BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on pay driver only and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |



#  need to automate with correct data
  @Regressions @CashOnlyBCPDErrorHandling
  Scenario Outline: Cash Only BCPD Error Handling Scenario
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And pass the value <TractorOne> from the TractorOne field
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on bill customer pay driver and validate if accept risk when accepting order alert
    Then close all open browsers
    Examples:
      | environment | browser | username | password    | billto  | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | TractorOne          | ActualDate   | ActualTime |
      | "staging"   | "edge"  | "hcic"   | "legendary" | "MJCBT" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "00001PD-PJ DUNCAN" | "09/26/2021" | "10:00"    |


  @databaseconnect
  Scenario Outline: Connect to database
    Given Connect to <environment> and <tableName>
    Examples:
      | environment | tableName                                  |
      | "launch"    | "[EBHLaunch].[dbo].[ACCOUNTS_CLASS_CODES]" |
