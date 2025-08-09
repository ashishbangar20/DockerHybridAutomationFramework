package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC_InvalidLogin_002 extends BaseClass {

    Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1, groups = {"sanity"})
    public void test_blank_login() {
        logger.info("=== Test_006_Login_Blank_Credentials Started ===");

        logger.info("=== TC_Login_001 Started ===");

        driver.get(prop.getProperty("AppURL"));
        logger.info("Navigated to URL: " + prop.getProperty("AppURL"));


        LoginPage lp = new LoginPage(driver);

        lp.clickLogin();
        logger.info("Clicked Login button without entering credentials");

        boolean validationShown = lp.isUsernameValidationErrorDisplayed();

        Assert.assertTrue(validationShown, "Validation message 'Required' was not displayed.");
        logger.info("Validation message displayed as expected.");

        logger.info("=== Test_006_Login_Blank_Credentials Passed ===");
    }
}
