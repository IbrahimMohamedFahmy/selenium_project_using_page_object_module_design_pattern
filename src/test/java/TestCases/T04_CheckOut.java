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

public class T04_CheckOut extends SuperClass
{
    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 1, dataProvider = "TestData")
    public void UserAddProductAndCheckOutItFlowSuccessfully(HashMap<String, String> input)
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

        // Step 13: Verify Payment Success
        soft.assertEquals(checkOut.getPaymentSuccessMessage(), "Congratulations! Your order has been confirmed!");

        // Step 41: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void UserPassCheckOutItFlowSuccessfully(HashMap<String, String> input)
    {
        // Step 1: Check Home URL
        String HomeURL = driver.getCurrentUrl();
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        signIn.openLoginPage();

        // Step 4: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        checkOut.login(input.get("ValidEmail"), input.get("Password")); // Login using POM method

        // Step 6: Go to Cart
        checkOut.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 7: Get Products Page
        checkOut.clickProductsPageLink();

        // Step 8: Add Product to Cart
        checkOut.addProductToCart();
        soft.assertEquals(checkOut.handleAddCartPopup(), "Your product has been added to cart."); // Handle popup

        // Step 9: Go to Cart
        checkOut.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 10: Checkout
        checkOut.clickCheckout();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CheckOutPage"));

        // Step 11: Verify Product Price
        double price = checkOut.getProductPrice();
        int quantity = checkOut.getQuantity();
        double expectedTotal = price * quantity;
        soft.assertEquals(expectedTotal, checkOut.getCartTotalPrice());

        // Step 12: Continue to Payment
        checkOut.paymentPage();

        // Step 13: Get Current Url Of the Payment Page
        String PayPage = driver.getCurrentUrl();

        // Step 14: Check the URLs
        soft.assertEquals(PayPage, input.get("PaymentPage"));

        // Step 15: Payment
        checkOut.enterPaymentDetails(
                input.get("CardName"),
                input.get("CardNumber"),
                input.get("CVC"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 16: Verify Payment Success
        soft.assertEquals(checkOut.getPaymentSuccessMessage(), "Congratulations! Your order has been confirmed!");

        // Step 17: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCardName(HashMap<String, String> input)
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
                input.get("NoValue"),
                input.get("CardNumber"),
                input.get("CVC"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 13: Check the Validation
        soft.assertEquals(checkOut.getCardNameValidationMessage(), "Please fill out this field.");

        // Step 14: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCardNumber(HashMap<String, String> input)
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
                input.get("NoValue"),
                input.get("CVC"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 13: Check the Validation
        soft.assertEquals(checkOut.getCardNumberValidationMessage(), "Please fill out this field.");

        // Step 14: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 5, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCVC(HashMap<String, String> input)
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
                input.get("NoValue"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 13: Check the Validation
        soft.assertEquals(checkOut.getCVCValidationMessage(), "Please fill out this field.");

        // Step 14: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 6, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingED(HashMap<String, String> input)
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
                input.get("NoValue"),
                input.get("YY")
        );

        // Step 13: Check the Validation
        soft.assertEquals(checkOut.getExpMonthValidationMessage(), "Please fill out this field.");

        // Step 14: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 7, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingEY(HashMap<String, String> input)
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
                input.get("NoValue")
        );

        // Step 13: Check the Validation
        soft.assertEquals(checkOut.getExpYearValidationMessage(), "Please fill out this field.");

        // Step 14: Call `assertAll()` to check all assertions
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
