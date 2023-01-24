package runner;

import model.HomePage;
import model.views.EditGlobalViewPage;
import model.views.EditListViewPage;
import model.views.EditMyViewPage;
import org.openqa.selenium.WebDriver;

public class ProjectMethodsUtils {
    public static void createNewPipelineProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewFreestyleProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMultiConfigurationProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMultibranchPipeline(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewOrganizationFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewGlobalViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setGlobalViewType()
                .clickCreateButton(new EditGlobalViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewListViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMyViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButton(new EditMyViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewListViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMyViewViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButton(new EditMyViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewUser(WebDriver driver, String username, String password, String fullName, String email) {
        new HomePage(driver)
                .getSideMenu()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(username)
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(fullName)
                .setEmail(email)
                .clickCreateUserButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void editDescriptionUserActiveField(WebDriver driver, String name) {
        new HomePage(driver)
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(name);
    }
}