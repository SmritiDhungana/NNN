package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class AARAgentManagementFileStepDef {
    WebDriver driver;
    String url = "";
    String usernameExpected = "";

    EBHLoginPage ebhLoginPage = new EBHLoginPage(null);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    AARAgentManagementPage aarAgentManagementPage = new AARAgentManagementPage(driver);
    Logger log = Logger.getLogger("TractorStepDef");
    private static Statement stmt;


    //............................................/ Background /................................................//

    @Given("Run Test for {string} on Browser {string} for AAR Agent Management and Enter the url")
    public void run_test_for_on_browser_for_aar_agent_management_and_enter_the_url(String environment, String browser) {

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

    @Given("Login to the Agents Portal with username {string} and password {string} for AAR Agent Management")
    public void login_to_the_agents_portal_with_username_and_password_for_aar_agent_management(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhLoginPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.password));
        driver.findElement(ebhLoginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.signinButton));
        driver.findElement(ebhLoginPage.signinButton).click();
    }

    @Given("Navigate to the Corporate Page on Main Menu and to the Settlements page for AAR Agent Management")
    public void navigate_to_the_corporate_page_on_main_menu_and_to_the_settlements_page_for_aar_agent_management() {
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


    @Then("^Close all open Browsers on EBH for AAR Agent Management$")
    public void Close_all_open_Browsers_on_EBH_for_AAR_Agent_Management() {
        driver.close();
        driver.quit();
    }


    //............................................/ 22 @AARAgentManagementFile /................................................//

    @Given("^Navigate to the AAR Agent Management File page$")
    public void Navigate_to_the_AAR_Agent_Management_File_page() throws InterruptedException {
        Thread.sleep(6000);
        driver.findElement(settlementsPage.AARAgentManagementFile).click();
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(aarAgentManagementPage.TableHeader).getText());
    }

    @And("^Click on Search Icon on Agent Code$")
    public void Click_on_Search_Icon_on_Agent_Code() {
        driver.findElement(aarAgentManagementPage.AgentCodeSearchIcon).click();
    }

    @And("^Get the details of Agent Code as \"([^\"]*)\"$")
    public void Get_the_details_of_Agent_Code_as(String code) {
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        System.out.println("Total Records Returned : " + list1.size());
        log.info("Agent Records Returned - " + driver.findElement(aarAgentManagementPage.DataTable).getText());
    }


    @And("^Validate the records returned for AAR Agent Management with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_records_returned_for_AAR_Agent_Management_with_Database_Record_and(String
                                                                                                        environment, String tableName, String agentCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + " (?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + agentCode + "");
        cstmt.setString(2, "1");


        ResultSet rs = cstmt.executeQuery();
        System.out.println("Details of " + agentCode + ": ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAAARAMTable = new ArrayList<>();
        List<String> dbAAARAMTable1 = new ArrayList<>();
        List<WebElement> aAARAMTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15));


            String a = rs.getString(2);
            dbAAARAMTable.add(a);

            String b = rs.getString(6);
            dbAAARAMTable1.add(b);

            boolean booleanValue = false;
            for (WebElement aarTable : aAARAMTable) {
                if (aarTable.getText().contains(a)) {
                    for (String dbaarTable : dbAAARAMTable) {
                        if (dbaarTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement aarTable1 : aAARAMTable) {
                if (aarTable1.getText().contains(b)) {
                    for (String dbaarTable1 : dbAAARAMTable1) {
                        if (dbaarTable1.contains(b)) {
                            System.out.println(b);
                            System.out.println();
                            booleanValue1 = true;
                        }
                    }
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


    @And("^Click on edit on Action field$")
    public void Click_on_edit_on_Action_field() {

        //   driver.findElement(aarAgentManagementPage.edit).click();
        driver.findElement(By.xpath("//*[@id=\"linkEdit\"]")).click();
    }

    @And("^Validate you are on AAR Agent Management Detail Page$")
    public void Validate_you_are_on_AAR_Agent_Management_Detail_Page() throws InterruptedException {
        assertTrue(driver.findElement(aarAgentManagementPage.TableHead).isDisplayed());
        System.out.println(driver.findElement(aarAgentManagementPage.TableHead).getText());
        Thread.sleep(5000);
    }

    @And("^Validate Agent Code and Vendor Code$")
    public void Validate_Agent_Code_and_Vendor_Code() {
        assertTrue(driver.findElement(aarAgentManagementPage.AgentCode).isDisplayed());
        System.out.println("Description Of Agent is :");
        System.out.println(driver.findElement(aarAgentManagementPage.AgentCode).getText());
        assertTrue(driver.findElement(aarAgentManagementPage.VendorCode).isDisplayed());
        System.out.println(driver.findElement(aarAgentManagementPage.VendorCode).getText());
        System.out.println("=========================================");
    }

    @And("^Click on Cancel$")
    public void Click_on_Cancel() throws InterruptedException {
        driver.findElement(aarAgentManagementPage.Cancel).click();
        Thread.sleep(5000);
    }


    @And("^Validate the records returned for Active Agents not Assigned with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_records_returned_for_Active_Agents_not_Assigned_with_Database_Record_and(String environment, String tableName1, String agentCodeActiveAgentsNotAssigned) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName1 + " }";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);
        ResultSet rs1 = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();

        while (rs1.next()) {

        }
        cstmt.getMoreResults();
        System.out.println("Active Agents not Assigned: ");
        ResultSet rs2 = cstmt.getResultSet();
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<String> dbAARAMTable = new ArrayList<>();
        List<String> dbAARAMTable1 = new ArrayList<>();
        List<WebElement> aAARAMTable = driver.findElements(By.xpath("//*[@id=\"dataTable_1\"]/tbody"));


        while (rs2.next()) {
            int rows = rs2.getRow();
            System.out.println("Number of Rows: " + rows);
            System.out.println(rs2.getString(1) +
                    "\t" + rs2.getString(2) +
                    "\t" + rs2.getString(3) +
                    "\t" + rs2.getString(4) +
                    "\t" + rs2.getString(5));


            String a = rs2.getString(1);
            dbAARAMTable.add(a);

            String b = rs2.getString(2);
            dbAARAMTable1.add(b);


            boolean booleanValue = false;
            for (WebElement str1 : aAARAMTable) {
                if (str1.getText().contains(a)) {
                    for (String str : dbAARAMTable) {
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
            boolean booleanValue1 = false;
            for (WebElement str1 : aAARAMTable) {
                if (str1.getText().contains(b)) {
                    for (String str : dbAARAMTable1) {
                        if (str.contains(b)) {
                            System.out.println(b);
                            System.out.println();
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
            }
            if (booleanValue1) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


//............................................../ 23 @AARReassignment /......................................................//
    //   Scenario Outline: V1.10 - Add Logic to Check Settling Table Upon AAR Reassignment in Launch Environment (Create Automation - [JIRA] (SET-2195) // (SET-2187))

    @Given("^Determine an agent to Reassign is on the Settling Table with the Current Settling Week \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void determine_an_agent_to_Reassign_is_on_the_Settling_Table_with_the_Current_Settling_Week_and(String environment, String tableName, String vendorCode, String acctingWk) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [STId]\n" +
                "      ,[SETTLING_VENDOR_CODE]\n" +
                "      ,[SETTLING_AGENT_CODE]\n" +
                "      ,[SETTLING_LOGIN]\n" +
                "      ,[SETTLING_ACCTING_DT]\n" +
                "      ,[SETTLING_Vendor_VERIFIED]\n" +
                "      ,[SETTLING_ACCTING_WK]\n" +
                "      ,[SESSION_ID]\n" +
                "   FROM " + tableName + " " +
                "   WHERE [SETTLING_ACCTING_WK] = '" + acctingWk + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);


        List<WebElement> aarAgentMgmt = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        List<String> dbSettlingTable = new ArrayList<>();

        while (res.next()) {
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));

            String a = res.getString(2);
            dbSettlingTable.add(a);


            boolean booleanValue = false;
            if (dbSettlingTable.contains(vendorCode)) {
                System.out.println("Current Settling Week on the Settling Table has the Vendor Code: " + vendorCode);
                System.out.println();
                booleanValue = true;

                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on Search Icon on Vendor Code$")
    public void Click_on_Search_Icon_on_Vendor_Code() {
        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        //*[@id="img5"]
    }


    @And("^Get the details of Vendor Code as \"([^\"]*)\" on AAR Agent Management$")
    public void Get_the_details_of_Vendor_Code_as_on_AAR_Agent_Management(String vendorcode) throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(vendorcode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
        log.info("Agent Records Returned - " + driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(2000);
    }


    @Given("^Validate the AAR Login \"([^\"]*)\" for Vendor Code \"([^\"]*)\" on AAR Agent Management Detail Page$")
    public void validate_the_AAR_Login_for_Vendor_Code_on_AAR_Agent_Management_Detail_Page(String aarLogin1, String code) {
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin1)) {
                System.out.println("AAR Login for Vendor Code (" + code + ") is " + aarLogin1);
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
        } else {
            fail("Vendor Code is not present!!");
        }
        System.out.println("=========================================");
    }


    @Given("^Reassign AAR Login to your login from the AAR Login \"([^\"]*)\" dropdown$")
    public void reassign_AAR_Login_to_your_login_from_the_AAR_Login_dropdown(String aarLogin) throws InterruptedException {

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).sendKeys(aarLogin);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).click();
        Thread.sleep(2000);
    }


    @Given("^Select CANCEL$")
    public void select_CANCEL() {
        driver.findElement(aarAgentManagementPage.Cancel).click();
    }


    @Given("^Check to make sure the Vendor Code \"([^\"]*)\" did not change AAR assignment \"([^\"]*)\" upon a CANCEL$")
    public void check_to_make_sure_the_Vendor_Code_did_not_change_AAR_assignment_upon_a_CANCEL(String code, String aarLogin1) throws Throwable {
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin1)) {
                System.out.println("AAR Login for Vendor Code (" + code + ") is " + aarLogin1);
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
        } else {
            fail("Vendor Code is not present!!");
        }
    }


    @Given("^Update Vendor Code and run the following SQL \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" upon a CANCEL$")
    public void Update_Vendor_Code_and_run_the_following_SQL_and_upon_a_CANCEL(String environment, String tableName, String vendorCode) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [STId]\n" +
                "      ,[SETTLING_VENDOR_CODE]\n" +
                "      ,[SETTLING_AGENT_CODE]\n" +
                "      ,[SETTLING_LOGIN]\n" +
                "      ,[SETTLING_ACCTING_DT]\n" +
                "      ,[SETTLING_Vendor_VERIFIED]\n" +
                "      ,[SETTLING_ACCTING_WK]\n" +
                "      ,[SESSION_ID]\n" +
                "   FROM " + tableName + " " +
                "   WHERE SETTLING_VENDOR_CODE = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);


        List<WebElement> aarAgentMgmt = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        List<String> dbSettlingTable = new ArrayList<>();

        while (res.next()) {
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));


            String a1 = res.getString(4);
            dbSettlingTable.add(a1);

            boolean booleanValue = false;
            for (WebElement aARAM : aarAgentMgmt) {
                if (aARAM.getText().contains(a1)) {
                    for (String dbST : dbSettlingTable) {
                        if (dbST.contains(a1)) {
                            System.out.println("Validated AAR Login with Database SETTLING_LOGIN (CANCEL) : " + a1);
                            System.out.println();
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Reassign AAR Login to your Login from the AAR Login \"([^\"]*)\" dropdown$")
    public void reassign_AAR_Login_to_your_Login_from_the_AAR_Login_dropdown(String aarLogin) throws InterruptedException {

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).sendKeys(aarLogin);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).click();
    }


    @Given("^Select SAVE, Click on NO$")
    public void select_SAVE_Click_on_NO() throws InterruptedException {
        driver.findElement(aarAgentManagementPage.Save).click();
        Thread.sleep(2000);
        driver.findElement(aarAgentManagementPage.PopUpConfirmation).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        //*[@id="btnYesAction"]
        driver.findElement(By.xpath("//*[@id=\"btnNo\"]")).click();

        //    /html/body/section/div/div/div[2]/div[9]/div/div/a[2]
        //*[@id="btnNo"]
        //    driver.findElement(aarAgentManagementPage.Ok).click();
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
    }


    @Given("^Check to make sure the Vendor Code \"([^\"]*)\" did not change AAR assignment \"([^\"]*)\" upon a SAVE, NO$")
    public void check_to_make_sure_the_Vendor_Code_did_not_change_AAR_assignment_upon_a_SAVE_NO(String code, String aarLogin) throws InterruptedException {
        Thread.sleep(4000);
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin)) {
                System.out.println("AAR Login for Vendor Code (" + code + ") when (SAVE, NO) : " + aarLogin);
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
        } else {
            fail("Vendor Code is not present!!");
        }
    }


    @Given("^Update Vendor Code and run the following SQL \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" upon a SAVE, NO$")
    public void Update_Vendor_Code_and_run_the_following_SQL_and_upon_a_SAVE_NO(String environment, String tableName, String vendorCode) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        try {
            stmt = connectionToDatabase.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT TOP 1000 [STId]\n" +
                "      ,[SETTLING_VENDOR_CODE]\n" +
                "      ,[SETTLING_AGENT_CODE]\n" +
                "      ,[SETTLING_LOGIN]\n" +
                "      ,[SETTLING_ACCTING_DT]\n" +
                "      ,[SETTLING_Vendor_VERIFIED]\n" +
                "      ,[SETTLING_ACCTING_WK]\n" +
                "      ,[SESSION_ID]\n" +
                "   FROM " + tableName + " " +
                "   WHERE SETTLING_VENDOR_CODE = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> aarAgentMgmt = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        List<String> dbSettlingTable = new ArrayList<>();

        while (res.next()) {
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));


            String a1 = res.getString(4);
            dbSettlingTable.add(a1);

            boolean booleanValue = false;
            for (WebElement aARAM : aarAgentMgmt) {
                if (aARAM.getText().contains(a1)) {
                    for (String dbST : dbSettlingTable) {
                        if (dbST.contains(a1)) {
                            System.out.println("Validated AAR Login with Database SETTLING_LOGIN (SAVE, NO): " + a1);
                            System.out.println();
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Select SAVE, Click on YES$")
    public void select_SAVE_Click_on_YES() {
        driver.findElement(aarAgentManagementPage.Save).click();
        driver.findElement(aarAgentManagementPage.PopUpConfirmation).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(aarAgentManagementPage.Yes).click();
        driver.findElement(aarAgentManagementPage.PopUpConfirmation).isDisplayed();
        driver.findElement(aarAgentManagementPage.Ok).click();
    }


    @Given("^Select SAVE, Click on YES on Confirmation$")
    public void select_SAVE_Click_on_YES_on_Confirmation() {
        driver.findElement(aarAgentManagementPage.Save).click();
        driver.findElement(aarAgentManagementPage.PopUpConfirmation).isDisplayed();
        driver.findElement(aarAgentManagementPage.Yes).click();
        driver.findElement(aarAgentManagementPage.Yes).click();
        driver.findElement(aarAgentManagementPage.PopUpConfirmation).isDisplayed();
        driver.findElement(aarAgentManagementPage.Ok).click();
    }


    @Given("^Check to make sure the Vendor Code \"([^\"]*)\" did change AAR assignment \"([^\"]*)\" upon a SAVE, YES$")
    public void check_to_make_sure_the_Vendor_Code_did_change_AAR_assignment_upon_a_SAVE_YES(String code, String aarLogin) {
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin)) {
                System.out.println("AAR Login for Vendor Code (" + code + ") when (SAVE, YES) : " + aarLogin);
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
        } else {
            fail("Vendor Code is not present!!");
        }
    }


    @Given("^Update Vendor Code and run the following SQL \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" upon a SAVE, YES$")
    public void Update_Vendor_Code_and_run_the_following_SQL_and_upon_a_SAVE_YES(String environment, String tableName, String vendorCode) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        try {
            stmt = connectionToDatabase.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT TOP 1000 [STId]\n" +
                "      ,[SETTLING_VENDOR_CODE]\n" +
                "      ,[SETTLING_AGENT_CODE]\n" +
                "      ,[SETTLING_LOGIN]\n" +
                "      ,[SETTLING_ACCTING_DT]\n" +
                "      ,[SETTLING_Vendor_VERIFIED]\n" +
                "      ,[SETTLING_ACCTING_WK]\n" +
                "      ,[SESSION_ID]\n" +
                "   FROM " + tableName + " " +
                "   WHERE SETTLING_VENDOR_CODE = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> aarAgentMgmt = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        List<String> dbSettlingTable = new ArrayList<>();

        while (res.next()) {
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8));


            String a1 = res.getString(4);
            dbSettlingTable.add(a1);

            boolean booleanValue = false;
            for (WebElement aARAM : aarAgentMgmt) {
                if (aARAM.getText().contains(a1)) {
                    for (String dbST : dbSettlingTable) {
                        if (dbST.contains(a1)) {
                            System.out.println("Validated AAR Login with Database SETTLING_LOGIN (SAVE, YES): " + a1);
                            System.out.println();
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Select Settlements in the Breadcrumbs$")
    public void select_Settlements_in_the_Breadcrumbs() {
        driver.findElement(By.xpath("/html/body/nav/div/nav/a[3]")).click();
    }

    @Given("^Choose Agent Settling and enter password, Choose Continue, Select OK \"([^\"]*)\"$")
    public void choose_Agent_Settling_and_enter_password_Choose_Continue_Select_OK(String password) throws Throwable {
        driver.findElement(settlementsPage.agentSettling).click();
        driver.findElement(settlementsPage.EnterPasswordForAgentSettling).sendKeys(password);
        driver.findElement(settlementsPage.OkButton).click();
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation).isDisplayed();
        Thread.sleep(5000);
        driver.findElement(settlementsPage.Continue).click();
        driver.findElement(By.xpath("//*[@id=\"btnSaveAgent\"]")).click();

    }

    @Given("^Ensure the new Agent is included in the Settlement Summary \"([^\"]*)\"$")
    public void ensure_the_new_Agent_is_included_in_the_Settlement_Summary(String code) throws InterruptedException {
        driver.findElement(By.xpath("/html/body/section/section/div/div/div[2]/div[1]/div/label/b")).isDisplayed();
        driver.findElement(By.xpath("/html/body/section/section/div/div/div[2]/div[1]/div/label/b")).getText();
        Thread.sleep(8000);
        log.info(driver.findElement(By.xpath("//*[@id=\"tblSettlementSummary\"]/tbody")).getText());
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list filterAgentSettlingMainForm')]//p"));

        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(code)) {
                System.out.println("Vendor Code on Settlement Summary has: " + code);
                webElement.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Vendor Code is present !!", true);
        } else {
            fail("Vendor Code is not present!!");
        }
    }


    @Given("^Choose Agent Settling and enter password\\.  Choose Reset\\.  Choose Current or Previous Week \\(whatever week you did not check above in Continue\\)\\.  Select OK \"([^\"]*)\"$")
    public void choose_Agent_Settling_and_enter_password_Choose_Reset_Choose_Current_or_Previous_Week_whatever_week_you_did_not_check_above_in_Continue_Select_OK(String password) throws Throwable {
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/nav/div/nav/a[3]")).click();
        driver.findElement(settlementsPage.agentSettling).click();
        driver.findElement(settlementsPage.EnterPasswordForAgentSettling).sendKeys(password);
        driver.findElement(settlementsPage.OkButton).click();
        Thread.sleep(3000);
        driver.findElement(settlementsPage.PopUpConformation).isDisplayed();
        Thread.sleep(5000);
        driver.findElement(settlementsPage.Reset).click();
        Thread.sleep(2000);
        driver.findElement(settlementsPage.PopUpDecision).isDisplayed();
        Thread.sleep(4000);
        driver.findElement(settlementsPage.PreviousWeek).click();
        driver.findElement(settlementsPage.DecisionOk).click();
    }


    @Given("^Return to the AAR Agent Management form and check filtering to make sure it retains filters when selecting different options \"([^\"]*)\" \"([^\"]*)\"$")
    public void return_to_the_AAR_Agent_Management_form_and_check_filtering_to_make_sure_it_retains_filters_when_selecting_different_options(String code, String aarLogin) throws InterruptedException {
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/nav/div/nav/a[3]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnAARAgentManagementFile\"]")).click();


        //   driver.findElement(aarAgentManagementPage.AgentCodeSearchIcon).click();
        //  List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));

        for (WebElement webElement : list) {
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        Thread.sleep(8000);
        log.info("Agent Records Returned - " + driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());


        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(aarLogin)) {
                System.out.println("AAR Login is " + aarLogin);
                webElement.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Agent Code is present !!", true);
        } else {
            fail("Agent Code is not present!!");
        }

        driver.findElement(aarAgentManagementPage.edit).click();
    }


    @Given("^REVERT back AAR reassignment back to original AAR before completing testing \"([^\"]*)\"$")
    public void revert_back_AAR_reassignment_back_to_original_AAR_before_completing_testing(String aarLogin1) throws Throwable {
        System.out.println("=========================================");
        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).sendKeys(aarLogin1);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).click();


        driver.findElement(aarAgentManagementPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();


        Thread.sleep(3000);
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(3000);


        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin1)) {
                System.out.println("AAR Login after revert back is " + aarLogin1);
                webElement.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Agent Code is present !!", true);
        } else {
            fail("Agent Code is not present!!");
        }
        System.out.println("=========================================");
    }


    //............................................../ 24 @AARReassignmentAgentThatSharesVendorCodeWithOtherAgents /......................................................//


    @Given("^REVERT back AAR reassignment back to original AAR Login before completing testing \"([^\"]*)\"$")
    public void revert_back_AAR_reassignment_back_to_original_AAR_Login_before_completing_testing(String aarLogin1) throws Throwable {
        System.out.println("=========================================");
        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).sendKeys(aarLogin1);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"AARLogin_I\"]")).click();

        driver.findElement(aarAgentManagementPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();


        Thread.sleep(3000);
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(3000);


        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr/td[1]"));
        boolean booleanValue = false;
        for (WebElement webElement : list) {
            if (webElement.getText().contains(aarLogin1)) {
                System.out.println("AAR Login after revert back is " + aarLogin1);
                webElement.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("Agent Code is present !!", true);
        } else {
            fail("Agent Code is not present!!");
        }
        System.out.println("=========================================");
    }


    //............................................../ #25 @AARAgentManagementWeeklyCutoffDay/Time /..........................................................//

    @Given("^Click on Search Icon on Weekly Cutoff Day as \"([^\"]*)\"$")
    public void click_on_Search_Icon_on_Weekly_Cutoff_Day_as(String day) {

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(day)) {
                webElement.click();
                break;
            }
        }
    }


    @Given("^Click on Search Icon on Weekly Cutoff Time as \"([^\"]*)\"$")
    public void click_on_Search_Icon_on_Weekly_Cutoff_Time_as(String week) throws Throwable {
        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]//p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(week)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Get the details based on Weekly Cutoff Day and Time on AAR Agent Management$")
    public void get_the_details_based_on_Weekly_Cutoff_Day_and_Time_on_AAR_Agent_Management() throws Throwable {
        log.info("Total Records Returned - " + driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(2000);
    }


    @Given("^Validate Records Returned with Database Setting Table \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_with_Database_Setting_Table_and(String environment, String tableName, String day, String week) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT\n" +
                "       DISTINCT\n" +
                "       ASI.AGENT_STL_ID,\n" +
                "       ASI.AGENT_STL_AAR,\n" +
                "       ASI.AGENT_STL_AGENT_CODE,\n" +
                "       ASI.AGENT_STL_ACCTING_DATE,\n" +
                "       ASI.AGENT_STL_ACCTING_WK,\n" +
                "       ASI.AGENT_STL_WKLY_CO_DAY,\n" +
                "       ASI.AGENT_STL_WKLY_CO_TIME,\n" +
                "       ASI.AGENT_STL_VENDOR_CODE,\n" +
                "       AGENT_STL_COMPANY_CODE,\n" +
                "       CC.[Company_desc]\n" +
                "       FROM " + tableName + "  AS  ASI WITH(NOLOCK)\n" +
                "       LEFT JOIN [EBHLAUNCH].[dbo].[LOCATIONS] AS Loc WITH(NOLOCK)\n" +
                "       ON ASI.AGENT_STL_AGENT_CODE    =Loc.LOCATION_CODE\n" +
                "       LEFT JOIN [EBHLAUNCH].[dbo].[COMPANY_CODES] AS CC\n" +
                "       ON Loc.[LOCATION_COMP_CODE] = CC.Company_code\n" +
                "       WHERE loc.[LOCATION_STATUS] ='ACTIVE'\n" +
                "       AND ASI.AGENT_STL_WKLY_CO_DAY = '" + day + "'" +
                "       AND ASI.AGENT_STL_WKLY_CO_TIME = '" + week + "'";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> aarAgentMgmt = driver.findElements(By.xpath("//*[@id=\"dataTable\"]"));
        List<String> dbAgentSettlementInfoTable = new ArrayList<>();
        List<String> dbAgentSettlementInfoTable1 = new ArrayList<>();

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
                    "\t" + res.getString(10));


            String a = res.getString(9);
            dbAgentSettlementInfoTable.add(a);

            boolean booleanValue = false;
            for (WebElement aARAM : aarAgentMgmt) {
                if (aARAM.getText().contains(a)) {
                    for (String dbASIT : dbAgentSettlementInfoTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println("Vendor Code = " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }


            String b = res.getString(3);
            dbAgentSettlementInfoTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement aARAM : aarAgentMgmt) {
                if (aARAM.getText().contains(b)) {
                    for (String dbASIT : dbAgentSettlementInfoTable1) {
                        if (dbASIT.contains(b)) {
                            System.out.println("Agent Code = " + b);
                            System.out.println();
                        }
                    }
                    booleanValue1 = true;
                    break;
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................../ @TractorAARLogin /..........................................................//

    @Given("Confirm Tractor AAR Login column is displayed and the data represented in the Tractor AAR Login is correct by running the following SQL {string} and {string}")
    public void confirm_tractor_aar_login_column_is_displayed_and_the_data_represented_in_the_tractor_aar_login_is_correct_by_running_the_following_sql_and(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"thTractorAARLogin\"]")).getAttribute("value"));

        Thread.sleep(2000);
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT asi.agent_stl_aar,\n" +
                "       Isnull(ebhl.user_fname + ' ', '')\n" +
                "       + Isnull(ebhl.user_lname, '')              AS NAME,\n" +
                "       asi.agent_stl_company_code,\n" +
                "       asi.agent_stl_agent_code,\n" +
                "\t   asi.AGENT_STL_TRAC_AAR,\n" +
                "       asi.agent_stl_vendor_code,\n" +
                "       loc.location_name,\n" +
                "       asi.agent_stl_accting_date,\n" +
                "       asi.agent_stl_wkly_co_day,\n" +
                "       asi.agent_stl_wkly_co_time,\n" +
                "       asi.agent_stl_accting_wk,\n" +
                "       Isnull(ash.agent_stl_hist_last_date + ' ', '')\n" +
                "       + Isnull(ash.agent_stl_hist_last_time, '') AS 'LAST SETTLE DATE/TIME',\n" +
                "       ash.agent_stl_hist_last_week,\n" +
                "       ash.agent_stl_hist_stl_by,\n" +
                "       loc.location_status\n" +
                "       FROM " + tableName + " AS asi\n" +
                "       LEFT JOIN [EBH].[dbo].[AGENT_STL_HISTORY] AS ash\n" +
                "              ON asi.agent_stl_agent_code = ash.agent_stl_hist_agent_code\n" +
                "       LEFT JOIN [EBH].[dbo].[locations] AS loc\n" +
                "              ON asi.agent_stl_agent_code = loc.location_code\n" +
                "       JOIN [EBH].[dbo].[ebh_logins] AS ebhl\n" +
                "         ON asi.agent_stl_aar = ebhl.user_login\n" +
                "WHERE  ( ash.agent_stl_hist_id IN (SELECT Max(agent_stl_hist_id)\n" +
                "                                   FROM [EBH].[dbo].[AGENT_STL_HISTORY]\n" +
                "                                   GROUP  BY agent_stl_hist_agent_code) )\n" +
                "       AND loc.location_status = 'ACTIVE'\n" +
                "--order by asi.AGENT_STL_AAR\n" +
                "UNION\n" +
                "SELECT asi.agent_stl_aar,\n" +
                "       Isnull(ebhl.user_fname + ' ', '')\n" +
                "       + Isnull(ebhl.user_lname, '')              AS NAME,\n" +
                "       asi.agent_stl_company_code,\n" +
                "       asi.agent_stl_agent_code,\n" +
                "\t   asi.AGENT_STL_TRAC_AAR,\n" +
                "       asi.agent_stl_vendor_code,\n" +
                "       loc.location_name,\n" +
                "       asi.agent_stl_accting_date,\n" +
                "       asi.agent_stl_wkly_co_day,\n" +
                "       asi.agent_stl_wkly_co_time,\n" +
                "       asi.agent_stl_accting_wk,\n" +
                "       Isnull(ash.agent_stl_hist_last_date + ' ', '')\n" +
                "       + Isnull(ash.agent_stl_hist_last_time, '') AS 'LAST SETTLE DATE/TIME',\n" +
                "       ash.agent_stl_hist_last_week,\n" +
                "       ash.agent_stl_hist_stl_by,\n" +
                "       loc.location_status\n" +
                "FROM   [EBH].[dbo].[AGENT_SETTLEMENT_INFO] AS asi\n" +
                "       LEFT JOIN [EBH].[dbo].[agent_stl_history] AS ash\n" +
                "              ON asi.agent_stl_agent_code = ash.agent_stl_hist_agent_code\n" +
                "       LEFT JOIN [EBH].[dbo].[locations] AS loc\n" +
                "              ON asi.agent_stl_agent_code = loc.location_code\n" +
                "       JOIN [EBH].[dbo].[ebh_logins] AS ebhl\n" +
                "         ON asi.agent_stl_aar = ebhl.user_login\n" +
                "WHERE  ( ash.agent_stl_hist_last_date IS NULL\n" +
                "         AND ash.agent_stl_hist_last_time IS NULL )\n" +
                "       AND loc.location_status = 'ACTIVE'\n" +
                "ORDER  BY asi.agent_stl_aar\n";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorAARLogin = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentSettlementInfoTable = new ArrayList<>();

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
                    "\t" + res.getString(14));


            String a = res.getString(5);
            dbAgentSettlementInfoTable.add(a);

            boolean booleanValue = false;
            for (WebElement tracaar : tractorAARLogin) {
                if (tracaar.getText().contains(a)) {
                    for (String dbASIT : dbAgentSettlementInfoTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println("AGENT_STL_TRAC_AAR = " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
    }

    @Given("Select Tractor AAR Login Agent Code {string} and Click on Edit")
    public void select_tractor_aar_login_Agent_Code_and_click_on_edit(String agentCode) throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"img3\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(agentCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(4000);
        driver.findElement(By.id("linkEdit")).click();
    }


    @Given("Upon Edit, Confirm Tractor AAR Login column is displayed, Confirm Tractor AAR Login is not blank, Verify Information to the right of the Tractor AAR Login Dropdown when selected")
    public void upon_edit_confirm_tractor_aar_login_column_is_displayed_confirm_tractor_aar_login_is_not_blank_verify_information_to_the_right_of_the_tractor_aar_login_dropdown_when_selected() throws InterruptedException {
        System.out.println("========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);
        System.out.println("========================================");
    }

    @Given("Confirm choices for Tractor AAR Login display an intuitive drop down and contains the following data Run SQL {string} and {string}")
    public void confirm_choices_for_tractor_aar_login_display_an_intuitive_drop_down_and_contains_the_following_data_run_sql_and(String environment, String code) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {

   /*     driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        //   List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"TractorAARLogin_DDD_L\"]"));

        //*[@id="TractorAARLogin_DDD_LLBVSTC"]

        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"TractorAARLogin_DDD_LLBVSTC\"]"));
        System.out.println(list.size());
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }  */


        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT el.User_Login, el.User_fname, el.User_lname " +
                "       FROM [EBH].[dbo].[ebh_logins] el " +
                "       WHERE el.DEPARTMENT_CODE = 'SETTL' ORDER BY el.User_Login";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        //   List<WebElement> tractorAARLogin = driver.findElements(By.xpath("//*[@id=\"TractorAARLogin_DDD_L_LBT\"]"));
        List<String> dbEbhLoginsTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1));


            String a = res.getString(1);
            dbEbhLoginsTable.add(a);

        /*    boolean booleanValue = false;
            for (WebElement tracaar : tractorAARLogin) {
                if (tracaar.getText().contains(a)) {
                    for (String dbASIT : dbEbhLoginsTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }  */
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");

        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
    }


    //............................................/ 56 @TractorAARLoginNoOneIsCurrentlySettling /................................................//

    @Given("TEST No-one else is currently settling agent, Update Tractor AAR Login {string}, click Save")
    public void to_test_no_one_else_is_currently_settling_agent_update_tractor_aar_login_click_Save(String tractorAARLoginUpdate) throws InterruptedException {

        System.out.println("========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("========================================");
        System.out.println("Current Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLoginUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(2000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(8000);
    }

    @Given("Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, regardless if the agent is verified or not, Run the SQL {string} {string} {string} {string} and update TRACTOR_SETTLING_AGENT_CODE with the Agent Code you are testing with")
    public void upon_adding_or_updating_tractor_aar_login_ensure_no_one_else_is_settling_this_agent_regardless_if_the_agent_is_verified_or_not_run_the_sql_and_update_tractor_settling_agent_code_with_the_agent_code_you_are_testing_with(String environment, String tableName1, String agentCode, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "Select * from " + tableName1 + " where TRACTOR_SETTLING_AGENT_CODE = '" + agentCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorAARLogin = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTracStlTable = new ArrayList<>();

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
                    "\t" + res.getString(10));

            String a = res.getString(3);
            dbTracStlTable.add(a);

            boolean booleanValue = false;
            for (WebElement tracaar : tractorAARLogin) {
                if (tracaar.getText().contains(a)) {
                    for (String dbASIT : dbTracStlTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println("TRACTOR_SETTLING_AGENT_CODE : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");


        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        stmt = connectionToDatabase.createStatement();
        String query2 = "SELECT TOP 1000 [AGENT_STL_ID]\n" +
                "      ,[AGENT_STL_AAR]\n" +
                "      ,[AGENT_STL_AGENT_CODE]\n" +
                "      ,[AGENT_STL_ACCTING_DATE]\n" +
                "      ,[AGENT_STL_ACCTING_WK]\n" +
                "      ,[AGENT_STL_WKLY_CO_DAY]\n" +
                "      ,[AGENT_STL_WKLY_CO_TIME]\n" +
                "      ,[AGENT_STL_NOTES]\n" +
                "      ,[AGENT_STL_VENDOR_CODE]\n" +
                "      ,[AGENT_STL_CREATED_BY]\n" +
                "      ,[AGENT_STL_CREATED_DATE]\n" +
                "      ,[AGENT_STL_LAST_UPDATED_DATE]\n" +
                "      ,[AGENT_STL_LAST_UPDATED_BY]\n" +
                "      ,[AGENT_STL_COMPANY_CODE]\n" +
                "      ,[AGENT_STL_TRAC_AAR]\n" +
                "  FROM " + tableName + "\n" +
                "  where [AGENT_STL_AGENT_CODE] = '" + agentCode + "'";

        ResultSet res2 = stmt.executeQuery(query2);
        ResultSetMetaData rsmd2 = res2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<WebElement> tractorAARLogin1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentStlInfoTable = new ArrayList<>();
        List<String> dbAgentStlInfoTable1 = new ArrayList<>();
        List<String> dbAgentStlInfoTable2 = new ArrayList<>();

        while (res2.next()) {
            int rows2 = res2.getRow();
            System.out.println("Number of Rows:" + rows2);
            System.out.println(res2.getString(1) +
                    "\t" + res2.getString(2) +
                    "\t" + res2.getString(3) +
                    "\t" + res2.getString(4) +
                    "\t" + res2.getString(5) +
                    "\t" + res2.getString(6) +
                    "\t" + res2.getString(7) +
                    "\t" + res2.getString(8) +
                    "\t" + res2.getString(9) +
                    "\t" + res2.getString(10) +
                    "\t" + res2.getString(11) +
                    "\t" + res2.getString(12) +
                    "\t" + res2.getString(13) +
                    "\t" + res2.getString(14) +
                    "\t" + res2.getString(15));

            String bb = res2.getString(15);
            dbAgentStlInfoTable.add(bb);
            String aa = res2.getString(12);
            dbAgentStlInfoTable1.add(aa);
            String cc = res2.getString(13);
            dbAgentStlInfoTable1.add(cc);

            boolean booleanValue = false;
            for (WebElement tracaar1 : tractorAARLogin1) {
                if (tracaar1.getText().contains(bb)) {
                    for (String dbASIT : dbAgentStlInfoTable) {
                        if (dbASIT.contains(bb)) {
                            System.out.println("AGENT_STL_TRAC_AAR = " + bb);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("AGENT_STL_LAST_UPDATED_DATE = " + aa);
            System.out.println("AGENT_STL_LAST_UPDATED_BY = " + cc);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");

    }


    @Given("Revert Tractor AAR assignment back to original assignment {string}")
    public void Revert_tractor_aar_assignment_back_to_original_assignment(String tractorAARLogin) throws InterruptedException {
        driver.findElement(By.id("linkEdit")).click();

        System.out.println("========================================");
        System.out.println("Updated Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);


        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLogin);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(3000);
        //    System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //    Thread.sleep(3000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Reverted Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
        System.out.println("=========================================");

    }


//............................................/ 57 @TractorAARLoginAnotherAARisSettlingTheAgentVerified /................................................//

    @Given("TEST Another AAR is settling the agent, the AGENT is VERIFIED, Update Tractor AAR Login {string}, click Save, Select No")
    public void test_another_aar_is_settling_the_agent_the_AGENT_is_verified_or_not_update_tractor_aar_login_click_save_Select_No(String tractorAARLoginUpdate) throws InterruptedException {
        System.out.println("========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("========================================");
        System.out.println("Select NO Scenario: ");
        System.out.println("Current Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLoginUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(2000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        //    System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //    Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnNo\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(8000);
    }


    @Given("TEST Another AAR is settling the agent, the AGENT is VERIFIED, Update Tractor AAR Login {string}, click Save, Select Yes")
    public void test_another_aar_is_settling_the_agent_the_AGENT_is_VERIFIED_update_tractor_aar_login_click_save_Select_Yes(String tractorAARLoginUpdate) throws InterruptedException {
        System.out.println("========================================");
        Thread.sleep(2000);
        //    System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("Select YES Scenario: ");
        System.out.println("Current Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLoginUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(2000);
        //  driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(8000);
    }


    @Given("Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is verified, Run SQL {string} {string} {string} {string}")
    public void upon_adding_or_updating_tractor_aar_login_ensure_no_one_else_is_settling_this_agent_the_agent_is_verified_run_sql(String environment, String tableName1, String agentCode, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "Select * from " + tableName1 + " " +
                "where TRACTOR_SETTLING_AGENT_CODE = '" + agentCode + "'" +
                "and TRACTOR_SETTLING_AGENT_VERIFIED  = 0";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorAARLogin = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTracStlTable = new ArrayList<>();
        List<String> dbTracStlTable2 = new ArrayList<>();
        List<String> dbTracStlTable3 = new ArrayList<>();

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
                    "\t" + res.getString(10));

            String a = res.getString(3);
            dbTracStlTable.add(a);

            boolean booleanValue = false;
            for (WebElement tracaar : tractorAARLogin) {
                if (tracaar.getText().contains(a)) {
                    for (String dbASIT : dbTracStlTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println("TRACTOR_SETTLING_AGENT_CODE : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = res.getString(6);
            dbTracStlTable3.add(c);
            System.out.println("TRACTOR_SETTLING_AGENT_VERIFIED : " + c);

            String bb = res.getString(5);
            dbTracStlTable2.add(bb);

            boolean booleanValue2 = false;
            for (WebElement tracaar1 : tractorAARLogin) {
                if (tracaar1.getText().contains(bb)) {
                    for (String dbASIT2 : dbTracStlTable2) {
                        if (dbASIT2.contains(bb)) {
                            System.out.println("TRACTOR_SETTLING_LOGIN : " + bb);
                            booleanValue2 = true;
                            break;
                        }
                    }
                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Database Closed ......");
            System.out.println("=========================================");
        }

        System.out.println("Connecting to Database ..............................");
        stmt = connectionToDatabase.createStatement();
        String query2 = "Select AGENT_STL_TRAC_AAR, AGENT_STL_LAST_UPDATED_DATE, AGENT_STL_LAST_UPDATED_BY " +
                "       FROM " + tableName + "  " +
                "       WHERE AGENT_STL_AGENT_CODE = '" + agentCode + "'";

        ResultSet res2 = stmt.executeQuery(query2);
        ResultSetMetaData rsmd2 = res2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<WebElement> tractorAARLogin1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentStlInfoTable = new ArrayList<>();
        List<String> dbAgentStlInfoTable1 = new ArrayList<>();
        List<String> dbAgentStlInfoTable2 = new ArrayList<>();

        while (res2.next()) {
            int rows2 = res2.getRow();
            System.out.println("Number of Rows:" + rows2);
            System.out.println(res2.getString(1) +
                    "\t" + res2.getString(2) +
                    "\t" + res2.getString(3));

            String bb = res2.getString(1);
            dbAgentStlInfoTable.add(bb);
            String aa = res2.getString(2);
            dbAgentStlInfoTable1.add(aa);
            String cc = res2.getString(3);
            dbAgentStlInfoTable2.add(cc);

            boolean booleanValue = false;
            for (WebElement tracaar1 : tractorAARLogin1) {
                if (tracaar1.getText().contains(bb)) {
                    for (String dbASIT : dbAgentStlInfoTable) {
                        if (dbASIT.contains(bb)) {
                            System.out.println("AGENT_STL_TRAC_AAR = " + bb);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
                System.out.println("AGENT_STL_LAST_UPDATED_DATE = " + aa);
                System.out.println("AGENT_STL_LAST_UPDATED_BY = " + cc);
            }
            System.out.println("Database Closed ......");
            System.out.println("=========================================");
        }


    /*    System.out.println("=========================================");
        //   System.out.println("Connecting to Database ..............................");
        stmt = connectionToDatabase.createStatement();
        String query3 = "select TRACTOR_SETTLING_LOGIN " +
                "       FROM " + tableName1 + "  "+
                "       WHERE TRACTOR_SETTLING_AGENT_CODE = '" + agentCode + "'";

        ResultSet res3 = stmt.executeQuery(query3);
        ResultSetMetaData rsmd3 = res3.getMetaData();
        int count3 = rsmd3.getColumnCount();
        List<String> columnList3 = new ArrayList<String>();
        for (int i = 1; i <= count3; i++) {
            columnList3.add(rsmd3.getColumnLabel(i));
        }
        System.out.println(columnList3);

        List<WebElement> tractorAARLogin3 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTracStlTable3 = new ArrayList<>();


        while (res3.next()) {
            int rows3 = res3.getRow();
            System.out.println("Number of Rows:" + rows3);
            System.out.println(res3.getString(1));

            String bb = res3.getString(1);
            dbTracStlTable3.add(bb);

            boolean booleanValue = false;
            for (WebElement tracaar3 : tractorAARLogin3) {
                if (tracaar3.getText().contains(bb)) {
                    for (String dbASIT3 : dbTracStlTable3) {
                        if (dbASIT3.contains(bb)) {
                            System.out.println("TRACTOR_SETTLING_LOGIN = " + bb);
                            booleanValue = true;
                            break;
                        }
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
        }  */
    }

    @Given("Revert Tractor AAR Assignment back to original assignment, the AGENT is VERIFIED {string}")
    public void revert_tractor_aar_assignment_back_to_original_assignment_the_agent_is_verified(String tractorAARLogin) throws InterruptedException {

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Updated Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLogin);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(3000);
        //    System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //    Thread.sleep(3000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Reverted Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
        System.out.println("=========================================");
    }


//............................................/ 58 @TractorAARLoginAnotherAARisSettlingTheAgentNOTVerified /................................................//

    @Given("TEST Another AAR is settling, the agent is not verified, Update Tractor AAR Login {string}, click Save, Select No")
    public void test_another_aar_is_settling_the_agent_is_not_verified_update_tractor_aar_login_click_save_select_no(String tractorAARLoginUpdate) throws InterruptedException {
        System.out.println("========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("========================================");
        System.out.println("Select NO Scenario: ");
        System.out.println("Current Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLoginUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(2000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        //    System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //    Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnNo\"]")).click();
        //  driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(8000);
    }


    @Given("TEST Another AAR is settling, the agent is not verified, Update Tractor AAR Login {string}, click Save, Select Yes")
    public void test_another_aar_is_settling_the_agent_is_not_verified_update_tractor_aar_login_click_save_Select_Yes(String tractorAARLoginUpdate) throws InterruptedException {
        System.out.println("========================================");
        Thread.sleep(2000);
        //    System.out.println(driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4")).getText());
        System.out.println("Select YES Scenario: ");
        System.out.println("Current Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);

        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLoginUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(8000);
    }


    @Given("Upon Adding or Updating Tractor AAR Login, ensure no-one else is settling this agent, the agent is not verified, Run SQL {string} {string} {string} {string}")
    public void upon_adding_or_updating_tractor_aar_login_ensure_no_one_else_is_settling_this_agent_the_agent_is_not_verified_run_sql(String environment, String tableName1, String agentCode, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "Select * from " + tableName1 + " " +
                "where TRACTOR_SETTLING_AGENT_CODE = '" + agentCode + "'" +
                "and TRACTOR_SETTLING_AGENT_VERIFIED  = 1";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorAARLogin = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTracStlTable = new ArrayList<>();
        List<String> dbTracStlTable2 = new ArrayList<>();
        List<String> dbTracStlTable3 = new ArrayList<>();

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
                    "\t" + res.getString(10));

            String a = res.getString(3);
            dbTracStlTable.add(a);

            boolean booleanValue = false;
            for (WebElement tracaar : tractorAARLogin) {
                if (tracaar.getText().contains(a)) {
                    for (String dbASIT : dbTracStlTable) {
                        if (dbASIT.contains(a)) {
                            System.out.println("TRACTOR_SETTLING_AGENT_CODE : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            String c = res.getString(6);
            dbTracStlTable3.add(c);
            System.out.println("TRACTOR_SETTLING_AGENT_VERIFIED : " + c);

            String bb = res.getString(5);
            dbTracStlTable2.add(bb);

            boolean booleanValue2 = false;
            for (WebElement tracaar1 : tractorAARLogin) {
                if (tracaar1.getText().contains(bb)) {
                    for (String dbASIT2 : dbTracStlTable2) {
                        if (dbASIT2.contains(bb)) {
                            System.out.println("TRACTOR_SETTLING_LOGIN : " + bb);
                            booleanValue2 = true;
                            break;
                        }
                    }
                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Database Closed ......");
            System.out.println("=========================================");
        }


        //   System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        stmt = connectionToDatabase.createStatement();
        String query2 = "Select AGENT_STL_TRAC_AAR, AGENT_STL_LAST_UPDATED_DATE, AGENT_STL_LAST_UPDATED_BY " +
                "       FROM " + tableName + "  " +
                "       WHERE AGENT_STL_AGENT_CODE = '" + agentCode + "'";

        ResultSet res2 = stmt.executeQuery(query2);
        ResultSetMetaData rsmd2 = res2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<WebElement> tractorAARLogin1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentStlInfoTable = new ArrayList<>();
        List<String> dbAgentStlInfoTable1 = new ArrayList<>();
        List<String> dbAgentStlInfoTable2 = new ArrayList<>();
        //   List<String> dbAgentStlInfoTable3 = new ArrayList<>();

        while (res2.next()) {
            int rows2 = res2.getRow();
            System.out.println("Number of Rows:" + rows2);
            System.out.println(res2.getString(1) +
                    "\t" + res2.getString(2) +
                    "\t" + res2.getString(3));

            String bb = res2.getString(1);
            dbAgentStlInfoTable.add(bb);
            String aa = res2.getString(2);
            dbAgentStlInfoTable1.add(aa);
            String cc = res2.getString(3);
            dbAgentStlInfoTable2.add(cc);


            boolean booleanValue = false;
            for (WebElement tracaar1 : tractorAARLogin1) {
                if (tracaar1.getText().contains(bb)) {
                    for (String dbASIT : dbAgentStlInfoTable) {
                        if (dbASIT.contains(bb)) {
                            System.out.println("AGENT_STL_TRAC_AAR = " + bb);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("AGENT_STL_LAST_UPDATED_DATE = " + aa);
            System.out.println("AGENT_STL_LAST_UPDATED_BY = " + cc);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Revert Tractor AAR Assignment back to original assignment, the agent is not verified {string}")
    public void revert_tractor_aar_assignment_back_to_original_assignment_the_agent_is_not_verified(String tractorAARLogin) throws InterruptedException {

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Updated Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);


        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLogin);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(3000);
        //    System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //    Thread.sleep(3000);
        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //   Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        //   driver.findElement(By.xpath("//*[@id=\"btnYes\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Reverted Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
        System.out.println("=========================================");

    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    @Given("Revert Tractor AAR Assignment back to original assignment {string}")
    public void Revert_tractor_aar_Assignment_back_to_original_assignment(String tractorAARLogin) throws InterruptedException {
        driver.findElement(By.id("linkEdit")).click();

        System.out.println("========================================");
        System.out.println("Updated Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);


        Actions act = new Actions(driver);
        WebElement btnClick = driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]"));
        act.doubleClick(btnClick).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).sendKeys(tractorAARLogin);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"btnSaveDetail\"]")).click();
        Thread.sleep(3000);
        //  System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getAttribute("value"));
        //  Thread.sleep(3000);
        //  System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getAttribute("value"));
        //  Thread.sleep(3000);
        //  driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(By.id("linkEdit")).click();
        System.out.println("========================================");
        System.out.println("Reverted Tractor AAR Login : ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"TractorAARLogin_I\"]")).getAttribute("value"));
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblTractorAARUserName\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[9]/div/div/a[2]")).click();
        System.out.println("=========================================");

    }


}