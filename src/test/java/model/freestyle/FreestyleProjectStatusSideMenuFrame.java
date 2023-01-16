package model.freestyle;

import model.BuildWithParametersPage;
import model.RenameItemPage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectStatusSideMenuFrame extends BaseStatusSideMenuFrame<FreestyleProjectStatusPage> {

    @FindBy(linkText = "Build with Parameters")
    private WebElement linkBuildWithParameters;

    @FindBy(linkText = "Rename")
    private WebElement linkRename;

    public FreestyleProjectStatusSideMenuFrame(WebDriver driver, FreestyleProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public BuildWithParametersPage<FreestyleProjectStatusPage> clickBuildWithParameters() {
        linkBuildWithParameters.click();

        return new BuildWithParametersPage<>(getDriver(), page);
    }

    public RenameItemPage<FreestyleProjectStatusPage> clickRename() {
        linkRename.click();

        return new RenameItemPage<>(getDriver(), page);
    }
}
