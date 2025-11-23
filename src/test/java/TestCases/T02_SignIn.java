package TestCases;

import SetUp.SuperClass;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class T02_SignIn extends SuperClass
{
    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 1, dataProvider = "TestData")
    public void SignInWithValidData(HashMap<String, String> input)
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

        // Step 9: Verify that the SignOut Button's Status
        boolean SignOut = driver.findElement(By.cssSelector("a[href='/logout']")).isDisplayed();

        // Step 10: Verify that the SignOut Button is existed
        soft.assertTrue(SignOut);

        // Step 11: Get The User Name From the Header
        String UserName = driver.findElement(By.cssSelector("li:nth-child(10) a:nth-child(1)")).getText();

        // Step 12: Verify That The User Name From The Header Match The Actual User name
        soft.assertTrue(UserName.contains(input.get("Name")));

        // Step 13: Call `assertAll()` to check all assertions
        soft.assertAll();

    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void SignInWithInValidEmailFormat(HashMap<String, String> input)
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

        // Step 6: Get The Email Text Filed and Type a Invalid Email
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(input.get("InvalidEmail"));

        // Step 7: Get the Password Text Filed and Type a Valid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("Password"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get The Validation of the Email
        String ValidationMessage = driver.findElement(By.cssSelector("input[data-qa='login-email']")).getAttribute("validationMessage");
        System.out.println(ValidationMessage);
        // Step 10: Check The System,s Validation
        soft.assertEquals(ValidationMessage, "Please include an '@' in the email address. 'ibrahimmohamedfahmi1.com' is missing an '@'.");

        // Step 11: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void SignInWithInValidPassword(HashMap<String, String> input)
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

        // Step 7: Get the Password Text Filed and Type an Invalid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("InvalidPassword"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get The System,s Validation
        String Validation = driver.findElement(By.xpath("//p[normalize-space()='Your email or password is incorrect!']")).getText();

        // Step 10: Check The Validation
        soft.assertEquals(Validation, "Your email or password is incorrect!");

        // Step 11: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void SignInWithNotExistingEmail(HashMap<String, String> input)
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

        // Step 6: Get The Email Text Filed and Type a not existing Email
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(getRandomEmail());

        // Step 7: Get the Password Text Filed and Type a Valid Password
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(input.get("Password"));

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get The System,s Validation
        String Validation = driver.findElement(By.xpath("//p[normalize-space()='Your email or password is incorrect!']")).getText();

        // Step 10: Check The Validation
        soft.assertEquals(Validation, "Your email or password is incorrect!");

        // Step 11: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 5, dataProvider = "TestData")
    public void SignInWithEmptyFields(HashMap<String, String> input)
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

        // Step 6: Get The Email Text Filed and Check it is Displayed
        driver.findElement(By.cssSelector("input[data-qa='login-email']")).isDisplayed();

        // Step 7: Get the Password Text Filed and Check it is Displayed
        driver.findElement(By.cssSelector("input[placeholder='Password']")).isDisplayed();

        // Step 8: Get Sign In Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

        // Step 9: Get The Validation of the Email filed
        String ValidationMessage = driver.findElement(By.cssSelector("input[data-qa='login-email']")).getAttribute("validationMessage");

        // Step 10: Check The System,s Validation
        soft.assertEquals(ValidationMessage, "Please fill out this field.");

        // Step 11: Call `assertAll()` to check all assertions
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
