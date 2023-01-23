package tests;

import static runner.TestUtils.getRandomStr;

import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;

public class UserProfileTest extends BaseTest {

    private static final String DESCRIPTION = getRandomStr();
    private static final String NEW_DESCRIPTION = getRandomStr();

    @Test
    public void testUserProfileAddDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), DESCRIPTION);

        String actualUserDescription = new StatusUserPage(getDriver())
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, DESCRIPTION);
    }

    @Test
    public void testUserProfileHidePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), DESCRIPTION);

        StatusUserPage statusUserPage = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @Test
    public void testUserProfilePreviewDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), DESCRIPTION);

        String actualPreviewText = new StatusUserPage(getDriver())
                .clickPreviewLink()
                .getPreviewText();

        Assert.assertEquals(actualPreviewText, DESCRIPTION);
    }

    @Test
    public void testUserProfileEditDescription() {
        ProjectMethodsUtils.editDescriptionUserActiveField(getDriver(), DESCRIPTION);

        String actualUserDescription = new StatusUserPage(getDriver())
                .setDescriptionField(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertNotEquals(actualUserDescription, DESCRIPTION);
    }
}