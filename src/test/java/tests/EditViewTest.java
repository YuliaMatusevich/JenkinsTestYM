package tests;

import model.views.EditListViewPage;
import model.views.EditGlobalViewPage;
import model.HomePage;

import model.views.ViewPage;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.*;

import runner.BaseTest;
import runner.BaseUtils;
import runner.TestUtils;

import java.util.*;

public class EditViewTest extends BaseTest {
    private static String localViewName = TestUtils.getRandomStr();

    @DataProvider(name = "illegalCharacters")
    public Object[][] illegalCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'&'}, {'<'}, {'>'},};
    }

    @Test
    public void testCreateOneItemFromListOfJobTypes() {
        int actualNumberOfJobs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectPipelineAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectPipelineAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectMultiConfigurationProjectAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectFolderAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectMultibranchPipelineAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectOrgFolderAndClickOk()

                .getBreadcrumbs()
                .clickDashboard()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualNumberOfJobs, 6);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testGlobalViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setGlobalViewType()
                .clickCreateButtonToEditGlobalView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testGlobalViewAddBothFilters() {
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setGlobalViewType()
                .clickCreateButtonToEditGlobalView()
                .selectFilterBuildQueueOptionCheckBox()
                .selectFilterBuildExecutorsOptionCheckBox()
                .clickOkButton()
                .clickEditGlobalView();

        Assert.assertTrue(editGlobalViewPage.isFilterBuildQueueOptionCheckBoxSelected() && editGlobalViewPage.isFilterBuildExecutorsOptionCheckBoxSelected());
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddFiveItems() {
        int expectedResult = 5;

        int actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(localViewName)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addJobsToListView(expectedResult)
                .clickOkButton()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems", "testCreateOneItemFromListOfJobTypes"})
    public void testListViewAddNewColumn() {
        String expectedResult = "Git Branches";

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(localViewName)
                .clickEditListView()
                .clickAddColumnDropDownMenu()
                .clickGitBranchesColumnMenuOption()
                .clickOkButton()
                .getJobTableLastHeaderText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testAddAllItemsToListView() {
        int expectedResult = new HomePage(getDriver())
                .getJobNamesList().size();

        int actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .clickOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddRegexFilterJobNamesContainingNine() {
        int expectedResult = new HomePage(getDriver())
                .getNumberOfJobsContainingString("9");

        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setListViewType()
                .clickCreateButtonToEditListView()
                .scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving();
        if (!new EditListViewPage(getDriver()).isRegexCheckboxChecked()) {
            new EditListViewPage(getDriver()).clickRegexCheckbox();
        }

        int actualResult = new EditListViewPage(getDriver())
                .scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving()
                .clearAndSendKeysRegexTextArea(".*9.*")
                .clickOkButton()
                .getJobNamesList().size();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewChangeColumnOrder() {
        String[] expectedResult = {"W", "S"};
        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .scrollToStatusColumnDragHandlePlaceInCenterWaitTillNotMoving()
                .dragByYOffset(100)
                .clickOkButton();

        String[] actualResult = {new ViewPage(getDriver())
                .getJobTableHeaderTextList().get(0), new ViewPage(getDriver())
                .getJobTableHeaderTextList().get(1)};
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setListViewType()
                .clickCreateButtonToEditListView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testMyViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr())
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .clickEditMyView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems", "testCreateOneItemFromListOfJobTypes"})
    public void testListViewCheckEveryAddColumnItem() {
        List<Boolean> isMatchingMenuItemToAddedColumn = new ArrayList<>();

        Map<String, String> tableMenuMap = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(localViewName)
                .clickEditListView()
                .clickAddColumnDropDownMenu()
                .getMapMatchingColumnDropDownMenuItemsAndJobTableHeader();

        for (int i = 0; i < new EditListViewPage(getDriver()).getNumberOfAllAddColumnDropDownMenuItems(); i++) {
            String currentAddColumnDropDownMenuItem = new EditGlobalViewPage(getDriver())
                    .getAddColumnDropDownMenuItemTextByOrder(i + 1);
            String newlyAddedColumn = new EditGlobalViewPage(getDriver())
                    .clickAddColumnDropDownMenuItemByOrder(i + 1)
                    .clickOkButton()
                    .getJobTableLastHeaderText()
                    .replace("↓", " ")
                    .trim();
            isMatchingMenuItemToAddedColumn.add(tableMenuMap.get(currentAddColumnDropDownMenuItem).equals(newlyAddedColumn));
            new ViewPage(getDriver())
                    .clickEditListView()
                    .clickLastExistingColumnDeleteButton()
                    .clickAddColumnDropDownMenu();
        }
        new EditGlobalViewPage(getDriver())
                .clickOkButton();

        Assert.assertTrue(isMatchingMenuItemToAddedColumn.stream().allMatch(element -> element == true));
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems", "testCreateOneItemFromListOfJobTypes"})
    public void testDeleteStatusColumn() {
        boolean isContainingStatusColumn = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(localViewName)
                .clickEditListView()
                .scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving()
                .clickDeleteStatusColumnButton()
                .clickOkButton()
                .getJobTableHeaderTextList()
                .contains("S");

        Assert.assertFalse(isContainingStatusColumn);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testMultipleSpacesRenameView() {
        localViewName = TestUtils.getRandomStr();
        final String nonSpaces = TestUtils.getRandomStr(6);
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String newNameMultipleSpaces = nonSpaces + spaces + nonSpaces;
        final String newNameSingleSpace = nonSpaces + " " + nonSpaces;

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(localViewName)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .clickOkButton()
                .clickEditListView()
                .renameView(newNameMultipleSpaces)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertNotEquals(actualResult, localViewName);
        Assert.assertNotEquals(actualResult, newNameMultipleSpaces);
        Assert.assertEquals(actualResult, newNameSingleSpace);
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems", "testCreateOneItemFromListOfJobTypes"}, dataProvider = "illegalCharacters")
    public void testIllegalCharacterRenameView(Character illegalCharacter) {
        new HomePage(getDriver()).clickMyViewsSideMenuLink();

        List<Boolean> checksList = new ArrayList<>();
        try {
            new HomePage(getDriver())
                    .clickMyViewsTopMenuLink()
                    .clickView(localViewName)
                    .clickEditGlobalView()
                    .renameView(illegalCharacter + localViewName)
                    .clickOkButton();
            if (new EditGlobalViewPage(getDriver()).getErrorPageHeader().equals("Error")) {
                checksList.add(new EditGlobalViewPage(getDriver()).isCorrectErrorPageDetailsText(illegalCharacter));

                checksList.add(!new HomePage(getDriver())
                        .getBreadcrumbs()
                        .clickDashboard()
                        .clickMyViewsSideMenuLink()
                        .getListViewsNames().contains(illegalCharacter + localViewName));
            } else {
                checksList.add(false);
                BaseUtils.log("Not an Error page");
            }
        } catch (NoSuchElementException exception) {
            BaseUtils.log(String.format("Invalid Page at Title: %s, URL: %s",
                    getDriver().getTitle(),
                    getDriver().getCurrentUrl()));
            checksList.add(false);
        }

        Assert.assertTrue(checksList.stream().allMatch(element -> element == true));
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testRenameView() {
        localViewName = TestUtils.getRandomStr();
        final String newName = TestUtils.getRandomStr();

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(localViewName)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .clickOkButton()
                .clickEditListView()
                .renameView(newName)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertFalse(actualResult.equals(localViewName));
        Assert.assertEquals(actualResult, newName);
    }
}