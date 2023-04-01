package model.page.view;

import io.qameta.allure.Step;
import model.page.base.BaseEditViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditGlobalViewPage extends BaseEditViewPage {

    @FindBy(xpath = "//label[text()='Filter build executors']")
    private WebElement filterBuildExecutorsOptionCheckBox;

    @FindBy(xpath = "//label[text()='Filter build queue']")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(xpath = "//input[@name = 'filterQueue']")
    private WebElement filterBuildExecutorsCheckBoxInput;

    @FindBy(xpath = "//input[@name = 'filterExecutors']")
    private WebElement filterBuildQueueCheckBoxInput;

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(css = ".jenkins-form-description")
    private WebElement uniqueTextOnGlobalViewEditPage;

    @FindBy(css = "#main-panel p")
    private WebElement errorPageDetailsText;

    @FindBy(css = ".bd ul")
    private WebElement listAddColumnDropDownMenuItems;

    @FindBy(css = "#main-panel h1")
    private WebElement errorPageHeader;

    public EditGlobalViewPage(WebDriver driver) {
        super(driver);
    }

    public EditGlobalViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

        return this;
    }

    @Step("Get note text in the 'View name' section")
    public String getUniqueTextOnGlobalViewEditPage() {

        return uniqueTextOnGlobalViewEditPage.getText();
    }

    public EditGlobalViewPage selectFilterBuildExecutorsOptionCheckBox() {
        filterBuildExecutorsOptionCheckBox.click();

        return this;
    }

    public EditGlobalViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.click();

        return this;
    }

    public boolean isFilterBuildQueueOptionCheckBoxSelected() {

        return filterBuildQueueCheckBoxInput.getAttribute("checked").equals("true");
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxSelected() {

        return filterBuildExecutorsCheckBoxInput.getAttribute("checked").equals("true");
    }

    @Step("Get 'Error page' header text")
    public String getErrorPageHeader() {

        return errorPageHeader.getText();
    }

    @Step("Is 'Error page' text equals to the text that contains a specified special character")
    public boolean isCorrectErrorPageDetailsText(char illegalCharacter) {

        return errorPageDetailsText.getText().equals(String.format("‘%c’ is an unsafe character", illegalCharacter));
    }

    public String getAddColumnDropDownMenuItemTextByOrder(int itemNumber) {

        return listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).getText();
    }

    public EditGlobalViewPage clickAddColumnDropDownMenuItemByOrder(int itemNumber) {
        listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).click();

        return new EditGlobalViewPage(getDriver());
    }
}