package TestCases;

import SetUp.SuperClass;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class T05_SignOut extends SuperClass
{
    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 1, dataProvider = "TestData")
    public void SignOutSuccessfullyAfterSuccessFulSignUp(HashMap<String, String> input)
    {
        // STEP 1: Verify Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // STEP 2: Open Login/Signup Page
        signUp.openLoginPage();

        // STEP 3: Verify Sign Up Text
        soft.assertEquals(signUp.getSignUpHeader(), "New User Signup!");

        // STEP 4: Enter valid name
        signUp.enterName(input.get("Name"));

        // STEP 5: Enter random email
        signUp.enterEmail(SuperClass.getRandomEmail());

        // STEP 6: Click Signup button
        signUp.clickSignUpButton();

        // STEP 7: Verify redirected to signup form
        soft.assertEquals(driver.getCurrentUrl(), input.get("SignUpUrl"));

        // STEP 8: Select Title (Mr.)
        signUp.selectGenderMr();

        // STEP 9: Enter Password
        signUp.enterPassword(input.get("Password"));

        // STEP 10: Select Date of Birth
        signUp.selectDay("5");
        signUp.selectMonth("1");
        signUp.selectYear("1998");

        // STEP 11: Check Newsletter & Offers
        signUp.checkNewsletter();
        signUp.checkSpecialOffers();

        // STEP 12: Fill address info
        signUp.enterFirstName(input.get("FirstName"));
        signUp.enterLastName(input.get("LastName"));
        signUp.enterCompany(input.get("Company"));
        signUp.enterAddress(input.get("Address"));

        // STEP 13: Select Country
        signUp.selectCountry("United States");

        // STEP 14: Fill State - City - Zip - Mobile
        signUp.enterState(input.get("Address"));
        signUp.enterCity(input.get("Address"));
        signUp.enterZipcode(input.get("Zipcode"));
        signUp.enterPhone(input.get("PhoneNumber"));

        // STEP 15: Click Create Account Button
        signUp.clickCreateAccount();

        // STEP 16: Wait URL
        wait.until(ExpectedConditions.urlToBe(input.get("CreatedAccountUrl")));
        soft.assertEquals(driver.getCurrentUrl(), input.get("CreatedAccountUrl"));

        // STEP 17: Verify success title
        soft.assertEquals(signUp.getAccountCreatedTitle(), "ACCOUNT CREATED!");

        // STEP 18: Verify success message
        soft.assertEquals(signUp.getAccountCreatedMessage(),
                "Congratulations! Your new account has been successfully created!");

        // STEP 19: Click Continue
        signUp.clickContinueButton();

        // STEP 20: Verify SignOut visible
        soft.assertTrue(signUp.isSignOutDisplayed());

        // Step 21: Click SignOut
        signOut.SignOutClick();

        // Step 22: Verify that Click SignOut redirect user to home
        soft.assertEquals(driver.getCurrentUrl(), input.get("SignIn"));

        // Step 13: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void SignOutSuccessfullyAfterSuccessFulSignIn(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        signIn.openLoginPage();

        // Step 4: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 5: Login with valid data
        signIn.login(input.get("ValidEmail"), input.get("Password"));

        // STEP 6: Verify SignOut visible
        soft.assertTrue(signUp.isSignOutDisplayed());

        // Step 7: Click SignOut
        signOut.SignOutClick();

        // Step 8: Verify that Click SignOut redirect user to home
        soft.assertEquals(driver.getCurrentUrl(), input.get("SignIn"));

        // Step 9: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void SignOutSuccessfullyAfterSuccessFulCartFlow(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 2: Open Sign In Page and Login
        cart.openSignInPage();
        cart.login(input.get("ValidEmail"), input.get("Password"));

        // Step 3: Get Product Card
        WebElement productCard = cart.getProductCard("Blue Top");

        // Step 4: Scroll to Product Card
        cart.scrollToElement(productCard);

        // Step 5: Get Product Name
        String productName = productCard.findElement(org.openqa.selenium.By.tagName("p")).getText();

        // Step 6: Add Product to Cart
        cart.addProductToCart(productCard);

        // Step 7: Validate Success Message
        String successMessage = cart.getSuccessAddToCartMessage();
        soft.assertEquals(successMessage, "Your product has been added to cart.");

        // Step 8: Close Popup
        cart.closePopup();

        // Step 9: Go to Cart Page
        cart.goToCartPage();

        // Step 10: Validate Cart URL
        String cartURL = driver.getCurrentUrl();
        soft.assertEquals(cartURL, input.get("CartUrl"));

        // Step 11: Get Product Name from Cart and Validate
        String cartProductName = cart.getProductNameInCart("/product_details/1");
        soft.assertEquals(cartProductName, productName);

        // STEP 12: Verify SignOut visible
        soft.assertTrue(signUp.isSignOutDisplayed());

        // Step 13: Click SignOut
        signOut.SignOutClick();

        // Step 14: Verify that Click SignOut redirect user to home
        soft.assertEquals(driver.getCurrentUrl(), input.get("SignIn"));

        // Step 15: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void SignOutSuccessfullyAfterSuccessFulCheckFlow(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignIn Form
        String SignUpText =driver.findElement(By.cssSelector("div[class='login-form'] h2")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "Login to your account");

        // Step 6: Get The Email Text Filed and Type a Valid Email
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(input.get("ValidEmail"));

        // Step 7: Get the Password Text Filed and Type a Valid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("Password"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get the Target Element
        WebElement productCard = driver.findElement(By.xpath("//p[text()='Blue Top']/ancestor::div[@class='productinfo text-center']"));

        // Step 10: Scroll to Get The Element
        SuperClass.Scroll(productCard);

        // Step 12: click Add to Cart from same card
        productCard.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).click();

        // Step 13: Get Successful Message of add product
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM = popup.getText();

        // Step 14: Check The Message
        soft.assertEquals(SM, "Your product has been added to cart.");

        // Step 15:Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 16: go to cart page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 17: Get Current Url
        String CartURL = driver.getCurrentUrl();

        // Step 18: Check Actual and Expected URLs
        soft.assertEquals(CartURL, input.get("CartUrl"));

        // Step 19: Go to The CheckOut Page
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.check_out")));
        checkoutBtn.click();

        // Step 20: Get Current Url
        String checkOutURL = driver.getCurrentUrl();

        // Step 21: Verify the Expected and Current URLs
        soft.assertEquals(checkOutURL, input.get("CheckOutPage"));

        // step 22: Scroll Down To Get Order Info
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Review Your Order']")));

        // Step 23: Get Product Price
        String priceText = driver.findElement(By.cssSelector("td[class='cart_price'] p")).getText();
        double price = Double.parseDouble(priceText.replace("Rs.", "").trim());

        // Step 24: Get Product Quantity
        String quantityText = driver.findElement(By.cssSelector(".cart_quantity button")).getText();
        int quantity = Integer.parseInt(quantityText.trim());

        // Step 25: Get The Total Expected Price
        double TotalProductPrice1 = price * quantity;

        // Step 26: Get The Total Price Element
        String actualTotalText = driver.findElement(By.cssSelector(".cart_total_price")).getText();
        double actualTotal = Double.parseDouble(actualTotalText.replace("Rs.", "").trim());

        // Step 27: Check expectedTotalPrice eq actualTotal
        soft.assertEquals(TotalProductPrice1, actualTotal);

        // Step 27: Check expectedTotalPrice eq actualTotal
        String actualCartTotalText = driver.findElement(By.xpath("(//p[@class='cart_total_price'])[2]")).getText();
        double actualCartTotal = Double.parseDouble(actualCartTotalText.replace("Rs.", "").trim());

        // Step 28: Check The Price OF all Products On the Checkout Page
        soft.assertEquals(TotalProductPrice1, actualCartTotal);

        // Step 29: Get The Place Order Button
        driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();

        // Step 30: Get Current Url Of the Payment Page
        String PayPage = driver.getCurrentUrl();

        // Step 31: Check the URLs
        soft.assertEquals(PayPage, input.get("PaymentPage"));

        // Step 32: Get the Text Filed For The Card Name
        driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys(input.get("CardName"));

        // Step 33: Get the Text Filed For The Card Name
        driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys(input.get("CardNumber"));

        // Step 34: Get the Text Filed For The CVC
        driver.findElement(By.cssSelector("input[placeholder='ex. 311']")).sendKeys(input.get("CVC"));

        // Step 35: Get the Text Filed For The Expired Day
        driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(input.get("ED"));

        // Step 36: Get the Text Filed For The Expired year
        driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(input.get("YY"));

        // Step 37: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 38: Get SuccessFul Message and Verify it
        String text = driver.findElement(By.cssSelector("div[class='col-sm-9 col-sm-offset-1'] p")).getText();
        soft.assertEquals(text, "Congratulations! Your order has been confirmed!");

        // Step 39: Verify that the SignOut Button's Status
        boolean SignOut = driver.findElement(By.cssSelector("a[href='/logout']")).isDisplayed();

        // Step 40: Verify that the SignOut Button is existed
        soft.assertTrue(SignOut);

        // Step 41: Verify that the SignOut Button's Status
        driver.findElement(By.cssSelector("a[href='/logout']")).click();

        // Step 42: Verify that Sign in Button is Displayed
        Boolean Sign_In = driver.findElement(By.cssSelector("a[href='/login']")).isDisplayed();
        soft.assertTrue(Sign_In);

        // Step 43: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 5, dataProvider = "TestData")
    public void UserCanNotSignOutWithOutSignIn(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Try locating SignOut button
        List<WebElement> signOutBtn = driver.findElements(By.cssSelector("a[href='/logout']"));

        // Step 4: Verify that the SignOut Button is NOT displayed
        soft.assertTrue(signOutBtn.isEmpty(), "SignOut button should NOT be visible for non-logged user");

        // Step 5: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @DataProvider
    public Object[][] TestData() throws IOException
    {
        String jsonFile = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//main//resources//TestData.json"), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, String>> DataList = objectMapper.readValue(jsonFile, new TypeReference<List<HashMap<String, String>>>() {});

        Object[][] Data = new Object[DataList.size()][1];
        for(int i = 0 ; i < DataList.size() ; i++)
        {
            Data[i][0] = DataList.get(i);
        }
        return Data;

    }
}
