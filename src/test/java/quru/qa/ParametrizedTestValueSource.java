package quru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParametrizedTestValueSource {

    @BeforeEach
    void beforeEachTest() {
        Configuration.baseUrl = "https://www.mvideo.ru";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(
            strings = {"Ноутбук Apple MacBook Air 13 M1/8/256 Space Gray",
                        "Ноутбук Apple MacBook Air 13 M1/8/256 Silver",
                        "Ноутбук Apple MacBook Air 13 M1/8/256 Gold"
                        }
    )
    @ParameterizedTest(name = "В результатах поиска присутствует модель {0}")
    @Tags({@Tag("Medium"), @Tag("UI_TEST")})
    void commonSearchMacBookAir(String modelName) {
        open("/");
        $(".bottom-navbar-slider-content").$(byText("Ноутбуки")).click();
        $(".plp__collections-container").$(byText("MacBook Air (2020)")).click();
        $(".product-cards-layout--list").shouldHave(Condition.text(modelName));
    }
}
