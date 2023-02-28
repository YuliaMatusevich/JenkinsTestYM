package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JenkinsManageCredentialsPage extends MainBasePage {

    @FindBy(xpath = "//td[@data='Jenkins Credentials Provider']")
    private WebElement iconCredentialsProvider;

    @FindBy(xpath = "//a[@href='/iconSize?16x16']")
    private WebElement iconSmallSize;

    public JenkinsManageCredentialsPage(WebDriver driver) {
        super(driver);
    }

    public String getIconSize() {
        return (iconCredentialsProvider.getCssValue("height"));
    }

    public JenkinsManageCredentialsPage clickSmallSizeIcon() {
        iconSmallSize.click();

        return this;
    }

    @Step("Make sure the icon size is the same")
    public boolean isIconEqualSmallIcon() {
        String str = getIconSize();
        clickSmallSizeIcon();
        String str1 = getIconSize();

        return str.equals(str1);
    }
}
