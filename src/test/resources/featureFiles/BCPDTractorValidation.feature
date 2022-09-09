Feature: BCPD Tractor Validation Feature


    #43a
  @Regression @BCPDCreateBillPositive
  Scenario Outline: Bill Customer and Pay Driver Create Bill (Positive Scenario)
    Given run test for <environment> on browser <browser> for Tractor Validation
    And enter the url for Tractor Validation
    And login on the Agents Portal with username <username> and password <password>
    And navigate to the Bookload Page
    And enter Billto as <billto> From as <from> and To as <to>
    And in References Tab enter Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate>
    And in Financial Information - Customer Charges Tab enter Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - Actual enter Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on Book Load and validate if the Booking Number got generated
    And click on Bill Customer Pay Driver Button
    Then close all Open Browsers for Tractor Validation
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix | TractorOnee | TractorTwoo | TractorThreee | TractorFourr | TractorFivee | TractorSixx | environment | tableName              | OrdLoc | billto    | from       | to         |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"      | "14"  | "11"  | "3"    | "57763"    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    | "181198"    | "WCB"       | "WCB"         | ""           | "V99999"     | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |


     #43b
  @Regression @BCPDCreateBillNeg
  Scenario Outline: Bill Customer and Pay Driver Create Bill (Negative Scenario)
    Given run test for <environment> on browser <browser> for Tractor Validation
    And enter the url for Tractor Validation
    And login on the Agents Portal with username <username> and password <password>
    And navigate to the Bookload Page
    And enter Billto as <billto> From as <from> and To as <to>
    And in References Tab enter Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate>
    And in Financial Information - Customer Charges Tab enter Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - Actual enter Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter Delivery Appointment Date as <DADate> and Time as <DATime>
  #  Then click on Book Load and validate if the Booking Number got generated for BCPD Negative Scenario <TractorOnee> <TractorThreee>
   # And click on Bill Customer Pay Driver Button for Negative Scenario
  #  And enter Valid Tractors for BCPD (whose Tractor Unit No. and Vendor Codes are available) <TractorOnee> <TractorTwoo> <TractorThreee> <TractorFourr> <TractorFivee> <TractorSixx>
    Then close all Open Browsers for Tractor Validation
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne          | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree        | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix | TractorOnee | TractorTwoo | TractorThreee | TractorFourr | TractorFivee | TractorSixx | environment | tableName              | OrdLoc | billto    | from       | to         |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "00001PD-PJ DUNCAN" | "14"  | "11"  | "3"    | "181198"   | "15"  | "10"  | "3"    | "VM9999"     | "15"    | "10"    | "3"      | "181203"    | "13"   | "10"   | "2"     | "V99999"    | "14"   | "11"   | "3"     | "V99999"   | "15"  | "10"  | "3"    | "WCB"       | ""          | "WCB"         | ""           | ""           | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"               | "14"  | "11"  | "3"    | "57763"    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    | "181198"    | "WCB"       | "WCB"         | ""           | "V99999"     | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "00001PD-PJ DUNCAN" | "14"  | "11"  | "3"    | "57763"    | "15"  | "10"  | "3"    | "00001PD-PJ DUNCAN" | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    | "57763"     | "WCB"       | "WCB"         | ""           | "V99999"     | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |



  #43ab
  @Regression @BCPDOrderNumOnDB
  Scenario Outline: Bill Customer and Pay Driver Bill Created validate on Database
    Given validate the created Order Number for Bill Customer Pay Driver with Database Record <environment> and <tableName> <OrderNo> <OrdLoc> <billto> <from> <to>
    Examples:
      | environment  | tableName              | OrderNo  | OrdLoc | billto    | from       | to         |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123607" | "HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123613" | "HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123622" | "HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123625" | "HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123626" | "HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123630" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123631" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123905" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123906" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "124021" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "124024" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |

      #44a
  @Regression @PDOnlyCreateBillPositive
  Scenario Outline: Pay Driver Only Create Bill (Positive Scenario)
    Given run test for <environment> on browser <browser> for Tractor Validation
    And enter the url for Tractor Validation
    And login on the Agents Portal with username <username> and password <password>
    And navigate to the Bookload Page
    And enter Billto as <billto> From as <from> and To as <to>
    And in References Tab enter Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate>
    And in Financial Information - Customer Charges Tab enter Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - Actual enter Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter Delivery Appointment Date as <DADate> and Time as <DATime>
    Then click on Book Load and validate if the Booking Number got generated
    And click on Pay Driver Only Button
    Then close all Open Browsers for Tractor Validation
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix | TractorOnee | TractorTwoo | TractorThreee | TractorFourr | TractorFivee | TractorSixx | environment | tableName              | OrdLoc | billto    | from       | to         |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"      | "14"  | "11"  | "3"    | "181198"   | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "181203"    | "13"   | "10"   | "2"     | "V99999"    | "14"   | "11"   | "3"     | "V99999"   | "15"  | "10"  | "3"    | "181198"    | "WCB"       | "WCB"         | ""           | "V99999"     | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "WCB"      | "14"  | "11"  | "3"    | "57763"    | "15"  | "10"  | "3"    | "WCB"        | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    | "181198"    | "WCB"       | "WCB"         | ""           | "V99999"     | ""          | "staging"   | "[EBH].[dbo].[ORDERS]" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |



     #44b
  @Regression @PDOnlyCreateBillNeg
  Scenario Outline: Pay Driver Only Create Bill (Negative Scenario)
    Given run test for <environment> on browser <browser> for Tractor Validation
    And enter the url for Tractor Validation
    And login on the Agents Portal with username <username> and password <password>
    And navigate to the Bookload Page
    And enter Billto as <billto> From as <from> and To as <to>
    And in References Tab enter Container as <container> Chassis as <chassis> and Empty Container as <emptycontainer>
    And in References Tab enter ReferenceOne as <referenceOne> ReferenceTwo as <referenceTwo> and RefOne as <refOne>
    And in Financial Information - Customer Charges Tab enter Freight Charges as <FreightCharges>
    And in Financial Information - Customer Charges Tab enter Fuel Surcharges Quantity as <FuelSurchargesQuantity> and Rate as <FuelSurchargesRate>
    And in Financial Information - Customer Charges Tab enter Daily Chassis Charges Quantity as <DailyChassisChargesQuantity> and Rate as <DailyChassisChargesRate>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorOne> from the TractorOne Field, Freight Charges <FCOne>, Fuel Surcharges <FSOne> and Daily Chassis Charges <DCCOne>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorTwo> from the TractorTwo Field, Freight Charges <FCTwo>, Fuel Surcharges <FSTwo> and Daily Chassis Charges <DCCTwo>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorThree> from the TractorThree Field, Freight Charges <FCThree>, Fuel Surcharges <FSThree> and Daily Chassis Charges <DCCThree>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFour> from the TractorFour Field, Freight Charges <FCFour>, Fuel Surcharges <FSFour> and Daily Chassis Charges <DCCFour>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorFive> from the TractorFive Field, Freight Charges <FCFive>, Fuel Surcharges <FSFive> and Daily Chassis Charges <DCCFive>
    And in Financial Information - Independent Contractor Pay Tab pass the value <TractorSix> from the TractorSix Field, Freight Charges <FCSix>, Fuel Surcharges <FSSix> and Daily Chassis Charges <DCCSix>
    And in Operations Information - Actual enter Date as <ActualDate> and Time as <ActualTime>
    And in Operations Information - enter Delivery Appointment Date as <DADate> and Time as <DATime>
  #  Then click on Book Load and validate if the Booking Number got generated for Pay Driver Only Negative Scenario <TractorOnee> <TractorThreee>
  #  And click on Pay Driver Only Button for Negative Scenario
  #  And enter Valid Tractors for Pay Driver Only, whose Tractor Unit No and Vendor Codes are available <TractorOnee> <TractorTwoo> <TractorThreee> <TractorFourr> <TractorFivee> <TractorSixx>
    Then close all Open Browsers for Tractor Validation
    Examples:
      | environment | browser  | username | password    | billto    | from       | to         | container | chassis | emptycontainer | referenceOne | referenceTwo | refOne  | FreightCharges | ActualDate   | ActualTime | DADate       | DATime  | FuelSurchargesQuantity | FuelSurchargesRate | DailyChassisChargesQuantity | DailyChassisChargesRate | TractorOne          | FCOne | FSOne | DCCOne | TractorTwo | FCTwo | FSTwo | DCCTwo | TractorThree        | FCThree | FSThree | DCCThree | TractorFour | FCFour | FSFour | DCCFour | TractorFive | FCFive | FSFive | DCCFive | TractorSix | FCSix | FSSix | DCCSix | TractorOnee | TractorTwoo | TractorThreee | TractorFourr | TractorFivee | TractorSixx |
    #  | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "00001PD-PJ DUNCAN" | "14"  | "11"  | "3"    | "181198"   | "15"  | "10"  | "3"    | "VM9999"            | "15"    | "10"    | "3"      | "181203"    | "13"   | "10"   | "2"     | "V99999"    | "14"   | "11"   | "3"     | "V99999"   | "15"  | "10"  | "3"    | "WCB"       | ""          | "WCB"         | ""           | ""           | ""          |
      | "staging"   | "chrome" | "hci"    | "legendary" | "TESTABC" | "HCI02147" | "HCI02257" | "fsd"     | "asf"   | "xbc"          | "bxc"        | "bbn"        | "nbvvc" | "1000"         | "08/02/2022" | "10:00"    | "08/02/2022" | "10:00" | "1"                    | "100"              | "1"                         | "10"                    | "00001PD-PJ DUNCAN" | "14"  | "11"  | "3"    | "57763"    | "15"  | "10"  | "3"    | "00001PD-PJ DUNCAN" | "15"    | "10"    | "3"      | "WCB"       | "13"   | "10"   | "2"     | "WCB"       | "14"   | "11"   | "3"     | "57763"    | "15"  | "10"  | "3"    | "57763"     | "WCB"       | "WCB"         | ""           | "V99999"     | ""          |


    #44ab
  @Regression @PDOnlyOrderNumOnDB
  Scenario Outline: Pay Driver Only Bill Created validate on Database
    Given validate the created Order Number for Pay Driver Only with Database Record <environment> and <tableName> <OrderNo> <OrdLoc> <billto> <from> <to>
    Examples:
      | environment  | tableName              | OrderNo  | OrdLoc | billto    | from       | to         |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123608" |"HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" |"[EBH].[dbo].[ORDERS]"|"123614" |"HCI"| "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123624" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
     # | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123629" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123907" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
    #  | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "123908" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "124022" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |
      | "ebhstaging" | "[EBH].[dbo].[ORDERS]" | "124028" | "HCI"  | "TESTABC" | "HCI02147" | "HCI02257" |






