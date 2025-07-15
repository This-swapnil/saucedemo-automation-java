package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProductDetailsPage extends BasePage {
    //locators
    @FindBy(xpath = "//div[@class='inventory_details_name large_size']")
    WebElement product_name_locator;
    @FindBy(xpath = "//div[@class='inventory_details_price']")
    WebElement product_price_locator;
    @FindBy(xpath = "//button[@id='add-to-cart']")
    WebElement add_to_cart_button;
    @FindBy(xpath = "//button[text()='Remove']")
    WebElement remove_button;
    @FindBy(xpath = "//button[@id='back-to-products']")
    WebElement back_to_plp_button;
    @FindBy(css = ".title")
    WebElement heading_locator;
    @FindBy(css = ".shopping_cart_link")
    WebElement cart_locator;
    @FindBy(xpath = "//div[@class='cart_list']/div[@class='cart_item']")
    WebElement cart_items;

    //contractor
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    //get methods

    public String getProductName() {
        return product_name_locator.getText();
    }

    public String getProductPrice() {
        return product_price_locator.getText();
    }

    //click methods
    public void clickBackToProduct() {
        back_to_plp_button.click();
    }

    public void clickAddToCart() {
        add_to_cart_button.click();
    }

    public void clickRemoveButton() {
        remove_button.click();
    }

    public void clickOnCartButton() {
        cart_locator.click();
    }

    //action method
    public List<Object> selectARandomProduct() {
        Random random = new Random();
        ProductListPage plp_page = new ProductListPage(this.driver);
        List<WebElement> all_products = plp_page.getAllProducts();
        if (all_products.isEmpty()) {
            return null;
        }
        // Pick random product
        WebElement product = all_products.get(new Random().nextInt(all_products.size()));

        // Extract name and price
        WebElement productNameElement = product.findElement(By.cssSelector(".inventory_item_name"));
        WebElement productPriceElement = product.findElement(By.cssSelector(".inventory_item_price"));

        String name = productNameElement.getText();
        String price = productPriceElement.getText();

        // Click the product name link
        productNameElement.click();
        return Arrays.asList(name, price, product);
    }

    public void addRandomProductToCart() {
        selectARandomProduct();
        clickAddToCart();
        clickOnCartButton();

        List<WebElement> itemsInCart = driver.findElements(
                By.xpath("//div[@class='cart_list']/div[@class='cart_item']"));
        if (itemsInCart.isEmpty()) {
            driver.navigate().back();
            clickAddToCart();
        }
    }

    public boolean productHeadingExists() {
        return heading_locator.isDisplayed();
    }
}
