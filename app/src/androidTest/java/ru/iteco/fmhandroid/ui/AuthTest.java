package ru.iteco.fmhandroid.ui;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.AuthSteps.enterLoginAndPasswordAndClickSignInButton;
import static ru.iteco.fmhandroid.ui.AuthSteps.logoutIfLogoVisible;
import static ru.iteco.fmhandroid.ui.Helper.assertViewDisplayed;
import static ru.iteco.fmhandroid.ui.Helper.checkPopupMessageText;
import static ru.iteco.fmhandroid.ui.TestConfig.LOGO;
import static ru.iteco.fmhandroid.ui.TestConfig.VALID_LOGIN;
import static ru.iteco.fmhandroid.ui.TestConfig.VALID_PASSWORD;
import static ru.iteco.fmhandroid.ui.TestConfig.empty_login_or_password;
import static ru.iteco.fmhandroid.ui.TestConfig.randomLogin;
import static ru.iteco.fmhandroid.ui.TestConfig.randomPassword;
import static ru.iteco.fmhandroid.ui.TestConfig.wrong_login_or_password;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
    }

    @After
    public void logOut() {
        logoutIfLogoVisible();
        };

    @DisplayName("ID 1. Вход в систему с зарегистрированными учетными данными (с включенным доступом в интернет)")
    @Feature("Авторизация")
    @Story("Позитивные сценарии")
    @Test
    public void testSuccessfulLoginWithValidCredentials() {
        step("Шаг 1. Авторизация с использованием верных учетных данных зарегистрированного пользователя");
        enterLoginAndPasswordAndClickSignInButton(VALID_LOGIN, VALID_PASSWORD);

        step("Шаг 2. Проверка отображения логотипа на главной странице после успешной авторизации");
        assertViewDisplayed(LOGO);
    }

    @DisplayName("ID 5. Ошибка авторизации с незаполненным паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnEmptyPassword() {
        mActivityScenarioRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
        step("Шаг 1. Авторизация без ввода пароля");
        enterLoginAndPasswordAndClickSignInButton(VALID_LOGIN, "");

        step("Шаг 2. Проверка отображения сообщения об ошибке при незаполненном пароле");
        checkPopupMessageText(empty_login_or_password, decorView);
    }

    @DisplayName("ID 4. Ошибка авторизации с незарегистрированными учетными данными")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorLoginWithUnregisteredCredentials() {
        step("Шаг 1. Попытка авторизации с использованием незарегистрированных учетных данных");
        enterLoginAndPasswordAndClickSignInButton(randomLogin, randomPassword);

        step("Шаг 2. Проверка отображения сообщения о неверном логине или пароле");
        checkPopupMessageText(wrong_login_or_password, decorView);
    }

    @DisplayName("ID 5. Ошибка авторизации с неверным паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnIncorrectPassword() {
        step("Шаг 1. Авторизация с неправильным паролем");
        enterLoginAndPasswordAndClickSignInButton(VALID_LOGIN, randomPassword);

        step("Шаг 2. Проверка отображения сообщения об ошибке при неверном пароле");
        checkPopupMessageText(wrong_login_or_password, decorView);
    }

}

