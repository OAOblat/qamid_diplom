package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.Helper.clickButton;
import static ru.iteco.fmhandroid.ui.Helper.enterText;
import static ru.iteco.fmhandroid.ui.TestConfig.LOGIN_FIELD;
import static ru.iteco.fmhandroid.ui.TestConfig.LOGO;
import static ru.iteco.fmhandroid.ui.TestConfig.LOGOUT_TEXT;
import static ru.iteco.fmhandroid.ui.TestConfig.PASSWORD_FIELD;
import static ru.iteco.fmhandroid.ui.TestConfig.SIGNING_BUTTON;
import static ru.iteco.fmhandroid.ui.TestConfig.authorization_image_button;
import static ru.iteco.fmhandroid.ui.WaitingUtil.waitDisplayed;


public class AuthSteps {
    public static void enterLoginAndPasswordAndClickSignInButton(String login, String password) {
        step("Ввод логина: " + login + " и пароля: " + password + " и нажатие на кнопку ВОЙТИ");
        onView(isRoot()).perform(waitDisplayed(LOGIN_FIELD, 7000));
        enterText(LOGIN_FIELD, login);
        enterText(PASSWORD_FIELD, password);
        clickButton(SIGNING_BUTTON);
    }

    public static boolean isViewVisible() {
        try {
            onView(withId(LOGO)).check(matches(isDisplayed()));
            return true;
        } catch (IncompatibleClassChangeError e) {
            return false;
        }
    }

    public static void logoutIfLogoVisible() {
        step("Выход из системы");
        // Проверяем, виден ли логотип на экране
        if (isViewVisible()) {
            // Если логотип виден, выполняем выход из системы
            clickButton(authorization_image_button);
            onView(withText(LOGOUT_TEXT)).perform(click());
        }
    }
}
