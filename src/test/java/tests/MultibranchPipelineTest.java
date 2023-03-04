package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.status.MultibranchPipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

import static runner.ProjectMethodsUtils.createNewMultibranchPipeline;
import static runner.TestUtils.getRandomStr;

public class MultibranchPipelineTest extends BaseTest {

    @TmsLink("bjGHs8RD")
    @Owner("MichaelS")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that 'Multibranch Pipeline' can be renamed from the drop-down menu")
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

    @TmsLink("bKnDxz0P")
    @Owner("MaryJern")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that 'Multibranch Pipeline' can be renamed from Side menu")
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

    @TmsLink("6KlCPRM9")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be disable")
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

    @TmsLink("LiXVhQt6")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be enable")
    @Test
    public void testEnableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        MultibranchPipelineStatusPage multibranchPipelineStatusPage = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        Assert.assertTrue(multibranchPipelineStatusPage.isDisableButtonPresent());
        Assert.assertFalse(multibranchPipelineStatusPage.getAttributeProjectIcon().contains("disable"));
    }

    @TmsLink("QP68zNAC")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Display name' can be added to the Multibranch Pipeline")
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

    @TmsLink("b9iezKZB")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Description' can be added to the Multibranch Pipeline after clicking the 'Configure the project' link")
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

    @TmsLink("Hqtxo2nW")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify that project icon can be changed")
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

    @TmsLink("pjIXYgYO")
    @Owner("Irina Samo")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that the 'Move' button isn't available in the side menu for Multibranch Pipeline if the folder isn't present")
    @Test
    public void testMoveMenuOptionIsNotAvailable() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        List<String> menuOptions = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .getMenuOptions();

        Assert.assertFalse(menuOptions.stream().anyMatch(option -> option.contains("Move")), "Move is present");
    }

    @TmsLink("aBja9QIU")
    @Owner("Irina Samo")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that the 'Move' button is available in the side menu for Multibranch Pipeline if the folder is present")
    @Test
    public void testMoveMenuOptionIsAvailableWhenFolderIsCreated() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        List<String> menuOptions = new HomePage(getDriver())
                .clickJobMultibranchPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .getMenuOptions();

        Assert.assertTrue(menuOptions.stream().anyMatch(option -> option.contains("Move")), "Move is not present");
    }

    @TmsLink("z0XkIfVl")
    @Owner("Dmitry Starski")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be moved to another folder")
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

    @TmsLink("RVGCw7HL")
    @Owner("ZoiaBut")
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