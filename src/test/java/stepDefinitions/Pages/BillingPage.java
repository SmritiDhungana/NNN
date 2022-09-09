
package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillingPage extends BasePage {

    //address section
    public final By billto = By.id("billto_accountSelection1_actid");
    public final By from = By.id("from_accountSelection1_actid");
    public final By to = By.id("to_accountSelection1_actid");
    public final By billto_retrieve = By.cssSelector("td.cellborder table.topleft5px tbody:nth-child(1) tr:nth-child(2) td:nth-child(1) a:nth-child(1) > img:nth-child(1)");
    public final By from_retrieve = By.cssSelector("td.cellborder table.topleft5px tbody:nth-child(1) tr:nth-child(2) td:nth-child(1) a:nth-child(1) > img:nth-child(1)");
    public final By to_retrieve = By.cssSelector("td.cellborder table.topleft5px tbody:nth-child(1) tr:nth-child(2) td:nth-child(1) a:nth-child(1) > img:nth-child(1)");

    //references section
    public final By container = By.id("container");
    public final By chassis = By.id("chassis");
    public final By emptyContainer = By.id("emptycontainer");
    public final By referenceOne = By.id("reference1");
    public final By referenceTwo = By.id("reference2");
    public final By referenceThree = By.id("reference3");

    //notes section

    //financial information section - customer charges
    public final By freightChargesTotal = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_total\"]");
    public final By fuelSurchargesQuantity = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_qty\"]");
    public final By fuelSurchargesRate = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_rate\"]");

    public final By dailyChasisChargesQuantity = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_qty\"]");
    public final By dailyChasisChargesRate = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_rate\"]");

    //financial information section - independent contractor pay
    public final By freightChargesIndependentContractorPay = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay1\"]");
    public final By fuelSurChargesIndependentContractorPay = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay1\"]");
    public final By dailyChasisChargesIndependentContractorPay = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay1\"]");

    public final By freightChargesIndependentContractorPayTwo = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay2\"]");
    public final By fuelSurChargesIndependentContractorPayTwo = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay2\"]");
    public final By dailyChasisChargesIndependentContractorPayTwo = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay2\"]");

    public final By freightChargesIndependentContractorPayThree = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay3\"]");
    public final By fuelSurChargesIndependentContractorPayThree = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay3\"]");
    public final By dailyChasisChargesIndependentContractorPayThree = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay3\"]");

    public final By freightChargesIndependentContractorPayFour = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay1\"]");
    public final By fuelSurChargesIndependentContractorPayFour = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay1\"]");
    public final By dailyChasisChargesIndependentContractorPayFour = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay1\"]");

    public final By freightChargesIndependentContractorPayFive = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay2\"]");
    public final By fuelSurChargesIndependentContractorPayFive = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay2\"]");
    public final By dailyChasisChargesIndependentContractorPayFive = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay2\"]");

    public final By freightChargesIndependentContractorPaySix = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay3\"]");
    public final By fuelSurChargesIndependentContractorPaySix = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay3\"]");
    public final By dailyChasisChargesIndependentContractorPaySix = By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay3\"]");




    //tractors
    public final By tractorOne = By.id("divFinancialInformation_Financialinformation1_tractor1_ComboBoxTextBox");
    public final By tractorTwo = By.id("divFinancialInformation_Financialinformation1_tractor2_ComboBoxTextBox");
    public final By tractorThree = By.id("divFinancialInformation_Financialinformation1_tractor3_ComboBoxTextBox");
    public final By tractorFour = By.id("divFinancialInformation_Financialinformation1_tractor1_ComboBoxTextBox");
    //*[@id="divFinancialInformation_Financialinformation1_tractor1_ComboBoxTextBox"]
    public final By tractorFive = By.id("divFinancialInformation_Financialinformation1_tractor2_ComboBoxTextBox");
    //*[@id="divFinancialInformation_Financialinformation1_tractor2_ComboBoxTextBox"]
    public final By tractorSix = By.id("divFinancialInformation_Financialinformation1_tractor3_ComboBoxTextBox");
    //*[@id="divFinancialInformation_Financialinformation1_tractor3_ComboBoxTextBox"]




    //operation information section
    public final By pickupAppointmentDate = By.id("DeliveryDate");
    public final By pickupAppointmentTime = By.id("DeliveryTime");
    public final By actualDate = By.id("ActualDate");
    public final By actualTime = By.id("ActualTime");

    
    //agents remarks section
    public final By bookLoadButton = By.xpath("//*[@id=\"BillingCommandBar2_bu_bookload\"]");
    public final By billCustomerPayDriverButton = By.xpath("//*[@id=\"BillingCommandBar2_bu_billpay\"]");
    public final By billCustomerOnlyButton = By.id("BillingCommandBar2_bu_billonly");
    public final By payDriverOnlyButton = By.id("BillingCommandBar2_bu_payonly");
    public final By retrieveOrderButton = By.id("BillingCommandBar1_bu_retrieve");
    public final By billCreated = By.xpath("//*[@id=\"BillMessage\"]");

    //alerts - errors messages after biling screen BCPD, PD or BC
    public final By instructionalMessageActualDateAndTime = By.id("InstructionLabel");
    public final By actualDateErrorMessage = By.xpath("//*[@id=\"errormessage\"]/table/tbody/tr[1]/td[2]");
    public final By actualTimeErrorMessage = By.xpath("//*[@id=\"errormessage\"]/table/tbody/tr[2]/td[2]");

    public final By tractorOneChargeErrorMessage = By.xpath("//*[@id=\"errormessage\"]");

    public final By tractorOneIdErrorMessage = By.xpath("//*[@id=\"errormessage\"]");


    //alerts - positive messages
    public final By assignTractorToIdAlertYes = By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img");
    public final By alertForAcceptingRiskWhenSubmittingOrder = By.xpath("//*[@id=\"Table2\"]/tbody/tr[1]/td/p");

    // for Scan Documents
    public final By AlertYes = By.xpath("//*[@id=\"Yes\"]/img");
    public final By ScanDocuments = By.xpath("//*[@id=\"Scandocuments\"]");
    public final By CrossOnPopUpMenu = By.xpath("//*[@id=\"dwt-InstallBody\"]/div/div[1]/div");



    public final By retrieveOrder = By.xpath("//*[@id=\"BillingCommandBar1_bu_retrieve\"]");


    public BillingPage(WebDriver driver) {
        super(driver);
    }

}
