package pageObjects;

import pageObjects.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.util.List;

public class assignLeavePage extends BasePage {

    // Constructor
    public assignLeavePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    @FindBy(xpath = "//div[@title='Assign Leave']/preceding-sibling::button")
    WebElement assignLeaveMenu;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    WebElement employeeNameInput;

    @FindBy(xpath = "//div[@role='listbox']//span")
    List<WebElement> suggestionList;

    @FindBy(xpath = "//label[text()='Leave Type']/../following-sibling::div//div[contains(@class,'oxd-select-text')]")
    WebElement leaveTypeDropdown;

    @FindBy(xpath = "//div[@role='listbox']//span[text()='CAN - Bereavement']")
    WebElement leaveTypeOption;

    @FindBy(xpath = "//input[@placeholder='yyyy-dd-mm'][1]")
    WebElement fromDatePicker;

    @FindBy(xpath = "//label[text()='To Date']/../following-sibling::div//input")
    WebElement toDatePicker;

    @FindBy(xpath = "//label[text()='Duration']/../following-sibling::div//div[contains(@class,'oxd-select-text')]")
    WebElement durationDropdown;

    @FindBy(xpath = "//div[@role='listbox']//span[text()='Full Day']")
    WebElement durationOption;

    @FindBy(xpath = "//textarea")
    WebElement commentsInput;

    @FindBy(xpath = "//button[normalize-space()='Assign']")
    WebElement assignButton;

    @FindBy(xpath = "//span[contains(text(),'Balance not sufficient')]")
    WebElement leaveBalanceWarning;

    // Actions
    public void clickAssignLeaveMenu() {
        assignLeaveMenu.click();
    }

    public void enterEmployeeName(String empName) {
        employeeNameInput.clear();
        employeeNameInput.sendKeys(empName);

        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        for (WebElement suggestion : suggestionList) {
            if (suggestion.getText().toLowerCase().contains(empName.toLowerCase())) {
                suggestion.click();
                break;
            }
        }
    }

    public void selectLeaveType() {
        leaveTypeDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeOption)).click();
    }

    public void selectFromDate(String fromDate) {
        fromDatePicker.clear();
        fromDatePicker.sendKeys(fromDate);
    }

    public void selectToDate(String toDate) {
        toDatePicker.clear();
        toDatePicker.sendKeys(toDate);
    }

    public void selectDuration() {
        durationDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(durationOption)).click();
    }

    public void enterComment(String comment) {
        commentsInput.sendKeys(comment);
    }

    public void clickAssign() {
        assignButton.click();
    }

    public boolean isLeaveBalanceWarningDisplayed() {
        try {
            return leaveBalanceWarning.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
