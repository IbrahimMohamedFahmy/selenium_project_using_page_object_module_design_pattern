package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class P05_SignOut
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P05_SignOut (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Data Members
    @FindBy(css = "a[href='/logout']")
    public static WebElement signOutLink;

    // Methods
    public void SignOutClick()
    {
         signOutLink.click();
    }

}
