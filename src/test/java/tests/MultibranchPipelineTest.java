package tests;

import model.*;
import model.status_pages.MultibranchPipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestDataUtils;

import java.util.List;

import static runner.ProjectMethodsUtils.*;
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
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME));
    }

    @Test
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .getSideMenu()
                .clickRename()
                .clearFieldAndInputNewName(TestDataUtils.MULTIBRANCH_PIPELINE_RENAME)
                .clickRenameButton()
                .getNameText();

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
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickDisableEnableButton()
                .getWarningMessage();

        Assert.assertEquals(warningMessage, "This Multibranch Pipeline is currently disabled");
        Assert.assertEquals(new MultibranchPipelineStatusPage(getDriver()).getAttributeProjectIcon(), "icon-folder-disabled icon-xlg");
    }

    @Test
    public void testEnableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        MultibranchPipelineStatusPage multibranchPipelineStatusPage = new HomePage(getDriver())
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        Assert.assertTrue(multibranchPipelineStatusPage.isDisableButtonPresent());
        Assert.assertFalse(multibranchPipelineStatusPage.getAttributeProjectIcon().contains("disable"));
    }

    @Test
    public void testAddDisplayName() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        final String displayName = getRandomStr();

        List<String> jobNamesList = new HomePage(getDriver())
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
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
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
                .clickLinkConfigureTheProject()
                .setDescription(description)
                .clickSaveButton()
                .getAdditionalDescriptionText();

        Assert.assertEquals(descriptionText, description);
    }

    @Test
    public void testChangeProjectIcon() {
        createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);

        String multibranchPipelineIcon = new HomePage(getDriver())
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
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
                .clickJobMBPipeline(TestDataUtils.MULTIBRANCH_PIPELINE_NAME)
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
}