package tests;

import io.qameta.allure.*;
import model.page.BuildWithParametersPage;
import model.page.ChangesBuildsPage;
import model.page.HomePage;
import model.page.RenameItemErrorPage;
import model.page.config.FreestyleProjectConfigPage;
import model.page.status.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;
import runner.TestUtils;

import java.util.*;

import static runner.TestUtils.*;

public class FreestyleProjectTest extends BaseTest {

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if Project status contains expected text when 'Disable Project' option has been applied")
    @Test
    public void testDisableProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickDisableProjectBtn();

        Assert.assertEquals(freestyleProjectStatusPage.getHeaderText(), String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(freestyleProjectStatusPage.getWarningMsg(), "This project is currently disabled");

        HomePage homePage = freestyleProjectStatusPage.getBreadcrumbs().clickDashboard();
        Assert.assertEquals(homePage.getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME), "Disabled");
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if the Project status icon displayed an expected status when 'Disable Project' option has been applied")
    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        final String jobStatusIconTooltip = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickDisableProjectBtn()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if expected Project is opened from Dashboard, appropriate Status page contains an expected text")
    @Test
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        final FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(freestyleProjectStatusPage.getHeaderText(), String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify if expected description of the project has been added")
    @Test
    public void testAddDescription() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        final String descriptionText = "This is job #" + TestDataUtils.FREESTYLE_PROJECT_NAME;

        String freestyleProjectDescription = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickAddOrEditDescription()
                .inputAndSaveDescriptionText(descriptionText)
                .getDescriptionText();

        Assert.assertEquals(freestyleProjectDescription, descriptionText);
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify if expected description of the project has been updated")
    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String newDescription = "It's new description to job";

        FreestyleProjectStatusPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonEditDescription()
                .inputAndSaveDescriptionText(newDescription);

        Assert.assertEquals(page.getDescriptionText(), newDescription);
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("Verify if Changes Builds Page contains expected text")
    @Test
    public void testNoBuildFreestyleProjectChanges() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        ChangesBuildsPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickChanges();

        Assert.assertEquals(page.getPageText(), "Changes\nNo builds.");
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if the list of projects' names on Dashboard contains expected Project name." +
            "Thus proving that the project renamed successfully.")
    @Test
    public void testRenameFreestyleProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        List<String> jobsList = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.FREESTYLE_PROJECT_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertFalse(jobsList.contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
        Assert.assertTrue(jobsList.contains(TestDataUtils.FREESTYLE_PROJECT_RENAME));
    }

    @Owner("Viktoriya D")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if error message arrears and contains expected text when attempting to rename an existing project with invalid data")
    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testRenameFreestyleProjectToIncorrectViaSideMenu(Character specialCharacter, String expectedErrorMessage) {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.FREESTYLE_PROJECT_NAME + specialCharacter)
                .clickRenameButtonWithInvalidData();

        Assert.assertEquals(renameItemErrorPage.getHeadErrorMessage(), "Error");
        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), String.format("‘%s’ is an unsafe character", expectedErrorMessage));
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if Project name link on the Dashboard forwards to appropriate Project page")
    @Test
    public void testViewFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String freestyleName = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getHeaderText();

        Assert.assertEquals(freestyleName, String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if 'Configure' menu in the Project dropdown menu on the Dashboard leads to 'Configuration' page")
    @Test
    public void testFreestyleProjectConfigureByDropdown() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickConfigDropDownMenuFreestyle();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Configuration");
    }

    @Owner("MaksPt")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if the build displayed in the 'Build History' section on the side menu " +
            "by comparing number of builds before and after current build")
    @Test
    public void testCreateBuildNowOnFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        int countBuildsBeforeCreatingNewBuild = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .openBuildHistory()
                .getSideMenu()
                .countBuilds();
        int countBuildsAfterCreatingNewBuild = new FreestyleProjectStatusPage(getDriver())
                .getSideMenu()
                .clickBuildNowAndWaitSuccessStatus()
                .getSideMenu()
                .countBuilds();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Owner("Anastasia Yakimova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify deletion of the project using side menu")
    @Test
    public void testDeleteFreestyleProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String pageHeaderText = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeleteProject()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Owner("olpolezhaeva")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if all expected lines of the side menu are displayed ")
    @Test
    public void testFreestyleConfigSideMenu() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        final Set<String> expectedFreestyleConfigSideMenu = new TreeSet<>(
                List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        Set<String> actualFreestyleConfigSideMenu = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickConfigure()
                .collectConfigSideMenu();

        Assert.assertEquals(actualFreestyleConfigSideMenu, expectedFreestyleConfigSideMenu);
    }

    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that expected parameters are saved and displayed after build is completed ")
    @Test
    public void testConfigureJobAsParameterized() {
        final String stringParameterName = "Held post";
        final String stringParameterDefaultValue = "Manager";
        final String choiceParameterName = "Employee_name";
        final String choiceParameterValues = "John Smith\nJane Dow";
        final String booleanParameterName = "Employed";
        final String descriptionText = "This build requires parameters:";

        BuildWithParametersPage<FreestyleProjectStatusPage> page = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectType()
                .clickOkButton()
                .switchONCheckBoxThisProjectIsParametrized()
                .clickButtonAddParameter()
                .selectStringParameter()
                .inputStringParameterName(stringParameterName)
                .inputStringParameterDefaultValue(stringParameterDefaultValue)
                .scrollAndClickAddParameterButton()
                .selectChoiceParameter()
                .inputChoiceParameterName(choiceParameterName)
                .inputChoiceParameterValue(choiceParameterValues)
                .scrollAndClickAddParameterButton()
                .selectBooleanParameter()
                .inputBooleanParameterName(booleanParameterName)
                .switchONBooleanParameterAsDefault()
                .clickSaveButton()
                .getSideMenu()
                .clickBuildWithParameters();

        Assert.assertTrue(page.getNameText().contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(page.getDescriptionText(), descriptionText);
        Assert.assertEquals(page.getNthParameterName(1), stringParameterName);
        Assert.assertEquals(page.getNthParameterValue(1), stringParameterDefaultValue);
        Assert.assertEquals(page.getNthParameterName(2), choiceParameterName);
        Assert.assertEquals(page.getSelectParametersValues(), "John Smith\nJane Dow");
        Assert.assertEquals(page.getNthParameterName(3), booleanParameterName);
        Assert.assertTrue(page.isBooleanParameterSetByDefault());
    }


    @Owner("AlekseiChapaev")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify if project sourced by GitHub successfully built")
    @Test(dependsOnMethods = "testConfigureJobAsParameterized")
    public void testConfigureSourceCodeByGIT() {

        HomePage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickConfigure()
                .switchOFFCheckBoxThisProjectIsParametrized()
                .clickLinkSourceCodeManagement()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(TestDataUtils.GITHUB_REPOSITORY_URL)
                .inputBranchSpecifier(TestDataUtils.BRANCH_SPECIFIER)
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndRedirectToDashboardAfterBuildCompleted();

        Assert.assertEquals(page.getJobBuildStatus(), "Success");
        Assert.assertNotEquals(page.getBuildDurationTime(), "N/A");
    }

    @Owner("Anastasia Yakimova")
    @Severity(SeverityLevel.MINOR)
    @Feature("Function")
    @Description("Verify if 'Days to keep builds' and 'Max # of builds to keep' settings of 'Discard old builds' are displayed upon saved")
    @Test
    public void testCheckFieldsDaysAndMaxNumberOfBuildsToKeepInConfigure() {
        final String expectedDaysToKeepBuilds = Integer.toString((int) (Math.random() * 20 + 1));
        final String expectedMaxNumberOfBuildsToKeep = Integer.toString((int) (Math.random() * 20 + 1));
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .clickDiscardOldBuildsCheckbox()
                .typeDaysToKeepBuilds(expectedDaysToKeepBuilds)
                .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                .clickSaveButton()
                .getSideMenu()
                .clickConfigure();

        Assert.assertEquals(freestyleProjectConfigPage.getNumberOfDaysToKeepBuilds(), expectedDaysToKeepBuilds);
        Assert.assertEquals(freestyleProjectConfigPage.getMaxNumberOfBuildsToKeep(), expectedMaxNumberOfBuildsToKeep);
    }

    @Owner("Anastasia Yakimova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if all expected options are displayed in the 'Add build steps' dropdown of the 'Build Steps' section")
    @Test
    public void testBuildStepsOptions() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        final Set<String> expectedOptionsInBuildStepsSection = new HashSet<>(List.of("Execute Windows batch command",
                "Execute shell", "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets",
                "Run with timeout", "Set build status to \"pending\" on GitHub commit"));

        Set<String> actualOptionsInBuildStepsSection = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .openAddBuildStepDropDown()
                .getOptionsInBuildStepsDropDown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Owner("Anastasia Yakimova")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify if 'Build Periodically' checkbox in the 'Build Triggers' section is checked")
    @Test
    public void testSelectBuildPeriodicallyCheckbox() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        boolean selectedCheckbox = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .clickBuildTriggersSideMenuOption()
                .scrollAndClickBuildPeriodicallyCheckbox()
                .verifyThatBuildPeriodicallyCheckboxIsSelected();

        Assert.assertTrue(selectedCheckbox);
    }

    @Owner("Yulia Matusevich")
    @Severity(SeverityLevel.MINOR)
    @Feature("Function")
    @Description("Verify build time displayed in the 'Build History' section of the side menu s the same as system date and time")
    @Flaky
    @Test
    public void testFreestyleProjectBuildDateAndTime() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String actualBuildDateTime = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickBuildNowAndWaitSuccessStatus()
                .getSideMenu()
                .getBuildDateTime();

        Assert.assertTrue(actualBuildDateTime.contains(currentDate()));
        Assert.assertTrue(actualBuildDateTime.contains(currentTime()));
        Assert.assertTrue(actualBuildDateTime.contains(currentDayPeriod()));
    }

    @Owner("Liudmila Plucci")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Test if Console Output page  log contains 'T E S T S' section thus proving that tests of sourced project were run." +
            "Test if Console Output log contains 'BUILD SUCCESS' text thus proving that the run was success" +
            "Project sourced from GitHub using Maven with Build Steps goal: 'test'")
    @Test
    public void testBuildGitProjectWithBuildStepsMaven() {
        final String goal = "test";
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String actualConsoleOutput = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(TestDataUtils.GITHUB_REPOSITORY_URL)
                .inputBranchSpecifier(TestDataUtils.BRANCH_SPECIFIER)
                .openAddBuildStepDropDown()
                .selectInvokeTopLevelMavenTargets()
                .selectMavenVersion()
                .setGoal(goal)
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndWaitSuccessStatus()
                .getSideMenu()
                .clickBuildIconInBuildHistory()
                .clickConsoleOutput()
                .getConsoleOutputText();

        Assert.assertTrue(actualConsoleOutput.contains("T E S T S"));
        Assert.assertTrue(actualConsoleOutput.contains("BUILD SUCCESS"));
    }

    @Owner("Yulia Matusevich")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify if the Downstream project is built automatically directly upon the Upstream project is built." +
            "Stream is set using 'Build other project' option is in 'Post-Build Actions' section of the Configuration Page")
    @Test
    public void testBuildProjectWithBuildOtherProjectOption() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME2);

        String project1StatusIconBeforeBuild = new HomePage(getDriver())
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME);

        String project2StatusIconBeforeBuild = new HomePage(getDriver())
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME2);

        String project1StatusIconAfterBuild = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .openAddPostBuildActionDropDown()
                .selectBuildOtherProjects()
                .setProjectToBuildName(TestDataUtils.FREESTYLE_PROJECT_NAME2)
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndWaitBuildCompleted()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME);

        String project2StatusIconAfterBuild = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME2)
                .getSideMenu()
                .getBuildStatus()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME2);

        Assert.assertEquals(project1StatusIconBeforeBuild, "Not built");
        Assert.assertEquals(project2StatusIconBeforeBuild, "Not built");
        Assert.assertEquals(project1StatusIconAfterBuild, "Success");
        Assert.assertEquals(project2StatusIconAfterBuild, "Success");
    }

    @Owner("Liudmila Plucci")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify if 'target' folder disappears from the 'Workspace' page when Build Steps goal: 'clean' is set. " +
            "Thus confirming that 'clean' command is succeed. Project sourced from GitHub using Maven.")
    @Test(dependsOnMethods = "testBuildGitProjectWithBuildStepsMaven")
    public void testBuildGitProjectWithBuildStepsMavenClean() {
        final String goal = "clean";

        List<String> actualListOfFoldersBeforeClean = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickWorkspace()
                .getListOfFolders();

        List<String> actualListOfFolders = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .setGoal(goal)
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndWaitSuccessStatus()
                .getSideMenu()
                .clickWorkspace()
                .getListOfFolders();

        Assert.assertTrue(actualListOfFoldersBeforeClean.contains("target"));
        Assert.assertFalse(actualListOfFolders.contains("target"));
    }

    @Owner("Yulia Matusevich")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Check the downstream project section and list of connected projects appear " +
            "on the StatusPage of the Upstream project")
    @Test(dependsOnMethods = "testBuildProjectWithBuildOtherProjectOption")
    public void testDownstreamProjectSectionAndListOfConnectedProjectsAppearsOnUpstreamProjectStatusPage() {

        List<String> h2HeaderNamesList = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getH2HeaderNamesList();

        List<String> downstreamProjectList = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getDownstreamProjectNamesList();

        Assert.assertTrue(h2HeaderNamesList.contains("Downstream Projects"));
        Assert.assertTrue(downstreamProjectList.contains(TestDataUtils.FREESTYLE_PROJECT_NAME2));
    }

    @Owner("Yulia Matusevich")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Check if the Downstream project name link on the Status page leads to the correct Upstream project Status Page")
    @Test(dependsOnMethods = "testBuildProjectWithBuildOtherProjectOption")
    public void testDownstreamProjectNameLeadsToCorrectUpstreamProjectStatusPage() {

        String downstreamProjectName = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickUpstreamProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME2)
                .getHeaderText();

        Assert.assertTrue(downstreamProjectName.contains(TestDataUtils.FREESTYLE_PROJECT_NAME2));
    }

    @TmsLink("2RF9x5pb")
    @Owner("ViktoriyaD")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Check if the project 'Build History' section on the side menu saves and displays exactly the same amount of builds" +
            " as configured by the 'Discard Old Builds' option in the Configuration page")
    @Test
    public void testBuildProjectWithDiscardOldBuildsMaxLimit() throws InterruptedException {
        final int expectedMaxNumberOfBuildsToKeep = 2;
        final int amountsOfBuild = expectedMaxNumberOfBuildsToKeep + 3;
        final List<String> expectedListOfBuildNames = TestUtils.getListOfLastKeepElements(amountsOfBuild,expectedMaxNumberOfBuildsToKeep);

        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        List<String> actualAmountOfSavedBuilds =
                new HomePage(getDriver())
                        .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                        .getSideMenu()
                        .clickConfigure()
                        .clickDiscardOldBuildsCheckbox()
                        .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                        .clickSaveButton()
                        .getSideMenu()
                        .clickBuildNowAndWaitStatusChangedNTimes(amountsOfBuild)
                        .refreshFreestyleProjectStatusPage()
                        .getSideMenu()
                        .getListOfSavedBuilds();

        Assert.assertEquals(actualAmountOfSavedBuilds.size(), expectedMaxNumberOfBuildsToKeep);
        Assert.assertEquals(actualAmountOfSavedBuilds, expectedListOfBuildNames);
    }

    @Owner("Yulia Matusevich")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Check if the 'Latest Test Result' link and test result history chart appear on the project Status Page " +
            "when the 'Publish JUnit test result report' option of the 'Add post-build actions' is used")
    @Test
    public void testBuildProjectWithPostBuildActionPublishJUnitReport() {
        final String goal = "clean compile test";
        final String reportPath = "target/surefire-reports/*.xml";
        final String expectedLinkName = "Latest Test Result";
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(TestDataUtils.GITHUB_REPOSITORY_URL)
                .inputBranchSpecifier(TestDataUtils.BRANCH_SPECIFIER)
                .openAddBuildStepDropDown()
                .selectInvokeTopLevelMavenTargets()
                .selectMavenVersion()
                .setGoal(goal)
                .openAddPostBuildActionDropDown()
                .selectPublishJUnitTestResultReport()
                .setReportPath(reportPath)
                .clearHealthReportAmplificationFactorField()
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndWaitBuildCompleted()
                .refreshFreestyleProjectStatusPage();

        Assert.assertTrue(freestyleProjectStatusPage.isTestResultLinkClickable());
        Assert.assertEquals(freestyleProjectStatusPage.getTestResultLinkText(), expectedLinkName);
        Assert.assertTrue(freestyleProjectStatusPage.isTestResultHistoryChartDisplayed());
    }
}