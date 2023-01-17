package model.organization_folder;

import model.RenameItemPage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusSideMenuFrame extends BaseStatusSideMenuFrame<OrgFolderStatusPage> {
    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(xpath = "//div[@id='tasks']//a[contains(@href, 'delete')]")
    private WebElement deleteButton;

    public OrgFolderStatusSideMenuFrame(WebDriver driver, OrgFolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public RenameItemPage<OrgFolderStatusPage> clickRenameSideMenu() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new OrgFolderStatusPage(getDriver()));
    }

    public OrgFolderConfigPage clickConfigureSideMenu() {
        configureButton.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusPage clickDeleteOrganizationFolder() {
        deleteButton.click();

        return new OrgFolderStatusPage(getDriver());
    }

}
