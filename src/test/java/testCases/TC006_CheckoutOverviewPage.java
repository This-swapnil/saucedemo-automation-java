package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.*;
import testBase.BaseCase;

import java.io.IOException;

public class TC006_CheckoutOverviewPage extends BaseCase {

    CartPage cartPage;
    ProductDetailsPage pdp;
    ProductListPage plp;
    CheckoutFormPage checkoutFormPage;

    @Test(priority = 1)
    public void userLogin() {
        try {
            logger.info("Step1: Perform complete Login");
            performCompleteLogin();
            logger.info("==== User login Successful ====");
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail("==== User Login failed ====");
        }
    }


    @Test(priority = 2)
    public void testCheckoutAllItems() throws IOException {
        plp = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);

        logger.info("Verify products, item total, total on checkout overview page and \n" +
                            "Thank you message after submitting the order for purchasing all the products.");
        logger.info("Step2: Add all the products to cart");
        plp.addAllProductsToCart();

        logger.info("Step3: Navigated to checkout overview page");
        CheckOutPage checkoutOverviewPage = checkoutFormPage.navigateCheckoutOverviewPage(plp, cartPage);
        logger.info("Capture receipt");
        checkoutOverviewPage.captureReceipt("screenshots/receipt_all_items.png");
        logger.info("Verify item total, total and products on checkout overview page");
        try {
            Assert.assertEquals(plp.getNumberOfCartItems(), checkoutOverviewPage.getQuantities());
            logger.info("The number of products on cart page and on checkout overview page are same");
            Assert.assertEquals(checkoutOverviewPage.calculateItemTotal(), checkoutOverviewPage.getItemTotal());
            logger.info("The item total is calculated correctly");
            Assert.assertEquals(checkoutOverviewPage.getTotal(), checkoutOverviewPage.calculateTotal());
            logger.info("The Total amount is also calculated correctly");
            logger.info("Click on Finish button");
            checkoutOverviewPage.clickFinishButton();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("There is unexpected item total, total or products mentioned on checkout overview page");
            Assert.fail("There is unexpected item total, total or products mentioned on checkout overview page");
        }
        try {
            Assert.assertTrue(checkoutOverviewPage.getThankyouMessage().contains("Thank you"));
            logger.info("Thank you message is displayed");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Thank you message is not displayed");
            Assert.fail("Thank you message is not displayed");
        }
    }

    @Test(priority = 3)
    public void testCheckoutOneItem() throws IOException {
        plp = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);

        logger.info("Verify products, item total, total on checkout overview page and " +
                            "Thank you message after submitting the order for purchasing a single products.");
        Double price = 15.99;
        logger.info("Add the product to cart with price: {}", price);
        plp.addToCartProductByPrice(price);
        logger.info("Step3: Navigated to checkout overview page");
        CheckOutPage checkoutOverviewPage = checkoutFormPage.navigateCheckoutOverviewPage(plp, cartPage);
        scrollToBottom();
        logger.info("Capture the receipt");
        checkoutOverviewPage.captureReceipt("screenshots/receipt_single_item.png");

        try {
            Assert.assertEquals(plp.getNumberOfCartItems(), checkoutOverviewPage.getQuantities());
            logger.info("The number of products on cart page and on checkout overview page are same");
            Assert.assertEquals(checkoutOverviewPage.calculateItemTotal(), checkoutOverviewPage.getItemTotal());
            logger.info("The item total is calculated correctly");
            Assert.assertEquals(checkoutOverviewPage.getTotal(), checkoutOverviewPage.calculateTotal());
            logger.info("The Total amount is also calculated correctly");
            logger.info("Click on Finish button");
            checkoutOverviewPage.clickFinishButton();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("There is unexpected item total, total or products mentioned on checkout overview page");
            Assert.fail("There is unexpected item total, total or products mentioned on checkout overview page");
        }
        try {
            Assert.assertTrue(checkoutOverviewPage.getThankyouMessage().contains("Thank you"));
            logger.info("Thank you message is displayed");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Thank you message is not displayed");
            Assert.fail("Thank you message is not displayed");
        }
    }

    @Test(priority = 4)
    public void testCancelButton() {
        plp = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);

        logger.info("Verify cancel button on checkout overview page.");
        Double price = 15.99;
        logger.info("Add the product to cart with price: {}", price);
        plp.addToCartProductByPrice(price);
        logger.info("Step3: Navigated to checkout overview page");
        CheckOutPage checkoutOverviewPage = checkoutFormPage.navigateCheckoutOverviewPage(plp, cartPage);
        logger.info("Click on cancel button");
        checkoutOverviewPage.clickCancelButton();
        logger.info("Verify user is navigated back to the home page with 'Products' heading");

        try {
            Assert.assertTrue(plp.getPageHeading().contains("Products"));
            logger.info(" user is on home page and Products heading is displayed");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("User is not on home page or Products title is not displayed");
            Assert.fail("User is not on home page or Products title is not displayed");
        }
    }

    @Test(priority = 5)
    public void testCheckoutWithZeroTotal() {
        plp = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);
        pdp = new ProductDetailsPage(driver);

        logger.info("Verify user is not able to proceed if cart is empty.");
        logger.info("Step 2: Add a product to cart");

        pdp.addRandomProductToCart();
        pdp.clickRemoveButton();
        pdp.clickOnCartButton();
        cartPage.clickCheckoutButton();
        CheckOutPage checkoutOverviewPage = checkoutFormPage.navigateCheckoutOverviewPage(plp, cartPage);

        try {
            Assert.assertEquals(checkoutOverviewPage.getTotal(), 0.0);
            checkoutOverviewPage.clickFinishButton();
            Assert.assertTrue(checkoutOverviewPage.checkoutOverviewHeadingText());
            logger.info("User is unable to proceed with having order total as 0");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("User is able to proceed with finish order when total is 0");
            Assert.fail("User is able to proceed with finish order when total is 0");
        }
    }
}
