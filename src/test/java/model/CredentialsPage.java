package model;

import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CredentialsPage extends MainBasePage {

    @FindBy(tagName = "h1")
    private WebElement headerH1;

    public CredentialsPage(WebDriver driver) {

        super(driver);
    }

    public String getHeaderH1Text() {

        return headerH1.getText();
    }
}
