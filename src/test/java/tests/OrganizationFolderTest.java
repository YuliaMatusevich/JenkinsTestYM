package tests;
import model.HomePage;
import model.folder.FolderStatusPage;
import model.organization_folder.OrgFolderStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

public class OrganizationFolderTest extends BaseTest {
    private static final String ORG_FOLDER_NAME = TestUtils.getRandomStr();
    private static final String NEW_ORG_FOLDER_NAME = TestUtils.getRandomStr();
    private static final String FOLDER_NAME = TestUtils.getRandomStr();
    private static final String DISPLAY_NAME = TestUtils.getRandomStr();

    @Test
    public void testRenameOrganizationFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORG_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(ORG_FOLDER_NAME)
                .getSideMenu()
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(NEW_ORG_FOLDER_NAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(NEW_ORG_FOLDER_NAME));
    }

    @Test
    public void testDeleteOrganizationFolderDependsMethods() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORG_FOLDER_NAME);

        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(ORG_FOLDER_NAME)
                .getSideMenu()
                .clickDeleteOrganizationFolder()
                .clickSaveButton();

        Assert.assertFalse(homePage.getJobNamesList().contains(ORG_FOLDER_NAME));
    }

    @Test
    public void testConfigureOrganizationFolder() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORG_FOLDER_NAME);
        final String description = TestUtils.getRandomStr();

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(ORG_FOLDER_NAME)
                .getSideMenu()
                .clickConfigureSideMenu()
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
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORG_FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(ORG_FOLDER_NAME)
                .clickMoveButton()
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME);

        Assert.assertTrue(folderStatusPage.getJobList().contains(ORG_FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testMoveOrgFolderToFolder")
    public void testMoveOrgFolderToDashboard() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickOrgFolder(ORG_FOLDER_NAME)
                .clickMoveButton()
                .selectOptionToDashBoard()
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(ORG_FOLDER_NAME));
    }
}
