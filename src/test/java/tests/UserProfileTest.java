package tests;

import static runner.TestUtils.getRandomStr;

import model.HomePage;
import model.StatusUserPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class UserProfileTest extends BaseTest {

    private static final String DESCRIPTION = getRandomStr();
    private static final String NEW_DESCRIPTION = getRandomStr();


    @Test
    public void testUserProfileAddDescription() {
        String actualUserDescription = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualUserDescription, DESCRIPTION);
    }

    @Test
    public void testUserProfileHidePreviewDescription() {
        StatusUserPage statusUserPage = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(DESCRIPTION)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(statusUserPage.isDisplayedPreviewField());
    }

    @Test
    public void testUserProfilePreviewDescription() {
        String actualPreviewText = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(DESCRIPTION)
                .clickPreviewLink()
                .getPreviewText();

        Assert.assertEquals(actualPreviewText, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testUserProfileAddDescription")
    public void testUserProfileEditDescription() {
        String actualUserDescription = new HomePage(getDriver())
                .getHeader()
                .clickUserIcon()
                .clickAddDescriptionLink()
                .clearDescriptionInputField()
                .setDescriptionField(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertNotEquals(actualUserDescription,DESCRIPTION);
    }
}