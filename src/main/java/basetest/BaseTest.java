package basetest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

/**
 * Class responsible to set up the suite for testing.
 *
 * Created by sridhar_s on 2/17/2019.
 */
public abstract class BaseTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getInstance().getDriver();
        driver.get("http://www.ericrochester.com/name-game/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getInstance().removeDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }
}



