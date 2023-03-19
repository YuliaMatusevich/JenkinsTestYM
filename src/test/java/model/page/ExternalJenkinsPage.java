package model.page;

import io.qameta.allure.Step;
import model.page.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExternalJenkinsPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement textHeaderJenkins;

    public ExternalJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get the header text")
    public String getHeaderText() {
        getWait(5).until(ExpectedConditions.visibilityOf(textHeaderJenkins));

        return textHeaderJenkins.getText();
    }
}
