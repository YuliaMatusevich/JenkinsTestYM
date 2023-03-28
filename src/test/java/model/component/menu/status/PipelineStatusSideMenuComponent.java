package model.component.menu.status;

import io.qameta.allure.Step;
import model.page.BuildWithParametersPage;
import model.component.base.BaseStatusSideMenuComponent;
import model.page.config.PipelineConfigPage;
import model.page.status.PipelineStatusPage;
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

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @FindBy(css = "#no-builds")
    private WebElement buildsInformation;

    @Override
    public PipelineConfigPage getConfigPage() {
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

    @Step("Get pipeline side menu links")
    public List<String> getPipelineSideMenuLinks() {
        List<String> pipelineProjectText = new ArrayList<>();
        for (WebElement list : pipelineSideMenuLinks) {
            pipelineProjectText.add(list.getText());
        }

        return pipelineProjectText;
    }

    @Step("Click on 'Build with Parameters' link")
    public BuildWithParametersPage<PipelineStatusPage> clickBuildWithParameters() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buildWithParameters)).click();

        return new BuildWithParametersPage<>(getDriver(), new PipelineStatusPage(getDriver()));
    }

    @Step("Get build status")
    public PipelineStatusPage getBuildStatus() {
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformation, "style", "display: none;"));

        return new PipelineStatusPage(getDriver());
    }
}