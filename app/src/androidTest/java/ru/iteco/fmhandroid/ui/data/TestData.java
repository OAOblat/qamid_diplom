package ru.iteco.fmhandroid.ui.data;


import io.bloco.faker.Faker;

public class TestData {
    static Faker faker = new Faker();

    public static final String validLogin = "login2";
    public static final String validPass = "password2";
    public static final String invalidLogin = faker.name.firstName() + faker.number.between(1, 10);
    public static final String invalidPass = faker.internet.password();
    public static final String emptyField = "";
}
