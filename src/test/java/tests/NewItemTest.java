package tests;

import io.qameta.allure.*;
import model.page.CreateItemErrorPage;
import model.page.HomePage;
import model.page.NewItemPage;
import model.page.status.MultiConfigurationProjectStatusPage;
import model.page.status.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;
import runner.TestUtils;
import java.util.List;
import static runner.ProjectMethodsUtils.createNewFolder;
import static runner.TestUtils.getRandomStr;

public class NewItemTest extends BaseTest {

    @TmsLink("1KVCZhYo")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Check if 'New Item' button on side menu navigates to NewItemPage")
    @Test
    public void testCheckNavigationToNewItemPage() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem();

        Assert.assertEquals(newItemPage.getH3HeaderText(), "Enter an item name");
    }

    @TmsLink("6A7BqMQ2")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Check if NewItemPage contains all six project types: Freestyle project, Pipeline, "
            + "Multi-configuration project, Folder, Multibranch Pipeline, Organization Folder")
    @Test
    public void testNewItemPageContainsSixItems() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem();

        Assert.assertEquals(newItemPage.newItemsNameList(), expectedResult);
    }

    @Owner("ViktoriyaD")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that creating NewItem with special characters is not possible")
    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testCreateNewItemWithSpecialCharacterName(Character specialCharacter, String expectedErrorMessage) {
        String errorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(String.valueOf(specialCharacter))
                .getItemNameInvalidMessage();

        Assert.assertEquals(errorMessage, String.format("» ‘%s’ is an unsafe character", specialCharacter));
    }

    @TmsLink("Lnb4Fkq3")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if a new freestyle project can be created")
    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getHeaderText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @TmsLink("Q2h9QUX4")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if a new freestyle project with only spaces in name can be created")
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

    @TmsLink("6qgwLMkK")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if a new freestyle project can be created using the name of already existing project")
    @Test
    public void testCreateNewFreestyleProjectWithDuplicateName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectType()
                .getItemNameInvalidMessage();

        Assert.assertEquals(actualResult, String.format("» A job already exists with the name ‘%s’", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @TmsLink("evK4GPAv")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if a new freestyle project with empty name can be created")
    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .selectFreestyleProjectType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMessage(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @TmsLink("1LRyBVVC")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Kate Bay")
    @Feature("Feature")
    @Description("Check if a new freestyle project with name from 256 characters can be created")
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

    @TmsLink("aqZH8gmR")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Gregory Ikhsanov")
    @Feature("Feature")
    @Description("Verify that pipeline project can be created and assert it on job page")
    @Test
    public void testCreatePipelineAssertInsideJob() {
        String actualPipelineName = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getHeaderText();

        Assert.assertEquals(actualPipelineName, "Pipeline " + TestDataUtils.PIPELINE_NAME);
    }

    @TmsLink("ar7WqxrG")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Radas Ivan")
    @Feature("Feature")
    @Description("Check if pipeline project can be created and assert it on Dashboard")
    @Test
    public void testCreatePipelineAssertOnDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        String actualPipelineName = new HomePage(getDriver())
                .getJobName(TestDataUtils.PIPELINE_NAME);

        Assert.assertEquals(actualPipelineName, TestDataUtils.PIPELINE_NAME);
    }

    @TmsLink("KK6a5QQO")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Anna Fedorova")
    @Feature("Feature")
    @Description("Verify if pipeline project can be created and assert it on breadcrumbs menu")
    @Test
    public void testCreatePipelineAssertOnBreadcrumbs() {
        String actualTextOnBreadcrumbs = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .getTextBreadcrumbs();

        Assert.assertTrue(actualTextOnBreadcrumbs.contains(TestDataUtils.PIPELINE_NAME), TestDataUtils.PIPELINE_NAME + " Pipeline Not Found On Breadcrumbs");
    }

    @TmsLink("m30ZGnDl")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Anna Fedorova")
    @Feature("Feature")
    @Description("Verify if pipeline project with description can be created")
    @Test
    public void testCreateNewPipelineWithDescription() {
        String actualPipelineDescription = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .setDescriptionField(TestDataUtils.NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualPipelineDescription, TestDataUtils.NEW_DESCRIPTION);
    }

    @TmsLink("Ew3JXeZc")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Anna Fedorova")
    @Feature("Feature")
    @Description("Check if pipeline project can be created as a copy of existing pipeline project")
    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        PipelineStatusPage pipelineStatusPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME_2)
                .selectPipelineType()
                .setCopyFromItemName(TestDataUtils.PIPELINE_NAME)
                .clickOkButton()
                .clickSaveButton();

        Assert.assertEquals(pipelineStatusPage.getPipelineName(), TestDataUtils.PIPELINE_NAME_2);
        Assert.assertEquals(pipelineStatusPage.getDescriptionText(), TestDataUtils.NEW_DESCRIPTION);
    }

    @TmsLink("v7X8N5Xr")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Pavel Kartavenko")
    @Feature("Feature")
    @Description("Check if pipeline job can be created without name")
    @Test
    public void testCreateNewItemTypePipelineWithEmptyName() {
        final String nameNewItemTypePipeline = "";
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(nameNewItemTypePipeline)
                .selectPipelineType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMessage(), "» This field cannot be empty, please enter a valid name");
    }

    @TmsLink("Jn2hOYQL")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Gregory Ikhsanov")
    @Feature("Feature")
    @Description("Check if pipeline job can be created with name, that is a name of already existing project")
    @Test
    public void testCreatePipelineExistingNameError() {
        String newItemPageErrorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .getItemNameInvalidMessage();

        Assert.assertEquals(newItemPageErrorMessage, (String.format("» A job already exists with the name ‘%s’", TestDataUtils.PIPELINE_NAME)));
    }

    @TmsLink("gwJ1umdK")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Arailym Ashimova")
    @Feature("Feature")
    @Description("Check if pipeline job is displayed on MyViewsPage")
    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {
        String pipelineNameInMyViewList = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .getListProjectsNamesAsString();

        Assert.assertTrue(pipelineNameInMyViewList.contains(TestDataUtils.PIPELINE_NAME), TestDataUtils.PIPELINE_NAME + " Pipeline not found");
    }

    @TmsLink("rgiXKWKB")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Igor Klimenko")
    @Feature("Feature")
    @Description("Check if pipeline can be created from another project using not existing name")
    @Test
    public void testCreateNewItemFromOtherNonExistingName() {
        createNewFolder(getDriver(), TestDataUtils.PROJECT_NAME);

        String errorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .setCopyFrom(TestDataUtils.PIPELINE_NAME)
                .clickOkToCreateItemErrorPage()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "No such job: " + TestDataUtils.PIPELINE_NAME);
    }

    @TmsLink("g4SSpzfi")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Anastasia Yakimova")
    @Feature("Feature")
    @Description("Check if multi-configuration project can be created with valid name")
    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @TmsLink("mSxZf8qt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if multi-configuration project can be created with description")
    @Test
    public void testCreateMultiConfigurationProjectWithDescription() {

        MultiConfigurationProjectStatusPage multiConfigProject = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .inputDescription(TestDataUtils.DESCRIPTION)
                .showPreview()
                .clickSaveButton();

        Assert.assertEquals(multiConfigProject.getNameMultiConfigProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME),
                TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);
        Assert.assertEquals(multiConfigProject.getDescriptionText(), TestDataUtils.DESCRIPTION);
    }

    @TmsLink("iTlY9aBK")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Anastasia Yakimova")
    @Feature("Feature")
    @Description("Check if multi-configuration project can be created as a copy of existing project with description")
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithDescription")
    public void testCreateNewMCProjectAsCopyFromExistingProject() {
        String actualDescription = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.NEW_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .setCopyFromItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickOkButton()
                .clickSaveButton()
                .getDescriptionText();

        String actualJobName = new MultiConfigurationProjectStatusPage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobName(TestDataUtils.NEW_PROJECT_NAME);

        Assert.assertEquals(actualDescription, TestDataUtils.DESCRIPTION);
        Assert.assertEquals(actualJobName, TestDataUtils.NEW_PROJECT_NAME);
    }

    @TmsLink("dhH00tqj")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Evgeniya Piskunova")
    @Feature("Feature")
    @Description("Check if a new folder can be created")
    @Test
    public void testCreateFolder() {

        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FOLDER_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickApplyButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getJobName(TestDataUtils.FOLDER_NAME), TestDataUtils.FOLDER_NAME);
    }

    @TmsLink("8e0E87Yp")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if a new multibranch pipeline can be created")
    @Test
    public void testCreateMultibranchPipeline() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(TestDataUtils.MULTIBRANCH_PIPELINE_NAME));
    }

    @TmsLink("O0BB7UOH")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Julia Dmitrieva")
    @Feature("Feature")
    @Description("Check if multibranch pipeline with empty name can be created")
    @Test
    public void testCreateMultibranchPipelineEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .selectMultibranchPipelineType();

        Assert.assertEquals(newItemPage.getItemNameRequiredMessage(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @TmsLink("ods5Hnvj")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Julia Dmitrieva")
    @Feature("Feature")
    @Description("Check if multibranch pipeline can be created with name, that is a name of already existing project")
    @Test
    public void testCreateMultibranchPipelineWithExistingName() {
        String actualErrorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineType()
                .getItemNameInvalidMessage();

        Assert.assertEquals(actualErrorMessage, String.format("» A job already exists with the name ‘%s’", TestDataUtils.MULTIBRANCH_PIPELINE_NAME));
    }

    @TmsLink("DEQVVZKR")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Julia Dmitrieva")
    @Feature("Feature")
    @Description("Check if multibranch pipeline can be created with unsafe characters in name")
    @Test
    public void testCreateMultibranchPipelineInvalidName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("MultibranchPipeline@")
                .selectMultibranchPipelineType();

        Assert.assertEquals(newItemPage.getItemNameInvalidMessage(), "» ‘@’ is an unsafe character");

        CreateItemErrorPage createItemErrorPage = newItemPage.clickOkToCreateItemErrorPage();

        Assert.assertEquals(createItemErrorPage.getCurrentURL(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(createItemErrorPage.getErrorHeader(),
                "Error");
        Assert.assertEquals(createItemErrorPage.getErrorMessage(), "‘@’ is an unsafe character");
    }

    @TmsLink("9exZkDCt")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Nadia Ludanik")
    @Feature("Feature")
    @Description("Verify that organization folder can be created")
    @Test
    public void testCreateOrgFolder() {
        List<String> allFolders = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(allFolders.contains(TestDataUtils.ORGANIZATION_FOLDER_NAME));
    }

    @TmsLink("rKiSToXZ")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Elena Grueber")
    @Feature("Feature")
    @Description("Verify that organization folder can be created with empty name")
    @Test
    public void testOrgFolderEmptyNameError() {
        String errMessageEmptyName = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderType()
                .getItemNameRequiredMessage();

        Assert.assertEquals(errMessageEmptyName,
                "» This field cannot be empty, please enter a valid name");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Check if organization folder can be created with empty name")
    @Test
    public void testCreateOrgFolderWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderType();

        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @TmsLink("kPVnKO87")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Elena Grueber")
    @Feature("Feature")
    @Description("Verify that organization folder can be created with name, that is a name of other existing project")
    @Test
    public void testCreateOrgFolderWithExistingName() {
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        String errorMessage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.ORGANIZATION_FOLDER_NAME)
                .selectFolderType()
                .getItemNameInvalidMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘"
                + TestDataUtils.ORGANIZATION_FOLDER_NAME + "’");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Feature")
    @Description("Verify that all six different job types can be created")
    @Test
    public void testCreateOneItemFromListOfJobTypes() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);

        int actualNumberOfJobs = new HomePage(getDriver())
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualNumberOfJobs, 6);
    }
}