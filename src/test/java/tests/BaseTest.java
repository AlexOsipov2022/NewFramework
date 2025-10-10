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
        // 1. Создаем и настраиваем ChromeOptions
        ChromeOptions options = new ChromeOptions();

        // Базовые настройки для Headless-режима
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        // *** ВАЖНЫЕ ДОБАВЛЕНИЯ ДЛЯ JENKINS/DOCKER (Устранение ошибок) ***

        // **A. Отключение песочницы (обязательно для большинства Docker-контейнеров)**
        // Устраняет проблемы безопасности и конфликтов, связанных с контейнеризацией.
        options.addArguments("--no-sandbox");

        // **B. Использование /tmp вместо /dev/shm**
        // Предотвращает сбои сессий, вызванные нехваткой памяти в /dev/shm внутри контейнера (часто ограничен 64MB).
        options.addArguments("--disable-dev-shm-usage");

        // **C. Уникальный каталог данных (Уже было у вас, оставляем)**
        // Использование номера сборки Jenkins гарантирует, что параллельные сборки не будут конфликтовать.
        String buildNumber = System.getenv("BUILD_NUMBER");
        // Проверяем на null, если переменная не установлена, используем уникальный ID
        String uniqueId = (buildNumber != null && !buildNumber.isEmpty()) ? buildNumber : String.valueOf(System.currentTimeMillis());
        String uniqueUserDataDir = "/tmp/chrome_user_data_" + uniqueId;
        options.addArguments("--user-data-dir=" + uniqueUserDataDir);

        // 2. Инициализация драйвера С ОПЦИЯМИ
        driver = new ChromeDriver(options);

        // 3. Установка неявного таймаута
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // 4. Инициализация явного ожидания
        longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}