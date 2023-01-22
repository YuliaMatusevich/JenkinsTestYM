package model.freestyle;

import model.BuildWithParametersPage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectStatusSideMenuFrame extends BaseStatusSideMenuFrame<FreestyleProjectStatusPage> {

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    public FreestyleProjectStatusSideMenuFrame(WebDriver driver, FreestyleProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public BuildWithParametersPage<FreestyleProjectStatusPage> clickBuildWithParameters() {
        buildWithParameters.click();

        return new BuildWithParametersPage<>(getDriver(), page);
    }
}
