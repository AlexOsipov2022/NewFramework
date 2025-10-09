package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseSelenideTest {

    @Getter
    static WebDriver driver;

    WebDriverWait longWait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        Configuration.browser = "chrome";
        ChromeOptions options = new ChromeOptions();
        // 2. Добавляем аргумент для безголового режима
        // Используйте '--headless=new' для современного режима или просто '--headless'
        options.addArguments("--headless=new");
        // 3. Устанавливаем эти опции в конфигурацию Selenide
        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 40;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}