package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.CartPage;
import pageObject.ProductDetailsPage;
import pageObject.ProductListPage;
import testBase.BaseCase;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TC005_CartPage extends BaseCase {

    CartPage cartPage;
    ProductDetailsPage pdp;
    ProductListPage plp;

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

    //    @Test(priority = 2)
    public void testCartHeading() {

        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);

        logger.info("Verify cart page heading is displayed as expected");
        logger.info("Step2: Add a product to Cart");
        pdp.clickOnCartButton();
        logger.info("Verify on cart page the heading of cart is displayed");
        try {
            Assert.assertTrue(cartPage.cartHeadingExists());
            logger.info("Cart page heading is displayed as expected");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Cart page is not displayed");
            Assert.fail("Cart page is not displayed");
        }
    }

    //    @Test(priority = 3)
    public void testCheckoutButton() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);

        logger.info("Step2: Add product to cart");
        pdp.clickOnCartButton();

        try {
            Assert.assertTrue(cartPage.cartHeadingExists());
            logger.info("Cart page heading is displayed as expected");
            cartPage.clickCheckoutButton();
            try {
                Assert.assertTrue(cartPage.checkoutPageHeadingExist());
                logger.info("Checkout button is working as expected");
            } catch (Exception e) {
                logger.error("Checkout button is not functioning properly");
                Assert.fail("Checkout button is not functioning properly");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Cart page heading is not displayed");
            Assert.fail("Cart page heading is not displayed");
        }
    }

    //    @Test(priority = 4)
    public void testContinueShopping() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);

        logger.info("Step2: Add a product to cart");
        pdp.clickOnCartButton();

        logger.info("Step3: Proceed with continue shopping");
        cartPage.clickContinueShoppingButton();

        logger.info("Step4: Verify user is on PLP page");
        String plpPageHeading = plp.getPageHeading();

        try {
            Assert.assertFalse(plpPageHeading.isEmpty());
            captureScreen("Before_Adding");
            logger.info("User is on PLP and heading of 'Products' is displayed");
            logger.info("Step5: Now add all the products to cart");
            plp.addAllProductsToCart();
            logger.info("Step6: Click on Cart Button");
            pdp.clickOnCartButton();
            int currentItemsInCart = cartPage.getCartItems().size();
            int totalCartItems = plp.getNumberOfCartItems();
            logger.info("Verify actual cart items are equal to the products added to cart");
            try {
                Assert.assertEquals(currentItemsInCart, totalCartItems);
                logger.info("Cart items are same as we have added");
                captureScreen("continue");
            } catch (IOException e) {
                logger.error("Cart items are not same as selected");
                Assert.fail("Cart items are not same as selected");
            }
        } catch (AssertionError | IOException e) {
            logger.error(e.getMessage());
            logger.error("User is not navigate to PLP after clicking on Continue shopping");
            Assert.fail("User is not navigate to PLP after clicking on Continue shopping");
        }
    }

    //    @Test(priority = 5)
    public void testRemoveFromCart() throws IOException, InterruptedException {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);

        logger.info("Verify checkout button is working as expected");
        logger.info("Step2: Select all products and add to cart");
        plp.addAllProductsToCart();
        logger.info("Step3: Click on cart button");
        pdp.clickOnCartButton();
        List<WebElement> cartItems = cartPage.getCartItems();
        int originalCartSize = cartItems.size();
        logger.info("Verify if cart items are present then validate the remove button functionality");

        if (!cartItems.isEmpty()) {
            logger.info("Select a random product from the cart");
            WebElement random_product = cartItems.get(new Random().nextInt(cartItems.size()));
            captureScreen("cart");
            logger.info("Remove that product using remove button");
            cartPage.removeCartItem(random_product);
            Thread.sleep(3000);
            logger.info("Get the current items in number");
            List<WebElement> currentCartItems = cartPage.getCartItems();
            int currentCartSize = currentCartItems.size();

            try {
                Assert.assertTrue(currentCartSize < originalCartSize);
                logger.info("current cart items are less than actual once so remove button is working fine.");
                try {
                    Assert.assertEquals(currentCartSize, plp.getNumberOfCartItems());
                    logger.info("Cart items on PLP and actual cart have same data");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.error("Error or mismatch in cart items on PLP and cart");
                    Assert.fail("cart item count is not as expected");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("cart item count is not as expected");
                Assert.fail("cart item count is not as expected");
            }
        }
    }

    @Test(priority = 6)
    public void testEmptyCartCheckout() {
        cartPage = new CartPage(driver);
        pdp = new ProductDetailsPage(driver);
        plp = new ProductListPage(driver);

        logger.info("Verify user is not able to proceed if cart is empty.");
        logger.info("Step 2: Add a product to cart");
        plp.emptyCart();
        plp.clickCartButton();
        logger.info("Verify on cart page the heading of Cart is displayed");
        try {
            Assert.assertEquals(cartPage.getCartItems().size(), 0);
            cartPage.clickCheckoutButton();
            logger.info("Verify user is unable to proceed with checkout");
            try {
                Assert.assertTrue(cartPage.cartHeadingExists());
                logger.info("User is unable to proceed with the cart having 0 items as expected");
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("User is able to proceed having cart with 0 items");
                Assert.fail("User is able to proceed having cart with 0 items");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Cart page is not empty even after removing all the products");
            Assert.fail("Cart page is not empty even after removing all the products");
        }
    }
}
