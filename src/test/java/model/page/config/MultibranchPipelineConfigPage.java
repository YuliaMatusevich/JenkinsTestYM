package model.page.config;

import model.page.base.BaseConfigPage;
import model.page.status.MultibranchPipelineStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayName;

    @FindBy(name = "_.description")
    private WebElement description;

    @FindBy(xpath = "//div[@class='jenkins-form-item has-help']//select[@class='jenkins-select__input dropdownList']")
    private WebElement selectIcon;

    @FindBy(xpath = "//option[contains(text(), 'Default Icon')]")
    private WebElement defaultIcon;

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

    public MultibranchPipelineConfigPage selectIcon() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), selectIcon);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(selectIcon)).click();
        defaultIcon.click();

        return this;
    }
}
