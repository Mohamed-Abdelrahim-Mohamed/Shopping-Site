package tests;

import helper.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pagefactory.StoreCartFactory;

import java.io.IOException;
import java.time.Duration;

public class SearchBar1 {
    WebDriver driver;
    Logger log;
    Exception exception;
    AssertionError assertionError;
    StoreCartFactory Cart;
    String URL;
    WebDriverWait wait;
    static Actions actions;
    @BeforeTest
    @Parameters({"timeout","URL"})
    public void setUp(String timeout, String URL) throws IOException {
        this.URL = URL;
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        log = new Logger(SearchBar1.class.getSimpleName(),driver);
        log.prepareTest(SearchBar1.class.getSimpleName());
        wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.valueOf(timeout)));
        actions = new Actions(driver);
        Cart = new StoreCartFactory(driver);
    }

    @Test
    public void SearchBar1() throws InterruptedException{
        try {
            log.step("Load " + URL + " into the browser");
            driver.get(URL);

            log.step("Typing in the search bar 'Mobile'");
            wait.until(ExpectedConditions.visibilityOf(Cart.searchItems)).sendKeys("Mobile");

            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

            log.step("Click on the search button");
            wait.until(ExpectedConditions.visibilityOf(Cart.search)).click();

        }catch(Exception exception){
            this.exception = exception;
            throw exception;
        } catch(AssertionError assertionError){
            this.assertionError = assertionError;
            throw assertionError;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) throws Exception {
        log.logResults(testResult, SearchBar1.class.getSimpleName(),exception,assertionError);
        driver.quit();
    }
}
