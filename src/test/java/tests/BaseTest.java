package tests;

import com.codeborne.selenide.Configuration;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    @Getter
    static WebDriver driver;
    WebDriverWait longWait;

    @BeforeEach
    void setup() {
        // 1. Создаем и настраиваем ChromeOptions для Headless-режима
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        // Крайне важно для Headless, чтобы имитировать разрешение экрана
        options.addArguments("--window-size=1920,1080");

        // 2. Инициализация драйвера С ОПЦИЯМИ
        // Эту строку необходимо вызвать ПЕРЕД тем, как использовать 'driver'!
        driver = new ChromeDriver(options);

        // 3. Установка неявного таймаута (теперь driver инициализирован!)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 4. Инициализация явного ожидания (теперь driver инициализирован!)
        longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}