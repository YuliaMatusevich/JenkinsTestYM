package model.status_side_menu_component;

import model.BuildWithParametersPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.PipelineConfigPage;
import model.status_pages.PipelineStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class PipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<PipelineStatusPage, PipelineConfigPage> {

    @FindBy(xpath = "(//a[contains(@class,'task-link')])[7]")
    private WebElement gitHub;

    @FindBy(xpath = "//span[@class='task-link-wrapper ']//span[2]")
    private List<WebElement> pipelineSideMenuLinks;

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    @Override
    protected PipelineConfigPage createConfigPage() {
        return new PipelineConfigPage(getDriver());
    }

    public PipelineStatusSideMenuComponent(WebDriver driver, PipelineStatusPage statusPage) {
        super(driver, statusPage);
    }

    public boolean isDisplayedGitHubOnSideMenu() {
        return gitHub.isDisplayed();
    }

    public String getAttributeGitHubSideMenu(String attribute) {
        return gitHub.getAttribute(attribute);
    }

    public List<String> getPipelineSideMenuLinks() {
        List<String> pipelineProjectText = new ArrayList<>();
        for (WebElement list : pipelineSideMenuLinks) {
            pipelineProjectText.add(list.getText());
        }

        return pipelineProjectText;
    }

    public BuildWithParametersPage<PipelineStatusPage> clickBuildWithParameters() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buildWithParameters)).click();

        return new BuildWithParametersPage<>(getDriver(), new PipelineStatusPage(getDriver()));
    }
}
