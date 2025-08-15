package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExtentReportUtility;

public class BaseClass {

    // Thread-safe WebDriver for parallel execution
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    public WebDriver driver;
    public Properties prop;
    public Logger logger;

    @BeforeSuite
    public void reportSetup() {
        ExtentReportUtility.initReport();
    }

    @BeforeClass
    public void setup() throws IOException {
        // Load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fis);

        // Logger setup
        logger = LogManager.getLogger(getClass());

        // Determine browser
        String browser = System.getenv("BROWSER");
        if (browser == null || browser.isEmpty()) {
            browser = prop.getProperty("browser", "chrome");
        }

        WebDriver localDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Unique user data directory to avoid session conflicts
                chromeOptions.addArguments("--user-data-dir=/tmp/chrome-profile-" + UUID.randomUUID());
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.setExperimentalOption("useAutomationExtension", false);

                localDriver = new ChromeDriver(chromeOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");

                localDriver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.addArguments("--headless");
                ffOptions.addArguments("--width=1920");
                ffOptions.addArguments("--height=1080");
                ffOptions.addArguments("--no-sandbox");
                ffOptions.addArguments("--disable-dev-shm-usage");

                localDriver = new FirefoxDriver(ffOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        // Thread-safe driver assignment
        driverThread.set(localDriver);
        driver = driverThread.get();

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("AppURL"));
    }

    // Get the driver for parallel execution
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    // Screenshot utility
    public String captureScreen(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driverThread.get() != null) {
            try {
                driverThread.get().quit();
            } catch (Exception e) {
                logger.error("Error quitting driver: ", e);
            } finally {
                driverThread.remove(); // Clean up ThreadLocal
            }
        }
    }

    @AfterSuite
    public void reportTearDown() {
        ExtentReportUtility.flushReport();
    }
}
