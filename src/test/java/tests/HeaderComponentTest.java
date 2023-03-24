package tests;

import com.sun.source.tree.AssertTree;
import io.qameta.allure.*;
import model.component.HeaderComponent;
import model.page.*;
import model.page.view.MyViewsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class HeaderComponentTest extends BaseTest {

    @TmsLink("9KuZvMb8")
    @Owner("ServachakM")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description ("Verify if Jenkins Icon is displayed in the Header")
    @Test
    public void testIsJenkinsNameIconExist() {

        boolean actualResult = new HomePage(getDriver()).getHeader().isJenkinsNameIconDisplayed();

        Assert.assertTrue(actualResult);
    }

    @TmsLink("aJTanIdo")
    @Owner("Snezhana F")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify that User name in the header and on the User page are the same and the User page has User ID")
    @Test
    public void testUserNameInHeaderAndInUserPage() {
        HomePage homePage = new HomePage(getDriver());

        String userNameInHeader = homePage
                .getHeader()
                .getUserNameText();

        StatusUserPage statusUserPage = homePage
                .getHeader()
                .clickUserIcon();

        Assert.assertEquals(statusUserPage.getH1Title(), userNameInHeader);
        Assert.assertEquals(statusUserPage.getUserIDText(), "Jenkins User ID: admin");
    }

    @Owner("Vadim Trefilov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify a quantity and titles of User dropdown menu lines")
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

    @Owner("Vadim Trefilov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify if 'Builds' link in the User dropdown menu leads to the expected page named as 'Builds for admin'")
    @Test
    public void testUserDropdownMenuToOpenBuildsUserPage() {
        BuildsUserPage buildsUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickBuildsItemInUserDropdownMenu();

        Assert.assertEquals(buildsUserPage.getHeaderH1Text(),
                "Builds for admin");
    }

    @Owner("Irina Samo")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify that Jenkins icon is displayed and enabled in the Header")
    @Test
    public void testLogoHeadIconIsSeen() {

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getHeader().isJenkinsHeadIconDisplayed());
        Assert.assertTrue(homePage.getHeader().isJenkinsHeadIconEnabled());
    }

    @Owner("Irina Samo")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify that click on Jenkins icon leads to the Main page when click on Manage Jenkins page")
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

    @Owner("Vadim Trefilov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if 'Configure' link in the User dropdown menu leads to the expected page " +
            "that contains 'Add new Token' section")
    @Test
    public void testUserDropdownMenuToOpenConfigureUserPage() {
        ConfigureUserPage configureUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickConfigureItemInUserDropdownMenu();

        Assert.assertEquals(configureUserPage.getAddNewTokenButtonName(),
                "Add new Token");
    }

    @Owner("Vadim Trefilov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify if click on 'My views' link in the User dropdown menu leads to the views page " +
            "that confirmed by 'My views' breadcrumb" )
    @Test
    public void testUserDropdownMenuToOpenMyViewsUserPage() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickMyViewItemInUserDropdownMenu();

        Assert.assertEquals(myViewsPage.getMyViewsTopMenuLinkText(),
                "My Views");
    }

    @Owner("Vadim Trefilov")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if 'Credentials' link in the User dropdown menu leads to the expected page " +
            "named as 'Credentials'")
    @Test
    public void testUserDropdownMenuToOpenCredentialsUserPage() {
        CredentialsPage credentialsPage = new HomePage(getDriver())
                .getHeader()
                .clickUserDropdownMenu()
                .clickCredentialsItemInUserDropdownMenu();

        Assert.assertEquals(credentialsPage.getHeaderH1Text(),
                "Credentials");
    }

    @Owner("NadyaSenyukova")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify if click on the Jenkins icon leads to the Main page that confirmed by base URL")
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

    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify that list of search results is not empty and contains expected search request")
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

    @Owner("Irina Samo")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description ("Verify that 'Search' field value contains expected text {text} and clear after click on the Jenkins icon")
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