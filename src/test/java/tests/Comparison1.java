package tests;

import helper.Logger;
import org.openqa.selenium.JavascriptExecutor;
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

public class Comparison1 {
    WebDriver driver;
    Logger log;
    Exception exception;
    AssertionError assertionError;
    StoreCartFactory Cart;
    String URL;
    WebDriverWait wait;
    static Actions actions;

    JavascriptExecutor js;
    @BeforeTest
    @Parameters({"timeout","URL"})
    public void setUp(String timeout, String URL) throws IOException {
        this.URL = URL;
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        log = new Logger(Comparison1.class.getSimpleName(),driver);
        log.prepareTest(Comparison1.class.getSimpleName());
        wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.valueOf(timeout)));
        actions = new Actions(driver);
        Cart = new StoreCartFactory(driver);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void AddingToCartTest1() throws InterruptedException{
        try {
            log.step("Load " + URL + " into the browser");
            js.executeScript("window.location = '"+URL+"'");

            for(int i=0;i<2;i++)
            {
                log.step("Moving to Product "+(i+1));
                actions.moveToElement(Cart.Products.get(i)).build().perform();

                log.step("Adding Product "+(i+1)+" to Comparison Table");
                wait.until(ExpectedConditions.visibilityOf(Cart.ProductsCompare.get(i))).click();
            }
            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

            js.executeScript("window.scrollTo(0,0)");

            log.warning("Wait for 2 seconds");
            Thread.sleep(2000);

            log.step("Open Comparison Table");
            wait.until(ExpectedConditions.visibilityOf(Cart.openComparisonTable)).click();


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
        log.logResults(testResult, Comparison1.class.getSimpleName(),exception,assertionError);
        driver.quit();
    }
}
