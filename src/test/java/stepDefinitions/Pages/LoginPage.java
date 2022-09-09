package stepDefinitions.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;




public class LoginPage extends BasePage {
    public final By username = By.id("username");
    public final By password = By.id("password");
    public final By loginButton = By.xpath("//*[@id=\"LoginButton\"]/img");
    public final By Alert = By.xpath("//*[@id=\"divCorpUser\"]");
    public final By EnterFirstNameandLastName = By.xpath("//*[@id=\"txtCorpName\"]");
    public final By Ok = By.xpath("//*[@id=\"Yes\"]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

}
