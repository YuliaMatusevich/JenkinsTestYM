package model.organization_folder;

import model.HomePage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusPage extends BaseStatusPage<OrgFolderStatusPage, OrgFolderStatusSideMenuComponent> {

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    @Override
    protected OrgFolderStatusSideMenuComponent createSideMenuComponent() {
        return new OrgFolderStatusSideMenuComponent(getDriver(),this);
    }

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return new HomePage(getDriver());
    }
}
