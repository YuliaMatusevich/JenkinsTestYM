package model.status_pages;

import model.HomePage;
import model.base.BaseStatusPage;
import model.config_pages.OrgFolderConfigPage;
import model.status_side_menu_component.OrgFolderStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusPage extends BaseStatusPage<OrgFolderStatusPage, OrgFolderStatusSideMenuComponent> {

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[text()='Configure the project']")
    private WebElement linkConfigureTheProject;

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
}
