package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LocationFilePage extends BasePage{

    public final By LocationCode = By.xpath("//*[@id=\"LocationCodePartial_I\"]");
    public final By PartOfLocationName = By.xpath("//*[@id=\"txtSearch\"]");
    public final By Select = By.linkText("Select");
    public final By Name = By.xpath("//*[@id=\"txtLocationName\"]");
    public final By Status = By.xpath("//*[@id=\"LocationStatusPartial\"]/tbody/tr/td[2]");
    public final By AARRep = By.xpath("//*[@id=\"locationDetail\"]/div[1]/div[2]/div[3]");


    public LocationFilePage(WebDriver driver) {
        super(driver);
    }
}
