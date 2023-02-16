package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.*;
import model.config_pages.PipelineConfigPage;
import model.status_pages.PipelineStatusPage;
import model.views.EditMyViewPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

public class PipelineTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check if pipeline project can be disabled")
    @Test
    public void testDisablePipelineProjectMessage() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        String actualMessageDisabledProject = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .clickDisableProject()
                .getMessageDisabledProject();

        Assert.assertEquals(actualMessageDisabledProject, "This project is currently disabled");
    }

    @Test(dependsOnMethods = "testDisablePipelineProjectMessage")
    public void testEnablePipelineProject() {
        String jobStatusAfterEnable = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .clickEnableProject()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus();

        Assert.assertNotEquals(jobStatusAfterEnable, "Disabled");
    }

    @Test
    public void testRenamePipelineWithValidName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.PIPELINE_RENAME)
                .clickRenameButton();

        Assert.assertEquals(new PipelineStatusPage(getDriver()).getNameText(), "Pipeline " + TestDataUtils.PIPELINE_RENAME);
    }

    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);

        String actualJobListAsString = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.ITEM_NAME)
                .selectMyView()
                .clickCreateButton(new EditMyViewPage(getDriver()))
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.PIPELINE_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.ITEM_NAME)
                .getJobListAsString();

        Assert.assertTrue(actualJobListAsString.contains(TestDataUtils.PIPELINE_RENAME), TestDataUtils.PIPELINE_RENAME + " Pipeline not found");
    }

    @Test
    public void testRenamePipelineWithoutChangingName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);

        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clickRenameButtonWithInvalidData();

        Assert.assertEquals(renameItemErrorPage.getHeadErrorMessage(), "Error");
        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "The new name is the same as the current name.");
    }

    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testRenamePipelineUsingSpecialCharacter(Character specialCharacter, String expectedErrorMessage) {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);

        String actualRenameErrorMessage = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.PIPELINE_NAME + specialCharacter)
                .clickRenameButtonWithInvalidData()
                .getErrorMessage();

        Assert.assertEquals(actualRenameErrorMessage, String.format("‘%s’ is an unsafe character", expectedErrorMessage));
    }

    @Test
    public void testPipelinePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .setDescriptionField(TestDataUtils.DESCRIPTION)
                .clickPreviewLink();

        Assert.assertEquals(pipelineConfigPage.getTextareaPreview(), TestDataUtils.DESCRIPTION);
    }

    @Test
    public void testPipelineHidePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .setDescriptionField(TestDataUtils.DESCRIPTION)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(pipelineConfigPage.isDisplayedPreviewTextDescription());
    }

    @Test
    public void testDeletePipelineFromDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        String homePageHeaderText = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeletePipeline()
                .getHeaderText();

        Assert.assertEquals(homePageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(gitHubRepo)
                .clickSaveButton();

        Assert.assertTrue(pipelineProjectPage.getSideMenu().isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineProjectPage.getSideMenu().getAttributeGitHubSideMenu("href").contains(gitHubRepo));
    }

    @Test
    public void testWarningMessageIsDisappeared() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        String emptyErrorArea = new HomePage(getDriver())
                .getSideMenu()
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
                .clickJobDropDownMenu(TestDataUtils.PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickParameterizationCheckbox()
                .clickAddParameter()
                .clickChoiceParameter()
                .setChoiceParameter("Select User", "Admin", "Guest", "User")
                .selectPipelineScriptFromScm()
                .selectScriptScm()
                .setGitHubUrl("https://github.com/patriotby07/simple-maven-project-with-tests")
                .clickSaveButton()
                .getSideMenu()
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
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .editDescription(TestDataUtils.DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(pipelineProjectPage.getDescriptionText(), TestDataUtils.DESCRIPTION);
    }

    @Test
    public void testAddDescriptionInExistPipeline() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        String actualDescription = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .getSideMenu()
                .clickConfigure()
                .setDescriptionField(TestDataUtils.NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, TestDataUtils.NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testPipelineAddDescription")
    public void testEditPipelineDescription() {

        String actualDescription = new HomePage(getDriver())
                .clickPipelineProjectName()
                .editDescription(TestDataUtils.NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, TestDataUtils.NEW_DESCRIPTION);
    }

    @Test
    public void testPipelineSideMenuLinks() {
        List<String> expectedResult = List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline",
                "Full Stage View", "Rename", "Pipeline Syntax");

        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        List<String> pipelineSideMenuOptionsLinks = new HomePage(getDriver())
                .clickPipelineProjectName()
                .getSideMenu()
                .getPipelineSideMenuLinks();

        Assert.assertEquals(pipelineSideMenuOptionsLinks, expectedResult);
    }

    @Test
    public void testBuildNewPipeline() {
        final String expectedLastSuccess = "N/A";

        String actualSuccessText = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.PIPELINE_NAME)
                .selectPipelineType()
                .clickOkButton()
                .scrollToEndPipelineConfigPage()
                .clickTrySamplePipelineDropDownMenu()
                .clickHelloWorld()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getLastSuccessText(TestDataUtils.PIPELINE_NAME);

        Assert.assertEquals(actualSuccessText, expectedLastSuccess);
    }

    @Test(dependsOnMethods = "testBuildNewPipeline")
    public void testBuildNewPipelineSuccess() {
        final String expectedCheckIcon = "Success";

        String actualCheckIcon = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .clickBuildNow(TestDataUtils.PIPELINE_NAME)
                .getBreadcrumbs()
                .clickDashboard()
                .movePointToCheckBox()
                .getStatusBuildText();

        Assert.assertEquals(actualCheckIcon, expectedCheckIcon);
    }
}