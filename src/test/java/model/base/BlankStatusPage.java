package model.base;

import model.BlankStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;

public abstract class BlankStatusPage<Self extends BlankStatusPage<?>> extends BaseStatusPage<Self, BlankStatusSideMenuComponent<Self>>{

    @Override
    protected BlankStatusSideMenuComponent<Self> createSideMenuComponent() {
        return new BlankStatusSideMenuComponent<>(getDriver(), (Self)this);
    }

    public BlankStatusPage(WebDriver driver) {
        super(driver);
    }
}
