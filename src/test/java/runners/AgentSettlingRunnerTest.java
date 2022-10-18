package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/featureFiles"},
        glue = {"stepDefinitions"},
        monochrome = true,
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "html:target/cucumber-reports/cucumber.html",
                "rerun:target/cucumber-reports/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        tags = "@Smoke"
)

public class AgentSettlingRunnerTest {


}


/// AgentSettling.feature
// 1   @OWTBFContinue
// 2   @OWTBFReset
// 3   @SettleYourAgent&ViewEpicorExcelReportsContinue
// 4   @SettleYourAgent&ViewEpicorExcelReportsReset
// 5   @SettlementOrderDetailsContinue
// 6   @SettlementOrderDetailsReset
// 7   @AgentsYoureSettlingAddedRemoved
// 8   @AdjustmentsDetailsAndAdjustmentsOnHold
// 9   @MyActiveAgentsNotSettled
// 10  @AgentSettlementFlagMaintenance
// 11  @DBAgentSettlements&AgentStlTrans
// 12  @DBOrderDocs&TfDocDataD4

/// AgentSettlementInquiry.feature
// 13 @AgentSettlementInquiryAgent
// 14 @AgentSettlementInquiryVendor
// 15 @AgentSettlementInquiryPayCodes

/// AgentsCurrentlyBeingSettled.feature
// 16 @AgentsCurrentlyBeingSettledVendorCode
// 17 @AgentsCurrentlyBeingSettledAgentCode

/// AgentCommission.feature
// 18 @AgentCommissionMaintenance
// 19 @AgentCommissionCalculation

/// BasicFileMaintenance.feature
// 20 @AccountFile
// 21 @LocationFile

/// @AARAgentManagementFile.feature
// 20 @AARAgentManagementFile   /
// 25 @AARReassignment
// 26 @AARReassignmentAgentThatSharesVendorCodeWithOtherAgents
// 27 @AARAgentManagementWeeklyCutoffDay/Time


/// AgentSettlementAdjustments.feature
// 15 @AgentSettlementAdjustmentsVendorId  /
// 16 @AgentSettlementAdjustmentsAgentCode
// 28 @ScenarioActiveCompleteActive
// 29 @ScenarioOnNewDATE
// 30 @ScenarioOnNewMaxLimitandTotaltoDate
// 31 @ScenarioAdvancedSearchVendorCode
// 32 @ScenarioAdvancedSearchAgentCode
// 33 @ScenarioViewButton
// 34 @ScenarioDeleteButton
// 35 @ScenarioReport
// 36 @ScenarioQuickEntry

/// EquipmentConsole.feature
// 37a  @IdentifyINVOICEforAgentReimbursement
// 37b  @AgentReimbursement
// 38a  @IdentifyINVOICEforAgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable
// 38b  @AgentReimbursementTestForAgentWithoutRecordOnAgentSettlementInfoTable
// 39a  @IdentifyINVOICEforAgentDeduction
// 39b  @AgentDeduction
// 40a  @IdentifyINVOICEforAgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable
// 40b  @AgentDeductionTestForAgentWithoutRecordOnAgentSettlementInfoTable
// 41a  @VerifyTaxAndAdminBillingCodesHaveBeenSetup&IdentifyINVOICEforBillCustomer
// 41b @BillCustomer
// 41c @RetrieveNewOrderPRo
// 41d @VerifyDataInTables

/// Billing.feature
// 42 @BookLoadScenarioOutLine

/// BCPDTractorValidation.feature
// 43a  @BCPDCreateBillPositive
// 43b  @BCPDCreateBillNeg
// 43ab @BCPDOrderNumOnDB
// 44a  @PDOnlyCreateBillPositive
// 44b  @PDOnlyCreateBillNeg
// 44ab @PDOnlyOrderNumOnDB

/// FuelPurchaseMaintenance.feature
// 45 @FPMValidationTractorID
// 46 @FPMValidationTractorIDEarliestDate
// 47 @FPMValidationTractorIDLatestDate
// 48 @FPMValidationIFTA
// 49 @FPMValidationIFTAEarliestDate
// 50 @FPMValidationIFTALatestDate
// 51 @FPMScenarioNew
// 52 @FPMScenarioEdit
// 53 @FPMScenarioReportTractorID
// 54 @FPMScenarioReportIFTA

/// AARAgentManagementFile.feature
// 55 @TractorAARLogin
// 56 @TractorAARLoginNoOneIsCurrentlySettling
// 57 @TractorAARLoginAnotherAARisSettlingTheAgentVerified
// 58 @TractorAARLoginAnotherAARisSettlingTheAgentNOTVerified