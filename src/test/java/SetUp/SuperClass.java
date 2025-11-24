package SetUp;

import PageObjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SuperClass
{
    // Create the WebDriver as a Global variable
    public static WebDriver driver;

    // Create a global variable for soft assert
    public SoftAssert soft;

    public WebDriverWait wait;

    // Create Object From Class Select
    public static void Select(By locator, String text)
    {
        Select select = new Select(driver.findElement(locator));
        select.selectByValue(text);
    };

    // Create Scroll Method
    public static void Scroll(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    };

    // Create Method to Generate a Random Email
    public static String getRandomEmail()
    {
        String randomPart = java.util.UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomPart + "@test.com";
    }

    // Make The Pages As Global
    public P01_SignUp signUp;
    public P02_SignIn signIn;
    public P03_Cart cart;
    public P04_CheckOut checkOut;
    public P05_SignOut signOut;

@BeforeMethod
public void OpenDriver()
{
    // Step 1: Create Object from
    driver = new ChromeDriver();

    // Step 2: Manage Conditional Synchronisation "implicit wait"
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    // Step 3: Manage Conditional Synchronisation "explicit wait"
    wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    // Step 4: Manage Window Maximize
    driver.manage().window().maximize();

    // Step 5: Create Object From Class SoftAssert
    soft = new SoftAssert();

    // Step 6: Launch The WebSide
    driver.get("https://automationexercise.com/");

    // Create Objects For All pages
    signUp = new P01_SignUp(driver);
    signIn = new P02_SignIn(driver);
    cart = new P03_Cart(driver);
    checkOut = new P04_CheckOut(driver);
    signOut = new P05_SignOut(driver);
};

@AfterMethod
public void CloseDriver() throws InterruptedException {

    // Step 1: Manage unconditional synchronization
    Thread.sleep(3000);

    //Step 2: Close Driver
    driver.quit();
};

}
