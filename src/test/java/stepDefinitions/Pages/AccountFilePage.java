package stepDefinitions.Pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountFilePage extends BasePage{

    public final By SearchIn = By.id("txtSearch");
    public final By SelectFirstColumn = By.xpath("//*[@id=\"selectFocus\"]");
    public final By AccountFileMaintenance = By.xpath("//*[@id=\"SectionDivId\"]/div[1]/div[1]/h4");
    public final By Name = By.xpath("//*[@id=\"lblAccountName\"]");
    public final By BillToButton = By.xpath("//*[@id=\"chkBillTos\"]");
    public final By SHCONButton = By.xpath("//*[@id=\"chkSHCN\"]");

    public final By Back = By.linkText("Back");

    public AccountFilePage(WebDriver driver) {
        super(driver);
    }
}
