package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class StoreCartFactory {
    WebDriver driver;

    @FindBy(className = "product-thumbnail")
    public List<WebElement> Products;

    @FindBy(xpath = "(//span[text()='Add to cart'])")
    public List<WebElement> ProductsButton;

    @FindBy(xpath = "(//label[@title='Add to compare'])")
    public List<WebElement> ProductsCompare;

    @FindBy(xpath="(//a[@data-target='https://demostore.x-cart.com/?target=compare'])[3]")
    public WebElement openComparisonTable;

    @FindBy(className = "ui-dialog-titlebar-close")
    public WebElement closePopUp;

    @FindBy(xpath="//div[contains(@class,'lc-minicart lc-minicart-horizontal')]")
    public WebElement itemsLastA;

    @FindBy(xpath="(//span[text()='View cart'])[1]")
    public WebElement viewCart;

    @FindBy(xpath ="(//input[@name='substring'])[1]")
    public WebElement searchItems;

    @FindBy(xpath ="(//button[contains(@class,'btn ')])[3]")
    public WebElement search;

    @FindBy(className="currency-indicator")
    public WebElement currency;

    @FindBy(id="currency_code_1")
    public WebElement currency_code;

    @FindBy(xpath="(//button[contains(@class,'btn ')])[4]")
    public WebElement save;


    public StoreCartFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
