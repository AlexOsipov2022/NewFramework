package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static constants.CommonConstants.UI_BASE_URL;

@Story("UI Selenide tests")
public class UiSelenideTests extends BaseSelenideTest {

    @Test
    void submitWebFormSelenideTest() {
        open(UI_BASE_URL);
        $(byText("Web form")).click();
        $("#my-text-id").setValue("Text");
        $("button[type='submit']").click();
        $(".display-6").shouldHave(text("Form submitted"));
    }
}
