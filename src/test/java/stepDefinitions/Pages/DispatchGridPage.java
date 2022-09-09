package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DispatchGridPage extends BasePage {
    public final By proNumberField = By.id("_ctl0_txtSearchPro");
    public final By quickSearchButton = By.xpath("//*[@id=\"aspnetForm\"]/div[3]/div[1]/table/tbody/tr/td[5]/a/img");

    public final By firstTractorToPickInTheList = By.xpath("//div[contains(text(),'00001PD')]");
    public final By assignToOrderNumberPulledUp = By.xpath("//*[@id=\"_peg\"]");

    public final By statusTabOnOrderLine = By.xpath("//*[@id=\"rw_0\"]/td[8]");
    public final By statusHistoryButtonOnStatusSelectionPopup = By.xpath("//*[@id=\"form1\"]/div[2]/img[2]");
    public final By entireTableInHistorySearch = By.xpath("//*[@id=\"GridViewStatusHistory\"]/tbody");

    public final By exitButtonOnHistorySearch = By.id("CloseButton_ctl0_RadWindowManager1_ClosableWindow");


    public DispatchGridPage(WebDriver driver) {
        super(driver);
    }

}
