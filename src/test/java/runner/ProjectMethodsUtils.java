package runner;

import io.qameta.allure.Step;
import model.HomePage;
import model.views.EditGlobalViewPage;
import model.views.EditListViewPage;
import model.views.EditMyViewPage;
import org.openqa.selenium.WebDriver;

public class ProjectMethodsUtils {

    @Step("Create new pipeline project and return to dashboard")
    public static void createNewPipelineProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewFreestyleProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMultiConfigurationProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectMultiConfigurationProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMultibranchPipeline(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectMultibranchPipelineType()
                .clickOkButton()
                .clickSaveButton()
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewOrganizationFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickNewItem()
                .setItemName(name)
                .selectOrgFolderType()
                .clickOkButton()
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
                .selectIncludeGlobalView()
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
                .selectListView()
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
                .selectMyView()
                .clickCreateButton(new EditMyViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewListViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .selectListView()
                .clickCreateButton(new EditListViewPage(driver))
                .getHeader()
                .clickJenkinsNameIcon();
    }

    public static void createNewMyViewViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .selectMyView()
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

    public static void changeDefaultView(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenu()
                .clickManageJenkins()
                .clickConfigureSystem()
                .selectDefaultView(name)
                .clickSaveButton();
    }
}