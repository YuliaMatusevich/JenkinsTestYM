package tests;

import model.HomePage;
import model.ManageOldDataPage;
import model.ManageUsersPage;
import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private final String NEW_USERS_FULLNAME = TestUtils.getRandomStr(6);
    private final String USERNAME = TestUtils.getRandomStr(6);
    private final String PASSWORD = TestUtils.getRandomStr(6);
    private final String USERS_FULL_NAME = TestUtils.getRandomStr(6);
    private final String EMAIL = TestUtils.getRandomStr(10) + "@gmail.com";

    @Test
    public void testRenameFullUserName() {
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearInputFieldFullUserName()
                .inputNameInFieldFullUserName(NEW_USERS_FULLNAME)
                .clickSaveButton()
                .refreshPage();

        String breadcrumbsUserName = new StatusUserPage(getDriver())
                .getBreadcrumbs().getTextBreadcrumbs();

        Assert.assertEquals(userStatusPage.getHeader().getUserNameText(), NEW_USERS_FULLNAME);
        Assert.assertTrue(breadcrumbsUserName.contains(NEW_USERS_FULLNAME));
        Assert.assertEquals(userStatusPage.getH1Title(), NEW_USERS_FULLNAME);
    }

    @Test(dependsOnMethods = "testRenameFullUserName")
    public void testNewFullnameAfterReLogging() {
        new HomePage(getDriver())
                .getHeader()
                .clickLogOut();
        loginWeb();

        Assert.assertEquals(new HomePage(getDriver()).getUserName(), NEW_USERS_FULLNAME);
    }

    @Test
    public void testManageOldData() {
        ManageOldDataPage page = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickLinkManageOldData();

        Assert.assertEquals(page.getMainPanelNoticeText(), "No old data was found.");
    }

    @Test
    public void testPluginManagerInstallPlugin() {
        final String pluginName = "TestNG Results";

        String notice = new HomePage(getDriver())
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkAvailable()
                .inputValueToSearchRow(pluginName)
                .clickCheckBoxTestNGResults()
                .clickButtonInstallWithoutRestart()
                .clickButtonGoBackToTopPage()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkInstalled()
                .inputValueToSearchRow(pluginName)
                .getResultFieldText();

        Assert.assertTrue(notice.contains("Failed to load: TestNG Results Plugin"));
    }

    @Test
    public void testCreateUserWithEmptyName() {
        String password = TestUtils.getRandomStr(10);

        String errorMessageWhenEmptyUserName = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(TestUtils.getRandomStr(10))
                .setEmail(TestUtils.getRandomStr(10) + "@gmail.com")
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyUserName, "\"\" is prohibited as a username for security reasons.");
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'&'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'<'}, {'>'}, {'.'}, {','}};
    }

    @Test(dataProvider = "specialCharacters")
    public void testCreateUserWithIncorrectCharactersInName(Character specialCharacter) {
        String password = TestUtils.getRandomStr(10);

        String errorMessageWhenIncorrectCharacters = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(String.valueOf(specialCharacter))
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(TestUtils.getRandomStr(10))
                .setEmail(TestUtils.getRandomStr(10) + "@gmail.com")
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenIncorrectCharacters, "User name must only contain alphanumeric characters, underscore and dash");
    }

    @Test
    public void testCreateUser() {
        ManageUsersPage manageUsersPage = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USERS_FULL_NAME)
                .setEmail(EMAIL)
                .clickCreateUserButton();

        Assert.assertTrue(manageUsersPage.getListOfUserIDs().contains(USERNAME), USERNAME + " username not found");
        Assert.assertTrue(manageUsersPage.getListOfFullNamesOfUsers().contains(USERS_FULL_NAME), USERS_FULL_NAME + " fullName not found");
    }

    @Test
    public void testDeleteUser() {
        ProjectMethodsUtils.createNewUser(getDriver(), USERNAME, PASSWORD, USERS_FULL_NAME, EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(USERNAME)
                .clickYes()
                .getListOfUserIDs();

        Assert.assertFalse(listOfUsers.contains(USERNAME));
    }
}
