package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {
    @FindBy(css = ".title")
    WebElement titleText;
    @FindBy(css = ".complete-header")
    WebElement message;
    @FindBy(css = "button[name='back-to-products']")
    WebElement backButton;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return titleText.getText();
    }

    public String getSuccessMessage() {
        return message.getText();
    }

    public void clickOnBackButton() {
        backButton.click();
    }
}
