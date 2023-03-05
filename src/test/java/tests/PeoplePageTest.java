package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.PeoplePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestDataUtils;

import java.util.List;

public class PeoplePageTest extends BaseTest {

    @TmsLink("L7BaR0hF")
    @Owner("Liudmila Plucci")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Checking for the presence of required elements on the page: header, footer, buttons")
    @Test
    public void testPeoplePageContent() {
        PeoplePage peoplePage = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople();
        Assert.assertEquals(peoplePage.getNameOfHeader(), "People");
        Assert.assertEquals(peoplePage.getDescription(), "Includes all known “users”," +
                " including login identities which the current security realm can enumerate," +
                " as well as people mentioned in commit messages in recorded changelogs.");
        Assert.assertTrue(peoplePage.isDisplayedSidePanel());
        Assert.assertTrue(peoplePage.getFooter().isDisplayedFooter());
        Assert.assertEquals(peoplePage.getPeopleTableColumnsAmount(), 5);
        Assert.assertEquals(peoplePage.getPeopleTableColumnsAsString(), "User ID Name Last Commit Activity On");
        Assert.assertEquals(peoplePage.getIconLabel(), "Icon:");
        Assert.assertEquals(peoplePage.getListIconSizeButtonsAsString(), "Small Medium Large");
    }

    @TmsLink("nErWohqh")
    @Owner("Evan Mai")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Locate a newly created user within the 'People' section")
    @Test
    public void testFindUserInThePeopleSection() {
        PeoplePage peoplePage = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickPeople();

        Assert.assertTrue(peoplePage.getListOfUsers().contains(TestDataUtils.USER_NAME), TestDataUtils.USER_NAME + " not found");
    }

    @TmsLink("e53PF9Xx")
    @Owner("Evan Mai")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that user was deleted")
    @Test(dependsOnMethods = "testFindUserInThePeopleSection")
    public void testPeopleDeleteUser() {
        PeoplePage peoplePage = new PeoplePage(getDriver())
                .getHeader()
                .clickJenkinsHomeLink()
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(TestDataUtils.USER_NAME)
                .clickYes()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickPeople();

        Assert.assertFalse(peoplePage.getListOfUsers().contains(TestDataUtils.USER_NAME), TestDataUtils.USER_NAME + " wasn't deleted");
    }

    @TmsLink("N1A394Tp")
    @Owner("Darina M")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that new user was created")
    @Test
    public void testCreateUser() {
        List<String> userList = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .clickPeople()
                .getListOfUsers();

        Assert.assertTrue(userList.contains(TestDataUtils.USER_NAME), TestDataUtils.USER_NAME + " not found");
    }

    @TmsLink("hiJarrLA")
    @Owner("Darina M")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify the header of the 'People' page")
    @Test
    public void testViewPeoplePage() {
        var peoplePage = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople();

        Assert.assertEquals(peoplePage.getNameOfHeader(), "People");
    }

    @TmsLink("lDCG6pdz")
    @Owner("Ina Romankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that new user was created going from 'People' page")
    @Test
    public void testCreateUserGoingFromPeoplePage() {
        List<String> userList = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople()
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserButton()
                .clickThisListLink()
                .getListOfUsers();

        Assert.assertTrue(userList.contains(TestDataUtils.USER_NAME));
    }
}