package tests;

import model.views.*;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class NewViewTest extends BaseTest {

    private static final String PROJECT_RANDOM_NAME = TestUtils.getRandomStr(6);
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle project";
    private static final String PIPELINE_PROJECT_NAME = "Pipeline";
    private static final String GLOBAL_VIEW_NAME = "Global_View";
    private static final String LIST_VIEW_NAME = "List_View";
    private static final String MY_VIEW_NAME = "My_View";
    private static final String LIST_VIEW_RENAME = "NewListView";

    @Test
    public void testCreateViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()

                .clickNewItem()
                .setItemName(PIPELINE_PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()

                .clickNewItem()
                .setItemName("Multi-configuration project")
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButtonToViewPage()
                .getBreadcrumbs()
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToViewPage()
                .getBreadcrumbs()
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(MY_VIEW_NAME)
                .setMyViewType()
                .clickCreateButtonToViewPage()
                .getBreadcrumbs()
                .clickDashboard()

                .clickMyViewsSideMenuLink();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateViews")
    public void testSelectJobsToAddInListView() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickLinkTextAddExistingJob()
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_PROJECT_NAME)
                .clickOkButton();

        Assert.assertEquals(viewPage.getListProjectsNamesAsString(),
                FREESTYLE_PROJECT_NAME.concat(" ").concat(PIPELINE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testSelectJobsToAddInListView")
    public void testDeselectJobsFromListView() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_PROJECT_NAME)
                .clickOkButton();

        Assert.assertEquals(viewPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + LIST_VIEW_NAME + "/");
        Assert.assertTrue(viewPage.getTextContentOnViewMainPanel().contains(
                "This view has no jobs associated with it. "
                        + "You can either add some existing jobs to this view or create a new job in this view."));
    }

    @Test(dependsOnMethods = "testDeselectJobsFromListView")
    public void testCreateViewWithExistingName() {
        NewViewPage newViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType();

        Assert.assertEquals(newViewPage.getErrorMessageViewAlreadyExist(),
                "A view with name " + LIST_VIEW_NAME + " already exists");
    }

    @Test(dependsOnMethods = "testCreateViewWithExistingName")
    public void testRenameView() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .renameView(LIST_VIEW_RENAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test(dependsOnMethods = "testRenameView")
    public void testViewHasSelectedTypeGlobalView() {
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(GLOBAL_VIEW_NAME)
                .clickEditGlobalView();

        Assert.assertEquals(editGlobalViewPage.getUniqueTextOnGlobalViewEditPage(),
                "The name of a global view that will be shown.");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeGlobalView")
    public void testViewHasSelectedTypeListView() {
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_RENAME)
                .clickEditGlobalView();

        Assert.assertEquals(editGlobalViewPage.getUniqueSectionOnListViewEditPage(),
                "Job Filters");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeListView")
    public void testViewHasSelectedTypeMyView() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(MY_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobListAsString(),
                new HomePage(getDriver()).getJobListAsString());
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeMyView")
    public void testViewSideMenu() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_RENAME);

        Assert.assertTrue(viewPage.getBreadcrumbs().getBreadcrumbsItemName(LIST_VIEW_RENAME).contains(LIST_VIEW_RENAME));
        Assert.assertEqualsNoOrder(viewPage.getSideMenuTextList(), viewPage.getActualSideMenu());
    }

    @Test(dependsOnMethods = "testViewSideMenu")
    public void testDeleteView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_RENAME)
                .clickDeleteViewToMyViews()
                .clickYes();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test
    public void testAddDescription() {
        String actualResult = new HomePage(getDriver())
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
                .clickMyViewsSideMenuLink()
                .clickEditDescription()
                .clearDescriptionField()
                .setDescription("New Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "New Description");
    }

    @Test
    public void testCreateListViewWithAddSettings() {
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_RANDOM_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr(6))
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addJobToView(PROJECT_RANDOM_NAME)
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

        Assert.assertEquals(actualMarkedProjectName, PROJECT_RANDOM_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(textConfirmAfterClickingApply, "Saved");
    }

    @Test
    public void testLettersSMLClickableMyViews() {
        MyViewsPage myViewsPageSizeM = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
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
}
