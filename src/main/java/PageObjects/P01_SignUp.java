package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class P01_SignUp
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P01_SignUp (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Data Members
    @FindBy(css = "a[href='/login']")
    private WebElement loginLink;

    @FindBy(xpath = "//h2[text()='New User Signup!']")
    private WebElement signUpFormHeader;

    @FindBy(css = "input[placeholder='Name']")
    public WebElement nameInput;

    @FindBy(css = "input[data-qa='signup-email']")
    public WebElement signupEmailInput;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    // -------------------- Account Information --------------------
    @FindBy(id = "id_gender1")
    private WebElement genderMr;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement daysSelect;

    @FindBy(id = "months")
    private WebElement monthsSelect;

    @FindBy(id = "years")
    private WebElement yearsSelect;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement optinCheckbox;

    // -------------------- Address --------------------
    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "country")
    private WebElement countrySelect;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountButton;

    // -------------------- Account Created Page --------------------
    @FindBy(css = "h2.title.text-center b")
    private WebElement accountCreatedTitle;

    @FindBy(xpath = "//p[text()='Congratulations! Your new account has been successfully created!']")
    private WebElement accountCreatedMessage;

    @FindBy(css = ".btn.btn-primary")
    private WebElement continueButton;

    // -------------------- Header --------------------
    @FindBy(css = "a[href='/logout']")
    private WebElement signOutLink;

    @FindBy(css = "li:nth-child(10) a:nth-child(1)")
    private WebElement headerUserName;

    @FindBy(xpath = "//p[text()='Email Address already exist!']")
    public WebElement existingEmailValidation;


    // Methods
    // -------------------- Open Sign Up Page --------------------
    public void openLoginPage() {
        loginLink.click();
    }

    public String getSignUpHeader() {
        return signUpFormHeader.getText();
    }

    public void enterName(String name) {
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        signupEmailInput.sendKeys(email);
    }

    public void clickSignUpButton() {
        signupButton.click();
    }

    // -------------------- Fill Account Information --------------------
    public void selectGenderMr() {
        genderMr.click();
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void selectDay(String day) {
        Select select = new Select(daysSelect);
        select.selectByValue(day);
    }

    public void selectMonth(String month) {
        Select select = new Select(monthsSelect);
        select.selectByValue(month);
    }

    public void selectYear(String year) {
        Select select = new Select(yearsSelect);
        select.selectByValue(year);
    }

    public void checkNewsletter() {
        newsletterCheckbox.click();
    }

    public void checkSpecialOffers() {
        optinCheckbox.click();
    }

    // -------------------- Fill Address --------------------
    public void enterFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void enterCompany(String company) {
        companyInput.sendKeys(company);
    }

    public void enterAddress(String address) {
        address1Input.sendKeys(address);
    }

    public void selectCountry(String country) {
        Select select = new Select(countrySelect);
        select.selectByValue(country);
    }


    public void enterState(String state) {
        stateInput.sendKeys(state);
    }

    public void enterCity(String city) {
        cityInput.sendKeys(city);
    }

    public void enterZipcode(String zipcode) {
        zipcodeInput.sendKeys(zipcode);
    }

    public void enterPhone(String phone) {
        mobileNumberInput.sendKeys(phone);
    }

    public void clickCreateAccount() {
        createAccountButton.click();
    }

    // -------------------- Account Created --------------------
    public String getAccountCreatedTitle() {
        return accountCreatedTitle.getText();
    }

    public String getAccountCreatedMessage() {
        return accountCreatedMessage.getText();
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    // -------------------- Header --------------------
    public boolean isSignOutDisplayed() {
        return signOutLink.isDisplayed();
    }

    public String getHeaderUserName() {
        return headerUserName.getText();
    }

}
