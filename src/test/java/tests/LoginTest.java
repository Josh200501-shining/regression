package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    public void setupClass() {
        // No need to set system property if using WebDriverManager or if path is on system PATH
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    }

    @BeforeEach
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();

        // Fix: create unique temp dir for user data to avoid profile in-use error in CI
        Path userDataDir = Files.createTempDirectory("chrome-user-data");
        options.addArguments("--user-data-dir=" + userDataDir.toAbsolutePath().toString());

        // Other recommended args for CI/headless if needed:
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new"); // or "--headless" for older Chrome versions
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
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
        // Intentionally failing test to check failure reporting
        Assertions.assertTrue(loginPage.isLoginSuccessful(), "This test is supposed to fail!");
    }

    @Test
    @DisplayName("Test invalid password - intentional failure")
    public void testInvalidPassword() {
        loginPage.login("testuser", "wrongpassword");
        // Intentionally failing test to check failure reporting
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
