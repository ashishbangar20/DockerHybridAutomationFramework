package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExtentReportUtility;

public class BaseClass {

    public WebDriver driver;
    public Properties prop;
    public Logger logger;

    @BeforeSuite
    public void reportSetup() {
        ExtentReportUtility.initReport();
    }

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) throws IOException {
        // Load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fis);

        // Logger setup
        logger = LogManager.getLogger(getClass());

        // Setup browser
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                
                // Headless for Jenkins
                if (System.getProperty("os.name").toLowerCase().contains("windows") && System.getenv("JENKINS_HOME") != null) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                }
                
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open URL
        driver.get(prop.getProperty("AppURL"));
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

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void reportTearDown() {
        ExtentReportUtility.flushReport();
    }
}
