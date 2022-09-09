package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettlementsPage extends BasePage {

    public final By agentSettling = By.id("btnAgentSettling");
    public final By EnterPasswordForAgentSettling = By.xpath("//*[@id=\"txtPasswordAgentSettling\"]");
    public final By OkButton = By.id("SettlingloadingClassMain");

    public final By PopUpConformation1 = By.xpath("//*[@id=\"Divpopup\"]");
    public final By PopUpConformation = By.xpath("//*[@id=\"Divpopup\"]/div");
    public final By Continue = By.xpath("//a[text()='Continue']");
    public final By Reset = By.xpath("//a[text()='Reset']");
    public final By PopUpDecision = By.xpath("//*[@id=\"divnewbutton\"]/div");
  //  public final By PreviousWeek = By.xpath("//*[@id=\"rdnPreviousWeek\"]");
    public final By PreviousWeek = By.id("rdnPreviousWeek");
  //  public final By CurrentWeek = By.xpath("//*[@id=\"rdnCurrentWeek\"]");
    public final By DecisionOk = By.xpath("//a[text()='OK']");

    public final By AgentCommissionMaintenance = By.id("btnAgentCommissionMaintenance");
    public final By EnterPasswordForAgentCommissionMaintenance = By.xpath("//*[@id=\"txtPassword\"]");
    public final By OkBtn = By.id("loadingClassMain");
    public final By AARAgentManagementFile = By.id("btnAARAgentManagementFile");
    public final  By AgentSettlementFlagMaintenance = By.id("btnAgentFlagMaintenance");
    public final By AgentCommissionCalculation = By.id("btnAgentCommissionCalculation");
    public final By AgentSettlementInquiry = By.xpath("//*[@id=\"divEquipment\"]/div/div[9]/a/input");
    //*[@id="divEquipment"]/div/div[10]/a/input
    public final By AgentSettlementAdjustments = By.xpath("//*[@id=\"btnAgentSettlementAdjustments\"]");
    public final By AgentsCurrentlyBeingSettled = By.xpath("//*[@id=\"btnAgentsCurrentlyBeingSettled\"]");
    public final By Logout = By.xpath("/html/body/header/div/div[2]/div/div[2]/a");


    public final By TractorVendorRelationship = By.xpath("//*[@id=\"divEquipment\"]/div/div[8]/a/input");
    //*[@id="divEquipment"]/div/div[8]/a/input
    public final By TractorSettlementAdjustments = By.xpath("//*[@id=\"divEquipment\"]/div/div[9]/a/input");







    public SettlementsPage(WebDriver driver) {
        super(driver);
    }
}

