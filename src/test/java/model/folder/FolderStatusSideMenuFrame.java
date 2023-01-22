package model.folder;

import model.DeletePage;
import model.NewItemPage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderStatusSideMenuFrame extends BaseStatusSideMenuFrame<FolderStatusPage> {

    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @FindBy(linkText = "Delete Folder")
    private WebElement deleteFolder;

    public FolderStatusSideMenuFrame(WebDriver driver, FolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public NewItemPage clickNewItem() {
        newItem.click();

        return new NewItemPage(getDriver());
    }

    public DeletePage<FolderStatusPage> clickDeleteFolder() {
        deleteFolder.click();

        return new DeletePage<>(getDriver(), page);
    }
}