package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportUtility;

public class TC_Blank_login__003 extends BaseClass {

    Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void verifyInvalidLogin() {
        logger.info("=== TC_Invalid_Login_004 Started ===");

        // Create ExtentReports test instance
        ExtentTest test = ExtentReportUtility.createTest(
                "TC_Invalid_Login_004 - Verify invalid credentials login");

        // Navigate to URL
        driver.get(prop.getProperty("AppURL"));
        logger.info("Navigated to URL: {}", prop.getProperty("AppURL"));
        test.info("Navigated to: " + prop.getProperty("AppURL"));

        // Create Login Page object
        LoginPage lp = new LoginPage(driver);

        // Enter invalid username and password
        String invalidUsername = "wrong_user";
        String invalidPassword = "wrong_pass";
        logger.info("Entering invalid username: {}", invalidUsername);
        test.info("Entering invalid username: " + invalidUsername);
        lp.setUsername(invalidUsername);

        logger.info("Entering invalid password: {}", invalidPassword);
        test.info("Entering invalid password: " + invalidPassword);
        lp.setPassword(invalidPassword);

        // Click login
        logger.info("Clicking login button with invalid credentials");
        test.info("Clicking login button with invalid credentials");
        lp.clickLogin();

        // Get error message text
        String actualErrorMsg = lp.getInvalidLoginMessage();
        String expectedErrorMsg = "Invalid credentials";

        logger.info("Validating error message: Expected='{}' | Actual='{}'",
                expectedErrorMsg, actualErrorMsg);
        test.info("Validating error message");

        try {
            Assert.assertEquals(actualErrorMsg, expectedErrorMsg,
                    "Error message did not match for invalid login!");
            
            logger.info("Invalid login validation passed");
            test.pass("Invalid login validation passed");
        } catch (AssertionError e) {
            logger.error("Invalid login validation failed", e);
            test.fail("Invalid login validation failed: " + e.getMessage());
            throw e; // rethrow to mark test as failed
        }

        logger.info("=== TC_Invalid_Login_004 Finished ===");
    }
}
