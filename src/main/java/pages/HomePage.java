package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Helper class for executing test cases.
 *
 * Created by sridhar_s on 2/17/2019.
 */
public class HomePage extends BasePage {

    /**
     * Constructor
     * @param driver - WebDriver
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Validates the title of the app
     */
    public void validateNameGameTitleText() {
        String title = getNameGameTitle().getText();
        Assert.assertNotNull(title);
    }

    /**
     * Verifies streak counter value is incremented for correct answers
     */
    public void validateStreakCounterIncremented()  {
        String nameFromQuestion = getNameFromTheQuestion().getText();

        int initialStreakCounter = getStreakCount();

        System.out.println("initial Streak count " + initialStreakCounter);

        int afterStreakCount = getStreakValueForQuestions(nameFromQuestion);

        System.out.println("after  Streak count " + afterStreakCount);

        Assert.assertTrue(afterStreakCount > initialStreakCounter, "Streak value incremented after correct answer");
    }

    /**
     * Given the name, selects the correct image and returns the Streak counter value
     * @param nameFromQuestion - Name of the correct person for which the image is to be clicked
     * @return - int - streak counter
     */
    public int getStreakValueForQuestions(String nameFromQuestion)  {
        int streakCount = 0;
        List<WebElement> elements = getAllPicturesFromTheQuestion();
        for (WebElement element : elements) {
            element = waitForElement(element);

            String nameFromTheList = extractName(element.getText());

            if (nameFromQuestion.equals(nameFromTheList)) {
                element.click();
                pause();
                streakCount = getStreakCount();
                break;
            }
        }
        return streakCount;
    }


    /**
     * Validates multiple streak counter resets to zero after wrong answer
     */
    public void validateMultipleStreakResetsAfterIncorrectAnswers() {

        for (int i = 0; i < 5; i++) {
            String nameFromQuestion = getNameFromTheQuestion().getText();
            int streakCount = getStreakValueForQuestions(nameFromQuestion);
            System.out.println("Streak count for multiple correct answers " + streakCount);
            Assert.assertEquals(streakCount, i + 1);
        }

        String nameFromQuestion = getNameFromTheQuestion().getText();

        List<WebElement> elements = getAllPicturesFromTheQuestion();
        int streakCount = -1;
        for (WebElement element : elements) {
            element = waitForElement(element);
            String nameFromTheList = extractName(element.getText());
            if(!nameFromQuestion.equals(nameFromTheList)){
                streakCount = getStreakValueForQuestions(nameFromTheList);
                break;
            }
        }
        System.out.println("after wrong answer streak count value " + streakCount);
        Assert.assertTrue(streakCount == 0, "Multiple Streak value resets to Zero after wrong answer");
    }


    /**
     * Checks for counter[ attempt, correct and streak] incremented after 10 random selections*
     */
    public void validateRandomClickCounterIncrement() {
        int attempt = 0;
        int correct = 0;
        int streak = 0;

        for (int i = 0; i < 10; i++) {
            List<WebElement> elements = getAllPicturesFromTheQuestion();
            int randValue = new Random().nextInt(elements.size());

            WebElement randomPicture = elements.get(randValue);

            String nameFromQuestion = getNameFromTheQuestion().getText();
            String nameFromRandomSelection = extractName(randomPicture.getText());

            attempt++;
            if (nameFromQuestion.equals(nameFromRandomSelection)) {
                correct++;
                streak++;
            } else {
                streak = 0;
            }

            randomPicture.click();
            pause();

            // assert the  attempt counters
            int attemptCount = getAttemptsCount();
            Assert.assertEquals(attempt, attemptCount);

            // assert the  correct counters
            int correctCount = getCorrectCount();
            Assert.assertEquals(correct, correctCount);

            // assert the  streak counters
            int streakCount = getStreakCount();
            Assert.assertEquals(streak, streakCount);
            System.out.println("Actual attempt, correct and streak ---> [" + attempt + " , " + correct + " , " + streak + " ]" + " Expected attemptCount, correctCount, streakCount ---> [" + attemptCount + " , " + correctCount + " ," + streakCount + " ]");
        }
    }

    /**
     * Verifies the name and the images change after selecting the correct answer
     */
    public void verifyNameAndImages() {

        String nameFromQuestion = getNameFromTheQuestion().getText();
        List<WebElement> elements = getAllPicturesFromTheQuestion();
        List<String> actualImages = getAllImageListAndClickCorrectPerson(elements, nameFromQuestion);
        System.out.println("List of all images before correct answer " + actualImages);
        pause();

        String newNameFromQuestion = getNameFromTheQuestion().getText();
        List<WebElement> newElements = getAllPicturesFromTheQuestion();
        // List<String> expectedImages = getAllImageList(newElements);
        List<String> expectedImages = getAllImageLinkList(newElements);
        System.out.println("List of expected images after correct answer " + expectedImages);
        Assert.assertNotEquals(nameFromQuestion, newNameFromQuestion, "name in the question after correct answer is not same!!!!");

        for (String actualImage : actualImages) {
            for (String expectedImage : expectedImages) {
                Assert.assertNotEquals(actualImage, expectedImage, "images after correct answer are not the same!!");
            }
        }
    }

    /**
     * Returns list of image links. Given the name of the image, will attempt to call Click on the image.
     *
     * @param webElements - List of web elements
     * @param name - Name of the person for which we need to click.
     * @return - List<String></String>
     */
    public List<String> getAllImageListAndClickCorrectPerson(List<WebElement> webElements, String name) {
        List<String> imageList = getAllImageLinkList(webElements);
        for (WebElement element : webElements) {
            String individualNameFromList = extractName(element.getText());
            if (name != null && name.equals(individualNameFromList)) {
                element.click();
                break;
            }
        }
        return imageList;
    }

    /**
     *
     * Validates the frequency of wrongly selected person is more than correct selected person.
     *
     * Selects a wrong and right person for every iteration for 100 iterations and collects the number of wrong and right persons reappearing.
     *
     * In the end asserts to make sure the number of wrong selection have appeared more than the number of right selection.
     */
    public void validateWrongCorrectlySelectedPersonOccurance() {
        Set<String> wrongSelectionSet = new HashSet<String>();
        Set<String> rightSelectionSet = new HashSet<String>();

        int frequencyOfWrong = 0;

        int frequencyOfRight = 0;

        for (int i = 0; i < 100; i++) {
            WebElement elementToClick = null;
            String questionName = getNameFromTheQuestion().getText();
            List<WebElement> allElements = getAllPicturesFromTheQuestion();

            String wrongElement = null;
            do {
                int random = new Random().nextInt(allElements.size());
                WebElement element = allElements.get(random);
                String text = extractName(element.getText());
                if (!text.equals(questionName)) {
                    wrongElement = text;
                    element.click();
                }
            } while (wrongElement == null);


            for (WebElement element : allElements) {
                String text = extractName(element.getText());

                if (wrongSelectionSet.contains(text)) {
                    frequencyOfWrong++;
                }

                if (wrongElement.equals(text)) {
                    wrongSelectionSet.add(text);
                }

                if (rightSelectionSet.contains(text)) {
                    frequencyOfRight++;
                }

                if (questionName.equals(text)) {
                    rightSelectionSet.add(text);
                    elementToClick = element;
                }
            }

            elementToClick.click();
            pause();
        }

        System.out.println("frequency of wrongly selected people " + frequencyOfWrong + " frequency of correctly selected people " + frequencyOfRight);

        Assert.assertTrue(frequencyOfWrong > frequencyOfRight, "frequency of wrong person is more the correctly answered person");
    }

    /**
     * Validates the attempt counter;
     */
    public void validateClickingFirstPhotoIncreasesTriesCounter() {
        int initialAttemptCount =  getAttemptsCount();
        pause();
        getAllPicturesFromTheQuestion().get(0).click();
        pause();
        int afterAttemptCount =  getAttemptsCount();
        System.out.println("Initial attempt count " + initialAttemptCount + " after attempt count " + afterAttemptCount);
        Assert.assertTrue(afterAttemptCount > initialAttemptCount);
    }

    /**
     * Helper method to wait for the DOM to render.
     */
    private void pause() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Assert.fail("Error when trying to pause");
        }
    }
}
