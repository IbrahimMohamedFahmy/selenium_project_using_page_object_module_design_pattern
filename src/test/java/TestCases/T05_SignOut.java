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
        // Step 1: Check Home URL
        String HomeURL = driver.getCurrentUrl();
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        signIn.openLoginPage();

        // Step 4: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        checkOut.login(input.get("ValidEmail"), input.get("Password")); // Login using POM method

        // Step 5: Add Product to Cart
        checkOut.addProductToCart();
        soft.assertEquals(checkOut.handleAddCartPopup(), "Your product has been added to cart."); // Handle popup

        // Step 6: Go to Cart
        checkOut.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 7: Checkout
        checkOut.clickCheckout();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CheckOutPage"));

        // Step 8: Verify Product Price
        double price = checkOut.getProductPrice();
        int quantity = checkOut.getQuantity();
        double expectedTotal = price * quantity;
        soft.assertEquals(expectedTotal, checkOut.getCartTotalPrice());

        // Step 9: Continue to Payment
        checkOut.paymentPage();

        // Step 10: Get Current Url Of the Payment Page
        String PayPage = driver.getCurrentUrl();

        // Step 11: Check the URLs
        soft.assertEquals(PayPage, input.get("PaymentPage"));

        // Step 12: Payment
        checkOut.enterPaymentDetails(
                input.get("CardName"),
                input.get("CardNumber"),
                input.get("CVC"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 13: Verify that the SignOut Button's Status
        boolean SignOut = driver.findElement(By.cssSelector("a[href='/logout']")).isDisplayed();

        // Step 14: Click SignOut
        signOut.SignOutClick();

        // Step 15: Verify that Click SignOut redirect user to home
        soft.assertEquals(driver.getCurrentUrl(), input.get("SignIn"));

        // Step 16: Call `assertAll()` to check all assertions
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
