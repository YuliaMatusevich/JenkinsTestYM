package model.freestyle;

import model.base.side_menu.BaseConfigSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectConfigSideMenuComponent extends BaseConfigSideMenuComponent<FreestyleProjectConfigPage> {

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement linkSourceCodeManagement;

    public FreestyleProjectConfigSideMenuComponent(WebDriver driver, FreestyleProjectConfigPage configPage) {
        super(driver, configPage);
    }

    public FreestyleProjectConfigPage clickLinkSourceCodeManagement() {
        linkSourceCodeManagement.click();

        return page;
    }
}