package model.views;

import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditGlobalViewPage extends MainBasePage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(css = "input[name=filterExecutors]")
    private WebElement filterBuildExecutorsOptionCheckBox;

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

    public EditGlobalViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditGlobalViewPage selectFilterBuildExecutorsOptionCheckBox() {
        filterBuildExecutorsOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public boolean isFilterBuildQueueOptionCheckBoxSelected() {

        return filterBuildQueueOptionCheckBox.isSelected();
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxSelected() {

        return filterBuildExecutorsOptionCheckBox.isSelected();
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