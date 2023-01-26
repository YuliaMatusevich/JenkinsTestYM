package model.views;

import model.base.BaseViewEditPage;
import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditGlobalViewPage extends BaseViewEditPage {

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

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

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

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public EditGlobalViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

        return this;
    }

    public String getUniqueTextOnGlobalViewEditPage() {

        return uniqueTextOnGlobalViewEditPage.getText();
    }

    public EditGlobalViewPage selectFilterBuildExecutorsOptionCheckBox() {
//        filterBuildExecutorsOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();
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

    public String getErrorPageHeader() {

        return errorPageHeader.getText();
    }

    public boolean isCorrectErrorPageDetailsText(char illegalCharacter) {

        return errorPageDetailsText.getText().equals(String.format("‘%c’ is an unsafe character", illegalCharacter));
    }

    public String getAddColumnDropDownMenuItemTextByOrder(int itemNumber){

        return listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).getText();
    }

    public EditGlobalViewPage clickAddColumnDropDownMenuItemByOrder(int itemNumber){
        listAddColumnDropDownMenuItems.findElement(By.cssSelector(String.format("li:nth-child(%d)", itemNumber))).click();

        return new EditGlobalViewPage(getDriver());
    }

}