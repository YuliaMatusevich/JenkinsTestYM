package model.organization_folder;

import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusSideMenuFrame extends BaseStatusSideMenuFrame<OrgFolderStatusPage> {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(xpath = "//div[@id='tasks']//a[contains(@href, 'delete')]")
    private WebElement deleteOrganizationFolder;

    public OrgFolderStatusSideMenuFrame(WebDriver driver, OrgFolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public OrgFolderConfigPage clickConfigure() {
        configure.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusPage clickDeleteOrganizationFolder() {
        deleteOrganizationFolder.click();

        return page;
    }
}
