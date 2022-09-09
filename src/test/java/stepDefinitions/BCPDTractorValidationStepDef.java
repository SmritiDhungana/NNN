package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.CommonUtils.CommonUtils;
import stepDefinitions.Pages.BillingPage;
import stepDefinitions.Pages.LoginPage;
import stepDefinitions.Pages.MainMenuPage;
import stepDefinitions.Pages.RetrieveOrderPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class BCPDTractorValidationStepDef {
    WebDriver driver;
    String url = "";
    String bookLoadNumber = "";

    String usernameExpctd = "";
    String billToExpctd = "";
    String fromExpctd = "";
    String toExpctd = "";
    String containerExpctd = "";
    String chassisExpctd = "";
    String emptyContainerExpctd = "";
    String referenceOneExpctd = "";
    String referenceTwoExpctd = "";
    String referenceThreeExpctd = "";
    String freightChargesTotalRPExpctd = "";
    String freightChargesIndependentContractorPayRPExpctd = "";
    String fuelChargesQuantityRPExpctd = "";
    String fuelChargesRateRPExpctd = "";
    String fuelChargesIndependentContractorPayRPExpctd = "";
    String dailyChassisChargesQuantityRPExpctd = "";
    String dailyChassisChargesRateRPExpctd = "";
    String dailyChassisChargesIndependentContractorPayRPExpctd = "";
    String pickupAppointmentDateExpctd = "";
    String pickupAppointmentTimeExpctd = "";
    String actualDateExpctd = "";
    String deliveryAppointmentDateExpctd = "";


    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    CommonUtils commonUtils = new CommonUtils();
    BillingPage billingPage = new BillingPage(driver);
    RetrieveOrderPage retrieveOrderPage = new RetrieveOrderPage(driver);
    MainMenuPage mainMenuPage = new MainMenuPage(driver);
    Logger log = Logger.getLogger("BCPDTractorValidationStepDef");
    private static Statement stmt;


    @Given("^run test for \"([^\"]*)\" on browser \"([^\"]*)\" for Tractor Validation$")
    public void runTestForOnBrowser_for_Tractor_Validation(String environment, String browser) {

        BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
        url = browserDriverInitialization.getDataFromPropertiesFile(environment, browser);
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            driver = new EdgeDriver();
        }
    }

    @Given("^enter the url for Tractor Validation$")
    public void enter_the_url_for_Tractor_Validation() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.get(url);
    }

    @And("^login on the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void log_in_On_The_Agents_Portal_With_Username_And_Password(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        usernameExpctd = username;
        driver.findElement(loginPage.username).sendKeys(usernameExpctd.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.password));
        driver.findElement(loginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.loginButton));
        driver.findElement(loginPage.loginButton).click();

        driver.findElement(loginPage.Alert).isDisplayed();
        driver.findElement(loginPage.EnterFirstNameandLastName).sendKeys("smriti dhungana");
        driver.findElement(loginPage.Ok).click();
        driver.findElement(By.xpath("//*[@id=\"closePopup\"]/img")).click();
    }

    @Then("^navigate to the Bookload Page$")
    public void navigate_to_the_Bookload_Page() {
        driver.findElement(mainMenuPage.operations).click();
        driver.findElement(mainMenuPage.biling).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Then("^close all Open Browsers for Tractor Validation$")
    public void close_All_Open_Browsers_for_Tractor_Validation() {
        driver.close();
        driver.quit();
    }


    //............................................/ #T1a @BCPDCreateBillPositive /................................................//

    @And("^enter Billto as \"([^\"]*)\" From as \"([^\"]*)\" and To as \"([^\"]*)\"$")
    public void enterBilltoAsFromAsAndToAs(String billto, String from, String to) {
        billToExpctd = billto;
        driver.findElement(billingPage.billto).sendKeys(billto);
        driver.findElement(billingPage.billto_retrieve).click();

        fromExpctd = from;
        driver.findElement(billingPage.from).sendKeys(from);
        driver.findElement(billingPage.from_retrieve).click();

        toExpctd = to;
        driver.findElement(billingPage.to).sendKeys(to);
        driver.findElement(billingPage.to_retrieve).click();
    }


    @And("^in References Tab enter Container as \"([^\"]*)\" Chassis as \"([^\"]*)\" and Empty Container as \"([^\"]*)\"$")
    public void inReferencesTabEnterContainerAsChassisAsAndEmptyContainerAs(String container, String chassis, String emptyContainer) {
        containerExpctd = container + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.container).sendKeys(containerExpctd);

        chassisExpctd = chassis + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.chassis).sendKeys(chassisExpctd);

        emptyContainerExpctd = emptyContainer + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.emptyContainer).sendKeys(emptyContainerExpctd);
    }

    @And("^in References Tab enter ReferenceOne as \"([^\"]*)\" ReferenceTwo as \"([^\"]*)\" and RefOne as \"([^\"]*)\"$")
    public void inReferencesTabEnterReferenceOneAsReferenceTwoAsAndReferenceOneAs(String referenceOne, String referenceTwo, String refOne) {
        referenceOneExpctd = referenceOne + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceOne).sendKeys(referenceOneExpctd);

        referenceTwoExpctd = referenceTwo + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceTwo).sendKeys(referenceTwoExpctd);

        referenceThreeExpctd = refOne + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceThree).sendKeys(referenceThreeExpctd);

        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"EquipmentType\"]")));
        dropdown.selectByVisibleText("48 foot van");

    }

    @And("^in Financial Information - Customer Charges Tab enter Freight Charges as \"([^\"]*)\"$")
    public void inFinancialInformationCustomerChargesTabEnterFreightChargesAs(String freightChargesTotal) {
        driver.findElement(billingPage.freightChargesTotal).sendKeys(freightChargesTotal);
        driver.findElement(billingPage.freightChargesTotal).sendKeys(Keys.TAB);
        freightChargesTotalRPExpctd = driver.findElement(billingPage.freightChargesTotal).getAttribute("value");
    }


    @Given("^in Financial Information - Customer Charges Tab enter Fuel Surcharges Quantity as \"([^\"]*)\" and Rate as \"([^\"]*)\"$")
    public void in_Financial_Information_Customer_Charges_Tab_enter_Fuel_Surcharges_Quantity_as_and_Rate_as(String CustomerChargesFuelSurchargesQuantity, String CustomerChargesFuelSurchargesRate) throws Throwable {

        driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(CustomerChargesFuelSurchargesQuantity);
        driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(Keys.TAB);
        fuelChargesQuantityRPExpctd = driver.findElement(billingPage.fuelSurchargesQuantity).getAttribute("value");

        driver.findElement(billingPage.fuelSurchargesRate).sendKeys(CustomerChargesFuelSurchargesRate);
        driver.findElement(billingPage.fuelSurchargesRate).sendKeys(Keys.TAB);
        fuelChargesRateRPExpctd = driver.findElement(billingPage.fuelSurchargesRate).getAttribute("value");
    }


    @Given("^in Financial Information - Customer Charges Tab enter Daily Chassis Charges Quantity as \"([^\"]*)\" and Rate as \"([^\"]*)\"$")
    public void in_Financial_Information_Customer_Charges_Tab_enter_Daily_Chassis_Charges_Quantity_as_and_Rate_as(String CustomerChargesDailyChassisChargesQuantity, String CustomerChargesDailyChassisChargesRate) {

        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(CustomerChargesDailyChassisChargesQuantity);
        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(Keys.TAB);
        dailyChassisChargesQuantityRPExpctd = driver.findElement(billingPage.dailyChasisChargesQuantity).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(CustomerChargesDailyChassisChargesRate);
        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(Keys.TAB);
        dailyChassisChargesRateRPExpctd = driver.findElement(billingPage.dailyChasisChargesRate).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorOne Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorOne_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorOne, String fCOne, String fSOne, String dCCOne) {

        driver.findElement(billingPage.tractorOne).sendKeys(tractorOne);

        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(fCOne);
        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPay).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(fSOne);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(dCCOne);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorTwo Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorTwo_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorTwo, String fCTwo, String fSTwo, String dCCTwo) {

        driver.findElement(billingPage.tractorTwo).sendKeys(tractorTwo);

        driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).sendKeys(fCTwo);
        driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).sendKeys(fSTwo);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).sendKeys(dCCTwo);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorThree Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorThree_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorThree, String fCThree, String fSThree, String dCCThree) throws InterruptedException {

        driver.findElement(billingPage.tractorThree).sendKeys(tractorThree);

        driver.findElement(billingPage.freightChargesIndependentContractorPayThree).sendKeys(fCThree);
        driver.findElement(billingPage.freightChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayThree).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).sendKeys(fSThree);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).sendKeys(dCCThree);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).getAttribute("value");


        driver.findElement(By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_next\"]/img")).click();
        Thread.sleep(3000);
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorFour Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorFour_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorFour, String fCFour, String fSFour, String dCCFour) {

        driver.findElement(billingPage.tractorFour).sendKeys(tractorFour);

        driver.findElement(billingPage.freightChargesIndependentContractorPayFour).sendKeys(fCFour);
        driver.findElement(billingPage.freightChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayFour).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).sendKeys(fSFour);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).sendKeys(dCCFour);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorFive Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorFive_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorFive, String fCFive, String fSFive, String dCCFive) {

        driver.findElement(billingPage.tractorFive).sendKeys(tractorFive);

        driver.findElement(billingPage.freightChargesIndependentContractorPayFive).sendKeys(fCFive);
        driver.findElement(billingPage.freightChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayFive).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).sendKeys(fSFive);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).sendKeys(dCCFive);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass the value \"([^\"]*)\" from the TractorSix Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_the_value_from_the_TractorSix_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorSix, String fCSix, String fSSix, String dCCSix) {

        driver.findElement(billingPage.tractorSix).sendKeys(tractorSix);

        driver.findElement(billingPage.freightChargesIndependentContractorPaySix).sendKeys(fCSix);
        driver.findElement(billingPage.freightChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPaySix).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).sendKeys(fSSix);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).sendKeys(dCCSix);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).getAttribute("value");
    }


    @And("in Operations Information - Actual enter Date as \"([^\"]*)\" and Time as \"([^\"]*)\"$")
    public void inOperationsInformationActualEnterDateAsAndTimeAs(String actualDate, String actualTime) {
        actualDateExpctd = actualDate;
        driver.findElement(billingPage.actualDate).sendKeys(actualDate);
        driver.findElement(billingPage.actualTime).sendKeys(actualTime);
    }

    @Given("^in Operations Information - enter Delivery Appointment Date as \"([^\"]*)\" and Time as \"([^\"]*)\"$")
    public void in_Operations_Information_enter_Delivery_Appointment_Date_as_and_Time_as(String dADate, String dATime) {
        deliveryAppointmentDateExpctd = dADate;
        driver.findElement(billingPage.pickupAppointmentDate).sendKeys(dADate);
        driver.findElement(billingPage.pickupAppointmentTime).sendKeys(dATime);
    }

    @Then("^click on Book Load and validate if the Booking Number got generated$")
    public void click_on_Book_Load_and_validate_if_the_Booking_Number_got_generated() throws InterruptedException {
        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
    }

    @Then("^click on Bill Customer Pay Driver Button$")
    public void clickOnBillCustomerPayDriverButton() throws InterruptedException {
        getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }


    //............................................/ #T1b @BCPDCreateBillNeg /................................................//

    @Then("^click on Book Load and validate if the Booking Number got generated for BCPD Negative Scenario \"([^\"]*)\" \"([^\"]*)\"$")
    public void click_on_Book_Load_and_validate_if_the_Booking_Number_got_generated_for_BCPD_Negative_Scenario(String tractorOnee, String tractorThreee) throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(3000);
        getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        System.out.println("=========================================");
     /*   log.info("Alert - " + driver.findElement(By.xpath("/html/body/div[1]/table/tbody")).getText());
        Thread.sleep(3000);
     //   driver.findElement(By.xpath("//*[@id=\"Yes\"]/img")).click();
        driver.findElement(By.xpath("//*[@id=\"Table2\"]/tbody/tr[7]/td/a/img")).click();

        getAndSwitchToWindowHandles();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_previous\"]/img")).click();

        driver.findElement(billingPage.tractorOne).clear();
        driver.findElement(billingPage.tractorOne).sendKeys(tractorOnee);



        driver.findElement(billingPage.tractorThree).clear();
        driver.findElement(billingPage.tractorThree).sendKeys(tractorThreee);

        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();

      //  driver.findElement(By.xpath("//*[@id=\"Table2\"]/tbody/tr[7]/td/a/img")).click();

        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
      //  driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
        getAndSwitchToWindowHandles();  */
    }

    @Then("^click on Bill Customer Pay Driver Button for Negative Scenario$")
    public void clickOnBillCustomerPayDriverButtonForNegativeScenario() throws InterruptedException {
     //   getAndSwitchToWindowHandles();
     //   Thread.sleep(2000);
     //   driver.findElement(billingPage.billCustomerPayDriverButton).click();

    //    driver.findElement(By.xpath("//*[@id=\"Table2\"]/tbody/tr[7]/td/a/img")).click();

    /*    getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");  */

    }


    @Given("^enter Valid Tractors for BCPD \\(whose Tractor Unit No\\. and Vendor Codes are available\\) \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Valid_Tractors_for_BCPD_whose_Tractor_Unit_No_and_Vendor_Codes_are_available(String tractorOnee, String tractorTwoo, String tractorThreee, String tractorFourr, String tractorFivee, String tractorSixx) throws Throwable {
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.tractorOne).clear();
        driver.findElement(billingPage.tractorOne).sendKeys(tractorOnee);

        Thread.sleep(2000);
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        log.info("Alert - " + driver.findElement(By.xpath("/html/body/div/table/tbody")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");

    }


    //............................................/ #T1ab @BCPDOrderNumOnDB /................................................//

    @And("^validate the created Order Number for Bill Customer Pay Driver with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_created_Order_Number_for_Bill_Customer_Pay_Driver_with_Database_Record_and(String environment, String tableName, String orderNo, String ordLoc, String billto, String from, String to) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [Ord_id]\n" +
                "      ,[Ord_loc]\n" +
                "      ,[Ord_num]\n" +
                "      ,[Ord_pro]\n" +
                "      ,[Ord_suffix]\n" +
                "      ,[Ord_trans_status]\n" +
                "      ,[Ord_bill_to_code]\n" +
                "      ,[Ord_bill_to_name]\n" +
                "      ,[Ord_bill_to_addr1]\n" +
                "      ,[Ord_bill_to_addr2]\n" +
                "      ,[Ord_bill_to_city]\n" +
                "      ,[Ord_bill_to_state]\n" +
                "      ,[Ord_bill_to_zip]\n" +
                "      ,[Ord_sh_code]\n" +
                "      ,[Ord_sh_name]\n" +
                "      ,[Ord_sh_addr1]\n" +
                "      ,[Ord_sh_addr2]\n" +
                "      ,[Ord_sh_city]\n" +
                "      ,[Ord_sh_state]\n" +
                "      ,[Ord_sh_zip]\n" +
                "      ,[Ord_cn_code]\n" +
                "      ,[Ord_cn_name]\n" +
                "      ,[Ord_cn_addr1]\n" +
                "      ,[Ord_cn_addr2]\n" +
                "      ,[Ord_cn_city]\n" +
                "      ,[Ord_cn_state]\n" +
                "      ,[Ord_cn_zip]\n" +
                "      ,[Ord_qty]\n" +
                "      ,[Ord_desc]\n" +
                "      ,[Ord_weight]\n" +
                "      ,[Ord_haz]\n" +
                "      ,[Ord_haz_code]\n" +
                "      ,[Ord_street_turn]\n" +
                "      ,[Ord_miles]\n" +
                "      ,[Ord_DAT_status]\n" +
                "      ,[Ord_DAT_comment]\n" +
                "      ,[Ord_DAT_code]\n" +
                "      ,[Ord_DAT_datetime]\n" +
                "      ,[Ord_accepted_risk]\n" +
                "      ,[Ord_creation_login]\n" +
                "      ,[Ord_creation_date]\n" +
                "      ,[Ord_update_login]\n" +
                "      ,[Ord_update_date]\n" +
                "      ,[Ord_trailer]\n" +
                "      ,[Ord_chassis]\n" +
                "      ,[Ord_emptytrailer]\n" +
                "      ,[Ord_seal]\n" +
                "      ,[Ord_vessel]\n" +
                "      ,[Ord_inbond]\n" +
                "      ,[Ord_covered_by]\n" +
                "      ,[Ord_booking_by]\n" +
                "      ,[Ord_has_billing_doc]\n" +
                "      ,[Ord_covered_date]\n" +
                "      ,[Ord_Is_Deleted]\n" +
                "      ,[Ord_emptychassis]\n" +
                "      ,[Ord_Is_PayDriver]\n" +
                "      ,[Ord_Is_BillCustomer]\n" +
                "      ,[Ord_Is_BillCustomerPayDriver]\n" +
                "      ,[Ord_Is_OrderClose]\n" +
                "      ,[Ord_Ship_reg_code]\n" +
                "      ,[Ord_cons_reg_code]\n" +
                "      ,[ORD_ACCTING_DATE]\n" +
                "      ,[ORD_ACCTING_WK]\n" +
                "      ,[ORD_AGENT_SETTLED]\n" +
                "      ,[ORD_TRACTOR_SETTLED]\n" +
                "      ,[ORD_INVOICED]\n" +
                "      ,[Ord_Is_X6State]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [Ord_Is_BillCustomerPayDriver] = 1 \n" +
                "       AND [Ord_num] = " + orderNo + " \n" +
                "       AND [Ord_loc]= '" + ordLoc + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbOrdersTable = new ArrayList<>();
        List<String> dbOrdersTable1 = new ArrayList<>();
        List<String> dbOrdersTable2 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10) +
                    "\t" + res.getString(11) +
                    "\t" + res.getString(12) +
                    "\t" + res.getString(13) +
                    "\t" + res.getString(14) +
                    "\t" + res.getString(15) +
                    "\t" + res.getString(16) +
                    "\t" + res.getString(17) +
                    "\t" + res.getString(18) +
                    "\t" + res.getString(19) +
                    "\t" + res.getString(20) +
                    "\t" + res.getString(21) +
                    "\t" + res.getString(22) +
                    "\t" + res.getString(23) +
                    "\t" + res.getString(24) +
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31) +
                    "\t" + res.getString(32) +
                    "\t" + res.getString(33) +
                    "\t" + res.getString(34) +
                    "\t" + res.getString(35) +
                    "\t" + res.getString(36) +
                    "\t" + res.getString(37) +
                    "\t" + res.getString(38) +
                    "\t" + res.getString(39) +
                    "\t" + res.getString(40) +
                    "\t" + res.getString(41) +
                    "\t" + res.getString(42) +
                    "\t" + res.getString(43) +
                    "\t" + res.getString(44) +
                    "\t" + res.getString(45) +
                    "\t" + res.getString(46) +
                    "\t" + res.getString(47) +
                    "\t" + res.getString(48) +
                    "\t" + res.getString(49) +
                    "\t" + res.getString(50) +
                    "\t" + res.getString(51) +
                    "\t" + res.getString(52) +
                    "\t" + res.getString(53) +
                    "\t" + res.getString(54) +
                    "\t" + res.getString(55) +
                    "\t" + res.getString(56) +
                    "\t" + res.getString(57) +
                    "\t" + res.getString(58) +
                    "\t" + res.getString(59) +
                    "\t" + res.getString(60) +
                    "\t" + res.getString(61) +
                    "\t" + res.getString(62) +
                    "\t" + res.getString(63) +
                    "\t" + res.getString(64) +
                    "\t" + res.getString(65) +
                    "\t" + res.getString(66) +
                    "\t" + res.getString(67));
            System.out.println();

            System.out.println("Ord_loc : " + res.getString(2));
            System.out.println("Ord_num : " + res.getString(3));
            System.out.println("Ord_Is_BillCustomerPayDriver : " + res.getString(58));
            System.out.println();

            String a = res.getString(7);
            dbOrdersTable.add(a);
            String b = res.getString(14);
            dbOrdersTable1.add(b);
            String c = res.getString(21);
            dbOrdersTable2.add(c);


            boolean booleanValue = false;
            for (String dbAAT : dbOrdersTable) {
                if (dbAAT.contains(a)) {
                    if (Objects.equals(a, billto)) {
                        System.out.println("BILL TO : " + a);
                    }
                }
            }

            for (String dbAAT1 : dbOrdersTable1) {
                if (dbAAT1.contains(b)) {
                    if (Objects.equals(b, from)) {
                        System.out.println("FROM : " + b);
                    }
                }
            }

            for (String dbAAT2 : dbOrdersTable2) {
                if (dbAAT2.contains(c)) {
                    if (Objects.equals(c, to)) {
                        System.out.println("TO : " + c);
                    }
                    booleanValue = true;
                }
            }
            if (booleanValue) {
                assertTrue("Assertion Passed!!", true);
            } else {
                fail("Assertion Failed!!");
            }
        }

        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................/  #T2a @PDOnlyCreateBillPositive /................................................//

    @Then("^click on Book Load and validate if the Booking Number got generated for Pay Load Only$")
    public void click_on_Book_Load_and_validate_if_the_Booking_Number_got_generated_for_Pay_Load_Only() throws InterruptedException {
        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
    }


    @Then("^click on Pay Driver Only Button$")
    public void click_On_Pay_Driver_Only_Button() throws InterruptedException {
        getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        driver.findElement(billingPage.payDriverOnlyButton).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");

    }

    //............................................/  #T2b @PDOnlyCreateBillNeg /................................................//


    @Then("^click on Book Load and validate if the Booking Number got generated for Pay Driver Only Negative Scenario \"([^\"]*)\" \"([^\"]*)\"$")
    public void click_on_Book_Load_and_validate_if_the_Booking_Number_got_generated_for_Pay_Driver_Only_Negative_Scenario(String tractorOnee, String tractorThreee) throws InterruptedException {


        Thread.sleep(2000);
        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(3000);
        getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        System.out.println("=========================================");
        log.info("Alert - " + driver.findElement(By.xpath("/html/body/div[1]/table/tbody")).getText());
        Thread.sleep(3000);
        //   driver.findElement(By.xpath("//*[@id=\"Yes\"]/img")).click();
        driver.findElement(By.xpath("//*[@id=\"Table2\"]/tbody/tr[7]/td/a/img")).click();

        getAndSwitchToWindowHandles();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_previous\"]/img")).click();

        driver.findElement(billingPage.tractorOne).clear();
        driver.findElement(billingPage.tractorOne).sendKeys(tractorOnee);



        driver.findElement(billingPage.tractorThree).clear();
        driver.findElement(billingPage.tractorThree).sendKeys(tractorThreee);

        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();

        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
        //  driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
        getAndSwitchToWindowHandles();

    }

    @Then("^click on Pay Driver Only Button for Negative Scenario$")
    public void click_On_Pay_Driver_Only_Button_for_Negative_Scenario() throws InterruptedException {

     /*   getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");  */

    }


    @Given("^enter Valid Tractors for Pay Driver Only, whose Tractor Unit No and Vendor Codes are available \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Valid_Tractors_for_Pay_Driver_Only_whose_Tractor_Unit_No_and_Vendor_Codes_are_available(String tractorOnee, String tractorTwoo, String tractorThreee, String tractorFourr, String tractorFivee, String tractorSixx) throws Throwable {

        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.tractorOne).clear();
        driver.findElement(billingPage.tractorOne).sendKeys(tractorOnee);
        Thread.sleep(2000);
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        log.info("Alert - " + driver.findElement(By.xpath("/html/body/div/table/tbody")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("//*[@id=\"Table3\"]/tbody/tr/td[1]/a/img")).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }


    //............................................/ #T2ab @PDOnlyOrderNumOnDB /................................................//

    @And("^validate the created Order Number for Pay Driver Only with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_created_Order_Number_for_Pay_Driver_Only_with_Database_Record_and(String environment, String tableName, String orderNo, String ordLoc, String billto, String from, String to) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [Ord_id]\n" +
                "      ,[Ord_loc]\n" +
                "      ,[Ord_num]\n" +
                "      ,[Ord_pro]\n" +
                "      ,[Ord_suffix]\n" +
                "      ,[Ord_trans_status]\n" +
                "      ,[Ord_bill_to_code]\n" +
                "      ,[Ord_bill_to_name]\n" +
                "      ,[Ord_bill_to_addr1]\n" +
                "      ,[Ord_bill_to_addr2]\n" +
                "      ,[Ord_bill_to_city]\n" +
                "      ,[Ord_bill_to_state]\n" +
                "      ,[Ord_bill_to_zip]\n" +
                "      ,[Ord_sh_code]\n" +
                "      ,[Ord_sh_name]\n" +
                "      ,[Ord_sh_addr1]\n" +
                "      ,[Ord_sh_addr2]\n" +
                "      ,[Ord_sh_city]\n" +
                "      ,[Ord_sh_state]\n" +
                "      ,[Ord_sh_zip]\n" +
                "      ,[Ord_cn_code]\n" +
                "      ,[Ord_cn_name]\n" +
                "      ,[Ord_cn_addr1]\n" +
                "      ,[Ord_cn_addr2]\n" +
                "      ,[Ord_cn_city]\n" +
                "      ,[Ord_cn_state]\n" +
                "      ,[Ord_cn_zip]\n" +
                "      ,[Ord_qty]\n" +
                "      ,[Ord_desc]\n" +
                "      ,[Ord_weight]\n" +
                "      ,[Ord_haz]\n" +
                "      ,[Ord_haz_code]\n" +
                "      ,[Ord_street_turn]\n" +
                "      ,[Ord_miles]\n" +
                "      ,[Ord_DAT_status]\n" +
                "      ,[Ord_DAT_comment]\n" +
                "      ,[Ord_DAT_code]\n" +
                "      ,[Ord_DAT_datetime]\n" +
                "      ,[Ord_accepted_risk]\n" +
                "      ,[Ord_creation_login]\n" +
                "      ,[Ord_creation_date]\n" +
                "      ,[Ord_update_login]\n" +
                "      ,[Ord_update_date]\n" +
                "      ,[Ord_trailer]\n" +
                "      ,[Ord_chassis]\n" +
                "      ,[Ord_emptytrailer]\n" +
                "      ,[Ord_seal]\n" +
                "      ,[Ord_vessel]\n" +
                "      ,[Ord_inbond]\n" +
                "      ,[Ord_covered_by]\n" +
                "      ,[Ord_booking_by]\n" +
                "      ,[Ord_has_billing_doc]\n" +
                "      ,[Ord_covered_date]\n" +
                "      ,[Ord_Is_Deleted]\n" +
                "      ,[Ord_emptychassis]\n" +
                "      ,[Ord_Is_PayDriver]\n" +
                "      ,[Ord_Is_BillCustomer]\n" +
                "      ,[Ord_Is_BillCustomerPayDriver]\n" +
                "      ,[Ord_Is_OrderClose]\n" +
                "      ,[Ord_Ship_reg_code]\n" +
                "      ,[Ord_cons_reg_code]\n" +
                "      ,[ORD_ACCTING_DATE]\n" +
                "      ,[ORD_ACCTING_WK]\n" +
                "      ,[ORD_AGENT_SETTLED]\n" +
                "      ,[ORD_TRACTOR_SETTLED]\n" +
                "      ,[ORD_INVOICED]\n" +
                "      ,[Ord_Is_X6State]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [Ord_Is_PayDriver] = 1 \n" +
                "       AND [Ord_num] = " + orderNo + " \n" +
                "       AND [Ord_loc]= '" + ordLoc + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbOrdersTable = new ArrayList<>();
        List<String> dbOrdersTable1 = new ArrayList<>();
        List<String> dbOrdersTable2 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10) +
                    "\t" + res.getString(11) +
                    "\t" + res.getString(12) +
                    "\t" + res.getString(13) +
                    "\t" + res.getString(14) +
                    "\t" + res.getString(15) +
                    "\t" + res.getString(16) +
                    "\t" + res.getString(17) +
                    "\t" + res.getString(18) +
                    "\t" + res.getString(19) +
                    "\t" + res.getString(20) +
                    "\t" + res.getString(21) +
                    "\t" + res.getString(22) +
                    "\t" + res.getString(23) +
                    "\t" + res.getString(24) +
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31) +
                    "\t" + res.getString(32) +
                    "\t" + res.getString(33) +
                    "\t" + res.getString(34) +
                    "\t" + res.getString(35) +
                    "\t" + res.getString(36) +
                    "\t" + res.getString(37) +
                    "\t" + res.getString(38) +
                    "\t" + res.getString(39) +
                    "\t" + res.getString(40) +
                    "\t" + res.getString(41) +
                    "\t" + res.getString(42) +
                    "\t" + res.getString(43) +
                    "\t" + res.getString(44) +
                    "\t" + res.getString(45) +
                    "\t" + res.getString(46) +
                    "\t" + res.getString(47) +
                    "\t" + res.getString(48) +
                    "\t" + res.getString(49) +
                    "\t" + res.getString(50) +
                    "\t" + res.getString(51) +
                    "\t" + res.getString(52) +
                    "\t" + res.getString(53) +
                    "\t" + res.getString(54) +
                    "\t" + res.getString(55) +
                    "\t" + res.getString(56) +
                    "\t" + res.getString(57) +
                    "\t" + res.getString(58) +
                    "\t" + res.getString(59) +
                    "\t" + res.getString(60) +
                    "\t" + res.getString(61) +
                    "\t" + res.getString(62) +
                    "\t" + res.getString(63) +
                    "\t" + res.getString(64) +
                    "\t" + res.getString(65) +
                    "\t" + res.getString(66) +
                    "\t" + res.getString(67));
            System.out.println();

            System.out.println("Ord_loc : " + res.getString(2));
            System.out.println("Ord_num : " + res.getString(3));
            System.out.println("Ord_Is_PayDriver : " + res.getString(56));
            System.out.println();

            String a = res.getString(7);
            dbOrdersTable.add(a);
            String b = res.getString(14);
            dbOrdersTable1.add(b);
            String c = res.getString(21);
            dbOrdersTable2.add(c);


            boolean booleanValue = false;
            for (String dbAAT : dbOrdersTable) {
                if (dbAAT.contains(a)) {
                    if (Objects.equals(a, billto)) {
                        System.out.println("BILL TO : " + a);
                    }
                }
            }

            for (String dbAAT1 : dbOrdersTable1) {
                if (dbAAT1.contains(b)) {
                    if (Objects.equals(b, from)) {
                        System.out.println("FROM : " + b);
                    }
                }
            }

            for (String dbAAT2 : dbOrdersTable2) {
                if (dbAAT2.contains(c)) {
                    if (Objects.equals(c, to)) {
                        System.out.println("TO : " + c);
                    }
                    booleanValue = true;
                }
            }
            if (booleanValue) {
                assertTrue("Assertion Passed!!", true);
            } else {
                fail("Assertion Failed!!");
            }
        }

        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @RetrieveOrder /................................................//

    @Given("^click on Retrieve Order and insert Order No \"([^\"]*)\"$")
    public void click_on_Retrieve_Order_and_insert_Order_No(String orderNo) throws InterruptedException {

        driver.findElement(billingPage.retrieveOrderButton).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.switchTo().frame(0);

        driver.findElement(retrieveOrderPage.orderNumberField).sendKeys(orderNo);
        driver.findElement(retrieveOrderPage.goButton).click();
        Thread.sleep(10000);
        Assert.assertTrue(driver.findElement(retrieveOrderPage.billToRP).getText().contains(billToExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.fromRP).getText().contains(fromExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.toRP).getText().contains(toExpctd));
        Thread.sleep(10000);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.containerRP).getAttribute("value"), containerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.chassisRP).getAttribute("value"), chassisExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.emptyContainerRP).getAttribute("value"), emptyContainerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceOneRP).getAttribute("value"), referenceOneExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceTwoRP).getAttribute("value"), referenceTwoExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceThreeRP).getAttribute("value"), referenceThreeExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesTotalRP).getAttribute("value"), freightChargesTotalRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesIndependentContractorPayRP).getAttribute("value"), freightChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesQuantityRP).getAttribute("value"), fuelChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesRateRP).getAttribute("value"), fuelChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesIndependentContractorPayRP).getAttribute("value"), fuelChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesQuantityRP).getAttribute("value"), dailyChassisChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesRateRP).getAttribute("value"), dailyChassisChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesIndependentContractorPayRP).getAttribute("value"), dailyChassisChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.pickupAppointmentDate).getAttribute("value"), pickupAppointmentDateExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.pickupAppointmentTime).getAttribute("value"), pickupAppointmentTimeExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.actualDate).getAttribute("value"), actualDateExpctd);
    }


    @Given("^click on Retrieve Order and insert Order No for BCPD \"([^\"]*)\"$")
    public void click_on_Retrieve_Order_and_insert_Order_No_for_BCPD(String orderNo) throws InterruptedException {
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.retrieveOrderButton).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.switchTo().frame(0);

        driver.findElement(retrieveOrderPage.orderNumberField).sendKeys(orderNo);
        driver.findElement(retrieveOrderPage.goButton).click();
        Thread.sleep(10000);
        Assert.assertTrue(driver.findElement(retrieveOrderPage.billToRP).getText().contains(billToExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.fromRP).getText().contains(fromExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.toRP).getText().contains(toExpctd));
        Thread.sleep(10000);

        Assert.assertTrue(driver.findElement(retrieveOrderPage.containerRP).getText().contains(containerExpctd.toUpperCase()));

        Assert.assertEquals(driver.findElement(retrieveOrderPage.containerRP).getAttribute("value"), containerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.chassisRP).getAttribute("value"), chassisExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.emptyContainerRP).getAttribute("value"), emptyContainerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceOneRP).getAttribute("value"), referenceOneExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceTwoRP).getAttribute("value"), referenceTwoExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceThreeRP).getAttribute("value"), referenceThreeExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesTotalRP).getAttribute("value"), freightChargesTotalRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesIndependentContractorPayRP).getAttribute("value"), freightChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesQuantityRP).getAttribute("value"), fuelChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesRateRP).getAttribute("value"), fuelChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesIndependentContractorPayRP).getAttribute("value"), fuelChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesQuantityRP).getAttribute("value"), dailyChassisChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesRateRP).getAttribute("value"), dailyChassisChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesIndependentContractorPayRP).getAttribute("value"), dailyChassisChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.pickupAppointmentDate).getAttribute("value"), pickupAppointmentDateExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.pickupAppointmentTime).getAttribute("value"), pickupAppointmentTimeExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.actualDate).getAttribute("value"), actualDateExpctd);
    }


}