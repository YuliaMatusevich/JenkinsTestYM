package tests;

import model.*;
import model.multibranch_pipeline.MultibranchPipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;

import static runner.ProjectMethodsUtils.*;
import static runner.TestUtils.getRandomStr;

public class MultibranchPipelineTest extends BaseTest {
    private static final String MULTIBRANCH_PIPELINE_NAME = getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_RENAME = getRandomStr();

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobDropDownMenu(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameMultibranchPipelineDropDownMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_RENAME)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_RENAME));
    }

    @Test
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMBPipeline(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_RENAME)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_RENAME));

        List<String> jobsList = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(jobsList.contains(MULTIBRANCH_PIPELINE_RENAME));
    }

    @Test
    public void testDisableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        String warningMessage = new HomePage(getDriver())
                .clickJobMBPipeline(MULTIBRANCH_PIPELINE_NAME)
                .clickDisableEnableButton()
                .getWarningMessage();

        Assert.assertEquals(warningMessage, "This Multibranch Pipeline is currently disabled");
        Assert.assertEquals(new MultibranchPipelineStatusPage(getDriver()).getAttributeProjectIcon(), "icon-folder-disabled icon-xlg");
    }

    @Test
    public void testEnableMultiBranchPipeline() {
        createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        MultibranchPipelineStatusPage multibranchPipelineStatusPage = new HomePage(getDriver())
                .clickJobMBPipeline(MULTIBRANCH_PIPELINE_NAME);

        Assert.assertTrue(multibranchPipelineStatusPage.isDisableButtonPresent());
        Assert.assertFalse(multibranchPipelineStatusPage.getAttributeProjectIcon().contains("disable"));
    }
}