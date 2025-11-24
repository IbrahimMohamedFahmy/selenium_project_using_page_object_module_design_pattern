package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static PageObjects.P03_Cart.wait;

public class P04_CheckOut
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P04_CheckOut (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Data Members
    // ================= Login / Sign In =================
    @FindBy(css = "input[data-qa='login-email']")
    WebElement emailField;

    @FindBy(css = "input[placeholder='Password']")
    WebElement passwordField;

    @FindBy(css = "button[data-qa='login-button']")
    WebElement loginButton;

    @FindBy(css = "div[class='login-form'] h2")
    WebElement signInHeader;

    // ================= Product Card =================
    @FindBy(xpath = "//p[text()='Blue Top']/ancestor::div[@class='productinfo text-center']")
    WebElement productCard;

    @FindBy(xpath = "//p[text()='Blue Top']/ancestor::div[@class='productinfo text-center']//a[contains(@class,'add-to-cart')]")
    WebElement addToCartButton;

    // ================= Add to Cart Popup =================
    @FindBy(xpath = "//p[text()='Your product has been added to cart.']")
    WebElement addCartSuccessMessage;

    @FindBy(xpath = "//button[normalize-space()='Continue Shopping']")
    WebElement continueShoppingButton;

    // ================= Cart Page =================
    @FindBy(css = ".cart_total_price")
    WebElement productTotalPrice;

    @FindBy(css = ".cart_quantity button")
    WebElement quantityButton;

    @FindBy(css = ".btn.btn-default.check_out")
    WebElement checkoutButton;

    @FindBy(xpath = "(//p[@class='cart_total_price'])[2]")
    WebElement cartTotalPrice;

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    WebElement cartLink;

    // ================= Payment Page =================
    @FindBy(css = "input[name='name_on_card']")
    WebElement cardNameInput;

    @FindBy(css = "input[name='card_number']")
    WebElement cardNumberInput;

    @FindBy(css = "input[placeholder='ex. 311']")
    WebElement cvcInput;

    @FindBy(css = "input[placeholder='MM']")
    WebElement expMonthInput;

    @FindBy(css = "input[placeholder='YYYY']")
    WebElement expYearInput;

    @FindBy(css = "#submit")
    WebElement submitPaymentButton;

    @FindBy(css = "div[class='col-sm-9 col-sm-offset-1'] p")
    WebElement paymentSuccessMessage;

    @FindBy(xpath = "//u[normalize-space()='here']")
    WebElement productsPageLink;


    // Methods
    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public void addProductToCart() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productCard);
        addToCartButton.click();
    }

    public void paymentPage()
    {
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.check_out")));
        checkoutBtn.click();
    };

    public String handleAddCartPopup() {

        // Wait until message is visible
        wait.until(ExpectedConditions.visibilityOf(addCartSuccessMessage));
        String message = addCartSuccessMessage.getText().trim();
        continueShoppingButton.click();
        return message;
    }
    public void enterPaymentDetails(String cardName, String cardNumber, String cvc, String month, String year) {
        cardNameInput.sendKeys(cardName);
        cardNumberInput.sendKeys(cardNumber);
        cvcInput.sendKeys(cvc);
        expMonthInput.sendKeys(month);
        expYearInput.sendKeys(year);
       WebElement e = driver.findElement(By.xpath("//h2[normalize-space()='Payment']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        submitPaymentButton.click();
    }

    public void clickProductsPageLink()
    {
        wait.until(ExpectedConditions.visibilityOf(productsPageLink));
        productsPageLink.click();
    }

    public void goToCartPage() {
        cartLink.click();
    }

    public double getProductTotalPrice() {
        return Double.parseDouble(productTotalPrice.getText().replace("Rs.", "").trim());
    }

    public int getQuantity() {
        return Integer.parseInt(quantityButton.getText().trim());
    }

    public void clickCheckout() {
        checkoutButton.click();
    }

    public double getCartTotalPrice() {
        return Double.parseDouble(cartTotalPrice.getText().replace("Rs.", "").trim());
    }

    public String getCardNameValidationMessage() {
        return cardNameInput.getAttribute("validationMessage");
    }

    public String getCardNumberValidationMessage() {
        return cardNumberInput.getAttribute("validationMessage");
    }

    public String getCVCValidationMessage() {
        return cvcInput.getAttribute("validationMessage");
    }

    public String getExpMonthValidationMessage() {
        return expMonthInput.getAttribute("validationMessage");
    }

    public String getExpYearValidationMessage() {
        return expYearInput.getAttribute("validationMessage");
    }

    public String getPaymentSuccessMessage() {
        return paymentSuccessMessage.getText();
    }


    public String getSignInHeaderText()
    {
        return signInHeader.getText();
    }
}
