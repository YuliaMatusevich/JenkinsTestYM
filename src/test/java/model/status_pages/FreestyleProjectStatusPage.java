package model.status_pages;

import model.HomePage;
import model.base.BaseStatusPage;
import model.status_side_menu_component.FreestyleProjectStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectStatusPage extends BaseStatusPage<FreestyleProjectStatusPage, FreestyleProjectStatusSideMenuComponent> {

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescriptionText;

    @FindBy(id = "yui-gen2")
    private WebElement buttonSave;

    @FindBy(id = "enable-project")
    private WebElement warningForm;

    @FindBy(name = "Submit")
    private WebElement disableProjectBtn;

    @FindBy(linkText = "Edit description")
    private WebElement buttonEditDescription;

    @Override
    protected FreestyleProjectStatusSideMenuComponent createSideMenuComponent() {
        return new FreestyleProjectStatusSideMenuComponent(getDriver(), this);
    }

    public FreestyleProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectStatusPage clickDisableProjectBtn() {
        disableProjectBtn.click();

        return this;
    }

    public FreestyleProjectStatusPage inputAndSaveDescriptionText(String description) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).clear();
        fieldDescriptionText.sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
    }

    public HomePage confirmAlertAndDeleteProject() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public String getWarningMsg() {
        return warningForm.getText().substring(0, warningForm.getText().indexOf("\n"));
    }

    public FreestyleProjectStatusPage clickButtonEditDescription() {
        buttonEditDescription.click();

        return this;
    }

    public FolderStatusPage confirmAlertAndDeleteProjectFromFolder() {
        getDriver().switchTo().alert().accept();

        return new FolderStatusPage(getDriver());
    }
}