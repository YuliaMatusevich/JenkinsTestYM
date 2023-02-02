package tests;

import static runner.TestUtils.getRandomStr;

import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

public class UserProfileTest extends BaseTest {

    @Test
    public void testUserProfileAddDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        String actualUserDescription = new StatusUserPage(getDriver())
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, TestDataUtils.DESCRIPTION);
    }

    @Test
    public void testUserProfileHidePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        StatusUserPage statusUserPage = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @Test
    public void testUserProfilePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), TestDataUtils.DESCRIPTION);

        String actualPreviewText = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .getPreviewText();

        Assert.assertEquals(actualPreviewText, TestDataUtils.DESCRIPTION);
    }

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