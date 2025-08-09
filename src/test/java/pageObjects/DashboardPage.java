package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for the Dashboard Page.
 */
public class DashboardPage extends BasePage {

    WebDriver driver;

    // ---------- Constructor ----------
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Locators ----------

    @FindBy(xpath = "//span[normalize-space()='Assign Leave']")
    private WebElement linkAssignLeave;

    // ---------- Action Methods ----------

    public void clickAssignLeave() {
        linkAssignLeave.click();
    }
}
