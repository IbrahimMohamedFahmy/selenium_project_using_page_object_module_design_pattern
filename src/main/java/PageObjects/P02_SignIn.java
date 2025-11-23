package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class P02_SignIn
{
    // Create constructor to call factory page class to declare web driver
    public WebDriver driver;

    public P02_SignIn (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Data Members
    // Methods
}
