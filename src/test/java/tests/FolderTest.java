package tests;

import model.folder.FolderStatusPage;
import model.HomePage;
import model.freestyle.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;

import java.util.List;

import static runner.TestUtils.getRandomStr;

public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = getRandomStr();
    private static final String FOLDER_NAME_2 = getRandomStr();
    private static final String DISPLAY_NAME = getRandomStr();
    private static final String FREESTYLE_PROJECT_NAME = getRandomStr();
    private static final String DESCRIPTION = getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_NAME = getRandomStr();

    @Test
    public void testCreateFolder() {
        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(FOLDER_NAME));
    }

    @Test
    public void testCreateMultiConfigurationProjectInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        final String multiConfigurationProjectName = getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickCreateJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickParentFolderInBreadcrumbs();

        Assert.assertTrue(folderStatusPage.getJobList().contains(multiConfigurationProjectName));
    }

    @Test
    public void testConfigureChangeFolderDisplayName() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickJobDropDownMenu(FOLDER_NAME)
                .clickConfigureDropDownMenuForFolder()
                .setDisplayName(DISPLAY_NAME)
                .setDescription("change name")
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(DISPLAY_NAME));
    }

    @Test(dependsOnMethods = "testConfigureChangeFolderDisplayName")
    public void testConfigureFolderDisplayNameSaveFolderName() {
        String folderStatusPage = new HomePage(getDriver())
                .clickFolder(DISPLAY_NAME)
                .getFolderName();

        Assert.assertEquals(folderStatusPage, "Folder name: " + FOLDER_NAME);
    }

    @Test
    public void testMoveFolderInFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME_2);

        List<String> foldersNamesInFolder = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .clickMoveButton()
                .selectFolder(FOLDER_NAME_2)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME_2)
                .getJobList();

        Assert.assertTrue(foldersNamesInFolder.contains(FOLDER_NAME));
    }

    @Test
    public void testMoveFolderInFolderFromDropdownMenuMoveButton() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME_2);

        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_NAME)
                .clickMoveButtonDropdown(new FolderStatusPage(getDriver()))
                .selectFolder(FOLDER_NAME_2)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(FOLDER_NAME));
        Assert.assertTrue(new HomePage(getDriver())
                .clickFolder(FOLDER_NAME_2).getJobList().contains(FOLDER_NAME));
    }

    @Test
    public void testDeleteFolder() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        String pageHeaderText = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickDeleteToHomePage()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameFolderFromDropDownMenuConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_NAME)
                .clickConfigDropDownMenu()
                .setProjectName(FOLDER_NAME_2)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(FOLDER_NAME_2));
    }

    @Test
    public void testRenameFolderFromDropDownMenuRename() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_NAME)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(FOLDER_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(FOLDER_NAME));
        Assert.assertTrue(homePage.getJobNamesList().contains(FOLDER_NAME_2));
    }

    @Test
    public void testRenameFolderFromSideMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        List<String> newFolderName = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(FOLDER_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(newFolderName.contains(FOLDER_NAME_2));
    }

    @Test
    public void testCreateFreestyleProjectInFolderCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        final String freestyleProjectName = getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickCreateJob()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),FREESTYLE_PROJECT_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickJobDropDownMenu(FREESTYLE_PROJECT_NAME)
                .clickMoveButtonDropdown(new FreestyleProjectStatusPage(getDriver()))
                .selectFolder(FOLDER_NAME)
                .clickMove()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectInFolderNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderNewItem")
    public void testDeleteFreestyleProjectInFolder() {

        List<String> jobListBeforeDeleting = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(getRandomStr())
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        List<String> jobList = new FolderStatusPage(getDriver())
                .clickProject(FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeleteProjectFromFolder()
                .getJobList();

        Assert.assertFalse(jobList.contains(FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(jobList.size(), (jobListBeforeDeleting.size() - 1));
    }

    @Test
    public void testCreateFolderInFolderFromCreateJob() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        List<String> folderNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickCreateJob()
                .setItemName(FOLDER_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(folderNamesInFolder.contains(FOLDER_NAME_2));
    }

    @Test
    public void testCreateFolderInFolderFromNewItem() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(FOLDER_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getNameText().contains(FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(FOLDER_NAME));
    }

    @Test
    public void testCreateFolderWithDescription() {
        String textDescription = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(FOLDER_NAME)
                .selectFolderAndClickOk()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateFolderWithDescription")
    public void testRenameFolderWithDescription() {
        FolderStatusPage folder = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_NAME)
                .clickRenameFolderDropDownMenu()
                .clearFieldAndInputNewName(FOLDER_NAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME);

        Assert.assertEquals(folder.getFolderNameHeader(), FOLDER_NAME);
        Assert.assertEquals(folder.getAdditionalDescriptionText(), DESCRIPTION);
    }

    @Test
    public void testAddFolderDescription() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        String textDescription = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickAddDescription()
                .setDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION);
    }

    @Test
    public void testAddFolderDescriptionFromDropDownConfigure() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        String folderDescription = new HomePage(getDriver())
                .clickJobDropdownMenu(FOLDER_NAME)
                .clickConfigDropDownMenu()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertTrue(folderDescription.contains(DESCRIPTION));
    }

    @Test
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        final String freestyleProjectName = getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
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
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getSideMenu()
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test (dependsOnMethods = "testCreateMultibranchPipelineProjectInFolder")
    public void testDeleteMultibranchPipelineFromFolder() {

        FolderStatusPage folder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickDeleteToFolder()
                .clickYes();

        Assert.assertEquals(folder.getFolderNameHeader(), FOLDER_NAME);
        Assert.assertNotNull(folder.getEmptyStateBlock());
    }

    @Test
    public void testCreatePipelineInFolderFromCreateJobButton() {
        final String PipelineProjectName = getRandomStr();

        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickCreateJob()
                .setItemName(PipelineProjectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(PipelineProjectName));
    }

    @Test
    public void testDeleteFolderUsingDropDown() {
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobDropDownMenu(FOLDER_NAME)
                .clickDeleteDropDownMenu()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}