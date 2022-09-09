package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FuelPurchaseMaintenancePage extends BasePage{

  //  public final By Search = By.id("/html/body/section/div[1]/div[4]/div[2]/div[3]/div[2]/a[2]");
    public final By Search = By.xpath("/html/body/section/div[1]/div[4]/div[3]/div[2]/div[2]/a[2]");
    //*[@id="btnSearch"]
   // public final By Cancel = By.id("btnSearch");
    public final By Cancel = By.xpath("/html/body/section/div[1]/div[4]/div[3]/div[2]/div[2]/a[1]");
    public final By TractorID = By.id("txtSearchTractorid");
    public final By EarliestDate = By.xpath("//*[@id=\"txtStartDate\"]");
    public final By LatestDate = By.xpath("//*[@id=\"txtEndDate\"]");
    public final By State = By.xpath("//*[@id=\"FuelPurStatePartial_I\"]");
    public final By Company = By.xpath("//*[@id=\"SearchCompanyCode_I\"]");
    public final By IFTA = By.xpath("//*[@id=\"IFTAPartial_I\"]");

    public final By Header = By.xpath("//*[@id=\"mainDiv\"]/div[1]/div[1]/h4/strong/span");
    public final By TotalRecordsReturned = By.xpath("//*[@id=\"spnTotalCount\"]");

    public final By New = By.xpath("//*[@id=\"btnNewSettlement12\"]");
    public final By PopupHeader = By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4");
    public final By CompanyN = By.id("FormCompanyCodePartial_I");
    public final By TractorIDN = By.id("formTractorId");
    public final By DateN = By.id("formDate");
    public final By LocationN = By.id("formLocation");
    public final By StateN = By.id("FormFuelPurStatePartial_I");

  //*[@id="FormFuelPurStatePartial_I"]
    public final By GallonsN = By.id("formGallons");
    public final By AmountN = By.id("formAmount");
    public final By CancelN = By.id("btnCancel");
    public final By SaveN = By.id("btnSaveAgent");

    public final By Edit = By.id("btnAddTractorVendorRelation");

    public final By Report = By.xpath("//*[@id=\"btnReport\"]");
    public final By AllRecords = By.xpath("//*[@id=\"btnYesAction\"]");
    public final By SearchResults = By.xpath("//*[@id=\"btnNo\"]");

    public FuelPurchaseMaintenancePage(WebDriver driver) {
        super(driver);
    }
}
