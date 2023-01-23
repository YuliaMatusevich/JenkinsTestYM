package tests;

import model.*;
import model.freestyle.FreestyleProjectConfigPage;
import model.freestyle.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static runner.TestUtils.getRandomStr;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = getRandomStr();
    private static final String FREESTYLE_PROJECT_RENAME = getRandomStr();

    @Test
    public void testDisableProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickDisableProjectBtn();

        Assert.assertEquals(freestyleProjectStatusPage.getNameText(), String.format("Project %s", FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(freestyleProjectStatusPage.getWarningMsg(), "This project is currently disabled");

        HomePage homePage = freestyleProjectStatusPage.getBreadcrumbs().clickDashboard();
        Assert.assertEquals(homePage.getJobBuildStatus(FREESTYLE_PROJECT_NAME), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        final String jobStatusIconTooltip = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickDisableProjectBtn()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        final FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(freestyleProjectStatusPage.getNameText(), String.format("Project %s", FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testAddDescription() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        final String descriptionText = "This is job #" + FREESTYLE_PROJECT_NAME;

        String freestyleProjectDescription = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonAddDescription()
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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        ChangesBuildsPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickLinkChanges();

        Assert.assertEquals(page.getPageText(), "Changes\nNo builds.");
    }

    @Test
    public void testRenameFreestyleProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        List<String> jobsList = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(FREESTYLE_PROJECT_RENAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertFalse(jobsList.contains(FREESTYLE_PROJECT_NAME));
        Assert.assertTrue(jobsList.contains(FREESTYLE_PROJECT_RENAME));
    }

    @Test
    public void testViewFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        String freestyleName = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getNameText();

        Assert.assertEquals(freestyleName, String.format("Project %s", FREESTYLE_PROJECT_NAME));
    }

    @Test
    public void testFreestyleProjectConfigureByDropdown() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(FREESTYLE_PROJECT_NAME)
                .clickConfigDropDownMenuFreestyle();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Configuration");
    }

    @Test
    public void testCreateBuildNowOnFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        int countBuildsBeforeCreatingNewBuild = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .openBuildHistoryOnSidePanel()
                .countBuildsOnSidePanel();
        int countBuildsAfterCreatingNewBuild = new FreestyleProjectStatusPage(getDriver())
                .clickBuildNowOnSidePanel()
                .countBuildsOnSidePanel();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Test
    public void testDeleteFreestyleProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        final Set<String> expectedFreestyleConfigSideMenu = new TreeSet<>(
                List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        Set<String> actualFreestyleConfigSideMenu = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigure()
                .getSideMenu()
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

        BuildWithParametersPage page = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
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

        Assert.assertTrue(page.getNameText().contains(FREESTYLE_PROJECT_NAME));
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
                .clickSideMenuConfigureLink()
                .switchOFFCheckBoxThisProjectIsParametrized()
                .getSideMenu()
                .clickLinkSourceCodeManagement()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(repositoryURL)
                .inputBranchSpecifier(branchSpecifier)
                .clickSaveButton()
                .clickButtonBuildNowAndRedirectToDashboardAfterBuildCompleted();

        Assert.assertEquals(page.getJobBuildStatus(), "Success");
        Assert.assertNotEquals(page.getBuildDurationTime(), "N/A");
    }

    @Test
    public void testCheckFieldsDaysAndMaxNumberOfBuildsToKeepInConfigure() {
        final String expectedDaysToKeepBuilds = Integer.toString((int) (Math.random() * 20 + 1));
        final String expectedMaxNumberOfBuildsToKeep = Integer.toString((int) (Math.random() * 20 + 1));
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickSideMenuConfigureLink()
                .clickDiscardOldBuildsCheckbox()
                .typeDaysToKeepBuilds(expectedDaysToKeepBuilds)
                .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                .clickSaveButton()
                .clickSideMenuConfigureLink();

        Assert.assertEquals(freestyleProjectConfigPage.getNumberOfDaysToKeepBuilds(), expectedDaysToKeepBuilds);
        Assert.assertEquals(freestyleProjectConfigPage.getMaxNumberOfBuildsToKeep(), expectedMaxNumberOfBuildsToKeep);
    }

    @Ignore
    @Test
    public void testBuildStepsOptions() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        final Set<String> expectedOptionsInBuildStepsSection = new HashSet<>(List.of("Execute Windows batch command",
                "Execute shell", "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets",
                "Run with timeout", "Set build status to \"pending\" on GitHub commit"));

        Set<String> actualOptionsInBuildStepsSection = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickSideMenuConfigureLink()
                .clickBuildStepsSideMenuOption()
                .openAddBuildStepDropDown()
                .getOptionsInBuildStepsDropDown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Test
    public void testSelectBuildPeriodicallyCheckbox() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        boolean selectedCheckbox = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_PROJECT_NAME)
                .clickSideMenuConfigureLink()
                .clickBuildTriggersSideMenuOption()
                .scrollAndClickBuildPeriodicallyCheckbox()
                .verifyThatBuildPeriodicallyCheckboxIsSelected();

        Assert.assertTrue(selectedCheckbox);
    }
}