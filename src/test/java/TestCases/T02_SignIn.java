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
        signIn.openLoginPage();

        // Step 4: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 5: Login with valid data
        signIn.login(input.get("ValidEmail"), input.get("Password"));

        // Step 6: Check logout button displayed
        soft.assertTrue(signIn.isLogoutDisplayed());

        // Step 7: Verify user name exists in header
        String UserName = signIn.getLoggedInUserName();
        soft.assertTrue(UserName.contains(input.get("Name")));

        // Step 8: assert all
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
        signIn.openLoginPage();

        // Step 4: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 5: Enter invalid email + valid password
        signIn.typeEmail(input.get("InvalidEmail"));
        signIn.typePassword(input.get("Password"));

        // Step 6: Click Login Button
        signIn.clickLoginButton();

        // Step 7: Get validation message from browser
        String validationMessage = signIn.getEmailValidationMessage();

        // Step 8: Assert validation text
        soft.assertEquals(validationMessage,
                "Please include an '@' in the email address. 'ibrahimmohamedfahmi1.com' is missing an '@'.");

        // Step 9: Assert all
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void SignInWithInValidPassword(HashMap<String, String> input)
    {
        // Step 1: Assert Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Open Login Page
        signIn.openLoginPage();

        // Step 3: Verify Login Form Text
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 4: Type Valid Email
        signIn.typeEmail(input.get("ValidEmail"));

        // Step 5: Type Invalid Password
        signIn.typePassword(input.get("InvalidPassword"));

        // Step 6: Click Login
        signIn.clickLoginButton();

        // Step 7: Check Validation Error
        soft.assertEquals(signIn.getInvalidLoginText(), "Your email or password is incorrect!");

        // Step 8: Validate Assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void SignInWithNotExistingEmail(HashMap<String, String> input)
    {
        // Step 1: Assert Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Open Login Page
        signIn.openLoginPage();

        // Step 3: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 4: Type Random (Not Existing) Email
        signIn.typeEmail(getRandomEmail());

        // Step 5: Type Valid Password
        signIn.typePassword(input.get("Password"));

        // Step 6: Click Sign In
        signIn.clickLoginButton();

        // Step 7: Verify Error Message
        soft.assertEquals(signIn.getInvalidLoginText(), "Your email or password is incorrect!");

        // Step 8: Validate Assertions
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 5, dataProvider = "TestData")
    public void SignInWithEmptyFields(HashMap<String, String> input)
    {
        // Step 1: Assert Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Open Login Page
        signIn.openLoginPage();

        // Step 3: Verify Login Form Title
        soft.assertEquals(signIn.getLoginFormTitle(), "Login to your account");

        // Step 4: Check Email Field is Displayed
        soft.assertTrue(signIn.isEmailFieldDisplayed());

        // Step 5: Check Password Field is Displayed
        soft.assertTrue(signIn.isPasswordFieldDisplayed());

        // Step 6: Click Login Button Without Filling Anything
        signIn.clickLoginButton();

        // Step 7: Get Browser Validation Message
        String validation = signIn.getEmailValidationMessage();

        // Step 8: Verify Validation Message
        soft.assertEquals(validation, "Please fill out this field.");

        // Step 9: Assert All
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
