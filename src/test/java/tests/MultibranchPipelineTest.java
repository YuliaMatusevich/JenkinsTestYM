package tests;

import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import java.util.List;

import static runner.TestUtils.getRandomStr;

public class MultibranchPipelineTest extends BaseTest {
    private static final String MULTIBRANCH_PIPELINE_NAME = getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_RENAME = getRandomStr();

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

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
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

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
}