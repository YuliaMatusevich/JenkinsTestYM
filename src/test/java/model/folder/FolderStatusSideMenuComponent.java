package model.folder;

import model.NewItemPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderStatusSideMenuComponent extends BaseStatusSideMenuComponent<FolderStatusPage> {

    @FindBy(linkText = "New Item")
    private WebElement newItem;

    public FolderStatusSideMenuComponent(WebDriver driver, FolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }
}