package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.page.HomePage;
import model.page.status.MultibranchPipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

import static runner.ProjectMethodsUtils.createNewMultibranchPipeline;
import static runner.TestUtils.getRandomStr;

public class MultibranchPipelineTest extends BaseTest {

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickRenameMultibranchPipelineDropDownMenu()
                .clearFieldAndInputNewName(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME)
                .clickRenameButton()
                .getHeaderText();

        Assert.assertTrue(actualMultibranchPipeline.contains(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME));
    }

    @Test
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME)
                .clickRenameButton()
                .getHeaderText();

        Assert.assertTrue(actualMultibranchPipeline.contains(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME));

        List<String> jobsList = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(jobsList.contains(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME));
    }

    @Test
    public void testDisableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String warningMessage = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickDisableEnableButton()
                .getWarningMessage();

        Assert.assertEquals(warningMessage, "This Multibranch Pipeline is currently disabled");
        Assert.assertEquals(new MultibranchPipelineStatusPage(getDriver()).getAttributeProjectIcon(), "icon-folder-disabled icon-xlg");
    }

    @Test
    public void testEnableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        MultibranchPipelineStatusPage multibranchPipelineStatusPage = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        Assert.assertTrue(multibranchPipelineStatusPage.isDisableButtonPresent());
        Assert.assertFalse(multibranchPipelineStatusPage.getAttributeProjectIcon().contains("disable"));
    }

    @Test
    public void testAddDisplayName() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        final String displayName = getRandomStr();

        List<String> jobNamesList = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickConfigure()
                .setDisplayName(displayName)
                .clickApplyButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(jobNamesList.contains(displayName));
    }

    @Test
    public void testAddDescription() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        final String description = getRandomStr();

        String descriptionText = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickLinkConfigureTheProject()
                .setDescription(description)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertEquals(descriptionText, description);
    }

    @Ignore
    @Test
    public void testChangeProjectIcon() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String multibranchPipelineIcon = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getAttributeProjectIcon();

        String defaultProjectIcon = new MultibranchPipelineStatusPage(getDriver())
                .getSideMenu()
                .clickConfigure()
                .selectIcon()
                .clickSaveButton()
                .getAttributeProjectIcon();

        Assert.assertNotEquals(defaultProjectIcon, multibranchPipelineIcon);
    }

    @Test
    public void testMoveMenuOptionIsNotAvailable() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        List<String> menuOptions = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .getMenuOptions();

        Assert.assertFalse(menuOptions.stream().anyMatch(option -> option.contains("Move")), "Move is present");
    }

    @Test
    public void testMoveMenuOptionIsAvailableWhenFolderIsCreated() {
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> menuOptions = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .getMenuOptions();


        Assert.assertTrue(menuOptions.stream().anyMatch(option -> option.contains("Move")), "Move is not present");
    }

    @Test
    public void testMoveMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        List<String> actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(TestDataUtils.FOLDER_NAME)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickMove()
                .selectFolder(TestDataUtils.FOLDER_NAME)
                .clickMoveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertFalse(actualResult.stream().anyMatch(option -> option.contains(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)), "Multibranch Pipeline is present on Dashboard");

        List<String> folderContent = new HomePage(getDriver())
                .clickFolder(TestDataUtils.FOLDER_NAME)
                .getJobList();

        Assert.assertTrue(folderContent.stream().anyMatch(option -> option.contains(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)), "Multibranch Pipeline is NOT present in folder");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Check if MultibranchPipeline can be deleted")
    @Test
    public void testDeleteMultibranchPipelineUsingDropDown() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickJobDropDownMenu(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickDeleteDropDownMenu()
                .clickYes()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}