package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TractorStepDef {
    WebDriver driver;
    String url = "";
    String usernameExpected = "";

    EBHLoginPage ebhLoginPage = new EBHLoginPage(null);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    TractorVendorRelationshipPage tractorVendorRelationshipPage = new TractorVendorRelationshipPage(driver);
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    FuelPurchaseMaintenancePage fuelPurchaseMaintenancePage = new FuelPurchaseMaintenancePage(driver);
    //   Logger log = Logger.getLogger("TractorStepDef");
    private static Statement stmt;


    //............................................/ Background /................................................//

    @Given("^Run Test for Environment \"([^\"]*)\" on Browser \"([^\"]*)\" for EBH Tractors and Enter the url$")
    public void run_Test_for_Environment_on_Browser_for_EBH_Tractors_and_Enter_the_url(String environment, String browser) {
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

    @And("^Login to the EBH with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Tractors$")
    public void Login_to_the_EBH_with_username_and_password_for_EBH_Tractors(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhLoginPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.password));
        driver.findElement(ebhLoginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.signinButton));
        driver.findElement(ebhLoginPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_then_to_the_Settlements_page_for_EBH_Tractors() {
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


    @Then("^Close all open Browsers on EBH for Tractors$")
    public void Close_all_open_Browsers_on_EBH_for_Tractors() {
        driver.close();
        driver.quit();
    }


    //............................................/ @FPMValidationTractorID /................................................//

    @Given("^Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_then_to_the_Fuel_and_Mileage_page_for_EBH_Tractors() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(corporatePage.fuelAndMileage).click();
    }

    @Given("^Navigate to Fuel Purchase Maintenance Page$")
    public void navigate_to_Fuel_Purchase_Maintenance_Page() {
        driver.findElement(corporatePage.fuelPurchaseMaintenance).click();
    }


    @Given("^Click on Search Button$")
    public void click_on_Search_Button() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);

    }


    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String latestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_for_TractorID(String environment, String tableName, String tractorID, String earliestDateDB, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMValidationTractorIDEarliestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Earliest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_Earliest_Date_for_TractorID(String environment, String tableName, String tractorID, String earliestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMValidationTractorIDLatestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Latest_Date_State_Company(String tractorID, String latestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Latest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_Latest_Date_for_TractorID(String environment, String tableName, String tractorID, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMValidationIFTA /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String latestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_for_IFTA(String environment, String tableName, String earliestDateDB, String latestDateDB, String state, String company, String ifta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................/ @FPMValidationIFTAEarliestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_State_Company_IFTA(String tractorID, String earliestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" Earliest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_Earliest_Date_for_IFTA(String environment, String tableName, String earliestDateDB, String state, String company, String ifta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................/ @FPMValidationIFTALatestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Latest_Date_State_Company_IFTA(String tractorID, String latestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" Latest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_Latest_Date_for_IFTA(String environment, String tableName, String latestDateDB, String state, String company, String ifta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
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
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMScenarioNew /................................................//

    @Given("^Select the New Button, Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_the_New_Button_Enter_required_fields(String company, String tractorID, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.New).click();
        Thread.sleep(3000);
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.PopupHeader).getText());
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).click();
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).click();
        driver.findElement(fuelPurchaseMaintenancePage.DateN).sendKeys(date);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).click();
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.StateN).sendKeys(state);
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
        Thread.sleep(2000);
    }

    @Given("^Click Cancel on Add Fuel Purchase$")
    public void click_Cancel_on_Add_Fuel_Purchase() {
        driver.findElement(fuelPurchaseMaintenancePage.CancelN).click();
    }

    @Given("^Re-Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void re_Enter_required_fields(String company, String tractorID, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).click();
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).sendKeys(company);
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).click();
        driver.findElement(fuelPurchaseMaintenancePage.DateN).sendKeys(date);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).click();
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.StateN).click();
        driver.findElement(fuelPurchaseMaintenancePage.StateN).sendKeys(state);
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
    }

    @Given("^Click Save on Add Fuel Purchase$")
    public void click_Save_on_Add_Fuel_Purchase() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.SaveN).click();
        Thread.sleep(2000);
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        driver.findElement(By.id("btnYesAction")).click();
        System.out.println("========================================");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        driver.findElement(By.id("btnOK")).click();
        driver.findElement(By.id("crossIconPopup")).click();
        System.out.println("========================================");
    }


    @Given("^Select \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" from dropdown$")
    public void select_from_dropdown(String company, String tractor, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(company)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(tractor)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img3\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(date)) {
                webElement.click();
                break;
            }
        }


        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(location)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(state)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list6) {
            if (webElement.getText().contains(gallons)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list7) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }

        Thread.sleep(3000);
    }


    @Given("^Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String tractorID, String company, String date, String location, String state, String gallons, String amount) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] = '" + date + "'" +
                "       AND [FUEL_PUR_LOCATION] = '" + location + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_GALLONS] = '" + gallons + "'" +
                "       AND [FUEL_PUR_TOTAL] = '" + amount + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println(" TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }
                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMScenarioEdit /................................................//


    @Given("^Select the Tractor from dropdown \"([^\"]*)\", Select Location from dropdown \"([^\"]*)\" and Select State from dropdown \"([^\"]*)\"$")
    public void select_the_Tractor_from_dropdown_and_Select_Location_from_dropdown_and_Select_State_from_dropdown(String tractor, String location, String state) {
        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list) {
            //   System.out.println(webElement.getText());
            if (webElement.getText().contains(tractor)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            //  System.out.println(webElement.getText());
            if (webElement.getText().contains(location)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list2) {
            //  System.out.println(webElement.getText());
            if (webElement.getText().contains(state)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Click on Edit, and edit the fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void click_on_Edit_and_edit_the_fields(String location1, String gallons1, String amount1) throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.Edit).click();
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4")).getText());
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location1);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons1);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount1);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
    }

    @Given("^Click Save on Edit Fuel Purchase$")
    public void click_Save_on_Edit_Fuel_Purchase() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.SaveN).click();
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        driver.findElement(By.id("btnOK")).click();
        Thread.sleep(6000);


        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img3\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list4) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list5) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list6) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list7) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();

        Thread.sleep(3000);
    }


    @Given("^Validate the Newly Edited Record with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Newly_Edited_Record_with_Database_Record_and(String environment, String tableName, String tractorID, String company, String date, String location1, String state, String gallons1, String amount1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] = '" + date + "'" +
                "       AND [FUEL_PUR_LOCATION] = '" + location1 + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_GALLONS] = '" + gallons1 + "'" +
                "       AND [FUEL_PUR_TOTAL] = '" + amount1 + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();
        List<String> dbFPM3 = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(7);
            dbFPM2.add(c);
            String d = rs.getString(8);
            dbFPM3.add(d);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION1: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println(" GALLONS1: " + c);
                            booleanValue2 = true;
                        }
                        break;
                    }

                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            boolean booleanValue3 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(d)) {
                    for (String dbfpm3 : dbFPM3) {
                        if (dbfpm3.contains(d)) {
                            System.out.println("  AMOUNT1: " + d);
                        }
                        booleanValue3 = true;
                        break;
                    }
                }
                if (booleanValue3) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ @FPMScenarioReport /................................................//

    @Given("^Click on Report Button, and Click on ALL RECORDS$")
    public void click_on_Report_Button_and_Click_on_ALL_RECORDS() {
        driver.findElement(fuelPurchaseMaintenancePage.Report).click();
        driver.findElement(fuelPurchaseMaintenancePage.AllRecords).click();
    }


    @And("^Get ALL RECORDS Excel Report from Downloads for FPM$")
    public void get_ALL_RECORDS_Excel_Report_from_Downloads_for_FPM() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(5000);
        String fileNameFPMAR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("ALL RECORDS Excel Report File Name :-" + fileNameFPMAR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameFPMAR + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

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
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("^Validate ALL RECORDS Excel Report with Database Record \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_ALL_RECORDS_Excel_Report_with_Database_Record(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    SELECT * FROM " + tableName + " " +
                "    WHERE [FUEL_PUR_ISDELETED] = 0 ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

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
                    "\t" + res.getString(15));

            String a = res.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                            booleanValue = true;
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
        }
        System.out.println("Excel Report Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Click on Report Button and Click on SEARCH RESULTS$")
    public void click_on_Report_Button_and_Click_on_Search_Results() {
        driver.findElement(fuelPurchaseMaintenancePage.Report).click();
        driver.findElement(fuelPurchaseMaintenancePage.SearchResults).click();
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for FPM$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_FPM() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(8000);
        String fileNameFPMSR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("SEARCH RESULTS Excel Report File Name :-" + fileNameFPMSR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameFPMSR + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

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
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("^Validate SEARCH RESULTS Excel Report with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_SEARCH_RESULTS_Excel_Report_with_Database_Record(String environment, String tableName, String tractorID, String earliestDateDB, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();


        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

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
                    "\t" + res.getString(15));

            String a = res.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                            booleanValue = true;
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
        }
        System.out.println("Excel Report Closed ......");
        System.out.println("=========================================");
    }


//............................................/ @FPMScenarioReportIFTA /................................................//

    @Given("^Validate SEARCH RESULTS Excel Report with Database Record for IFTA \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_SEARCH_RESULTS_Excel_Report_with_Database_Record_for_IFTA(String environment, String tableName, String earliestDateDB, String latestDateDB, String state, String company, String ifta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

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

            String a = rs.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
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


    //............................................/ @TractorVendorRelationship /................................................//

    @And("^Navigate to Tractor Vendor Relationship$")
    public void Navigate_to_Tractor_Vendor_Relationship() throws InterruptedException {
        driver.findElement(settlementsPage.TractorVendorRelationship).click();
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader));
        driver.findElement(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader).isDisplayed();
        System.out.println("========================================");
        System.out.println(driver.findElement(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader).getText());
    }

    @Given("^Enter Unit Number \"([^\"]*)\" and click on Search$")
    public void enter_unit_number_and_click_on_search(String unitNumber) throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).sendKeys(unitNumber);
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).click();
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
    }

    @Given("Validate the Total Records Returned with database Records {string} {string} {string}")
    public void validate_the_total_records_returned_with_database_records(String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship2 = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<WebElement> tractorVendorRelationship1 = driver.findElements(By.xpath("  //*[@id=\"tdstatus\"]"));
        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();

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
            System.out.println();


            String a = res.getString(2);
            dbtractorVendor.add(a);
            String b = res.getString(6);
            dbtractorVendor1.add(b);
            System.out.println("UNIT NO : " + a);
            System.out.println("ACCOUNTING STATUS : " + b);

         /*   boolean booleanValue = false;
            for (WebElement tVR : tractorVendorRelationship) {
                if (tVR.getText().contains(a)) {
                    for (String dbTV : dbtractorVendor) {
                        if (dbTV.contains(a)) {
                            System.out.println("UNIT NO : " + a);
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

            boolean booleanValue1 = false;
            for (WebElement tVR1 : tractorVendorRelationship1) {
                if (tVR1.getText().contains(b)) {
                    for (String dbTV1 : dbtractorVendor1) {
                        if (dbTV1.contains(b)) {
                            System.out.println("ACCOUNTING STATUS : " + b);
                            booleanValue1 = true;
                        }
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }   */
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


    @Given("Validate the Total Records Returned with database Records DRIVERthreesixty {string} {string}")
    public void validate_the_total_records_returned_with_database_records_DRIVERthreesixty(String environment1, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [OwnerID]\n" +
                "      ,[Name]\n" +
                "      ,[Address1]\n" +
                "      ,[Address2]\n" +
                "      ,[City]\n" +
                "      ,[State]\n" +
                "      ,[Zip]\n" +
                "      ,[OwnerType]\n" +
                "      ,[Phone1]\n" +
                "      ,[Phone2]\n" +
                "      ,[BirthDate]\n" +
                "      ,[FedIDorSSN]\n" +
                "      ,[HashValue]\n" +
                "      ,[LicenseNo]\n" +
                "      ,[LicenseState]\n" +
                "      ,[Email]\n" +
                "      ,[Firstname]\n" +
                "      ,[Lastname]\n" +
                "      ,[TempOwnerID]\n" +
                "  FROM [Driver360Staging].[dbo].[EQOwner]";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable4 = new ArrayList<>();

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
                    "\t" + res.getString(15));

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);
            System.out.println("AGENT CODE : " + a);

         /*   boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE : " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }  */

        }


    }


    @Given("Click on Report Button and on SEARCH RESULTS")
    public void click_on_report_button_and_on_search_results() {
     driver.findElement(tractorVendorRelationshipPage.Report).click();
        driver.findElement(tractorVendorRelationshipPage.SearchResults).click();
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_EBH_Tractors() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(5000);
        String fileNameAllAdjustments = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameAllAdjustments);
        driver.close();


        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameAllAdjustments + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

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
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("Validate the Excel Search result Report with database Records {string} {string} {string}")
    public void validate_the_excel_search_result_report_with_database_records(String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + ""  +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbTV = new ArrayList<>();

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
                    "\t" + rs.getString(10));

            String a = rs.getString(2);
            dbTV.add(a);

            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(a)) {
                    for (String dbtv : dbTV) {
                        if (dbtv.contains(a)) {
                            System.out.println("UNIT NO: " + a);
                        }
                        booleanValue = true;
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


    @Given("Click on View")
    public void click_on_view() throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.View).click();
        Thread.sleep(8000);
        driver.findElement(tractorVendorRelationshipPage.CrossIconPopup).click();

        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4")).getAttribute("value"));
        System.out.println("CREATED BY : " + driver.findElement(By.xpath("//*[@id=\"lblCreatedBy\"]")).getAttribute("value") + driver.findElement(By.xpath("//*[@id=\"lblCreatedDate\"]")).getAttribute("value"));
        System.out.println("UPDATED BY : " + driver.findElement(By.xpath("//*[@id=\"lblUpdatedBy\"]")).getAttribute("value") + driver.findElement(By.xpath("//*[@id=\"lblUpdatedDate\"]")).getAttribute("value"));

    }

    @Given("Click on Edit")
    public void click_on_edit() throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.Edit).click();
        Thread.sleep(5000);

        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).clear();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(tractorVendorRelationshipPage.AccountingStatus);
        act.doubleClick(ele).perform();
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).sendKeys("ACCOUNTING HOLD");
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).click();
        Thread.sleep(5000);

        driver.findElement(tractorVendorRelationshipPage.Cancel).click();
        driver.findElement(tractorVendorRelationshipPage.CrossIconPopup).click();


    }

    @Given("Click on New")
    public void click_on_new() throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.New).click();
        Thread.sleep(5000);
        driver.findElement(tractorVendorRelationshipPage.Cancel).click();
        driver.findElement(tractorVendorRelationshipPage.CrossIconPopup).click();

    }
}