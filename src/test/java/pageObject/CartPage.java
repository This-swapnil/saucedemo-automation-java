package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    //locators
    @FindBy(css = ".title")
    WebElement heading_locator;

    @FindBy(id = "checkout")
    WebElement checkout_button;

    @FindBy(xpath = "//button[@id='continue-shopping']")
    WebElement continue_shopping_button;

    @FindBy(css = ".title")
    WebElement checkout_page_heading;

    @FindBy(css = ".cart_item")
    List<WebElement> cart_items;

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement remove_button;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    //action method
    public boolean cartHeadingExists() {
        String cart_heading = heading_locator.getText();
        return cart_heading.contains("Cart");
    }

    public void clickCheckoutButton() {
        checkout_button.click();
    }

    public void clickContinueShoppingButton() {
        continue_shopping_button.click();
    }

    public boolean checkoutPageHeadingExist() {
        return checkout_page_heading.isDisplayed();
    }

    public List<WebElement> getCartItems() {
        return cart_items;
    }

    public void removeCartItem(WebElement item) {
        item.findElement(By.xpath("//button[text()='Remove']")).click();
    }
}
