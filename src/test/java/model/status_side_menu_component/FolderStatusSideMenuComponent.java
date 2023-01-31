package model.status_side_menu_component;

import model.NewItemPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.FolderConfigPage;
import model.status_pages.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderStatusSideMenuComponent extends BaseStatusSideMenuComponent<FolderStatusPage, FolderConfigPage> {

    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @Override
    protected FolderConfigPage createConfigPage() {
        return new FolderConfigPage(getDriver());
    }

    public FolderStatusSideMenuComponent(WebDriver driver, FolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }
}