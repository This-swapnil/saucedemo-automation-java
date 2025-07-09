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
}
