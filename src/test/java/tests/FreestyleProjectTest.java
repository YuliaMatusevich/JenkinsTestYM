package tests;

import io.qameta.allure.*;
import model.BuildWithParametersPage;
import model.ChangesBuildsPage;
import model.HomePage;
import model.RenameItemErrorPage;
import model.config_pages.FreestyleProjectConfigPage;
import model.status_pages.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import static runner.TestUtils.*;

public class FreestyleProjectTest extends BaseTest {

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

    @Test
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        final FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(freestyleProjectStatusPage.getHeaderText(), String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

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

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String newDescription = "It's new description to job";

        FreestyleProjectStatusPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonEditDescription()
                .inputAndSaveDescriptionText(newDescription);

        Assert.assertEquals(page.getDescriptionText(), newDescription);
    }

    @Test
    public void testNoBuildFreestyleProjectChanges() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        ChangesBuildsPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickChanges();

        Assert.assertEquals(page.getPageText(), "Changes\nNo builds.");
    }

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

    @Test
    public void testViewFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String freestyleName = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getHeaderText();

        Assert.assertEquals(freestyleName, String.format("Project %s", TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testFreestyleProjectConfigureByDropdown() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickConfigDropDownMenuFreestyle();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Configuration");
    }

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

    @Test(dependsOnMethods = "testConfigureJobAsParameterized")
    public void testConfigureSourceCodeByGIT() {
        final String repositoryURL = "https://github.com/RedRoverSchool/JenkinsQA_05.git";
        final String branchSpecifier = "main";

        HomePage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickConfigure()
                .switchOFFCheckBoxThisProjectIsParametrized()
                .clickLinkSourceCodeManagement()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(repositoryURL)
                .inputBranchSpecifier(branchSpecifier)
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNowAndRedirectToDashboardAfterBuildCompleted();

        Assert.assertEquals(page.getJobBuildStatus(), "Success");
        Assert.assertNotEquals(page.getBuildDurationTime(), "N/A");
    }

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
    @Description("Build project sourced from GitHub using Maven with Build Steps goal: 'test'")
    @Test
    public void testBuildGitProjectWithBuildStepsMaven() {
        final String repositoryURL = "https://github.com/LiudmilaPlucci/Java_05";
        final String branchSpecifier = "*/main";
        final String goal = "test";
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        String actualConsoleOutput = new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(repositoryURL)
                .inputBranchSpecifier(branchSpecifier)
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
    @Description("Build projects with 'Build other project' option in Post Build Actions")
    @Test
    public void testBuildProjectWithBuildOtherProjectOption() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),TestDataUtils.FREESTYLE_PROJECT_NAME2);

       String project1StatusIconBeforeBuild  = new HomePage(getDriver())
               .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME);

       String project2StatusIconBeforeBuild  = new HomePage(getDriver())
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
                .clickBuildNowAndWaitSuccessStatus()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.FREESTYLE_PROJECT_NAME);

        String project2StatusIconAfterBuild  = new HomePage(getDriver())
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
    @Description("Build project sourced from GitHub using Maven with Build Steps goal: 'clean'")
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

    @Test (dependsOnMethods = "testBuildProjectWithBuildOtherProjectOption")
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
    @Description("Check if the Downstream project name link leads to the correct Upstream project Status Page")
    @Test (dependsOnMethods = "testBuildProjectWithBuildOtherProjectOption")
    public void testDownstreamProjectNameLeadsToCorrectUpstreamProjectStatusPage() {

        String downstreamProjectName= new HomePage(getDriver())
                .clickFreestyleProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickUpstreamProjectName(TestDataUtils.FREESTYLE_PROJECT_NAME2)
                .getHeaderText();

        Assert.assertTrue(downstreamProjectName.contains(TestDataUtils.FREESTYLE_PROJECT_NAME2));
    }
}