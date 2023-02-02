package tests;

import model.HomePage;
import model.status_pages.FolderStatusPage;
import model.status_pages.OrgFolderStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import static runner.TestUtils.getRandomStr;

public class OrganizationFolderTest extends BaseTest {

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

    @Test
    public void testConfigureOrganizationFolderWithName() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickConfigure()
                .inputDisplayName(TestDataUtils.DISPLAY_NAME)
                .clickSaveButton();

        Assert.assertEquals(orgFolderStatusPage.getNameText(), TestDataUtils.DISPLAY_NAME);
    }

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
}