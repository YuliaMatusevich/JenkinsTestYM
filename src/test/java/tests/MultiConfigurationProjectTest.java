package tests;

import model.ConsoleOutputPage;
import model.HomePage;
import model.RenameItemErrorPage;
import model.config_pages.MultiConfigurationProjectConfigPage;
import model.status_pages.MultiConfigurationProjectStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;
import runner.TestUtils;

import java.util.Arrays;
import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {

    @Test
    public void testMultiConfigurationProjectAddDescription() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickAddOrEditDescription()
                .fillDescription("Description")
                .clickSave();

        Assert.assertEquals(multiConfPage.getDescriptionText(), "Description");
    }

    @Test
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickJobDropdownMenu(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickRenameMultiConfigurationDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME), TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME);
    }

    @Test
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME), TestDataUtils.MULTI_CONFIGURATION_PROJECT_RENAME);
    }

    @Test
    public void testMultiConfigurationProjectDelete() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickDeleteToMyStatusPage()
                .confirmAlertAndDeleteProject();

        Assert.assertFalse(homePage.getJobNamesList().contains(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test
    public void testMultiConfigurationProjectDisable() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.getTextDisabledWarning().contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisable")
    public void testMultiConfigurationProjectEnable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.disableButtonIsDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectBuild() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        int countBuildsBeforeNewBuild = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .countBuildsInBuildHistory();

        new MultiConfigurationProjectStatusPage(getDriver())
                .getSideMenu()
                .clickBuildNow();
        getDriver().navigate().refresh();

        int countBuildsAfterNewBuild = new MultiConfigurationProjectStatusPage(getDriver())
                .getSideMenu()
                .countBuildsInBuildHistory();

        Assert.assertNotEquals(countBuildsAfterNewBuild, countBuildsBeforeNewBuild);
    }

    @Test
    public void testFindMultiConfigurationProject() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .getHeader().setSearchAndClickEnter(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(multiConfigurationProjectStatusPage.getNameMultiConfigProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProjectDisableCheckIconDashboardPage() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        String jobStatusIconTooltip = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickDisableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Disabled");
    }

    @Ignore
    @Test(dependsOnMethods = "testMultiConfigurationProjectDisableCheckIconDashboardPage")
    public void testMultiConfigurationProjectEnableCheckIconDashboardPage() {
        String jobStatusIconTooltip = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test
    public void testMultiConfigurationProjectRenameToInvalidNameViaSideMenu() {
        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName("&")
                .clickRenameButtonWithInvalidData();

        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "‘&amp;’ is an unsafe character");
    }

    @Test(dependsOnMethods = {"testMultiConfigurationProjectBuild"})
    public void testMultiConfigurationProjectsRunJobInBuildHistory() {
        List<String> listNameOfLabels = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickBuildHistory()
                .getNameOfLabelsOnTimeLineBuildHistory();

        Assert.assertTrue(listNameOfLabels.contains(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME + " #1"));
    }

    @Test
    public void testMultiConfigurationProjectDisableCheckIconProjectName() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickDisableButton();
        Assert.assertTrue(multiConfigPrStatusPage.iconProjectDisabledIsDisplayed());
    }

    @Test(dependsOnMethods = {"testMultiConfigurationProjectDisableCheckIconProjectName"})
    public void testMultiConfigurationProjectEnableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton();

        Assert.assertTrue(multiConfigPrStatusPage.iconProjectEnabledIsDisplayed());
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisableCheckIconDashboardPage")
    public void testEnableMultiConfigurationProject() {
        HomePage buildNowButton = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickProjectDropdownMenu(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertTrue(buildNowButton.buildNowButtonIsDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectWithBuildStepCheckBuildSuccess() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectConfigPage multiConfigProjectPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .scrollAndClickBuildSteps();
        if (TestUtils.isCurrentOSWindows()) {
            multiConfigProjectPage
                    .selectionAndClickExecuteWindowsFromBuildSteps()
                    .enterCommandInExecuteWindowsBuildSteps("echo Hello World");
        } else {
            multiConfigProjectPage
                    .selectionAndClickExecuteShellFromBuildSteps()
                    .enterCommandInExecuteShellBuildSteps("echo Hello World");
        }
        ConsoleOutputPage multiConfigProjectConsole = multiConfigProjectPage
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNow()
                .getSideMenu()
                .clickBuildIcon();

        Assert.assertEquals(multiConfigProjectConsole.getTextConsoleOutputUserName(), new HomePage(getDriver()).getHeader().getUserNameText());
        Assert.assertTrue(multiConfigProjectConsole.getConsoleOutputText().contains("Finished: SUCCESS"));
    }

    @Test
    public void testNewestBuildsButton() {
        MultiConfigurationProjectStatusPage mcpStatusPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getSideMenu()
                .clickBuildNow()
                .clickBuildHistoryPageNavigationNewestBuild();

        Assert.assertTrue(mcpStatusPage.NewestBuildIsDisplayed());
    }

    @Test
    public void testSetConfigurationMatrix() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage configMatrix = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure()
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME, "stage", 1)
                .enterValueUserDefinedAxis("sandbox dev uat prod", 1)
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME, "maven_tool", 2)
                .enterValueUserDefinedAxis("clean validate compile test", 2)
                .clickApplyButton()
                .clickSaveButton();

        Assert.assertTrue(configMatrix.configurationMatrixIsDisplayed());
    }

    @Ignore
    @Test
    public void testSetContentInThreeBuildStepsBuildStatusOnGitHubCommitSaved() {
        final int COUNT_BUILD_STEPS = 3;
        final String contentText = "Content text ";
        String[] expectedContents = new String[COUNT_BUILD_STEPS];
        String[] actualContents = new String[COUNT_BUILD_STEPS];

        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectConfigPage configPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME)
                .getSideMenu()
                .clickConfigure();

        for (int i = 1; i <= COUNT_BUILD_STEPS; i++) {
            configPage
                    .scrollAndClickBuildSteps()
                    .selectionAndClickSetBuildStatusOnGitHubCommitFromBuildSteps()
                    .scrollAndClickLastAdvancedButtonInBuildStepsSection()
                    .setLastContentFieldsInBuildStepsBuildStatusOnGitHubCommit(contentText + i);
            expectedContents[i - 1] = contentText + i;
        }

        configPage
                .clickSaveButton()
                .getSideMenu()
                .clickConfigure();

        for (int i = 1; i <= COUNT_BUILD_STEPS; i++) {
            actualContents[i - 1] = configPage
                    .scrollAndClickSpecificAdvancedButtonInBuildStepsSection(i)
                    .getContentFieldsInBuildStepsBuildStatusOnGitHubCommit(i);
        }
        Assert.assertEquals(Arrays.toString(actualContents), Arrays.toString(expectedContents));
    }
}