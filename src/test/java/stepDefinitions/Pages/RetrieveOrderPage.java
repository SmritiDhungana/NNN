package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RetrieveOrderPage extends BasePage {
    public final By orderNumberField = By.id("ordernum");
    public final By goButton = By.xpath("//tbody/tr[1]/td[3]/a[1]/img[1]");

    public final By billToRP = By.xpath("//body[1]/form[1]/div[1]/table[2]/tbody[1]/tr[1]/td[5]/div[1]/table[4]/tbody[1]/tr[1]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]");
    public final By fromRP = By.xpath("//body[1]/form[1]/div[1]/table[2]/tbody[1]/tr[1]/td[5]/div[1]/table[4]/tbody[1]/tr[1]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]");
    public final By toRP = By.xpath("//body[1]/form[1]/div[1]/table[2]/tbody[1]/tr[1]/td[5]/div[1]/table[4]/tbody[1]/tr[1]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[5]/span[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]");
    public final By containerRP = By.id("container");
    public final By chassisRP = By.id("chassis");
    public final By emptyContainerRP = By.id("emptycontainer");
    public final By referenceOneRP = By.id("reference1");
    public final By referenceTwoRP = By.id("reference2");
    public final By referenceThreeRP = By.id("reference3");
    public final By freightChargesTotalRP = By.id("divFinancialInformation_Financialinformation1_freightcharge_total");
    public final By freightChargesIndependentContractorPayRP = By.id("divFinancialInformation_Financialinformation1_freightcharge_tractorDisplay1");
    public final By fuelChargesQuantityRP = By.id("divFinancialInformation_Financialinformation1_fuelsurcharge_qty");
    public final By fuelChargesRateRP = By.id("divFinancialInformation_Financialinformation1_fuelsurcharge_rate");
    //public final By fuelChargesTotalRP = By.xpath("//tbody/tr[1]/td[3]/a[1]/img[1]");
    public final By fuelChargesIndependentContractorPayRP = By.id("divFinancialInformation_Financialinformation1_fuelsurcharge_tractorDisplay1");
    public final By dailyChassisChargesQuantityRP = By.id("divFinancialInformation_Financialinformation1_dailychassis_qty");
    public final By dailyChassisChargesRateRP = By.id("divFinancialInformation_Financialinformation1_dailychassis_rate");
    //public final By dailyChassisChargesTotalRP = By.xpath("//tbody/tr[1]/td[3]/a[1]/img[1]");
    public final By dailyChassisChargesIndependentContractorPayRP = By.id("divFinancialInformation_Financialinformation1_dailychassis_tractorDisplay1");
    public final By pickupAppointmentDate = By.id("AppointmentDate");
    public final By pickupAppointmentTime = By.id("appointmenttime");
    public final By actualDate = By.id("ActualDate");



    public RetrieveOrderPage(WebDriver driver) {
        super(driver);
    }

}
