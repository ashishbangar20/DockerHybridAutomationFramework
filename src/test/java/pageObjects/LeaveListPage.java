package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class LeaveListPage {

    WebDriver driver;

    public LeaveListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ WebElements using @FindBy

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

    // ✅ Actions
 // temp comment to test git commit


    public void navigateToLeaveList() {
        leaveMenu.click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement leaveListOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Leave List')]")));
        leaveListOption.click();
    }


    public void enterEmployeeName(String name) {
        employeeNameInput.clear();
        employeeNameInput.sendKeys(name);

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='listbox']//span")));

        driver.findElement(By.xpath("//div[@role='listbox']//span[contains(text(),'" + name + "')]")).click();
    }

    public void enterAndSelectEmployee(String partialName, String fullNameToSelect) {
        employeeNameInput.clear();
        employeeNameInput.sendKeys(partialName);

        List<WebElement> suggestions = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='listbox']//span")));

        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().toLowerCase().contains(fullNameToSelect.toLowerCase())) {
                suggestion.click();
                break;
            }
        }
    }

    public void setDateRange(String fromDate, String toDate) {
        fromDateInput.clear();
        fromDateInput.sendKeys(fromDate);

        toDateInput.clear();
        toDateInput.sendKeys(toDate);
    }

    public void clickSearch() {
        searchButton.click();
        waitFor(2);
    }

    public boolean isResultDisplayed() {
        try {
            List<WebElement> rows = driver.findElements(resultTableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoRecordFound() {
        try {
            return noRecordsText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}
