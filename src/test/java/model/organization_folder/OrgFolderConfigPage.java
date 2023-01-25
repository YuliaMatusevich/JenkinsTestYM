package model.organization_folder;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class OrgFolderConfigPage extends BaseConfigPage<OrgFolderStatusPage, OrgFolderConfigPage, OrgFolderConfigSideMenuComponent> {
    @FindBy(xpath = "//input  [@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea [@name='_.description']")
    private WebElement description;

    @FindBy(id = "itemname-required")
    private WebElement errorMessageEmptyField;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(xpath = "//button[@data-section-id = 'health-metrics']")
    private WebElement healthMetricsTab;

    @FindBy(id = "yui-gen12-button")
    private WebElement childHealthMetricsButton;

    @FindBy(id = "yui-gen9-button")
    private WebElement addMetricButton;

    @FindBy(xpath = "//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric']")
    private WebElement childHealthMetric;

    @Override
    protected OrgFolderStatusPage createStatusPage() {
        return new OrgFolderStatusPage(getDriver());
    }

    @Override
    protected OrgFolderConfigSideMenuComponent createSideMenuComponent() {
        return new OrgFolderConfigSideMenuComponent(getDriver(), this);
    }

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrgFolderConfigPage inputDisplayName(String name) {
        displayName.sendKeys(name);

        return this;
    }

    public OrgFolderConfigPage inputDescription(String name) {
        description.sendKeys(name);

        return this;
    }
    public String getErrorMessageEmptyField() {
        return errorMessageEmptyField.getText();
    }

    public boolean isOkButtonEnabled() {
        return okButton.isEnabled();
    }

    public OrgFolderConfigPage clickHealthMetricsTab() {
        healthMetricsTab.click();

        return this;
    }

    public OrgFolderConfigPage clickMetricsButton() {
        getWait(5)
                .until(TestUtils.ExpectedConditions.elementIsNotMoving(childHealthMetricsButton))
                .click();

        return this;
    }

    public boolean checkChildMetricsIsDisplayed() {
        getWait(5).until(ExpectedConditions.visibilityOf(addMetricButton));
        return childHealthMetric.isDisplayed();
    }

}
