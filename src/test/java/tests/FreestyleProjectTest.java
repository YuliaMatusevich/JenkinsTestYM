package tests;

import model.*;
import model.freestyle.FreestyleProjectConfigPage;
import model.freestyle.FreestyleProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static runner.TestUtils.getRandomStr;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = getRandomStr(10);
    private static final String NEW_FREESTYLE_NAME = getRandomStr(10);

    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithSpacesInsteadOfName")
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters =
                List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');
        NewItemPage newItemPage = new HomePage(getDriver()).clickNewItem();

        for (Character character : incorrectNameCharacters) {
            newItemPage.clearItemName()
                    .setItemName(String.valueOf(character))
                    .selectFreestyleProject();

            Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), String.format("» ‘%s’ is an unsafe character", character));
        }
    }

    @Test
    public void testDisableProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);
        FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickDisableProjectBtn();

        Assert.assertEquals(freestyleProjectStatusPage.getNameText(), String.format("Project %s", FREESTYLE_NAME));
        Assert.assertEquals(freestyleProjectStatusPage.getWarningMsg(), "This project is currently disabled");

        HomePage homePage = freestyleProjectStatusPage.getBreadcrumbs().clickDashboard();
        Assert.assertEquals(homePage.getJobBuildStatus(FREESTYLE_NAME), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        final String jobStatusIconTooltip = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickDisableProjectBtn()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(FREESTYLE_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        final FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME);

        Assert.assertEquals(freestyleProjectStatusPage.getNameText(), String.format("Project %s", FREESTYLE_NAME));
    }

    @Test
    public void testAddDescription() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        final String descriptionText = "This is job #" + FREESTYLE_NAME;

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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        ChangesBuildsPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickLinkChanges();

        Assert.assertEquals(page.getPageText(), "Changes\nNo builds.");
    }

    @Test
    public void testRenameFreestyleProject() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        List<String> jobsList = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(NEW_FREESTYLE_NAME)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertFalse(jobsList.contains(FREESTYLE_NAME));
        Assert.assertTrue(jobsList.contains(NEW_FREESTYLE_NAME));
    }

    @Test
    public void testViewFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        String freestyleName = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getNameText();

        Assert.assertEquals(freestyleName, String.format("Project %s", FREESTYLE_NAME));
    }

    @Test
    public void testFreestyleProjectConfigureByDropdown() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(FREESTYLE_NAME)
                .clickConfigDropDownMenuFreestyle();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Configuration");
    }

    @Test
    public void testCreateNewFreestyleProjectWithDuplicateName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProject()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualResult, String.format("» A job already exists with the name ‘%s’", FREESTYLE_NAME));
    }

    @Test
    public void testCreateBuildNowOnFreestyleProjectPage() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

        String pageHeaderText = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonDeleteProject()
                .confirmAlertAndDeleteProject()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testFreestyleConfigSideMenu() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_NAME);

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
    public void testCreateFreestyleProjectWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectFreestyleProject();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(" ")
                .selectFreestyleProjectAndClickOk();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Error");
        Assert.assertEquals(freestyleProjectConfigPage.getErrorMsg(), "No name is specified");
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = "view/all/createItem";
        final String expectedTextOfError = "A problem occurred while processing the request.";

        CreateItemErrorPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(getRandomStr(256))
                .selectFreestyleProjectAndClickOkWithError();

        Assert.assertTrue(errorPage.getPageUrl().endsWith(expectedURL));
        Assert.assertTrue(errorPage.isErrorPictureDisplayed());
        Assert.assertEquals(errorPage.getErrorDescription(), expectedTextOfError);
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
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
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
                .clickButtonBuildWithParameters();

        Assert.assertTrue(page.getNameText().contains(FREESTYLE_NAME));
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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),FREESTYLE_NAME );

        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickSideMenuConfigureLink()
                .clickDiscardOldBuildsCheckbox()
                .typeDaysToKeepBuilds(expectedDaysToKeepBuilds)
                .typeMaxNumberOfBuildsToKeep(expectedMaxNumberOfBuildsToKeep)
                .clickSaveButton()
                .clickSideMenuConfigureLink();

        Assert.assertEquals(freestyleProjectConfigPage.getNumberOfDaysToKeepBuilds(), expectedDaysToKeepBuilds);
        Assert.assertEquals(freestyleProjectConfigPage.getMaxNumberOfBuildsToKeep(), expectedMaxNumberOfBuildsToKeep);
    }

    @Test
    public void testBuildStepsOptions() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),FREESTYLE_NAME );
        final Set<String> expectedOptionsInBuildStepsSection = new HashSet<>(List.of("Execute Windows batch command",
                "Execute shell", "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets",
                "Run with timeout", "Set build status to \"pending\" on GitHub commit"));

        Set<String> actualOptionsInBuildStepsSection = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickSideMenuConfigureLink()
                .clickBuildStepsSideMenuOption()
                .openAddBuildStepDropDown()
                .getOptionsInBuildStepsDropDown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Test
    public void testSelectBuildPeriodicallyCheckbox() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(),FREESTYLE_NAME );

        boolean selectedCheckbox = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickSideMenuConfigureLink()
                .clickBuildTriggersSideMenuOption()
                .scrollAndClickBuildPeriodicallyCheckbox()
                .verifyThatBuildPeriodicallyCheckboxIsSelected();

        Assert.assertTrue(selectedCheckbox);
    }
}
