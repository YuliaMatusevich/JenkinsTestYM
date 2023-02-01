package tests;

import model.CreateItemErrorPage;
import model.HomePage;
import model.NewItemPage;
import model.status_pages.MultiConfigurationProjectStatusPage;
import model.status_pages.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;
import runner.TestDataUtils;

import java.util.List;

import static runner.ProjectMethodsUtils.createNewFolder;
import static runner.TestUtils.getRandomStr;

public class NewItemTest extends BaseTest {

    private static final String PROJECT_NAME = getRandomStr();
    private static final String ITEM_NEW_DESCRIPTION = "New description";
    private static final String NEW_PROJECT_NAME = getRandomStr();

    @Test
    public void testNewItemsPageContainsItemsWithoutCreatedProject() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem();

        Assert.assertEquals(newItemPage.newItemsNameList(), expectedResult);
    }

    @Test
    public void testCheckNavigationToNewItemPage() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem();

        Assert.assertEquals(newItemPage.getH3HeaderText(), "Enter an item name");
    }

    @Test
    public void testCreateFolder() {

        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickApplyButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getJobName(PROJECT_NAME), PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineAssertInsideJob() {
        String actualPipelineName = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(actualPipelineName, "Pipeline " + PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineAssertOnDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PROJECT_NAME);
        String actualPipelineName = new HomePage(getDriver())
                .getJobName(PROJECT_NAME);

        Assert.assertEquals(actualPipelineName, PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineAssertOnBreadcrumbs() {
        String actualTextOnBreadcrumbs = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .getTextBreadcrumbs();

        Assert.assertTrue(actualTextOnBreadcrumbs.contains(PROJECT_NAME), PROJECT_NAME + " Pipeline Not Found On Breadcrumbs");
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        String actualPipelineDescription = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .setDescriptionField(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualPipelineDescription, ITEM_NEW_DESCRIPTION);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        PipelineStatusPage pipelineStatusPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton();

        Assert.assertEquals(pipelineStatusPage.getPipelineName(), NEW_PROJECT_NAME);
        Assert.assertEquals(pipelineStatusPage.getDescriptionText(), ITEM_NEW_DESCRIPTION);
    }

    @Test
    public void testCreateNewItemWithUnsafeCharacterName() {
        final String nameNewItem = "5%^PiPl$^Ne)";
        String errorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(nameNewItem)
                .getItemNameInvalidMsg();

        Assert.assertEquals(errorMessage, "» ‘%’ is an unsafe character");
    }

    @Test
    public void testCreateNewItemTypePipelineWithEmptyName() {
        final String nameNewItemTypePipeline = "";
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(nameNewItemTypePipeline)
                .selectPipelineType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        String newItemPageErrorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .getItemNameInvalidMsg();

        Assert.assertEquals(newItemPageErrorMessage, (String.format("» A job already exists with the name ‘%s’", PROJECT_NAME)));
    }

    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {
        String pipelineNameInMyViewList = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickMyViewsSideMenuLink().getListProjectsNamesAsString();

        Assert.assertTrue(pipelineNameInMyViewList.contains(PROJECT_NAME), PROJECT_NAME + " Pipeline not found");
    }

    @Test
    public void testCreateNewItemFromOtherNonExistingName() {
        createNewFolder(getDriver(), PROJECT_NAME);
        final String jobName = TestUtils.getRandomStr(7);

        String errorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(jobName)
                .selectPipelineType()
                .setCopyFrom(jobName)
                .clickOkToCreateItemErrorPage()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No such job: " + jobName);
    }

    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        CreateItemErrorPage createItemErrorPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(" ")
                .selectFreestyleProjectType()
                .clickOkToCreateItemErrorPage();

        Assert.assertEquals(createItemErrorPage.getErrorHeader(), "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters =
                List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem();

        for (Character character : incorrectNameCharacters) {
            newItemPage.clearItemName()
                    .setItemName(String.valueOf(character))
                    .selectFreestyleProjectType();

            Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), String.format("» ‘%s’ is an unsafe character", character));
        }
    }

    @Test
    public void testCreateNewFreestyleProjectWithDuplicateName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), PROJECT_NAME);

        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleProjectType()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualResult, String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .selectFreestyleProjectType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = "view/all/createItem";
        final String expectedTextOfError = "A problem occurred while processing the request.";

        CreateItemErrorPage errorPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(getRandomStr(256))
                .selectFreestyleProjectType()
                .clickOkToCreateItemErrorPage();

        Assert.assertTrue(errorPage.getPageUrl().endsWith(expectedURL));
        Assert.assertTrue(errorPage.isErrorPictureDisplayed());
        Assert.assertEquals(errorPage.getErrorDescription(), expectedTextOfError);
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithDescription() {
        final String nameMCP = "MultiConfigProject000302";
        final String descriptionMCP = "Description000302";

        MultiConfigurationProjectStatusPage multiConfigProject = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(nameMCP)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .inputDescription(descriptionMCP)
                .showPreview()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMultiConfigurationProject(nameMCP);

        MultiConfigurationProjectStatusPage multiConfigProjectPreview = new MultiConfigurationProjectStatusPage(getDriver());

        Assert.assertEquals(multiConfigProject.getNameMultiConfigProject(nameMCP), nameMCP);
        Assert.assertEquals(multiConfigProject.getDescriptionText(), descriptionMCP);
        Assert.assertEquals(multiConfigProjectPreview.getDescriptionText(), descriptionMCP);

    }

    @Test (dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testCreateNewMCProjectAsCopyFromExistingProject() {
        String actualProjectName = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobName(NEW_PROJECT_NAME);

        Assert.assertEquals(actualProjectName, NEW_PROJECT_NAME);
    }

    @Test
    public void testCreateMultibranchPipeline() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test
    public void testCreateMbPipelineEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .selectMultibranchPipelineType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateMultibranchPipelineWithExistingName() {
        String actualErrorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipelineType()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testCreateMultibranchPipelineUnsafeCharacter(Character specialCharacter, String errorMessage) {
        String actualErrorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(specialCharacter.toString())
                .selectMultibranchPipelineType()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» ‘%s’ is an unsafe character", specialCharacter));
    }

    @Test
    public void testCreateMultibranchPipelineInvalidName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("MultibranchPipeline@")
                .selectMultibranchPipelineType();

        Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), "» ‘@’ is an unsafe character");

        CreateItemErrorPage createItemErrorPage = newItemPage.clickOkToCreateItemErrorPage();

        Assert.assertEquals(createItemErrorPage.getCurrentURL(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(createItemErrorPage.getErrorHeader(),
                "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "‘@’ is an unsafe character");
    }

    @Test
    public void testCreateOrgFolder() {
        List<String> allFolders = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(allFolders.contains(PROJECT_NAME));
    }

    @Test
    public void testOrgFolderEmptyNameErr() {
        String errMessageEmptyName = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderType()
                .getItemNameRequiredMsg();

        Assert.assertEquals(errMessageEmptyName,
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateOrgFolderWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderType();

        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateOrgFolderExistName() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), PROJECT_NAME);

        String errMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderType()
                .getItemNameInvalidMsg();

        Assert.assertEquals(errMessage, "» A job already exists with the name ‘"
                + PROJECT_NAME + "’");
    }

    @Test
    public void testCreateOneItemFromListOfJobTypes() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewFolder(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestUtils.getRandomStr());

        int actualNumberOfJobs = new HomePage(getDriver())
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualNumberOfJobs, 6);
    }
}