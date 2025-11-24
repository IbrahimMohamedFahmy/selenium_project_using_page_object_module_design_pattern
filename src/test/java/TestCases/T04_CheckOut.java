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

        // Step 2: Add Product to Cart
        checkOut.addProductToCart();
        soft.assertEquals(checkOut.handleAddCartPopup(), "Your product has been added to cart."); // Handle popup

        // Step 3: Go to Cart
        checkOut.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 4: Checkout
        checkOut.clickCheckout();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CheckOutPage"));

        // Step 5: Verify Product Price
        double price = checkOut.getProductTotalPrice();
        int quantity = checkOut.getQuantity();
        double expectedTotal = price * quantity;
        soft.assertEquals(expectedTotal, checkOut.getCartTotalPrice());

        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.check_out")));
        checkoutBtn.click();
        // Step 32: Get Current Url Of the Payment Page
        String PayPage = driver.getCurrentUrl();

        // Step 33: Check the URLs
        soft.assertEquals(PayPage, input.get("PaymentPage"));
        // Step 6: Payment
        checkOut.enterPaymentDetails(
                input.get("CardName"),
                input.get("CardNumber"),
                input.get("CVC"),
                input.get("ED"),
                input.get("YY")
        );

        // Step 7: Verify Payment Success
        soft.assertEquals(checkOut.getPaymentSuccessMessage(), "Congratulations! Your order has been confirmed!");

        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void UserPassCheckOutItFlowSuccessfully(HashMap<String, String> input)
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

        // Step 9: go to cart page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 10: Get Current Url
        String CartURL = driver.getCurrentUrl();

        // Step 11: Check Actual and Expected URLs
        soft.assertEquals(CartURL, input.get("CartUrl"));

        // Step 12: Get Products Page
        driver.findElement(By.xpath("//u[normalize-space()='here']")).click();

        // Step 13: Get the Target Element
        WebElement productCard = driver.findElement(By.xpath("//p[text()='Blue Top']/ancestor::div[@class='productinfo text-center']"));

        // Step 14: Scroll to Get The Element
        SuperClass.Scroll(productCard);

        // Step 15: click Add to Cart from same card
        productCard.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).click();

        // Step 16: Get Successful Message of add product
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM = popup.getText();

        // Step 17: Check The Message
        soft.assertEquals(SM, "Your product has been added to cart.");

        // Step 18:Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 19: go to cart page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 20: Check Actual and Expected URLs
        soft.assertEquals(CartURL, input.get("CartUrl"));

        // Step 21: Go to The CheckOut Page
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.check_out")));
        checkoutBtn.click();

        // Step 22: Get Current Url
        String checkOutURL = driver.getCurrentUrl();

        // Step 23: Verify the Expected and Current URLs
        soft.assertEquals(checkOutURL, input.get("CheckOutPage"));

        // step 24: Scroll Down To Get Order Info
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Review Your Order']")));

        // Step 25: Get Product Price
        String priceText = driver.findElement(By.cssSelector("td[class='cart_price'] p")).getText();
        double price = Double.parseDouble(priceText.replace("Rs.", "").trim());

        // Step 26: Get Product Quantity
        String quantityText = driver.findElement(By.cssSelector(".cart_quantity button")).getText();
        int quantity = Integer.parseInt(quantityText.trim());

        // Step 27: Get The Total Expected Price
        double TotalProductPrice1 = price * quantity;

        // Step 28: Get The Total Price Element
        String actualTotalText = driver.findElement(By.cssSelector(".cart_total_price")).getText();
        double actualTotal = Double.parseDouble(actualTotalText.replace("Rs.", "").trim());

        // Step 29: Check expectedTotalPrice eq actualTotal
        soft.assertEquals(TotalProductPrice1, actualTotal);

        String actualCartTotalText = driver.findElement(By.xpath("(//p[@class='cart_total_price'])[2]")).getText();
        double actualCartTotal = Double.parseDouble(actualCartTotalText.replace("Rs.", "").trim());

        // Step 30: Check The Price OF all Products On the Checkout Page
        soft.assertEquals(TotalProductPrice1, actualCartTotal);

        // Step 31: Get The Place Order Button
        driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();

        // Step 32: Get Current Url Of the Payment Page
        String PayPage = driver.getCurrentUrl();

        // Step 33: Check the URLs
        soft.assertEquals(PayPage, input.get("PaymentPage"));

        // Step 34: Get the Text Filed For The Card Name
        driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys(input.get("CardName"));

        // Step 35: Get the Text Filed For The Card Name
        driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys(input.get("CardNumber"));

        // Step 36: Get the Text Filed For The CVC
        driver.findElement(By.cssSelector("input[placeholder='ex. 311']")).sendKeys(input.get("CVC"));

        // Step 37: Get the Text Filed For The Expired Day
        driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(input.get("ED"));

        // Step 38: Get the Text Filed For The Expired year
        driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(input.get("YY"));

        // Step 39: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 40: Get SuccessFul Message and Verify it
        String text = driver.findElement(By.cssSelector("div[class='col-sm-9 col-sm-offset-1'] p")).getText();
        soft.assertEquals(text, "Congratulations! Your order has been confirmed!");

        // Step 41: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCardName(HashMap<String, String> input)
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

        // Step 32: Get the Validation For The Card Number Filed
        String Validation = driver.findElement(By.cssSelector("input[name='name_on_card']")).getAttribute("validationMessage");

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

        // Step 38: Check the Validation
        soft.assertEquals(Validation, "Please fill out this field.");

        // Step 39: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCardNumber(HashMap<String, String> input)
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

        // Step 33: Get the Validation For The Card Number Filed
        String Validation = driver.findElement(By.cssSelector("input[name='card_number']")).getAttribute("validationMessage");

        // Step 34: Get the Text Filed For The CVC
        driver.findElement(By.cssSelector("input[placeholder='ex. 311']")).sendKeys(input.get("CVC"));

        // Step 35: Get the Text Filed For The Expired Day
        driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(input.get("ED"));

        // Step 36: Get the Text Filed For The Expired year
        driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(input.get("YY"));

        // Step 37: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 38: Check the Validation
        soft.assertEquals(Validation, "Please fill out this field.");


        // Step 39: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 5, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingCVC(HashMap<String, String> input)
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

        // Step 34: Get the Validation For The CVC Filed
        String Validation = driver.findElement(By.cssSelector("input[placeholder='ex. 311']")).getAttribute("validationMessage");

        // Step 35: Get the Text Filed For The Expired Day
        driver.findElement(By.cssSelector("input[placeholder='MM']")).sendKeys(input.get("ED"));

        // Step 36: Get the Text Filed For The Expired year
        driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(input.get("YY"));

        // Step 37: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 38: Check the Validation
        soft.assertEquals(Validation, "Please fill out this field.");

        // Step 39: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 6, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingED(HashMap<String, String> input)
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

        // Step 35: Get the Validation For Expired Day Filed
        String Validation = driver.findElement(By.cssSelector("input[placeholder='MM']")).getAttribute("validationMessage");

        // Step 36: Get the Text Filed For The Expired year
        driver.findElement(By.cssSelector("input[placeholder='YYYY']")).sendKeys(input.get("YY"));

        // Step 37: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 38: Check the Validation
        soft.assertEquals(Validation, "Please fill out this field.");

        // Step 39: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 7, dataProvider = "TestData")
    public void UserCanNotCheckOutWithMissingEY(HashMap<String, String> input)
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
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-default.check_out")));
        checkoutBtn.click();

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

        // Step 36: Get the Validation For Expired year Filed
        String Validation = driver.findElement(By.cssSelector("input[placeholder='YYYY']")).getAttribute("validationMessage");

        // Step 37: Click ON Submit Button
        SuperClass.Scroll(driver.findElement(By.xpath("//h2[normalize-space()='Payment']")));
        driver.findElement(By.cssSelector("#submit")).click();

        // Step 38: Check the Validation
        soft.assertEquals(Validation, "Please fill out this field.");

        // Step 39: Call `assertAll()` to check all assertions
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
