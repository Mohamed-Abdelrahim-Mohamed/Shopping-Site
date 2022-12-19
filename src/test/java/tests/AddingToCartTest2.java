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

public class AddingToCartTest2 {
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
        log = new Logger(AddingToCartTest2.class.getSimpleName(),driver);
        log.prepareTest(AddingToCartTest2.class.getSimpleName());
        wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.valueOf(timeout)));
        actions = new Actions(driver);
        Cart = new StoreCartFactory(driver);
    }

    @Test
    public void AddingToCartTest2() throws InterruptedException{
        try {
            log.step("Load " + URL + " into the browser");
            driver.get(URL);

            for(int i=0;i<2;i++)
            {
                log.step("Moving to Product "+(i+1));
                actions.moveToElement(Cart.Products.get(i)).build().perform();

                log.step("Adding Product "+(i+1)+" to Cart");
                wait.until(ExpectedConditions.visibilityOf(Cart.ProductsButton.get(i))).click();
            }
            log.warning("Wait for 5 seconds");
            Thread.sleep(5000);

            log.step("Closing Pop up message");
            wait.until(ExpectedConditions.visibilityOf(Cart.closePopUp)).click();

            log.warning("Wait for 5 seconds");
            Thread.sleep(5000);

            log.step("Opening Cart Pop Up");
            wait.until(ExpectedConditions.visibilityOf(Cart.itemsLastA)).click();

            log.step("Opening Cart Page");
            wait.until(ExpectedConditions.visibilityOf(Cart.viewCart)).click();

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
        log.logResults(testResult, AddingToCartTest2.class.getSimpleName(),exception,assertionError);
        driver.quit();
    }
}
