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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public WebDriver driver;
    public Properties prop;
    public Logger logger;

    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) throws IOException {

        // 🔹 Load config.properties file
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream("./src/test/resources/config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("❌ Failed to load config.properties");
            throw e;
        }

        // 🔹 Setup logger
        logger = LogManager.getLogger(getClass());
        logger.info("🟢 Logger initialized for " + getClass().getSimpleName());

        // 🔹 Setup WebDriver
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                logger.info("🚀 Chrome browser launched");
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                logger.info("🚀 Firefox browser launched");
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                logger.info("🚀 Edge browser launched");
                break;
            default:
                logger.warn("⚠️ Invalid browser passed: " + browser + ". Launching Chrome by default.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        // 🔹 Configure browser
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 🔹 Navigate to URL from properties file
        String url = prop.getProperty("AppURL");
        if (url != null) {
            driver.get(url);
            logger.info("🌐 Navigated to: " + url);
        } else {
            logger.error("❌ 'AppURL' not found in config.properties!");
            throw new RuntimeException("AppURL is missing in config file.");
        }
    }
    
    public void captureScreen(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        File dest = new File(path);
        try {
            FileUtils.copyFile(src, dest);
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("🔴 Browser closed");
        }
    }
}
