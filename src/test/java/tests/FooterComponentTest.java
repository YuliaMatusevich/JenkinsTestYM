package tests;

import io.qameta.allure.*;
import model.page.ExternalJenkinsPage;
import model.page.HomePage;
import model.page.RestApiPage;
import model.page.XmlPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FooterComponentTest extends BaseTest {

    @TmsLink("qXXhUpuj")
    @Owner("Svetlana Babintseva/RustamKhudoyarov")
    @Severity(SeverityLevel.MINOR)
    @Feature("Navigation")
    @Description("Verification that the link redirect to the '/api/' page")
    @Test
    public void testFooterLinkRestRedirectToPage() {

        String urlRestApi = new HomePage(getDriver())
                .getFooter()
                .clickRestApiLink()
                .getCurrentURL();

        Assert.assertTrue(urlRestApi.contains("api"));
        Assert.assertEquals(new RestApiPage(getDriver()).getTextH1RestApi(), "REST API");
    }

    @TmsLink("qXXhUpuj")
    @Owner("Svetlana Babintseva/Rustam Khudoyarov")
    @Severity(SeverityLevel.MINOR)
    @Feature("Navigation")
    @Description("Verification that the link redirect to the 'https://www.jenkins.io/'")
    @Test
    public void testFooterLinkJenkinsRedirectToPage() {

        String textJenkins = new HomePage(getDriver())
                .getFooter()
                .clickJenkinsVersion()
                .getHeaderText();

        Assert.assertTrue(new ExternalJenkinsPage(getDriver()).getCurrentURL().contains("jenkins"));
        Assert.assertEquals(textJenkins, "Jenkins");

    }

    @TmsLink("zmIllMYO")
    @Owner("RustamKhudoyarov")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verification of getting an example XML-schema")
    @Test
    public void testFooterRestApiClickOnXmlApiDisplayXML() {
        XmlPage xmlPage = new HomePage(getDriver())
                .getFooter()
                .clickRestApiLink()
                .clickXmlApi();

        Assert.assertEquals(xmlPage.getStructureXML(), "This XML file does not appear to have any "
                + "style information associated with it. The document tree is shown below.");
    }

    @Owner("DenSebrovsky")
    @Severity(SeverityLevel.MINOR)
    @Feature("UI")
    @Description("Verification that the link redirect to the 'https://www.jenkins.io/'")
    @Flaky
    @Test
    public void testFooterLinkJenkinsIsClickable() {
        String externalJenkinsPageHeader = new HomePage(getDriver())
                .getSideMenu()
                .clickManageJenkins()
                .moveToJenkinsVersion()
                .clickJenkinsVersion()
                .getHeaderText();

        Assert.assertEquals(externalJenkinsPageHeader, "Jenkins");
    }
}