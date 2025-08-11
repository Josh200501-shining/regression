package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;
    private final String url = "https://your-login-page-url.com";  // replace with actual URL

    // Locators
    private final By usernameField = By.id("username");    // change according to your page
    private final By passwordField = By.id("password");    // change according to your page
    private final By loginButton = By.id("loginBtn");      // change according to your page
    private final By errorMessage = By.id("errorMsg");     // change according to your page
    private final By successIndicator = By.id("welcomeMsg"); // some element visible on successful login

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        try {
            return driver.findElement(successIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginFailed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
