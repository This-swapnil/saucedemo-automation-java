package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.CartPage;
import pageObject.CheckoutFormPage;
import pageObject.ProductListPage;
import testBase.BaseCase;

public class TC004_CheckoutInformationPage extends BaseCase {
    /*
    Scenarios:
    1. Submit empty form and check error exist
    2. Enter first name only and submit form and check error exist
    3. Enter last name only and submit form and check error exist
    4. Enter post code only and submit form and check error exist
    5. Fill the complete form and submit
     */

    CheckoutFormPage checkoutFormPage;
    ProductListPage productListPage;
    CartPage cartPage;


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
    public void testInformationForm() {
        logger.info("Verify checkout information form fields are displaying error message for empty data");
        checkoutFormPage = new CheckoutFormPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);

        logger.info("Step2: Add all the products to cart");
        productListPage.addAllProductsToCart();

        logger.info("Step3: Navigated to cart page");
        productListPage.clickCartButton();

        logger.info("Step4: Click on checkout button");
        cartPage.clickCheckoutButton();

        logger.info("Step5: Click on continue button without filling any details");
        checkoutFormPage.clickOnContinueButton();
        try {
            Assert.assertTrue(checkoutFormPage.errorExits());
            logger.info("Error message is displayed");
            String error = checkoutFormPage.getErrorMsg();
            logger.info("Verify error is displayed for first name");
            Assert.assertTrue(error.contains("First Name"));
            logger.info("Error is displayed for first name: {}", error);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error message is not displayed for first name or no message is displayed at all");
            Assert.fail("Error message is not displayed for first name or no message is displayed at all");
        }

        //Enter only first name
        logger.info("Now enter only first name");
        checkoutFormPage.enterFirstName("John");
        checkoutFormPage.clickOnContinueButton();
        try {
            Assert.assertTrue(checkoutFormPage.errorExits());
            logger.info("Error message is displayed");
            String error = checkoutFormPage.getErrorMsg();
            logger.info("Verify error is displayed for last name");
            Assert.assertTrue(error.contains("Last Name"));
            logger.info("Error is displayed for last name: {}", error);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error message is not displayed for last name or no message is displayed at all");
            Assert.fail("Error message is not displayed for last name or no message is displayed at all");
        }

        //Enter last name
        logger.info("Now enter last name");
        checkoutFormPage.enterLastName("Wick");
        checkoutFormPage.clickOnContinueButton();
        try {
            Assert.assertTrue(checkoutFormPage.errorExits());
            logger.info("Error message is displayed");
            String error = checkoutFormPage.getErrorMsg();
            logger.info("Verify error is displayed for post code");
            Assert.assertTrue(error.contains("Postal Code"));
            logger.info("Error is displayed for Postal Code: {}", error);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error message is not displayed for Postal Code or no message is displayed at all");
            Assert.fail("Error message is not displayed for Postal Code or no message is displayed at all");
        }

        //Enter all the details
        logger.info("Now enter all the details and click on submit");
        checkoutFormPage.enterPostalCode("212121");
        checkoutFormPage.clickOnContinueButton();
        logger.info("Verify user is on checkout overview page");

        try {
            Assert.assertFalse(checkoutFormPage.getInformationPageHeading().contains("Checkout: Your Information"));
            logger.info("User is successfully navigated to checkout overview page");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("User is not navigated to checkout overview page");
            Assert.fail("User is not navigated to checkout overview page");
        }
    }

    @Test(priority = 3)
    public void testCancel() {
        // It should redirect to cart page
        logger.info("Verify checkout information form fields are displaying error message for empty data");
        checkoutFormPage = new CheckoutFormPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);

        logger.info("Step2: Add all the products to cart");
        productListPage.addAllProductsToCart();

        logger.info("Step3: Navigated to cart page");
        productListPage.clickCartButton();

        logger.info("Step4: Click on checkout button");
        cartPage.clickCheckoutButton();

        logger.info("Step5: Click on cancel button");
        checkoutFormPage.clickCancelButton();

        logger.info("Verify user is on previous page that is cart page now ");
        try {
            Assert.assertTrue(cartPage.cartHeadingExists());
            logger.info("User is navigated back to Cart page");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("User is not navigated to cart page");
            Assert.fail("User is not navigated to cart page");
        }
    }

    @Test(priority = 4)
    public void testInformationFormNegative() {
        checkoutFormPage = new CheckoutFormPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);

        logger.info("Verify checkout information form field are displaying error message for invalid data");
        logger.info("Step2: Add all the products to cart");
        productListPage.addAllProductsToCart();

        logger.info("Step3: Navigated to cart page");
        productListPage.clickCartButton();

        logger.info("Step4: Click on checkout button");
        cartPage.clickCheckoutButton();

        logger.info("Step5: Enter number data inside first name and last name and String inside postal code");
        checkoutFormPage.enterFirstName("00000");
        checkoutFormPage.enterLastName("11111");
        checkoutFormPage.enterPostalCode("test");

        logger.info("Verify user should not be able to proceed as invalid data is entered");
        try {
            checkoutFormPage.clickOnContinueButton();
            Assert.assertTrue(checkoutFormPage.informationPageHeadingExists());
            logger.info("User is unable to proceed with entering invalid information data");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("User is able to proceed with entering invalid information data");
            Assert.fail("User is able to proceed with entering invalid information data");
        }
    }
}
