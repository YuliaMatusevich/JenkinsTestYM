package model.multibranch_pipeline;

import model.base.BlankConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineConfigPage extends BlankConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

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
