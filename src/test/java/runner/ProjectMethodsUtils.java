package runner;

import model.HomePage;
import org.openqa.selenium.WebDriver;

public class ProjectMethodsUtils {
    public static void createNewPipelineProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewFreestyleProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMultiConfigurationProject(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMultibranchPipeline(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectMultibranchPipeline()
                .clickOkMultibranchPipeline()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewOrganizationFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(name)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewGlobalViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setGlobalViewType()
                .clickCreateButtonToEditGlobalView()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewListViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMyViewForMyViews(WebDriver driver, String name) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewListViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewMyViewViewForDashboard(WebDriver driver, String name) {
        new HomePage(driver)
                .clickAddViewLink()
                .setViewName(name)
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .getHeader().clickJenkinsNameIcon();
    }

    public static void createNewUser(WebDriver driver, String username, String password, String fullName, String email) {
        new HomePage(driver)
                .getSideMenuFrame()
                .clickManageJenkins()
                .clickManageUsers()
                .clickCreateUser()
                .setUsername(username)
                .setPassword(password)
                .confirmPassword(password)
                .setFullName(fullName)
                .setEmail(email)
                .clickCreateUserButton()
                .getHeader().clickJenkinsNameIcon();
    }
}
