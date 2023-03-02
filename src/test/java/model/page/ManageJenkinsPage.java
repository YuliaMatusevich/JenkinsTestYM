package model.page;

import io.qameta.allure.Step;
import model.component.menu.HomeSideMenuComponent;
import model.component.FooterComponent;
import model.page.base.MainBasePageWithSideMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import static runner.TestUtils.*;

public class ManageJenkinsPage extends MainBasePageWithSideMenu<HomeSideMenuComponent> {

    @FindBy(xpath = "//a[@href='configureTools']")
    private WebElement configureTools;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsers;

    @FindBy(xpath = "//div//h1")
    private WebElement header1;

    @FindBy(xpath = "//a[@href  = 'administrativeMonitor/OldData/']/parent::div")
    private WebElement linkManageOldData;

    @FindBy(xpath = "//a[@href='pluginManager']")
    private WebElement linkPluginManager;

    @FindBy(xpath = "//a[@href='configure']")
    private WebElement configureSystem;

    @FindBy(xpath = "//a[@href='credentials']")
    private WebElement manageCredentials;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected HomeSideMenuComponent createSideMenuComponent() {
        return new HomeSideMenuComponent(getDriver());
    }

    public GlobalToolConfigurationPage clickConfigureTools() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), configureTools);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(configureTools)).click();

        return new GlobalToolConfigurationPage(getDriver());
    }

    @Step("Click on 'Manage Users' link in 'Security' section")
    public ManageUsersPage clickManageUsers() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), manageUsers);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(manageUsers)).click();

        return new ManageUsersPage(getDriver());
    }

    public String getTextHeader1ManageJenkins() {
        getWait(10).until(ExpectedConditions.visibilityOf(header1));

        return header1.getText();
    }

    @Step("Click on 'Manage Old Data' link in 'Troubleshooting' section")
    public ManageOldDataPage clickLinkManageOldData() {
        scrollToElement(getDriver(), linkManageOldData);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkManageOldData)).click();

        return new ManageOldDataPage(getDriver());
    }

    @Step("Click on 'Manage Plugins' link in 'System Configuration' section")
    public PluginManagerPage clickLinkManagePlugins() {
        scrollToElement(getDriver(), linkPluginManager);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkPluginManager)).click();

        return new PluginManagerPage(getDriver());
    }

    public FooterComponent moveToJenkinsVersion() {
        scrollToEnd(getDriver());
        WebElement linkJenkins = new HomePage(getDriver()).getFooter().getJenkinsFooterLink();
        getAction().pause(500).moveToElement(getWait(3).until(ExpectedConditions.elementToBeClickable(linkJenkins)))
                .perform();

        return new FooterComponent(getDriver());
    }

    @Step("Click 'Configure System' link on 'System Configuration' section")
    public JenkinsConfigureSystemPage clickConfigureSystem() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), configureSystem);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(configureSystem)).click();

        return new JenkinsConfigureSystemPage(getDriver());
    }

    @Step("Click 'Manage Credentials' link on Security section")
    public JenkinsManageCredentialsPage clickManageCredentials(){
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), manageCredentials);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(manageCredentials)).click();

        return new JenkinsManageCredentialsPage(getDriver());
    }
}
