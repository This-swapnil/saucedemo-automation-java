package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.*;
import testBase.BaseCase;

public class TC007_CompleteCheckoutPage extends BaseCase {
    CartPage cartPage;
    ProductDetailsPage pdp;
    ProductListPage plp;
    CheckoutFormPage checkoutFormPage;
    CheckOutPage checkOutPage;
    CheckoutCompletePage checkoutCompletePage;

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

    //@Test(priority = 2)
    public void testBackButton() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);
        checkOutPage = new CheckOutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        logger.info("Complete the order by adding all the products");
        plp.addAllProductsToCart();
        plp.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutFormPage.enterFirstName("John");
        checkoutFormPage.enterLastName("Wick");
        checkoutFormPage.enterPostalCode("2121");
        checkoutFormPage.clickOnContinueButton();

        checkOutPage.clickFinishButton();
        logger.info("Order is finished and success message is displayed");
        logger.info("Now click on back button");
        checkoutCompletePage.clickOnBackButton();
        logger.info("Verify user is navigated to inventory page");
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
            logger.info("User navigated to inventory page successfully");
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            logger.error("user is not redirected to home/inventory page");
            Assert.fail("user is not redirected to home/inventory page");
        }
    }

    //    @Test(priority = 3)
    public void testSuccessMessage() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);
        checkOutPage = new CheckOutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        logger.info("Verify success message on  checkout complete page.");
        logger.info("Complete the order by adding all the products");
        plp.addAllProductsToCart();
        plp.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutFormPage.enterFirstName("John");
        checkoutFormPage.enterLastName("Wick");
        checkoutFormPage.enterPostalCode("2121");
        logger.info("Complete the order by adding the products");
        checkoutFormPage.clickOnContinueButton();

        checkOutPage.clickFinishButton();
        String successMessage = checkoutCompletePage.getSuccessMessage();
        try {
            Assert.assertTrue(successMessage.contains("Thank you"));
            logger.info("Thank you message is displayed");
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            logger.error("Thank you message is not displayed");
            Assert.fail("Thank you message is not displayed");
        }
    }

    @Test(priority = 4)
    public void testTitle() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);
        checkOutPage = new CheckOutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        logger.info("Verify success message on  checkout complete page.");
        logger.info("Complete the order by adding all the products");
        plp.addAllProductsToCart();
        plp.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutFormPage.enterFirstName("John");
        checkoutFormPage.enterLastName("Wick");
        checkoutFormPage.enterPostalCode("2121");
        logger.info("Complete the order by adding the products");
        checkoutFormPage.clickOnContinueButton();
        checkOutPage.clickFinishButton();

        String pageTitle = checkoutCompletePage.getTitle();
        try {
            logger.info("Check Complete! is displayed on checkout complete page");
            Assert.assertTrue(pageTitle.contains("Complete!"));
            logger.info("Complete! is displayed");
        } catch (AssertionError e) {
            logger.error(e.getMessage());
            logger.error("Complete! is not displayed as title on checkout complete page");
            Assert.fail("Complete! is not displayed as title on checkout complete page");
        }
    }
}
