package tests;

import model.folder.FolderStatusPage;
import model.HomePage;
import model.freestyle.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;
import java.util.List;

public class FolderTest extends BaseTest {

    final String FOLDER_RANDOM_NAME_1 = TestUtils.getRandomStr();
    final String FOLDER_RANDOM_NAME_2 = TestUtils.getRandomStr();
    final String DISPLAY_RANDOM_NAME = TestUtils.getRandomStr();
    final String FREESTYLE_PROJECT_NAME = TestUtils.getRandomStr();
    final String DESCRIPTION = TestUtils.getRandomStr(10);
    final String MULTIBRANCH_PIPELINE_NAME = TestUtils.getRandomStr(10);

    @Test
    public void testCreateFolder() {
        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(FOLDER_RANDOM_NAME_1));
    }

    @Test
    public void testCreateMultiConfigurationProjectInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        final String multiConfigurationProjectName = TestUtils.getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickCreateJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickParentFolderInBreadcrumbs();

        Assert.assertTrue(folderStatusPage.getJobList().contains(multiConfigurationProjectName));
    }

    @Test
    public void testConfigureChangeFolderDisplayName() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickJobDropDownMenu(FOLDER_RANDOM_NAME_1)
                .clickConfigureDropDownMenuForFolder()
                .setDisplayName(DISPLAY_RANDOM_NAME)
                .setDescription("change name")
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(DISPLAY_RANDOM_NAME));
    }

    @Test(dependsOnMethods = "testConfigureChangeFolderDisplayName")
    public void testConfigureFolderDisplayNameSaveFolderName() {
        String folderStatusPage = new HomePage(getDriver())
                .clickFolder(DISPLAY_RANDOM_NAME)
                .getFolderName();

        Assert.assertEquals(folderStatusPage, "Folder name: " + FOLDER_RANDOM_NAME_1);
    }

    @Test
    public void testMoveFolderInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_2);

        List<String> foldersNamesInFolder = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickMoveButton()
                .selectFolder(FOLDER_RANDOM_NAME_2)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .getJobList();

        Assert.assertTrue(foldersNamesInFolder.contains(FOLDER_RANDOM_NAME_1));
    }

    @Test
    public void testMoveFolderInFolderFromDropdownMenuMoveButton() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_2);

        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_RANDOM_NAME_1)
                .clickMoveButtonDropdown(new FolderStatusPage(getDriver()))
                .selectFolder(FOLDER_RANDOM_NAME_2)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(FOLDER_RANDOM_NAME_1));
        Assert.assertTrue(new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_2).getJobList().contains(FOLDER_RANDOM_NAME_1));
    }

    @Test
    public void testDeleteFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        String pageHeaderText = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickDelete()
                .clickYes()
                .getNameText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameFolderFromDropDownMenuConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_RANDOM_NAME_1)
                .clickConfigDropDownMenu()
                .setProjectName(FOLDER_RANDOM_NAME_2)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(FOLDER_RANDOM_NAME_2));
    }

    @Test
    public void testRenameFolderFromDropDownMenuRename() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_RANDOM_NAME_1)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(FOLDER_RANDOM_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(FOLDER_RANDOM_NAME_1));
        Assert.assertTrue(homePage.getJobNamesList().contains(FOLDER_RANDOM_NAME_2));
    }

    @Test
    public void testRenameFolderFromSideMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        List<String> newFolderName = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(FOLDER_RANDOM_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(newFolderName.contains(FOLDER_RANDOM_NAME_2));
    }

    @Test
    public void testCreateFreestyleProjectInFolderCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickCreateJob()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),FREESTYLE_PROJECT_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickJobDropDownMenu(FREESTYLE_PROJECT_NAME)
                .clickMoveButtonDropdown(new FreestyleProjectStatusPage(getDriver()))
                .selectFolder(FOLDER_RANDOM_NAME_1)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectInFolderNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderNewItem")
    public void testDeleteFreestyleProjectInFolder() {

        List<String> jobListBeforeDeleting = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        List<String> jobList = new FolderStatusPage(getDriver())
                .clickProject(FREESTYLE_PROJECT_NAME)
                .clickButtonDeleteProject()
                .confirmAlertAndDeleteProjectFromFolder()
                .getJobList();

        Assert.assertFalse(jobList.contains(FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(jobList.size(), (jobListBeforeDeleting.size() - 1));
    }

    @Test
    public void testCreateFolderInFolderFromCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        List<String> folderNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickCreateJob()
                .setItemName(FOLDER_RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(folderNamesInFolder.contains(FOLDER_RANDOM_NAME_2));
    }

    @Test
    public void testCreateFolderInFolderFromNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getNameText().contains(FOLDER_RANDOM_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(FOLDER_RANDOM_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(FOLDER_RANDOM_NAME_1));
    }

    @Test
    public void testCreateFolderWithDescription() {
        String textDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateFolderWithDescription")
    public void testRenameFolderWithDescription() {
        FolderStatusPage folder = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_RANDOM_NAME_1)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(FOLDER_RANDOM_NAME_1)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1);

        Assert.assertEquals(folder.getFolderNameHeader(), FOLDER_RANDOM_NAME_1);
        Assert.assertEquals(folder.getAdditionalDescriptionText(), DESCRIPTION);
    }

    @Test
    public void testAddFolderDescription() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        String textDescription = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickAddDescription()
                .setDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

    @Test
    public void testAddFolderDescriptionFromDropDownConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        String folderDescription = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_RANDOM_NAME_1)
                .clickConfigDropDownMenu()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertTrue(folderDescription.contains(DESCRIPTION));
    }

    @Test
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickNewItemDropdownThisFolderInBreadcrumbs()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .clickParentFolderInBreadcrumbs()
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testCreateMultibranchPipelineProjectInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getSideMenu()
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test (dependsOnMethods = "testCreateMultibranchPipelineProjectInFolder")
    public void testDeleteMultibranchPipelineFromFolder() {

        FolderStatusPage folder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME)
                .clickDeleteMultibranchPipelineWithFolder()
                .clickYes();

        Assert.assertEquals(folder.getFolderNameHeader(), FOLDER_RANDOM_NAME_1);
        Assert.assertNotNull(folder.getEmptyStateBlock());
    }

    @Test
    public void testCreatePipelineInFolderFromCreateJobButton() {
        final String PipelineProjectName = TestUtils.getRandomStr();

        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);
        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickCreateJob()
                .setItemName(PipelineProjectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(PipelineProjectName));
    }

    @Test
    public void testDeleteFolderUsingDropDown() {
        ProjectMethodsUtils.createNewFolder(getDriver(),FOLDER_RANDOM_NAME_1);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobDropDownMenu(FOLDER_RANDOM_NAME_1)
                .clickDeleteDropDownMenu()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}