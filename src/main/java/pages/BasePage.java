package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to provide methods to access the dom.
 *
 * Created by sridhar_s on 2/17/2019.
 */
public class BasePage {

    /**
     * WebDriver
     */
    private WebDriver driver;


    /**
     * Constructor
     * @param driver - WebDriver
     * @throws Exception
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns the title of the app.
     * @return - WebElement
     */
    public WebElement getNameGameTitle() {
        return driver.findElement(By.xpath("//h1[@class = 'text-muted']"));
    }

    /**
     * Returns the count for number of tries.
     * @return - int
     */
    public int getAttemptsCount() {
        WebElement element = driver.findElement(By.className("attempts"));
        element = waitForElement(element);
        return Integer.parseInt(element.getText());
    }


    /**
     * Returns a list of webelements with class 'photo'
     * @return - List<WebElements>
     */
    public List<WebElement> getAllPicturesFromTheQuestion() {
        return driver.findElements(By.xpath("//div[@class = 'photo']"));
    }

    /**
     * Returns the list of image source for all the images.
     * @param elements - list of elements
     * @return - List<String>
     */
    public List<String> getAllImageLinkList(List<WebElement> elements) {
        List<String> imageLinkList = new ArrayList<String>();
        for (WebElement ele : elements) {
            String image = ele.findElement(By.tagName("img")).getAttribute("src");
            imageLinkList.add(image);
        }
        return imageLinkList;
    }


    /**
     * Helper method to return the webelement from DOM once its rendered..
     * @param element - WebElement to get
     * @return - Webelement
     */
    public WebElement waitForElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 180);
        return wait.until(
                ExpectedConditions.elementToBeClickable(element));
    }


    /**
     * Returns the correct counter count
     * @return - int
     */
    public int getCorrectCount() {
        WebElement element = driver.findElement(By.className("correct"));
        element = waitForElement(element);
        return Integer.parseInt(element.getText());
    }

    /**
     * Returns the Streak counter count
     * @return - int
     */
    public int getStreakCount() {
        WebElement element = driver.findElement(By.className("streak"));
        element = waitForElement(element);
        return Integer.parseInt(element.getText());

    }

    /**
     * Returns the name in the question asked.
     * @return - String
     */
    public WebElement getNameFromTheQuestion() {
        WebElement element = driver.findElement(By.xpath("//span[@id='name']"));
        element = waitForElement(element);
        return element;
    }

    /**
     * Helper method to extract the name using regular expression
     * @param nameFromList - name
     * @return - string
     */
    public String extractName(String nameFromList) {
        String[] name = nameFromList.split("\\r?\\n");
        return name[1];
    }
}





