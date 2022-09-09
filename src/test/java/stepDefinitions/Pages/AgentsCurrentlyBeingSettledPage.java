package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentsCurrentlyBeingSettledPage extends BasePage {

    public final By FilterIconOnSettlingVendorCode = By.xpath("//*[@id=\"img1\"]");
    public final By FilterIconOnSettlingAgentCode = By.xpath("//*[@id=\"img2\"]");
    public final By TotalRecordsReturned = By.xpath("//*[@id=\"countTableRecord\"]");
    public final By TableResult = By.xpath("//*[@id=\"tableResult\"]");

    public AgentsCurrentlyBeingSettledPage(WebDriver driver) {
        super(driver);
    }
}
