package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P03_Cart
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P03_Cart (WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

    }

    // Data Members
    @FindBy(css = "a[href='/login']")
    private WebElement signInLink;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmailField;

    @FindBy(css = "input[placeholder='Password']")
    private WebElement passwordField;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[normalize-space()='Continue Shopping']")
    private WebElement continueShoppingBtn;

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    private WebElement cartLink;

    @FindBy(xpath = "//p[text()='Your product has been added to cart.']")
    private WebElement successAddToCartMsg;

    // Product Details Page
    @FindBy(css = ".product-information h2")
    private WebElement productDetailsName;

    @FindBy(css = "button.cart")
    private WebElement addToCartBtn;

    // Methods
    public void openSignInPage() {
        signInLink.click();
    }

    public void login(String email, String password) {
        loginEmailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public WebElement getProductCard(String productName) {
        return driver.findElement(
                org.openqa.selenium.By.xpath("//p[text()='" + productName + "']/ancestor::div[@class='productinfo text-center']")
        );
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void addProductToCart(WebElement productCard) {
        productCard.findElement(
                org.openqa.selenium.By.xpath(".//a[contains(@class,'add-to-cart')]")
        ).click();
    }
    public static WebDriverWait wait;
    public String getSuccessAddToCartMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successAddToCartMsg)).getText();
    }

    public void closePopup() {
        continueShoppingBtn.click();
    }

    public void goToCartPage() {
        cartLink.click();
    }

    public String getProductNameInCart(String productHref) {
        return driver.findElement(org.openqa.selenium.By.cssSelector("a[href='" + productHref + "']")).getText();
    }

    // Product Details Page Actions
    public void openProductDetails(String productHref) {
        WebElement productLink = driver.findElement(By.cssSelector("a[href='" + productHref + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productLink);
    }

    public String getProductNameFromDetails() {
        return productDetailsName.getText();
    }

    public void clickAddToCartFromDetails() {
        scrollToElement(addToCartBtn);
        addToCartBtn.click();
    }
}
