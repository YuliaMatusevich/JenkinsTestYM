package model.config_pages;

import model.base.BaseConfigPage;
import model.status_pages.MultibranchPipelineStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayName;

    @FindBy(name = "_.description")
    private WebElement description;

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

    public MultibranchPipelineConfigPage setDescription(String text) {
        description.clear();
        description.sendKeys(text);

        return this;
    }
}
