package basetest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Singleton class to initialize the Webdriver.
 *
 * Created by sridhar_s on 2/17/2019.
 */
public class DriverFactory {
    /**
     * Creates the instance of DriverFactory
     */
    private static DriverFactory instance = new DriverFactory();

    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
    {

        protected WebDriver initialValue() {
            if (System.getProperty("operatingSystem").equals("Windows")) {

                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

            } else if (System.getProperty("operatingSystem").equals("Mac")) {

                System.setProperty("webdriver.chrome.driver", "chromedriver");

            } else {
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            }
            return new ChromeDriver();
        }
    };

    private DriverFactory() {
        //Do-nothing..Do not allow to initialize this class from outside
    }

    /**
     * Returns the instance of this class
     * @return DriverFactory
     */
    public static DriverFactory getInstance() {
        return instance;
    }

    /**
     * Returns the Webdriver
     * @return - Webdriver
     */
    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        return driver.get();
    }

    /**
     * Removes the driver - Quits the driver and closes the browser
     */
    public void removeDriver() // Quits the driver and closes the browser
    {
        driver.get().quit();
        driver.remove();
    }
}

