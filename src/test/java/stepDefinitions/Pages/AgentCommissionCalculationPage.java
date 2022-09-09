package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentCommissionCalculationPage extends BasePage {


    public final By TableHead = By.xpath("/html/body/section/section/div[1]/div/div/div[1]/div[1]/h3/b/i");
    public final By enterProNumber  = By.id("txtProNumber");
    public final  By CalculateCommission = By.id("btnCalculateCommission");
    public final  By VendorCode = By.xpath("//*[@id=\"tblCommissionSummary\"]/tbody/tr[1]/td");
    public final  By TotalCommission = By.xpath("//*[@id=\"tblCommissionSummary\"]/tbody/tr[2]/td");
    public final  By DisplayMatrix = By.id("redirectToAgentCommission");
    public final By WarningIsDisplayed = By.xpath("//*[@id=\"passwordPopup\"]");
    public final By EnterPasswordForAgentCommissionMaintenance = By.xpath("//*[@id=\"txtPassword\"]");
    public final By OkBtn = By.xpath("//*[@id=\"loadingClassMain\"]");

    public AgentCommissionCalculationPage(WebDriver driver) {
        super(driver);
    }
}
