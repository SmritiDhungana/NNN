package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentSettlementFlagMaintenancePage extends BasePage{

    public final By TotalRecordsReturned = By.xpath("//*[@id=\"lblRowCount\"]");

    public AgentSettlementFlagMaintenancePage(WebDriver driver) {
        super(driver);
    }
}
