package tests;

import io.qameta.allure.*;
import model.page.BuildHistoryPage;
import model.page.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

public class BuildHistoryTest extends BaseTest {

    @TmsLink("4dzBvbes")
    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify the header of the 'Build  History' page")
    @Test
    public void testH1HeaderBuildHistory() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);

        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory();

        Assert.assertEquals(buildHistoryPage.getHeaderText(), "Build History of Jenkins");
    }

    @TmsLink("UePoJpwt")
    @Owner("Anna Morozova")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that 'Small', 'Medium', 'Large' icons are displayed on the 'Build History' page")
    @Test
    public void testSMLIconsExist() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory();

        Assert.assertTrue(buildHistoryPage.smallSizeIconIsDisplayed());
        Assert.assertTrue(buildHistoryPage.middleSizeIconIsDisplayed());
        Assert.assertTrue(buildHistoryPage.largeSizeIconIsDisplayed());
    }

    @TmsLink("35Hl2kyt")
    @Owner("Ilya Cheshkov")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that the icons of three RSS feeds exist on the 'Build History' page")
    @Test
    public void testRssItemsExist() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory();

        Assert.assertTrue(buildHistoryPage.iconAtomFeedForAllIsDisplayed());
        Assert.assertTrue(buildHistoryPage.iconAtomFeedForFailuresIsDisplayed());
        Assert.assertTrue(buildHistoryPage.iconAtomFeedForFoJustLatestBuildsIsDisplayed());
    }

    @TmsLink("7xsqP6Gl")
    @Owner("Viktoriia Karpus")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that the 'Icon Legend' exist on the 'Build History' page")
    @Test
    public void testIconLegendExist() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory();

        Assert.assertTrue(buildHistoryPage.isIconDisplayed());
    }

    @TmsLink("2aJ7orwX")
    @Owner("Tatiana P")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify the number of columns in 'Project Status' table on the 'Build History' page")
    @Test
    public void testNumberOfColumnsInProjectStatusTable() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory();

        Assert.assertEquals(buildHistoryPage.getSize(), 5);
    }

    @TmsLink("U0vo3TTd")
    @Owner("Irina Irico")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that click on Dashboard on 'BuildHistory' page redirects to the main page")
    @Test
    public void testRedirectToMainPage() {
        HomePage homePage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildHistory()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }
}