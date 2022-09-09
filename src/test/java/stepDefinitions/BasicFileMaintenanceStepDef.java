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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;


public class BasicFileMaintenanceStepDef {
    WebDriver driver;
    String url = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(driver);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    BasicFileMaintenancePage basicFileMaintenancePage = new BasicFileMaintenancePage(driver);
    AccountFilePage accountFilePage = new AccountFilePage(driver);
    LocationFilePage locationFilePage = new LocationFilePage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();


//............................................/ Background /................................................//

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" and Enter the url for EBH Basic File Maintenance$")
    public void run_Test_for_on_Browser_and_Enter_the_url_for_EBH_Basic_File_Maintenance(String environment, String browser) {
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

    @And("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Basic File Maintenance$")
    public void Login_to_the_Agents_Portal_with_username_and_password_for_EBH_Basic_File_Maintenance(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu and to the Basic File Maintenance page for EBH Basic File Maintenance$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_and_to_the_Basic_File_Maintenance_page_for_EBH_Basic_File_Maintenance() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        driver.findElement(corporatePage.basicFileMaintenance).click();
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }


    @Then("^Close all open Browsers on EBH$")
    public void Close_all_open_Browsers_on_EBH() {
        driver.close();
        driver.quit();
    }


    //............................................/ 20 @AccountFile /................................................//

    @Given("^Navigate to the Account File page$")
    public void Navigate_to_the_Account_File_page() {
        driver.findElement(basicFileMaintenancePage.AccountFile).click();
    }

    @And("^Enter Agent/Location Code as \"([^\"]*)\" in Search In Box and click$")
    public void Enter_Agent_Location_Code_as_in_Search_In_Box_and_click(String code) throws
            InterruptedException {
        driver.findElement(accountFilePage.SearchIn).sendKeys(code + "\n");
        Thread.sleep(1000);
    }

    @And("^Verify Bill-To is Checked on and get the Records Returned for \"([^\"]*)\"$")
    public void verify_Bill_To_is_Checked_on_and_get_the_Records_Returned_for(String code) throws
            InterruptedException {
        System.out.println("=========================================");
        System.out.println("Bill-To is Checked On : " + driver.findElement(accountFilePage.BillToButton).isSelected());
        Thread.sleep(5000);
        System.out.println("Records returned when Bill-To is Checked On for " + code + " : " + driver.findElement(By.id("lblRowCount")).getText());
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable_wrapper\"]/div[2]/div")).getText());
        //*[@id="dataTable"]
        //*[@id="dataTable"]/tbody
        Thread.sleep(10000);
    }

    @And("^Validate Records Returned on Bill-To with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Bill_To_with_Database_Record_and(String environment, String
            tableName, String accountName, String assertValue) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [Account_Code]\n" +
                "       ,[Account_Name] \n" +
                "       ,[Account_Addr1]\n" +
                "       ,[Account_Addr2]\n" +
                "       ,[Account_City]\n" +
                "       ,[Account_State]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [ACCOUNT_BILLTO] = 1\n" +
                "       AND [Account_Name] LIKE '" + accountName + "' OR [Account_Code] LIKE '" + accountName + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAccountsTable = new ArrayList<>();
        List<WebElement> billToAccountsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
            System.out.println();

            String a = rs.getString(2);
            dbAccountsTable.add(a);

        }
        String b = "" + assertValue + "";

        boolean booleanValue = false;
        for (WebElement billToTable : billToAccountsTable) {
            if (billToTable.getText().contains(b)) {
                for (String dbaTable : dbAccountsTable) {
                    if (dbaTable.contains(b)) {
                        System.out.println(b);
                        System.out.println();
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
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on SH/CON, Verify SH/CON is Checked on and get the Records Returned for \"([^\"]*)\"$")
    public void click_on_SH_CON_Verify_SH_CON_is_Checked_on_and_get_the_Records_Returned_for(String code) throws
            InterruptedException {
        Thread.sleep(8000);
        driver.findElement(accountFilePage.SHCONButton).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println("SH/CON is Checked On : " + driver.findElement(accountFilePage.SHCONButton).isSelected());
        System.out.println("Records returned when SH/CON is Checked On for " + code + " : " + driver.findElement(By.id("lblRowCount")).getText());
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable_wrapper\"]")).getText());
        Thread.sleep(10000);
    }

    @And("^Validate Records Returned on SH/CON with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_SH_CON_with_Database_Record_and(String environment, String
            tableName, String accountName, String assertValue2) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000[Account_Code]\n" +
                "       ,[Account_Name] \n" +
                "       ,[Account_Addr1]\n" +
                "       ,[Account_Addr2]\n" +
                "       ,[Account_City]\n" +
                "       ,[Account_State]\n" +
                "       FROM " + tableName + "" +
                "       WHERE [ACCOUNT_SHIPCON] = 1\n" +
                "       AND [Account_Name] LIKE '" + accountName + "' OR [Account_Code] LIKE '" + accountName + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbShConTable = new ArrayList<>();
        List<WebElement> shipConAccountsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
            System.out.println();

            String a = rs.getString(1);
            dbShConTable.add(a);
        }

        String str = "" + assertValue2 + "";

        boolean booleanValue = false;
        for (WebElement shConToTable : shipConAccountsTable) {
            if (shConToTable.getText().contains(str)) {
                for (String dbShTable : dbShConTable) {
                    if (dbShTable.contains(str)) {
                        System.out.println(str);
                        System.out.println();
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
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click Select of first column$")
    public void Click_Select_of_first_column() {
        driver.findElement(accountFilePage.SelectFirstColumn).click();
    }

    @Then("^Validate Table Header and Name on Account File Maintenance$")
    public void Validate_Table_Header_and_Name_on_Account_File_Maintenance() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        assertTrue(driver.findElement(accountFilePage.AccountFileMaintenance).isDisplayed());
        System.out.println(driver.findElement(accountFilePage.AccountFileMaintenance).getText());
        Thread.sleep(5000);
        assertTrue(driver.findElement(accountFilePage.Name).isDisplayed());
        System.out.println(driver.findElement(accountFilePage.Name).getText());
        System.out.println("=========================================");
    }


    //............................................/ 21 @LocationFile /................................................//

    @Given("^Navigate to the Basic File Maintenance page$")
    public void Navigate_to_the_Basic_File_Maintenance_page() {
        driver.findElement(corporatePage.basicFileMaintenance).click();
    }

    @And("^Navigate to the Location File page$")
    public void Navigate_to_the_Location_File_page() {
        driver.findElement(basicFileMaintenancePage.LocationFile).click();
    }


    @Given("^Enter Part of Location Name as \"([^\"]*)\" and click$")
    public void enter_Part_of_Location_Name_as_and_click(String partOfLocationName) throws InterruptedException {
        driver.findElement(locationFilePage.PartOfLocationName).sendKeys(partOfLocationName + "\n");
        Thread.sleep(5000);
    }

    @Given("^Validate Records Returned for Location File Maintenance with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_for_Location_File_Maintenance_with_Database_Record_and(String environment, String tableName, String partOfLocationName1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 [LOCATION_CODE]\n" +
                "      ,[LOCATION_NAME]\n" +
                "      ,[LOCATION_ADDR1]\n" +
                "      ,[LOCATION_ADDR2]\n" +
                "      ,[LOCATION_CITY]\n" +
                "      ,[LOCATION_STATE]\n" +
                "  FROM " + tableName + "\n" +
                "  WHERE [LOCATION_NAME] like '" + partOfLocationName1 + "'";


        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbLocationsTable = new ArrayList<>();
        List<WebElement> locationFileMainteTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
            System.out.println();

            String a = rs.getString(2);
            dbLocationsTable.add(a);

            boolean booleanValue = false;
            for (WebElement locaTable : locationFileMainteTable) {
                if (locaTable.getText().contains(a)) {
                    for (String dbLocaTable : dbLocationsTable) {
                        if (dbLocaTable.contains(a)) {
                            System.out.println(a);
                            System.out.println();
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
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("^Click on Select and Validate Name, Status and AAR Rep$")
    public void click_on_Select_and_Validate_Name_Status_and_AAR_Rep() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"selectFocus\"]")).click();

        Thread.sleep(5000);
        assertTrue(driver.findElement(locationFilePage.LocationCode).isDisplayed());

        WebElement LocationCode = driver.findElement(locationFilePage.LocationCode);
        // String aa = driver.findElement(locationFilePage.LocationCode).getAttribute());
        //   assertEquals();
        //  System.out.println(driver.findElement(agentCommissionMaintenancePage.agentCodeDescription).getText());
        System.out.println("Location Code : " + driver.findElement(locationFilePage.LocationCode).getText());
        System.out.println("Name : " + driver.findElement(locationFilePage.Name).getText());
        System.out.println("Status : " + driver.findElement(locationFilePage.Status).getText());
        System.out.println("AAR Rep : " + driver.findElement(locationFilePage.AARRep).getText());
        assertEquals(LocationCode, LocationCode);
    }


}




