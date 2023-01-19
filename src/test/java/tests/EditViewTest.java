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
import runner.ProjectMethodsUtils;
import runner.TestUtils;
import java.util.*;

public class EditViewTest extends BaseTest {
    private static final String LOCAL_VIEW_NAME = TestUtils.getRandomStr();

    @DataProvider(name = "illegalCharacters")
    public Object[][] illegalCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'&'}, {'<'}, {'>'},};
    }

    public void createFiveItems() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestUtils.getRandomStr());
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestUtils.getRandomStr());
    }

    @Test
    public void testGlobalViewAddFilterBuildQueue() {
        createFiveItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButtonToEditGlobalView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testGlobalViewAddBothFilters() {
        createFiveItems();
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButtonToEditGlobalView()
                .selectFilterBuildQueueOptionCheckBox()
                .selectFilterBuildExecutorsOptionCheckBox()
                .clickOkButton()
                .clickEditGlobalView();

        Assert.assertTrue(editGlobalViewPage.isFilterBuildQueueOptionCheckBoxSelected() && editGlobalViewPage.isFilterBuildExecutorsOptionCheckBoxSelected());
    }

    @Test
    public void testListViewAddFiveItems() {
        createFiveItems();

        int actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addJobsToListView(5)
                .clickOkButton()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualResult, 5);
    }

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testListViewAddNewColumn() {
        String expectedResult = "Git Branches";

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LOCAL_VIEW_NAME)
                .clickEditListView()
                .clickAddColumnDropDownMenu()
                .clickGitBranchesColumnMenuOption()
                .clickOkButton()
                .getJobTableLastHeaderText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testAddAllItemsToListView() {
        createFiveItems();
        int expectedResult = new HomePage(getDriver())
                .getJobNamesList().size();

        int actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .clickOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddRegexFilterJobNamesContainingNine() {
        createFiveItems();
        int expectedResult = new HomePage(getDriver())
                .getNumberOfJobsContainingString("9");

        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
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

    @Test
    public void testListViewChangeColumnOrder() {
        createFiveItems();
        String[] expectedResult = {"W", "S"};
        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
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

    @Test
    public void testListViewAddFilterBuildQueue() {
        createFiveItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testMyViewAddFilterBuildQueue() {
        createFiveItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .clickEditMyView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testListViewCheckEveryAddColumnItem() {
        List<Boolean> isMatchingMenuItemToAddedColumn = new ArrayList<>();

        Map<String, String> tableMenuMap = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LOCAL_VIEW_NAME)
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

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testDeleteStatusColumn() {
        boolean isContainingStatusColumn = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LOCAL_VIEW_NAME)
                .clickEditListView()
                .scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving()
                .clickDeleteStatusColumnButton()
                .clickOkButton()
                .getJobTableHeaderTextList()
                .contains("S");

        Assert.assertFalse(isContainingStatusColumn);
    }

    @Test
    public void testMultipleSpacesRenameView() {
        createFiveItems();
        final String nonSpaces = TestUtils.getRandomStr(6);
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String newNameMultipleSpaces = nonSpaces + spaces + nonSpaces;
        final String newNameSingleSpace = nonSpaces + " " + nonSpaces;

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LOCAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addAllJobsToListView()
                .clickOkButton()
                .clickEditListView()
                .renameView(newNameMultipleSpaces)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertNotEquals(actualResult, LOCAL_VIEW_NAME);
        Assert.assertNotEquals(actualResult, newNameMultipleSpaces);
        Assert.assertEquals(actualResult, newNameSingleSpace);
    }
    @Ignore
    @Test(dependsOnMethods = "testListViewAddFiveItems", dataProvider = "illegalCharacters")
    public void testIllegalCharacterRenameView(Character illegalCharacter) {
        new HomePage(getDriver()).clickMyViewsSideMenuLink();

        List<Boolean> checksList = new ArrayList<>();
        try {
            new HomePage(getDriver())
                    .clickMyViewsTopMenuLink()
                    .clickView(LOCAL_VIEW_NAME)
                    .clickEditGlobalView()
                    .renameView(illegalCharacter + LOCAL_VIEW_NAME)
                    .clickOkButton();
            if (new EditGlobalViewPage(getDriver()).getErrorPageHeader().equals("Error")) {
                checksList.add(new EditGlobalViewPage(getDriver()).isCorrectErrorPageDetailsText(illegalCharacter));

                checksList.add(!new HomePage(getDriver())
                        .getBreadcrumbs()
                        .clickDashboard()
                        .clickMyViewsSideMenuLink()
                        .getListViewsNames().contains(illegalCharacter + LOCAL_VIEW_NAME));
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

    @Test
    public void testRenameView() {
        createFiveItems();
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LOCAL_VIEW_NAME);
        final String newName = TestUtils.getRandomStr();

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LOCAL_VIEW_NAME)
                .clickEditListView()
                .renameView(newName)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertFalse(actualResult.equals(LOCAL_VIEW_NAME));
        Assert.assertEquals(actualResult, newName);
    }

    @Test
    public void testViewHasListOfItems() {
        createFiveItems();
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), LOCAL_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LOCAL_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobListAsString(),
                new HomePage(getDriver()).getJobListAsString());
    }
}