package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.AccountLogin;
import testBase.BaseCase;
import utilities.DataProviders;

public class TC001_LoginTest_DDT extends BaseCase {
    @Test(groups = {"Regression", "Master"}, dataProvider = "logindata", dataProviderClass = DataProviders.class)
    public void testLogin(String username, String password) {
        logger.info("=====  TC001_LoginTest Started =====");

        try {
            AccountLogin accountLogin = new AccountLogin(driver);

            logger.info("Providing Login Credentials");

            accountLogin.setUsername(username);
            accountLogin.setPassword(password);
            accountLogin.setBtnLogin();

            String currentURL = driver.getCurrentUrl();

            if (currentURL.equals("https://www.saucedemo.com/inventory.html")) {
//                AccountLogout lg = new AccountLogout(driver);
//                lg.clickMenu();
//                lg.clickBtnLogout();
                logger.info("{} user successfully login!", username);
                Assert.assertTrue(true);
            } else {
                logger.info(" login failed for user: {}", username);
                Assert.fail();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail();
        }

        logger.info("=====  Finished TC001_LoginTest    =====");
    }
}
