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

        // STEP 21: Verify Username shown in header
        soft.assertTrue(signUp.getHeaderUserName().contains(input.get("Name")));

        // Final assertion
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 2, dataProvider = "TestData")
    public void SignUpWithInvalidEmail(HashMap<String, String> input)
    {
            // Step 1: Verify Home URL
            soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

            // Step 2: Open Sign Up Page
            signUp.openLoginPage();

            // Step 3: Verify SignUp Title
            soft.assertEquals(signUp.getSignUpHeader(), "New User Signup!");

            // Step 4: Enter a valid name
            signUp.enterName(input.get("Name"));

            // Step 5: Enter invalid email
            signUp.enterEmail(input.get("InvalidEmail"));

            // Step 6: Click Sign Up button
            signUp.clickSignUpButton();

            // Step 7: Get validation message from browser
            String validationMessage = signUp.signupEmailInput.getAttribute("validationMessage");

            // Step 8: Verify validation message matches expected
            soft.assertEquals(validationMessage, "Please include an '@' in the email address. '" + input.get("InvalidEmail") + "' is missing an '@'.");

            // Final assertion
            soft.assertAll();
    }

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 3, dataProvider = "TestData")
    public void SignUpWithEmptyFields(HashMap<String, String> input)
    {
        // Step 1: Verify Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Open Sign Up Page
        signUp.openLoginPage();

        // Step 3: Verify SignUp Title
        soft.assertEquals(signUp.getSignUpHeader(), "New User Signup!");

        // Step 4: Ensure Name and Email fields are displayed
        soft.assertTrue(signUp.nameInput.isDisplayed());
        soft.assertTrue(signUp.signupEmailInput.isDisplayed());

        // Step 5: Click Sign Up button without filling anything
        signUp.clickSignUpButton();

        // Step 6: Get browser validation message for empty Name field
        String validationMessage = signUp.nameInput.getAttribute("validationMessage");

        // Step 7: Verify validation message
        soft.assertEquals(validationMessage, "Please fill out this field.");

        // Final assertion
        soft.assertAll();
    };

    @Test(groups = {"Sad Scenarios", "All Scenarios"}, priority = 4, dataProvider = "TestData")
    public void SignUpWithExistingEmail(HashMap<String, String> input)
    {
        // Step 1: Verify Home URL
        soft.assertEquals(driver.getCurrentUrl(), input.get("HomeUrl"));

        // Step 2: Open Sign Up Page
        signUp.openLoginPage();

        // Step 3: Verify SignUp Title
        soft.assertEquals(signUp.getSignUpHeader(), "New User Signup!");

        // Step 4: Enter a valid name
        signUp.enterName(input.get("Name"));

        // Step 5: Enter an existing email
        signUp.enterEmail(input.get("ValidEmail"));

        // Step 6: Click Sign Up button
        signUp.clickSignUpButton();

        // Step 7: Get system validation message
        String validationMessage = signUp.existingEmailValidation.getText();

        // Step 8: Verify validation message
        soft.assertEquals(validationMessage, "Email Address already exist!");

        // Final assertion
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
