package tests;

import io.qameta.allure.*;
import model.page.HomePage;
import model.page.view.*;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.BaseUtils;
import runner.ProjectMethodsUtils;
import runner.TestDataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static runner.TestUtils.getRandomStr;

public class ViewsTest extends BaseTest {

    @Step("Creating All Six Item: Freestyle Project, Pipeline,Multi-configuration Project, Folder, Multibranch Pipeline," +
            "Organization Folder ")
    public void createAllSixItems() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), TestDataUtils.MULTI_CONFIGURATION_PROJECT_NAME);
        ProjectMethodsUtils.createNewFolder(getDriver(), TestDataUtils.FOLDER_NAME);
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        ProjectMethodsUtils.createNewOrganizationFolder(getDriver(), TestDataUtils.ORGANIZATION_FOLDER_NAME);
    }


    @Step("Creating Views : Global View, List View, My View")
    public void createViews() {
        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), TestDataUtils.GLOBAL_VIEW_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), TestDataUtils.MY_VIEW_NAME);
    }

    @TmsLink("pAxP1BFq")
    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that Multibranch Pipeline contains expected Views : Global View, List View, My View")
    @Test
    public void testCreateViews() {
        ProjectMethodsUtils.createNewMultibranchPipeline(getDriver(), TestDataUtils.MULTIBRANCH_PIPELINE_NAME);
        createViews();

        String listViewsNames = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .getListViewsNames();

        Assert.assertTrue(listViewsNames.contains(TestDataUtils.GLOBAL_VIEW_NAME));
        Assert.assertTrue(listViewsNames.contains(TestDataUtils.LIST_VIEW_NAME));
        Assert.assertTrue(listViewsNames.contains(TestDataUtils.MY_VIEW_NAME));
    }

    @Owner("Nadia Ludanik")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that Multibranch Pipeline contains expected Views : Global View, List View, My View")
    @Test
    public void testCreateListViewAndAddSixItems() {
        createAllSixItems();

        int actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .addJobsToListView(6)
                .clickOkButton()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualResult, 6);
    }

    @TmsLink("Y9Ulf9j1")
    @Owner("Maria Servachak")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that created View contains existing name")
    @Test
    public void testCreateViewWithExistingName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);

        NewViewFromMyViewsPage newViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType();

        Assert.assertEquals(newViewPage.getErrorMessageViewAlreadyExist(),
                "A view with name " + TestDataUtils.LIST_VIEW_NAME + " already exists");
    }

    @Test
    public void testCreateListViewWithAddSettings() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .addJobToView(TestDataUtils.FREESTYLE_PROJECT_NAME)
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

        Assert.assertEquals(actualMarkedProjectName, TestDataUtils.FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(textConfirmAfterClickingApply, "Saved");
    }


    @Test
    public void testAddJobsToListView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), TestDataUtils.PIPELINE_NAME);
        createViews();

        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView()
                .clickJobsCheckBoxForAddRemoveToListView(TestDataUtils.PIPELINE_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListProjectsNamesFromView().contains(TestDataUtils.PIPELINE_NAME));
        Assert.assertTrue(viewPage.getListProjectsNamesFromView().contains(TestDataUtils.FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testAddJobsToListView")
    public void testDeselectJobsFromListView() {
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView()
                .clickJobsCheckBoxForAddRemoveToListView(TestDataUtils.FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(TestDataUtils.PIPELINE_NAME)
                .clickOkButton();

        Assert.assertEquals(viewPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + TestDataUtils.LIST_VIEW_NAME + "/");
        Assert.assertTrue(viewPage.getTextContentOnViewMainPanel().contains(
                "This view has no jobs associated with it. "
                        + "You can either add some existing jobs to this view or create a new job in this view."));
    }

    @Test
    public void testRenameView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView()
                .renameView(TestDataUtils.VIEW_RENAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListViewsNames().contains(TestDataUtils.VIEW_RENAME));
    }

    @Test(dataProvider = "specialCharacters", dataProviderClass = TestDataUtils.class)
    public void testIllegalCharacterRenameView(Character specialCharacter, String errorMessage) {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);

        List<Boolean> checksList = new ArrayList<>();
        try {
            new HomePage(getDriver())
                    .getHeader()
                    .clickMyViewItemInUserDropdownMenu()
                    .clickView(TestDataUtils.LIST_VIEW_NAME)
                    .clickEditListView()
                    .renameView(specialCharacter + TestDataUtils.LIST_VIEW_NAME)
                    .clickOkButton();
            if (new EditGlobalViewPage(getDriver()).getErrorPageHeader().equals("Error")) {
                checksList.add(new EditGlobalViewPage(getDriver()).isCorrectErrorPageDetailsText(specialCharacter));

                checksList.add(!new HomePage(getDriver())
                        .getBreadcrumbs()
                        .clickDashboard()
                        .getSideMenu()
                        .clickMyViewsSideMenuLink()
                        .getListViewsNames().contains(specialCharacter + TestDataUtils.LIST_VIEW_NAME));
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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), TestDataUtils.GLOBAL_VIEW_NAME);
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.GLOBAL_VIEW_NAME)
                .clickEditGlobalView();

        Assert.assertEquals(editGlobalViewPage.getUniqueTextOnGlobalViewEditPage(),
                "The name of a global view that will be shown.");
    }

    @Test
    public void testEditListViewPageContainsTitleJobFilters() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);
        EditListViewPage editListViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView();

        Assert.assertEquals(editListViewPage.getUniqueSectionOnListViewEditPage(),
                "Job Filters");
    }

    @Test
    public void testListViewSideMenu() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);

        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME);

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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
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
                .setViewName(TestDataUtils.GLOBAL_VIEW_NAME)
                .selectGlobalViewType()
                .clickCreateButton()
                .selectFilterBuildQueueOptionCheckBox()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testGlobalViewAddBothFilters() {
        createAllSixItems();
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.GLOBAL_VIEW_NAME)
                .selectGlobalViewType()
                .clickCreateButton()
                .selectFilterBuildQueueOptionCheckBox()
                .selectFilterBuildExecutorsOptionCheckBox()
                .clickOkButton()
                .clickEditGlobalView();

        Assert.assertTrue(editGlobalViewPage.isFilterBuildQueueOptionCheckBoxSelected());
        Assert.assertTrue(editGlobalViewPage.isFilterBuildExecutorsOptionCheckBoxSelected());
    }

    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems")
    public void testListViewAddNewColumn() {
        String expectedResult = "Git Branches";

        String actualResult = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
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
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
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
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
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
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
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
                .setViewName(TestDataUtils.GLOBAL_VIEW_NAME)
                .selectGlobalViewType()
                .clickCreateButton()
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
                .setViewName(TestDataUtils.MY_VIEW_NAME)
                .selectMyViewType()
                .clickCreateButton()
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
                .clickView(TestDataUtils.LIST_VIEW_NAME)
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
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .addAllJobsToListView()
                .clickOkButton()
                .clickEditListView()
                .renameView(newNameMultipleSpaces)
                .clickOkButton()
                .getActiveViewName();

        Assert.assertNotEquals(actualResult, TestDataUtils.LIST_VIEW_NAME);
        Assert.assertNotEquals(actualResult, newNameMultipleSpaces);
        Assert.assertEquals(actualResult, newNameSingleSpace);
    }

    @Test
    public void testViewHasListOfItems() {
        createAllSixItems();
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), TestDataUtils.MY_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.MY_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobListAsString(),
                new HomePage(getDriver()).getJobListAsString());
    }

    @Test(dependsOnMethods = "testCreateListViewAndAddSixItems")
    public void testDeleteStatusColumn() {
        boolean isContainingStatusColumn = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), TestDataUtils.LIST_VIEW_NAME);
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickDeleteViewToMyViews()
                .clickYes();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(TestDataUtils.LIST_VIEW_NAME));
    }

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = getRandomStr();

        int quantityProjectsInListView = new HomePage(getDriver())
                .getSideMenu()
                .clickNewItem()
                .setItemName(projectOne)
                .selectFreestyleProjectType()
                .clickOkButton()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickAddViewLink()
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .addJobToView(projectOne)
                .clickOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(new HomePage(getDriver()).getViewList().contains(TestDataUtils.LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = getRandomStr();

        String actualDescription = new HomePage(getDriver())
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView()
                .addDescription(descriptionRandom)
                .clickOkButton()
                .getTextDescription();

        Assert.assertEquals(actualDescription, descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {
        String descriptionText = new HomePage(getDriver())
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(descriptionText, "");
    }

    @Test(dependsOnMethods = "testEditViewDeleteDescription")
    public void testRemoveSomeHeadersFromProjectStatusTableInListView() {
        final List<String> namesRemoveColumns = List.of("Weather", "Last Failure", "Last Duration");

        int numberOfJobTableHeadersListView = new HomePage(getDriver())
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickEditListView()
                .removeSomeColumns(namesRemoveColumns)
                .clickApplyButton()
                .clickOkButton()
                .getJobTableHeadersSize();

        int numberOfJobTableHeadersAll = new ViewPage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobTableHeadersSize();

        Assert.assertNotEquals(numberOfJobTableHeadersAll, numberOfJobTableHeadersListView);
    }

    @Test(dependsOnMethods = "testRemoveSomeHeadersFromProjectStatusTableInListView")
    public void testDeleteListView() {
        List<String> viewList = new HomePage(getDriver())
                .clickView(TestDataUtils.LIST_VIEW_NAME)
                .clickDeleteViewToHomePage()
                .clickYes()
                .getViewList();

        Assert.assertFalse(viewList.contains(TestDataUtils.LIST_VIEW_NAME));
    }

    @Test
    public void testUnsavedChangesJavascriptAlert() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), TestDataUtils.FREESTYLE_PROJECT_NAME);
        new HomePage(getDriver())
                .getSideMenu()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestDataUtils.LIST_VIEW_NAME)
                .selectListViewType()
                .clickCreateButton();
        new EditGlobalViewPage(getDriver())
                .selectFilterBuildQueueOptionCheckBox()
                .getHeader()
                .clickJenkinsNameIcon();
        boolean alertIsPresent = new EditListViewPage(getDriver()).isAlertPresent();

        Assert.assertTrue(alertIsPresent);
    }
}