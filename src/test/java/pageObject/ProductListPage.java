package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductListPage extends BasePage {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    //Locatprs
    @FindBy(xpath = "//div[@class='pricebar']/button")
    List<WebElement> btnAddToCarts;

    @FindBy(xpath = "//div[@class='pricebar']/button[text()='Remove']")
    List<WebElement> btnRemoves;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    WebElement cartIconButton;

    @FindBy(css = "span[class='shopping_cart_badge']")
    WebElement cartIcon;

    @FindBy(xpath = "//div[@class=\"inventory_item_price\"]")
    List<WebElement> productPriceLocators;

    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    WebElement hamburgerMenu;

    @FindBy(xpath = "//div[@class='inventory_item']")
    List<WebElement> productLocator;

    @FindBy(xpath = "//div[@data-test='inventory-item-name']")
    List<WebElement> productNameLocator;

    @FindBy(xpath = "//span[@class='title']")
    WebElement pageHeading;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement filterDropdown;

    Select sel_filter_dropdown;


    //Constructor
    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    //Action Methods
    public void clickHamburgerMenu() {
        hamburgerMenu.click();
    }

    public void clickCartButton() {
        cartIconButton.click();
    }

//    public void clickRemoveButton() {
//        btn_remove.click();
//    }

    public void sort(String sort) {
        sel_filter_dropdown = new Select(filterDropdown);
        switch (sort) {
            case "A_to_Z":
                sel_filter_dropdown.selectByVisibleText("Name (A to Z)");
                break;
            case "Z_to_A":
                sel_filter_dropdown.selectByVisibleText("Name (Z to A)");
                break;
            case "low_high":
                sel_filter_dropdown.selectByVisibleText("Price (low to high)");
                break;
            case "high_low":
                sel_filter_dropdown.selectByVisibleText("Price (high to low)");
                break;
            default:
                throw new NoSuchElementException("Invalid sort option: " + sort);
        }
    }

    public List<WebElement> getAllProducts() {
        return productLocator;
    }

    public List<WebElement> getAllProductNames() {
        return productNameLocator;
    }

    public List<WebElement> getAllProductsPrices() {
        return productPriceLocators;
    }

    public List<WebElement> getAllRemoveButtons() {
        return btnRemoves;
    }

    public List<WebElement> getListOfProductsByPrice(String price) {
        List<WebElement> matchedProducts = new ArrayList<>();

        for (WebElement product : productLocator) {
            String productPriceText = product.findElement(By.xpath(".//div[@class='inventory_item_price']")).getText();
            if (productPriceText.replace("$", "").equals(price)) {
                matchedProducts.add(product);
            }
        }
        return matchedProducts;
    }

    public List<WebElement> getListOfProductsByTitle(String title) {
        List<WebElement> matchedProducts = new ArrayList<>();

        for (WebElement product : productLocator) {
            String productTitleText = product.findElement(By.cssSelector(".inventory_item_name")).getText();
            if (productTitleText.equals(title)) {
                matchedProducts.add(product);
            }
        }
        return matchedProducts;
    }

    public int getNumberOfCartItems() {
        try {
            js.executeScript("arguments[0].scrollIntoView(true);", cartIcon);
//            wait.until(ExpectedConditions.visibilityOf(cartIcon));

            return Integer.parseInt(cartIcon.getText());
//            return cart_item if cart_item else 0;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAllProductsToCart() {
        if (!btnRemoves.isEmpty()) {
            emptyCart();
            for (WebElement btn : btnAddToCarts) {
                btn.click();
            }
        } else {
            for (WebElement btn : btnAddToCarts) {
                btn.click();
            }
        }
    }

    public List<WebElement> addToCartProductsByTitle(String title) {
        String idTitle = String.join("-", title.toLowerCase().split(" "));

        List<WebElement> cartButtons = driver.findElements(
                By.cssSelector("button[name='add-to-cart-" + idTitle + "']"));
        for (WebElement carBtn : cartButtons) {
            carBtn.click();
        }
        return cartButtons;
    }

    public List<WebElement> addToCartProductByPrice(Double price) {
        List<WebElement> cartButtons = driver.findElements(
                By.xpath("//div[@class='inventory_item_price'][text()='" + price + "']/../button"));
        for (WebElement cartBtn : cartButtons) {
            cartBtn.click();
        }
        return cartButtons;
    }

    public void emptyCart() {
        if (!btnRemoves.isEmpty()) {
            for (WebElement remove : btnRemoves) {
                remove.click();
            }
        }
    }
}
