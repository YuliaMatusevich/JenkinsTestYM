package model.component.menu.status;

import model.page.NewItemPage;
import model.component.base.BaseStatusSideMenuComponent;
import model.page.config.FolderConfigPage;
import model.page.status.FolderStatusPage;
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

    public NewItemPage<?> clickNewItem() {
        newItem.click();

        return new NewItemPage<>(getDriver(), null);
    }
}