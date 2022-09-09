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
import stepDefinitions.Pages.EquipmentConsoleLoginPage;
import stepDefinitions.Pages.EquipmentConsolePage;
import stepDefinitions.Pages.EquipmentConsoleTractorAdjustmentsPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class EquipmentConsoleTractorAdjustmentsStepDef {
    WebDriver driver;
    String url = "";
    String beforeChange = "";

    EquipmentConsoleLoginPage equipmentConsoleLoginPage = new EquipmentConsoleLoginPage(driver);
    EquipmentConsoleTractorAdjustmentsPage equipmentConsoleTractorAdjustmentsPage = new EquipmentConsoleTractorAdjustmentsPage(driver);
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();


    //............................................/ BACKGROUND /................................................//

    @Given("Run Test for {string} on Browser {string} and Enter the url for Tractor")
    public void run_test_for_on_browser_and_enter_the_url_for_tractor(String environment1, String browser) {

      //  BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
        url = browserDriverInitialization.getDataFromPropertiesFileForEquipmentConsole(environment1, browser);
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

    @Given("Login to the Portal with USERNAME {string} and PASSWORD {string} for Tractor")
    public void login_to_the_portal_with_username_and_password_for_tractor(String username, String password) {

        driver.findElement(equipmentConsoleLoginPage.username).sendKeys(username);
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.password));
        driver.findElement(equipmentConsoleLoginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.loginButton));
        driver.findElement(equipmentConsoleLoginPage.loginButton).click();
    }

    @Given("Click NO on alert")
    public void click_no_on_alert() {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNo).click();
    }

    @Then("^Close all open Browsers on Equipment Console$")
    public void close_all_open_Browsers_on_Equipment_Console() {
        driver.close();
        driver.quit();
    }

    //....................../  @IdentifyINVOICEforTractorReimbursement /..............................//


    @Given("^Locate a Record from Database for Tractor Reimbursement, Agent Status must have a Status of Agent Review or Corp Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Locate_a_Record_from_Database_for_Tractor_Reimbursement_Agent_Status_must_have_a_Status_of_Agent_Review_or_Corp_Review(String environment, String tableName, String tableName1, String tableName2, String tableName3, String tableName4, String tableName5) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // SQL#1
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = ";WITH \n" +
                "PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "AS (\n" +
                "    SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "    From " + tableName1 + " With(Nolock)\n" +
                "    Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "\t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "          AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "    Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "   ), \n" +
                "IRRCTE ( InvoiceRegisterID, Agent, ProNumber)\n" +
                "AS (\n" +
                "    SELECT  InvoiceRegisterID, Agent, ProNumber\n" +
                "    From " + tableName + " With(Nolock)\n" +
                "    Where     AgentStatus IN ('CorpReview', 'AgentReview') and Agent is NOT Null and ProNumber  <> ''\n" +
                "    Group By  InvoiceRegisterID, Agent, ProNumber\n" +
                "   )\n" +
                "   ,\n" +
                "SICTE (Agent_Stl_Agent_Code, Agent_Stl_Company_Code)\n" +
                "\n" +
                "AS (\n" +
                "    SELECT  Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "    From " + tableName2 + "  With(Nolock)\n" +
                "    Group By Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "   )\n" +
                "\n" +
                "   \n" +
                "   ,\n" +
                "SLCTE (Sl_Agent)\n" +
                "\n" +
                "AS (\n" +
                "    SELECT  distinct substring(STL_PRONUM, 1,3) as Sl_Agent\n" +
                "    From " + tableName3 + " With(Nolock)\n" +
                "\t    Group By substring(STL_PRONUM, 1,3)\n" +
                ")\n" +
                "Select  Distinct ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue, ir.TypeOfBill, Matrix_Prim_Vendor, MATRIX_COMP_CODE, sl.Sl_Agent\n" +
                "FROM [Evans].[dbo].[InvoiceRegister]ir With(Nolock)\n" +
                "inner Join IRRCTE irr With(Nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId\n" +
                "inner Join PMCTE apm With(Nolock) on irr.Agent = apm.MATRIX_LOC_CODE\n" +
                "Inner join " + tableName4 + " o With(Nolock) on o.Ord_loc = irr.Agent and o.Ord_num = irr.ProNumber\n" +
                "Inner join " + tableName5 + " L With(Nolock) on l.LOCATION_CODE = o.ord_loc\n" +
                "Inner join SICTE si With(Nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE\n" +
                "Inner join SLCTE sl With(Nolock) on o.Ord_loc = sl_Agent\n" +
                "Where InvoiceDate >= '01/01/2021' \n" +
                "Order by IRR.Agent, ir.InvoiceNumber, ir.InvoiceDate, IRR.ProNumber\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder = new ArrayList<>();
        List<String> stringBuilder2 = new ArrayList<>();

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
                    "\t" + res.getString(9));

        //    StringBuilder sb = new StringBuilder();
         //   sb.append(res.getString(1)).append(" ").append(res.getString(19)).append(" ").append(res.getString(20));
       //     stringBuilder.add(sb.toString());
         //   System.out.println("Invoice No, Agent Status and Corp Status:  " + sb);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(res.getString(3)).append(" ").append(res.getString(4));
            stringBuilder2.add(sb2.toString());
            System.out.println("Agent and Pro Number:  " + sb2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//................................/ 37b @AgentReimbursement /.......................................//


    @Given("Enter Invoice Number {string} in Search Criteria on Equipment Console Interface for Tractor")
    public void enter_invoice_number_in_search_criteria_on_equipment_console_interface_for_tractor(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.invoice).sendKeys(invoiceNo);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.refresh).click();
        Thread.sleep(5000);
    }

    @Given("Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review {string} for Tractor")
    public void select_agent_status_button_for_a_record_that_has_a_agent_status_agent_review_or_corp_review_for_tractor(String agentStatus) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus).click();
     //   List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("Select Agent {string} and ProNo {string} for Tractor")
    public void select_agent_and_pro_no_for_tractor(String agent, String proNum) throws InterruptedException {

        Thread.sleep(3000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        boolean booleanValue = false;
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
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

    @Given("Select Driver Reimbursement on Status {string}")
    public void select_driver_reimbursement_on_status(String driverReimbursement) throws InterruptedException {

        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(driverReimbursement)) {
                webElement.click();
                break;
            }
        }
    }

    /*
    @Given("^Enter Amount OR # of Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Amount_OR_of_Days_Effective_Date_Todays_Date(String amount, String effDate) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).click();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).sendKeys(effDate);
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(" //*[@id=\"Text35\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"Text35\"]")).click();
    }

    @Given("^Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column$")
    public void verify_Notes_Column_has_the_Customer_Number_Chassis_No_and_Container_No_prefilled_in_the_notes_column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("AGENT STATUS, AGENT REVIEW, AGENT REIMBURSEMENT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).getAttribute("value"));
        System.out.println();
    }

    @Given("^Enter something into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No \"([^\"]*)\"$")
    public void enter_something_into_the_Notes_Column_and_make_sure_to_enter_a_comma_Select_Ok_Select_Go_Select_No(String notes) {

        driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).sendKeys(notes);
        driver.findElement(equipmentConsolePage.alertNotes).isDisplayed();
        driver.findElement(equipmentConsolePage.ok).click();
        driver.findElement(equipmentConsolePage.go).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.no).click();
    }

    @Given("^Verify previously entered data remained the same, Select Go, Select Yes, Main Form appears$")
    public void verify_previously_entered_data_remained_the_same_Select_Go_Select_Yes_Main_Form_appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("ENTERED DATAS:");
        System.out.println("Amount: " + driver.findElement(By.xpath("//*[@id=\"Text35\"]")).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(By.xpath("//*[@id=\"Text36\"]")).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).getAttribute("value"));
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.go).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.yes).click();
        driver.findElement(By.xpath("/html/body/div[7]/div[11]/div/button/span")).click();
    }

    @Given("^Query Data in Agent Adjustments Table, There should be no record in this table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Agent_Adjustments_Table_There_should_be_no_record_in_this_table_for_this_transaction(String environment, String tableName, String createdDate, String orderNum) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +
                "PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "AS (\n" +
                "    SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "    From [EBHLaunch].[dbo].[Agent_Pay_Matrix] With(nolock)\n" +
                "    Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "\t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "          AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "    Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "   )\n" +
                "\n" +
                "\n" +
                "Select 'Primary Vendor Info', pm.MATRIX_LOC_CODE, pm.MATRIX_COMP_CODE, pm.MATRIX_PRIM_VENDOR, '   ', 'Agent Adjustment Record', \n" +
                "aa.AGENT_ADJUST_ID, aa.AGENT_ADJUST_VENDOR_CODE, aa.AGENT_ADJUST_PAY_CODE, aa.AGENT_ADJUST_STATUS, aa.AGENT_ADJUST_FREQ, \n" +
                "aa.AGENT_ADJUST_AMOUNT_TYPE, aa.AGENT_ADJUST_AMOUNT, aa.AGENT_ADJUST_TOP_LIMIT, aa.agent_adjust_total_to_date,  aa.AGENT_ADJUST_LAST_DATE,\n" +
                "aa.AGENT_ADJUST_MAX_TRANS, aa.AGENT_ADJUST_START_DATE, aa.AGENT_ADJUST_END_DATE, aa.AGENT_ADJUST_PAY_VENDOR, aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "aa.AGENT_ADJUST_NOTE, aa.AGENT_ADJUST_CREATED_BY, aa.AGENT_ADJUST_CREATED_DATE, aa.AGENT_ADJUST_UPDATED_BY, aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "aa.AGENT_ADJUST_IS_DELETED, aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, aa.AGENT_ADJUST_APPLY_TO_AGENT, aa.AGENT_ADJUST_ORDER_NO, aa.AGENT_ADJUST_COMP_CODE\n" +
                "From " + tableName + " AA With(nolock)\n" +
                "Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "'\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

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
                    "\t" + res.getString(31));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Click on Clear Filters$")
    public void Click_on_Click_Filters() throws InterruptedException {
        driver.findElement(equipmentConsolePage.clearFilters).click();
        Thread.sleep(4000);
    }


    @Given("^Select Corp Status = Corp Review for that same record that has Agent Status = Agent Reimbursement and Corp Status = Corp Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_Corp_Status_Corp_Review_for_that_same_record_that_has_Agent_Status_Agent_Reimbursement_and_Corp_Status_Corp_Review(String corpStatus, String agentStatus1, String agent, String proNum) throws InterruptedException {
        driver.findElement(equipmentConsolePage.agentStatus).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.agent).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.proNo).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.corpStatus).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(corpStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Select Agent Reimbursement on CropReview$")
    public void select_Agent_Reimbursement_on_CropReview() {
        driver.findElement(equipmentConsolePage.corpReview).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains("AgentReimbursement")) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^The Days, Amount and Notes Columns are filled in with the same information that was previously entered\\. Enter a different Amount, Days or Effective Date \"([^\"]*)\"$")
    public void the_Days_Amount_and_Notes_Columns_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_a_different_Amount_Days_or_Effective_Date(String ofDays) {
        driver.findElement(By.xpath("//*[@id=\"Text36\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"Text36\"]")).sendKeys(ofDays);
        driver.findElement(By.xpath("//*[@id=\"Text36\"]")).click();
    }

    @Given("^Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the Notes Column$")
    public void verify_Notes_Column_has_the_Customer_Number_Chassis_No_and_Container_No_prefilled_in_the_Notes_Column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("CORP STATUS, AGENT REVIEW, AGENT DEDUCT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).isDisplayed();
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).getAttribute("value"));
        System.out.println();
    }

    @Given("^Enter something into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No \"([^\"]*)\"$")
    public void enter_something_into_the_Notes_Column_and_make_sure_to_enter_a_Comma_Select_Ok_Select_Go_Select_No(String notes1) {
        driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).sendKeys(notes1);
        driver.findElement(equipmentConsolePage.alertNotes).isDisplayed();
        driver.findElement(equipmentConsolePage.ok).click();
        driver.findElement(equipmentConsolePage.go).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.no).click();
    }

    @Given("^Verify previously entered data remained the same, Select Go, Select Yes, Main Form Appears$")
    public void verify_previously_entered_data_remained_the_same_Select_Go_Select_Yes_Main_Form_Appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(("ENTERED DATAS: "));
        System.out.println("Amount: " + driver.findElement(By.xpath("//*[@id=\"Text35\"]")).getAttribute("value"));
        System.out.println("Effective Date: " + driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(By.xpath("//*[@id=\"Text36\"]")).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).getAttribute("value"));
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.go).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        Thread.sleep(4000);
        //  System.out.println("Alert: " + (driver.findElement(By.xpath("//*[@id=\"ui-id-16\"]/div/span"))).getAttribute("value"));
        driver.findElement(equipmentConsolePage.yes).click();
        Thread.sleep(4000);
        driver.findElement(By.xpath("/html/body/div[7]")).isDisplayed();
        System.out.println("Error: " + (driver.findElement(By.xpath("//*[@id=\"dialog-message\"]/div[1]"))).getAttribute("value"));
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[7]/div[11]/div/button/span")).click();
    }


    @Given("^Verify Agent Status and Corp Status = Agent Reimbursement in Main Form$")
    public void verify_Agent_Status_and_Corp_Status_Agent_Reimbursement_in_Main_Form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"griddata_wrapper\"]")).getAttribute("value"));
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getAttribute("value");
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[8]/a")).getAttribute("value");
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        assertEquals("Agent Status and Corp Status = Agent Reimbursement", agentStatus, corpStatus);
    }


    @Given("^Query Data in Agent_Adjustments SQL Table, There should be one record on the Agent_Adjustments table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Agent_Adjustments_SQL_Table_There_should_be_one_record_on_the_Agent_Adjustments_table_for_this_transaction(String environment, String tableName, String createdDate, String orderNum) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // SQL#3
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        //   String useDB = "use " + tableName;
        String query = ";WITH \n" +
                "       PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "       AS (\n" +
                "       SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "       From [EBHLaunch].[dbo].[Agent_Pay_Matrix] With(nolock)\n" +
                "       Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "       \t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "          AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "       Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "      )\n" +
                "       \n" +
                "       \n" +
                "       Select 'Primary Vendor Info', pm.MATRIX_LOC_CODE, pm.MATRIX_COMP_CODE, pm.MATRIX_PRIM_VENDOR, '   ', 'Agent Adjustment Record', \n" +
                "       aa.AGENT_ADJUST_ID, aa.AGENT_ADJUST_VENDOR_CODE, aa.AGENT_ADJUST_PAY_CODE, aa.AGENT_ADJUST_STATUS, aa.AGENT_ADJUST_FREQ, \n" +
                "       aa.AGENT_ADJUST_AMOUNT_TYPE, aa.AGENT_ADJUST_AMOUNT, aa.AGENT_ADJUST_TOP_LIMIT, aa.agent_adjust_total_to_date,  aa.AGENT_ADJUST_LAST_DATE,\n" +
                "       aa.AGENT_ADJUST_MAX_TRANS, aa.AGENT_ADJUST_START_DATE, aa.AGENT_ADJUST_END_DATE, aa.AGENT_ADJUST_PAY_VENDOR, aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "       aa.AGENT_ADJUST_NOTE, aa.AGENT_ADJUST_CREATED_BY, aa.AGENT_ADJUST_CREATED_DATE, aa.AGENT_ADJUST_UPDATED_BY, aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "       aa.AGENT_ADJUST_IS_DELETED, aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, aa.AGENT_ADJUST_APPLY_TO_AGENT, aa.AGENT_ADJUST_ORDER_NO, aa.AGENT_ADJUST_COMP_CODE\n" +
                "       From " + tableName + " AA With(nolock)\n" +
                "       Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "       Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "       and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "       and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "'\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> equipmentConsoleRecord = driver.findElements(By.xpath("//*[@id=\"griddata\"]/tbody"));
        List<String> dbAATable = new ArrayList<>();
        List<String> dbAATable1 = new ArrayList<>();
        List<String> dbAATable2 = new ArrayList<>();

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
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            dbAATable.add(a2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            dbAATable2.add(a11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            dbAATable1.add(a13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            for (WebElement eC : equipmentConsoleRecord) {
                if (eC.getText().contains(a2)) {
                    for (String dbAAT : dbAATable) {
                        if (dbAAT.contains(a2)) {
                            System.out.println("Agent Code: " + a2);
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

            System.out.println();
            System.out.println("=========================================");
            System.out.println("(No column name)                 = " + a1);
            System.out.println("MATRIX_LOC_CODE                  = " + a2);
            System.out.println("MATRIX_COMP_CODE                 = " + a3);
            System.out.println("MATRIX_PRIM_VENDOR               = " + a4);
            System.out.println("(No column name)                 = " + a5);
            System.out.println("(No column name)                 = " + a6);
            System.out.println("AGENT_ADJUST_ID                  = " + a7);
            System.out.println("AGENT_ADJUST_VENDOR_CODE         = " + a8);
            System.out.println("AGENT_ADJUST_PAY_CODE            = " + a9);
            System.out.println("AGENT_ADJUST_STATUS              = " + a10);
            System.out.println("AGENT_ADJUST_FREQ                = " + a11);
            System.out.println("AGENT_ADJUST_AMOUNT_TYPE         = " + a12);
            System.out.println("AGENT_ADJUST_AMOUNT              = " + a13);
            System.out.println("AGENT_ADJUST_TOP_LIMIT           = " + a14);
            System.out.println("agent_adjust_total_to_date       = " + a15);
            System.out.println("AGENT_ADJUST_LAST_DATE           = " + a16);
            System.out.println("AGENT_ADJUST_MAX_TRANS           = " + a17);
            System.out.println("AGENT_ADJUST_START_DATE          = " + a18);
            System.out.println("AGENT_ADJUST_END_DATE            = " + a19);
            System.out.println("AGENT_ADJUST_PAY_VENDOR          = " + a20);
            System.out.println("AGENT_ADJUST_LAST_AMOUNT         = " + a21);
            System.out.println("AGENT_ADJUST_NOTE                = " + a22);
            System.out.println("AGENT_ADJUST_CREATED_BY          = " + a23);
            System.out.println("AGENT_ADJUST_CREATED_DATE        = " + a24);
            System.out.println("AGENT_ADJUST_UPDATED_BY          = " + a25);
            System.out.println("AGENT_ADJUST_LAST_UPDATED        = " + a26);
            System.out.println("AGENT_ADJUST_IS_DELETED          = " + a27);
            System.out.println("AGENT_ADJUST_PAY_VENDOR_PAY_CODE = " + a28);
            System.out.println("AGENT_ADJUST_APPLY_TO_AGENT      = " + a29);
            System.out.println("AGENT_ADJUST_ORDER_NO            = " + a30);
            System.out.println("AGENT_ADJUST_COMP_CODE           = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ 38a @IdentifyINVOICEforAgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable /..................................................../
    // SQL#2 //
    @Given("^Locate a Record to test from Database, Must have a Corp Status of Agent Review or Corp Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void locate_a_Record_to_test_from_Database_Must_have_a_Corp_Status_of_Agent_Review_or_Corp_Review(String environment, String tableName2, String createdDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue,  l.LOCATION_CODE, l.LOCATION_COMP_CODE,  si.AGENT_STL_AGENT_CODE, si.AGENT_STL_COMPANY_CODE, *\n" +
                "       FROM " + tableName2 + " irr With(nolock)\n" +
                "       Inner Join [Evans].[dbo].[InvoiceRegister]ir With(nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId \n" +
                "       Inner join [EBHLaunch].[dbo].[LOCATIONS] L With(nolock) on l.LOCATION_CODE = irr.agent \n" +
                "       Left Join [EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO] si With(nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE \n" +
                "       Where irr.CorpStatus in ('AgentReview', 'CorpReview') and InvoiceDate >= '" + createdDate + "' and si.AGENT_STL_AGENT_CODE is Null";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder = new ArrayList<>();

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
                    "\t" + res.getString(21));

            StringBuilder sb = new StringBuilder();
            sb.append(res.getString(1)).append(" ").append(res.getString(19)).append(" ").append(res.getString(20));
            stringBuilder.add(sb.toString());
            System.out.println("Invoice No, Agent Status and Corp Status:  " + sb);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ 38b @AgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable /..................................................../

    @Given("^Enter Invoice Number \"([^\"]*)\" in Search Criteria on Equipment Console Interface and Click Refresh$")
    public void enter_Invoice_Number_in_Search_Criteria_on_Equipment_Console_Interface_and_Click_Refresh(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsolePage.invoice).sendKeys(invoiceNo);
        driver.findElement(equipmentConsolePage.refresh).click();
        Thread.sleep(5000);
    }

    @Given("^Select a record that has a Corp Status = Corp Review or Agent Review \"([^\"]*)\"$")
    public void select_a_record_that_has_a_Corp_Status_Corp_Review_or_Agent_Review(String corpStatus) throws InterruptedException {

        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.corpStatus).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(corpStatus)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
        System.out.println("=========================================");
        beforeChange = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]")).getText();
        System.out.println("Before Change: ");
        System.out.println(beforeChange);
    }


    @Given("^Select Agent Reimbursement$")
    public void select_Agent_Reimbursement() throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.corpReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains("AgentReimbursement")) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the notes column$")
    public void verify_Notes_Column_has_the_Customer_Number_Chassis_No_and_Container_No_Prefilled_in_the_notes_column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("NOTES:");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber_AR\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).isDisplayed();
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).getAttribute("value"));
        System.out.println("=========================================");
    }

    @Given("^Enter Amount OR Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Amount_OR_Days_Effective_Date_Todays_Date(String amount, String effDate) {
        driver.findElement(By.xpath("//*[@id=\"Text35\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"Text35\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"Text35\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).click();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).sendKeys(effDate);
        driver.findElement(By.xpath("//*[@id=\"AgentReimbursementEffectiveDate\"]")).click();
    }


    @Given("^Enter something into the Notes Column \"([^\"]*)\" and Select Go, Select Yes, Select OK$")
    public void enter_something_into_the_Notes_Column_and_Select_Go_Select_Yes_Select_OK(String notes) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"Textarea1\"]")).sendKeys(notes);
        driver.findElement(equipmentConsolePage.go).click();
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button[1]")).click();
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button")).click();
        Thread.sleep(3000);
    }

    @Given("^Select X to Close Agent Reimbursement Form$")
    public void select_X_to_Close_Agent_Reimbursement_Form() {
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button")).click();
    }

    @Given("^Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the same$")
    public void conform_Equipment_Console_Main_Form_is_Displayed_and_Agent_Status_and_Corp_Status_remained_the_same() throws InterruptedException {
        Thread.sleep(2000);
        String afterChange = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]")).getText();
        System.out.println("After Change:");
        System.out.println(afterChange);
        assertEquals("Agent Status and Corp Status remained the same!!", beforeChange, afterChange);
        System.out.println("Agent Status and Corp Status remained the same!!");
        System.out.println("=========================================");
    }


    //....................../ 39a  @IdentifyINVOICEforAgentDeduction /..............................//

    @Given("^Locate a Record from Database for Agent Deduction Process, Agent Status must have a Status of Agent Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Locate_a_Record_from_Database_for_Agent_Deduction_Process_Agent_Status_must_have_a_Status_of_Agent_Review(String environment, String tableName1, String createdDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // SQL#1 //
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue,  l.LOCATION_CODE, l.LOCATION_COMP_CODE,  si.AGENT_STL_AGENT_CODE, si.AGENT_STL_COMPANY_CODE, *\n" +
                "       FROM " + tableName1 + "irr With(nolock)\n" +
                "       Inner Join [Evans].[dbo].[InvoiceRegister]ir With (nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId \n" +
                "       Inner join [EBHLaunch].[dbo].[LOCATIONS] L With(nolock) on l.LOCATION_CODE = irr.agent \n" +
                "       Inner Join [EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO] si With(nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE \n" +
                "       Where irr.CorpStatus in ('AgentReview', 'CorpReview') and InvoiceDate >= '" + createdDate + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder = new ArrayList<>();
        List<String> stringBuilder2 = new ArrayList<>();

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
                    "\t" + res.getString(25));

            StringBuilder sb = new StringBuilder();
            sb.append(res.getString(1)).append(" ").append(res.getString(19)).append(" ").append(res.getString(20));
            stringBuilder.add(sb.toString());
            System.out.println("Invoice No, Agent Status and Corp Status:  " + sb);

            StringBuilder sb2 = new StringBuilder();
            sb2.append(res.getString(3)).append(" ").append(res.getString(4));
            stringBuilder2.add(sb2.toString());
            System.out.println("Agent and Pro Number:  " + sb2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//................................/ 39b @AgentDeduction/.......................................//

    @Given("^Enter the Invoice Number \"([^\"]*)\" in Search Criteria on Equipment Console Interface$")
    public void enter_the_Invoice_Number_in_Search_Criteria_on_Equipment_Console_Interface(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsolePage.invoice).sendKeys(invoiceNo);
        driver.findElement(equipmentConsolePage.refresh).click();
        Thread.sleep(5000);
    }

    @Given("^Select Agent Status Button for a record that has Agent Status = Agent Review or Corp Review \"([^\"]*)\"$")
    public void select_Agent_Status_Button_for_a_record_that_has_Agent_Status_Agent_Review_or_Corp_Review(String agentStatus) {
        driver.findElement(equipmentConsolePage.agentStatus).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Select the Agent \"([^\"]*)\" and ProNo \"([^\"]*)\"$")
    public void select_the_Agent_and_ProNo(String agent, String proNum) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.agent).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
    /*    driver.findElement(equipmentConsolePage.proNo).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
                break;
            }
        }  */

    /*    driver.findElement(equipmentConsolePage.proNo).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        boolean booleanValue = false;
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
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

    @Given("^Select Agent Deduct on Agent Review Status \"([^\"]*)\"$")
    public void select_Agent_Deduct_on_Agent_Review_Status(String status) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.agentReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Enter Amount Or # of Days, Effective Date = Todays Date, Splits 1 \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Amount_Or_of_Days_Effective_Date_Todays_Date_Splits_1(String amount, String effDate) {
        driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).click();
    }

    @Given("^Verify Notes Column has the Customer Number, Chassis No and Container No, Prefilled in the Notes Column$")
    public void verify_Notes_Column_has_the_Customer_Number_Chassis_No_and_Container_No_Prefilled_in_the_Notes_Column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("AGENT STATUS, AGENT REVIEW, AGENT DEDUCT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).getAttribute("value"));
        System.out.println();
    }

    @Given("^Enter Notes into the Notes Column and make sure to enter Comma, Select Ok, Select Go, Select No \"([^\"]*)\"$")
    public void enter_Notes_into_the_Notes_Column_and_make_sure_to_enter_a_comma_Select_Ok_Select_Go_Select_No(String notes) {
        driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).sendKeys(notes);

        driver.findElement(equipmentConsolePage.alertNotes).isDisplayed();
        driver.findElement(equipmentConsolePage.ok).click();

        driver.findElement(equipmentConsolePage.goAD).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.no).click();
    }

    @Given("^Verify previously entered data remained Same, Select Go, Select Yes, Main Form appears$")
    public void verify_previously_entered_data_remained_Same_Select_Go_Select_Yes_Main_Form_appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("ENTERED DATAS:");
        System.out.println("Amount: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).getAttribute("value"));
        System.out.println("Effective Date: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfDays\"]")).getAttribute("value"));
        System.out.println("Splits: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfSplits\"]")).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).getAttribute("value"));
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.goAD).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.yes).click();
        driver.findElement(By.xpath("/html/body/div[7]/div[11]/div/button/span")).click();
    }

    @Given("^Verify Agent Status and Corp Status = Agent Deduct in Main Form$")
    public void verify_Agent_Status_and_Corp_Status_Agent_Deduct_in_Main_Form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"griddata_wrapper\"]")).getAttribute("value"));
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getAttribute("value");
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[8]/a")).getAttribute("value");
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        assertEquals("Agent Status and Corp Status = Agent Deduct", agentStatus, corpStatus);
    }


    @Given("^Query Data in Agent Adjustments Table, There should be no Record in this table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Agent_Adjustments_Table_There_should_be_no_Record_in_this_table_for_this_transaction(String environment, String tableName, String createdDate, String orderNum) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +
                "PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "AS (\n" +
                "    SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "    From [EBHLaunch].[dbo].[Agent_Pay_Matrix] With(nolock)\n" +
                "    Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "\t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "          AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "    Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "   )\n" +
                "\n" +
                "\n" +
                "Select 'Primary Vendor Info', pm.MATRIX_LOC_CODE, pm.MATRIX_COMP_CODE, pm.MATRIX_PRIM_VENDOR, '   ', 'Agent Adjustment Record', \n" +
                "aa.AGENT_ADJUST_ID, aa.AGENT_ADJUST_VENDOR_CODE, aa.AGENT_ADJUST_PAY_CODE, aa.AGENT_ADJUST_STATUS, aa.AGENT_ADJUST_FREQ, \n" +
                "aa.AGENT_ADJUST_AMOUNT_TYPE, aa.AGENT_ADJUST_AMOUNT, aa.AGENT_ADJUST_TOP_LIMIT, aa.agent_adjust_total_to_date,  aa.AGENT_ADJUST_LAST_DATE,\n" +
                "aa.AGENT_ADJUST_MAX_TRANS, aa.AGENT_ADJUST_START_DATE, aa.AGENT_ADJUST_END_DATE, aa.AGENT_ADJUST_PAY_VENDOR, aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "aa.AGENT_ADJUST_NOTE, aa.AGENT_ADJUST_CREATED_BY, aa.AGENT_ADJUST_CREATED_DATE, aa.AGENT_ADJUST_UPDATED_BY, aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "aa.AGENT_ADJUST_IS_DELETED, aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, aa.AGENT_ADJUST_APPLY_TO_AGENT, aa.AGENT_ADJUST_ORDER_NO, aa.AGENT_ADJUST_COMP_CODE\n" +
                "From " + tableName + " AA With(nolock)\n" +
                "Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "'\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

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
                    "\t" + res.getString(31));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Select Corp Status = Corp Review for the Same Record, that has the Agent Status = Agent Deduct and Corp Status = Corp Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Select_Corp_Status_Corp_Review_for_the_Same_Record_that_has_the_Agent_Status_Agent_Deduct_and_Corp_Status_Corp_Review(String corpStatus, String agentStatus1, String agent, String proNum) throws InterruptedException {

        driver.findElement(equipmentConsolePage.agentStatus).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        driver.findElement(equipmentConsolePage.agent).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        driver.findElement(equipmentConsolePage.proNo).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        driver.findElement(equipmentConsolePage.corpStatus).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(corpStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Select Agent Deduct on Corp Review Status \"([^\"]*)\"$")
    public void select_Agent_Deduct_on_Corp_Review_Status(String status) {
        driver.findElement(equipmentConsolePage.corpReview).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^The Days, Amount and Notes Column are filled in with the same information that was previously entered\\. Enter different Amount, Days or Effective Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void the_Days_Amount_and_Notes_Column_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_different_Amount_Days_or_Effective_Date(String ofDays, String splits, String effDate1) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfDays\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfDays\"]")).sendKeys(ofDays);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfDays\"]")).click();

        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).sendKeys(effDate1);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfSplits\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfSplits\"]")).sendKeys(splits);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfSplits\"]")).click();
    }

    @Given("^Verify Notes Column has the Customer Number, Chassis No and Container No, already prefilled in the Notes Column$")
    public void verify_Notes_Column_has_the_Customer_Number_Chassis_No_and_Container_No_already_prefilled_in_the_Notes_Column() throws InterruptedException {
        Thread.sleep(8000);
        System.out.println("CORP STATUS, AGENT REVIEW, AGENT DEDUCT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).isDisplayed();
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).getAttribute("value"));
        System.out.println();
    }

    @Given("^Enter Notes \"([^\"]*)\" into the Notes Column, make sure to enter a Comma, Select Ok, Select Go, Select No$")
    public void Enter_Notes_into_the_Notes_Column_make_sure_to_enter_a_Comma_Select_Ok_Select_Go_Select_No(String notes1) {

        driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).sendKeys(notes1);
        driver.findElement(equipmentConsolePage.alertNotes).isDisplayed();
        driver.findElement(equipmentConsolePage.ok).click();
        driver.findElement(equipmentConsolePage.goAD).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.no).click();
    }

    @Given("^Verify previously entered data remained the Same, Select Go, Select Yes, Main Form Appears$")
    public void Verify_previously_entered_data_remained_the_Same_Select_Go_Select_Yes_Main_Form_Appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(("ENTERED DATAS: "));
        System.out.println("Amount: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).getAttribute("value"));
        System.out.println("Effective Date: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfDays\"]")).getAttribute("value"));
        System.out.println("Splits: " + driver.findElement(By.xpath("//*[@id=\"AgentDeductNoOfSplits\"]")).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).getAttribute("value"));
        Thread.sleep(4000);

        driver.findElement(equipmentConsolePage.goAD).click();
        driver.findElement(equipmentConsolePage.reimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsolePage.yes).click();

        driver.findElement(By.xpath("/html/body/div[7]")).isDisplayed();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[7]/div[11]/div/button/span")).click();
        System.out.println("=========================================");
    }


    @Given("^Query Data in Agent_Adjustments SQL Table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Agent_Adjustments_SQL_Table_for_this_transaction(String environment, String tableName, String createdDate, String orderNum) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // SQL#3
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +
                "PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "AS (\n" +
                "    SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "    From [EBHLaunch].[dbo].[Agent_Pay_Matrix] With(nolock)\n" +
                "    Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "\t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "          AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "    Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "   )\n" +
                "\n" +
                "\n" +
                "Select 'Primary Vendor Info', pm.MATRIX_LOC_CODE, pm.MATRIX_COMP_CODE, pm.MATRIX_PRIM_VENDOR, '   ', 'Agent Adjustment Record', \n" +
                "aa.AGENT_ADJUST_ID, aa.AGENT_ADJUST_VENDOR_CODE, aa.AGENT_ADJUST_PAY_CODE, aa.AGENT_ADJUST_STATUS, aa.AGENT_ADJUST_FREQ, \n" +
                "aa.AGENT_ADJUST_AMOUNT_TYPE, aa.AGENT_ADJUST_AMOUNT, aa.AGENT_ADJUST_TOP_LIMIT, aa.agent_adjust_total_to_date,  aa.AGENT_ADJUST_LAST_DATE,\n" +
                "aa.AGENT_ADJUST_MAX_TRANS, aa.AGENT_ADJUST_START_DATE, aa.AGENT_ADJUST_END_DATE, aa.AGENT_ADJUST_PAY_VENDOR, aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "aa.AGENT_ADJUST_NOTE, aa.AGENT_ADJUST_CREATED_BY, aa.AGENT_ADJUST_CREATED_DATE, aa.AGENT_ADJUST_UPDATED_BY, aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "aa.AGENT_ADJUST_IS_DELETED, aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, aa.AGENT_ADJUST_APPLY_TO_AGENT, aa.AGENT_ADJUST_ORDER_NO, aa.AGENT_ADJUST_COMP_CODE\n" +
                "From " + tableName + " AA With(nolock)\n" +
                "Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "'\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> equipmentConsoleRecord = driver.findElements(By.xpath("//*[@id=\"griddata\"]/tbody"));
        List<String> dbAATable = new ArrayList<>();
        //   List<String> dbAATable1 = new ArrayList<>();
        //   List<String> dbAATable2 = new ArrayList<>();

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
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            dbAATable.add(a2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            for (WebElement eC : equipmentConsoleRecord) {
                if (eC.getText().contains(a2)) {
                    for (String dbAAT : dbAATable) {
                        if (dbAAT.contains(a2)) {
                            System.out.println("Agent Code: " + a2);
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

            System.out.println();
            System.out.println("=========================================");
            System.out.println("(No column name)                 = " + a1);
            System.out.println("MATRIX_LOC_CODE                  = " + a2);
            System.out.println("MATRIX_COMP_CODE                 = " + a3);
            System.out.println("MATRIX_PRIM_VENDOR               = " + a4);
            System.out.println("(No column name)                 = " + a5);
            System.out.println("(No column name)                 = " + a6);
            System.out.println("AGENT_ADJUST_ID                  = " + a7);
            System.out.println("AGENT_ADJUST_VENDOR_CODE         = " + a8);
            System.out.println("AGENT_ADJUST_PAY_CODE            = " + a9);
            System.out.println("AGENT_ADJUST_STATUS              = " + a10);
            System.out.println("AGENT_ADJUST_FREQ                = " + a11);
            System.out.println("AGENT_ADJUST_AMOUNT_TYPE         = " + a12);
            System.out.println("AGENT_ADJUST_AMOUNT              = " + a13);
            System.out.println("AGENT_ADJUST_TOP_LIMIT           = " + a14);
            System.out.println("agent_adjust_total_to_date       = " + a15);
            System.out.println("AGENT_ADJUST_LAST_DATE           = " + a16);
            System.out.println("AGENT_ADJUST_MAX_TRANS           = " + a17);
            System.out.println("AGENT_ADJUST_START_DATE          = " + a18);
            System.out.println("AGENT_ADJUST_END_DATE            = " + a19);
            System.out.println("AGENT_ADJUST_PAY_VENDOR          = " + a20);
            System.out.println("AGENT_ADJUST_LAST_AMOUNT         = " + a21);
            System.out.println("AGENT_ADJUST_NOTE                = " + a22);
            System.out.println("AGENT_ADJUST_CREATED_BY          = " + a23);
            System.out.println("AGENT_ADJUST_CREATED_DATE        = " + a24);
            System.out.println("AGENT_ADJUST_UPDATED_BY          = " + a25);
            System.out.println("AGENT_ADJUST_LAST_UPDATED        = " + a26);
            System.out.println("AGENT_ADJUST_IS_DELETED          = " + a27);
            System.out.println("AGENT_ADJUST_PAY_VENDOR_PAY_CODE = " + a28);
            System.out.println("AGENT_ADJUST_APPLY_TO_AGENT      = " + a29);
            System.out.println("AGENT_ADJUST_ORDER_NO            = " + a30);
            System.out.println("AGENT_ADJUST_COMP_CODE           = " + a31);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ 40a @IdentifyINVOICEforAgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable /..................................................../
    // SQL#2 //
    @Given("^Locate a Record to test from Database, select a record that has Corp Status = Corp Review \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void locate_a_Record_to_test_from_Database_select_a_record_that_has_Corp_Status_Corp_Review(String environment, String tableName2, String createdDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue,  l.LOCATION_CODE, l.LOCATION_COMP_CODE,  si.AGENT_STL_AGENT_CODE, si.AGENT_STL_COMPANY_CODE, *\n" +
                "       FROM " + tableName2 + " irr With(nolock)\n" +
                "       Inner Join [Evans].[dbo].[InvoiceRegister]ir With(nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId \n" +
                "       Inner join [EBHLaunch].[dbo].[LOCATIONS] L With(nolock) on l.LOCATION_CODE = irr.agent \n" +
                "       Left Join [EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO] si With(nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE \n" +
                "       Where irr.CorpStatus in ('AgentReview', 'CorpReview') and InvoiceDate >= '" + createdDate + "' and si.AGENT_STL_AGENT_CODE is Null";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder = new ArrayList<>();

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
                    "\t" + res.getString(21));

            StringBuilder sb = new StringBuilder();
            sb.append(res.getString(1)).append(" ").append(res.getString(19)).append(" ").append(res.getString(20));
            stringBuilder.add(sb.toString());
            System.out.println("Invoice No, Agent Status and Corp Status:  " + sb);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ 40b @AgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable /..................................................../

    @Given("^Enter Invoice Number \"([^\"]*)\" in Search Criteria on Equipment Console and Click Refresh$")
    public void enter_Invoice_Number_in_Search_Criteria_on_Equipment_Console_and_Click_Refresh(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsolePage.invoice).sendKeys(invoiceNo);
        driver.findElement(equipmentConsolePage.refresh).click();
        Thread.sleep(5000);
    }

    @Given("^Select a record that has a Corp Status = Corp Review \"([^\"]*)\"$")
    public void select_a_record_that_has_a_Corp_Status_Corp_Review(String corpStatus) throws InterruptedException {

        Thread.sleep(3000);
        driver.findElement(equipmentConsolePage.corpStatus).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(corpStatus)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
        System.out.println("=========================================");
        beforeChange = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]")).getText();
        System.out.println("Before Change: ");
        System.out.println(beforeChange);
    }


    @Given("^Select Agent Deduct$")
    public void select_Agent_Deduct() throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(equipmentConsolePage.corpReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains("AgentDeduct")) {
                webElement.click();
                break;
            }
        }
    }


    @Given("^Verify Notes Column has the Customer No, Chassis No and Container No, Prefilled in the Notes Column$")
    public void verify_Notes_Column_has_the_Customer_No_Chassis_No_and_Container_No_Prefilled_in_the_Notes_Column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("NOTES:");
        driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtCustomerBill\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtChasisNumber\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"txtContainerNumber\"]")).getAttribute("value"));
        driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).isDisplayed();
        System.out.println("Notes: " + driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).getAttribute("value"));
        System.out.println("=========================================");
    }

    @Given("^Enter Amount OR No of Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Amount_OR_No_of_Days_Effective_Date_Todays_Date(String amount, String effDate) throws InterruptedException {
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).sendKeys(effDate);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductEffectiveDate\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"AgentDeductTotal\"]")).click();
    }


    @Given("^Enter some notes into the Notes Column \"([^\"]*)\" and Select Go, Select Yes, Select OK$")
    public void enter_some_notes_into_the_Notes_Column_and_Select_Go_Select_Yes_Select_OK(String notes) {
        driver.findElement(By.xpath("//*[@id=\"agentNotes\"]")).sendKeys(notes);
        driver.findElement(equipmentConsolePage.goAD).click();

        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button[1]")).click();
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button")).click();
    }

    @Given("^Select X to Close Agent Deduction Form$")
    public void select_X_to_Close_Agent_Deduction_Form() {
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/button/span[1]")).click();
    }

    @Given("^Conform Equipment Console Main Form is Displayed and Agent Status and Corp Status remained the Same$")
    public void conform_Equipment_Console_Main_Form_is_Displayed_and_Agent_Status_and_Corp_Status_remained_the_Same() throws InterruptedException {
        Thread.sleep(2000);
        String afterChange = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]")).getText();
        System.out.println("After Change:");
        System.out.println(afterChange);
        assertEquals("Agent Status and Corp Status remained the same!!", beforeChange, afterChange);
        System.out.println("Agent Status and Corp Status remained the same!!");
        System.out.println("=========================================");
    }


    //........................./ (EMGR-132) Test #2 - Bill Customer and Create Order with a Suffix /....................................//
    //........................./ 41a @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICEforBillCustomer /....................................//

    @Given("^Locate a Record to Verify TAX and ADMIN BILLING CODES have been setup for Bill Customer from Database SQL-One \"([^\"]*)\" \"([^\"]*)\"$")
    public void locate_a_Record_to_Verify_TAX_and_ADMIN_BILLING_CODES_have_been_setup_for_Bill_Customer_from_Database_SQL_One(String environment, String tableName1) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("BILLING_CODES TABLE (SQL#1) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select *\n" +
                "       from  " + tableName1 + "\n " +
                "       Where BILLING_CODE in ('TAX', 'Admin')\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbbillingCodesTable = new ArrayList<>();

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
                    "\t" + res.getString(32));

            String a = res.getString(1);
            dbbillingCodesTable.add(a);

            boolean booleanValue = false;
            for (String str2 : dbbillingCodesTable) {
                if (str2.contains(a)) {
                    System.out.println("BILLING_CODE: " + a);
                    System.out.println();
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
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Locate a Record to Identify INVOICE for Bill Customer from Database SQL-Two \"([^\"]*)\" \"([^\"]*)\"$")
    public void locate_a_Record_to_Identify_INVOICE_for_Bill_Customer_from_Database_SQL_Two(String environment, String tableName2) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("InvoiceRegisterRecord (SQL#2) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +

                "       PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +

                "       AS (\n" +
                "       SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "       From [EBHLaunch].[dbo].[Agent_Pay_Matrix]\n" +
                "        Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "       \t\t  AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "        AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "       Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "        )\n" +
                "       , \n" +
                "       IRRCTE ( InvoiceRegisterID, Agent, ProNumber)\n" +

                "       AS (\n" +
                "       SELECT  InvoiceRegisterID, Agent, ProNumber\n" +
                "       From [Evans].[dbo].[InvoiceRegisterRecord]\n" +
                "       Where     CorpStatus IN ('AgentReview', 'CorpReview') and Agent is NOT Null and ProNumber  <> '' and DuplicateMatch = ''\n" +
                "       Group By  InvoiceRegisterID, Agent, ProNumber\n" +
                "       )\n" +
                "       ,\n" +
                "       SICTE (Agent_Stl_Agent_Code, Agent_Stl_Company_Code)\n" +
                "       AS (\n" +
                "       SELECT  Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "       From [EBHLaunch].[dbo].[Agent_Settlement_Info]\n" +
                "       --Where AGENT_STL_AAR = 'AAR Name'\n" +
                "       Group By Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "       )\n" +
                "       Select  Distinct ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue, Matrix_Prim_Vendor, MATRIX_COMP_CODE, IR.Tax, IR.TaxRate\n" +
                "       FROM " + tableName2 + "ir\n" +
                "       inner Join IRRCTE irr on ir.InvoiceRegisterId = irr.InvoiceRegisterId\n" +
                "       inner Join PMCTE apm on irr.Agent = apm.MATRIX_LOC_CODE\n" +
                "       inner join [EBHLaunch].[dbo].[Orders] o on o.Ord_loc = irr.Agent and o.Ord_num = irr.ProNumber\n" +
                "       inner join [EBHLaunch].[dbo].[Locations] L on l.LOCATION_CODE = o.ord_loc\n" +
                "       inner join SICTE si on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE\n" +
                "       Where InvoiceDate >= '01/01/2021' \n" +
                "       Order by IRR.Agent, ir.InvoiceNumber, ir.InvoiceDate, IRR.ProNumber\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

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
                    "\t" + res.getString(9));

            System.out.println("Invoice No, Agent and Pro Number:  " + res.getString(1) + " " + res.getString(3) + " " + res.getString(4)
            );
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    //......................................................./ 41b @BillCustomer /....................................................................//

    @Given("^Select a Record that has a Agent Status and Corp Status \"([^\"]*)\" \"([^\"]*)\" = Corp Review or Agent Review$")
    public void select_a_Record_that_has_a_Agent_Status_and_Corp_Status_Corp_Review_or_Agent_Review(String agentStatus, String corpStatus) throws InterruptedException {

   /*     driver.findElement(equipmentConsolePage.agentStatus).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus)) {
                webElement.click();
                break;
            }
        }  */

   /*     driver.findElement(equipmentConsolePage.corpStatus).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(corpStatus)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
    }


    @Given("^Select Bill Customer on Corp Review Status \"([^\"]*)\"$")
    public void select_Bill_Customer_on_Corp_Review_Status(String status) {
        driver.findElement(equipmentConsolePage.corpReview).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
    }


    @Given("^Enter all the information in Bill Customer Form, Select Go, Select Yes \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_all_the_information_in_Bill_Customer_Form_Select_Go_Select_Yes(String billedToID, String billingCode, String noofDays, String rate, String noofDays2, String rate2, String adminFee, String notes, String billDetails) throws InterruptedException {

   /*     driver.findElement(equipmentConsolePage.billedToID).click();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(equipmentConsolePage.billedToID);
        act.doubleClick(ele).perform();
        driver.findElement(equipmentConsolePage.billedToID).sendKeys(billedToID);
        driver.findElement(equipmentConsolePage.billedToID).click();

        driver.findElement(equipmentConsolePage.billingCode).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"BillCustomerKeyword\"]/option"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(billingCode)) {
                webElement.click();
                break;
            }
        }  */

   /*     driver.findElement(equipmentConsolePage.noofDays).sendKeys(noofDays);
        driver.findElement(equipmentConsolePage.noofDays).click();
        driver.findElement(equipmentConsolePage.rate).sendKeys(rate);
        driver.findElement(equipmentConsolePage.rate).click();
        //   driver.findElement(By.xpath("//*[@id=\"BillCustomerAddRow2\"]")).click();

        //    driver.findElement(equipmentConsolePage.noofDays2).sendKeys(noofDays2);
        //    driver.findElement(equipmentConsolePage.noofDays2).click();
        //     driver.findElement(equipmentConsolePage.rate2).sendKeys(rate2);
        //    driver.findElement(equipmentConsolePage.rate2).click();

        driver.findElement(equipmentConsolePage.useTaxRateCheckBox).click();

        //    driver.findElement(equipmentConsolePage.adminFee).clear();
        //    driver.findElement(equipmentConsolePage.adminFee).sendKeys(adminFee);
        //   driver.findElement(equipmentConsolePage.adminFee).click();
        //   driver.findElement(equipmentConsolePage.useAdminFeeCheckBox).click();

        driver.findElement(equipmentConsolePage.notes).sendKeys(notes);
        driver.findElement(equipmentConsolePage.notes).click();
        driver.findElement(equipmentConsolePage.billDetails).sendKeys(billDetails);
        driver.findElement(equipmentConsolePage.billDetails).click();
        Thread.sleep(2000);
        driver.findElement(equipmentConsolePage.goBC).click();
        driver.findElement(By.xpath("/html/body/div[7]")).isDisplayed();
        //   /html/body/div[8]
        //   driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button[1]/span")).click();

        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button[1]")).click();
        //   /html/body/div[7]/div[3]/div/button[1]
        //  /html/body/div[8]/div[3]/div/button[1]/span
        //   /html/body/div[7]/div[3]/div/button[1]/span

        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button/span")).click();
        Thread.sleep(8000);
    }


    @Given("^Verify Bill is created for ProNo \"([^\"]*)\"$")
    public void verify_Bill_is_created_for_ProNo(String proNo) throws InterruptedException {

        System.out.println("=========================================");
        System.out.println("");
        System.out.println("BILLED CUSTOMER:");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"griddata_wrapper\"]")).getText());
        String invoiceNo = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[3]/a")).getText();
        String agent = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[9]")).getText();
        String proNum = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[10]")).getText();
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[1]")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[8]/a")).getText();
        String statusUpdateDate = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[43]")).getText();
        Thread.sleep(8000);
        System.out.println("");
        System.out.println("Invoice No: " + invoiceNo);
        System.out.println("Agent: " + agent);
        System.out.println("ProNum: " + proNum);
        assertEquals(proNum, proNo);
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        System.out.println("Status Update Date: " + statusUpdateDate);
        Thread.sleep(2000);
        System.out.println("");
        System.out.println("=========================================");
    }

    //............................................/ 41c @RetrieveNewOrderPRo /................................................//

    @Given("^Verify Order Pro is created in Chassis Bill Customer Record, and Retrieve the NEW ORDER PRO from Database SQL-Three \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Order_Pro_is_created_in_Chassis_Bill_Customer_Record_and_Retrieve_the_NEW_ORDER_PRO_from_Database_SQL_Three(String environment, String tableName3, String originalProNumber) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ChassisBillCustomerRecord (SQL#3) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT OrderPro, *\n" +
                "FROM " + tableName3 + " \n" +
                "Where OrderPro Like '" + originalProNumber + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbCBCR = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println();
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
                    "\t" + res.getString(38));

            String a = res.getString(1);
            dbCBCR.add(a);

            boolean booleanValue = false;
            for (String str : dbCBCR) {
                if (str.contains(a)) {
                    System.out.println("OrderPro: " + a);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }

            System.out.println("=========================================");
            System.out.println("OrderPro                    : " + res.getString(1));
            System.out.println("ChassisBillCustomerRecordId : " + res.getString(2));
            System.out.println("InvoiceRegisterRecordId     : " + res.getString(3));
            System.out.println("IsAgentStatusChange         : " + res.getString(4));
            System.out.println("BilledCustomerId            : " + res.getString(5));
            System.out.println("Keyword                     : " + res.getString(6));
            System.out.println("Days                        : " + res.getString(7));
            System.out.println("Rate                        : " + res.getString(8));
            System.out.println("UseTaxRate                  : " + res.getString(9));
            System.out.println("TotalAmount                 : " + res.getString(10));
            System.out.println("Notes                       : " + res.getString(11));
            System.out.println("RecordStatus                : " + res.getString(12));
            System.out.println("CreatedBy                   : " + res.getString(13));
            System.out.println("CreatedDate                 : " + res.getString(14));
            System.out.println("UpdatedBy                   : " + res.getString(15));
            System.out.println("UpdatedDate                 : " + res.getString(16));
            System.out.println("BillDetails                 : " + res.getString(17));
            System.out.println("Day2Value                   : " + res.getString(18));
            System.out.println("Rate2Value                  : " + res.getString(19));
            System.out.println("Day3Value                   : " + res.getString(20));
            System.out.println("Rate3Value                  : " + res.getString(21));
            System.out.println("Day4Value                   : " + res.getString(22));
            System.out.println("Rate4Value                  : " + res.getString(23));
            System.out.println("Day5Value                   : " + res.getString(24));
            System.out.println("Rate5Value                  : " + res.getString(25));
            System.out.println("UseAdminFee                 : " + res.getString(26));
            System.out.println("AdminFee                    : " + res.getString(27));
            System.out.println("OverrideAmount1             : " + res.getString(28));
            System.out.println("OverrideAmount2             : " + res.getString(29));
            System.out.println("OverrideAmount3             : " + res.getString(30));
            System.out.println("OverrideAmount4             : " + res.getString(31));
            System.out.println("OverrideAmount5             : " + res.getString(32));
            System.out.println("Amount1Value                : " + res.getString(33));
            System.out.println("Amount2Value                : " + res.getString(34));
            System.out.println("Amount3Value                : " + res.getString(35));
            System.out.println("Amount4Value                : " + res.getString(36));
            System.out.println("Amount5Value                : " + res.getString(37));
            System.out.println("OrderPro                    : " + res.getString(38));
            System.out.println("=========================================");
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 41d  @VerifyDataInTables /................................................//

    @Given("^Verify Data on Orders Table, Enter ORIGINAL PRO and NEW PRO and Retrieve records from Database SQL-Four \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Verify_Data_on_Orders_Table_enter_ORIGINAL_PRO_and_NEW_PRO_and_Retrieve_records_from_Database_SQL_Four(String environment, String tableName, String originalProNumber, String proNumberWithSuffix) throws Throwable {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDERS TABLE (SQL#4) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @OPro VarChar (10) = '" + originalProNumber + "'   -- Original Pro\n" +
                "       declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Table_Name nvarchar(50)\n" +
                "       declare @Key_Name nvarchar(50)\n" +
                "       declare @Src_Key_ValueName int\n" +
                "       declare @Tgt_Key_ValueName int\n" +
                "       declare @Column_Eval nvarchar(200)\n" +
                "       declare @SQL nvarchar(max)\n" +
                "       set @Table_Name = 'Orders'\n" +
                "       set @Key_Name = 'Ord_ID'\n" +
                "       set @SQL = ''\n" +

                "       Set @Src_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @OPro)\n" +

                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +

                "       -- All Columns from Orders Table\n" +
                "       Select Ord_id, Ord_loc, Ord_Num, Ord_suffix, Ord_pro, 'All Columns From Orders Table', * \n" +
                "       From " + tableName + "  \n" +
                "       Where Ord_ID in ( @Src_Key_ValueName, @Tgt_Key_ValueName)\n" +

                "       -- This SQL Stament will return the Account Information for the New Pro Number.  If any of the Bill To Information on the Orders table is different use this infomration to determine if the information for the New Order Pro is correct.'\n" +
                "       Select Ord_Pro, Ord_id, ACCOUNT_CODE, ACCOUNT_NAME, ACCOUNT_ADDR1, ACCOUNT_ADDR2, ACCOUNT_CITY, ACCOUNT_STATE, ACCOUNT_ZIP, Orders.*\n" +
                "       From " + tableName + " \n" +
                "       Inner join [EBHLaunch].[dbo].[ACCOUNTS] on Accounts.ACCOUNT_Code = Orders.Ord_bill_to_code\n" +
                "       Where Ord_id In (@Tgt_Key_ValueName)\n" +

                "       Select 'Use to Verify ORD_ACCTING_DATE and ORD_ACCTING_WK on Orders Table', AGENT_STL_AGENT_CODE, Cast(AGENT_STL_ACCTING_DATE as DATE), AGENT_STL_ACCTING_WK\n" +
                "       from [EBHLaunch].[dbo].[AGENT_SETTLEMENT_INFO]\n" +
                "       where AGENT_STL_AGENT_CODE = substring(@NPro,1,3)\n";

        ResultSet res = stmt.executeQuery(query);
        System.out.println("Contents of the First result-set: ");
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbOrdersTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println();
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
                    "\t" + res.getString(67) +
                    "\t" + res.getString(68) +
                    "\t" + res.getString(69) +
                    "\t" + res.getString(70) +
                    "\t" + res.getString(71) +
                    "\t" + res.getString(72) +
                    "\t" + res.getString(73));

            String a = res.getString(3);
            dbOrdersTable.add(a);

            boolean booleanValue = false;
            for (String str : dbOrdersTable) {
                if (str.contains(a)) {
                    System.out.println("Ord_Num: " + a);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("Ord_id                      : " + res.getString(1));
            System.out.println("Ord_loc                     : " + res.getString(2));
            System.out.println("Ord_Num                     : " + res.getString(3));
            System.out.println("Ord_suffix                  : " + res.getString(4));
            System.out.println("Ord_pro                     : " + res.getString(5));
            System.out.println("                            : " + res.getString(6));
            System.out.println("Ord_id                      : " + res.getString(7));
            System.out.println("Ord_loc                     : " + res.getString(8));
            System.out.println("Ord_num                     : " + res.getString(9));
            System.out.println("Ord_pro                     : " + res.getString(10));
            System.out.println("Ord_suffix                  : " + res.getString(11));
            System.out.println("Ord_trans_status            : " + res.getString(12));
            System.out.println("Ord_bill_to_code            : " + res.getString(13));
            System.out.println("Ord_bill_to_name            : " + res.getString(14));
            System.out.println("Ord_bill_to_addr1           : " + res.getString(15));
            System.out.println("Ord_bill_to_addr2           : " + res.getString(16));
            System.out.println("Ord_bill_to_city            : " + res.getString(17));
            System.out.println("Ord_bill_to_state           : " + res.getString(18));
            System.out.println("Ord_bill_to_zip             : " + res.getString(19));
            System.out.println("Ord_sh_code                 : " + res.getString(20));
            System.out.println("Ord_sh_name                 : " + res.getString(21));
            System.out.println("Ord_sh_addr1                : " + res.getString(22));
            System.out.println("Ord_sh_addr2                : " + res.getString(23));
            System.out.println("Ord_sh_city                 : " + res.getString(24));
            System.out.println("Ord_sh_state                : " + res.getString(25));
            System.out.println("Ord_sh_zip                  : " + res.getString(26));
            System.out.println("Ord_cn_code                 : " + res.getString(27));
            System.out.println("Ord_cn_name                 : " + res.getString(28));
            System.out.println("Ord_cn_addr1                : " + res.getString(29));
            System.out.println("Ord_cn_addr2                : " + res.getString(30));
            System.out.println("Ord_cn_city                 : " + res.getString(31));
            System.out.println("Ord_cn_state                : " + res.getString(32));
            System.out.println("Ord_cn_zip                  : " + res.getString(33));
            System.out.println("Ord_qty                     : " + res.getString(34));
            System.out.println("Ord_desc                    : " + res.getString(35));
            System.out.println("Ord_weight                  : " + res.getString(36));
            System.out.println("Ord_haz                     : " + res.getString(37));
            System.out.println("Ord_haz_code                : " + res.getString(38));
            System.out.println("Ord_street_turn             : " + res.getString(39));
            System.out.println("Ord_miles                   : " + res.getString(40));
            System.out.println("Ord_DAT_status              : " + res.getString(41));
            System.out.println("Ord_DAT_comment             : " + res.getString(42));
            System.out.println("Ord_DAT_code                : " + res.getString(43));
            System.out.println("Ord_DAT_datetime            : " + res.getString(44));
            System.out.println("Ord_accepted_risk           : " + res.getString(45));
            System.out.println("Ord_creation_login          : " + res.getString(46));
            System.out.println("Ord_creation_date           : " + res.getString(47));
            System.out.println("Ord_update_login            : " + res.getString(48));
            System.out.println("Ord_update_date             : " + res.getString(49));
            System.out.println("Ord_trailer                 : " + res.getString(50));
            System.out.println("Ord_chassis                 : " + res.getString(51));
            System.out.println("Ord_emptytrailer            : " + res.getString(52));
            System.out.println("Ord_seal                    : " + res.getString(53));
            System.out.println("Ord_vessel                  : " + res.getString(54));
            System.out.println("Ord_inbond                  : " + res.getString(55));
            System.out.println("Ord_covered_by              : " + res.getString(56));
            System.out.println("Ord_booking_by              : " + res.getString(57));
            System.out.println("Ord_has_billing_doc         : " + res.getString(58));
            System.out.println("Ord_covered_date            : " + res.getString(59));
            System.out.println("Ord_Is_Deleted              : " + res.getString(60));
            System.out.println("Ord_emptychassis            : " + res.getString(61));
            System.out.println("Ord_Is_PayDriver            : " + res.getString(62));
            System.out.println("Ord_Is_BillCustomer         : " + res.getString(63));
            System.out.println("Ord_Is_BillCustomerPayDriver: " + res.getString(64));
            System.out.println("Ord_Is_OrderClose           : " + res.getString(65));
            System.out.println("Ord_Ship_reg_code           : " + res.getString(66));
            System.out.println("Ord_cons_reg_code           : " + res.getString(67));
            System.out.println("ORD_ACCTING_DATE            : " + res.getString(68));
            System.out.println("ORD_ACCTING_WK              : " + res.getString(69));
            System.out.println("ORD_AGENT_SETTLED           : " + res.getString(70));
            System.out.println("ORD_TRACTOR_SETTLED         : " + res.getString(71));
            System.out.println("ORD_INVOICED                : " + res.getString(72));
            System.out.println("Ord_Is_X6State              : " + res.getString(73));
            System.out.println("=========================================");
        }

        stmt.getMoreResults();
        System.out.println();
        System.out.println("Contents of the Second result-set: ");
        ResultSet rs2 = stmt.getResultSet();
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<String> dbOrdersTable2 = new ArrayList<>();

        while (rs2.next()) {
            int rows = rs2.getRow();
            System.out.println();
            System.out.println("Number of Rows:" + rows);
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
                    "\t" + rs2.getString(19) +
                    "\t" + rs2.getString(20) +
                    "\t" + rs2.getString(21) +
                    "\t" + rs2.getString(22) +
                    "\t" + rs2.getString(23) +
                    "\t" + rs2.getString(24) +
                    "\t" + rs2.getString(25) +
                    "\t" + rs2.getString(26) +
                    "\t" + rs2.getString(27) +
                    "\t" + rs2.getString(28) +
                    "\t" + rs2.getString(29) +
                    "\t" + rs2.getString(30) +
                    "\t" + rs2.getString(31) +
                    "\t" + rs2.getString(32) +
                    "\t" + rs2.getString(33) +
                    "\t" + rs2.getString(34) +
                    "\t" + rs2.getString(35) +
                    "\t" + rs2.getString(36) +
                    "\t" + rs2.getString(37) +
                    "\t" + rs2.getString(38) +
                    "\t" + rs2.getString(39) +
                    "\t" + rs2.getString(40) +
                    "\t" + rs2.getString(41) +
                    "\t" + rs2.getString(42) +
                    "\t" + rs2.getString(43) +
                    "\t" + rs2.getString(44) +
                    "\t" + rs2.getString(45) +
                    "\t" + rs2.getString(46) +
                    "\t" + rs2.getString(47) +
                    "\t" + rs2.getString(48) +
                    "\t" + rs2.getString(49) +
                    "\t" + rs2.getString(50) +
                    "\t" + rs2.getString(51) +
                    "\t" + rs2.getString(52) +
                    "\t" + rs2.getString(53) +
                    "\t" + rs2.getString(54) +
                    "\t" + rs2.getString(55) +
                    "\t" + rs2.getString(56) +
                    "\t" + rs2.getString(57) +
                    "\t" + rs2.getString(58) +
                    "\t" + rs2.getString(59) +
                    "\t" + rs2.getString(60) +
                    "\t" + rs2.getString(61) +
                    "\t" + rs2.getString(62) +
                    "\t" + rs2.getString(63) +
                    "\t" + rs2.getString(64) +
                    "\t" + rs2.getString(65) +
                    "\t" + rs2.getString(66) +
                    "\t" + rs2.getString(67) +
                    "\t" + rs2.getString(68) +
                    "\t" + rs2.getString(69) +
                    "\t" + rs2.getString(70) +
                    "\t" + rs2.getString(71) +
                    "\t" + rs2.getString(72) +
                    "\t" + rs2.getString(73) +
                    "\t" + rs2.getString(74) +
                    "\t" + rs2.getString(75) +
                    "\t" + rs2.getString(76));

            String b = rs2.getString(12);
            dbOrdersTable2.add(b);

            boolean booleanValue = false;
            for (String str2 : dbOrdersTable2) {
                if (str2.contains(b)) {
                    System.out.println("Ord_Num: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("Ord_Pro                     : " + rs2.getString(1));
            System.out.println("Ord_id                      : " + rs2.getString(2));
            System.out.println("ACCOUNT_CODE                : " + rs2.getString(3));
            System.out.println("ACCOUNT_NAME                : " + rs2.getString(4));
            System.out.println("ACCOUNT_ADDR1               : " + rs2.getString(5));
            System.out.println("ACCOUNT_ADDR2               : " + rs2.getString(6));
            System.out.println("ACCOUNT_CITY                : " + rs2.getString(7));
            System.out.println("ACCOUNT_STATE               : " + rs2.getString(8));
            System.out.println("ACCOUNT_ZIP                 : " + rs2.getString(9));
            System.out.println("Ord_id                      : " + rs2.getString(10));
            System.out.println("Ord_loc                     : " + rs2.getString(11));
            System.out.println("Ord_num                     : " + rs2.getString(12));
            System.out.println("Ord_pro                     : " + rs2.getString(13));
            System.out.println("Ord_suffix                  : " + rs2.getString(14));
            System.out.println("Ord_trans_status            : " + rs2.getString(15));
            System.out.println("Ord_bill_to_code            : " + rs2.getString(16));
            System.out.println("Ord_bill_to_name            : " + rs2.getString(17));
            System.out.println("Ord_bill_to_addr1           : " + rs2.getString(18));
            System.out.println("Ord_bill_to_addr2           : " + rs2.getString(19));
            System.out.println("Ord_bill_to_city            : " + rs2.getString(20));
            System.out.println("Ord_bill_to_state           : " + rs2.getString(21));
            System.out.println("Ord_bill_to_zip             : " + rs2.getString(22));
            System.out.println("Ord_sh_code                 : " + rs2.getString(23));
            System.out.println("Ord_sh_name                 : " + rs2.getString(24));
            System.out.println("Ord_sh_addr1                : " + rs2.getString(25));
            System.out.println("Ord_sh_addr2                : " + rs2.getString(26));
            System.out.println("Ord_sh_city                 : " + rs2.getString(27));
            System.out.println("Ord_sh_state                : " + rs2.getString(28));
            System.out.println("Ord_sh_zip                  : " + rs2.getString(29));
            System.out.println("Ord_cn_code                 : " + rs2.getString(30));
            System.out.println("Ord_cn_name                 : " + rs2.getString(31));
            System.out.println("Ord_cn_addr1                : " + rs2.getString(32));
            System.out.println("Ord_cn_addr2                : " + rs2.getString(33));
            System.out.println("Ord_cn_city                 : " + rs2.getString(34));
            System.out.println("Ord_cn_state                : " + rs2.getString(35));
            System.out.println("Ord_cn_zip                  : " + rs2.getString(36));
            System.out.println("Ord_qty                     : " + rs2.getString(37));
            System.out.println("Ord_desc                    : " + rs2.getString(38));
            System.out.println("Ord_weight                  : " + rs2.getString(39));
            System.out.println("Ord_haz                     : " + rs2.getString(40));
            System.out.println("Ord_haz_code                : " + rs2.getString(41));
            System.out.println("Ord_street_turn             : " + rs2.getString(42));
            System.out.println("Ord_miles                   : " + rs2.getString(43));
            System.out.println("Ord_DAT_status              : " + rs2.getString(44));
            System.out.println("Ord_DAT_comment             : " + rs2.getString(45));
            System.out.println("Ord_DAT_code                : " + rs2.getString(46));
            System.out.println("Ord_DAT_datetime            : " + rs2.getString(47));
            System.out.println("Ord_accepted_risk           : " + rs2.getString(48));
            System.out.println("Ord_creation_login          : " + rs2.getString(49));
            System.out.println("Ord_creation_date           : " + rs2.getString(50));
            System.out.println("Ord_update_login            : " + rs2.getString(51));
            System.out.println("Ord_update_date             : " + rs2.getString(52));
            System.out.println("Ord_trailer                 : " + rs2.getString(53));
            System.out.println("Ord_chassis                 : " + rs2.getString(54));
            System.out.println("Ord_emptytrailer            : " + rs2.getString(55));
            System.out.println("Ord_seal                    : " + rs2.getString(56));
            System.out.println("Ord_vessel                  : " + rs2.getString(57));
            System.out.println("Ord_inbond                  : " + rs2.getString(58));
            System.out.println("Ord_covered_by              : " + rs2.getString(59));
            System.out.println("Ord_booking_by              : " + rs2.getString(60));
            System.out.println("Ord_has_billing_doc         : " + rs2.getString(61));
            System.out.println("Ord_covered_date            : " + rs2.getString(62));
            System.out.println("Ord_Is_Deleted              : " + rs2.getString(63));
            System.out.println("Ord_emptychassis            : " + rs2.getString(64));
            System.out.println("Ord_Is_PayDriver            : " + rs2.getString(65));
            System.out.println("Ord_Is_BillCustomer         : " + rs2.getString(66));
            System.out.println("Ord_Is_BillCustomerPayDriver: " + rs2.getString(67));
            System.out.println("Ord_Is_OrderClose           : " + rs2.getString(68));
            System.out.println("Ord_Ship_reg_code           : " + rs2.getString(69));
            System.out.println("Ord_cons_reg_code           : " + rs2.getString(70));
            System.out.println("ORD_ACCTING_DATE            : " + rs2.getString(71));
            System.out.println("ORD_ACCTING_WK              : " + rs2.getString(72));
            System.out.println("ORD_AGENT_SETTLED           : " + rs2.getString(73));
            System.out.println("ORD_TRACTOR_SETTLED         : " + rs2.getString(74));
            System.out.println("ORD_INVOICED                : " + rs2.getString(75));
            System.out.println("Ord_Is_X6State              : " + rs2.getString(76));
        }
        stmt.getMoreResults();
        System.out.println("=========================================");
        System.out.println();
        System.out.println("Contents of the Third result-set: ");
        ResultSet rs3 = stmt.getResultSet();
        ResultSetMetaData rsmd3 = rs3.getMetaData();
        int count3 = rsmd3.getColumnCount();
        List<String> columnList3 = new ArrayList<String>();
        for (int i = 1; i <= count3; i++) {
            columnList3.add(rsmd3.getColumnLabel(i));
        }
        System.out.println(columnList3);

        List<String> dbOrdersTable3 = new ArrayList<>();

        while (rs3.next()) {
            int rows = rs3.getRow();
            System.out.println();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs3.getString(1) +
                    "\t" + rs3.getString(2) +
                    "\t" + rs3.getString(3) +
                    "\t" + rs3.getString(4));

            String c = rs3.getString(2);
            dbOrdersTable3.add(c);

            boolean booleanValue3 = false;
            for (String str3 : dbOrdersTable3) {
                if (str3.contains(c)) {
                    System.out.println("AGENT_STL_AGENT_CODE: " + c);
                    booleanValue3 = true;
                    break;
                }
            }
            if (booleanValue3) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("                       : " + rs3.getString(1));
            System.out.println("AGENT_STL_AGENT_CODE   : " + rs3.getString(2));
            System.out.println("                       : " + rs3.getString(3));
            System.out.println("AGENT_STL_ACCTING_WK   : " + rs3.getString(4));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Billing Table, Enter ORDER ID from NEW PRO NUMBER and Retrieve records from Database SQL-Five \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Billing_Table_enter_ORDER_ID_from_NEW_PRO_NUMBER_and_Retrieve_records_from_Database_SQL_Five(String environment, String tableName, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_BILLING TABLE (SQL#5)..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        //  String useDB = "use " + tableName;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "declare @Tgt_Key_ValueName int\n" +
                "\n" +
                "Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "From " + tableName + "  O\n" +
                "Where o.Ord_pro = @NPro)\n" +
                "\n" +
                "Select BILLING_CODE, Billing_Desc, Billing_Tag, Order_Billing.*\n" +
                "From [EBHLaunch].[dbo].[ORDER_BILLING]\n" +
                "Left Join [EBHLaunch].[dbo].[BILLING_CODES] on BILL_BILLING_CODE = BILLING_CODE\n" +
                "Where Bill_ord_id = @Tgt_Key_ValueName\n" +
                "Order By BILL_LINE_NUMBER\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println();
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
                    "\t" + res.getString(18));

            String b = res.getString(4);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("Bill_ord_id: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println();
            System.out.println("BILLING_CODE        : " + res.getString(1));
            System.out.println("Billing_Desc        : " + res.getString(2));
            System.out.println("Billing_Tag         : " + res.getString(3));
            System.out.println("Bill_ord_id         : " + res.getString(4));
            System.out.println("Bill_tag            : " + res.getString(5));
            System.out.println("Bill_qty            : " + res.getString(6));
            System.out.println("Bill_rate           : " + res.getString(7));
            System.out.println("Bill_value          : " + res.getString(8));
            System.out.println("Bill_creation_login : " + res.getString(9));
            System.out.println("Bill_creation_date  : " + res.getString(10));
            System.out.println("Bill_update_login   : " + res.getString(11));
            System.out.println("Bill_update_date    : " + res.getString(12));
            System.out.println("Bill_description    : " + res.getString(13));
            System.out.println("Bill_charge_misc    : " + res.getString(14));
            System.out.println("Order_bill_id       : " + res.getString(15));
            System.out.println("BILL_LINE_NUMBER    : " + res.getString(16));
            System.out.println("BILL_BILLING_CODE   : " + res.getString(17));
            System.out.println("BILL_DESC           : " + res.getString(18));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Refs Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and Retrieve records from Database SQL-Six \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Refs_Table_enter_ORDER_ID_from_ORIGINAL_PRO_NUMBER_and_NEW_PRO_NUMBER_and_Retrieve_records_from_Database_SQL_Six(String environment, String tableName, String originalProNumber, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_REFS TABLE (SQL#6) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        //  String useDB = "use " + tableName;
        String query = "declare @OPro VarChar (10) = '" + originalProNumber + "'   -- Original Pro\n" +
                "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "declare @Src_Key_ValueName int\n" +
                "declare @Tgt_Key_ValueName int\n" +
                "\n" +
                "Set @Src_Key_ValueName = (Select O.Ord_id\n" +
                "From " + tableName + "  O\n" +
                "Where o.Ord_pro = @OPro)\n" +
                "\n" +
                "Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "From " + tableName + "  O\n" +
                "Where o.Ord_pro = @NPro)\n" +
                "\n" +
                "\n" +
                "Select O.Ord_loc, O.Ord_Num, o.Ord_suffix, O.Ord_id, R.*\n" +
                "From " + tableName + "  O\n" +
                "Inner Join [EBHLaunch].[dbo].[ORDER_REFS] R on O.Ord_id = r.Refs_ord_id\n" +
                "Where Refs_ord_id in ( @Src_Key_ValueName, @Tgt_Key_ValueName)\n" +
                "Order by Refs_tag, Refs_ord_id\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

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
                    "\t" + res.getString(11));

            String b = res.getString(2);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("Ord_Num: " + b);
                    System.out.println();
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }

          /*    System.out.println("=========================================");
            System.out.println("Ord_loc             :" + res.getString(1));
            System.out.println("Ord_Num             :" + res.getString(2));
            System.out.println("Ord_suffix          :" + res.getString(3));
            System.out.println("Ord_id              :" + res.getString(4));
            System.out.println("Refs_ord_id         :" + res.getString(5));
            System.out.println("Refs_tag            :" + res.getString(6));
            System.out.println("Refs_value          :" + res.getString(7));
            System.out.println("REFS_creation_login :" + res.getString(8));
            System.out.println("REFS_creation_date  :" + res.getString(9));
            System.out.println("REFS_update_login   :" + res.getString(10));
            System.out.println("REFS_update_date    :" + res.getString(11));
            System.out.println();
            System.out.println("=========================================");  */
        }
   /*     System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Ops Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER then Retrieve records from Database SQL-Seven \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Ops_Table_enter_ORDER_ID_from_ORIGINAL_PRO_NUMBER_and_NEW_PRO_NUMBER_then_Retrieve_records_from_Database_SQL_Seven(String environment, String tableName, String originalProNumber, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_Ops TABLE (SQL#7) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @OPro VarChar (10) = '" + originalProNumber + "'  -- Original Pro\n" +
                "       declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Src_Key_ValueName int\n" +
                "       declare @Tgt_Key_ValueName int\n" +
                "       \n" +
                "       Set @Src_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @OPro)\n" +
                "       \n" +
                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +
                "       \n" +
                "       \n" +
                "       Select O.Ord_loc, O.Ord_Num, o.Ord_suffix, O.Ord_id, Order_Ops.*\n" +
                "       From " + tableName + " O\n" +
                "       Inner Join [EBHLaunch].[dbo].[ORDER_Ops] on O.Ord_id = ORDER_OPS.Ops_Ord_Id\n" +
                "       Where Ops_Ord_Id in ( @Src_Key_ValueName, @Tgt_Key_ValueName)\n" +
                "       and Ops_tag in ('Actual', 'Appointment', 'Delivery')\n" +
                "       Order by Ops_Tag, Ops_Ord_Id\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

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
                    "\t" + res.getString(9));

            String b = res.getString(2);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("Ord_Num: " + b);
                    System.out.println();
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
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Misc Table, Enter ORDER ID from ORIGINAL PRO NUMBER and NEW PRO NUMBER and then Retrieve records from Database SQL-Eight \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Misc_Table_enter_ORDER_ID_from_ORIGINAL_PRO_NUMBER_and_NEW_PRO_NUMBER_and_then_Retrieve_records_from_Database_SQL_Eight(String environment, String tableName, String originalProNumber, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_MISC TABLE (SQL#8) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @OPro VarChar (10) = '" + originalProNumber + "'  -- Original Pro\n" +
                "       declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Table_Name nvarchar(50)\n" +
                "       declare @Key_Name nvarchar(50)\n" +
                "       declare @Src_Key_ValueName int\n" +
                "       declare @Tgt_Key_ValueName int\n" +
                "       declare @Column_Eval nvarchar(200)\n" +
                "       declare @SQL nvarchar(max)\n" +
                "       set @Table_Name = 'Order_Misc'\n" +
                "       set @Key_Name = 'OrderID'\n" +
                "       set @SQL = ''\n" +

                "       Set @Src_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @OPro)\n" +

                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +

                "       -- SQL to Identify Columns that have specific values in the PRD\n" +
                "       -- SQL to Identify Columns that have specific values in the PRD\n" +
                "       Select OrderID, Button, CreatedDate, Ord_EDI_204, \n" +
                "       OrderMiscId, ProDate, ProLocation, ProNumber, ProSuffix, Request,\n" +
                "       TotalBilled, \n" +
                "       Tractor1Name, Tractor1PayTotal, \n" +
                "       Tractor2Name, Tractor2PayTotal, \n" +
                "       Tractor3Name, Tractor3PayTotal, \n" +
                "       Tractor4Name, Tractor4PayTotal,  \n" +
                "       Tractor5Name, Tractor5PayTotal,\n" +
                "       Tractor6Name, Tractor6PayTotal,\n" +
                "       UpdatedDate, [User]\n" +
                "       From [EBHLaunch].[dbo].[Order_Misc]  \n" +
                "       Where OrderID = @Tgt_Key_ValueName\n" +

                "       -- All Columns from Order_Misc Table\n" +
                "       Select  'All Columns From Order_Misc Table ',  * \n" +
                "       From [EBHLaunch].[dbo].[Order_Misc] \n" +
                "       Where OrderID in ( @Src_Key_ValueName, @Tgt_Key_ValueName)\n";

        ResultSet rs1 = stmt.executeQuery(query);
        System.out.println("Contents of the First result-set: ");
        ResultSetMetaData rsmd = rs1.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (rs1.next()) {
            int rows = rs1.getRow();
            System.out.println();
            System.out.println("Number of Rows:" + rows);
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
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String b = rs1.getString(1);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str2 : dbTable) {
                if (str2.contains(b)) {
                    System.out.println("OrderID: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }

            System.out.println("=========================================");
            System.out.println("OrderID           :" + rs1.getString(1));
            System.out.println("Button            :" + rs1.getString(2));
            System.out.println("CreatedDate       :" + rs1.getString(3));
            System.out.println("Ord_EDI_204       :" + rs1.getString(4));
            System.out.println("OrderMiscId       :" + rs1.getString(5));
            System.out.println("ProDate           :" + rs1.getString(6));
            System.out.println("ProLocation       :" + rs1.getString(7));
            System.out.println("ProNumber         :" + rs1.getString(8));
            System.out.println("ProSuffix         :" + rs1.getString(9));
            System.out.println("Request           :" + rs1.getString(10));
            System.out.println("TotalBilled       :" + rs1.getString(11));
            System.out.println("Tractor1Name      :" + rs1.getString(12));
            System.out.println("Tractor1PayTotal  :" + rs1.getString(13));
            System.out.println("Tractor2Name      :" + rs1.getString(14));
            System.out.println("Tractor2PayTotal  :" + rs1.getString(15));
            System.out.println("Tractor3Name      :" + rs1.getString(16));
            System.out.println("Tractor3PayTotal  :" + rs1.getString(17));
            System.out.println("Tractor4Name      :" + rs1.getString(18));
            System.out.println("Tractor4PayTotal  :" + rs1.getString(19));
            System.out.println("Tractor5Name      :" + rs1.getString(20));
            System.out.println("Tractor5PayTotal  :" + rs1.getString(21));
            System.out.println("Tractor6Name      :" + rs1.getString(22));
            System.out.println("Tractor6PayTotal  :" + rs1.getString(23));
            System.out.println("UpdatedDate       :" + rs1.getString(24));
            System.out.println("User              :" + rs1.getString(25));
        }

        stmt.getMoreResults();
        System.out.println("=========================================");
        System.out.println("Contents of the Second result-set: ");
        ResultSet rs2 = stmt.getResultSet();
        ResultSetMetaData rsmd2 = rs2.getMetaData();
        int count2 = rsmd2.getColumnCount();
        List<String> columnList2 = new ArrayList<String>();
        for (int i = 1; i <= count2; i++) {
            columnList2.add(rsmd2.getColumnLabel(i));
        }
        System.out.println(columnList2);

        List<String> dbTable2 = new ArrayList<>();

        while (rs2.next()) {
            int rows = rs2.getRow();
            System.out.println();
            System.out.println("Number of Rows:" + rows);

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
                    "\t" + rs2.getString(19) +
                    "\t" + rs2.getString(20) +
                    "\t" + rs2.getString(21) +
                    "\t" + rs2.getString(22) +
                    "\t" + rs2.getString(23) +
                    "\t" + rs2.getString(24) +
                    "\t" + rs2.getString(25) +
                    "\t" + rs2.getString(26) +
                    "\t" + rs2.getString(27) +
                    "\t" + rs2.getString(28) +
                    "\t" + rs2.getString(29) +
                    "\t" + rs2.getString(30) +
                    "\t" + rs2.getString(31) +
                    "\t" + rs2.getString(32) +
                    "\t" + rs2.getString(33) +
                    "\t" + rs2.getString(34) +
                    "\t" + rs2.getString(35) +
                    "\t" + rs2.getString(36) +
                    "\t" + rs2.getString(37) +
                    "\t" + rs2.getString(38) +
                    "\t" + rs2.getString(39) +
                    "\t" + rs2.getString(40) +
                    "\t" + rs2.getString(41) +
                    "\t" + rs2.getString(42) +
                    "\t" + rs2.getString(43) +
                    "\t" + rs2.getString(44) +
                    "\t" + rs2.getString(45) +
                    "\t" + rs2.getString(46) +
                    "\t" + rs2.getString(47) +
                    "\t" + rs2.getString(48) +
                    "\t" + rs2.getString(49) +
                    "\t" + rs2.getString(50) +
                    "\t" + rs2.getString(51) +
                    "\t" + rs2.getString(52) +
                    "\t" + rs2.getString(53) +
                    "\t" + rs2.getString(54) +
                    "\t" + rs2.getString(55) +
                    "\t" + rs2.getString(56) +
                    "\t" + rs2.getString(57) +
                    "\t" + rs2.getString(58) +
                    "\t" + rs2.getString(59) +
                    "\t" + rs2.getString(60) +
                    "\t" + rs2.getString(61) +
                    "\t" + rs2.getString(62) +
                    "\t" + rs2.getString(63) +
                    "\t" + rs2.getString(64) +
                    "\t" + rs2.getString(65) +
                    "\t" + rs2.getString(66) +
                    "\t" + rs2.getString(67) +
                    "\t" + rs2.getString(68) +
                    "\t" + rs2.getString(69) +
                    "\t" + rs2.getString(70) +
                    "\t" + rs2.getString(71) +
                    "\t" + rs2.getString(72) +
                    "\t" + rs2.getString(73) +
                    "\t" + rs2.getString(74) +
                    "\t" + rs2.getString(75) +
                    "\t" + rs2.getString(76) +
                    "\t" + rs2.getString(77) +
                    "\t" + rs2.getString(78) +
                    "\t" + rs2.getString(79) +
                    "\t" + rs2.getString(80) +
                    "\t" + rs2.getString(81) +
                    "\t" + rs2.getString(82) +
                    "\t" + rs2.getString(83) +
                    "\t" + rs2.getString(84) +
                    "\t" + rs2.getString(85) +
                    "\t" + rs2.getString(86) +
                    "\t" + rs2.getString(87) +
                    "\t" + rs2.getString(88) +
                    "\t" + rs2.getString(89) +
                    "\t" + rs2.getString(90));

            String a = rs2.getString(2);
            dbTable2.add(a);

            boolean booleanValue2 = false;
            for (String str2 : dbTable2) {
                if (str2.contains(a)) {
                    System.out.println("OrderId: " + a);
                    booleanValue2 = true;
                    break;
                }
            }
            if (booleanValue2) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("                        : " + rs2.getString(1));
            System.out.println("OrderMiscId             : " + rs2.getString(2));
            System.out.println("OrderId                 : " + rs2.getString(3));
            System.out.println("User                    : " + rs2.getString(4));
            System.out.println("Request                 : " + rs2.getString(5));
            System.out.println("StatusCan               : " + rs2.getString(6));
            System.out.println("StatusAccount           : " + rs2.getString(7));
            System.out.println("StatusDataStatus        : " + rs2.getString(8));
            System.out.println("StatusInit              : " + rs2.getString(9));
            System.out.println("StatusDate              : " + rs2.getString(10));
            System.out.println("StatusTime              : " + rs2.getString(11));
            System.out.println("StatusTrac              : " + rs2.getString(12));
            System.out.println("Zone                    : " + rs2.getString(13));
            System.out.println("FromApptSentCode        : " + rs2.getString(14));
            System.out.println("ToApptSentCode          : " + rs2.getString(15));
            System.out.println("GridDate                : " + rs2.getString(16));
            System.out.println("Button                  : " + rs2.getString(17));
            System.out.println("Tractor1Name            : " + rs2.getString(18));
            System.out.println("Tractor2Name            : " + rs2.getString(19));
            System.out.println("Tractor3Name            : " + rs2.getString(20));
            System.out.println("Tractor4Name            : " + rs2.getString(21));
            System.out.println("Tractor5Name            : " + rs2.getString(22));
            System.out.println("Tractor6Name            : " + rs2.getString(23));
            System.out.println("DATEQEnhance            : " + rs2.getString(24));
            System.out.println("DATEQLength             : " + rs2.getString(25));
            System.out.println("ProLocation             : " + rs2.getString(26));
            System.out.println("ProNumber               : " + rs2.getString(27));
            System.out.println("ProSuffix               : " + rs2.getString(28));
            System.out.println("ProDate                 : " + rs2.getString(29));
            System.out.println("ProInit                 : " + rs2.getString(30));
            System.out.println("DriverSettlementDate    : " + rs2.getString(31));
            System.out.println("DriverSettlementInit    : " + rs2.getString(32));
            System.out.println("AsWeight                : " + rs2.getString(33));
            System.out.println("FreightRate             : " + rs2.getString(34));
            System.out.println("IsLclBill               : " + rs2.getString(35));
            System.out.println("NatLocation             : " + rs2.getString(36));
            System.out.println("NatNumber               : " + rs2.getString(37));
            System.out.println("NatStops                : " + rs2.getString(38));
            System.out.println("AsgInit                 : " + rs2.getString(39));
            System.out.println("AsgDate                 : " + rs2.getString(40));
            System.out.println("NatDeliveryDate         : " + rs2.getString(41));
            System.out.println("BillToMessage           : " + rs2.getString(42));
            System.out.println("RefType                 : " + rs2.getString(43));
            System.out.println("ReturnToAcctId          : " + rs2.getString(44));
            System.out.println("ReturnToAcctIdFiller    : " + rs2.getString(45));
            System.out.println("ReturnToType            : " + rs2.getString(46));
            System.out.println("RefInit                 : " + rs2.getString(47));
            System.out.println("RefSize                 : " + rs2.getString(48));
            System.out.println("LoadSuffix              : " + rs2.getString(49));
            System.out.println("ImageBillOfLading       : " + rs2.getString(50));
            System.out.println("ImageDR                 : " + rs2.getString(51));
            System.out.println("ImagePurchaseOrder      : " + rs2.getString(52));
            System.out.println("DATUID                  : " + rs2.getString(53));
            System.out.println("CreditFlag              : " + rs2.getString(54));
            System.out.println("CreatedDate             : " + rs2.getString(55));
            System.out.println("UpdatedDate             : " + rs2.getString(56));
            System.out.println("StopCount               : " + rs2.getString(57));
            System.out.println("ContactInfo             : " + rs2.getString(58));
            System.out.println("DriverPhone             : " + rs2.getString(59));
            System.out.println("Available               : " + rs2.getString(60));
            System.out.println("TrackDate               : " + rs2.getString(61));
            System.out.println("LoadType                : " + rs2.getString(62));
            System.out.println("IsDesignateBrokerLoad   : " + rs2.getString(63));
            System.out.println("Ord_Sh_Country          : " + rs2.getString(64));
            System.out.println("Ord_Con_Country         : " + rs2.getString(65));
            System.out.println("AgentOfficeCode         : " + rs2.getString(66));
            System.out.println("IsPartialDesignator     : " + rs2.getString(67));
            System.out.println("ShipperQuickInfo        : " + rs2.getString(68));
            System.out.println("ConsigneeQuickInfo      : " + rs2.getString(69));
            System.out.println("TotalBilled             : " + rs2.getString(70));
            System.out.println("Tractor1PayTotal        : " + rs2.getString(71));
            System.out.println("Tractor2PayTotal        : " + rs2.getString(72));
            System.out.println("Tractor3PayTotal        : " + rs2.getString(73));
            System.out.println("Tractor4PayTotal        : " + rs2.getString(74));
            System.out.println("Tractor5PayTotal        : " + rs2.getString(75));
            System.out.println("Tractor6PayTotal        : " + rs2.getString(76));
            System.out.println("ContainerReturnLocation : " + rs2.getString(77));
            System.out.println("ChassisReturnLocation   : " + rs2.getString(78));
            System.out.println("VesselETA               : " + rs2.getString(79));
            System.out.println("ShipperContact          : " + rs2.getString(80));
            System.out.println("ConsigneeContact        : " + rs2.getString(81));
            System.out.println("ShipperEmail            : " + rs2.getString(82));
            System.out.println("ConsigneeEmail          : " + rs2.getString(83));
            System.out.println("ShipperWebsite          : " + rs2.getString(84));
            System.out.println("ConsigneeWebsite        : " + rs2.getString(85));
            System.out.println("Ord_sh_phone            : " + rs2.getString(86));
            System.out.println("Ord_cn_phone            : " + rs2.getString(87));
            System.out.println("Ord_EDI_204             : " + rs2.getString(88));
            System.out.println("Ord_DRP_TRAC            : " + rs2.getString(89));
            System.out.println("LoadTypeCode            : " + rs2.getString(90));
            System.out.println("=========================================");
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Action_History Table, Enter ORDER ID from NEW PRO NUMBER, and Retrieve Records from Database SQL-Nine \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Action_History_Table_enter_ORDER_ID_from_NEW_PRO_NUMBER_and_Retrieve_Records_from_Database_SQL_Nine(String environment, String tableName, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_ACTION_HISTORY TABLE (SQL#9) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Tgt_Key_ValueName int\n" +
                "       \n" +
                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +
                "       \n" +
                "       Select *\n" +
                "       From [EBHLaunch].[dbo].[ORDER_Action_History]\n" +
                "       Where Action_Ord_Id = @Tgt_Key_ValueName\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5));

            String a = res.getString(1);
            dbTable.add(a);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(a)) {
                    System.out.println("Action_Ord_Id: " + a);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }

            System.out.println("=========================================");
            System.out.println("Action_Ord_Id    : " + res.getString(1));
            System.out.println("Action_Name      : " + res.getString(2));
            System.out.println("Action_DateTime  : " + res.getString(3));
            System.out.println("Action_Login     : " + res.getString(4));
            System.out.println("AcceptedRisk     : " + res.getString(5));
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Verify Data on Order_Notes Table, Enter ORDER ID from NEW PRO NUMBER and then Retrieve records from Database SQL-Ten \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Notes_Table_enter_ORDER_ID_from_NEW_PRO_NUMBER_and_then_Retrieve_records_from_Database_SQL_Ten(String environment, String tableName, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_NOTES TABLE (SQL#10) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Tgt_Key_ValueName int\n" +

                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +

                "       Select *\n" +
                "       From [EBHLaunch].[dbo].[ORDER_Notes]\n" +
                "       Where Notes_Ord_Id = @Tgt_Key_ValueName\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7));

            String b = res.getString(1);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("Notes_ord_id: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("Notes_ord_id         : " + res.getString(1));
            System.out.println("Notes_tag            : " + res.getString(2));
            System.out.println("Notes_value          : " + res.getString(3));
            System.out.println("Notes_creation_login : " + res.getString(4));
            System.out.println("Notes_creation_date  : " + res.getString(5));
            System.out.println("Notes_update_login   : " + res.getString(6));
            System.out.println("Notes_update_date    : " + res.getString(7));
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Verify Data on Order_Billing_Com Table, Enter ORDER ID from NEW PRO NUMBER then Retrieve records from Database SQL-Eleven \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Data_on_Order_Billing_Com_Table_enter_ORDER_ID_from_NEW_PRO_NUMBER_then_Retrieve_records_from_Database_SQL_Eleven(String environment, String tableName, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("ORDER_BILLING_COM TABLE (SQL#11) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       declare @Tgt_Key_ValueName int\n" +

                "       Set @Tgt_Key_ValueName = (Select O.Ord_id\n" +
                "       From " + tableName + "  O\n" +
                "       Where o.Ord_pro = @NPro)\n" +

                "       Select *\n" +
                "       From [EBHLaunch].[dbo].[ORDER_BILLLING_COM]\n" +
                "       Where Bill_Com_Ord_id = @Tgt_Key_ValueName\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3));

            String b = res.getString(1);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("Bill_Com_ord_id: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("Bill_Com_ord_id   : " + res.getString(1));
            System.out.println("Bill_Com_comment  : " + res.getString(2));
            System.out.println("Bill_com_print    : " + res.getString(3));
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Verify Record was added to Agent_Stl_Trans Table, Enter NEW PRO NUMBER and Retrieve records from Database SQL-Twelve \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Record_was_added_to_Agent_Stl_Trans_Table_enter_NEW_PRO_NUMBER_and_Retrieve_records_from_Database_SQL_Twelve(String environment, String tableName1, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("AGENT_STL_TRANS TABLE (SQL#12) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName1;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       SELECT  *\n" +
                "       FROM " + tableName1 + " \n" +
                "       Where AGENT_TRANS_ORDER_NUM = @Npro\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println();
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
                    "\t" + res.getString(36));

            String b = res.getString(10);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("AGENT_TRANS_ORDER_NUM: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("AGENT_TRANS_ID                          : " + res.getString(1));
            System.out.println("AGENT_TRANS_TYPE                        : " + res.getString(2));
            System.out.println("AGENT_TRANS_ACCTING_DATE                : " + res.getString(3));
            System.out.println("AGENT_TRANS_ACCTING_WK                  : " + res.getString(4));
            System.out.println("AGENT_TRANS_FLAG_ID                     : " + res.getString(5));
            System.out.println("AGENT_TRANS_FLAG_OVERRIDE               : " + res.getString(6));
            System.out.println("AGENT_TRANS_FLAG_OVERRIDE_LOGIN         : " + res.getString(7));
            System.out.println("AGENT_TRANS_LOC_OR_TRAC                 : " + res.getString(8));
            System.out.println("AGENT_TRANS_ORDER_ID                    : " + res.getString(9));
            System.out.println("AGENT_TRANS_ORDER_NUM                   : " + res.getString(10));
            System.out.println("AGENT_TRANS_VENDOR_CODE                 : " + res.getString(11));
            System.out.println("AGENT_TRANS_PAYEE_LOC_OR_TRAC           : " + res.getString(12));
            System.out.println("AGENT_TRANS_PAY_TYPE                    : " + res.getString(13));
            System.out.println("AGENT_TRANS_REV                         : " + res.getString(14));
            System.out.println("AGENT_TRANS_PAY                         : " + res.getString(15));
            System.out.println("AGENT_TRANS_COM                         : " + res.getString(16));
            System.out.println("AGENT_TRANS_ADJUST_ID                   : " + res.getString(17));
            System.out.println("AGENT_TRANS_BILL_DATE                   : " + res.getString(18));
            System.out.println("AGENT_TRANS_STL_DT                      : " + res.getString(19));
            System.out.println("AGENT_TRANS_STL_BY                      : " + res.getString(20));
            System.out.println("AGENT_TRANS_ACCTING_PUSH                : " + res.getString(21));
            System.out.println("AGENT_TRANS_ACCTING_PUSH_DT             : " + res.getString(22));
            System.out.println("AGENT_TRANS_NOT_PROC                    : " + res.getString(23));
            System.out.println("AGENT_TRANS_PAYEE_VENDOR                : " + res.getString(24));
            System.out.println("AGENT_TRANS_PAY_VENDOR_PAY_CODE         : " + res.getString(25));
            System.out.println("AGENT_TRANS_ACTION                      : " + res.getString(26));
            System.out.println("AGENT_TRANS_CREATED_BY                  : " + res.getString(27));
            System.out.println("AGENT_TRANS_CREATED_DT                  : " + res.getString(28));
            System.out.println("AGENT_TRANS_INITIAL_VENDOR              : " + res.getString(29));
            System.out.println("AGENT_TRANS_INITIAL_VENDOR_UPDATED_BY   : " + res.getString(30));
            System.out.println("AGENT_TRANS_INITIAL_VENDOR_UPDATED_DT   : " + res.getString(31));
            System.out.println("AGENT_TRANS_NOTE                        : " + res.getString(32));
            System.out.println("AGENT_TRANS_AP_SESSION_ID               : " + res.getString(33));
            System.out.println("AGENT_TRANS_ORIGINAL_COM                : " + res.getString(34));
            System.out.println("AGENT_TRANS_COM_ADJUSTMENT              : " + res.getString(35));
            System.out.println("AGENT_TRANS_VERIFIED                    : " + res.getString(36));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }
*/
/*
    @Given("^Verify Record was added to the Agent_Settlements Table and Verify Calculations are correct, Enter NEW PRO NUMBER, then Retrieve records from Database SQL-Thirteen \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void verify_Record_was_added_to_the_Agent_Settlements_Table_and_Verify_Calculations_are_correct_Enter_NEW_PRO_NUMBER_then_Retrieve_records_from_Database_SQL_Thirteen(String environment, String tableName2, String proNumberWithSuffix) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println("AGENT_SETTLEMENTS TABLE (SQL#13) ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String useDB = "use " + tableName2;
        String query = "declare @NPro VarChar (10) = '" + proNumberWithSuffix + "' -- New Pro\n" +
                "       SELECT  *\n" +
                "       FROM " + tableName2 + " \n" +
                "       Where STL_PRONUM = @NPro\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbTable = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println();
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
                    "\t" + res.getString(19));

            String b = res.getString(5);
            dbTable.add(b);

            boolean booleanValue = false;
            for (String str : dbTable) {
                if (str.contains(b)) {
                    System.out.println("STL_PRONUM: " + b);
                    booleanValue = true;
                    break;
                }
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
            System.out.println("=========================================");
            System.out.println("ID               : " + res.getString(1));
            System.out.println("STL_ORDER_ID     : " + res.getString(2));
            System.out.println("STL_VENDOR_CODE  : " + res.getString(3));
            System.out.println("STL_VENDOR_TYPE  : " + res.getString(4));
            System.out.println("STL_PRONUM       : " + res.getString(5));
            System.out.println("STL_BILLTO       : " + res.getString(6));
            System.out.println("STL_SHIP         : " + res.getString(7));
            System.out.println("STL_CONS         : " + res.getString(8));
            System.out.println("STL_PAYDED_CODE  : " + res.getString(9));
            System.out.println("STL_TYPE         : " + res.getString(10));
            System.out.println("STL_DESC         : " + res.getString(11));
            System.out.println("STL_GROSS        : " + res.getString(12));
            System.out.println("STL_REV          : " + res.getString(13));
            System.out.println("STL_PAY          : " + res.getString(14));
            System.out.println("STL_COM          : " + res.getString(15));
            System.out.println("STL_MATRIX_ID    : " + res.getString(16));
            System.out.println("STL_ORDER_TYPE   : " + res.getString(17));
            System.out.println("STL_TRANS_DATE   : " + res.getString(18));
            System.out.println("STL_PROCESSED    : " + res.getString(19));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

*/

