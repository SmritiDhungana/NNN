package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentSettlementInquiryPage extends BasePage {

    public final By AgentSearchBox = By.xpath("//*[@id=\"singleSelectComboBoxAgent_I\"]");
    public final By SearchButton = By.xpath("//*[@id=\"searchbtn\"]");
    public final By Warning = By.xpath("//*[@id=\"Divpopup\"]");
    public final By WarningYes = By.xpath("//a[@id='btnYesAction']");

    public final By CountTableRecord = By.xpath("//*[@id=\"countTableRecord\"]");
    public final By TableRecord = By.xpath("//*[@id=\"dataTable_wrapper\"]/div[2]/div");


    public final By PayCodesBox = By.xpath("//*[@id=\"singleSelectComboBoxPayCode_I\"]");
    public final By VendorSearchBox = By.xpath("//*[@id=\"singleSelectComboBoxVendor_I\"]");
    public final By Close = By.linkText("Close");

    public final By ComTransType = By.xpath("//*[@id=\"chkCom\"]");
    public final By AdjTransType = By.xpath("//*[@id=\"chkAdj\"]");
    public final By AllTransType = By.xpath("//*[@id=\"chkAll\"]");

    public AgentSettlementInquiryPage(WebDriver driver) {
        super(driver);
    }
}
