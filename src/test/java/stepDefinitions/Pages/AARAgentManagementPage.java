package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AARAgentManagementPage extends BasePage {

    public final By AgentCodeSearchIcon = By.cssSelector("#img3");
    public final By TableHeader = By.xpath("/html/body/section/div/div[1]/div[1]/h4");
    public final By TableHead = By.xpath("/html/body/section/div/div/div[2]/div[1]/div[1]/h4");
    public final By AgentCode = By.xpath("//*[@id=\"lblAgentCode\"]");
    public final By VendorCode = By.xpath("//*[@id=\"lblVenderCode\"]");
    public final By DataTable = By.xpath("//*[@id=\"dataTable_wrapper\"]");
    public final By edit = By.linkText("linkEdit");
    public final By Cancel = By.linkText("Cancel");
    public final By Save = By.linkText("Save");
    public final By close = By.linkText("Close");

    public final By PopUpConfirmation = By.xpath("//*[@id=\"Divpopup\"]");
    public final By No = By.xpath("//*[@id=\"btnNo\"]");
    public final By AARAgentManagement = By.linkText("AAR Agent Management");
    public final By Yes = By.xpath("//*[@id=\"btnYesAction\"]");
    public final By Ok = By.xpath("//*[@id=\"btnOK\"]/img");

    public AARAgentManagementPage(WebDriver driver) {
        super(driver);
    }
}
