package tests;

import model.HomePage;
import model.status_pages.FolderStatusPage;
import model.status_pages.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

import static runner.TestUtils.getRandomStr;

public class FolderTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FOLDER_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(TestDataUtils.FOLDER_NAME));
    }

    @Test
    public void testCreateMultiConfigurationProjectInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        final String multiConfigurationProjectName = getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickCreateJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickParentFolder();

        Assert.assertTrue(folderStatusPage.getJobList().contains(multiConfigurationProjectName));
    }

    @Test
    public void testConfigureChangeFolderDisplayName() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.FOLDER_NAME)
                .clickConfigureDropDownMenuForFolder()
                .setDisplayName(TestDataUtils.DISPLAY_NAME)
                .setDescription("change name")
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(TestDataUtils.DISPLAY_NAME));
    }

    @Test(dependsOnMethods = "testConfigureChangeFolderDisplayName")
    public void testConfigureFolderDisplayNameSaveFolderName() {
        String folderStatusPage = new HomePage(getDriver())
                .clickFolder(TestDataUtils.DISPLAY_NAME)
                .getFolderName();

        Assert.assertEquals(folderStatusPage, "Folder name: " + TestDataUtils.FOLDER_NAME);
    }

    @Test
    public void testMoveFolderInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME_2);

        List<String> foldersNamesInFolder = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickMove()
                .selectFolder(TestDataUtils.FOLDER_NAME_2)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME_2)
                .getJobList();

        Assert.assertTrue(foldersNamesInFolder.contains(TestDataUtils.FOLDER_NAME));
    }

    @Test
    public void testMoveFolderInFolderFromDropdownMenuMoveButton() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME_2);

        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickMoveButtonDropdown(new FolderStatusPage(getDriver()))
                .selectFolder(TestDataUtils.FOLDER_NAME_2)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME));
        Assert.assertTrue(new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME_2).getJobList().contains(TestDataUtils.FOLDER_NAME));
    }

    @Test
    public void testDeleteFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        String pageHeaderText = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickDeleteToHomePage()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameFolderFromDropDownMenuConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickConfigDropDownMenu()
                .setProjectName(TestDataUtils.FOLDER_NAME_2)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME_2));
    }

    @Test
    public void testRenameFolderFromDropDownMenuRename() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.FOLDER_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME));
        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME_2));
    }

    @Test
    public void testRenameFolderFromSideMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> newFolderName = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.FOLDER_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(newFolderName.contains(TestDataUtils.FOLDER_NAME_2));
    }

    @Test
    public void testCreateFreestyleProjectInFolderCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        final String freestyleProjectName = getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickCreateJob()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickMoveButtonDropdown(new FreestyleProjectStatusPage(getDriver()))
                .selectFolder(TestDataUtils.FOLDER_NAME)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectInFolderNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderNewItem")
    public void testDeleteFreestyleProjectInFolder() {

        List<String> jobListBeforeDeleting = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(getRandomStr())
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        List<String> jobList = new FolderStatusPage(getDriver())
                .clickProject(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeleteProjectFromFolder()
                .getJobList();

        Assert.assertFalse(jobList.contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(jobList.size(), (jobListBeforeDeleting.size() - 1));
    }

    @Test
    public void testCreateFolderInFolderFromCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        List<String> folderNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickCreateJob()
                .setItemName(TestDataUtils.FOLDER_NAME_2)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(folderNamesInFolder.contains(TestDataUtils.FOLDER_NAME_2));
    }

    @Test
    public void testCreateFolderInFolderFromNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FOLDER_NAME_2)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getNameText().contains(TestDataUtils.FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenuLinkText().contains(TestDataUtils.FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenuLinkText().contains(TestDataUtils.FOLDER_NAME));
    }

    @Test
    public void testCreateFolderWithDescription() {
        String textDescription = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FOLDER_NAME)
                .selectFolderType()
                .clickOkButton()
                .setDescription(TestDataUtils.DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertEquals(textDescription, TestDataUtils.DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateFolderWithDescription")
    public void testRenameFolderWithDescription() {
        FolderStatusPage folder = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.FOLDER_NAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME);

        Assert.assertEquals(folder.getFolderNameHeader(), TestDataUtils.FOLDER_NAME);
        Assert.assertEquals(folder.getAdditionalDescriptionText(), TestDataUtils.DESCRIPTION);
    }

    @Test
    public void testAddFolderDescription() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        String textDescription = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickAddOrEditDescription()
                .setDescription(TestDataUtils.DESCRIPTION)
                .clickSubmitButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, TestDataUtils.DESCRIPTION);
    }

    @Test
    public void testAddFolderDescriptionFromDropDownConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME_2);

        String folderDescription = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME_2)
                .clickConfigDropDownMenu()
                .setDescription(TestDataUtils.DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertTrue(folderDescription.contains(TestDataUtils.DESCRIPTION));
    }

    @Test
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        final String freestyleProjectName = getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickNewItemDropdownThisFolderInBreadcrumbs()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickParentFolder()
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testCreateMultibranchPipelineProjectInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(TestDataUtils.MULTIBRANCH_PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineProjectInFolder")
    public void testDeleteMultibranchPipelineFromFolder() {

        FolderStatusPage folder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickDeleteToFolder()
                .clickYes();

        Assert.assertEquals(folder.getFolderNameHeader(), TestDataUtils.FOLDER_NAME);
        Assert.assertNotNull(folder.getEmptyStateBlock());
    }

    @Test
    public void testCreatePipelineInFolderFromCreateJobButton() {
        final String pipelineProjectName = getRandomStr();

        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .clickCreateJob()
                .setItemName(pipelineProjectName)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(pipelineProjectName));
    }

    @Test
    public void testDeleteFolderUsingDropDown() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.FOLDER_NAME)
                .clickDeleteDropDownMenu()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}