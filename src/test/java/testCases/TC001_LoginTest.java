package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.AccountLogin;
import testBase.BaseCase;

public class TC001_LoginTest extends BaseCase {
    @Test(groups = {"Regression", "Master"})
    public void testLogin() {
        logger.info("****TC001_LoginTest****");

        try {
            AccountLogin accountLogin = new AccountLogin(driver);

            logger.info("Providing Login Credentials");

            accountLogin.setUsername(p.getProperty("username"));
            accountLogin.setPassword(p.getProperty("password"));
            accountLogin.setBtnLogin();

            String currentURL = driver.getCurrentUrl();

            Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html");
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }

        logger.info("**** Finished TC001_LoginTest ****");
    }
}
