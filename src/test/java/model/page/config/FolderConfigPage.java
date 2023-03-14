package model.page.config;

import io.qameta.allure.Step;
import model.page.base.BaseConfigPage;
import model.page.status.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderConfigPage extends BaseConfigPage<FolderStatusPage, FolderConfigPage> {

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea[@name='_.description']")
    private WebElement description;

    @Override
    protected FolderStatusPage createStatusPage() {
        return new FolderStatusPage(getDriver());
    }

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{secondJobName}' into 'Display Name' field")
    public FolderConfigPage setDisplayName(String secondJobName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(displayName));
        displayName.sendKeys(secondJobName);

        return this;
    }

    @Step("Input '{inputDescription}' into 'Description' field")
    public FolderConfigPage setDescription(String inputDescription) {
        getWait(5).until(ExpectedConditions.visibilityOf(description)).click();
        description.sendKeys(inputDescription);

        return this;
    }
}