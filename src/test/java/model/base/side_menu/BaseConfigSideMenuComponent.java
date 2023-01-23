package model.base.side_menu;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class BaseConfigSideMenuComponent<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseSideMenuWithGenericComponent<ConfigPage> {

    @FindBy(xpath = "//button[@data-section-id='general']")
    private WebElement linkGeneral;

    @FindBy(css = "button.task-link")
    private List<WebElement> configSideMenu;

    public BaseConfigSideMenuComponent(WebDriver driver, ConfigPage configPage) {
        super(driver, configPage);
    }

    public ConfigPage clickGeneral() {
        linkGeneral.click();

        return page;
    }

    public Set<String> collectConfigSideMenu() {
        return configSideMenu.stream().map(WebElement::getText).collect(Collectors.toCollection(TreeSet::new));
    }
}
