package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportUtility;

public class TC_InvalidLogin_002 extends BaseClass {

    Logger logger = LogManager.getLogger(this.getClass());

    @Test( groups = {"sanity"})
    public void test_blank_login() {
        logger.info("=== Test_006_Login_Blank_Credentials Started ===");

        // ExtentReports test create
        ExtentTest test = ExtentReportUtility.createTest("TC_InvalidLogin_002 - Blank Credentials Login");

        driver.get(prop.getProperty("AppURL"));
        logger.info("Navigated to URL: " + prop.getProperty("AppURL"));
        test.info("Navigated to: " + prop.getProperty("AppURL"));

        LoginPage lp = new LoginPage(driver);

        lp.clickLogin();
        logger.info("Clicked Login button without entering credentials");
        test.info("Clicked Login button without entering credentials");

        boolean validationShown = lp.isUsernameValidationErrorDisplayed();

        if (validationShown) {
            logger.info("Validation message displayed as expected.");
            test.pass("Validation message displayed as expected.");
            Assert.assertTrue(true);
        } else {
            String screenshotPath = captureScreen("blank_login_fail");
            logger.error("Validation message 'Required' was not displayed.");
            test.fail("Validation message 'Required' was not displayed.")
                .addScreenCaptureFromPath(screenshotPath);
            Assert.fail("Validation message 'Required' was not displayed.");
        }

        logger.info("=== Test_006_Login_Blank_Credentials Completed ===");
        test.info("=== Test_006_Login_Blank_Credentials Completed ===");
    }
}
