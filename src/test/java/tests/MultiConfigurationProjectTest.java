package tests;

import model.ConsoleOutputPage;
import model.HomePage;
import model.NewItemPage;
import model.RenameItemErrorPage;
import model.multiconfiguration.MultiConfigurationProjectConfigPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;
import java.util.List;

import static runner.TestUtils.getRandomStr;

public class MultiConfigurationProjectTest extends BaseTest {
    private static final String MULTI_CONFIGURATION_PROJECT_NAME = getRandomStr();
    private static final String MULTI_CONFIGURATION_PROJECT_RENAME = getRandomStr();

    @Test
    public void testMultiConfigurationProjectAddDescription() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickAddDescription()
                .fillDescription("Description")
                .clickSave();

        Assert.assertEquals(multiConfPage.getDescriptionText(), "Description");
    }

    @Test
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickJobDropdownMenu(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickRenameMultiConfigurationDropDownMenu()
                .clearFieldAndInputNewName(MULTI_CONFIGURATION_PROJECT_RENAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(MULTI_CONFIGURATION_PROJECT_RENAME), MULTI_CONFIGURATION_PROJECT_RENAME);
    }

    @Test
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(MULTI_CONFIGURATION_PROJECT_RENAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(MULTI_CONFIGURATION_PROJECT_RENAME), MULTI_CONFIGURATION_PROJECT_RENAME);
    }

    @Test
    public void testMultiConfigurationProjectDelete() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .deleteMultiConfigProject();

        Assert.assertFalse(homePage.getJobNamesList().contains(MULTI_CONFIGURATION_PROJECT_NAME));
    }

    @Test
    public void testMultiConfigurationProjectDisable() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickConfiguration(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.getTextDisabledWarning().contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisable")
    public void testMultiConfigurationProjectEnable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickConfiguration(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.disableButtonIsDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectBuild() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        int countBuildsBeforeNewBuild = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .countBuildsOnSidePanel();

        new MultiConfigurationProjectStatusPage(getDriver()).clickBuildNowOnSideMenu(MULTI_CONFIGURATION_PROJECT_NAME);
        getDriver().navigate().refresh();

        int countBuildsAfterNewBuild = new MultiConfigurationProjectStatusPage(getDriver())
                .countBuildsOnSidePanel();

        Assert.assertNotEquals(countBuildsAfterNewBuild, countBuildsBeforeNewBuild);
    }

    @Test
    public void testFindMultiConfigurationProject() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .setSearchAndClickEnter(MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(multiConfigurationProjectStatusPage.getNameMultiConfigProject(MULTI_CONFIGURATION_PROJECT_NAME), MULTI_CONFIGURATION_PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProjectDisableCheckIconDashboardPage() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        String jobStatusIconTooltip = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickDisableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Disabled");
    }

    @Ignore
    @Test(dependsOnMethods = {"testMultiConfigurationProjectDisableCheckIconDashboardPage"})
    public void testMultiConfigurationProjectEnableCheckIconDashboardPage() {
        String jobStatusIconTooltip = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus(MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test
    public void testMultiConfigurationProjectRenameToInvalidNameViaSideMenu() {
        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName("&")
                .clickSaveButtonAndGetError();

        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "‘&amp;’ is an unsafe character");
    }

    @Test(dependsOnMethods = {"testMultiConfigurationProjectBuild"})
    public void testMultiConfigurationProjectsRunJobInBuildHistory() {
        List<String> listNameOfLabels = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickBuildHistory()
                .getNameOfLabelsOnTimeLineBuildHistory();

        Assert.assertTrue(listNameOfLabels.contains(MULTI_CONFIGURATION_PROJECT_NAME + " #1"));
    }

    @Test
    public void testMultiConfigurationProjectDisableCheckIconProjectName() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickDisableButton();
        Assert.assertTrue(multiConfigPrStatusPage.iconProjectDisabledIsDisplayed());
    }

    @Test(dependsOnMethods = {"testMultiConfigurationProjectDisableCheckIconProjectName"})
    public void testMultiConfigurationProjectEnableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton();

        Assert.assertTrue(multiConfigPrStatusPage.iconProjectEnabledIsDisplayed());
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisableCheckIconDashboardPage")
    public void testEnableMultiConfigurationProject() {
        HomePage buildNowButton = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickEnableButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickProjectDropdownMenu(MULTI_CONFIGURATION_PROJECT_NAME);

        Assert.assertTrue(buildNowButton.buildNowButtonIsDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectWithBuildStepCheckBuildSuccess() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectConfigPage multiConfigProjectPage = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickConfiguration(MULTI_CONFIGURATION_PROJECT_NAME)
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
                .clickBuildNowButton()
                .clickBuildIcon();

        Assert.assertEquals(multiConfigProjectConsole.getTextConsoleOutputUserName(), new HomePage(getDriver()).getUserName());
        Assert.assertTrue(multiConfigProjectConsole.getConsoleOutputText().contains("Finished: SUCCESS"));
    }

    @Ignore
    @Test
    public void testNewestBuildsButton() {
        new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem();
        MultiConfigurationProjectStatusPage newMultiConfigItem = new NewItemPage(getDriver())
                .setItemName(MULTI_CONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton();
        MultiConfigurationProjectStatusPage mcpStatusPage = new MultiConfigurationProjectStatusPage(getDriver());
        mcpStatusPage.multiConfigurationProjectBuildNow(getDriver());
        mcpStatusPage.multiConfigurationProjectNewestBuilds(getDriver());

        Assert.assertTrue(getDriver().
                findElement(By.xpath("//*[@id='buildHistory/']/div[2]/table/tbody/tr[2]")).isDisplayed());
    }

    @Test
    public void testSetConfigurationMatrix() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);

        MultiConfigurationProjectStatusPage configMatrix = new HomePage(getDriver())
                .clickMultiConfigurationProject(MULTI_CONFIGURATION_PROJECT_NAME)
                .clickConfiguration(MULTI_CONFIGURATION_PROJECT_NAME)
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(MULTI_CONFIGURATION_PROJECT_NAME, "stage", 1)
                .enterValueUserDefinedAxis("sandbox dev uat prod", 1)
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(MULTI_CONFIGURATION_PROJECT_NAME, "maven_tool", 2)
                .enterValueUserDefinedAxis("clean validate compile test", 2)
                .clickApplyButton()
                .clickSaveButton();

        Assert.assertTrue(configMatrix.configurationMatrixIsDisplayed());
    }
}