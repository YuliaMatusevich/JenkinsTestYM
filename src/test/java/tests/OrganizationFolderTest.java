package tests;
import model.HomePage;
import model.folder.FolderStatusPage;
import model.organization_folder.OrgFolderStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;

import static runner.TestUtils.getRandomStr;

public class OrganizationFolderTest extends BaseTest {
    private static final String ORGANIZATION_FOLDER_NAME = getRandomStr();
    private static final String ORGANIZATION_FOLDER_RENAME = getRandomStr();
    private static final String FOLDER_NAME = getRandomStr();
    private static final String DISPLAY_NAME = getRandomStr();

    @Test
    public void testRenameOrganizationFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(ORGANIZATION_FOLDER_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(ORGANIZATION_FOLDER_RENAME));
    }

    @Test
    public void testDeleteOrganizationFolderDependsMethods() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickDeleteToHomePage()
                .clickYes();

        Assert.assertFalse(homePage.getJobNamesList().contains(ORGANIZATION_FOLDER_NAME));
    }

    @Test
    public void testConfigureOrganizationFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);
        final String description = getRandomStr();

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(ORGANIZATION_FOLDER_NAME)
                .getSideMenu()
                .clickConfigure()
                .inputDisplayName(DISPLAY_NAME)
                .inputDescription(description)
                .clickSaveButton();

        Assert.assertEquals(orgFolderStatusPage.getNameText(), DISPLAY_NAME);
        Assert.assertEquals(orgFolderStatusPage.getAdditionalDescriptionText(), description);

        HomePage homePage = orgFolderStatusPage.getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(DISPLAY_NAME));
    }

    @Test
    public void testMoveOrgFolderToFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(ORGANIZATION_FOLDER_NAME)
                .clickMoveButton()
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME);

        Assert.assertTrue(folderStatusPage.getJobList().contains(ORGANIZATION_FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testMoveOrgFolderToFolder")
    public void testMoveOrgFolderToDashboard() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickOrgFolder(ORGANIZATION_FOLDER_NAME)
                .clickMoveButton()
                .selectOptionToDashBoard()
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(ORGANIZATION_FOLDER_NAME));
    }
}