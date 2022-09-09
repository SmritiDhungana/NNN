package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasicFileMaintenancePage extends BasePage{

    public final By AccountFile = By.id("btnAccountFile");
    public final By LocationFile = By.id("btnLocationFile");

    public BasicFileMaintenancePage(WebDriver driver) {
        super(driver);
    }
}
