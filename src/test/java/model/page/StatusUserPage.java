package model.page;

import io.qameta.allure.Step;
import model.component.menu.UserSideMenuComponent;
import model.page.base.MainBasePageWithSideMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusUserPage extends MainBasePageWithSideMenu<UserSideMenuComponent> {

    @FindBy(xpath = "//h1")
    private WebElement h1Title;

    @FindBy(id = "description-link")
    private WebElement addDescriptionLink;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement descriptionInputField;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement previewLink;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewField;

    @FindBy(xpath = "//a[@class='textarea-hide-preview']")
    private WebElement hidePreviewLink;

    @FindBy(id = "yui-gen1-button")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement displayedDescriptionText;

    @FindBy(xpath = "//div[@id='main-panel']/div[contains(text(), 'ID')]")
    private WebElement userID;

    public StatusUserPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected UserSideMenuComponent createSideMenuComponent() {
        return new UserSideMenuComponent(getDriver());
    }

    @Step("Refresh the page")
    public StatusUserPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    @Step("Get full username from the title on the main panel")
    public String getH1Title() {
        return h1Title.getText();
    }

    @Step("Click the 'Add description' link")
    public StatusUserPage clickAddDescriptionLink() {
        addDescriptionLink.click();

        return this;
    }

    @Step("Clear the 'Description' field")
    public StatusUserPage clearDescriptionInputField() {
        getWait(10).until(ExpectedConditions.visibilityOf(descriptionInputField));
        descriptionInputField.clear();

        return this;
    }

    @Step("Insert the description '{text}' into the description field")
    public StatusUserPage setDescriptionField(String text) {
        descriptionInputField.sendKeys(text);

        return this;
    }

    @Step("Click 'Preview' link below the description field")
    public StatusUserPage clickPreviewLink() {
        previewLink.click();

        return this;
    }

    @Step("Get description preview text")
    public String getPreviewText() {

        return previewField.getText();
    }

    @Step("Click 'Hide preview' link below the description field")
    public StatusUserPage clickHidePreviewLink() {
        hidePreviewLink.click();

        return this;
    }

    public boolean isDisplayedPreviewField() {

        return previewField.isDisplayed();
    }

    @Step("Click 'Save' button")
    public StatusUserPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    @Step("Get description text")
    public String getDescriptionText() {

        return displayedDescriptionText.getText();
    }

    @Step("Get user ID text")
    public String getUserIDText() {

        return userID.getText();
    }
}