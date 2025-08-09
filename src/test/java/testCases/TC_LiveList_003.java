package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pageObjects.LoginPage;
import pageObjects.LeaveListPage;
import testBase.BaseClass;

public class TC_LiveList_003 extends BaseClass {

    Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1, groups = {"sanity"})
    public void test_leave_list_search() throws InterruptedException {
        logger.info("=== TC_LeaveListSearch_003 Started ===");

        driver.get(prop.getProperty("AppURL"));
        logger.info("Navigated to URL: " + prop.getProperty("AppURL"));

        LoginPage lp = new LoginPage(driver);

        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        

        lp.setUsername(username);
        logger.info("Entered Username");

        lp.setPassword(password);
        logger.info("Entered Password");

        lp.clickLogin();
        logger.info("Clicked Login button");

        // Leave List
        LeaveListPage ll = new LeaveListPage(driver);
        ll.navigateToLeaveList();
        logger.info("Navigated to Leave List Page");

        ll.enterAndSelectEmployee("a", "Ranga Akunuri");
        logger.info("Entered and selected employee: Ranga Akunuri");

        ll.setDateRange("2025-07-01", "2025-07-16");
        logger.info("Entered date range");

        ll.clickSearch();
        logger.info("Clicked Search button");

        if (ll.isResultDisplayed()) {
            logger.info("Leave records found");
            Assert.assertTrue(true);
        } else if (ll.isNoRecordFound()) {
            logger.info("No records found for selected filter");
            Assert.assertTrue(true);
        } else {
            captureScreen("leave_list_search_failure");
            logger.error("Leave search failed or unexpected behavior");
            Assert.fail("Leave list search failed unexpectedly.");
        }

        logger.info("=== TC_LeaveListSearch_003 Completed ===");
    }
}
