package test;

import basetest.BaseTest;
import basetest.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;

/**
 * Tests for Name Game app
 *
 * Created by sridhar_s on 2/17/2019.
 */
public class TestNameGame extends BaseTest {

    /**
     * Verifies name game title is shown
     */
    @Test
    public void test_validate_title_is_present() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateNameGameTitleText();
    }

    /**
     * Validates attempt counter
     */
    @Test
    public void test_clicking_photo_increases_tries_counter() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateClickingFirstPhotoIncreasesTriesCounter();
    }

    /**
     * Verify the "streak" counter is incrementing on correct selections.
     */
    @Test
    public void test_StreakCounter_Increment_On_CorrectAnswer() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateStreakCounterIncremented();
    }

    /**
     * Verify the a multiple “streak” counter resets after getting an incorrect answer.
     */
    @Test
    public void test_Multiple_StreakCounter_Resets_After_Wrong_Answer() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateMultipleStreakResetsAfterIncorrectAnswers();
    }

    /**
     * Verify that after 10 random selections the correct counters are being incremented for
     * tries and correct counters.
     */
    @Test
    public void test_10Random_Selections_Increments_Correct_TriesCounter() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateRandomClickCounterIncrement();
    }


    /**
     * Verify name and displayed photos change after selecting the correct answer.
     */
    @Test
    public void test_Name_Images_Change_After_CorrectAnswer() {
        HomePage homePage = new HomePage(getDriver());
        homePage.verifyNameAndImages();
    }

    /**
     * Bonus​ - Write a test to verify that failing to select one person’s name correctly makes
     * that person appear more frequently than other “correctly selected” people.
     */
    @Test
    public void test_Bonus_Frequency_Of_WronglySelectedPeople_More_Than_Correctly_SelectedPeople() {
        HomePage homePage = new HomePage(getDriver());
        homePage.validateWrongCorrectlySelectedPersonOccurance();
    }
}
