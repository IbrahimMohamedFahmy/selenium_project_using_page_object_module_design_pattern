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

        // Step 12: Final Assertion
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void AddMoreThanProductToCartSuccessfully(HashMap<String, String> input)
    {
        // Step 1: Get Current URL and verify
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Login
        cart.openSignInPage();
        soft.assertEquals(driver.findElement(By.cssSelector("div[class='login-form'] h2")).getText(), "Login to your account");
        cart.login(input.get("ValidEmail"), input.get("Password"));

        // Step 3: Add Product 1 ("Blue Top") to Cart
        WebElement productCard1 = cart.getProductCard("Blue Top");
        cart.scrollToElement(productCard1);
        String productName1 = productCard1.findElement(By.tagName("p")).getText();
        cart.addProductToCart(productCard1);
        soft.assertEquals(cart.getSuccessAddToCartMessage(), "Your product has been added to cart.");
        cart.closePopup();

        // Step 4: Add Product 2 ("Men Tshirt") to Cart
        WebElement productCard2 = cart.getProductCard("Men Tshirt");
        cart.scrollToElement(productCard2);
        String productName2 = productCard2.findElement(By.tagName("p")).getText();
        cart.addProductToCart(productCard2);
        soft.assertEquals(cart.getSuccessAddToCartMessage(), "Your product has been added to cart.");
        cart.closePopup();

        // Step 5: Go to Cart Page and Verify URL
        cart.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 6: Verify Products in Cart
        soft.assertEquals(cart.getProductNameInCart("/product_details/1"), productName1);
        soft.assertEquals(cart.getProductNameInCart("/product_details/2"), productName2);

        // Step 7: Remove Product 2 from Cart
        driver.findElement(By.cssSelector("tr[id='product-2'] a[class='cart_quantity_delete']")).click();

        // Step 8: Final Soft Assert
        soft.assertAll();
    };

    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void AddAProductToCartFromHisPageSuccessfully(HashMap<String, String> input)
    {
        // Step 1: Verify Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Login
        cart.openSignInPage();
        soft.assertEquals(signIn.getLoginFormTitle(),  "Login to your account");
        cart.login(input.get("ValidEmail"), input.get("Password"));

        // Step 3: Open Product Details Page (Product 1)
        cart.openProductDetails("/product_details/1");
        soft.assertEquals(driver.getCurrentUrl(), input.get("ProductDetailsUrl"));

        // Step 4: Get Product Name from Details Page
        String productName = cart.getProductNameFromDetails();

        // Step 5: Add Product to Cart
        cart.clickAddToCartFromDetails();
        soft.assertEquals(cart.getSuccessAddToCartMessage(), "Your product has been added to cart.");
        cart.closePopup();

        // Step 6: Go to Cart Page and Verify URL
        cart.goToCartPage();
        soft.assertEquals(driver.getCurrentUrl(), input.get("CartUrl"));

        // Step 7: Verify Product in Cart
        soft.assertEquals(cart.getProductNameInCart("/product_details/1"), productName);

        // Step 8: Final Soft Assert
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
