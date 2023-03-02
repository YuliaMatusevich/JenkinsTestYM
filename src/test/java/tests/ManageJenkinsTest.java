package tests;

import io.qameta.allure.*;
import model.page.*;
import model.page.status.FolderStatusPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @TmsLink("lE2qeHdF")
    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.MINOR)
    @Feature("Function")
    @Description("Rename 'Full Name' for User with valid characters and verity that New Full Name is displayed on StatusUserPage and on Header and on Breadcrumbs")
    @Test
    public void testRenameFullUserName() {
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearFullNameField()
                .setFullName(TestDataUtils.NEW_USER_FULL_NAME)
                .clickSaveButton()
                .refreshPage();

        String breadcrumbsUserName = new StatusUserPage(getDriver())
                .getBreadcrumbs().getTextBreadcrumbs();

        Assert.assertEquals(userStatusPage.getHeader().getUserNameText(), TestDataUtils.NEW_USER_FULL_NAME);
        Assert.assertTrue(breadcrumbsUserName.contains(TestDataUtils.NEW_USER_FULL_NAME));
        Assert.assertEquals(userStatusPage.getH1Title(), TestDataUtils.NEW_USER_FULL_NAME);
    }

    @TmsLink("4kAcrDNU")
    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.MINOR)
    @Feature("Function")
    @Description("Verify that the user, after logging out of the session, in which changing of the FullName have been made, is able to see the 'new FullName' in the header when logging in again")
    @Test(dependsOnMethods = "testRenameFullUserName")
    public void testNewFullNameAfterReLogging() {
        new HomePage(getDriver())
                .getHeader()
                .clickLogOut();
        loginWeb();

        Assert.assertEquals(new HomePage(getDriver()).getHeader().getUserNameText(), TestDataUtils.NEW_USER_FULL_NAME);
    }

    @TmsLink("8YAYAHrO")
    @Owner("Aleksei Chapaev")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verify that there is no entries of old data are found on the 'Manage Old Data' page")
    @Test
    public void testManageOldData() {
        ManageOldDataPage page = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickLinkManageOldData();

        Assert.assertEquals(page.getMainPanelNoticeText(), "No old data was found.");
    }

    @Ignore
    @Flaky
    @TmsLink("Ud8LDADw")
    @Owner("Aleksei Chapaev")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that installed plugin NOT found in 'Available' tab installed plugin but found in 'Installed' tab")
    @Test
    public void testPluginManagerInstallPlugin() {
        final String pluginName = "TestNG Results";

        String notice = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkAvailable()
                .setSearch(pluginName)
                .clickCheckBoxTestNGResults()
                .clickButtonInstallWithoutRestart()
                .clickButtonGoBackToTopPage()
                .getSideMenu()
                .clickManageJenkins()
                .clickLinkManagePlugins()
                .clickLinkInstalled()
                .setSearch(pluginName)
                .getResultFieldText();

        Assert.assertTrue(notice.contains("Failed to load: TestNG Results Plugin"));
    }

    @TmsLink("0ak6t6Hy")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with empty name and verify that error message '\"\" is prohibited as a username for security reasons.' appeared")
    @Test
    public void testCreateUserWithEmptyName() {
        String errorMessageWhenEmptyUserName = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyUserName, "\"\" is prohibited as a username for security reasons.");
    }

    @TmsLink("3pQppG3t")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with invalid characters in name and verify that error message 'User name must only contain alphanumeric characters, underscore and dash' appeared")
    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testCreateUserWithIncorrectCharactersInName(Character specialCharacter, String errorMessage) {
        String errorMessageWhenIncorrectCharacters = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(String.valueOf(specialCharacter))
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenIncorrectCharacters, "User name must only contain alphanumeric characters, underscore and dash");
    }

    @TmsLink("HFC4xCSR")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Create user going from 'Manage Users' page with valid data and verify that new user is created and is displayed on the 'Manage users' page")
    @Test
    public void testCreateUser() {
        ManageUsersPage manageUsersPage = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserButton();

        Assert.assertTrue(manageUsersPage.getListOfUserIDs().contains(TestDataUtils.USER_NAME), TestDataUtils.USER_NAME + " username not found");
        Assert.assertTrue(manageUsersPage.getListOfFullNamesOfUsers().contains(TestDataUtils.USER_FULL_NAME), TestDataUtils.USER_FULL_NAME + " fullName not found");
    }

    @TmsLink("e53PF9Xx")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Delete user by 'delete user' icon in the table on the 'Manage users' page and verify that deleted user is not displayed on the 'Manage users' page")
    @Test
    public void testDeleteUser() {
        ProjectMethodsUtils.createNewUser(getDriver(), TestDataUtils.USER_NAME, TestDataUtils.PASSWORD, TestDataUtils.USER_FULL_NAME, TestDataUtils.EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickDeleteUser(TestDataUtils.USER_NAME)
                .clickYes()
                .getListOfUserIDs();

        Assert.assertFalse(listOfUsers.contains(TestDataUtils.USER_NAME));
    }

    @TmsLink("tXliH1OH")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with exist name and verify that error message 'User name is already taken' appeared")
    @Test
    public void testCreateUserWithExistName() {
        ProjectMethodsUtils.createNewUser(getDriver(), TestDataUtils.USER_NAME, TestDataUtils.PASSWORD, TestDataUtils.USER_FULL_NAME, TestDataUtils.EMAIL);

        String errorMessageWhenExistName = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenExistName, "User name is already taken");
    }

    @TmsLink("VGtfAc0W")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with empty 'Password' field and verify that error message 'Password is required' appeared")
    @Test
    public void testCreateUserWithEmptyPassword() {
        String errorMessageWhenEmptyPassword = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyPassword, "Password is required");
    }

    @TmsLink("wO5G0fwq")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with empty 'Confirm password' field and verify that error message 'Password didn't match' appeared")
    @Test
    public void testCreateUserWithEmptyConfirmPassword() {
        String errorMessageWhenEmptyConfirmPassword = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .setEmail(TestDataUtils.EMAIL)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyConfirmPassword, "Password didn't match");
    }

    @TmsLink("CZrnbgVE")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Delete user by 'Delete' on the side menu on the 'Status user' page and verify that deleted user is not displayed on the 'People' page")
    @Test
    public void testDeleteUserGoingFromStatusUserPage() {
        ProjectMethodsUtils.createNewUser(getDriver(), TestDataUtils.USER_NAME, TestDataUtils.PASSWORD, TestDataUtils.USER_FULL_NAME, TestDataUtils.EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople()
                .clickUserID(TestDataUtils.USER_NAME)
                .getSideMenu()
                .clickDelete()
                .clickYes()
                .getSideMenu()
                .clickPeople()
                .getListOfUsers();

        Assert.assertFalse(listOfUsers.contains(TestDataUtils.USER_NAME));
    }

    @TmsLink("0J05H5rt")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Delete user by 'Delete' on the side menu on the 'Configure user' page and verify that deleted user is not displayed on the 'People' page")
    @Test
    public void testDeleteUserGoingFromConfigureUserPage() {
        ProjectMethodsUtils.createNewUser(getDriver(), TestDataUtils.USER_NAME, TestDataUtils.PASSWORD, TestDataUtils.USER_FULL_NAME, TestDataUtils.EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople()
                .clickUserID(TestDataUtils.USER_NAME)
                .getSideMenu()
                .clickConfigure()
                .getSideMenu()
                .clickDelete()
                .clickYes()
                .getSideMenu()
                .clickPeople()
                .getListOfUsers();

        Assert.assertFalse(listOfUsers.contains(TestDataUtils.USER_NAME));
    }

    @TmsLink("yixkCbNN")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Delete user by 'Delete' on the side menu on the 'Builds user' page and verify that deleted user is not displayed on the 'People' page")
    @Test
    public void testDeleteUserGoingFromBuildsUserPage() {
        ProjectMethodsUtils.createNewUser(getDriver(), TestDataUtils.USER_NAME, TestDataUtils.PASSWORD, TestDataUtils.USER_FULL_NAME, TestDataUtils.EMAIL);

        List<String> listOfUsers = new HomePage(getDriver())
                .getSideMenu()
                .clickPeople()
                .clickUserID(TestDataUtils.USER_NAME)
                .getSideMenu()
                .clickBuilds()
                .getSideMenu()
                .clickDelete()
                .clickYes()
                .getSideMenu()
                .clickPeople()
                .getListOfUsers();

        Assert.assertFalse(listOfUsers.contains(TestDataUtils.USER_NAME));
    }

    @TmsLink("xSB0f2rB")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Change default view on 'Jenkins Configure System' page and verify that Default view is changed on the dashboard")
    @Test
    public void testChangeDefaultView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForDashboard(getDriver(), TestDataUtils.LIST_VIEW_NAME);

        HomePage homePage = new HomePage(getDriver());

        String attributeDefaultView = homePage.getAttributeViewAll();
        boolean isViewCanBeRemoved = homePage.getSideMenuList().contains("Delete View");

        String attributeViewAfterChange = homePage
                .getSideMenu()
                .clickManageJenkins()
                .clickConfigureSystem()
                .selectDefaultView(TestDataUtils.LIST_VIEW_NAME)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getAttributeViewAll();

        boolean isViewCanBeRemovedAfterChange = homePage.clickViewAll().getSideMenuList().contains("Delete View");

        Assert.assertFalse(isViewCanBeRemoved);
        Assert.assertNotEquals(attributeViewAfterChange, attributeDefaultView);
        Assert.assertTrue(isViewCanBeRemovedAfterChange);

        ProjectMethodsUtils.changeDefaultView(getDriver(), "All".toLowerCase());
    }

    @TmsLink("GlNQXUvn")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Try to create user with empty E-mail address and verify that error message 'Invalid e-mail address' appeared")
    @Test
    public void testCreateUserWithEmptyEmailAddress() {
        String errorMessageWhenEmptyConfirmPassword = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(TestDataUtils.USER_NAME)
                .setPassword(TestDataUtils.PASSWORD)
                .confirmPassword(TestDataUtils.PASSWORD)
                .setFullName(TestDataUtils.USER_FULL_NAME)
                .clickCreateUserAndGetErrorMessage();

        Assert.assertEquals(errorMessageWhenEmptyConfirmPassword, "Invalid e-mail address");
    }

    @TmsLink("Q3eM8fl6")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.MINOR)
    @Feature("Function")
    @Description("Add a system message to the Jenkins configuration and verify that this message is displayed on the dashboard and on the folder status page")
    @Test
    public void testSetSystemMessage() {
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickConfigureSystem()
                .setSystemMessage(TestDataUtils.DESCRIPTION)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickFolder(TestDataUtils.FOLDER_NAME);

        HomePage homePage = folderStatusPage
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(folderStatusPage.getSystemMessageFromFolder(), TestDataUtils.DESCRIPTION);
        Assert.assertEquals(homePage.getSystemMessageFromDashboard(), TestDataUtils.DESCRIPTION);

        homePage
                .getSideMenu()
                .clickManageJenkins()
                .clickConfigureSystem()
                .setSystemMessage("")
                .clickSaveButton();
    }

    @TmsLink("Q0BEas9B")
    @Owner("Dmitry Starski")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Create New Node by clicking on 'Build Executor Status' on Dashboard")
    @Test
    public void testCreateNewNodeWithValidNameFromDashboard() {
        List<String> nodeNamesList = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildExecutorStatus()
                .getSideMenu()
                .clickNewNode()
                .setName(TestDataUtils.ITEM_NAME)
                .selectPermanentAgent()
                .clickCreateButton()
                .clickSaveButton()
                .getNodeTitles();

        Assert.assertTrue(nodeNamesList.contains(TestDataUtils.ITEM_NAME));
    }

    @TmsLink("SzScD2x7")
    @Owner("Stanislav Akulionok")
    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("The 'Jenkins Credentials Provider' icon is reduced in size when the 'S' button is pressed")
    @Test
    public void testCheckIconSize() {

        boolean iconSize = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .clickManageCredentials()
                .isIconEqualSmallIcon();

        Assert.assertFalse(iconSize);
    }

    @TmsLink("HyjbQZz8")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Delete Node By DropDown On Dashboard")
    @Test(dependsOnMethods = "testCreateNewNodeWithValidNameFromDashboard")
    public void testDeleteNodeByDropDownOnDashboard() {
        List<String> nodeNameList = new HomePage(getDriver())
                .getSideMenu()
                .clickNodeDropdownMenu(TestDataUtils.ITEM_NAME)
                .selectDeleteAgentInDropdown()
                .clickYes()
                .getNodeTitles();

        Assert.assertFalse(nodeNameList.contains(TestDataUtils.ITEM_NAME));
    }

    @Ignore
    @TmsLink("wDWLOqNz")
    @Owner("Ina Ramankova")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Create New Node with valid name by option 'Set up an agent' on Dashboard")
    @Test
    public void testCreateNewNodeWithValidNameByOptionSetUpAnAgent() {
        List<String> nodeNameList = new HomePage(getDriver())
                .clickOnSetUpAnAgent()
                .setName(TestDataUtils.ITEM_NAME)
                .selectPermanentAgent()
                .clickCreateButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getSideMenu()
                .getNodeList();

        Assert.assertTrue(nodeNameList.contains(TestDataUtils.ITEM_NAME));
    }

    @TmsLink("5O9NxH4z")
    @Owner("Elena Grueber")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Create New Node With Existed Name by clicking on 'Build Executor Status' on Dashboard")
    @Test
    public void testCreateNewNodeWithExistedNameFromDashboard() {
        ProjectMethodsUtils.createNewNode(getDriver(), TestDataUtils.ITEM_NAME);
        String errorMessageWhenExistedName = "Agent called ‘" + TestDataUtils.ITEM_NAME + "’ already exists";
        NewNodePage nodePage = new HomePage(getDriver())
                .getSideMenu()
                .clickBuildExecutorStatus()
                .getSideMenu()
                .clickNewNode()
                .setName(TestDataUtils.ITEM_NAME)
                .selectPermanentAgent();

        Assert.assertEquals(nodePage.getNodeNameInvalidMessage(), errorMessageWhenExistedName);
    }
}