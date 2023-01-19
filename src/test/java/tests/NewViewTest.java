package tests;

import model.views.*;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;

public class NewViewTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle project";
    private static final String PIPELINE_PROJECT_NAME = "Pipeline";
    private static final String MULTI_CONFIGURATION_PROJECT = "Multi-configuration project";
    private static final String GLOBAL_VIEW_NAME = "Global_View";
    private static final String LIST_VIEW_NAME = "List_View";
    private static final String MY_VIEW_NAME = "My_View";
    private static final String LIST_VIEW_RENAME = "NewListView";

    @Test
    public void testCreateViews() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_PROJECT_NAME);
        ProjectMethodsUtils.createNewMultiConfigurationProject(getDriver(), MULTI_CONFIGURATION_PROJECT);

        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), GLOBAL_VIEW_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        ProjectMethodsUtils.createNewMyViewForMyViews(getDriver(), MY_VIEW_NAME);

        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test
    public void testSelectJobsToAddInListView() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_PROJECT_NAME);
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);

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

    @Test
    public void testCreateViewWithExistingName() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);

        NewViewPage newViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType();

        Assert.assertEquals(newViewPage.getErrorMessageViewAlreadyExist(),
                "A view with name " + LIST_VIEW_NAME + " already exists");
    }

    @Test
    public void testRenameView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .renameView(LIST_VIEW_RENAME)
                .clickOkButton();

        Assert.assertTrue(viewPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test
    public void testEditGlobalViewPageViewNameLabelContainsDescription() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewGlobalViewForMyViews(getDriver(), GLOBAL_VIEW_NAME);
        EditGlobalViewPage editGlobalViewPage = new HomePage(getDriver())
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
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME);

        Assert.assertEqualsNoOrder(viewPage.getSideMenuTextList(), viewPage.getActualSideMenu());
    }

    @Test
    public void testDeleteView() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        ProjectMethodsUtils.createNewListViewForMyViews(getDriver(), LIST_VIEW_NAME);
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickDeleteViewToMyViews()
                .clickYes();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
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
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr(6))
                .setListViewType()
                .clickCreateButtonToEditListView()
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
    public void testLettersSMLClickableMyViews() {
        ProjectMethodsUtils.createNewFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);
        MyViewsPage myViewsPageSizeM = new HomePage(getDriver())
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
