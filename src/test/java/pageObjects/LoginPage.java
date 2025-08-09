package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Page Object Model for the Login Page.
 */
public class LoginPage extends BasePage {

    WebDriver driver;
    
    public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

    // ---------- Locators ----------
    
    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement txtUsername;

    @FindBy(name = "password")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnLogin;

    @FindBy(xpath = "//p[text()='Invalid credentials']")
    private WebElement invalidMsg;

    @FindBy(xpath = "//span[text()='Required']")
    private WebElement usernameError;

    // ---------- Constructor ----------
   

    // ---------- Action Methods ----------

    public void setUsername(String username) {
        txtUsername.clear();
        txtUsername.sendKeys(username);
    }

    public void setPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void clickLogin() {
        btnLogin.click();
    }

    public String getInvalidLoginMessage() {
        return invalidMsg.getText();
    }

    public boolean isUsernameValidationErrorDisplayed() {
        return usernameError.isDisplayed();
    }
}
