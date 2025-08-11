package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    private By usernameField = By.id("username");  // Change locator as per your page
    private By passwordField = By.id("password");  // Change locator
    private By loginButton = By.id("loginBtn");    // Change locator
    private By successMessage = By.id("welcomeMsg"); // Or any element visible after login

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        // Simple check if success message displayed
        return driver.findElements(successMessage).size() > 0;
    }
}
