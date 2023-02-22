package model;

import io.qameta.allure.Step;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuildStatusPage extends MainBasePage {

    @FindBy(linkText = "Console Output")
    private WebElement consoleOutput;

    public BuildStatusPage(WebDriver driver) {
        super(driver);
    }
    @Step("Click on ‘Console output’ on side menu")
    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutput.click();

        return new ConsoleOutputPage(getDriver());
    }
}
