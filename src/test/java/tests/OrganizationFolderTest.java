package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.status.FolderStatusPage;
import model.page.status.OrgFolderStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.HashMap;

import static runner.TestUtils.getRandomStr;

public class OrganizationFolderTest extends BaseTest {

    @TmsLink("HpuGw5FI")
    @Owner("Umida Kaharova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be renamed")
    @Test
    public void testRenameOrganizationFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.ORGANIZATION_FOLDER_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.ORGANIZATION_FOLDER_RENAME));
    }

    @TmsLink("s4ILu5Hi")
    @Owner("EkaterinaTurgunova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be deleted with the 'Delete' on the side menu")
    @Test
    public void testDeleteOrganizationFolderDependsMethods() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickDeleteToHomePage()
                .clickYes();

        Assert.assertFalse(homePage.getJobNamesList().contains(TestDataUtils.ORGANIZATION_FOLDER_NAME));
    }

    @TmsLink("vTPNdXOw")
    @Owner("EkaterinaTurgunova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Display name' can be added to the Organization Folder")
    @Test
    public void testConfigureOrganizationFolderWithName() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickConfigure()
                .inputDisplayName(TestDataUtils.DISPLAY_NAME)
                .clickSaveButton();

        Assert.assertEquals(orgFolderStatusPage.getHeaderText(), TestDataUtils.DISPLAY_NAME);
    }

    @TmsLink("vTPNdXOw")
    @Owner("Stanislaw1349")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Description' can be added to the Organization Folder")
    @Test
    public void testConfigureOrganizationFolderWithDescription() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);
        final String description = getRandomStr();

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickConfigure()
                .inputDescription(description)
                .clickSaveButton();

        Assert.assertEquals(orgFolderStatusPage.getAdditionalDescriptionText(), description);
    }

    @TmsLink("D3Cgw46S")
    @Owner("Ina Chemerko")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The Organization folder can be moved to folder")
    @Test
    public void testMoveOrgFolderToFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickMove()
                .selectFolder(TestDataUtils.FOLDER_NAME)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME);

        Assert.assertTrue(folderStatusPage.getJobList().contains(TestDataUtils.ORGANIZATION_FOLDER_NAME));
    }

    @TmsLink("iwaFKc65")
    @Owner("Ina Chemerko")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The Organization folder can be moved from folder to dashboard")
    @Test(dependsOnMethods = "testMoveOrgFolderToFolder")
    public void testMoveOrgFolderToDashboard() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickMove()
                .selectDashboardAsFolder()
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.ORGANIZATION_FOLDER_NAME));
    }

    @Ignore
    @TmsLink("LksqezL1")
    @Owner("Alina_Sinyagina")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The child health metrics can be added to Organization folder")
    @Test
    public void testCheckChildHealthMetrics() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        boolean actualResult = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .clickLinkConfigureTheProject()
                .clickHealthMetricsTab()
                .clickMetricsButton()
                .checkChildMetricsIsDisplayed();

        Assert.assertTrue(actualResult);
    }

    @TmsLink("gR0bpDze")
    @Owner("Alina_Sinyagina")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be disable")
    @Test
    public void testChangeStatusToDisableOrgFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        HashMap<String, String> warningMessage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .clickDisableButton()
                .getWarningTextAboutDisabledOrgFolder();

        Assert.assertEquals(warningMessage.get("Warning Message"), "This Organization Folder is currently disabled");
        Assert.assertEquals(warningMessage.get("Message Color"), "rgba(254, 130, 10, 1)");
    }
}