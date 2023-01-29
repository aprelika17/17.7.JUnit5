package quru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParametrisedTestCsvSource {

    @BeforeEach
    void beforeEachTest() {
        Configuration.baseUrl = "https://nn.avtosushi.ru";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void afterEach() {
        Selenide.clearBrowserCookies();
    }

    @CsvSource({"Бургер классика, #cart-form-7133",
            "Чизбургер, #cart-form-7135",
            "Чикенбургер, #cart-form-7134"})
    @ParameterizedTest(name = "Был выбран {0} и добавлен в Корзину")
    @Tags({@Tag("Blocker"), @Tag("UI_TEST")})
    void commonAddBurgerToCart(String burgerName, String burgerId) {
        open("/shop/burgery");
        $(burgerId).$(byText(burgerName)).click();
        $(burgerId).$(byText("В корзину")).click();
        $("#shop_cart").click();
        $("#cart-items-grid").shouldHave(Condition.text(burgerName));
    }
}
