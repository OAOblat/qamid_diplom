package ru.iteco.fmhandroid.ui.helper;

import ru.iteco.fmhandroid.ui.data.TestData;

public class AuthHelper {

    private final TestData testData;

    public AuthHelper(TestData testData) {
        this.testData = testData;
    }

    public class User {
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

    public User authInfo() {
        return new User(testData.getValidLogin(), testData.getValidPass());
    }

    public User invalidAuthData() {
        return new User(testData.getInvalidLogin(), testData.getInvalidPass());
    }

    public User invalidLoginData() {
        return new User(testData.getInvalidLogin(), testData.getValidPass());
    }

    public User invalidPassData() {
        return new User(testData.getValidLogin(), testData.getInvalidPass());
    }

    public User emptyLogin() {
        return new User(testData.getEmptyField(), testData.getValidPass());
    }

    public User emptyPassword() {
        return new User(testData.getValidLogin(), testData.getEmptyField());
    }

    public User emptyLoginAndPassword() {
        return new User(testData.getEmptyField(), testData.getEmptyField());
    }
}
