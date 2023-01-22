package model.freestyle;

import model.BuildWithParametersPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectStatusSideMenuComponent extends BaseStatusSideMenuComponent<FreestyleProjectStatusPage> {

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    public FreestyleProjectStatusSideMenuComponent(WebDriver driver, FreestyleProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public BuildWithParametersPage<FreestyleProjectStatusPage> clickBuildWithParameters() {
        buildWithParameters.click();

        return new BuildWithParametersPage<>(getDriver(), page);
    }
}
