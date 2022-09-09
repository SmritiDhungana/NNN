package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentSettlementAdjustmentsPage extends BasePage {

    public final By VendorIDBox = By.xpath("//*[@id=\"VendorPartial_I\"]");
    public final By View = By.xpath("//*[@id=\"linkView\"]");
    public final By VendorCode = By.xpath("//*[@id=\"lblVendorcode\"]");
    public final By VendorCodeDescription = By.xpath("//*[@id=\"lblVendorCodedescription\"]");
    public final By CrossIcon = By.xpath("//*[@id=\"crossIconPopup\"]");
    public final By SearchByAgentCodeBox = By.xpath("//*[@id=\"AgentCodePartial_I\"]");
    public final By RecurringOnly = By.xpath("//*[@id=\"chkRecurring\"]");
 //   public final By EFS = By.xpath("//*[@id=\"chkEFS\"]");
    public final By EFS = By.xpath("//*[@id=\"chkEFS\"]");
    public final By IncludeComplete = By.xpath("//*[@id=\"chkIncludeComplete\"]");
    public final By TotalRecordsReturned = By.xpath("//*[@id=\"lblRowCount\"]");
    public final By DataTable = By.xpath("//*[@id=\"dataTable\"]");
    public final By Edit = By.linkText("EDIT");

    //*[@id="linkEdit_3614"]

    public final By Save = By.linkText("Save");

    public final By New = By.linkText("New");
    public final By Report = By.linkText("Report");
    public final By PopUpReport = By.xpath("//*[@id=\"Divpopup\"]");
    public final By AllAdjustments = By.linkText("All Adjustments");
    public final By Yes = By.linkText("Yes");
    public final By SearchResults = By.linkText("Search Results");
    public final By HeaderAdvanceSearch = By.xpath("//*[@id=\"mainForm\"]/div/div/div/div[1]/div[1]/h4");
    public final By AdvancedSearch = By.linkText("Advanced Search");

    public final By Search = By.linkText("Search");

    public AgentSettlementAdjustmentsPage(WebDriver driver) {
        super(driver);
    }
}
