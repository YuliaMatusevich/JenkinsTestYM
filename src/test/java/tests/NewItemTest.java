package tests;

import model.CreateItemErrorPage;
import model.HomePage;
import model.NewItemPage;
import model.freestyle.FreestyleProjectConfigPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import model.organization_folder.OrgFolderConfigPage;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
                .getSideMenuFrame()
                .clickNewItem();

        Assert.assertEquals(newItemPage.newItemsNameList(), expectedResult);
    }

    @Test
    public void testCheckNavigationToNewItemPage() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem();

        Assert.assertEquals(newItemPage.getH3HeaderText(), "Enter an item name");
    }

    @Test
    public void testCreateFolder() {

        HomePage homePage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderAndClickOk()
                .clickApplyButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getJobName(PROJECT_NAME), PROJECT_NAME);
    }

    @DataProvider(name = "typeProjectList")
    private Object[][] getTypeProjectList() {
        return Stream.<Consumer<NewItemPage>>of(
                NewItemPage::selectFreestyleProjectAndClickOk,
                NewItemPage::selectPipelineAndClickOk,
                NewItemPage::selectMultiConfigurationProjectAndClickOk,
                NewItemPage::selectFolderAndClickOk,
                NewItemPage::selectMultibranchPipelineAndClickOk,
                NewItemPage::selectOrgFolderAndClickOk
        ).map(func -> new Object[]{func}).toArray(Object[][]::new);
    }

    @Test(dataProvider = "typeProjectList")
    public void testCreateAnyItemWithSpacesOnlyNameError(Consumer<NewItemPage> typeProjectMethod) {
        typeProjectMethod.accept(
                new HomePage(getDriver())
                        .getSideMenuFrame()
                        .clickNewItem()
                        .setItemName("     "));

        Assert.assertEquals(
                new CreateItemErrorPage(getDriver()).getErrorMessage(),
                "No name is specified");
    }

    @Test
    public void testCreatePipelineAssertInsideJob() {
        String actualPipelineName = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
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
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .getTextBreadcrumbs();

        Assert.assertTrue(actualTextOnBreadcrumbs.contains(PROJECT_NAME), PROJECT_NAME + " Pipeline Not Found On Breadcrumbs");
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        String actualPipelineDescription = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualPipelineDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        String actualJobName = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOk()
                .clickSaveButton()
                .getPipelineName();

        String actualDescription = new PipelineStatusPage(getDriver()).getDescriptionText();

        Assert.assertEquals(actualJobName, NEW_PROJECT_NAME);
        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test
    public void testCreateItemsWithoutName() {
        int itemsListSize = new HomePage((getDriver()))
                .getSideMenuFrame()
                .clickNewItem()
                .getItemsListSize();

        for (int i = 0; i < itemsListSize; i++) {
            String actualErrorMessage = new NewItemPage((getDriver()))
                    .getBreadcrumbs()
                    .clickDashboard()
                    .getSideMenuFrame()
                    .clickNewItem()
                    .setItem(i)
                    .getItemNameRequiredMsg();

            Assert.assertEquals(actualErrorMessage, "» This field cannot be empty, please enter a valid name");
        }
    }

    @Test
    public void testCreateNewItemWithUnsafeCharacterName() {
        final String nameNewItem = "5%^PiPl$^Ne)";
        String errorMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(nameNewItem)
                .getItemNameInvalidMsg();

        Assert.assertEquals(errorMessage, "» ‘%’ is an unsafe character");
    }

    @Test
    public void testCreateNewItemTypePipelineWithEmptyName() {
        final String nameNewItemTypePipeline = "";
        PipelineConfigPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(nameNewItemTypePipeline)
                .selectPipelineAndClickOk();

        Assert.assertEquals(new NewItemPage(getDriver()).getItemNameRequiredMsg(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        String newItemPageErrorMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(newItemPageErrorMessage, (String.format("» A job already exists with the name ‘%s’", PROJECT_NAME)));
    }

    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {
        String pipelineNameInMyViewList = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink().getListProjectsNamesAsString();

        Assert.assertTrue(pipelineNameInMyViewList.contains(PROJECT_NAME), PROJECT_NAME + " Pipeline not found");
    }

    @Test
    public void testCreateNewItemFromOtherNonExistingName() {
        createNewFolder(getDriver(), PROJECT_NAME);
        final String jobName = TestUtils.getRandomStr(7);

        String errorMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(jobName)
                .selectPipeline()
                .setCopyFrom(jobName)
                .clickOkButton()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No such job: " + jobName);
    }

    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(" ")
                .selectFreestyleProjectAndClickOk();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Error");
        Assert.assertEquals(freestyleProjectConfigPage.getErrorMsg(), "No name is specified");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithSpacesInsteadOfName")
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters =
                List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem();

        for (Character character : incorrectNameCharacters) {
            newItemPage.clearItemName()
                    .setItemName(String.valueOf(character))
                    .selectFreestyleProject();

            Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), String.format("» ‘%s’ is an unsafe character", character));
        }
    }

    @Test
    public void testCreateNewFreestyleProjectWithDuplicateName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), PROJECT_NAME);

        String actualResult = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualResult, String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .selectFreestyleProject();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = "view/all/createItem";
        final String expectedTextOfError = "A problem occurred while processing the request.";

        CreateItemErrorPage errorPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(getRandomStr(256))
                .selectFreestyleProjectAndClickOkWithError();

        Assert.assertTrue(errorPage.getPageUrl().endsWith(expectedURL));
        Assert.assertTrue(errorPage.isErrorPictureDisplayed());
        Assert.assertEquals(errorPage.getErrorDescription(), expectedTextOfError);
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
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
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(nameMCP)
                .selectMultiConfigurationProjectAndClickOk()
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
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOK()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobName(NEW_PROJECT_NAME);

        Assert.assertEquals(actualProjectName, NEW_PROJECT_NAME);
    }

    @Test
    public void testCreateMultibranchPipeline() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test
    public void testCreateMbPipelineEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateMultibranchPipelineWithExistingName() {
        String actualErrorMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultibranchPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'$'}, {'<'}, {'>'},};
    }

    @Test(dataProvider = "specialCharacters")
    public void testCreateMultibranchPipelineUnsafeCharacter(Character unsafeCharacter) {
        String actualErrorMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(unsafeCharacter.toString())
                .selectMultibranchPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualErrorMessage, String.format("» ‘%s’ is an unsafe character", unsafeCharacter));
    }

    @Test
    public void testCreateMultibranchPipelineInvalidName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName("MultibranchPipeline@")
                .selectMultibranchPipeline();

        Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), "» ‘@’ is an unsafe character");

        CreateItemErrorPage createItemErrorPage = newItemPage.clickOkButton();

        Assert.assertEquals(createItemErrorPage.getCurrentURL(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(createItemErrorPage.getErrorHeader(),
                "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "‘@’ is an unsafe character");
    }

    @Test
    public void testCreateOrgFolder() {
        List<String> allFolders = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(allFolders.contains(PROJECT_NAME));
    }

    @Test
    public void testOrgFolderEmptyNameErr() {
        String errMessageEmptyName = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderAndClickOk()
                .getErrorMessageEmptyField();

        Assert.assertEquals(errMessageEmptyName,
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateOrgFolderWithEmptyName() {
        OrgFolderConfigPage orgFolderConfigPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderAndClickOk();

        Assert.assertFalse(orgFolderConfigPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateOrgFolderExistName() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), PROJECT_NAME);

        String errMessage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectExistFolderAndClickOk()
                .getErrorMessage();

        Assert.assertEquals(errMessage, "A job already exists with the name ‘"
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