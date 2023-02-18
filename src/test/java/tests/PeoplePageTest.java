package tests;

import model.HomePage;
import model.PeoplePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestDataUtils;

import java.util.List;

public class PeoplePageTest extends BaseTest {

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

    @Test
    public void testViewPeoplePage() {
        var peoplePage = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople();

        Assert.assertEquals(peoplePage.getNameOfHeader(), "People");
    }

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