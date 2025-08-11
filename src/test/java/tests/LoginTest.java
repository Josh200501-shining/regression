package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Test valid login")
    public void testValidLogin() {
        loginPage.login("testuser", "password123");
        Assertions.assertTrue(loginPage.isLoginSuccessful(), "Login should succeed");
        Assertions.assertFalse(loginPage.isLoginFailed(), "Error message should NOT be displayed");
    }

    @Test
    @DisplayName("Test invalid username - intentional failure")
    public void testInvalidUsername() {
        loginPage.login("wronguser", "password123");
        // Intentionally asserting login success so test fails
        Assertions.assertTrue(loginPage.isLoginSuccessful(), "This test is supposed to fail!");
    }

    @Test
    @DisplayName("Test invalid password - intentional failure")
    public void testInvalidPassword() {
        loginPage.login("testuser", "wrongpassword");
        // Intentionally asserting login success so test fails
        Assertions.assertTrue(loginPage.isLoginSuccessful(), "This test is supposed to fail!");
    }

    @Test
    @DisplayName("Test empty username")
    public void testEmptyUsername() {
        loginPage.login("", "password123");
        Assertions.assertFalse(loginPage.isLoginSuccessful(), "Login should fail");
        Assertions.assertTrue(loginPage.isLoginFailed(), "Error message should be displayed");
    }

    @Test
    @DisplayName("Test empty password")
    public void testEmptyPassword() {
        loginPage.login("testuser", "");
        Assertions.assertFalse(loginPage.isLoginSuccessful(), "Login should fail");
        Assertions.assertTrue(loginPage.isLoginFailed(), "Error message should be displayed");
    }
}
