package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StatusSelectionPage extends BasePage {

    public final By statusForCommitted = By.id("statusForCommitted");
    public final By statusForCustomsHold = By.id("statusForCustomsHold");
    public final By statusForCustomsCleared = By.id("statusForCustomsClear");
    public final By statusForDroppedLoaded = By.id("statusForDroppedLoaded");
    public final By statusForDroppedEmpty = By.id("statusForDroppedEmpty");
    public final By statusForLoading = By.id("statusForLoading");
    public final By statusForDepartedPickUp = By.id("statusForDepartedPickUp");
    public final By statusForUnloading = By.id("statusForUnloading");
    public final By statusForDepartedDelivery = By.id("statusForDepartedDelivery");
    public final By statusForReportedEmpty = By.id("statusForReportedEmpty");
    public final By statusForLoadedToGo = By.id("statusForLoadedToGo");
    public final By statusForDispatched = By.id("statusForGridDispatched");
    public final By statusForCompleted = By.id("statusForCompleted");

    public final By changeStatusButton = By.xpath("//*[@id=\"form1\"]/div[2]/img[1]");


    public StatusSelectionPage(WebDriver driver) {
        super(driver);
    }

}
