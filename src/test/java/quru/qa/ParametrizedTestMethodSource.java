package quru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import quru.qa.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedTestMethodSource {
    @BeforeEach
    void beforeEach() {
        Configuration.baseUrl = "https://admgor.nnov.ru";
        Configuration.browserSize = "1200x960";
        Configuration.holdBrowserOpen = true;
    }

    static Stream<Arguments> selenideLocaleDataProvider() {
        return Stream.of(
                Arguments.of(Locale.Русский, List.of("Муниципалитет", "О городе", "Районы", "Жителям", "Бизнесу",
                        "Документы", "Пресс-центр", "Приемная")),
                Arguments.of(Locale.Français, List.of("La municipalité", "Sur la ville", "Zones", "Les résidents",
                        "Entreprise", "Documents", "Le centre de presse", "Réception")),
                Arguments.of(Locale.Deutsch, List.of("Gemeinde", "Über die Stadt", "Stadtteile", "Bewohner",
                        "Geschäft", "Dokumente", "Presse-Center", "Rezeption")),
                Arguments.of(Locale.Español, List.of("El municipio de", "Sobre la ciudad", "Zonas",
                        "Los habitantes de la", "Negocio", "Documentos", "Centro de prensa", "Recepción")),
                Arguments.of(Locale.English, List.of("Municipality", "About the city", "Districts", "For residents",
                        "For business", "Documents", "Press center", "Reception area"))
        );
    }

    @MethodSource("selenideLocaleDataProvider")
    @ParameterizedTest(name = "Для локали {0} отображены пункты меню {1}")
    @Tags({@Tag("Critical"), @Tag("UI_TEST")})
    void checkMenuButtonsForAllLocale(
            Locale locale,
            List<String> menuItems
    ) {
        open("/#");
        $(".btn-lang").$(".icon-right-arrow").click();
        $$("#site-lang-dropdown li").find(text(locale.name())).click();
        //$(".btn-lang").$(".icon-right-arrow").click();
        $(".header-nav-more").click();
        $$(".nav-accordion li")
                .filter(visible)
                .shouldHave(CollectionCondition.texts(menuItems));
    }
}
