package model.component.base;

import org.openqa.selenium.WebDriver;
import runner.BaseModel;

public abstract class BaseComponent extends BaseModel {

    public BaseComponent(WebDriver driver) {
        super(driver);
    }
}