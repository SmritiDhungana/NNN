package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;


public class AgentSettlingStepDef {
    WebDriver driver;
    String url = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(driver);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    BasicFileMaintenancePage basicFileMaintenancePage = new BasicFileMaintenancePage(driver);
    AccountFilePage accountFilePage = new AccountFilePage(driver);
    LocationFilePage locationFilePage = new LocationFilePage(driver);
    AgentCommissionMaintenancePage agentCommissionMaintenancePage = new AgentCommissionMaintenancePage(driver);
    AARAgentManagementPage aarAgentManagementPage = new AARAgentManagementPage(driver);
    AgentCommissionCalculationPage agentCommissionCalculationPage = new AgentCommissionCalculationPage(driver);
    AgentSettlingPage agentSettlingPage = new AgentSettlingPage(driver);
    AgentSettlementInquiryPage agentSettlementInquiryPage = new AgentSettlementInquiryPage(driver);
    AgentSettlementAdjustmentsPage agentSettlementAdjustmentsPage = new AgentSettlementAdjustmentsPage(driver);
    AgentsCurrentlyBeingSettledPage agentsCurrentlyBeingSettledPage = new AgentsCurrentlyBeingSettledPage(driver);
    AgentSettlementFlagMaintenancePage agentSettlementFlagMaintenancePage = new AgentSettlementFlagMaintenancePage(driver);
    AgentSettlementAdjustmentsUploadPage agentSettlementAdjustmentsUploadPage = new AgentSettlementAdjustmentsUploadPage();
    Logger log = Logger.getLogger("SettlingStepDef");
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    Scenario scn;



    @After("@bb")
    public void takeScreenshotOnFailure1(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
        }
    }





    //............................................/ Background /................................................//

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" and Enter the url for EBH$")
    public void run_Test_for_on_Browser_and_Enter_the_url_for_EBH(String environment, String browser) {
        BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
        url = browserDriverInitialization.getDataFromPropertiesFileForEBH(environment, browser);
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(150, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(150, TimeUnit.SECONDS);
        driver.get(url);
    }

    @Given("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void login_to_the_Agents_Portal_with_username_and_password(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu and to the Settlements page$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_and_to_the_Settlements_page() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
        driver.findElement(corporatePage.settlements).click();
    }


    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Then("^Close all open Browsers$")
    public void Close_all_open_Browsers() {
        driver.close();
        driver.quit();
    }

    //............................................/ 1 @AgentSettling @OWTBFContinue /................................................//

    @And("^Navigate to the Agent Settling page$")
    public void navigate_to_the_Agent_Settling_page() {
        driver.findElement(settlementsPage.agentSettling).click();
    }

    @And("^Pop Up Menu arises insert password \"([^\"]*)\"$")
    public void Pop_Up_Menu_arises_insert_password(String password) {
        driver.findElement(settlementsPage.EnterPasswordForAgentSettling).sendKeys(password);
    }

    @And("^Click on Ok$")
    public void Click_on_Ok() {
        driver.findElement(settlementsPage.OkButton).click();
    }

    @And("^Conformation pop up menu arises Reset or Continue, click Continue$")
    public void conformation_pop_up_menu_arises_reset_or_continue_click_Continue() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation).isDisplayed();
        Thread.sleep(5000);
        driver.findElement(settlementsPage.Continue).click();
    }

    @And("^Conformation pop up menu arises reset or continue, click Continue$")
    public void Conformation_pop_up_menu_arises_reset_or_continue_click_Continue() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation1).isDisplayed();
        Thread.sleep(3000);
        driver.findElement(settlementsPage.Continue).click();
    }

    @And("^Verify you are on Agent Settling Page$")
    public void Verify_you_are_on_Agent_Settling_Page() throws InterruptedException {
        assertTrue(driver.findElement(agentSettlingPage.AgentSettlingTableHead).isDisplayed());
        System.out.println("=========================================");
        System.out.println(driver.findElement(agentSettlingPage.AgentSettlingTableHead).getText());
        Thread.sleep(5000);
    }

    @And("^Click Filter Icon on Orders Waiting To Be Finalized$")
    public void Click_Filter_Icon_on_Orders_Waiting_To_Be_Finalized() {
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedFilterIcon).click();
    }

    @And("^Click NO on drop down item list and validate the color of OWTBF at the left end of the page$")
    public void Click_NO_on_drop_down_item_list_and_validate_the_color_of_OWTBF_at_the_left_end_of_the_page() throws InterruptedException {
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedDropDownNo).click();
        driver.findElement(agentSettlingPage.ShowDetails).click();
        Thread.sleep(2000);
        WebElement newBtn = driver.findElement(By.xpath("//*[@id=\"ordersWaitingFinalized\"]"));
        Actions action = new Actions(driver);
        action.moveToElement(newBtn).perform();
        System.out.println("When OWTBF is NO, Color of OWTBF Button is Transparent: " + newBtn.getCssValue("background"));
        Thread.sleep(1000);
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedFilterIcon).click();
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedDropDownClearFilter).click();
        Thread.sleep(1000);
    }

    @And("^Click YES on drop down item list for OWTBF as \"([^\"]*)\" and validate the color of OWTBF at the left end of the page$")
    public void Click_YES_on_drop_down_item_list_for_OWTBF_as_and_validate_the_color_of_OWTBF_at_the_left_end_of_the_page(String code) throws Throwable {
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedFilterIcon).click();
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedDropDownYes).click();
     /*   List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'btn btn-secondary dropdown-toggle')]/img/div"));
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }  */

        Thread.sleep(1000);
        driver.findElement(agentSettlingPage.ShowDetails).click();
        Thread.sleep(1000);
        WebElement newBtn = driver.findElement(By.xpath("//*[@id=\"ordersWaitingFinalized\"]"));
        Actions action = new Actions(driver);
        action.moveToElement(newBtn).perform();
        System.out.println("When OWTBF is YES, Color of OWTBF Button is Red: " + newBtn.getCssValue("background"));
    }

    @And("^Click Orders Waiting To Be Finalized Button at the left end of the page$")
    public void Click_Orders_Waiting_To_Be_Finalized_Button_at_the_left_end_of_the_page() {
        driver.findElement(agentSettlingPage.OrdersWaitingToBeFinalized).click();
    }

    @And("^Verify Agent/Location Code and Record Returned on Orders Waiting To Be Finalized Page for \"([^\"]*)\"$")
    public void Verify_Agent_Location_Code_and_Record_Returned_on_Orders_Waiting_To_Be_Finalized_Page_for(String agentCode) throws InterruptedException {
        Thread.sleep(2000);
        assertTrue(driver.findElement(agentSettlingPage.AgentCode).isDisplayed());
        System.out.println("=========================================");
        System.out.println(driver.findElement(agentSettlingPage.AgentCode).getText());
        assertTrue(driver.findElement(agentSettlingPage.VendorName).isDisplayed());
        System.out.println(driver.findElement(agentSettlingPage.VendorName).getText());
        System.out.println("=========================================");
        System.out.println(driver.findElement(agentSettlingPage.RecordsReturned).getText());

        List<WebElement> oWTBFTable = driver.findElements(By.xpath("//*[@id=\"tblOrdersWaitingToBeFinalize\"]"));
        for (int i = 0; i <= oWTBFTable.size() - 1; i++) {
            System.out.println(oWTBFTable.get(i).getText());
        }

        boolean booleanValue = false;
        for (WebElement owtbfTable1 : oWTBFTable) {
            if (owtbfTable1.getText().contains(agentCode)) {
                System.out.println("Agent Code is present: " + agentCode);
                booleanValue = true;
            }
            break;
        }
        if (booleanValue) {
            Assert.assertTrue("Agent Code is present !!", true);
        } else {
            fail("Agent Code is not present!!");
        }
        System.out.println("=========================================");
    }


    @Given("^Validate Records Returned with Database Record \"([^\"]*)\" and \"([^\"]*)\" for \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_with_Database_Record_and_for_and(String environment, String tableName, String agentCode, String vendorCode) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "" + vendorCode + "");
        cstmt.setString(3, "EVA");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbOWTBFTable = new ArrayList<>();
        List<String> dbOWTBFTable1 = new ArrayList<>();
        List<String> dbOWTBFTable2 = new ArrayList<>();
        List<String> dbOWTBFTable3 = new ArrayList<>();
        List<WebElement> oWTBFTable = driver.findElements(By.xpath("//*[@id=\"tblOrdersWaitingToBeFinalize\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));


            String a = rs.getString(11);
            dbOWTBFTable.add(a);

            boolean booleanValue = false;
            for (WebElement owtbfTable1 : oWTBFTable) {
                if (owtbfTable1.getText().contains(a)) {
                    for (String dbowtbfTable : dbOWTBFTable) {
                        if (dbowtbfTable.contains(a)) {
                            System.out.println("Required Docs is: " + a);
                        }
                    }
                    booleanValue = true;
                    break;
                }
                if (booleanValue) {
                    Assert.assertTrue("Required Docs Matches!!", true);
                } else {
                    fail("Required Docs Doesn't Matches!!");
                }
            }

            String b = rs.getString(5);
            dbOWTBFTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement owtbfTable1 : oWTBFTable) {
                if (owtbfTable1.getText().contains(b)) {
                    for (String dbowtbfTable1 : dbOWTBFTable1) {
                        if (dbowtbfTable1.contains(b)) {
                            System.out.println("Shipper Name matches: " + b);
                        }
                    }
                    booleanValue1 = true;
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("Shipper Name matches!!", true);
                } else {
                    fail("Shipper Name Doesn't Matches!!");
                }
            }

            String d = rs.getString(3);
            dbOWTBFTable3.add(d);

            boolean booleanValue3 = false;
            for (WebElement owtbfTable1 : oWTBFTable) {
                if (owtbfTable1.getText().contains(d)) {
                    for (String dbowtbfTable3 : dbOWTBFTable3) {
                        if (dbowtbfTable3.contains(d)) {
                            System.out.println("Order Number Matches: " + d);
                        }
                    }
                    booleanValue3 = true;
                    break;
                }
                if (booleanValue3) {
                    Assert.assertTrue("Order Number Matches!!", true);
                } else {
                    fail("Order Number Doesn't Matches!!");
                }
            }

            String c = rs.getString(12);
            dbOWTBFTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement owtbfTable1 : oWTBFTable) {
                if (owtbfTable1.getText().contains(c)) {
                    for (String dbowtbfTable2 : dbOWTBFTable2) {
                        if (dbowtbfTable2.contains(c)) {
                            System.out.println("Action Performed Matches: " + c);
                        }
                    }
                    booleanValue2 = true;
                    break;
                }
                if (booleanValue2) {
                    Assert.assertTrue("Action Performed Matches !!", true);
                } else {
                    fail("Action Performed Doesn't Matches!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @And("^Click Finalize on Orders Waiting To Be Finalized Page and Close$")
    public void Click_Finalize_on_Orders_Waiting_To_Be_Finalized_Page_and_Close() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"tblOrdersWaitingToBeFinalize\"]/thead/tr/th[11]/div/button/img")).click();
        String text1 = driver.findElement(By.xpath("//*[@id=\"tblOrdersWaitingToBeFinalize\"]/thead/tr/th[11]/div/div")).getText();
        System.out.println("Required Docs is: " + driver.findElement(By.xpath("//*[@id=\"tblOrdersWaitingToBeFinalize\"]/tbody/tr/td[11]")).getText());
        Thread.sleep(1000);
        if (text1.contains("YES")) {
            driver.findElement(agentSettlingPage.Finalize).click();
            Thread.sleep(3000);
            System.out.println("Finalized !!");
            System.out.println("=========================================");
        } else {
            System.out.println("Not Finalized !!");
        }
    }


    //............................................/ 2 @AgentSettling @OWTBFReset /................................................//

    @Given("^Conformation pop up menu arises Reset or Continue, click Reset$")
    public void Conformation_pop_up_menu_arises_Reset_or_Continue_click_Reset() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation).isDisplayed();
        Thread.sleep(4000);
        driver.findElement(settlementsPage.Reset).click();
        Thread.sleep(2000);
        driver.findElement(settlementsPage.PopUpDecision).isDisplayed();
        Thread.sleep(4000);
        driver.findElement(settlementsPage.PreviousWeek).click();
        driver.findElement(settlementsPage.DecisionOk).click();
    }

    @Given("^Conformation pop up menu arises reset or continue, click Reset$")
    public void Conformation_pop_up_menu_arises_reset_or_continue_click_Reset() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation1).isDisplayed();
        Thread.sleep(4000);
        driver.findElement(settlementsPage.Reset).click();
        Thread.sleep(2000);
        driver.findElement(settlementsPage.PopUpDecision).isDisplayed();
        Thread.sleep(4000);
        driver.findElement(settlementsPage.PreviousWeek).click();
        driver.findElement(settlementsPage.DecisionOk).click();
    }


    //................../ 3 @SettleYourAgent&ViewEpicorExcelReportsContinue / 4 @SettleYourAgent&ViewEpicorExcelReportsReset /...................//

    @Given("^Click YES on drop down item list of OWTBF as \"([^\"]*)\"$")
    public void Click_YES_on_drop_down_item_list_of_OWTBF_as(String code) {
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedFilterIcon).click();
        driver.findElement(agentSettlingPage.OrdersWaitingTobeFinalizedDropDownYes).click();

     /*   List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'btn btn-secondary dropdown-toggle')]/img/div"));
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"thOWTBF\"]/div/div"));
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }*/
    }

    @And("^Toggle On the Verified Box$")
    public void Toggle_On_the_Verified_Box() throws InterruptedException {
        if (driver.findElement(agentSettlingPage.ToggleOnOffVerifiedBox).isDisplayed()) {
            driver.findElement(agentSettlingPage.ToggleOnOffVerifiedBox).click();
            Thread.sleep(2000);
            driver.findElement(agentSettlingPage.ShowDetails).click();
        } else {
            throw new AssertionError("Toggle Box Not Seen");
        }
    }

    @And("^Toggle On the Verified Box of Agent Code as \"([^\"]*)\"$")
    public void Toggle_On_the_Verified_Box_of_Agent_Code_as(String code) throws Throwable {
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'btn btn-secondary dropdown-toggle')]/img/div"));
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(1000);
        //   driver.findElement(agentSettlingPage.ToggleOnOffVerifiedBox).click();
        //  Thread.sleep(2000);
        //  driver.findElement(agentSettlingPage.ShowDetails).click();

        try {
            driver.findElement(agentSettlingPage.ToggleOnOffVerifiedBox).click();
            Thread.sleep(2000);
            driver.findElement(agentSettlingPage.ShowDetails).click();
        } catch (Exception e) {
            throw new AssertionError("Toggle Box Not Seen", e);
        }
    }

    @And("^Click on Settle Your Agents on Agent Settling Page$")
    public void Click_on_Settle_Your_Agents_on_Agent_Settling_Page() {
        driver.findElement(agentSettlingPage.SettleYourAgents).click();
    }

    @And("^Validate Final Agent Settlement Summary Table and Negative Settlements for \"([^\"]*)\"$")
    public void validate_Final_Agent_Settlement_Summary_Table_and_Negative_Settlements_for(String vendorCode) throws InterruptedException {

        System.out.println("=========================================");
        System.out.println("Final Agent Settlement Summary Table");
        Thread.sleep(3000);
        log.info(driver.findElement(agentSettlingPage.FinalAgentSettlementSummaryTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");

        List<WebElement> fASSTable = driver.findElements(By.xpath("//*[@id=\"tblFinalAgentDetail\"]/tbody"));
        for (WebElement webElement : fASSTable) {
            if (webElement.getText().contains(vendorCode)) {
                System.out.println("Vendor Code is present in Final Agent Settlement Table: " + vendorCode);
            } else {
                System.out.println("Vendor Code is not present in Final Agent Settlement Table: " + vendorCode);
            }
        }

        List<WebElement> fASSTableNeg = driver.findElements(By.xpath("//*[@id=\"tblNegativeFinalAgentDetail\"]/tbody"));
        for (WebElement webElementNeg : fASSTableNeg) {
            if (webElementNeg.getText().contains(vendorCode)) {
                System.out.println("Vendor Code is present in Final Agent Negative Settlement Table: " + vendorCode);
            } else {
                System.out.println("Vendor Code is not present in Final Agent Negative Settlement Table: " + vendorCode);
            }
        }
    }

    @And("^Validate Final Agent Settlement Summary Table and Negative Settlements with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Final_Agent_Settlement_Summary_Table_and_Negative_Settlements_with_Database_Record_and(String environment, String tableName, String userLogin, String acctingWk, String acctingDt) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");

        try {
            Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

            String query = "{call " + tableName + " (?,?,?)}";
            CallableStatement cstmt = connectionToDatabase.prepareCall(query);

            cstmt.setString(1, "" + userLogin + "");
            cstmt.setString(2, "" + acctingWk + "");
            cstmt.setString(3, "" + acctingDt + "");

            List<String> dbFASSTable = new ArrayList<>();
            List<String> dbFASSTable1 = new ArrayList<>();
            List<String> dbFASSTable2 = new ArrayList<>();
            List<WebElement> fASSTable = driver.findElements(By.xpath("//*[@id=\"tblFinalAgentDetail\"]/tbody"));
            List<WebElement> fASSTableNeg = driver.findElements(By.xpath("//*[@id=\"tblNegativeFinalAgentDetail\"]/tbody"));

            boolean results = cstmt.execute();
            int count = 0;
            do {
                if (results) {
                    ResultSet rs = cstmt.getResultSet();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count1 = rsmd.getColumnCount();
                    List<String> columnList = new ArrayList<String>();
                    for (int i = 1; i <= count1; i++) {
                        columnList.add(rsmd.getColumnLabel(i));
                    }
                    System.out.println(columnList);

                    while (rs.next()) {
                        int rows = rs.getRow();
                        System.out.println("Number of Rows:" + rows);
                        System.out.println(rs.getString(1) +
                                "\t" + rs.getString(2) +
                                "\t" + rs.getString(3) +
                                "\t" + rs.getString(4) +
                                "\t" + rs.getString(5) +
                                "\t" + rs.getString(6) +
                                "\t" + rs.getString(7) +
                                "\t" + rs.getString(8) +
                                "\t" + rs.getString(9));

                        String a = rs.getString(1);
                        dbFASSTable.add(a);

                        boolean booleanValue = false;
                        for (WebElement fassTable : fASSTable) {
                            if (fassTable.getText().contains(a)) {
                                for (String dbfassTable : dbFASSTable) {
                                    if (dbfassTable.contains(a)) {
                                        System.out.println(a);
                                    }
                                }
                                booleanValue = true;
                                break;
                            }
                            if (booleanValue) {
                                Assert.assertTrue("AssertValue is present !!", true);
                            } else {
                                fail("AssertValue is not present!!");
                            }
                        }

                        String b = rs.getString(9);
                        dbFASSTable1.add(b);

                        boolean booleanValue1 = false;
                        for (WebElement fassTable1 : fASSTable) {
                            if (fassTable1.getText().contains(b)) {
                                for (String dbfassTable1 : dbFASSTable1) {
                                    if (dbfassTable1.contains(b)) {
                                        System.out.println(b);
                                    }
                                }
                                booleanValue1 = true;
                                break;
                            }
                            if (booleanValue1) {
                                Assert.assertTrue("AssertValue is present !!", true);
                            } else {
                                fail("AssertValue is not present!!");
                            }
                        }

                        // for negative settlement table
              /*   String c = rs.getString(1);
                    dbFASSTable2.add(c);

                    boolean booleanValue2 = false;
                    for (WebElement fassTableNeg2 : fASSTableNeg) {
                        if (fassTableNeg2.getText().contains(c)) {
                            for (String dbfassTable2 : dbFASSTable2) {
                                if (dbfassTable2.contains(c)) {
                                    System.out.println(c);
                                    booleanValue2 = true;
                                    break;
                                }
                            }
                        }
                        if (booleanValue2) {
                            Assert.assertTrue("AssertValue is present !!", true);
                        } else {
                            fail("AssertValue is not present!!");
                        }
                    }   */
                    }
                } else {
                    count = cstmt.getUpdateCount();
                }
                results = cstmt.getMoreResults();

            }
            while (results || count != -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on View Epicor Details$")
    public void click_on_View_Epicor_Details() throws InterruptedException {
        driver.findElement(By.linkText("View Epicor Details")).click();
        Thread.sleep(1000);
    }

    @And("^Get the File Name of downloaded Excel View Epicor Details and Add the Total Amount \"([^\"]*)\" \"([^\"]*)\"$")
    public double get_the_File_Name_of_downloaded_Excel_View_Epicor_Details_and_Add_the_Total_Amount(String vrow, String vcol) throws IOException {
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        String fileNameDetails = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        //   String sourceURL = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').href");
        //  String downloadedPath = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div.is-active.focus-row-active #file-icon-wrapper img').src");
        //  System.out.println("Download deatils");
        System.out.println("File Name :-" + fileNameDetails);
        System.out.println("=========================================");
        driver.close();

        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameDetails + ""));
        HSSFWorkbook excel = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = excel.getSheetAt(0);
        HSSFCell formulaCell = sheet.createRow(Integer.parseInt(vrow)).createCell(Integer.parseInt(vcol));
        formulaCell.setCellFormula("SUM(J2:J20)");
        inputStream.close();

        HSSFFormulaEvaluator formulaEvaluator = excel.getCreationHelper().createFormulaEvaluator();
        formulaEvaluator.evaluateFormulaCellEnum(formulaCell);

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail1.xls");
        excel.write(outputStream);
        return formulaCell.getNumericCellValue();
    }


    @And("^Get Excel Report of View Epicor Details from Downloads$")
    public void get_Excel_Report_of_View_Epicor_Details_from_Downloads() throws IOException {

        FileInputStream excelEpicor = new FileInputStream(new File("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail1.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(excelEpicor);
        HSSFSheet sheet = wb.getSheetAt(0);

        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
    }

    @And("^Validate Amount in View Epicor Details \"([^\"]*)\" \"([^\"]*)\" with Total Settlement on Final Agent Settlement Summary Table$")
    public void validate_Amount_in_View_Epicor_Details_with_Total_Settlement_on_Final_Agent_Settlement_Summary_Table(String vrow, String vcol) throws IOException {

        float value;
        FileInputStream excelEpicor = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail1.xls");
        HSSFWorkbook wb = new HSSFWorkbook(excelEpicor);

        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(Integer.parseInt(vrow));
        Cell cell = row.getCell(Integer.parseInt(vcol));

        boolean b = cell.getCellTypeEnum() == CellType.NUMERIC;
        value = (float) cell.getNumericCellValue();
        System.out.println("$" + value);
        wb.close();

        String excelAmount = ("$" + value + "0");
        getAndSwitchToWindowHandles();
        String totalSettlement = driver.findElement(By.xpath("//*[@id=\"tblFinalAgentDetailTotalSettlement\"]")).getText();
        assertEquals(excelAmount, totalSettlement);
        if (excelAmount.equals(totalSettlement)) {
            System.out.println("Excel Amount and Total Settlement Amount are Equal !!");
        } else System.out.println("Assertion Failed !!");
        System.out.println("=========================================");

    }

    @And("^Click on View Epicor Detail Summary$")
    public void click_on_View_Epicor_Detail_Summary() throws InterruptedException {
        driver.findElement(By.linkText("View Epicor Detail Summary")).click();
        Thread.sleep(1000);
    }

    @And("^Get the File Name of downloaded Excel View Epicor Detail Summary and Add the Total Amount \"([^\"]*)\" \"([^\"]*)\"$")
    public double get_the_File_Name_of_downloaded_Excel_View_Epicor_Detail_Summary_and_Add_the_Total_Amount(String vrow1, String vcol1) throws IOException {
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        String fileNameSummary = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("File Name :-" + fileNameSummary);
        System.out.println("=========================================");
        driver.close();

        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameSummary + ""));
        HSSFWorkbook excel = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = excel.getSheetAt(0);
        HSSFCell formulaCell = sheet.createRow(Integer.parseInt(vrow1)).createCell(Integer.parseInt(vcol1));
        formulaCell.setCellFormula("SUM(I2:I20)");
        inputStream.close();

        HSSFFormulaEvaluator formulaEvaluator = excel.getCreationHelper().createFormulaEvaluator();
        formulaEvaluator.evaluateFormulaCellEnum(formulaCell);

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail Summary1.xls");
        excel.write(outputStream);
        return formulaCell.getNumericCellValue();
    }


    @And("^Get Excel Report of View Epicor Detail Summary from Downloads$")
    public void get_Excel_Report_of_View_Epicor_Detail_Summary_from_Downloads() throws IOException {
        FileInputStream excelEpicor = new FileInputStream(new File("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail Summary1.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(excelEpicor);
        HSSFSheet sheet = wb.getSheetAt(0);

        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            ///     System.out.println();
        }
    }

    @And("^Validate Amount in View Epicor Detail Summary \"([^\"]*)\" \"([^\"]*)\" with Total Settlement on Final Agent Settlement Summary Table$")
    public void validate_Amount_in_View_Epicor_Detail_Summary_VRow_VColumn_with_Total_Settlement_on_Final_Agent_Settlement_Summary_Table(String vrow1, String vcol1) throws IOException {

        float value;
        FileInputStream excelEpicor = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\View Epicor Detail Summary1.xls");
        HSSFWorkbook wb = new HSSFWorkbook(excelEpicor);

        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(Integer.parseInt(vrow1));
        Cell cell = row.getCell(Integer.parseInt(vcol1));

        boolean b = cell.getCellTypeEnum() == CellType.NUMERIC;
        value = (float) cell.getNumericCellValue();
        System.out.println("$" + value);
        wb.close();

        String excelAmount = ("$" + value + "0");
        getAndSwitchToWindowHandles();
        String totalSettlement = driver.findElement(By.xpath("//*[@id=\"tblFinalAgentDetailTotalSettlement\"]")).getText();
        assertEquals(excelAmount, totalSettlement);
        if (excelAmount.equals(totalSettlement)) {
            System.out.println("Excel Amount and Total Settlement Amount are Equal !!");
        } else System.out.println("Assertion Failed !!");
        System.out.println("=========================================");
    }

    @And("^Click on View Epicor Header$")
    public void click_on_View_Epicor_Header() throws InterruptedException {
        driver.findElement(By.linkText("View Epicor Header")).click();
        Thread.sleep(1000);
    }

    @And("^Get the File Name of downloaded Excel View Epicor Header, Get Excel Report from Downloads and Validate Amount in View Epicor Header \"([^\"]*)\" \"([^\"]*)\" with Total Settlement on Final Agent Settlement Summary Table$")
    public void get_the_File_Name_of_downloaded_Excel_View_Epicor_Header_Get_Excel_Report_from_Downloads_and_validate_Amount_in_View_Epicor_Header_VRow_VColumn_with_Total_Settlement_on_Final_Agent_Settlement_Summary_Table(String vrow2, String vcol2) throws IOException {

        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        String fileNameHeader = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("File Name :-" + fileNameHeader);
        System.out.println("=========================================");
        driver.close();


        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameHeader + ""));
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }

        float value;
        Row row = sheet.getRow(Integer.parseInt(vrow2));
        Cell cell = row.getCell(Integer.parseInt(vcol2));

        boolean b = cell.getCellTypeEnum() == CellType.NUMERIC;
        value = (float) cell.getNumericCellValue();
        System.out.println("$" + value);
        wb.close();

        String excelAmount = ("$" + value + "0");
        getAndSwitchToWindowHandles();
        String totalSettlement = driver.findElement(By.xpath("//*[@id=\"tblFinalAgentDetailTotalSettlement\"]")).getText();
        assertEquals(excelAmount, totalSettlement);
        if (excelAmount.equals(totalSettlement)) {
            System.out.println("Excel Amount and Total Settlement Amount are Equal !!");
        } else System.out.println("Assertion Failed !!");
        System.out.println("=========================================");
    }


    @And("^Click on Confirm and Settle on Final Agent Settlement Summary Page$")
    public void Click_on_Confirm_and_Settle_on_Final_Agent_Settlement_Summary_Page() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(agentSettlingPage.ConfirmAndSettle).click();
        Thread.sleep(5000);
    }


    //........................../ 5 @SettlementOrderDetailsContinue / 6 @SettlementOrderDetailsReset /...........................//

    @Given("^Click on Show Details of agent code on Action field$")
    public void Click_on_Show_Details_of_agent_code_on_Action_field() {
        driver.findElement(agentSettlingPage.ShowDetails).click();
    }

    @And("^Validate Settlement Summary Totals and Settlement Order Details All Totals$")
    public void Validate_Settlement_Summary_Totals_and_Settlement_Order_Details_All_Totals() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("Comparing Settlement Summary Totals and Settlement Order Deatils All Totals");
        String TotalRevenue = driver.findElement(By.xpath("//*[@id=\"tRevenue\"]/strong")).getText();
        String Revenue = driver.findElement(By.xpath("//*[@id=\"tRevenueOrderDetails\"]")).getText();
        Assert.assertEquals(String.valueOf(true), TotalRevenue, Revenue);
        System.out.println("TotalRevenue = Revenue");

        String TotalPay = driver.findElement(By.xpath("//*[@id=\"tPay\"]/strong")).getText();
        String Pay = driver.findElement(By.xpath("//*[@id=\"tPaymentOrderDetails\"]")).getText();
        Assert.assertEquals(TotalPay, Pay);
        System.out.println("TotalPay = Pay");

        String TotalCommission = driver.findElement(By.xpath("//*[@id=\"tCommission\"]")).getText();
        String Commission = driver.findElement(By.xpath("//*[@id=\"tCommissionOrderDetails\"]")).getText();
        Assert.assertEquals(TotalCommission, Commission);
        System.out.println("TotalCommission = Commission");

        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("Settlement Order Details:");
        log.info(driver.findElement(agentSettlingPage.SettlementOrderDetailsTable).getText());

    }

    @And("^Validate the records returned on Settlement Order Details with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_records_returned_on_Settlement_Order_Details_with_Database_Record_and(String environment, String tableName, String proNum, String vendorCode, String accDate, String accWk) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
      /*  String query = "SELECT TOP 1000 " +
                "       [STL_ORDER_ID]\n" +
                "       ,[STL_PRONUM]\n" +
                "       ,[STL_PAYDED_CODE]\n" +
                "       ,[STL_REV]\n" +
                "       ,[STL_PAY]\n" +
                "       ,[STL_COM]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_SETTLEMENTS]" +
                "       WHERE [STL_PRONUM] = '" + proNum + "'";   */

        String query = "SELECT TOP 1000 " +
                "      [AGENT_TRANS_ACCTING_DATE]\n" +
                "      ,[AGENT_TRANS_ACCTING_WK]\n" +
                "      ,[AGENT_TRANS_FLAG_ID]\n" +
                "      ,[AGENT_TRANS_FLAG_OVERRIDE]\n" +
                "      ,[AGENT_TRANS_LOC_OR_TRAC]\n" +
                "      ,[AGENT_TRANS_ORDER_ID]\n" +
                "      ,[AGENT_TRANS_ORDER_NUM]\n" +
                "      ,[AGENT_TRANS_VENDOR_CODE]\n" +
                "      ,[AGENT_TRANS_PAY_TYPE]\n" +
                "      ,[AGENT_TRANS_REV]\n" +
                "      ,[AGENT_TRANS_PAY]\n" +
                "      ,[AGENT_TRANS_COM]\n" +
                "      ,[AGENT_TRANS_BILL_DATE]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [AGENT_TRANS_VENDOR_CODE] = '" + vendorCode + "'" +
                "       AND [AGENT_TRANS_ACCTING_DATE] = '" + accDate + "'" +
                "       AND [AGENT_TRANS_ACCTING_WK] = '" + accWk + "'";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAgentSettlementsTable = new ArrayList<>();
        List<WebElement> sodtTable = driver.findElements(By.xpath("//*[@id=\"trReviewDetals\"]"));

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows: " + rows);
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
                    "\t" + res.getString(13));

            String a = res.getString(7);
            dbAgentSettlementsTable.add(a);


            boolean booleanValue = false;
            for (WebElement str : sodtTable) {
                if (str.getText().contains(a)) {
                    for (String str1 : dbAgentSettlementsTable) {
                        if (str1.contains(a)) {
                            System.out.println(a);
                            System.out.println();
                            booleanValue = true;
                            break;
                        }
                    }

                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on Show Only Order with Flags, Get the records returned and click again on Show Only Order with Flags$")
    public void Click_on_Show_Only_Order_with_Flags_Get_the_records_returned_and_click_again_on_Show_Only_Order_with_Flags() throws InterruptedException {
        driver.findElement(agentSettlingPage.ShowOnlyOrderWithFlags).click();
        Thread.sleep(1000);
        System.out.println("=========================================");
        System.out.println("Orders with Flag:");
        log.info(driver.findElement(agentSettlingPage.TableShowOnlyOrderWithFlags).getText());
        driver.findElement(agentSettlingPage.ShowOnlyOrderWithFlags).click();
    }

    @And("^Click on Show Next Week Orders, Get the records returned and click again on Show Next Week Orders$")
    public void Click_on_Show_Next_Week_Orders_Get_the_records_returned_and_click_again_on_Show_Next_Week_Orders() throws InterruptedException {
        driver.findElement(agentSettlingPage.ShowNextWeekOrders).click();
        System.out.println("=========================================");
        Thread.sleep(1000);
        System.out.println("Next Week Orders:");
        log.info(driver.findElement(agentSettlingPage.TableShowOnlyOrderWithFlags).getText());
    }


    @And("^Validate Records Returned on Show Next Week Orders with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_Records_Returned_on_Show_Next_Week_Orders_with_Database_Record_and(String environment, String tableName, String proNum1) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 " +
                "       [STL_ORDER_ID]\n" +
                "       ,[STL_PRONUM]\n" +
                "       ,[STL_PAYDED_CODE]\n" +
                "       ,[STL_REV]\n" +
                "       ,[STL_PAY]\n" +
                "       ,[STL_COM]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [STL_PRONUM] = '" + proNum1 + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAgentSettlementsTable = new ArrayList<>();
        List<WebElement> sodtTable = driver.findElements(By.xpath("//*[@id=\"trReviewDetals\"]"));

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows: " + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6));

            String a = res.getString(2);
            dbAgentSettlementsTable.add(a);

            boolean booleanValue = false;
            for (WebElement str : sodtTable) {
                if (str.getText().contains(a)) {
                    for (String str1 : dbAgentSettlementsTable) {
                        if (str1.contains(a)) {
                            System.out.println(a);
                            System.out.println();
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on Show Previous Week Orders, Get the records returned and click again on Show Previous Week Orders$")
    public void Click_on_Show_Previous_Week_Orders_Get_the_records_returned_and_click_again_on_Show_Previous_Week_Orders() throws InterruptedException {
        driver.findElement(agentSettlingPage.ShowPreviousWeekOrders).click();
        System.out.println("=========================================");
        Thread.sleep(1000);
        System.out.println("Previous Week Orders:");
        log.info(driver.findElement(agentSettlingPage.TableShowOnlyOrderWithFlags).getText());
        driver.findElement(agentSettlingPage.ShowPreviousWeekOrders).click();
    }


    //........................................./ 7  @AgentsYoureSettlingAddedRemoved /.............................................//

    @And("^Add Vendor Code as \"([^\"]*)\" in Agent's You're Settling, Vendor Code Column then Click on Add$")
    public void Add_Vendor_Code_as_in_Agent_s_You_re_Settling_Vendor_Code_Column_then_Click_on_Add(String code) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).sendKeys(code);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial\"]")).click();
        driver.findElement(agentSettlingPage.Add).click();
    }

    @And("^Click Yes on Conformation PopUp$")
    public void click_Yes_on_Conformation_PopUp() throws InterruptedException {
        driver.findElement(agentSettlingPage.Confirmation).isDisplayed();
        driver.findElement(agentSettlingPage.ConfirmationYes).click();
        Thread.sleep(3000);
    }

    @And("^Navigate to the Settlements page on Agent Settlement$")
    public void navigate_to_the_Settlements_page_on_Agent_Settlement() {
        driver.findElement(By.linkText("Settlements")).click();
    }

    @And("^Verify Vendor \"([^\"]*)\" is present in Agent's You're Settling Table$")
    public void Verify_Vendor_is_present_in_Agent_s_You_re_Settling_Table(String code) throws InterruptedException {
        Thread.sleep(10000);
        boolean bolnValue = false;
        WebElement table = driver.findElement(By.xpath("//*[@id=\"tblAgentsSettlingMain\"]/tbody"));
        List<WebElement> rowsList = table.findElements(By.tagName("tr"));
        List<WebElement> columnsList = null;
        for (WebElement row : rowsList) {
            columnsList = row.findElements(By.tagName("td"));
            for (WebElement column : columnsList) {
                System.out.println(column.getText() + ",");

                if (column.getText().contains(code)) {
                    bolnValue = true;
                }
                break;
            }
        }
        if (bolnValue) {
            Assert.assertTrue("String was present", true);
        } else {
            fail("String was not present");
        }
        System.out.println("Vendor Code is Added !!");
    }

    @And("^Verify Vendor \"([^\"]*)\" added before is removed from Agent's You're Settling, Vendor Code Column$")
    public void Verify_Vendor_added_before_is_removed_from_Agent_s_You_re_Settling_Vendor_Code_Column(String code) throws InterruptedException {
        boolean bolnValue = true;
        Thread.sleep(1000);
        WebElement table = driver.findElement(By.xpath("//*[@id=\"tblAgentsSettlingMain\"]/tbody"));

        List<WebElement> rowsList = table.findElements(By.tagName("tr"));
        List<WebElement> columnsList = null;
        for (WebElement row : rowsList) {
            columnsList = row.findElements(By.tagName("td"));
            for (WebElement column : columnsList) {
                System.out.println(column.getText() + ",");
                if (column.getText().contains(code)) {
                    bolnValue = false;
                }
                break;
            }
            if (bolnValue) {
                Assert.assertTrue("String was removed", true);
            } else {
                fail("String is present");
            }
        }
        System.out.println("Vendor Code is Removed !!");
    }


    //......................................./ 8 @AdjustmentsDetailsAndAdjustmentsOnHold /........................................//

    @And("^Get the values of Adjustment's Details Table$")
    public void get_the_values_of_Adjustment_s_Details_Table() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("Adjustment's Details:");
        log.info(driver.findElement(agentSettlingPage.AdjustmentsDetails).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
    }

    @And("^Validate Adjustment's Details with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_Adjustment_s_Details_with_Database_Record_and(String environment, String tableName, String vendorCode, String orderNum) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 [AGENT_ADJUST_VENDOR_CODE]\n" +
                "       ,[AGENT_ADJUST_PAY_CODE]\n" +
                "       ,[AGENT_ADJUST_STATUS]\n" +
                "       ,[AGENT_ADJUST_FREQ]\n" +
                "       ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "       ,[AGENT_ADJUST_AMOUNT]\n" +
                "       ,[AGENT_ADJUST_START_DATE]\n" +
                "       ,[AGENT_ADJUST_NOTE]\n" +
                "       ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "       ,[AGENT_ADJUST_ID]\n" +
                "       ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "       ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " " +
                "   WHERE [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   AND [AGENT_ADJUST_ORDER_NO] = '" + orderNum + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<WebElement> adjustmentDetailsTable = driver.findElements(agentSettlingPage.AdjustmentsDetails);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12));

            String a = rs.getString(2);
            dbAgentAdjustmentsTable.add(a);

            String b = rs.getString(11);
            dbAgentAdjustmentsTable1.add(b);

            boolean booleanValue = false;
            for (WebElement adTable : adjustmentDetailsTable) {
                if (adTable.getText().contains(a)) {
                    System.out.println(a);
                    for (String dbaaTable : dbAgentAdjustmentsTable) {
                        if (dbaaTable.contains(a)) {
                            System.out.println(a);
                        }
                    }
                    booleanValue = true;
                    break;
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            boolean booleanValue1 = false;
            for (WebElement adTable1 : adjustmentDetailsTable) {
                if (adTable1.getText().contains(b)) {
                    for (String dbaaTable1 : dbAgentAdjustmentsTable1) {
                        if (dbaaTable1.contains(b)) {
                            System.out.println(b);
                            System.out.println();
                        }
                    }
                    booleanValue1 = true;
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @And("^Validate Adjustments on Hold$")
    public void Validate_Adjustments_on_Hold() {
        System.out.println("=========================================");
        System.out.println("Adjustments on Hold:");
        log.info(driver.findElement(agentSettlingPage.AdjustmentsOnHold).getText());
    }


    //............................................/ 9 @MyActiveAgentsNotSettled /................................................//

    @And("^Click on Reload All My Active Agents Not Settled$")
    public void Click_on_Reload_All_My_Active_Agents_Not_Settled() {
        driver.findElement(agentSettlingPage.ReloadAllMyActiveAgentsNotSettled).click();
    }

    @And("^Validate My Active Agents Not Settled for \"([^\"]*)\"$")
    public void Validate_My_Active_Agents_Not_Settled_for(String code) throws InterruptedException {
        System.out.println("=========================================");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("My Active Agents Not Settled:");
        log.info(driver.findElement(agentSettlingPage.SettlementSummary).getText());

        List<WebElement> myActive = (driver.findElements(agentSettlingPage.SettlementSummary));
        boolean booleanValue = false;
        for (WebElement myActive1 : myActive) {
            if (myActive1.getText().contains(code)) {
                System.out.println(code);
                booleanValue = true;
            }
            break;
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
            System.out.println("Vendor Code is present !!");
        } else {
            fail("Vendor Code is not present!!");
        }
        System.out.println("=========================================");
    }

    @And("^Validate My Active Agents Not Settled with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_My_Active_Agents_Not_Settled_with_Database_Record_and(String environment, String tableName, String userId, String acctingDt, String acctingWk, String vendorCode) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + userId + "");
        cstmt.setString(2, "" + acctingDt + "");
        cstmt.setString(3, "" + acctingWk + "");


        List<String> dbSSTable = new ArrayList<>();
        List<String> dbACMTable1 = new ArrayList<>();
        List<WebElement> ssTable = driver.findElements(By.xpath("//*[@id=\"tblSettlementSummary\"]/tbody"));

        List<String> stringBuilderTable = new ArrayList<>();


        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);


        while (rs.next()) {
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(14));


            String a = rs.getString(3);
            dbSSTable.add(a);

            StringBuilder sb = new StringBuilder();
            sb.append(rs.getString(3)).append(" ").append(rs.getString(7)).append(" ").append(rs.getString(8)).append(" ").append(rs.getString(9)).append(" ").append(rs.getString(14));
            stringBuilderTable.add(sb.toString());

            System.out.println("Here is the Combination:  " + sb.toString());


            boolean booleanValue1 = false;
            for (WebElement sstable : ssTable) {
                if (sstable.getText().contains(a)) {
                    for (String dbssTable : dbSSTable) {
                        if (dbssTable.contains(a)) {
                            System.out.println("Vendor Code is present: " + a);
                            System.out.println();
                            booleanValue1 = true;
                        }
                    }
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }

        boolean booleanValue = false;
        for (WebElement sstable : ssTable) {
            if (sstable.getText().contains(vendorCode)) {
                for (String dbssTable : dbSSTable) {
                    if (dbssTable.contains(vendorCode)) {
                        System.out.println("Vendor Code is present: " + vendorCode);
                        booleanValue = true;
                    }
                }
                break;
            }

            if (booleanValue) {
                Assert.assertTrue("Assertion Passed!!", true);
            } else {
                fail("Assertion Failed!!");
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 13 @AgentSettlementInquiryAgent /................................................//

    @Given("^Navigate to Agent Settlement Inquiry$")
    public void Navigate_to_Agent_Settlement_Inquiry() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(settlementsPage.AgentSettlementInquiry).click();
        Thread.sleep(8000);
    }

    @And("^Enter Agent as \"([^\"]*)\" and click$")
    public void Enter_Agent_as_and_click(String agent) {
        driver.findElement(agentSettlementInquiryPage.AgentSearchBox).sendKeys(agent);
        driver.findElement(agentSettlementInquiryPage.AgentSearchBox).click();
    }

    @And("^Click on Search Button for Agent$")
    public void Click_on_Search_Button_for_Agent() throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        Thread.sleep(4000);
    }


    @And("^Click on COM on Trans Type and validate total records returned on Agent as \"([^\"]*)\"$")
    public void Click_on_COM_on_Trans_Type_and_validate_total_records_returned_on_Agent_as(String agent) throws
            InterruptedException {
        Thread.sleep(3000);
        driver.findElement(agentSettlementInquiryPage.ComTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(3000);
        System.out.println("Agent " + agent + " total records returned on clicking COM: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Agent " + agent + " Records Returned on clicking COM: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @And("^Validate Records Returned on Agent when COM on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Agent_when_COM_on_Trans_Type_with_Database_Record_and(String
                                                                                                           environment, String tableName, String agentCode, String drBegin, String drEnd) throws
            Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "COM");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_COM");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIACOMTable = new ArrayList<>();
        List<String> dbASIACOMTable1 = new ArrayList<>();
        List<String> dbASIACOMTable2 = new ArrayList<>();
        List<WebElement> aSIACOMTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIACOMTable.add(a);

            boolean booleanValue = false;
            for (WebElement asiacomTable : aSIACOMTable) {
                if (asiacomTable.getText().contains(a)) {
                    for (String dbasiacomTable : dbASIACOMTable) {
                        if (dbasiacomTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIACOMTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asiacomTable1 : aSIACOMTable) {
                if (asiacomTable1.getText().contains(b)) {
                    for (String dbasiacomTable1 : dbASIACOMTable1) {
                        if (dbasiacomTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIACOMTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asiacomTable2 : aSIACOMTable) {
                if (asiacomTable2.getText().contains(c)) {
                    for (String dbasiacomTable2 : dbASIACOMTable2) {
                        if (dbasiacomTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue2 = true;
                        }
                    }
                    break;
                }
                if (booleanValue2) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on ADJ on Trans Type and validate total records returned on Agent as \"([^\"]*)\"$")
    public void Click_on_ADJ_on_Trans_Type_and_validate_total_records_returned_on_Agent_as(String agent) throws
            InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AdjTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(5000);
        System.out.println("Agent " + agent + " total records returned on clicking ADJ: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Agent " + agent + " Records Returned on clicking ADJ: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @And("^Validate Records Returned on Agent when ADJ on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Agent_when_ADJ_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String agentCode, String drBegin, String drEnd) throws
            Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "ADJ");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_COM");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIAADJTable = new ArrayList<>();
        List<String> dbASIAADJTable1 = new ArrayList<>();
        List<String> dbASIAADJTable2 = new ArrayList<>();
        List<WebElement> aSIAADJTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIAADJTable.add(a);

            boolean booleanValue = false;
            for (WebElement asiaadjTable : aSIAADJTable) {
                if (asiaadjTable.getText().contains(a)) {
                    for (String dbasiaadjTable : dbASIAADJTable) {
                        if (dbasiaadjTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIAADJTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asiaadjTable1 : aSIAADJTable) {
                if (asiaadjTable1.getText().contains(b)) {
                    for (String dbasiaadjTable1 : dbASIAADJTable1) {
                        if (dbasiaadjTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIAADJTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asiaadjTable2 : aSIAADJTable) {
                if (asiaadjTable2.getText().contains(c)) {
                    for (String dbasiaadjTable2 : dbASIAADJTable2) {
                        if (dbasiaadjTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue2 = true;
                        }
                    }
                    break;
                }
                if (booleanValue2) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on All on Trans Type and validate total records returned on Agent as \"([^\"]*)\"$")
    public void Click_on_All_on_Trans_Type_and_validate_total_records_returned_on_Agent_as(String agent) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AllTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Agent " + agent + " total records returned on clicking ALL: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Agent " + agent + " Records Returned on clicking ALL: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @And("^Validate Records Returned on Agent when All on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Agent_when_All_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String agentCode, String drBegin, String drEnd) throws
            Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "ALL");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_COM");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIAADJTable = new ArrayList<>();
        List<String> dbASIAADJTable1 = new ArrayList<>();
        List<String> dbASIAADJTable2 = new ArrayList<>();
        List<WebElement> aSIAADJTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIAADJTable.add(a);

            boolean booleanValue = false;
            for (WebElement asiaadjTable : aSIAADJTable) {
                if (asiaadjTable.getText().contains(a)) {
                    for (String dbasiaadjTable : dbASIAADJTable) {
                        if (dbasiaadjTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIAADJTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asiaadjTable1 : aSIAADJTable) {
                if (asiaadjTable1.getText().contains(b)) {
                    for (String dbasiaadjTable1 : dbASIAADJTable1) {
                        if (dbasiaadjTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIAADJTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asiaadjTable2 : aSIAADJTable) {
                if (asiaadjTable2.getText().contains(c)) {
                    for (String dbasiaadjTable2 : dbASIAADJTable2) {
                        if (dbasiaadjTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue2 = true;
                        }
                    }
                    break;
                }
                if (booleanValue2) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


//............................................/ 14 @AgentSettlementInquiryVendor /................................................//

    @And("^Enter Vendor as \"([^\"]*)\" and click$")
    public void Enter_Vendor_as_and_click(String vendor) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.VendorSearchBox).sendKeys(vendor);
        driver.findElement(agentSettlementInquiryPage.VendorSearchBox).click();
        driver.findElement(By.xpath("//*[@id=\"singleSelectComboBoxVendor_DDD_L_LBI0T0\"]")).click();
        Thread.sleep(1000);
    }

    @And("^Click on Search Button for Vendor$")
    public void Click_on_Search_Button_for_Vendor() {
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
    }


    @Given("^Click on COM on Trans Type and validate total records returned on Vendor as \"([^\"]*)\"$")
    public void click_on_COM_on_Trans_Type_and_validate_total_records_returned_on_Vendor_as(String vendorCode1) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.ComTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Vendor " + vendorCode1 + " total records returned on clicking COM: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Vendor " + vendorCode1 + " Records Returned on clicking COM: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @Given("^Validate Records Returned on Vendor when COM on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Vendor_when_COM_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String vendorCode, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "" + vendorCode + "");
        cstmt.setString(3, "");
        cstmt.setString(4, "COM");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIVCTable = new ArrayList<>();
        List<String> dbASIVCTable1 = new ArrayList<>();
        List<String> dbASIVCTable2 = new ArrayList<>();
        List<WebElement> aSIVCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIVCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asivcTable : aSIVCTable) {
                if (asivcTable.getText().contains(a)) {
                    for (String dbasivcTable : dbASIVCTable) {
                        if (dbasivcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIVCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asivcTable1 : aSIVCTable) {
                if (asivcTable1.getText().contains(b)) {
                    for (String dbasivcTable1 : dbASIVCTable1) {
                        if (dbasivcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIVCTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asivcTable2 : aSIVCTable) {
                if (asivcTable2.getText().contains(c)) {
                    for (String dbasivcTable2 : dbASIVCTable2) {
                        if (dbasivcTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue1 = true;
                        }
                    }
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("^Click on ADJ on Trans Type and validate total records returned on Vendor as \"([^\"]*)\"$")
    public void click_on_ADJ_on_Trans_Type_and_validate_total_records_returned_on_Vendor_as(String vendorCode1) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AdjTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Vendor " + vendorCode1 + " total records returned on clicking ADJ: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Vendor " + vendorCode1 + " Records Returned on clicking ADJ: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @Given("^Validate Records Returned on Vendor when ADJ on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Vendor_when_ADJ_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String vendorCode, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "" + vendorCode + "");
        cstmt.setString(3, "");
        cstmt.setString(4, "ADJ");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIVCTable = new ArrayList<>();
        List<String> dbASIVCTable1 = new ArrayList<>();
        List<String> dbASIVCTable2 = new ArrayList<>();
        List<WebElement> aSIVCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIVCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asivcTable : aSIVCTable) {
                if (asivcTable.getText().contains(a)) {
                    for (String dbasivcTable : dbASIVCTable) {
                        if (dbasivcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIVCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asivcTable1 : aSIVCTable) {
                if (asivcTable1.getText().contains(b)) {
                    for (String dbasivcTable1 : dbASIVCTable1) {
                        if (dbasivcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIVCTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asivcTable2 : aSIVCTable) {
                if (asivcTable2.getText().contains(c)) {
                    for (String dbasivcTable2 : dbASIVCTable2) {
                        if (dbasivcTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue1 = true;
                        }
                    }
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("^Click on All on Trans Type and validate total records returned on Vendor as \"([^\"]*)\"$")
    public void click_on_All_on_Trans_Type_and_validate_total_records_returned_on_Vendor_as(String vendorCode1) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AllTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Vendor " + vendorCode1 + " total records returned on clicking ALL: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Vendor " + vendorCode1 + " Records Returned on clicking ALL: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @Given("^Validate Records Returned on Vendor when All on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Vendor_when_All_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String vendorCode, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "" + vendorCode + "");
        cstmt.setString(3, "");
        cstmt.setString(4, "ALL");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIVCTable = new ArrayList<>();
        List<String> dbASIVCTable1 = new ArrayList<>();
        List<String> dbASIVCTable2 = new ArrayList<>();
        List<WebElement> aSIVCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIVCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asivcTable : aSIVCTable) {
                if (asivcTable.getText().contains(a)) {
                    for (String dbasivcTable : dbASIVCTable) {
                        if (dbasivcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                            break;
                        }

                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIVCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asivcTable1 : aSIVCTable) {
                if (asivcTable1.getText().contains(b)) {
                    for (String dbasivcTable1 : dbASIVCTable1) {
                        if (dbasivcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(6);
            dbASIVCTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asivcTable2 : aSIVCTable) {
                if (asivcTable2.getText().contains(c)) {
                    for (String dbasivcTable2 : dbASIVCTable2) {
                        if (dbasivcTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue1 = true;
                        }
                    }
                    break;
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 15 @AgentSettlementInquiryPayCodes /................................................//

    @And("^Enter Pay Codes as \"([^\"]*)\" and click$")
    public void Enter_Pay_Codes_as_and_click(String payCodes) {
        driver.findElement(agentSettlementInquiryPage.PayCodesBox).sendKeys(payCodes);
        driver.findElement(agentSettlementInquiryPage.PayCodesBox).click();
    }

    @And("^Click on Search Button for Pay Codes$")
    public void Click_on_Search_Button_for_Pay_Codes() throws InterruptedException {
        Thread.sleep(1000);
        if (driver.findElement(agentSettlementInquiryPage.SearchButton).isDisplayed()) {
            driver.findElement(agentSettlementInquiryPage.SearchButton).click();
            Thread.sleep(1000);
        } else {
            driver.findElement(agentSettlementInquiryPage.Warning).isDisplayed();
            driver.findElement(agentSettlementInquiryPage.WarningYes).click();
        }
    }

    @Given("^Click on COM on Trans Type and validate total records returned on Pay Codes as \"([^\"]*)\"$")
    public void click_on_COM_on_Trans_Type_and_validate_total_records_returned_on_Pay_Codes_as(String paycodes) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.ComTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Pay Code " + paycodes + " total records returned on clicking COM: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Pay Code " + paycodes + " Records Returned on clicking COM: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }


    @Given("^Validate Records Returned on Pay Codes when COM on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Pay_Codes_when_COM_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String payCodes, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + payCodes + "");
        cstmt.setString(4, "COM");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIPCTable = new ArrayList<>();
        List<String> dbASIPCTable1 = new ArrayList<>();
        List<String> dbASIPCTable2 = new ArrayList<>();
        List<WebElement> aSIPCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIPCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asipcTable : aSIPCTable) {
                if (asipcTable.getText().contains(a)) {
                    for (String dbasipcTable : dbASIPCTable) {
                        if (dbasipcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIPCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asipcTable1 : aSIPCTable) {
                if (asipcTable1.getText().contains(b)) {
                    for (String dbasipcTable1 : dbASIPCTable1) {
                        if (dbasipcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = rs.getString(5);
            dbASIPCTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asipcTable2 : aSIPCTable) {
                if (asipcTable2.getText().contains(c)) {
                    for (String dbasipcTable2 : dbASIPCTable2) {
                        if (dbasipcTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue2 = true;
                        }
                    }
                    break;
                }
                if (booleanValue2) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("^Click on ADJ on Trans Type and validate total records returned on Pay Codes as \"([^\"]*)\"$")
    public void click_on_ADJ_on_Trans_Type_and_validate_total_records_returned_on_Pay_Codes_as(String paycodes) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AdjTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Pay Code " + paycodes + " total records returned on clicking ADJ: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Pay Code " + paycodes + " Records Returned on clicking ADJ: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }


    @Given("^Validate Records Returned on Pay Codes when ADJ on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Pay_Codes_when_ADJ_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String payCodes, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + payCodes + "");
        cstmt.setString(4, "ADJ");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIPCTable = new ArrayList<>();
        List<String> dbASIPCTable1 = new ArrayList<>();
        List<String> dbASIPCTable2 = new ArrayList<>();
        List<WebElement> aSIPCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIPCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asipcTable : aSIPCTable) {
                if (asipcTable.getText().contains(a)) {
                    for (String dbasipcTable : dbASIPCTable) {
                        if (dbasipcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIPCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asipcTable1 : aSIPCTable) {
                if (asipcTable1.getText().contains(b)) {
                    for (String dbasipcTable1 : dbASIPCTable1) {
                        if (dbasipcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            /*    String c = rs.getString(5);
                dbASIPCTable2.add(c);

                boolean booleanValue2 = false;
                for (WebElement asipcTable2 : aSIPCTable) {
                    if (asipcTable2.getText().contains(c)) {
                        for (String dbasipcTable2 : dbASIPCTable2) {
                            if (dbasipcTable2.contains(c)) {
                                System.out.println("Assertion Passed: " + c);
                                System.out.println();
                                booleanValue2 = true;
                                break;
                            }
                        }
                    }
                    if (booleanValue2) {
                        Assert.assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }  */
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("^Click on All on Trans Type and validate total records returned on Pay Codes as \"([^\"]*)\"$")
    public void click_on_All_on_Trans_Type_and_validate_total_records_returned_on_Pay_Codes_as(String paycodes) throws InterruptedException {
        driver.findElement(agentSettlementInquiryPage.AllTransType).click();
        driver.findElement(agentSettlementInquiryPage.SearchButton).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Pay Code " + paycodes + " total records returned on clicking ALL: " + driver.findElement(agentSettlementInquiryPage.CountTableRecord).getText());
        System.out.println("Pay Code " + paycodes + " Records Returned on clicking ALL: ");
        log.info(driver.findElement(agentSettlementInquiryPage.TableRecord).getText());
    }

    @Given("^Validate Records Returned on Pay Codes when All on Trans Type with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Pay_Codes_when_All_on_Trans_Type_with_Database_Record_and(String environment, String tableName, String payCodes, String drBegin, String drEnd) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + payCodes + "");
        cstmt.setString(4, "ALL");
        cstmt.setString(5, "Date Created");
        cstmt.setString(6, "" + drBegin + "");
        cstmt.setString(7, "" + drEnd + "");
        cstmt.setString(8, "");
        cstmt.setString(9, "");
        cstmt.setString(10, "1");
        cstmt.setString(11, "500");
        cstmt.setString(12, "AGENT_TRANS_TYPE");
        cstmt.setString(13, "asc");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASIPCTable = new ArrayList<>();
        List<String> dbASIPCTable1 = new ArrayList<>();
        List<String> dbASIPCTable2 = new ArrayList<>();
        List<WebElement> aSIPCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14));

            String a = rs.getString(7);
            dbASIPCTable.add(a);

            boolean booleanValue = false;
            for (WebElement asipcTable : aSIPCTable) {
                if (asipcTable.getText().contains(a)) {
                    for (String dbasipcTable : dbASIPCTable) {
                        if (dbasipcTable.contains(a)) {
                            System.out.println("Assertion Passed: " + a);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String b = rs.getString(2);
            dbASIPCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement asipcTable1 : aSIPCTable) {
                if (asipcTable1.getText().contains(b)) {
                    for (String dbasipcTable1 : dbASIPCTable1) {
                        if (dbasipcTable1.contains(b)) {
                            System.out.println("Assertion Passed: " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

         /*   String c = rs.getString(5);
            dbASIPCTable2.add(c);

            boolean booleanValue2 = false;
            for (WebElement asipcTable2 : aSIPCTable) {
                if (asipcTable2.getText().contains(c)) {
                    for (String dbasipcTable2 : dbASIPCTable2) {
                        if (dbasipcTable2.contains(c)) {
                            System.out.println("Assertion Passed: " + c);
                            System.out.println();
                            booleanValue2 = true;
                            break;
                        }
                    }
                }
                if (booleanValue2) {
                    Assert.assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }  */
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 16 @AgentsCurrentlyBeingSettledVendorCode /...........................................//

    @Given("^Navigate to Agents Currently Being Settled$")
    public void Navigate_to_Agents_Currently_Being_Settled() {
        driver.findElement(settlementsPage.AgentsCurrentlyBeingSettled).click();
    }

    @And("^Validate the records returned on Agents Currently Being Settled$")
    public void Validate_the_records_returned_on_Agents_Currently_Being_Settled() {
        String headerLoc = "//*[@id=\"dataTable\"]/thead/tr/th";
        List<WebElement> allHeadersEle = driver.findElements(By.xpath(headerLoc));
        List<String> allHeaderNames = new ArrayList<String>();
        for (WebElement header : allHeadersEle) {
            String headerName = header.getText();
            allHeaderNames.add(headerName);
        }

        List<LinkedHashMap<String, String>> allTableData = new ArrayList<LinkedHashMap<String, String>>();

        String rowLoc = "//*[@id=\"dataTable\"]/tbody/tr";
        List<WebElement> allRowsEle = driver.findElements(By.xpath(rowLoc));
        for (int i = 1; i <= allRowsEle.size(); i++) {
            String specificRowLoc = "//*[@id=\"dataTable\"]/tbody/tr[" + i + "]";
            List<WebElement> allColumnsEle = driver.findElement(By.xpath(specificRowLoc)).findElements(By.tagName("td"));

            LinkedHashMap<String, String> eachRowData = new LinkedHashMap<>();
            for (int j = 0; j < allColumnsEle.size(); j++) {
                String cellValue = allColumnsEle.get(j).getText();
                eachRowData.put(allHeaderNames.get(j), cellValue);
            }
            allTableData.add(eachRowData);
        }
        System.out.println("Table data are:-");
        System.out.println("=========================================");
        for (LinkedHashMap<String, String> data : allTableData) {
            for (String key : data.keySet()) {
                System.out.println(key + "      : " + data.get(key));
            }
            System.out.println("=========================================");
        }
    }

    @And("^Click on Filter Icon on Settling Vendor Code$")
    public void Click_on_Filter_Icon_on_Settling_Vendor_Code() {
        driver.findElement(agentsCurrentlyBeingSettledPage.FilterIconOnSettlingVendorCode).click();
    }

    @And("^Enter Vendor Code as \"([^\"]*)\"$")
    public void Enter_Vendor_Code_as(String vendorCode) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        System.out.println("Total Number of Vendor Code : " + list.size());
        for (WebElement webElement : list) {
            if (webElement.getText().contains(vendorCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(1000);
        System.out.println("Total records returned for Vendor Code " + vendorCode + " : " + driver.findElement(agentsCurrentlyBeingSettledPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentsCurrentlyBeingSettledPage.TableResult).getText());
    }

    @And("^Validate total Records returned for Vendor Code with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_total_Records_returned_for_Vendor_Code_with_Database_Record_and(String
                                                                                                 environment, String tableName, String vendorCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String beingSettled = " SELECT TOP 1000 [STId]\n" +
                "           ,[SETTLING_VENDOR_CODE]\n " +
                "           ,[SETTLING_AGENT_CODE]\n " +
                "           ,[SETTLING_LOGIN]\n" +
                "           ,[SETTLING_ACCTING_DT]\n" +
                "           ,[SETTLING_Vendor_VERIFIED]\n" +
                "           ,[SETTLING_ACCTING_WK]\n" +
                "           ,[SESSION_ID]\n" +
                "       FROM " + tableName + " " +
                "   WHERE [SETTLING_VENDOR_CODE] = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(beingSettled);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbSettlingTable = new ArrayList<>();
        List<String> dbSettlingTable1 = new ArrayList<>();
        List<WebElement> settlingTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getInt(1) +
                    "\t" + res.getInt(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));

            String a = res.getString(3);
            dbSettlingTable.add(a);

            boolean booleanValue = false;
            for (WebElement seTTlingTable : settlingTable) {
                if (seTTlingTable.getText().contains(a)) {
                    for (String dbsTable : dbSettlingTable) {
                        if (dbsTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = res.getString(7);
            dbSettlingTable1.add(b);

            boolean booleanValue1 = false;
            for (String dbaTable1 : dbSettlingTable1) {
                if (dbaTable1.contains(b)) {
                    for (WebElement seTTlingTable1 : settlingTable) {
                        if (seTTlingTable1.getText().contains(b)) {
                            System.out.println(b);
                        }
                    }
                    booleanValue1 = true;
                    break;
                }
                if (booleanValue1) {
                    assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 17 @AgentsCurrentlyBeingSettledAgentCode /...........................................//

    @And("^Click on Filter Icon on Settling Agent Code$")
    public void Click_on_Filter_Icon_on_Settling_Agent_Code() {
        driver.findElement(agentsCurrentlyBeingSettledPage.FilterIconOnSettlingAgentCode).click();
    }

    @And("^Enter Agent Code as \"([^\"]*)\"$")
    public void Enter_Agent_Code_as(String agentCode) throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        System.out.println("Total Number of Agent Code : " + list.size());
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
        System.out.println("Total records returned for Agent Code " + agentCode + " : " + driver.findElement(agentsCurrentlyBeingSettledPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentsCurrentlyBeingSettledPage.TableResult).getText());
        Thread.sleep(5000);
    }

    @And("^Validate total Records returned for Agent Code with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_total_Records_returned_for_Agent_Code_with_Database_Record_and(String
                                                                                                environment, String tableName, String agentCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String sql = " SELECT TOP 1000 [STId]\n" +
                "           ,[SETTLING_VENDOR_CODE]\n " +
                "           ,[SETTLING_AGENT_CODE]\n " +
                "           ,[SETTLING_LOGIN]\n" +
                "           ,[SETTLING_ACCTING_DT]\n" +
                "           ,[SETTLING_Vendor_VERIFIED]\n" +
                "           ,[SETTLING_ACCTING_WK]\n" +
                "           ,[SESSION_ID]\n" +
                "       FROM " + tableName + " " +
                "   WHERE [SETTLING_AGENT_CODE] = '" + agentCode + "'";

        ResultSet res = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbSettlingTable = new ArrayList<>();
        List<String> dbSettlingTable1 = new ArrayList<>();
        List<WebElement> settlingTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getInt(1) +
                    "\t" + res.getInt(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));

            String a = res.getString(2);
            dbSettlingTable.add(a);

            boolean booleanValue = false;
            for (String dbsTable : dbSettlingTable) {
                if (dbsTable.contains(a)) {
                    for (WebElement seTTlingTable : settlingTable) {
                        if (seTTlingTable.getText().contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = res.getString(7);
            dbSettlingTable1.add(b);

            boolean booleanValue1 = false;
            for (String dbaTable1 : dbSettlingTable1) {
                if (dbaTable1.contains(b)) {
                    for (WebElement seTTlingTable1 : settlingTable) {
                        if (seTTlingTable1.getText().contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 18 @AgentCommissionMaintenance /................................................//

    @And("^Navigate to the Agent Commission Maintenance page$")
    public void Navigate_to_the_Agent_Commission_Maintenance_page() {
        driver.findElement(settlementsPage.AgentCommissionMaintenance).click();
    }

    @And("^Pop up Menu arises insert password \"([^\"]*)\"$")
    public void Pop_up_Menu_arises_insert_password(String password) {
        driver.findElement(settlementsPage.EnterPasswordForAgentCommissionMaintenance).sendKeys(password);
    }

    @And("^Click on ok btn$")
    public void Click_on_ok_btn() {
        driver.findElement(settlementsPage.OkBtn).click();
    }

    @And("^Enter Agent/Location Code as \"([^\"]*)\" and click$")
    public void Enter_Agent_Location_Code_as_and_click(String code) throws InterruptedException {
        driver.findElement(agentCommissionMaintenancePage.agentLocationCode).sendKeys(code);
        driver.findElement(agentCommissionMaintenancePage.agentLocationCode).click();
        Thread.sleep(2000);
    }

    @And("^Validate Agent Code Description and Agent is active$")
    public void Validate_Agent_Code_Description_and_Agent_is_active() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        assertTrue(driver.findElement(agentCommissionMaintenancePage.agentCodeDescription).isDisplayed());
        System.out.println(driver.findElement(agentCommissionMaintenancePage.agentCodeDescription).getText());
        assertTrue(driver.findElement(agentCommissionMaintenancePage.agentIsActive).isDisplayed());
        System.out.println(driver.findElement(agentCommissionMaintenancePage.agentIsActive).getText());

        //   log.info("Agent Records Returned - " + driver.findElement(By.xpath("//*[@id=\"tblAgentCommMaintenance\"]/tbody")).getText());
    }


    @And("^Get Records of Agent Commission Maintenance$")
    public void Get_Records_of_Agent_Commission_Maintenance() throws InterruptedException {
        Thread.sleep(15000);
        System.out.println("=========================================");

        String headerLoc = "//*[@id=\"tblAgentCommMaintenance\"]/thead/tr[2]/th";
        List<WebElement> allHeadersEle = driver.findElements(By.xpath(headerLoc));
        List<String> allHeaderNames = new ArrayList<String>();
        for (WebElement header : allHeadersEle) {
            String headerName = header.getText();
            allHeaderNames.add(headerName);
        }

        List<LinkedHashMap<String, String>> allTableData = new ArrayList<LinkedHashMap<String, String>>();

        String rowLoc = "//*[@id=\"tblAgentCommMaintenance\"]/tbody/tr";
        List<WebElement> allRowsEle = driver.findElements(By.xpath(rowLoc));

        for (int i = 1; i <= allRowsEle.size(); i++) {

            String specificRowLoc = "//*[@id=\"tblAgentCommMaintenance\"]/tbody/tr[" + i + "]";

            List<WebElement> allColumnsEle = driver.findElement(By.xpath(specificRowLoc)).findElements(By.tagName("td"));
            LinkedHashMap<String, String> eachRowData = new LinkedHashMap<>();

            for (int j = 0; j < allColumnsEle.size(); j++) {
                //        String cellValue = allColumnsEle.get(j).getAccessibleName();
                //       eachRowData.put(allHeaderNames.get(j), cellValue);
            }
            allTableData.add(eachRowData);
        }
        System.out.println(allTableData);
        System.out.println("Table data are:-");
        System.out.println("=========================================");
        for (LinkedHashMap<String, String> data : allTableData) {
            for (String key : data.keySet()) {
                System.out.println(key + "      : " + data.get(key));
            }
            System.out.println("=========================================");
        }
    }


    @And("^Validate Assert Values On Agent Commission Maintenance with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Assert_Values_On_Agent_Commission_Maintenance_with_Database_Record_and(String environment, String tableName, String agentCode, String assertValue, String assertValue1, String assertValue2) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");

        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "");
        cstmt.setString(5, "");
        cstmt.setString(6, "");
        cstmt.setString(7, "0");

        List<String> stringBuilderTable = new ArrayList<>();

        ResultSet rs1 = cstmt.executeQuery();
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs1.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs1.next()) {
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19));
            System.out.println();
        }
        cstmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs2 = cstmt.getResultSet();
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);
        while (rs2.next()) {
            System.out.println(rs2.getString(1) +
                    "\t" + rs2.getString(2) +
                    "\t" + rs2.getString(3) +
                    "\t" + rs2.getString(4) +
                    "\t" + rs2.getString(5) +
                    "\t" + rs2.getString(6) +
                    "\t" + rs2.getString(7) +
                    "\t" + rs2.getString(8) +
                    "\t" + rs2.getString(9) +
                    "\t" + rs2.getString(10) +
                    "\t" + rs2.getString(11) +
                    "\t" + rs2.getString(12) +
                    "\t" + rs2.getString(13) +
                    "\t" + rs2.getString(14) +
                    "\t" + rs2.getString(15) +
                    "\t" + rs2.getString(16) +
                    "\t" + rs2.getString(17) +
                    "\t" + rs2.getString(18) +
                    "\t" + rs2.getString(19));


            StringBuilder sb = new StringBuilder();
            sb.append(rs2.getString(3)).append(" ").append(rs2.getString(4)).append(" ").append(rs2.getString(5)).append(" ").append(rs2.getString(9));
            stringBuilderTable.add(sb.toString());

            System.out.println("Here is the Combination:  " + sb.toString());
            System.out.println();

        }

        if (stringBuilderTable.contains(assertValue)) {
            System.out.println("This Combination is True for " + agentCode + ": " + assertValue);
        } else
            fail(String.valueOf(Boolean.parseBoolean("This Combination is False for " + agentCode + ": " + assertValue)));


        if (stringBuilderTable.contains(assertValue2)) {
            System.out.println("This Combination is True for " + agentCode + ": " + assertValue2);
        } else
            fail(String.valueOf(Boolean.parseBoolean("This Combination is False for " + agentCode + ": " + assertValue2)));


        //    if (stringBuilderTable.contains(assertValue1)) {
        //        System.out.println("This Combination is True for " + agentCode + ": " + assertValue1);
        //    } else
        //        fail(String.valueOf(Boolean.parseBoolean("This Combination is False for " + agentCode + ": " + assertValue1)));

        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 19 @AgentCommissionCalculation /................................................//

    @Given("^Navigate to Agent Commission Calculation$")
    public void Navigate_to_Agent_Commission_Calculation() {
        driver.findElement(settlementsPage.AgentCommissionCalculation).click();
    }

    @And("^Validate you are on Agent Commission Calculation page$")
    public void Validate_you_are_on_Agent_Commission_Calculation_page() throws InterruptedException {
        Thread.sleep(4000);
        assertTrue(driver.findElement(agentCommissionCalculationPage.TableHead).isDisplayed());
        System.out.println(driver.findElement(agentCommissionCalculationPage.TableHead).getText());
    }

    @And("^Enter Pro Number as \"([^\"]*)\"$")
    public void enter_Pro_Number_as(String pronumber) throws InterruptedException {
        driver.findElement(agentCommissionCalculationPage.enterProNumber).sendKeys(pronumber);
        Thread.sleep(4000);
    }

    @And("^Click on Calculate Commission$")
    public void Click_on_Calculate_Commission() {
        driver.findElement(agentCommissionCalculationPage.CalculateCommission).click();
    }

    @And("^Validate Vendor Code and Total Commission$")
    public void Validate_Vendor_Code_and_Total_Commission() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(driver.findElement(agentCommissionCalculationPage.VendorCode).getText());
        System.out.println(driver.findElement(agentCommissionCalculationPage.TotalCommission).getText());
    }

    @And("^Validate the records returned on Agent Commission Calculation with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_records_returned_on_Agent_Commission_Calculation_with_Database_Record_and
            (String environment, String tableName, String agentCode, String orderNum) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {


        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");

        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + "(?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "" + orderNum + "");
        cstmt.setString(3, "");
        cstmt.setString(4, "");

        List<String> dbACCTable = new ArrayList<>();
        List<String> dbACCTable1 = new ArrayList<>();
        List<WebElement> aCCTable = driver.findElements(By.xpath("//*[@id=\"tblAgentCommission\"]/tbody"));


        ResultSet rs1 = cstmt.executeQuery();
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs1.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs1.next()) {
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3));

            System.out.println();
        }
        cstmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs2 = cstmt.getResultSet();
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        while (rs2.next()) {
            System.out.println(rs2.getString(1) +
                    "\t" + rs2.getString(2) +
                    "\t" + rs2.getString(3));
            System.out.println();
        }

        cstmt.getMoreResults();
        System.out.println("Contents of the third result-set: ");
        ResultSet rs3 = cstmt.getResultSet();
        ResultSetMetaData rsmd3 = rs3.getMetaData();
        int count3 = rsmd3.getColumnCount();
        List<String> columnList3 = new ArrayList<String>();
        for (int i = 1; i <= count3; i++) {
            columnList3.add(rsmd3.getColumnLabel(i));
        }
        System.out.println(columnList3);

        while (rs3.next()) {
            System.out.println(rs3.getString(1) +
                    "\t" + rs3.getString(2) +
                    "\t" + rs3.getString(3) +
                    "\t" + rs3.getString(4) +
                    "\t" + rs3.getString(5) +
                    "\t" + rs3.getString(6) +
                    "\t" + rs3.getString(7) +
                    "\t" + rs3.getString(10) +
                    "\t" + rs3.getString(20));

            String a = rs3.getString(3);
            dbACCTable.add(a);

            boolean booleanValue = false;
            for (WebElement accTable : aCCTable) {
                if (accTable.getText().contains(a)) {
                    for (String dbaccTable : dbACCTable) {
                        if (dbaccTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs3.getString(4);
            dbACCTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement accTable1 : aCCTable) {
                if (accTable1.getText().contains(b)) {
                    for (String dbaccTable1 : dbACCTable1) {
                        if (dbaccTable1.contains(b)) {
                            System.out.println(b);
                            System.out.println();
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on Display Matrix$")
    public void Click_on_Display_Matrix() {
        driver.findElement(agentCommissionCalculationPage.DisplayMatrix).click();
    }

    @And("^Popup Menu arises insert password \"([^\"]*)\"$")
    public void popup_Menu_arises_insert_password(String password) throws InterruptedException {
        getAndSwitchToWindowHandles();
        Thread.sleep(2000);
        driver.findElement(agentCommissionCalculationPage.WarningIsDisplayed).isDisplayed();
        driver.findElement(agentCommissionCalculationPage.EnterPasswordForAgentCommissionMaintenance).sendKeys(password);
    }

    @And("^Click on Ok button$")
    public void Click_on_Ok_button() {
        driver.findElement(agentCommissionCalculationPage.OkBtn).click();
    }

    @And("^Validate Primary Vendor Name and Primary Vendor Code$")
    public void Validate_Primary_Vendor_Name_and_Primary_Vendor_Code() {
        //   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4000));
        //   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"lblPrimaryVendorDesc\"]")));
        assertTrue(driver.findElement(By.xpath("//*[@id=\"lblPrimaryVendorDesc\"]")).isDisplayed());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblPrimaryVendorDesc\"]")).getText());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"PrimaryVendorList\"]/tbody/tr/td[2]")).isDisplayed());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"PrimaryVendorList\"]/tbody/tr/td[2]")).getText());
    }



//............................................/ 22 @AgentSettlementFlagMaintenance /................................................//

    @Given("^Navigate to the Agent Settlement Flag Maintenance$")
    public void Navigate_to_the_Agent_Settlement_Flag_Maintenance() {
        driver.findElement(settlementsPage.AgentSettlementFlagMaintenance).click();
    }

    @And("^Validate total records returned$")
    public void Validate_total_records_returned() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Agent Settlement Flag Maintenance : " + driver.findElement(agentSettlementFlagMaintenancePage.TotalRecordsReturned).getText());
    }

    @And("^Validate total records returned with Database Record \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validate_total_records_returned_with_Database_Record_and(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " }";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        ResultSet rs1 = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASFMTable = new ArrayList<>();
        List<WebElement> aSFMTable = driver.findElements(By.xpath("//*[@id=\"tblAgentflagmaintenance\"]/tbody"));

        while (rs1.next()) {
            int rows = rs1.getRow();
            System.out.println("Number of Rows: " + rows);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13));

            String a = rs1.getString(9);
            dbASFMTable.add(a);

            boolean booleanValue = false;
            for (WebElement str1 : aSFMTable) {
                if (str1.getText().contains(a)) {
                    for (String str : dbASFMTable) {
                        if (str.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


//............................................/ 23 @DBAgentSettlements&AgentStlTrans /................................................//

    @Given("^Connect to \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" and Validate the records for AGENT SETTLEMENTS and AGENT_STL_TRANS$")
    public void connect_to_and_and_and_Validate_the_records_for_AGENT_SETTLEMENTS_and_AGENT_STL_TRANS
            (String environment, String tableName, String tableName1, String code) throws Throwable {

        System.out.println("=========================================");
        System.out.println("Connecting to Database [EBHLaunch].[dbo].[AGENT_SETTLEMENTS] and [EBHLaunch].[dbo].[AGENT_STL_TRANS]..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 " +
                "       [STL_ORDER_ID]\n" +
                "       ,[STL_PRONUM]\n" +
                "       ,[STL_PAYDED_CODE]\n" +
                "       ,[STL_REV]\n" +
                "       ,[STL_PAY]\n" +
                "       ,[STL_COM]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [STL_PRONUM] = '" + code + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAgentSettlementsTable = new ArrayList<>();
        List<String> dbAgentSettlementsTable1 = new ArrayList<>();
        List<String> dbAgentSettlementsTable2 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows dbAgentSettlementsTable:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6));
            System.out.println();

            String a = res.getString(1);
            dbAgentSettlementsTable.add(a);

            String b = res.getString(2);
            dbAgentSettlementsTable1.add(b);

            String c = res.getString(3);
            dbAgentSettlementsTable2.add(c);
        }

        System.out.println("=========================================");
        stmt = connectionToDatabase.createStatement();
        String query1 = "SELECT TOP 1000 " +
                "       [AGENT_TRANS_ORDER_ID]\n" +
                "       ,[AGENT_TRANS_ORDER_NUM]\n" +
                "       ,[AGENT_TRANS_PAY_TYPE]\n" +
                "       ,[AGENT_TRANS_REV]\n" +
                "       ,[AGENT_TRANS_PAY]\n" +
                "       ,[AGENT_TRANS_COM]\n" +
                "       FROM " + tableName1 + " " +
                "       WHERE [AGENT_TRANS_ORDER_NUM] = '" + code + "'";

        ResultSet res1 = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = res1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<String> dbAgentStlTransTable = new ArrayList<>();
        List<String> dbAgentStlTransTable1 = new ArrayList<>();
        List<String> dbAgentStlTransTable2 = new ArrayList<>();

        while (res1.next()) {
            int rows1 = res1.getRow();
            System.out.println("Number of Rows dbAgentStlTransTable:" + rows1);
            System.out.println(res1.getString(1) +
                    "\t" + res1.getString(2) +
                    "\t" + res1.getString(3) +
                    "\t" + res1.getString(4) +
                    "\t" + res1.getString(5) +
                    "\t" + res1.getString(6));


            String a1 = res1.getString(1);
            dbAgentStlTransTable.add(a1);

            boolean booleanValue = false;
            for (String st : dbAgentSettlementsTable) {
                if (st.contains(a1)) {
                    for (String tt : dbAgentStlTransTable) {
                        if (tt.contains(a1)) {
                            System.out.println("Order ID matches in both Tables: " + a1);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Order ID matches!!", true);
                } else {
                    fail("Order ID doesn't match!!");
                }
            }

            String b1 = res1.getString(2);
            dbAgentStlTransTable1.add(b1);

            boolean booleanValue1 = false;
            for (String st1 : dbAgentSettlementsTable1) {
                if (st1.contains(b1)) {
                    for (String tt1 : dbAgentStlTransTable1) {
                        if (tt1.contains(b1)) {
                            System.out.println("Order Number matches in both Tables: " + b1);
                            booleanValue1 = true;
                        }
                    }
                    break;
                }
                if (booleanValue1) {
                    assertTrue("Order Number matches!!", true);
                } else {
                    fail("Order Number doesn't match!!");
                }
            }

            String c1 = res1.getString(3);
            dbAgentStlTransTable2.add(c1);

            boolean booleanValue2 = false;
            for (String st2 : dbAgentSettlementsTable2) {
                if (st2.contains(c1)) {
                    for (String tt2 : dbAgentStlTransTable2) {
                        if (tt2.contains(c1)) {
                            System.out.println("Pay Type matches in both Tables: " + c1);
                            System.out.println();
                            booleanValue2 = true;
                        }
                    }
                    break;
                }
            }
            if (booleanValue2) {
                Assert.assertTrue("Pay Type Matches !!", true);
            } else {
                fail("Pay Type doesn't match!!");
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 24 @DBOrderDocs&TfDocDataD4 /................................................//

    @Given("^Connect to \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and Validate the records for ORDER_DOCS and Connect to \"([^\"]*)\" \"([^\"]*)\" and Validate the records for tf_doc_data$")
    public void connect_to_and_and_Validate_the_records_for_ORDER_DOCS_and_Connect_to_and_Validate_the_records_for_tf_doc_data
            (String environment, String tableName, String ordLoc, String ordNum, String environment1, String
                    tableName1) throws Throwable {

        System.out.println("=========================================");
        System.out.println("Connecting to Database [EBHLaunch].[dbo].[ORDER_DOCS] ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        try {
            stmt = connectionToDatabase.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT TOP 1000 [DOCS_ID]" +
                "           ,[DOCS_ORD_LOC]\n" +
                "           ,[DOCS_ORD_NUM]\n" +
                "           ,[DOCS_DATE]\n" +
                "           ,[DOCS_CREATION_DATE]\n" +
                "           ,[DOCS_UPDATE_DATE]\n" +
                "           ,[DOCS_CREATION_AGENT_LOGIN]\n" +
                "           ,[DOCS_CREATION_AGENT_LOGIN]\n" +
                "           ,[DOCS_UPDATE_AGENT_LOGIN]\n" +
                "           ,[DOCS_TRACTORID]\n" +
                "           ,[DOC_TYPE]\n" +
                "           ,[DOC_DELIV_METHOD]\n" +
                "           ,[DOCS_ORD_ID]\n" +
                "       FROM " + tableName + " " +
                "       as od with(nolock) where DOCS_ORD_LOC = '" + ordLoc + "' and DOCS_ORD_NUM = '" + ordNum + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList1.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<String> dbOrderDocsTable = new ArrayList<>();
        List<String> dbOrderDocsTable1 = new ArrayList<>();
        List<String> dbOrderDocsTable2 = new ArrayList<>();

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
                    "\t" + res.getString(13));
            System.out.println();

            String a = res.getString(2);
            dbOrderDocsTable.add(a);

            String b = res.getString(3);
            dbOrderDocsTable1.add(b);

            String c = res.getString(11);
            dbOrderDocsTable2.add(c);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");


        System.out.println("Connecting to Database [TRANSFLO].[dbo].[tf_doc_data_d4] ..............................");
        Connection connectionToDatabase1 = browserDriverInitialization.getConnectionToDatabase1(environment1);
        try {
            stmt = connectionToDatabase1.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query1 = "SELECT TOP 1000 [TRANSFLO].[dbo].[tf_doc_data_d4].[doc_id]" +
                "       ,[fd_agentcode]\n" +
                "       ,[fd_pro]\n" +
                "       ,[fd_pronumber]\n" +
                "       ,[fd_doctype]\n" +
                "       ,[fd_batchid]\n" +
                "       ,[fd_companycode]\n" +
                "       ,[fd_zsubject]\n" +
                "       ,[fd_zdoctype]\n" +
                "       ,[fd_emailfromaddress]\n" +
                "       ,[fd_ztoaddress]\n" +
                "       ,[fd_billto]\n" +
                "       ,[fd_capturerequired]\n" +
                "       ,[fd_portaloutput]\n" +
                "       ,[fd_moveid]\n" +
                "       ,[fd_inputsource]\n" +
                "       ,[fd_specialaccount]\n" +
                "       ,[fd_origindoctype]\n" +
                "       ,[fd_loadtype]\n" +
                "       ,[fd_scac_out]\n" +
                "       ,[fd_rpsource]\n" +
                "        FROM " + tableName1 + "  " +
                "       WHERE fd_agentcode = '" + ordLoc + "'" +
                "       and fd_pronumber = '" + ordNum + "'";

        ResultSet rs = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = rs.getMetaData();
        int count1 = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTfDocDataD4Table = new ArrayList<>();
        List<String> dbTfDocDataD4Table1 = new ArrayList<>();
        List<String> dbTfDocDataD4Table2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21));
            System.out.println();


            String a1 = rs.getString(2);
            dbTfDocDataD4Table.add(a1);

            String b1 = rs.getString(4);
            dbTfDocDataD4Table1.add(b1);

            String c1 = rs.getString(5);
            dbTfDocDataD4Table2.add(c1);

            boolean booleanValue = false;
            for (String tfDocData : dbTfDocDataD4Table) {
                if (tfDocData.contains(a1)) {
                    for (String orderDocsTable : dbOrderDocsTable) {
                        if (orderDocsTable.contains(a1)) {
                            System.out.println("Order Loc Matches: " + a1);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Order Loc Matches!!", true);
                } else {
                    fail("Order Loc doesn't Match!!");
                }
            }
            boolean booleanValue1 = false;
            for (String tfDocData1 : dbTfDocDataD4Table1) {
                if (tfDocData1.contains(b1)) {
                    for (String orderDocsTable1 : dbOrderDocsTable1) {
                        if (orderDocsTable1.contains(b1)) {
                            System.out.println("Order Number Matches: " + b1);
                            booleanValue1 = true;
                        }
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Order Number Matches !!", true);
                } else {
                    fail("Order Number doesn't Match !!");
                }
            }

            boolean booleanValue2 = false;
            for (String tfDocData2 : dbTfDocDataD4Table2) {
                if (tfDocData2.contains(c1)) {
                    for (String orderDocsTable2 : dbOrderDocsTable2) {
                        if (orderDocsTable2.contains(c1)) {
                            System.out.println("Doc Type Matches: " + c1);
                            booleanValue2 = true;
                        }
                        break;
                    }
                }
                if (booleanValue2) {
                    assertTrue("Doc Type Matches !!", true);
                } else {
                    fail("Doc Type doesn't Match!!");
                }
            }
            System.out.println("Database Closed ......");
            System.out.println("=========================================");
        }
    }






//............................................../ @databaseconnect /..........................................................//

    @Given("^Connect to databaseEBH \"([^\"]*)\" and \"([^\"]*)\"$")
    public void connect_to_databaseEBH_and(String environment, String tableName) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 [ACCT_CLASS]\n" +
                "      ,[ACCT_CLASS_DESC]\n" +
                "      ,[ACCT_CREATED_BY]\n" +
                "      ,[ACCT_CREATED_DATE]\n" +
                "      ,[ACCT_UPDATED_BY]\n" +
                "      ,[ACCT_UPDATED_DATE]\n" +
                "      ,[ACCT_CLASS_Is_Deleted]\n" +
                "  FROM [EBHLaunch].[dbo].[ACCOUNTS_CLASS_CODES]";
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            System.out.println(res.getString(1));
            System.out.println("\t" + res.getString(2));
            System.out.println("\t" + res.getString(3));
            System.out.println("\t" + res.getString(4));
        }
    }


}




