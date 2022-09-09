package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EquipmentConsolePage extends BasePage {
    public final By alert = By.xpath("/html/body/div[3]");
    public final By alertYes = By.xpath("/html/body/div[3]/div[3]/div/button[1]");
    public final By alertNo = By.xpath("/html/body/div[3]/div[3]/div/button[2]");

    public final By invoice = By.xpath("//*[@id=\"txtInvoiceFilter\"]");
    public final By refresh = By.xpath("//*[@id=\"SearchBar\"]/table/tbody/tr[1]/td[3]/a[1]/img");
    public final By clearFilters = By.xpath("//*[@id=\"SearchBar\"]/table/tbody/tr[1]/td[4]/a/img");

    //*[@id="SearchBar"]/table/tbody/tr[1]/td[4]/a/img
    public final By agentStatus = By.xpath("//*[@id=\"griddata_wrapper\"]/div[3]/div[1]/div/table/thead/tr/th[1]");
    public final By agentReview = By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a");

    public final By agent = By.xpath("//*[@id=\"griddata_wrapper\"]/div[3]/div[1]/div/table/thead/tr/th[9]");
    public final By proNo = By.xpath("//*[@id=\"griddata_wrapper\"]/div[3]/div[1]/div/table/thead/tr/th[10]");


    public final By amount = By.xpath("//*[@id=\"Text35\"]");
  //  public final By effectivedate = By.xpath("");
    public final By alertNotes = By.xpath("/html/body/div[7]");

    public final By ok = By.xpath("//button[@type='button' and span='Ok']");
    public final By go = By.xpath("//*[@id=\"AgentReimbursementbutton\"]");
    public final By reimbursementsAlert = By.xpath("/html/body/div[8]");
    public final By no = By.xpath("/html/body/div[8]/div[3]/div/button[2]/span");
    public final By yes = By.xpath("/html/body/div[8]/div[3]/div/button[1]/span");

    public final By corpStatus = By.xpath("//*[@id=\"griddata_wrapper\"]/div[3]/div[1]/div/table/thead/tr/th[3]");
    //*[@id="griddata_wrapper"]/div[3]/div[1]/div/table/thead/tr/th[3]

    public final By corpReview = By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[3]/a");
    //*[@id="griddata"]/tbody/tr[1]/td[8]/a

    public final By crossClose = By.xpath("/html/body/div[7]/div[1]/div/button/span[1]");


    public final By goAD = By.xpath("//*[@id=\"AgentDeductbutton\"]");
 //   public final By reimbursementsAlert = By.xpath("/html/body/div[8]");



    public final By billedToID = By.xpath("//*[@id=\"BillCustomer\"]/table[1]/tbody/tr[1]/td[2]/input");
    public final By lookUp = By.xpath("//*[@id=\"lnkBillToIdLookup\"]/img");

    public final By billingCode = By.xpath("//*[@id=\"BillCustomerKeyword\"]");

    public final By noofDays = By.xpath("//*[@id=\"BillCustomerDays1\"]");
    public final By rate = By.xpath("//*[@id=\"BillCustomerRate1\"]");
    public final By noofDays2 = By.xpath("//*[@id=\"BillCustomerDays2\"]");
    public final By rate2 = By.xpath("//*[@id=\"BillCustomerRate2\"]");

    public final By useTaxRateCheckBox = By.xpath("//*[@id=\"BillCustomerUseTaxRate\"]");
    public final By adminFee = By.xpath("//*[@id=\"BillCustomerAdminFee\"]");
    public final By useAdminFeeCheckBox = By.xpath("//*[@id=\"BillCustomerUseAdminFee\"]");

    public final By notes = By.xpath("//*[@id=\"BillCustomerNotes\"]");
    public final By billDetails = By.xpath("//*[@id=\"BillCustomerDetails\"]");

    public final By goBC = By.xpath("//*[@id=\"doBillCustomerOperation\"]");


    public final By agentAmount = By.xpath("//*[@id=\"Text57\"]");
    public final By driverAmount = By.xpath("//*[@id=\"Text59\"]");
    public final By corpAmount = By.xpath("//*[@id=\"Text61\"]");





    public EquipmentConsolePage(WebDriver driver) {
        super(driver);
    }

    }