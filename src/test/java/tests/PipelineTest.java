package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.RenameItemErrorPage;
import model.page.config.PipelineConfigPage;
import model.page.status.FolderStatusPage;
import model.page.status.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

public class PipelineTest extends BaseTest {

    @TmsLink("TaFR4Mkn")
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

    @TmsLink("hTueKlqU")
    @Owner("Denis Sebrovsky")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check if pipeline project can be enabled after disabling")
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

    @TmsLink("1ywqKe4A")
    @Owner("Dmitry Starski")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check if pipeline project can be renamed")
    @Test
    public void testRenamePipelineWithValidName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropdownMenu()
                .clearFieldAndInputNewName(TestDataUtils.PIPELINE_RENAME)
                .clickRenameButton();

        Assert.assertEquals(new PipelineStatusPage(getDriver()).getHeaderText(), "Pipeline " + TestDataUtils.PIPELINE_RENAME);
    }

    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);

        String actualJobListAsString = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.ITEM_NAME)
                .selectMyViewType()
                .clickCreateButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropdownMenu()
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
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropdownMenu()
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
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickRenamePipelineDropdownMenu()
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

    @Owner("Igor Klimenko")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check the GitHub creation button on the side menu")
    @Test
    public void testAddingGitRepository() {

        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(TestDataUtils.GITHUB_REPOSITORY_URL)
                .clickSaveButton();

        Assert.assertTrue(pipelineProjectPage.getSideMenu().isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineProjectPage.getSideMenu()
                .getAttributeGitHubSideMenu("href").contains(TestDataUtils.GITHUB_REPOSITORY_URL));
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

    @Ignore
    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testBuildParametrizedProject() {
        String consoleOutputText = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickParameterizationCheckbox()
                .clickAddParameter()
                .clickChoiceParameter()
                .setChoiceParameter("Select User", "Admin", "Guest", "User")
                .selectPipelineScriptFromScm()
                .selectScriptScm()
                .setGitHubUrl(TestDataUtils.GITHUB_REPOSITORY_URL)
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

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Configure project to be automatically built after successful building of the other project")
    @Owner("Denis Sebrovsky")
    @Test
    public void testConfigureProjectToBeBuiltAfterBuildingOtherProject() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME_2);

        String buildStatusBeforeBuild = new HomePage(getDriver())
                .getJobBuildStatus(TestDataUtils.PIPELINE_NAME_2);

        String buildStatusAfterBuild = new HomePage(getDriver())
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME_2)
                .getSideMenu()
                .clickConfigure()
                .setTriggerBuildAfterOtherProjectsAreBuilt(TestDataUtils.PIPELINE_NAME)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME)
                .clickBuildNow(TestDataUtils.PIPELINE_NAME)
                .getBreadcrumbs()
                .clickDashboard()
                .clickPipelineJob(TestDataUtils.PIPELINE_NAME_2)
                .getSideMenu()
                .getBuildStatus()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.PIPELINE_NAME_2);

        Assert.assertEquals(buildStatusBeforeBuild, "Not built");
        Assert.assertEquals(buildStatusAfterBuild, "Success");
    }
}