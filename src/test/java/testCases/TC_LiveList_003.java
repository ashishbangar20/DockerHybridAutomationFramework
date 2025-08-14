package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import pageObjects.LeaveListPage;
import testBase.BaseClass;
import utilities.ExtentReportUtility;

public class TC_LiveList_003 extends BaseClass {

    Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1, groups = {"sanity"})
    public void test_leave_list_search() throws InterruptedException {
        logger.info("=== TC_LeaveListSearch_003 Started ===");
        
        // Create ExtentReports test
        ExtentTest test = ExtentReportUtility.createTest("TC_LiveList_003 - Verify Leave List Search");

        driver.get(prop.getProperty("AppURL"));
        logger.info("Navigated to URL: " + prop.getProperty("AppURL"));
        test.info("Navigated to: " + prop.getProperty("AppURL"));

        LoginPage lp = new LoginPage(driver);

        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        lp.setUsername(username);
        logger.info("Entered Username");
        test.info("Entered Username");

        lp.setPassword(password);
        logger.info("Entered Password");
        test.info("Entered Password");

        lp.clickLogin();
        logger.info("Clicked Login button");
        test.info("Clicked Login button");

        // Leave List actions
        LeaveListPage ll = new LeaveListPage(driver);
        ll.navigateToLeaveList();
        logger.info("Navigated to Leave List Page");
        test.info("Navigated to Leave List Page");

        ll.enterAndSelectEmployee("a", "Ranga Akunuri");
        logger.info("Entered and selected employee: Ranga Akunuri");
        test.info("Entered and selected employee: Ranga Akunuri");

        ll.setDateRange("2025-07-01", "2025-07-16");
        logger.info("Entered date range");
        test.info("Entered date range");

        ll.clickSearch();
        logger.info("Clicked Search button");
        test.info("Clicked Search button");

        if (ll.isResultDisplayed()) {
            logger.info("Leave records found");
            test.pass("Leave records found");
            Assert.assertTrue(true);
        } else if (ll.isNoRecordFound()) {
            logger.info("No records found for selected filter");
            test.pass("No records found for selected filter");
            Assert.assertTrue(true);
        } else {
            String screenshotPath = captureScreen("leave_list_search_failure");
            logger.error("Leave search failed or unexpected behavior");
            test.fail("Leave search failed or unexpected behavior")
                .addScreenCaptureFromPath(screenshotPath);
            Assert.fail("Leave list search failed unexpectedly.");
        }
//ashish
        logger.info("=== TC_LeaveListSearch_003 Completed ===");
        test.info("=== TC_LeaveListSearch_003 Completed ===");
    }
}
