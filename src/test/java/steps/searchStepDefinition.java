package steps;
import java.time.Duration;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class searchStepDefinition {

    public static WebDriver driver;

    @RunWith(Cucumber.class)
    @CucumberOptions(
            features = "src/test/resources/features", // Path to feature files
            glue = {"steps"},  // Path to step definitions
            plugin = {"pretty",
                    "html:target/cucumber-reports.html",
                    "json:target/cucumber-reports.json"}, // Report formats
            monochrome = true // Better console output
    )
    public class TestRunner {
    }

    @Given("I open the browser and navigate to {string}")
    public void i_open_the_browser_and_navigate_to(String url) throws InterruptedException {
        // Set up FirefoxOptions
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-infos");

        // Set the path for geckodriver
        System.setProperty("webdriver.gecko.driver", "/opt/homebrew/bin/geckodriver");
        driver = new FirefoxDriver(options);
        driver.get(url);  // Navigate to the provided URL
    }

    @When("I enter {string} in the search bar")
    public void when_i_enter_in_the_search_bar(String searchTerm) {
        // Locate the search bar and enter the search term
        By searchBar = By.id("sb_form_q");

        // Highlight the search bar by adding a red border around it
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", driver.findElement(searchBar));
        // Enter the search term
        driver.findElement(searchBar).sendKeys(searchTerm);


        // Simulate a gentle tap (click) on the search bar
        WebElement searchInput = driver.findElement(searchBar);
        js.executeScript("arguments[0].click();", searchInput);
        // Optionally, simulate pressing Enter after tapping
        searchInput.click();
        searchInput.sendKeys(Keys.RETURN); // Simulate pressing Enter


    }


    @Then("I should see search results related to {string}")
    public void iShouldSeeSearchResultsRelatedTo(String searchTerm) {
        // Assert that search results contain the search term
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("b_results"))); // Use the correct ID for Bing results

        String pageSource = driver.getPageSource();
        assertTrue("Search results do not contain the expected term.", pageSource.contains(searchTerm));

    }


    // Close the browser after each scenario
    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
