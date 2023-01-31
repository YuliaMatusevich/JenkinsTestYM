package model.status_side_menu_component;

import model.BuildWithParametersPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.FreestyleProjectConfigPage;
import model.status_pages.FreestyleProjectStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectStatusSideMenuComponent extends BaseStatusSideMenuComponent<FreestyleProjectStatusPage, FreestyleProjectConfigPage> {

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    @Override
    protected FreestyleProjectConfigPage createConfigPage() {
        return new FreestyleProjectConfigPage(getDriver());
    }

    public FreestyleProjectStatusSideMenuComponent(WebDriver driver, FreestyleProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public BuildWithParametersPage<FreestyleProjectStatusPage> clickBuildWithParameters() {
        buildWithParameters.click();

        return new BuildWithParametersPage<>(getDriver(), page);
    }
}
