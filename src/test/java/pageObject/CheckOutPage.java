package pageObject;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckOutPage extends BasePage {
    //Find the webelements
    @FindBy(css = "div[class='summary_subtotal_label']")
    WebElement itemTotal;

    @FindBy(css = "div[class='summary_tax_label']")
    WebElement tax;

    @FindBy(css = ".summary_total_label")
    WebElement total;

    @FindBy(css = ".inventory_item_price")
    List<WebElement> itemPriceLocator;

    @FindBy(css = ".cart_quantity")
    List<WebElement> quantityLocator;

    @FindBy(id = "cancel")
    WebElement cancelButton;

    @FindBy(id = "finish")
    WebElement finishButton;

    @FindBy(css = ".complete-header")
    WebElement thankyouMessage;

    @FindBy(css = ".summary_info")
    WebElement receiptSection;

    @FindBy(className = "title")
    WebElement titleHeading;

    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    //get methods

    public double getItemTotal() {
        String totalText = itemTotal.getText();
        return Double.parseDouble(totalText.split("\\$")[1]);
    }

    public double getTax() {
        String taxText = tax.getText();
        return Double.parseDouble(taxText.split("\\$")[1]);
    }

    public double getTotal() {
        String textTotal = total.getText();
        return Double.parseDouble(textTotal.split("\\$")[1]);
    }

    public String getThankyouMessage() {
        return thankyouMessage.getText();
    }

    public List<Double> getPriceOfAllItems() {
        List<Double> finalPrices = new ArrayList<>();
        for (WebElement price : itemPriceLocator) {
            finalPrices.add(Double.parseDouble(price.getText().substring(1)));
        }
        return finalPrices;
    }

    public int getQuantities() {
        int countOfProducts = 0;
        for (WebElement qnt : quantityLocator) {
            countOfProducts += Integer.parseInt(qnt.getText());
        }
        return countOfProducts;
    }

    //click methods
    public void clickCancelButton() {
        cancelButton.click();
    }

    public CheckoutCompletePage clickFinishButton() {
        finishButton.click();
        CheckoutCompletePage chekoutCompletePage = new CheckoutCompletePage(driver);
        return chekoutCompletePage;
    }

    //Complete Action methods
    public boolean checkoutOverviewHeadingText() {
        String title = titleHeading.getText();
        return title.contains("Overview");
    }

    public Double calculateItemTotal() {
        List<Double> prices = getPriceOfAllItems();
        Double itemTotal = 0.0;
        for (Double price : prices) {
            itemTotal += price;
        }
        return itemTotal;
    }

    public double calculateTotal() {
        Double itemTotal = calculateItemTotal();
        Double tax = getTax();
        return itemTotal + tax;
    }

    public void captureReceipt(String name) throws IOException {
        File screenshot = receiptSection.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(name));
    }

    public CheckoutCompletePage navigateToCheckoutCompletePage(ProductListPage plp, CartPage crtPage,
                                                               CheckoutFormPage cfp, CheckOutPage chkPage) {
        plp.clickCartButton();
        crtPage.clickCheckoutButton();
        cfp.clickOnContinueButton();
        cfp.enterFirstName("John");
        cfp.enterLastName("Wick");
        cfp.enterPostalCode("12345");
        cfp.clickOnContinueButton();
        CheckoutCompletePage checkoutCompletePage = chkPage.clickFinishButton();
        return checkoutCompletePage;
    }
}
