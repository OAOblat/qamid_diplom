package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.TestData;
import ru.iteco.fmhandroid.ui.helper.AuthHelper;
import ru.iteco.fmhandroid.ui.helper.SetupHelper;
import ru.iteco.fmhandroid.ui.helper.ToastHelper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.ToastSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {
    private AuthSteps authSteps;
    private final NavSteps navSteps = new NavSteps();
    private ToastSteps toastSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        TestData testData = new TestData();
        AuthHelper authHelper = new AuthHelper(testData);
        authSteps = new AuthSteps(authHelper);

        SetupHelper setupHelper = new SetupHelper(mActivityScenarioRule);
        toastSteps = new ToastSteps(new ToastHelper(setupHelper.getDecorView()));
        try {
            navSteps.isAuthScreenVisible();
        } catch (Exception e) {
            setupHelper.logoutIfLogoVisible();
        }
    }

    @DisplayName("Отображение всех необходимых элеменов на экране авторизации")
    @Feature("Авторизация")
    @Story("Позитивные сценарии")
    @Test
    public void testVisibilityAllElementsOnAuthScreen() {
        authSteps.checkAuthScreenElements();
    }

    @DisplayName("Вход в систему с зарегистрированными учетными данными (с включенным доступом в интернет)")
    @Feature("Авторизация")
    @Story("Позитивные сценарии")
    @Test
    public void testSuccessfulLoginWithValidCredentials() {
        AuthHelper.User info = authSteps.getAuthHelper().authInfo();
        authSteps.authenticate(info);
        authSteps.checkLogoDisplayedAfterAuth(true);
    }

    @DisplayName("Ошибка авторизации с незаполненным паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnEmptyPassword() {
        AuthHelper.User info = authSteps.getAuthHelper().emptyPassword();
        authSteps.authenticate(info);
        toastSteps.checkEmptyLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }

    @DisplayName("Ошибка авторизации с незаполненным логином")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnEmptyLogin() {
        AuthHelper.User info = authSteps.getAuthHelper().emptyLogin();
        authSteps.authenticate(info);
        toastSteps.checkEmptyLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }

    @DisplayName("Ошибка авторизации с незаполненным логином и паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnEmptyLoginAndPassword() {
        AuthHelper.User info = authSteps.getAuthHelper().emptyLoginAndPassword();
        authSteps.authenticate(info);
        toastSteps.checkEmptyLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }

    @DisplayName("Ошибка авторизации с незарегистрированными учетными данными")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorLoginWithUnregisteredCredentials() {
        AuthHelper.User info = authSteps.getAuthHelper().invalidAuthData();
        authSteps.authenticate(info);
        toastSteps.checkWrongLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }

    @DisplayName("Ошибка авторизации с верным логином и неверным паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnIncorrectPassword() {
        AuthHelper.User info = authSteps.getAuthHelper().invalidPassData();
        authSteps.authenticate(info);
        toastSteps.checkWrongLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }

    @DisplayName("Ошибка авторизации с неверным логином и верным паролем")
    @Feature("Авторизация")
    @Story("Негативные сценарии")
    @Test
    public void testErrorOnIncorrectLogin() {
        AuthHelper.User info = authSteps.getAuthHelper().invalidLoginData();
        authSteps.authenticate(info);
        toastSteps.checkWrongLoginOrPassword();
        authSteps.checkLogoDisplayedAfterAuth(false);
    }
}
