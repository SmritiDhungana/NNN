package stepDefinitions.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EBHMainMenuPage extends BasePage {

    public final By corporate = By.xpath("//*[@id=\"lnkCorporate\"]/a");

    public EBHMainMenuPage(WebDriver driver) {
        super(driver);
    }
}

