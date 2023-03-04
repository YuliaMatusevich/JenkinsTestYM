package model.page.base;

import io.qameta.allure.Step;
import model.component.base.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseStatusPage<Self extends BaseStatusPage<?, ?>, StatusSideMenuComponent extends BaseStatusSideMenuComponent<Self, ?>> extends MainBasePageWithSideMenu<StatusSideMenuComponent> {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "#description>div:first-child")
    private WebElement description;

    @FindBy(id = "view-message")
    private WebElement additionalDescription;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescriptionLink;

    public BaseStatusPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get the name of the page by Header text")
    public String getHeaderText() {
        return getWait(5).until(ExpectedConditions.elementToBeClickable(header)).getText();
    }

    @Step("Get description text")
    public String getDescriptionText() {
        return getWait(5).until(ExpectedConditions.visibilityOf(description)).getText();
    }

    @Step("Get the text of the additional description")
    public String getAdditionalDescriptionText() {
        return additionalDescription.getText();
    }

    @SuppressWarnings("unchecked")
    @Step("Click 'Add description' or 'Edit description'")
    public Self clickAddOrEditDescription() {
        addOrEditDescriptionLink.click();

        return (Self) this;
    }
}
