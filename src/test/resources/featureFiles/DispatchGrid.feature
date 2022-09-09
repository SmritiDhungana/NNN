Feature: Dispatch Grid feature

  @Regressions @DispatchGrid
  Scenario Outline: Book a Load and go through Dispath Grid Status in Agents Portal in Staging Environment
    Given run test for <environment> on browser <browser>
    And enter the url
    And login on the agents portal with username <username> and password <password>
    And navigate to the bookload page
    And enter billto as <billto> from as <from> and to as <to>
    And in references tab enter container as <container> chasis as <chasis> and empty container as <emptycontainer>
    And in references tab enter referenceOne as <referenceOne> referenceTwo as <referenceTwo> and referenceThree as <referenceThree>
    And in financial information - customer charges tab enter freight charges as <CustomerChargesFreightCharges>
    And in financial information - independent contractor pay tab enter freight charges as <IndependentContractorFreightCharges>
    And in financial information - customer charges tab enter fuel charges quantity as <CustomerChargesFuelChargesQuantity> and rate as <CustomerChargesFuelChargesRate>
    And in financial information - independent contractor pay tab enter fuel surcharges as <IndependentContractorFuelSurCharges>
    And in financial information - customer charges tab enter daily chasis charges quantity as <CustomerChargesDailyChasisChargesQuantity> and rate as <CustomerChargesDailyChasisChargesRate>
    And in financial information - independent contractor pay tab enter daily chasis charges as <IndependentContractorDailyChasisCharges>
    And in operations information - pickup appointment enter date as <PickUpAppointmentDate> and time as <PickUpAppointmentTime>
    And in operations information - actual enter date as <ActualDate> and time as <ActualTime>
    Then click on book load and validate if the booking number got generated
    And navigate to Dispatch Grid screen
    And search with the order number created
    And assign tractor from the carrier lookup section
    Then assign different status and validate in status history
    Then close all open browsers
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chasis | emptycontainer | referenceOne | referenceTwo | referenceThree | CustomerChargesFreightCharges | IndependentContractorFreightCharges | CustomerChargesFuelChargesQuantity | CustomerChargesFuelChargesRate | IndependentContractorFuelSurCharges | CustomerChargesDailyChasisChargesQuantity | CustomerChargesDailyChasisChargesRate | IndependentContractorDailyChasisCharges | PickUpAppointmentDate | PickUpAppointmentTime | ActualDate   | ActualTime |
      | "staging"   | "chrome" | "hcic"   | "legendary" | "ICARBGA" | "HCI02147" | "HCI02257" | "fsd"     | "asf"  | "xbc"          | "bxc"        | "bbn"        | "nbvvc"        | "1000"                        | "100"                               | "1"                                | "100"                          | "10"                                | "1"                                       | "100"                                 | "10"                                    | "09/25/2021"          | "10:00"               | "09/26/2021" | "10:00"    |


