package model.views;

import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditMyViewPage extends MainBasePage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

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

}
