package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P02_SignIn
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P02_SignIn (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Data Members
    @FindBy(css = "a[href='/login']")
    private WebElement signInPageLink;

    @FindBy(css = "div[class='login-form'] h2")
    private WebElement loginFormTitle;

    @FindBy(css = "input[data-qa='login-email']")
    public WebElement loginEmailField;

    @FindBy(css = "input[placeholder='Password']")
    public WebElement loginPasswordField;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[normalize-space()='Your email or password is incorrect!']")
    private WebElement invalidLoginMessage;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(css = "li:nth-child(10) a:nth-child(1)")
    private WebElement loggedInUserName;


    // Methods
    // Open Login Page
    public void openLoginPage() {
        signInPageLink.click();
    }

    // Get Login Form Text
    public String getLoginFormTitle() {
        return loginFormTitle.getText();
    }

    // Type email
    public void typeEmail(String email) {
        loginEmailField.clear();
        loginEmailField.sendKeys(email);
    }

    // Type password
    public void typePassword(String password) {
        loginPasswordField.clear();
        loginPasswordField.sendKeys(password);
    }

    // Click login button
    public void clickLoginButton() {
        loginButton.click();
    }

    // Combined Login Action
    public void login(String email, String password) {
        typeEmail(email);
        typePassword(password);
        clickLoginButton();
    }

    // Check invalid login message
    public String getInvalidLoginText() {
        return invalidLoginMessage.getText();
    }

    // Get HTML validation message
    public String getEmailValidationMessage() {
        return loginEmailField.getAttribute("validationMessage");
    }

    // Check if logout button is displayed
    public boolean isLogoutDisplayed() {
        return logoutButton.isDisplayed();
    }

    // Get logged in username from header
    public String getLoggedInUserName() {
        return loggedInUserName.getText();
    }

    public boolean isEmailFieldDisplayed() {
        return loginEmailField.isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return loginPasswordField.isDisplayed();
    }



}
