package pageObjects;

import pageObjects.BasePage;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class AdminUserManegmentPage extends BasePage {

    // Constructor
    public AdminUserManegmentPage(WebDriver driver) {
        super(driver);  // calls BasePage constructor
    }

    // Locators
    @FindBy(xpath = "//span[text()='Admin']")
    WebElement adminTab;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    WebElement usernameField;

    @FindBy(xpath = "//label[text()='User Role']/../following-sibling::div//div[contains(@class,'oxd-select-text')]")
    WebElement userRoleDropdown;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    WebElement employeeNameField;

    @FindBy(xpath = "//label[text()='Status']/following::div[contains(@class,'oxd-select-wrapper')]")
    WebElement statusDropdown;

    @FindBy(xpath = "//div[@role='listbox']//span[text()='Enabled']")
    WebElement statusOptionEnabled;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@role='row']")
    List<WebElement> resultRows;

    // Actions
    public void goToAdminPage() {
        adminTab.click();
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void selectUserRole() {
        userRoleDropdown.click();
        wait.until(ExpectedConditions.visibilityOf(userRoleDropdown)).click();
    }

    public void enterEmployeeName(String empName) {
        employeeNameField.sendKeys(empName);
    }

    public void selectStatus() {
        statusDropdown.click();
        wait.until(ExpectedConditions.visibilityOf(statusOptionEnabled)).click();
    }

    public void clickSearch() {
        searchButton.click();
    }

    public int getResultCount() {
        return resultRows.size();
    }
}
