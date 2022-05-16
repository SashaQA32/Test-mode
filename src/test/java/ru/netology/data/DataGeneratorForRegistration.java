package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

public class DataGeneratorForRegistration {
    private DataGeneratorForRegistration() {
    }

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private String login;
        private String password;
        private String status;
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static UserInfo getUserInfo() {
        return new UserInfo(faker.name().username(),
                faker.internet().password(),
                "active");
    }

    public static String getInvalidLogin() {
        return faker.name().username();
    }

    public static String getInvalidPassword() {
        return faker.internet().password();
    }
}
