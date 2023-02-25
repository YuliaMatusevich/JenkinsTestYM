package tests;

import model.page.CreateItemErrorPage;
import model.page.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;

import static runner.TestUtils.getRandomStr;

public class CopyItemTest extends BaseTest {

    @Test
    public void testCopyFromNotExistItemName() {
        final String nameItem = getRandomStr();
        final String nameNotExistItem = getRandomStr();
        final String nameExistItem = getRandomStr();
        final String expectedErrorMessage = "Error No such job: " + nameNotExistItem;

        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), nameExistItem);

        CreateItemErrorPage createItemErrorPage = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(nameItem)
                .selectFreestyleProjectType()
                .setCopyFrom(nameNotExistItem)
                .clickOkToCreateItemErrorPage();

        String actualErrorMessage = createItemErrorPage.getErrorHeader() +
                " " + createItemErrorPage.getErrorMessage();
        boolean isItemAtTheDashboard = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobListAsString()
                .contains(nameItem);

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        Assert.assertFalse(isItemAtTheDashboard, "Item " + nameItem + " at the Dashboard");
    }

    @Test
    public void testFieldCopyFromDoNotDisplayIfDoNotHaveAnyItems() {
        boolean isDisplayedFieldFrom = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .isDisplayedFieldCopyFrom();

        Assert.assertFalse(isDisplayedFieldFrom, "Field Copy from is displayed");
    }
}