package pageObjects;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for the Admin Page.
 */
public class AdminPage extends BasePage {

    WebDriver driver;

    public AdminPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // ----------- WebElements -------------

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenu;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    private WebElement usernameField;

    @FindBy(xpath = "//label[text()='User Role']/following::div[contains(@class,'oxd-select-wrapper')]")
    private WebElement userRoleDropdown;

    @FindBy(xpath = "//div[@role='listbox']//span")
    private List<WebElement> userRoleOptions;

    @FindBy(xpath = "//label[text()='Employee Name']/following::input[1]")
    private WebElement empNameField;

    @FindBy(xpath = "//label[text()='Status']/following::div[contains(@class,'oxd-select-wrapper')]")
    private WebElement statusDropdown;

    @FindBy(xpath = "//div[@role='listbox']//span")
    private List<WebElement> statusOptions;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-table-body']//div[@role='row']")
    private List<WebElement> resultRows;


    // ----------- Action Methods -------------

    public void clickAdminMenu() {
        adminMenu.click();
    }

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void selectUserRole(String roleName) {
        userRoleDropdown.click();
        for (WebElement role : userRoleOptions) {
            if (role.getText().trim().equals(roleName)) {
                role.click();
                break;
            }
        }
    }

    public void enterEmployeeName(String empName) {
        empNameField.clear();
        empNameField.sendKeys(empName);
        empNameField.sendKeys(Keys.ENTER);
    }

    public void selectStatus(String status) {
        statusDropdown.click();
        for (WebElement stat : statusOptions) {
            if (stat.getText().trim().equals(status)) {
                stat.click();
                break;
            }
        }
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public boolean isResultFound() {
        return resultRows.size() > 0;
    }
}
