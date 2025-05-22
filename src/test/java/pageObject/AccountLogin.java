package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogin extends BasePage {

    //Locators
    @FindBy(xpath = "//input[@id='user-name']")
    WebElement username;
    @FindBy(xpath = "//input[@id='password']")
    WebElement password;
    @FindBy(xpath = "//input[@id='login-button']")
    WebElement btnLogin;

    //constuctor
    public AccountLogin(WebDriver driver) {
        super(driver);
    }

    //Action Methods
    public void setUsername(String uname) {
        username.sendKeys(uname);
    }

    public void setPassword(String pass) {
        password.sendKeys(pass);
    }

    public void setBtnLogin() {
        btnLogin.click();
    }
}
