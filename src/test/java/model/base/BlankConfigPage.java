package model.base;

import model.BlankConfigSideMenuComponent;
import org.openqa.selenium.WebDriver;

public abstract class BlankConfigPage<StatusPage extends BaseStatusPage<?, ?>, Self extends BlankConfigPage<?, ?>> extends BaseConfigPage<StatusPage, Self, BlankConfigSideMenuComponent<Self>> {

    @Override
    protected BlankConfigSideMenuComponent<Self> createSideMenuComponent() {
        return new BlankConfigSideMenuComponent<>(getDriver(), (Self)this);
    }

    public BlankConfigPage(WebDriver driver) {
        super(driver);
    }
}
