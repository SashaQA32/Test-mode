package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Api;
import ru.netology.data.DataGeneratorForRegistration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    DataGeneratorForRegistration.UserInfo generator1 = DataGeneratorForRegistration.getUserInfoActive();
    DataGeneratorForRegistration.UserInfo generator2 = DataGeneratorForRegistration.getUserInfoBlocked();

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");
    }

    public void registration(String login, String password) {
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $(".button").click();
    }

    @Test
    public void shouldSignInIfExistentUser() {

        Api.signUp(generator1);
        registration(generator1.getLogin(), generator1.getPassword());
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignInIfNotExistentUser() {

        registration(generator2.getLogin(), generator2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignInIfBlockUser() {

        Api.signUp(generator2);
        registration(generator2.getLogin(), generator2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignIfInvalidValueLogin() {

        Api.signUp(generator2);
        registration(DataGeneratorForRegistration.getInvalidLogin(), generator2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotSignIfInvalidValuePassword() {

        Api.signUp(generator2);
        registration(generator2.getLogin(), DataGeneratorForRegistration.getInvalidPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}
