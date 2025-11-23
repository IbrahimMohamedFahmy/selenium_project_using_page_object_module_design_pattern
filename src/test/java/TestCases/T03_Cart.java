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

public class T03_Cart extends SuperClass
{
    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 1, dataProvider = "TestData")
    public void AddAProductToCartSuccessfully(HashMap<String, String> input)
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

        // Step 11: Get Product Name
        String productName = productCard.findElement(By.tagName("p")).getText();

        // Step 12: click Add to Cart from same card
        productCard.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).click();

        // Step 13: Get Successful Message of add product
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM = popup.getText();

        // Step 14: Check The Message
        soft.assertEquals(SM, "Your product has been added to cart.");

        // Step 15:Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 15: go to cart page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 16: Get Current Url
        String CartURL = driver.getCurrentUrl();

        // Step 15: Check Actual and Expected URLs
        soft.assertEquals(CartURL, input.get("CartUrl"));

        // Step 16: Get The Product Name From the Cart
        String CartProductName = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();

        // Step 17: Check if The Product's Names Match or Not
        soft.assertEquals(CartProductName, productName);

        // Step 18: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void AddMoreThanProductToCartSuccessfully(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignIn Form
        String SignUpText = driver.findElement(By.cssSelector("div[class='login-form'] h2")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "Login to your account");

        // Step 6: Get The Email Text Filed and Type a Valid Email
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(input.get("ValidEmail"));

        // Step 7: Get the Password Text Filed and Type a Valid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("Password"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get the Target Element (Product 1)
        WebElement productCard1 = driver.findElement(By.xpath("//p[text()='Blue Top']/ancestor::div[@class='productinfo text-center']"));

        // Step 10: Scroll to Get The Element
        SuperClass.Scroll(productCard1);

        // Step 11: Get Product Name
        String productName1 = productCard1.findElement(By.tagName("p")).getText();

        // Step 12: click Add to Cart from same card
        productCard1.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).click();

        // Step 13: Get Successful Message of add product
        WebElement popup1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM1 = popup1.getText();

        // Step 14: Check The Message
        soft.assertEquals(SM1, "Your product has been added to cart.");

        // Step 15:Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 16(P2): Get the Target Element (Product 2)
        WebElement productCard2 = driver.findElement(By.xpath("//p[text()='Men Tshirt']/ancestor::div[@class='productinfo text-center']"));

        // Step 17(P2): Scroll
        SuperClass.Scroll(productCard2);

        // Step 18(P2): Get Product Name
        String productName2 = productCard2.findElement(By.tagName("p")).getText();

        // Step 19(P2): Add to cart
        productCard2.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]")).click();

        // Step 20(P2): Get Success Message
        WebElement popup2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM2 = popup2.getText();

        // Step 21(P2): Validate success
        soft.assertEquals(SM2 ,"Your product has been added to cart.");

        // Step 22(P2): Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 23: go to cart page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 24: Get Current Url
        String CartURL = driver.getCurrentUrl();

        // Step 25: Check Actual and Expected URLs
        soft.assertEquals(CartURL, input.get("CartUrl"));

        // Step 26: Product 1 in cart
        String CartProductName1 = driver.findElement(By.xpath("//a[@href='/product_details/1']")).getText();

        // Step 27: Product 2 in cart
        String CartProductName2 = driver.findElement(By.xpath("//a[@href='/product_details/2']")).getText();

        // Step 28: Check if The Product's Names Match
        soft.assertEquals(CartProductName1, productName1);
        soft.assertEquals(CartProductName2, productName2);

        // Step 29: Clean The Product 2
        driver.findElement(By.cssSelector("tr[id='product-2'] a[class='cart_quantity_delete']")).click();

        // Step 29: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void AddAProductToCartFromHisPageSuccessfully(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign In Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignIn Form
        String SignUpText = driver.findElement(By.cssSelector("div[class='login-form'] h2")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "Login to your account");

        // Step 6: Get The Email Text Filed and Type a Valid Email
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(input.get("ValidEmail"));

        // Step 7: Get the Password Text Filed and Type a Valid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("Password"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get the Target Element
        WebElement productCard = driver.findElement(By.cssSelector("a[href='/product_details/1']"));

        // Step 10: Scroll to Get The Element
        SuperClass.Scroll(productCard);
        productCard.click();

        // Step 11: Verify Product Details URL
        String productURL = driver.getCurrentUrl();
        soft.assertEquals(productURL, input.get("ProductDetailsUrl"));

        // Step 12: Get Product Name From Product Details Page
        String productName = driver.findElement(By.cssSelector(".product-information h2")).getText();

        // Step 13: Scroll to Add to Cart Button
        WebElement addToCartBtn = driver.findElement(By.cssSelector("button.cart"));

        // Step 14: Click Add to Cart Button
        addToCartBtn.click();

        // Step 15: Get Successful Message
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your product has been added to cart.']")));
        String SM = popup.getText();

        // Step 16: Validate Message
        soft.assertEquals(SM, "Your product has been added to cart.");

        // Close popup
        driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();

        // Step 17: Go to Cart Page
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // Step 18: Get Cart URL
        String cartURL = driver.getCurrentUrl();

        // Step 19: Validate Cart URL
        soft.assertEquals(cartURL, input.get("CartUrl"));

        // Step 20: Get Product Name from Cart
        String productNameInCart = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();

        // Step 21: Compare product name
        soft.assertEquals(productNameInCart, productName);

        // Step 22: Final Soft Assert Check
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
