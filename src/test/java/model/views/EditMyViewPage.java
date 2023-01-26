package model.views;

import model.base.BaseViewEditPage;
import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditMyViewPage extends BaseViewEditPage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editViewLink;

    public EditMyViewPage(WebDriver driver) {
        super(driver);
    }

    public EditMyViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public EditMyViewPage clickEditMyView() {
        editViewLink.click();

        return new  EditMyViewPage(getDriver());
    }

}
