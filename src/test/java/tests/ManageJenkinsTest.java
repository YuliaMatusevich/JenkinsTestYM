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
import java.util.List;

import static runner.TestUtils.getRandomStr;

public class ManageJenkinsTest extends BaseTest {

    private static final String NEW_USER_FULL_NAME = getRandomStr();
    private static final String USER_NAME = getRandomStr();
    private static final String PASSWORD = getRandomStr(7);
    private static final String USER_FULL_NAME = getRandomStr();
    private static final String EMAIL = getRandomStr(5) + "@gmail.com";

    @Test
    public void testRenameFullUserName() {
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearInputFieldFullUserName()
                .inputNameInFieldFullUserName(NEW_USER_FULL_NAME)
                .clickSaveButton()
                .refreshPage();

        String breadcrumbsUserName = new StatusUserPage(getDriver())
                .getBreadcrumbs().getTextBreadcrumbs();

        Assert.assertEquals(userStatusPage.getHeader().getUserNameText(), NEW_USER_FULL_NAME);
        Assert.assertTrue(breadcrumbsUserName.contains(NEW_USER_FULL_NAME));
        Assert.assertEquals(userStatusPage.getH1Title(), NEW_USER_FULL_NAME);
    }

    @Test(dependsOnMethods = "testRenameFullUserName")
    public void testNewFullNameAfterReLogging() {
        new HomePage(getDriver())
                .getHeader()
                .clickLogOut();
        loginWeb();

        Assert.assertEquals(new HomePage(getDriver()).getUserName(), NEW_USER_FULL_NAME);
    }

    @Test
    public void testManageOldData() {
        ManageOldDataPage page = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickLinkManageOldData();

        Assert.assertEquals(page.getMainPanelNoticeText(), "No old data was found.");
    }

    @Test
    public void testPluginManagerInstallPlugin() {
        final String pluginName = "TestNG Results";

        String notice = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkAvailable()
                .inputValueToSearchRow(pluginName)
                .clickCheckBoxTestNGResults()
                .clickButtonInstallWithoutRestart()
                .clickButtonGoBackToTopPage()
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkInstalled()
                .inputValueToSearchRow(pluginName)
                .getResultFieldText();

        Assert.assertTrue(notice.contains("Failed to load: TestNG Results Plugin"));
    }

    @Test
    public void testCreateUserWithEmptyName() {
        String errorMessageWhenEmptyUserName = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyUserName, "\"\" is prohibited as a username for security reasons.");
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'&'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'<'}, {'>'}, {'.'}, {','}};
    }

    @Test(dataProvider = "specialCharacters")
    public void testCreateUserWithIncorrectCharactersInName(Character specialCharacter) {
        String errorMessageWhenIncorrectCharacters = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(String.valueOf(specialCharacter))
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenIncorrectCharacters, "User name must only contain alphanumeric characters, underscore and dash");
    }

    @Test
    public void testCreateUser() {
        ManageUsersPage manageUsersPage = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(EMAIL)
                .clickCreateUserButton();

        Assert.assertTrue(manageUsersPage.getListOfUserIDs().contains(USER_NAME), USER_NAME + " username not found");
        Assert.assertTrue(manageUsersPage.getListOfFullNamesOfUsers().contains(USER_FULL_NAME), USER_FULL_NAME + " fullName not found");
    }

    @Test
    public void testDeleteUser() {
        ProjectMethodsUtils.createNewUser(getDriver(), USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(USER_NAME)
                .clickYes()
                .getListOfUserIDs();

        Assert.assertFalse(listOfUsers.contains(USER_NAME));
    }

    @Test
    public void testCreateUserWithExistName() {
        ProjectMethodsUtils.createNewUser(getDriver(), USER_NAME, PASSWORD, USER_FULL_NAME, EMAIL);

        String errorMessageWhenExistName = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .confirmPassword(PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenExistName, "User name is already taken");
    }
}