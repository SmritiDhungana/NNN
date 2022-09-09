package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainMenuPage extends BasePage {
    public final By operations = By.id("Operations");
    public final By biling = By.xpath("//*[@id=\"HyperLinkBilling\"]/li");
    public final By dispatchGridLink = By.xpath("//*[@id=\"HyperLinkOpenGrid\"]/li");


    public MainMenuPage(WebDriver driver) {
        super(driver);
    }

}
