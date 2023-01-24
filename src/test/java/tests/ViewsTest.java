package tests;

import model.views.*;
import model.HomePage;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.BaseUtils;
import runner.ProjectMethodsUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static runner.TestUtils.getRandomStr;

public class ViewsTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = getRandomStr();
    private static final String PIPELINE_NAME = getRandomStr();
    private static final String MULTI_CONFIGURATION_PROJECT_NAME = getRandomStr();
    private static final String FOLDER_NAME = getRandomStr();
    private static final String MULTIBRANCH_PIPELINE_NAME = getRandomStr();
    private static final String ORGANIZATION_FOLDER_NAME = getRandomStr();
    private static final String GLOBAL_VIEW_NAME = getRandomStr();
    private static final String LIST_VIEW_NAME = getRandomStr();
    private static final String MY_VIEW_NAME = getRandomStr();
    private static final String VIEW_RENAME = getRandomStr();

    @DataProvider(name = "illegalCharacters")
    public Object[][] illegalCharactersList() {
        return new Object[][]{{'!'}, {'@'}, {'#'}, {'$'}, {'%'}, {'^'}, {'*'}, {'['}, {']'}, {'\\'}, {'|'}, {';'}, {':'}, {'/'}, {'?'}, {'&'}, {'<'}, {'>'},};
    }

    public void createAllSixItems() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), FOLDER_NAME);
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);
    }

    public void createViews() {
        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), GLOBAL_VIEW_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), MY_VIEW_NAME);
    }

    @Test
    public void testCreateViews() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);
        createViews();

        String listViewsNames = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .getListViewsNames();

        Assert.assertTrue(listViewsNames.contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(listViewsNames.contains(LIST_VIEW_NAME));
        Assert.assertTrue(listViewsNames.contains(MY_VIEW_NAME));
    }

    @Test
    public void testCreateListViewAndAddSixItems() {
        createAllSixItems();

        int actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(getDriver()))
                .addJobsToListView(6)
                .clickOkButton()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualResult, 6);
    }

    @Test
    public void testCreateViewWithExistingName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);

        NewViewPage newViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType();

        Assert.assertEquals(newViewPage.getErrorMessageViewAlreadyExist(),
                "A view with name " + LIST_VIEW_NAME + " already exists");
    }

    @Test
    public void testCreateListViewWithAddSettings() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(getRandomStr(6))
                .setListViewType().clickCreateButton(new EditListViewPage(getDriver()))
                .addJobToView(FREESTYLE_PROJECT_NAME)
                .getCountColumns();

        String textConfirmAfterClickingApply = new EditListViewPage(getDriver())
                .addColumn("Build Button")
                .clickApplyButton()
                .getTextConfirmAfterClickingApply();

        String actualMarkedProjectName = new EditGlobalViewPage(getDriver())
                .clickOkButton()
                .clickEditListView()
                .getSelectedJobName();

        int countColumnsAfterAdd = new EditListViewPage(getDriver())
                .getCountColumns();

        Assert.assertEquals(actualMarkedProjectName, FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(textConfirmAfterClickingApply, "Saved");
    }


    @Test
    public void testAddJobsToListView () {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        createViews();

        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListProjectsNamesFromView().contains(PIPELINE_NAME));
        Assert.assertTrue(viewPage.getListProjectsNamesFromView().contains(FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testAddJobsToListView")
    public void testDeselectJobsFromListView() {
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_NAME)
                .clickOkButton();

        Assert.assertEquals(viewPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + LIST_VIEW_NAME + "/");
        Assert.assertTrue(viewPage.getTextContentOnViewMainPanel().contains(
                "This view has no jobs associated with it. "
                        + "You can either add some existing jobs to this view or create a new job in this view."));
    }

    @Test
    public void testRenameView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .renameView(VIEW_RENAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListViewsNames().contains(VIEW_RENAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems", dataProvider = "illegalCharacters")
    public void testIllegalCharacterRenameView(Character illegalCharacter) {
        new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink();

        List<Boolean> checksList = new ArrayList<>();
        try {
            new HomePage(getDriver())
                    .getHeader()
                    .clickMyViewItemInUserDropdownMenu()
                    .clickView(GLOBAL_VIEW_NAME)
                    .clickEditGlobalView()
                    .renameView(illegalCharacter + GLOBAL_VIEW_NAME)
                    .clickOkButton();
            if (new EditGlobalViewPage(getDriver()).getErrorPageHeader().equals("Error")) {
                checksList.add(new EditGlobalViewPage(getDriver()).isCorrectErrorPageDetailsText(illegalCharacter));

                checksList.add(!new HomePage(getDriver())
                        .getBreadcrumbs()
                        .clickDashboard()
                        .getSideMenu()
                        .clickMyViewsSideMenuLink()
                        .getListViewsNames().contains(illegalCharacter + GLOBAL_VIEW_NAME));
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
    public void testEditGlobalViewPageViewNameLabelContainsDescription() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), GLOBAL_VIEW_NAME);
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME)
                .clickEditGlobalView();

        Assert.assertEquals(editGlobalViewPage.getUniqueTextOnGlobalViewEditPage(),
                "The name of a global view that will be shown.");
    }

    @Test
    public void testEditListViewPageContainsTitleJobFilters() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        EditListViewPage editListViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView();

        Assert.assertEquals(editListViewPage.getUniqueSectionOnListViewEditPage(),
                "Job Filters");
    }

    @Test
    public void testListViewSideMenu() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);

        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME);

        Assert.assertEqualsNoOrder(viewPage.getSideMenuTextList(), viewPage.getActualSideMenu());
    }

    @Test
    public void testAddDescription() {
        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickAddDescription()
                .clearDescriptionField()
                .setDescription("Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "Description");
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickEditDescription()
                .clearDescriptionField()
                .setDescription("New Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "New Description");
    }

    @Test
    public void testLettersSMLClickableMyViews() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        MyViewsPage myViewsPageSizeM = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickSizeM();

        Assert.assertTrue(myViewsPageSizeM.tableSizeM());

        MyViewsPage myViewsPageSizeS = new MyViewsPage(getDriver())
                .clickSizeS();

        Assert.assertTrue(myViewsPageSizeS.tableSizeS());

        MyViewsPage myViewsPageSizeL = new MyViewsPage(getDriver())
                .clickSizeL();

        Assert.assertTrue(myViewsPageSizeL.tableSizeL());
    }

    @Test
    public void testGlobalViewAddFilterBuildQueue() {
        createAllSixItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton(new EditGlobalViewPage(getDriver()))
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Ignore
    @Test
    public void testGlobalViewAddBothFilters() {
        createAllSixItems();
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton(new EditGlobalViewPage(getDriver()))
                .selectFilterBuildQueueOptionCheckBox()
                .selectFilterBuildExecutorsOptionCheckBox()
                .clickOkButton()
                .clickEditGlobalView();

        Assert.assertTrue(editGlobalViewPage.isFilterBuildQueueOptionCheckBoxSelected(), "Global view not selected");
        Assert.assertTrue(editGlobalViewPage.isFilterBuildExecutorsOptionCheckBoxSelected(), "Executors view not selected");
    }

    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems")
    public void testListViewAddNewColumn() {
        String expectedResult = "Git Branches";

        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME)
                .clickEditListView()
                .clickAddColumnDropDownMenu()
                .clickGitBranchesColumnMenuOption()
                .clickOkButton()
                .getJobTableLastHeaderText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testAddAllItemsToListView() {
        createAllSixItems();
        int expectedResult = new HomePage(getDriver())
                .getJobNamesList().size();

        int actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(getDriver()))
                .addAllJobsToListView()
                .clickOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddRegexFilterJobNamesContainingNine() {
        createAllSixItems();
        int expectedResult = new HomePage(getDriver())
                .getNumberOfJobsContainingString("9");

        new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(getDriver()))
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
        createAllSixItems();
        String[] expectedResult = {"W", "S"};
        new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(getDriver()))
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
        createAllSixItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditGlobalViewPage(getDriver()))
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testMyViewAddFilterBuildQueue() {
        createAllSixItems();
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(MY_VIEW_NAME)
                .setMyViewType()
                .clickCreateButton(new EditMyViewPage(getDriver()))
                .clickEditMyView()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems")
    public void testListViewCheckEveryAddColumnItem() {
        List<Boolean> isMatchingMenuItemToAddedColumn = new ArrayList<>();

        Map<String, String> tableMenuMap = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME)
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

    @Test
    public void testMultipleSpacesRenameView() {
        createAllSixItems();
        final String nonSpaces = getRandomStr(6);
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String newNameMultipleSpaces = nonSpaces + spaces + nonSpaces;
        final String newNameSingleSpace = nonSpaces + " " + nonSpaces;

        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setListViewType()
                .clickCreateButton(new EditListViewPage(getDriver()))
                .addAllJobsToListView()
                .clickOkButton()
                .clickEditListView()
                .renameView(newNameMultipleSpaces)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertNotEquals(actualResult, GLOBAL_VIEW_NAME);
        Assert.assertNotEquals(actualResult, newNameMultipleSpaces);
        Assert.assertEquals(actualResult, newNameSingleSpace);
    }

    @Test
    public void testViewHasListOfItems() {
        createAllSixItems();
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), GLOBAL_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobListAsString(),
                new HomePage(getDriver()).getJobListAsString());
    }

    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems")
    public void testDeleteStatusColumn() {
        boolean isContainingStatusColumn = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME)
                .clickEditListView()
                .scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving()
                .clickDeleteStatusColumnButton()
                .clickOkButton()
                .getJobTableHeaderTextList()
                .contains("S");

        Assert.assertFalse(isContainingStatusColumn);
    }

    @Test
    public void testDeleteView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickDeleteViewToMyViews()
                .clickYes();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
    }
}