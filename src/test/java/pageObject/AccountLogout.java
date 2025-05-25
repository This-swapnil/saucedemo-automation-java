package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogout extends BasePage {

    //Locators
    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    WebElement menu;

    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    WebElement btnLogout;

    //Constructor
    public AccountLogout(WebDriver driver) {
        super(driver);
    }

    //Action Method

    public void clickMenu() {
        menu.click();
    }

    public void clickBtnLogout() {
        btnLogout.click();
    }
}
