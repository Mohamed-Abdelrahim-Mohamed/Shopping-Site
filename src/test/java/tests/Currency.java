package tests;

import helper.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pagefactory.StoreCartFactory;

import java.io.IOException;
import java.time.Duration;

public class Currency {
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
        log = new Logger(Currency.class.getSimpleName(),driver);
        log.prepareTest(Currency.class.getSimpleName());
        wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.valueOf(timeout)));
        actions = new Actions(driver);
        Cart = new StoreCartFactory(driver);
    }

    @Test
    @Parameters({"Value"})
    public void Currency(String Value) throws InterruptedException{
        try {
            log.step("Load " + URL + " into the browser");
            driver.get(URL);

            log.step("Open the Currency Pop Up");
            wait.until(ExpectedConditions.visibilityOf(Cart.currency)).click();

            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

            log.step("Change currency to "+Value);
            Select currencies = new Select(Cart.currency_code);
            currencies.selectByValue(Value);

            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

            log.step("Save");
            wait.until(ExpectedConditions.visibilityOf(Cart.save)).click();

            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

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
        log.logResults(testResult, Currency.class.getSimpleName(),exception,assertionError);
        driver.quit();
    }
}
