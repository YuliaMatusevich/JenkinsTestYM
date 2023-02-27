package model.component;

import io.qameta.allure.Step;
import model.component.base.BaseComponent;
import model.page.*;
import model.page.status.MultiConfigurationProjectStatusPage;
import model.page.view.MyViewsPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.List;

public class HeaderComponent extends BaseComponent {

    @FindBy(id = "jenkins-head-icon")
    private WebElement jenkinsHeadIcon;

    @FindBy(id = "jenkins-name-icon")
    private WebElement jenkinsNameIcon;

    @FindBy(xpath = "//div/a[@class='model-link']")
    private WebElement iconUserName;

    @FindBy(id = "search-box")
    private WebElement searchField;

    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logOut;

    @FindBy(css = "#page-header .jenkins-menu-dropdown-chevron")
    private WebElement userDropdownMenu;

    @FindBy(linkText = "Credentials")
    private WebElement credentialsItemInUserDropdownMenu;

    @FindBy(linkText = "My Views")
    private WebElement myViewItemInUserDropdownMenu;

    @FindBy(linkText = "Configure")
    private WebElement configureItemInUserDropdownMenu;

    @FindBy(linkText = "Builds")
    private WebElement buildsItemInUserDropdownMenu;

    @FindBy(css = ".first-of-type > .yuimenuitem")
    private List<WebElement> userDropdownMenuItems;

    @FindBy(id = "jenkins-home-link")
    private WebElement jenkinsHomeLink;

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public HomePage clickJenkinsHeadIcon() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(jenkinsHeadIcon)).click();

        return new HomePage(getDriver());
    }

    public HomePage clickJenkinsNameIcon() {
        jenkinsNameIcon.click();

        return new HomePage(getDriver());
    }

    public LoginPage clickLogOut() {
        logOut.click();

        return new LoginPage(getDriver());
    }

    public boolean isJenkinsNameIconDisplayed() {

        return jenkinsNameIcon.isDisplayed();
    }

    public boolean isJenkinsHeadIconDisplayed() {

        return jenkinsNameIcon.isDisplayed();
    }

    public boolean isJenkinsHeadIconEnabled() {

        return jenkinsNameIcon.isEnabled();
    }

    public StatusUserPage clickUserIcon() {
        iconUserName.click();

        return new StatusUserPage(getDriver());
    }

    @Step("Get username from the header")
    public String getUserNameText() {

        return iconUserName.getText();
    }

    public SearchResultPage setSearchFieldAndClickEnter(String request) {
        searchField.sendKeys(request);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(searchField)).sendKeys(Keys.ENTER);

        return new SearchResultPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage setSearchAndClickEnter(String request) {
        searchField.sendKeys(request);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(searchField)).sendKeys(Keys.ENTER);

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public HeaderComponent clickUserDropdownMenu() {
        userDropdownMenu.click();

        return this;
    }

    public CredentialsPage clickCredentialsItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                credentialsItemInUserDropdownMenu)).click();

        return new CredentialsPage(getDriver());
    }

    public MyViewsPage clickMyViewItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                myViewItemInUserDropdownMenu)).click();

        return new MyViewsPage(getDriver());
    }

    public ConfigureUserPage clickConfigureItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                configureItemInUserDropdownMenu)).click();

        return new ConfigureUserPage(getDriver());
    }

    public BuildsUserPage clickBuildsItemInUserDropdownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(
                buildsItemInUserDropdownMenu)).click();

        return new BuildsUserPage(getDriver());
    }

    public int getItemsCountInUserDropdownMenu() {
        int itemsCount = 0;
        for (WebElement item : getWait(5).until(
                ExpectedConditions.visibilityOfAllElements(
                        userDropdownMenuItems))) {
            itemsCount++;
        }

        return itemsCount;
    }

    public String getItemsNamesInUserDropdownMenu() {
        StringBuilder itemsNames = new StringBuilder();
        for (WebElement item : getWait(5).until(
                ExpectedConditions.visibilityOfAllElements(
                        userDropdownMenuItems))) {
            itemsNames.append(item.getText()).append(" ");
        }

        return itemsNames.toString().trim();
    }

    public HeaderComponent setTextInSearchField(String str) {
        searchField.sendKeys(str);
        getWait(3).until(ExpectedConditions.attributeToBeNotEmpty(searchField, "value"));

        return this;
    }

    public String getSearchFieldValue() {

        return searchField.getAttribute("value");
    }

    public HomePage clickJenkinsHomeLink() {
        jenkinsHomeLink.click();
        return new HomePage(getDriver());
    }
}