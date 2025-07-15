package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutFormPage extends BasePage {

    @FindBy(css = ".title")
    WebElement title_text;
    @FindBy(id = "first-name")
    WebElement first_name;
    @FindBy(id = "last-name")
    WebElement last_name;
    @FindBy(id = "postal-code")
    WebElement post_code;
    @FindBy(id = "cancel")
    WebElement cancel_button;

    @FindBy(id = "continue")
    WebElement continue_button;

    @FindBy(css = ".error-message-container h3")
    WebElement error_message;

    public CheckoutFormPage(WebDriver driver) {
        super(driver);
    }


    //Get Methods
    public String getInformationPageHeading() {
        return title_text.getText();
    }

    public String getFirstName() {
        return first_name.getAttribute("value");
    }

    public String getLastName() {
        return last_name.getAttribute("value");
    }

    public String getPostCode() {
        return post_code.getAttribute("value");
    }

    public String getErrorMsg() {
        return error_message.getText();
    }

    public CheckOutPage getContinueButton() {
        continue_button.click();
        return new CheckOutPage(driver);
    }

    public void clickCancelButton() {
        if (cancel_button.isDisplayed()) {
            cancel_button.click();
        } else {
            System.out.println("Cancel button on information page is not available");
        }
    }

    public void clickOnContinueButton() {
        continue_button.click();
    }

    //Enter data
    public void enterFirstName(String fname) {
        first_name.sendKeys(fname);
    }

    public void enterLastName(String lname) {
        last_name.sendKeys(lname);
    }

    public void enterPostalCode(String pcode) {
        post_code.sendKeys(pcode);
    }

    public boolean errorExits() {
        return !getErrorMsg().isEmpty();
    }

    public boolean informationPageHeadingExists() {
        String heading_text = title_text.getText();
        return heading_text.contains("Information");
    }

    public CheckOutPage navigateCheckoutOverviewPage(ProductListPage plp, CartPage cart_page) {
        plp.clickCartButton();
        cart_page.clickCheckoutButton();
        clickOnContinueButton();
        enterFirstName("John");
        enterLastName("wick");
        enterPostalCode("2154");
        clickOnContinueButton();
        return new CheckOutPage(driver);
    }
}
