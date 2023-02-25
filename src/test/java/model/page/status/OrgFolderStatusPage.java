package model.page.status;

import model.page.HomePage;
import model.page.base.BaseStatusPage;
import model.page.config.OrgFolderConfigPage;
import model.component.menu.status.OrgFolderStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;

public class OrgFolderStatusPage extends BaseStatusPage<OrgFolderStatusPage, OrgFolderStatusSideMenuComponent> {

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[text()='Configure the project']")
    private WebElement linkConfigureTheProject;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableOrgFolderButton;

    @FindBy(id = "enable-project")
    private WebElement warningTextAboutDisableOrgFolder;

    @Override
    protected OrgFolderStatusSideMenuComponent createSideMenuComponent() {
        return new OrgFolderStatusSideMenuComponent(getDriver(), this);
    }

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return new HomePage(getDriver());
    }

    public OrgFolderConfigPage clickLinkConfigureTheProject() {
        linkConfigureTheProject.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusPage clickDisableButton() {
        disableOrgFolderButton.click();

        return this;
    }

    public HashMap<String, String> getWarningTextAboutDisabledOrgFolder() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Warning Message", warningTextAboutDisableOrgFolder.getText().substring(0, warningTextAboutDisableOrgFolder.getText().indexOf(" \n")));
        hashMap.put("Message Color", warningTextAboutDisableOrgFolder.getCssValue("color"));

        return hashMap;
    }
}
