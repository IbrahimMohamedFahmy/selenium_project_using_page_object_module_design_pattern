package TestCases;

import SetUp.SuperClass;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class T01_SignUp extends SuperClass
{
    @Test(groups = {"Happy Scenarios", "All Scenarios"}, priority = 1, dataProvider = "TestData")
    public void SignUpWithValidData(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign Up Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignUp Form
        String SignUpText = driver.findElement(By.xpath("//h2[text()='New User Signup!']")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "New User Signup!");

        // Step 6: Get The Name Text Field and Type a Valid Name
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(input.get("Name"));

        // Step 7: Get The Email Text Filed and Type A valid Email
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(SuperClass.getRandomEmail());

        // Step 8: Get The SignUp Button And Click ON it
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // Step 9: Get The Current URL of The SignUp Page
        String SignUpURL = driver.getCurrentUrl();

        // Step 10: Check The Current and Expected URLs are Match
        soft.assertEquals(SignUpURL, input.get("SignUpUrl"));

        // Step 11: Get Check Box For "Mr." and Check it
        driver.findElement(By.id("id_gender1")).click();

        // Step 12: Get The Password Text Filed and Type a Valid Password
        driver.findElement(By.id("password")).sendKeys(input.get("Password"));

        // Step 13: Select The Day of Birthday
        SuperClass.Scroll(driver.findElement(By.id("days")));
        SuperClass.Select(By.id("days"), "5");

        // Step 14: Select The Month of Birthday
        SuperClass.Select(By.id("months"), "1");

        // Step 15: Select The Month of Birthday
        SuperClass.Select(By.id("years"), "1998");

        // Step 16: Check Box For newsletters
        driver.findElement(By.id("newsletter")).click();

        // Step 17: Check Box For special offers
        driver.findElement(By.id("optin")).click();

        // Step 18: Get First Name Text Filed and Type a Valid First Name
        driver.findElement(By.id("first_name")).sendKeys(input.get("FirstName"));

        // Step 19: Get Last Name Text Filed and Type a Valid Last Name
        driver.findElement(By.id("last_name")).sendKeys(input.get("LastName"));

        // Step 20: Get Company Text Filed and Type a Valid Company Name
        driver.findElement(By.id("company")).sendKeys(input.get("Company"));

        // Step 21: Get Address Text Filed and Type a Valid address
        driver.findElement(By.id("address1")).sendKeys(input.get("Address"));

        // Step 22: Select The country
        SuperClass.Scroll(driver.findElement(By.id("country")));
        SuperClass.Select(By.id("country"), "United States");

        // Step 23: Get the State Text Filed and Type a Valid State
        driver.findElement(By.id("state")).sendKeys(input.get("Address"));

        // Step 24: Get The City Text Filed and Type a Valid City
        driver.findElement(By.id("city")).sendKeys(input.get("Address"));

        // Step 25: Get The Zipcode Text Filed and Type a Valid Zipcode
        driver.findElement(By.id("zipcode")).sendKeys(input.get("Zipcode"));

        // Step 26: Get The Phone Number Text Filed and Type a Valid Phone Number
        driver.findElement(By.id("mobile_number")).sendKeys(input.get("PhoneNumber"));

        // Step 27: Get The Create Button and Click on it
        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

        // Step 28: Get The Current URL
        wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/account_created"));
        String CreatedAccountURL = driver.getCurrentUrl();

        // Step 29: Verify That The Current and Expected URLs for Created Account Page are Match
        soft.assertEquals(CreatedAccountURL, input.get("CreatedAccountUrl"));

        // Step 30: Get The Successful Text
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title.text-center b")));
        String ActualSuccessfulText = driver.findElement(By.cssSelector("h2[class='title text-center'] b")).getText();

        // Step 31: Verify That the Actual Successful Text and Expected Text are Match
        soft.assertEquals(ActualSuccessfulText, "ACCOUNT CREATED!");

        // Step 32: Get Successful Message
        String ActualSuccessfulMessage = driver.findElement(By.xpath("//p[text()='Congratulations! Your new account has been successfully created!']")).getText();

        // Step 33: Verify that the Actual Successful Message and Expected Successful Message are Match
        soft.assertEquals(ActualSuccessfulMessage, "Congratulations! Your new account has been successfully created!");

        // Step 34: Get The Continue Button and Click on it to go to The Products Page
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();

        // Step 35: Verify that the SignOut Button's Status
        boolean SignOut = driver.findElement(By.cssSelector("a[href='/logout']")).isDisplayed();

        // Step 36: Verify that the SignOut Button is existed
        soft.assertTrue(SignOut);

        // Step 37: Get The User Name From the Header
        String UserName = driver.findElement(By.cssSelector("li:nth-child(10) a:nth-child(1)")).getText();

        // Step 38: Verify That The User Name From The Header Match The Actual User name
        soft.assertTrue(UserName.contains(input.get("Name")));

        // Step 39: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void SignUpWithInvalidEmail(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign Up Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignUp Form
        String SignUpText = driver.findElement(By.xpath("//h2[text()='New User Signup!']")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "New User Signup!");

        // Step 6: Get The Name Text Field and Type a Valid Name
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(input.get("Name"));

        // Step 7: Get The Email Text Filed and Type A valid Email
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(input.get("InvalidEmail"));

        // Step 8: Get The SignUp Button And Click ON it
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // Step 9: Get The Validation Message
        String ValidationMessage =  driver.findElement(By.cssSelector("input[data-qa='signup-email']")).getAttribute("validationMessage");

        // Step 10: Verify the System Validation
        soft.assertEquals(ValidationMessage, "Please include an '@' in the email address. 'ibrahimmohamedfahmi1.com' is missing an '@'.");

        // Step 11: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void SignUpWithEmptyFields(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign Up Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignUp Form
        String SignUpText = driver.findElement(By.xpath("//h2[text()='New User Signup!']")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "New User Signup!");

        // Step 6: Get The Name Text Field and it Displayed
        driver.findElement(By.cssSelector("input[placeholder='Name']")).isDisplayed();

        // Step 7: Get The Email Text Filed and it Displayed
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).isDisplayed();

        // Step 8: Get The SignUp Button And Click ON it
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // Step 9: Get The System Validation
        String ValidationMessage = driver.findElement(By.cssSelector("input[placeholder='Name']")).getAttribute("validationMessage");

        // Step 10: Verify The System's Validation
        soft.assertEquals(ValidationMessage, "Please fill out this field.");

        // Step 11: Call `assertAll()` to check all assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void SignUpWithExistingEmail(HashMap<String, String> input)
    {
        // Step 1: Get the Current URL
        String HomeURL = driver.getCurrentUrl();

        // Step 2: verify That the Current URL and the HomeUrl are Match
        soft.assertEquals(HomeURL, input.get("HomeUrl"));

        // Step 3: Open Sign Up Page
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Step 4: Get The Text Of The SignUp Form
        String SignUpText = driver.findElement(By.xpath("//h2[text()='New User Signup!']")).getText();

        // Step 5: Check The SignUp Text
        soft.assertEquals(SignUpText, "New User Signup!");

        // Step 6: Get The Name Text Field and Type a Valid Name
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(input.get("Name"));

        // Step 7: Get The Email Text Filed and Type A valid Existing Email
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(input.get("ValidEmail"));

        // Step 8: Get The SignUp Button And Click ON it
        driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

        // Step 9: Get The System's  Validation
        String Validation = driver.findElement(By.xpath("//p[text()='Email Address already exist!']")).getText();

        // Step 10: Verify The Validation
        soft.assertEquals(Validation, "Email Address already exist!");

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
