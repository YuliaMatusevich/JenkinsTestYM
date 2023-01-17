package model.organization_folder;

import model.RenameItemPage;
import model.HomePage;
import model.base.BaseStatusPage;
import model.base.BlankStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusPage extends BaseStatusPage<OrgFolderStatusPage, OrgFolderStatusSideMenuFrame> {

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    @Override
    protected OrgFolderStatusSideMenuFrame createSideMenuFrame() {
        return new OrgFolderStatusSideMenuFrame(getDriver(),this);
    }

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return new HomePage(getDriver());
    }
}
