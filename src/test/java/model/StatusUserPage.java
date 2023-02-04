package model;

import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusUserPage extends MainBasePage {

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

    @FindBy(xpath = "//div[@id='tasks']//span[contains(., 'Delete')]")
    private WebElement delete;

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(linkText = "Builds")
    private WebElement builds;

    public StatusUserPage(WebDriver driver) {
        super(driver);
    }

    public StatusUserPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    public String getH1Title() {
        return h1Title.getText();
    }

    public StatusUserPage clickAddDescriptionLink() {
        addDescriptionLink.click();

        return this;
    }

    public StatusUserPage clearDescriptionInputField() {
        getWait(10).until(ExpectedConditions.visibilityOf(descriptionInputField));
        descriptionInputField.clear();

        return this;
    }

    public StatusUserPage setDescriptionField(String text) {
        descriptionInputField.sendKeys(text);

        return this;
    }

    public StatusUserPage clickPreviewLink() {
        previewLink.click();

        return this;
    }

    public String getPreviewText() {

        return previewField.getText();
    }

    public StatusUserPage clickHidePreviewLink() {
        hidePreviewLink.click();

        return this;
    }

    public boolean isDisplayedPreviewField() {

        return previewField.isDisplayed();
    }

    public StatusUserPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public String getDescriptionText() {

        return displayedDescriptionText.getText();
    }

    public String getUserIDText() {

        return userID.getText();
    }

    public DeletePage<HomePage> clickDelete() {
        delete.click();

        return new DeletePage<>(getDriver(), new HomePage(getDriver()));
    }

    public ConfigureUserPage clickConfigure() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configure)).click();

        return new ConfigureUserPage(getDriver());
    }

    public BuildsUserPage clickBuilds() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(builds)).click();

        return new BuildsUserPage(getDriver());
    }
}