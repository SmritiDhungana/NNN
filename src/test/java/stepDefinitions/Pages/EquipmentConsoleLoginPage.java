package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EquipmentConsoleLoginPage extends BasePage {
    public final By username = By.id("username");
    public final By password = By.id("password");
    public final By loginButton = By.xpath("//*[@id=\"LoginButton\"]/img");


    public EquipmentConsoleLoginPage(WebDriver driver) {
        super(driver);
    }

    }