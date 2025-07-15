package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.ProductDetailsPage;
import pageObject.ProductListPage;
import testBase.BaseCase;

import java.util.List;
import java.util.NoSuchElementException;

public class TC003_ProductDetailsPageTest extends BaseCase {

    ProductDetailsPage productDetailsPage;
    ProductListPage productListPage;

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
    public void testProductNameAndPrice() {
        logger.info("Verify the names and prices of th products are in sync on PLP and PDP");

        productDetailsPage = new ProductDetailsPage(driver);

        logger.info("Step2: Select a random product from PLP");
        List<Object> prodDet = productDetailsPage.selectARandomProduct();

        logger.info("Product selected on PLP having name as {} and price as {}", prodDet.getFirst(), prodDet.get(1));

        String productNameOnPdp = productDetailsPage.getProductName();
        String productPriceOnPdp = productDetailsPage.getProductPrice();

        logger.info("Product on PDP having name as {} and price is {}", productNameOnPdp, productPriceOnPdp);

        try {
            Assert.assertEquals(productNameOnPdp, prodDet.getFirst(), "Product name mismatched");
            Assert.assertEquals(productPriceOnPdp, prodDet.get(1), "Product price mismatched");
            logger.info("Product name and price on PLP and PDP are in sync");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Product name and price on PLP and PDP are not in sync");
            Assert.fail("Product name and price on PLP and PDP are not in sync");
        }
    }

    //    @Test(priority = 3)
    public void testBackToPLP() {
        logger.info("Verify back to PLP button on product details page.");
        productDetailsPage = new ProductDetailsPage(driver);

        logger.info("Step2: Select a random product on PLP");
        productDetailsPage.selectARandomProduct();

        logger.info("Verify product is selected and redirected to PDP ");

        logger.info("Step3: click on back to products button");
        productDetailsPage.clickBackToProduct();

        try {
            Assert.assertTrue(productDetailsPage.productHeadingExists());
            logger.info("Products listing page is displayed with the 'Products' heading");
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            logger.error("'Products' heading is not displayed and user is not redirected to PLP");
            Assert.fail("'Products' heading is not displayed and user is not redirected to PLP");
        }
    }

    //@Test(priority = 2)
    public void testAddToCart() {
        logger.info("Verify add to cart functionality is working as expected from PDP");
        productDetailsPage = new ProductDetailsPage(driver);
        productListPage = new ProductListPage(driver);

        int cartItems = 0;
        productListPage.emptyCart();

        logger.info("Step2: Select a random product and navigate to its PDP and click on add to cart");
        List<Object> firstProductDetails = productDetailsPage.selectARandomProduct();
        productDetailsPage.clickAddToCart();
        logger.info("Item added to the cart");
        cartItems += 1;

        logger.info("Step3: Get the actual number of cart items");

        int actualCartItems = productListPage.getNumberOfCartItems();

        try {
            Assert.assertEquals(actualCartItems, cartItems, "Add to cart on PDP is not working as expected");
            logger.info("add to cart on PDP is working as expected");
            logger.info("Navigate back to the PLP page and select a different product");
            productDetailsPage.clickBackToProduct();
            List<Object> secondProductDetails = productDetailsPage.selectARandomProduct();

            if (secondProductDetails.getLast() != firstProductDetails.getLast()) {
                logger.info("Second product is Selected");
                logger.info("Click on Add to cart on its PDP");

                productDetailsPage.clickAddToCart();
                cartItems += 1;

                logger.info("Verify cart items are increasing by 1");
                actualCartItems = productListPage.getNumberOfCartItems();

                Assert.assertEquals(actualCartItems, cartItems);
                productDetailsPage.clickRemoveButton();
                cartItems -= 1;

                actualCartItems = productListPage.getNumberOfCartItems();
                Assert.assertEquals(actualCartItems, cartItems);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("Add to cart is not working properly");
            Assert.fail("Add to cart not working properly");
        }
    }

    @Test(priority = 2)
    public void testRemoveButton() {
        logger.info("Verify remove button functionality is working as expected from PDP");
        int cartItems = 0;

        productDetailsPage = new ProductDetailsPage(driver);
        productListPage = new ProductListPage(driver);
        productListPage.emptyCart();

        logger.info("Step2: add any random product to cart");
        productDetailsPage.addRandomProductToCart();
        cartItems += 1;
        logger.info("Step3: Click on remove button on PDP");
        productDetailsPage.clickRemoveButton();
        cartItems -= 1;
        int actualCartItems = productListPage.getNumberOfCartItems();

        try {
            Assert.assertEquals(actualCartItems, 0, "Cart Items Count does not match");
            logger.info("Remove button is working as expected");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Remove button is not working as expected");
            Assert.fail("Remove button is not working as expected");
        }
    }
}
