package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;

public class LeaveListPage {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public LeaveListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout for Jenkins
        actions = new Actions(driver);
    }

    // ===================== WebElements =====================
    @FindBy(xpath = "//span[text()='Leave']")
    WebElement leaveMenu;

    @FindBy(xpath = "//a[contains(text(),'Leave List')]")
    WebElement leaveListOption;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    WebElement employeeNameInput;

    @FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[1]")
    WebElement fromDateInput;

    @FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[2]")
    WebElement toDateInput;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    WebElement searchButton;

    @FindBy(xpath = "//span[text()='No Records Found']")
    WebElement noRecordsText;

    By resultTableRows = By.xpath("//div[@class='oxd-table-body']/div");

    // ===================== Actions =====================

    /**
     * Navigate to Leave List safely
     */
    public void navigateToLeaveList() {
        // Click Leave menu
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu)).click();

        // Click Leave List option
        wait.until(ExpectedConditions.elementToBeClickable(leaveListOption)).click();
    }

    /**
     * Enter employee name and select from suggestions
     */
    public void enterEmployeeName(String name) {
        employeeNameInput.clear();
        employeeNameInput.sendKeys(name);

        // Wait for dropdown suggestions
        List<WebElement> suggestions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@role='listbox']//span")
                )
        );

        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().equalsIgnoreCase(name)) {
                suggestion.click();
                break;
            }
        }
    }

    /**
     * Enter partial name and select exact employee
     */
    public void enterAndSelectEmployee(String partialName, String fullNameToSelect) {
        employeeNameInput.clear();
        employeeNameInput.sendKeys(partialName);

        List<WebElement> suggestions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@role='listbox']//span")
                )
        );

        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().equalsIgnoreCase(fullNameToSelect)) {
                suggestion.click();
                break;
            }
        }
    }

    /**
     * Set date range
     */
    public void setDateRange(String fromDate, String toDate) {
        fromDateInput.clear();
        fromDateInput.sendKeys(fromDate);

        toDateInput.clear();
        toDateInput.sendKeys(toDate);
    }

    /**
     * Click Search button
     */
    public void clickSearch() {
        searchButton.click();
        waitFor(2);
    }

    /**
     * Check if table has results
     */
    public boolean isResultDisplayed() {
        try {
            List<WebElement> rows = driver.findElements(resultTableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if "No Records Found" message is displayed
     */
    public boolean isNoRecordFound() {
        try {
            return noRecordsText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait utility
     */
    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {}
    }
}
