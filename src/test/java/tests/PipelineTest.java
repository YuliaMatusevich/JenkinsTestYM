package tests;

import model.*;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import java.util.List;

import static runner.TestUtils.getRandomStr;

public class PipelineTest extends BaseTest {
    private static final String PIPELINE_RENAME = getRandomStr() + "renamed";
    private static final String PIPELINE_NAME = getRandomStr();
    private static final String ITEM_NAME = getRandomStr();
    private static final String DESCRIPTION = getRandomStr() + " description";
    private static final String NEW_DESCRIPTION = "New description";

    @Test
    public void testDisablePipelineProjectMessage() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String actualMessageDisabledProject = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickDisableProject()
                .getMessageDisabledProject();

        Assert.assertEquals(actualMessageDisabledProject, "This project is currently disabled");
    }

    @Test(dependsOnMethods = "testDisablePipelineProjectMessage")
    public void testEnablePipelineProject() {
        String jobStatusAfterEnable = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickEnableProject()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus();

        Assert.assertNotEquals(jobStatusAfterEnable, "Disabled");
    }

    @Test
    public void testRenamePipelineWithValidName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_RENAME)
                .clickRenameButton();

        Assert.assertEquals(new PipelineStatusPage(getDriver()).getNameText(), "Pipeline " + PIPELINE_RENAME);
    }

    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        String actualJobListAsString = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(ITEM_NAME)
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickView(ITEM_NAME)
                .getJobListAsString();

        Assert.assertTrue(actualJobListAsString.contains(PIPELINE_RENAME), PIPELINE_RENAME + " Pipeline not found");
    }

    @Test
    public void testRenamePipelineWithoutChangingName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clickSaveButtonAndGetError();

        Assert.assertEquals(renameItemErrorPage.getHeadErrorMessage(), "Error");
        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "The new name is the same as the current name.");
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'&', "&amp;"}, {'>', "&gt;"}, {'<', "&lt;"}, {'!', "!"}, {'@', "@"}, {'#', "#"},
                {'$', "$"}, {'%', "%"}, {'^', "^"}, {'*', "*"}, {'[', "["}, {']', "]"}, {'\\', "\\"}, {'|', "|"},
                {';', ";"}, {':', ":"}, {'/', "/"}, {'?', "?"},};
    }

    @Test(dataProvider = "specialCharacters")
    public void testRenamePipelineUsingSpecialCharacter(Character unsafeCharacter, String expectedUnsafeCharacterInErrorMessage) {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        String actualRenameErrorMessage = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_NAME + unsafeCharacter)
                .clickSaveButtonAndGetError()
                .getErrorMessage();

        Assert.assertEquals(actualRenameErrorMessage, String.format("‘%s’ is an unsafe character", expectedUnsafeCharacterInErrorMessage));
    }

    @Test
    public void testPipelinePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(DESCRIPTION)
                .clickPreviewLink();

        Assert.assertEquals(pipelineConfigPage.getTextareaPreview(), DESCRIPTION);
    }

    @Test
    public void testPipelineHidePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(DESCRIPTION)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(pipelineConfigPage.isDisplayedPreviewTextDescription());
    }

    @Test
    public void testDeletePipelineFromDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String homePageHeaderText = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeletePipeline()
                .getHeaderText();

        Assert.assertEquals(homePageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(gitHubRepo)
                .clickSaveButton();

        Assert.assertTrue(pipelineProjectPage.isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineProjectPage.getAttributeGitHubSideMenu("href").contains(gitHubRepo));
    }

    @Test
    public void testWarningMessageIsDisappeared() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String emptyErrorArea = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickConfigureTools()
                .clickFirstAddMavenButton()
                .setFirstMavenTitleField("Maven")
                .clickApplyButton()
                .getErrorAreaText();

        Assert.assertEquals(emptyErrorArea, "");
    }

    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testBuildParametrizedProject() {
        String consoleOutputText = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickParameterizationCheckbox()
                .clickAddParameter()
                .clickChoiceParameter()
                .setChoiceParameter("Select User", "Admin", "Guest", "User")
                .selectPipelineScriptFromScm()
                .selectScriptScm()
                .setGitHubUrl("https://github.com/patriotby07/simple-maven-project-with-tests")
                .clickSaveButton()
                .clickBuildWithParameters()
                .selectParameterByText("Guest")
                .clickBuildButton()
                .clickLastBuildLink()
                .clickConsoleOutput()
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutputText.contains("BUILD SUCCESS"));
        Assert.assertTrue(consoleOutputText.contains("Finished: SUCCESS"));
    }

    @Test
    public void testPipelineAddDescription() {
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .editDescription(DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(pipelineProjectPage.getDescriptionText(), DESCRIPTION);
    }

    @Test
    public void testAddDescriptionInExistPipeline() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String actualDescription = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickConfigure()
                .setDescriptionField(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testPipelineAddDescription")
    public void testEditPipelineDescription() {

        String actualDescription = new HomePage(getDriver())
                .clickPipelineProjectName()
                .editDescription(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, NEW_DESCRIPTION);
    }

    @Test
    public void testPipelineSideMenuLinks() {
        List<String> expectedResult = List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline",
                "Full Stage View", "Rename", "Pipeline Syntax");

        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        List<String> pipelineSideMenuOptionsLinks = new HomePage(getDriver())
                .clickPipelineProjectName()
                .getPipelineSideMenuLinks();

        Assert.assertEquals(pipelineSideMenuOptionsLinks, expectedResult);
    }

    @Test
    public void testBuildNewPipeline() {
        final String expectedLastSuccess = "N/A";

        String actualSuccessText = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToEndPipelineConfigPage()
                .clickTrySamplePipelineDropDownMenu()
                .clickHelloWorld()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getLastSuccessText(PIPELINE_NAME);

        Assert.assertEquals(actualSuccessText, expectedLastSuccess);
    }

    @Test(dependsOnMethods = "testBuildNewPipeline")
    public void testBuildNewPipelineSuccess() {
        final String expectedCheckIcon = "Success";

        String actualCheckIcon = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickBuildNow(PIPELINE_NAME)
                .getBreadcrumbs()
                .clickDashboard()
                .movePointToCheckBox()
                .getStatusBuildText();

        Assert.assertEquals(actualCheckIcon, expectedCheckIcon);
    }
}