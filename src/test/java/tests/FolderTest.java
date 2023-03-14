package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.status.FolderStatusPage;
import model.page.status.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

import static runner.TestUtils.getRandomStr;

public class FolderTest extends BaseTest {

    @TmsLink("iRLto5x7")
    @Owner("Vita Zharskaya")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify the ability to create a new folder for organizing items")
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

    @TmsLink("vAWUjRKF")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify the ability to create a 'Multi-configuration project' in existed folder")
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

    @TmsLink("oNC0G9nC")
    @Owner("Ilya Korolkov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create a unique display name for the folder")
    @Test
    public void testConfigureChangeFolderDisplayName() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickConfigureDropDownMenuForFolder()
                .setDisplayName(TestDataUtils.DISPLAY_NAME)
                .setDescription("change name")
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(projectNamesOnDashboard.contains(TestDataUtils.DISPLAY_NAME));
    }

    @TmsLink("28hfWiR7")
    @Owner("Maks Pt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the previously changed folder name is saved")
    @Test(dependsOnMethods = "testConfigureChangeFolderDisplayName")
    public void testConfigureFolderDisplayNameSaveFolderName() {
        String folderStatusPage = new HomePage(getDriver())
                .clickFolder(TestDataUtils.DISPLAY_NAME)
                .getFolderName();

        Assert.assertEquals(folderStatusPage, "Folder name: " + TestDataUtils.FOLDER_NAME);
    }

    @TmsLink("IzjUz5kr")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the folder can be moved into another folder")
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

    @TmsLink("bHnC2d35")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the folder can be moved into another folder from dropdown menu")
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

    @TmsLink("Pxtu2m1s")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the folder can be deleted")
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

    @TmsLink("oNC0G9nC")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the name of the folder can be changed through 'Configure' dropdown menu")
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

    @TmsLink("dBpWY2Od")
    @Owner("0jeqCDcX")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the name of the folder can be changed using the option 'Rename' dropdown menu")
    @Test
    public void testRenameFolderFromDropDownMenuRename() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        HomePage homePage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickRenameFolderDropdownMenu()
                .clearFieldAndInputNewName(TestDataUtils.FOLDER_NAME_2)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME));
        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.FOLDER_NAME_2));
    }

    @TmsLink("whA7Azxk")
    @Owner("Arailym Ashimova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the name of the folder can be changed through side menu")
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

    @TmsLink("ddlBlCgY")
    @Owner("Maks Pt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify ability to create 'Freestyle project' in folder using the option 'Create a job'")
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

    @TmsLink("H8dMlRHm")
    @Owner("Maks Pt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create a 'Freestyle Project' in the folder")
    @Test
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickMoveButtonDropdown(new FreestyleProjectStatusPage(getDriver()))
                .selectFolder(TestDataUtils.FOLDER_NAME)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @TmsLink("nuV6ObMF")
    @Owner("Maks Pt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create a 'Freestyle project' in the folder using the option 'New Item'")
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

    @TmsLink("VQ4w9ZyF")
    @Owner("Ina Romankova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to delete 'Freestyle Project' from the folder")
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

    @TmsLink("7i2o1N0L")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create folder in the folder using the option 'Create a job'")
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

    @TmsLink("WEBhQeoB")
    @Owner("Igor Shmakov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create a new folder in existing folder using the 'New Item' option")
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

        Assert.assertTrue(folderStatusPage.getHeaderText().contains(TestDataUtils.FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenuLinkText().contains(TestDataUtils.FOLDER_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenuLinkText().contains(TestDataUtils.FOLDER_NAME));
    }

    @TmsLink("8TVmjORt")
    @Owner("Evgeniya Piskunova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to create a new folder with description")
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

    @TmsLink("0jeqCDcX")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify the ability to rename a folder's name with a description")
    @Test(dependsOnMethods = "testCreateFolderWithDescription")
    public void testRenameFolderWithDescription() {
        FolderStatusPage folder = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickRenameFolderDropdownMenu()
                .clearFieldAndInputNewName(TestDataUtils.FOLDER_NAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME);

        Assert.assertEquals(folder.getFolderNameHeader(), TestDataUtils.FOLDER_NAME);
        Assert.assertEquals(folder.getAdditionalDescriptionText(), TestDataUtils.DESCRIPTION);
    }

    @TmsLink("8TVmjORt")
    @Owner("Igor Shmakov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Ensure the ability to add a description to an existing folder through the field 'Description'")
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

    @TmsLink("qBCeUdZo")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Ensure the ability to add a description to an existing folder through the folder's dropdown menu")
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

    @TmsLink("T1Tyel4t")
    @Owner("Sergei Obukhov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Confirm that the created folder includes the newly created Freestyle Project")
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

    @TmsLink("vVV2AFAt")
    @Owner("Vikky Efanova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Confirm that the 'Multibranch Pipeline' project was successfully created within the folder")
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

    @TmsLink("awfycpQw")
    @Owner("Den Sebrovsky")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Confirm that the 'Multibranch pipeline' no longer exists within the folder after it has been deleted")
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

    @TmsLink("WRr5Px6O")
    @Owner("Andrew Shapiro")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Confirm that a 'Pipeline Project' was created within the folder by using the 'Create a job' button")
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

    @TmsLink("08U6qhrf")
    @Owner("Arailym Ashimova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Confirm that the folder was successfully deleted by using the dropdown menu")
    @Test
    public void testDeleteFolderUsingDropDown() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.FOLDER_NAME)
                .clickDeleteDropDownMenu()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}