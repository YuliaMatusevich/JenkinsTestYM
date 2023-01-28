package model.config_pages;

import model.base.BaseConfigPage;
import model.status_pages.MultibranchPipelineStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayName;

    @Override
    protected MultibranchPipelineStatusPage createStatusPage() {
        return new MultibranchPipelineStatusPage(getDriver());
    }

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage setDisplayName(String name) {
        displayName.clear();
        displayName.sendKeys(name);

        return this;
    }
}
