package tests;

import model.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {
    private static final String MULTIBRANCH_PIPELINE_NAME = TestUtils.getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_NAME_RENAMED = TestUtils.getRandomStr();

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameMultibranchPipelineDropDownMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_NAME_RENAMED)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMBPipeline(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(MULTIBRANCH_PIPELINE_NAME_RENAMED)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));

        List<String> jobsList = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(jobsList.contains(MULTIBRANCH_PIPELINE_NAME_RENAMED));
    }
}
