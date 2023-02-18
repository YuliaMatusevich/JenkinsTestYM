package model.views;

import model.base.BaseEditViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditMyViewPage extends BaseEditViewPage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editViewLink;

    public EditMyViewPage(WebDriver driver) {
        super(driver);
    }

    public EditMyViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditMyViewPage clickEditMyView() {
        editViewLink.click();

        return new EditMyViewPage(getDriver());
    }
}
