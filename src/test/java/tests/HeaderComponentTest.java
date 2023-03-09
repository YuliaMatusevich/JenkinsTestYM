package tests;

import model.component.HeaderComponent;
import model.page.*;
import model.page.view.MyViewsPage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class HeaderComponentTest extends BaseTest {

    @Test
    public void testIsJenkinsNameIconExist() {

        boolean actualResult = new HomePage(getDriver()).getHeader().isJenkinsNameIconDisplayed();

        Assert.assertTrue(actualResult);
    }

    @Ignore
    @Test
    public void testUserIdInUserAccountLinkAndInUserPage() {

        String usernameInUserAccountLink = new HomePage(getDriver())
                .getHeader()
                .getUserNameText();

        String usernameInUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .getUserIDText();

        Assert.assertEquals(usernameInUserPage,
                String.format("Jenkins User ID: %s", usernameInUserAccountLink));
    }

    @Test
    public void testCountAndNamesItemsInUserDropdownMenu() {
        int itemsCount = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .getItemsCountInUserDropdownMenu();

        String itemsNames = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .getItemsNamesInUserDropdownMenu();

        Assert.assertEquals(itemsCount, 4);
        Assert.assertEquals(itemsNames, "Builds Configure My Views Credentials");
    }

    @Test
    public void testUserDropdownMenuToOpenBuildsUserPage() {
        BuildsUserPage buildsUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickBuildsItemInUserDropdownMenu();

        Assert.assertEquals(buildsUserPage.getHeaderH1Text(),
                "Builds for admin");
    }

    @Test
    public void testLogoHeadIconIsSeen() {

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getHeader().isJenkinsHeadIconDisplayed());
        Assert.assertTrue(homePage.getHeader().isJenkinsHeadIconEnabled());
    }

    @Test
    public void testManageJenkinsClickNameIconToReturnToTheMainPage() {
        ManageJenkinsPage manageJenkinsPage = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins();

        Assert.assertEquals(manageJenkinsPage.getCurrentURL(), "http://localhost:8080/manage/");
        Assert.assertEquals(manageJenkinsPage.getTextHeader1ManageJenkins(), "Manage Jenkins");

        HomePage homePage = manageJenkinsPage.getHeader().clickJenkinsNameIcon();

        Assert.assertEquals(homePage.getCurrentURL(), "http://localhost:8080/");
        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }

    @Test
    public void testUserDropdownMenuToOpenConfigureUserPage() {
        ConfigureUserPage configureUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickConfigureItemInUserDropdownMenu();

        Assert.assertEquals(configureUserPage.getAddNewTokenButtonName(),
                "Add new Token");
    }

    @Test
    public void testUserDropdownMenuToOpenMyViewsUserPage() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickMyViewItemInUserDropdownMenu();

        Assert.assertEquals(myViewsPage.getMyViewsTopMenuLinkText(),
                "My Views");
    }

    @Test
    public void testUserDropdownMenuToOpenCredentialsUserPage() {
        CredentialsPage credentialsPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickCredentialsItemInUserDropdownMenu();

        Assert.assertEquals(credentialsPage.getHeaderH1Text(),
                "Credentials");
    }

    @Test
    public void testReturnFromNewItemPageToHomePageByClickingOnHeadIcon() {

        String actualURL = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .getHeader()
                .clickJenkinsNameIcon()
                .getCurrentURL();

        Assert.assertEquals(actualURL, "http://localhost:8080/");
    }

    @Test
    public void testCheckTheAppropriateSearchResult() {
        String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 1000);
        String searchRequest = "organiza";

        List<String> searchResults = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(organizationFolderName)
                .selectOrgFolderType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getHeader()
                .setSearchFieldAndClickEnter(searchRequest)
                .getSearchResultList();

        Assert.assertTrue(searchResults.size() > 0);

        for (String result : searchResults) {
            Assert.assertTrue(result.contains(searchRequest));
        }
    }

    @Test
    public void testLogoHeadIconReloadMainPage() {
        String text = "Salut!";

        HeaderComponent headerComponent = new HomePage(getDriver())
                .getHeader()
                .setTextInSearchField(text);

        Assert.assertEquals(headerComponent.getSearchFieldValue(), text);

        headerComponent.clickJenkinsHeadIcon();

        Assert.assertEquals(headerComponent.getSearchFieldValue(), "");
    }
}