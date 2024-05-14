package ru.iteco.fmhandroid.ui.helper;

import static ru.iteco.fmhandroid.ui.data.TestData.emptyField;
import static ru.iteco.fmhandroid.ui.data.TestData.invalidLogin;
import static ru.iteco.fmhandroid.ui.data.TestData.invalidPass;
import static ru.iteco.fmhandroid.ui.data.TestData.validLogin;
import static ru.iteco.fmhandroid.ui.data.TestData.validPass;

public class AuthHelper {
    public AuthHelper() {
    }

    public static class User {
        private final String login;
        private final String pass;

        public User(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        public String getLogin() {
            return login;
        }

        public String getPass() {
            return pass;
        }
    }

    public static User authInfo() {
        return new User(validLogin, validPass);
    }

    public static User invalidAuthData() {
        return new User(invalidLogin, invalidPass);
    }

    public static User invalidLoginData() {
        return new User(invalidLogin, validPass);
    }

    public static User invalidPassData() {
        return new User(validLogin, invalidPass);
    }

    public static User emptyLogin() {
        return new User(emptyField, validPass);
    }

    public static User emptyPassword() {
        return new User(validLogin, emptyField);
    }

    public static User emptyLoginAndPassword() {
        return new User(emptyField, emptyField);
    }
}
