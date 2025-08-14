package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportUtility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TC_Login_001 extends BaseClass {

    @Test
    public void testLogin() {
        
        // Create ExtentReports test
        ExtentTest test = ExtentReportUtility.createTest("TC_Login_001 - Verify Login");

        logger.info("=== TC_Login_001 Started ===");
        test.info("Test started: Navigating to URL");

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
        logger.info("Clicked Login Button");
        test.info("Clicked Login Button");

        String expectedTitle = "OrangeHRM";
        String actualTitle = driver.getTitle();

        if (actualTitle.equals(expectedTitle)) {
            logger.info("Login Test Passed ✅");
            test.pass("Login Test Passed ✅");
            Assert.assertTrue(true);
        } else {
            String screenshotPath = takeScreenshot("testLogin");
            logger.error("Login Test Failed ❌ - Page title mismatch");
            test.fail("Login Test Failed ❌ - Expected: " + expectedTitle + ", but got: " + actualTitle)
                .addScreenCaptureFromPath(screenshotPath);
            Assert.fail("Page title mismatch - Expected: " + expectedTitle + ", but got: " + actualTitle);
        }
    }

    public String takeScreenshot(String name) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + name + ".png";

        try {
            FileUtils.copyFile(src, new File(screenshotPath));
            logger.info("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }
}
