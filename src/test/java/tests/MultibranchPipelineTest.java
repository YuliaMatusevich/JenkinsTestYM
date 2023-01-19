package tests;

import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {
    private static final String PROJECT_NAME = TestUtils.getRandomStr();
    private static final String NEW_PROJECT_NAME = TestUtils.getRandomStr();

    @Test
    public void testRenameMultiBranchPipelineFromDropdown() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), PROJECT_NAME);


        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobDropDownMenu(PROJECT_NAME)
                .clickRenameMultibranchPipelineDropDownMenu()
                .clearFieldAndInputNewName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(NEW_PROJECT_NAME));
    }

    @Test
    public void testRenameMultiBranchPipelineFromLeftSideMenu() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), PROJECT_NAME);

        String actualMultibranchPipeline = new HomePage(getDriver())
                .clickJobMBPipeline(PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getNameText();

        Assert.assertTrue(actualMultibranchPipeline.contains(NEW_PROJECT_NAME));

        List<String> jobsList = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList();

        Assert.assertTrue(jobsList.contains(NEW_PROJECT_NAME));
    }
}
