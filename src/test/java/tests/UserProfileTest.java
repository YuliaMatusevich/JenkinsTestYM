package tests;

import io.qameta.allure.*;
import model.page.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

public class UserProfileTest extends BaseTest {

    @TmsLink("G2voTwB8")
    @Owner("RomanSaf")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if the description text on the User Profile page is the same as expected " +
            "after it has been edited and saved")
    @Test
    public void testUserProfileAddDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        String actualUserDescription = new StatusUserPage(getDriver())
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, TestDataUtils.DESCRIPTION);
    }

    @TmsLink("z78QCmLs")
    @Owner("Yuliya Shershen")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if 'Hide preview' link disappears after clicking 'Hide preview' link ")
    @Test
    public void testUserProfileHidePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        StatusUserPage statusUserPage = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @TmsLink("dmjgdXDd")
    @Owner("TaniaKuno")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if description text is the same as expected after clicking 'Preview' link on the Status User Page")
    @Test
    public void testUserProfilePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        String actualPreviewText = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .getPreviewText();

        Assert.assertEquals(actualPreviewText, TestDataUtils.DESCRIPTION);
    }

    @TmsLink("DyG1fKGu")
    @Owner("RomanSaf")
    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("Verify if description text has been changed on the Status User Page")
    @Test
    public void testUserProfileEditDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        String actualUserDescription = new StatusUserPage(getDriver())
                .setDescriptionField(TestDataUtils.NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertNotEquals(actualUserDescription, TestDataUtils.DESCRIPTION);
    }
}