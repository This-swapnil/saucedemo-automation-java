package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.ProductListPage;
import testBase.BaseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TC002_ProductsPage extends BaseCase {
    ProductListPage productsPage;

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
    public void testSortAtoZ() throws InterruptedException {
        logger.info("Verify user is able to sort the products using a-to-z filter");
        try {
            productsPage = new ProductListPage(driver);
            List<String> productsBeforeSort = new ArrayList<>();
            List<String> productsAfterSort = new ArrayList<>();

            for (WebElement products : productsPage.getAllProducts()) {
                productsBeforeSort.add(products.getText());
            }
            logger.info("Sort the product with a-to-z filter");
            productsPage.sort("A_to_Z");

            for (WebElement products : productsPage.getAllProducts()) {
                productsAfterSort.add(products.getText());
            }


            if (productsBeforeSort.equals(productsAfterSort)) {
                logger.info("Products are sorted Successfully");
            } else {
                logger.error("Products are not sorted A to Z correctly.");
                Assert.fail("Products are not sorted in A to Z order.");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
        logger.info("==== Test sorting the products using a-to-z filter Completed ====");
    }

    @Test(priority = 3)
    public void testSortZtoA() throws InterruptedException {
        logger.info("Verify user is able to sort the products using z-to-a filter");
        try {
            productsPage = new ProductListPage(driver);
            List<String> productsBeforeSort = new ArrayList<>();
            List<String> productsAfterSort = new ArrayList<>();

            for (WebElement products : productsPage.getAllProductNames()) {
                productsBeforeSort.add(products.getText());
            }
            logger.info("Sort the product with z-to-a filter");
            productsPage.sort("Z_to_A");

            for (WebElement products : productsPage.getAllProductNames()) {
                productsAfterSort.add(products.getText());
            }

            Collections.reverse(
                    productsAfterSort); //==> To check the reverse order of list is equal to the original list

            if (productsBeforeSort.equals(productsAfterSort)) {
                logger.info("Products are sorted Successfully (Z-to-A)");
            } else {
                logger.error("Products are not sorted Z to A correctly.");
                Assert.fail("Products are not sorted in Z to A order.");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
        logger.info("==== Test sorting the products using z-to-a filter Completed ====");
    }

    @Test(priority = 4)
    public void testSortLowToHigh() throws InterruptedException {
        logger.info("Verify user is able to sort the products using Low-to-High filter");
        try {
            productsPage = new ProductListPage(driver);
            List<Double> productsBeforeSort = new ArrayList<>();
            List<Double> productsAfterSort = new ArrayList<>();

            for (WebElement products : productsPage.getAllProductsPrices()) {
                productsBeforeSort.add(Double.parseDouble(products.getText().replace("$", "")));
            }
            logger.info("Sort the product with Low-to-High filter");
            productsPage.sort("low_high");

            for (WebElement products : productsPage.getAllProductsPrices()) {
                productsAfterSort.add(Double.parseDouble(products.getText().replace("$", "")));
            }

            //Collections.sort(productsBeforeSort);
            productsBeforeSort.sort(Comparator.naturalOrder());

            if (productsAfterSort.equals(productsBeforeSort)) {
                logger.info("Products are sorted Successfully (Low-to-High)");
            } else {
                logger.error("Products are not sorted Low-to-High correctly.");
                Assert.fail("Products are not sorted in Low-to-High order.");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
        logger.info("==== Test sorting the products using Low-to-High filter Completed ====");
    }

    @Test(priority = 5)
    public void testSortHighToLow() throws InterruptedException {
        logger.info("Verify user is able to sort the products using High-to-Low filter");
        try {
            productsPage = new ProductListPage(driver);
            List<Double> productsBeforeSort = new ArrayList<>();
            List<Double> productsAfterSort = new ArrayList<>();

            for (WebElement products : productsPage.getAllProductsPrices()) {
                productsBeforeSort.add(Double.parseDouble(products.getText().replace("$", "")));
            }
            logger.info("Sort the product with High-to-Low filter");
            productsPage.sort("high_low");

            for (WebElement products : productsPage.getAllProductsPrices()) {
                productsAfterSort.add(Double.parseDouble(products.getText().replace("$", "")));
            }

            productsBeforeSort.sort(Comparator.reverseOrder());

            if (productsAfterSort.equals(productsBeforeSort)) {
                logger.info("Products are sorted Successfully (LHigh-to-Low)");
            } else {
                logger.error("Products are not sorted High-to-Low correctly.");
                Assert.fail("Products are not sorted in High-to-Low order.");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }
        logger.info("==== Test sorting the products using High-to-Low filter Completed ====");
    }

    @Test(priority = 6)
    public void testAddToCartAllProducts() {
        //Validating the add to cart button on PLP

        logger.info("Verify user is able to add all the present product to cart");
        productsPage = new ProductListPage(driver);

        logger.info("Step2: Add all the products to cart");

        productsPage.addAllProductsToCart();
        int addedProducts = productsPage.getNumberOfCartItems();
        List<WebElement> allProducts = productsPage.getAllProducts();


        if (addedProducts == allProducts.size()) {
            logger.info("Successfully added all the  products to cart");
        } else {
            logger.error("Unable to add all the products to cart");
            Assert.fail("Unable to add all the products to cart");
        }

        productsPage.emptyCart();
    }

    @Test(priority = 7)
    public void testRemoveButtons() {
        logger.info("Verify remove buttons on home page for all the products are functioning properly");
        productsPage = new ProductListPage(driver);

        productsPage.emptyCart();
        List<WebElement> removeButtons = productsPage.getAllRemoveButtons();
        logger.info("Check no remove buttons should be displayed at start");

        Assert.assertTrue(removeButtons.isEmpty());

        logger.info("No remove buttons are present");
        logger.info("Adding all the products to cart");

        productsPage.addAllProductsToCart();
        List<WebElement> allProducts = productsPage.getAllProducts();
        int productsInCart = productsPage.getNumberOfCartItems();

        if (allProducts.size() == productsInCart) {
            logger.info("Added all the products to cart");
            logger.info("Removing products using remove button");

            productsPage.emptyCart();

            Assert.assertTrue(productsPage.getAllRemoveButtons().isEmpty());
            logger.info("All the remove buttons are functioning properly");
        } else {
            logger.error("Remove buttons are not functioning properly");
            Assert.fail();
        }
    }

    @Test(priority = 8)
    public void testAddToCartByTitle() throws InterruptedException {
        logger.info("Verify user is able to add the products to cart by its title");
        productsPage = new ProductListPage(driver);

        logger.info("Step2: Provide tht product title for adding to cart");
        List<WebElement> products = productsPage.getListOfProductsByTitle("Sauce Labs Backpack");

        logger.info("title provided for product: Sauce Labs Backpack");
        logger.info("Step3: Add product to cart with title Sauce Labs Backpack");

        productsPage.addToCartProductsByTitle("Sauce Labs Backpack");

        try {
            Assert.assertEquals(products.size(), productsPage.getNumberOfCartItems());
            logger.info("Product with given title is added to cart");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Product with given title is not added to cart");
            Assert.fail("Product with given title is not added to cart");
        }
        productsPage.emptyCart();
    }

    @Test(priority = 9)
    public void testAddToCartByPrice() throws InterruptedException {
        logger.info("Verify use is able to add the products to cart by its price");

        productsPage = new ProductListPage(driver);

        logger.info("Step2: Provide the product price for adding to cart");
        List<WebElement> products = productsPage.getListOfProductsByPrice("7.99");
        logger.info("Price provided for product: 7.99");

        logger.info("Step3: Add product to cart with price $7.99");
        productsPage.addToCartProductByPrice(7.99);

        try {
            Assert.assertEquals(products.size(), productsPage.getNumberOfCartItems());
            logger.info("Product with given price is added to cart");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Product with given price is not added to cart");
            Assert.fail("Product with given price is not added to cart");
        }
        productsPage.emptyCart();
    }
}
