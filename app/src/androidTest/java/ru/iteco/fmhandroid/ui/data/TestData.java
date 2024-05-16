package ru.iteco.fmhandroid.ui.data;

import io.bloco.faker.Faker;

public class TestData {
    private final String validLogin;
    private final String validPass;
    private final String invalidLogin;
    private final String invalidPass;
    private final String emptyField;

    public TestData() {
        Faker faker = new Faker();
        this.validLogin = "login2";
        this.validPass = "password2";
        this.invalidLogin = faker.name.firstName() + faker.number.between(1, 10);
        this.invalidPass = faker.internet.password();
        this.emptyField = "";
    }

    public String getValidLogin() {
        return validLogin;
    }

    public String getValidPass() {
        return validPass;
    }

    public String getInvalidLogin() {
        return invalidLogin;
    }

    public String getInvalidPass() {
        return invalidPass;
    }

    public String getEmptyField() {
        return emptyField;
    }
}
