package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EBHLoginPage extends BasePage {
    public final By username = By.id("username");
    public final By password = By.id("password");
    public final By signinButton = By.xpath("/html/body/div/div/div[1]/form/div[4]/div/input");


    public EBHLoginPage(WebDriver driver) {
        super(driver);
    }

    }