package ru.iteco.fmhandroid.ui.steps;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.AuthScreen.authorization;
import static ru.iteco.fmhandroid.ui.elements.AuthScreen.loginField;
import static ru.iteco.fmhandroid.ui.elements.AuthScreen.passField;
import static ru.iteco.fmhandroid.ui.elements.AuthScreen.signInButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.logo;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkViewIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.enterText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.isViewDisplayedAfterAction;
import static ru.iteco.fmhandroid.ui.helper.UIActions.waitForViewDisplayed;
import static ru.iteco.fmhandroid.ui.helper.WaitingUtil.waitDisplayed;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.helper.AuthHelper;

public class AuthSteps {

    private final AuthHelper authHelper;

    public AuthSteps(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    public AuthHelper getAuthHelper() {
        return authHelper;
    }

    public void authenticate(AuthHelper.User info) {
        waitForViewDisplayed(loginField, 6000);
        performSignIn(info);
    }

    public void performSignIn(AuthHelper.User info) {
        step("Ввод логина: <" + info.getLogin() + "> и пароля: <" + info.getPass() + "> и нажатие на кнопку ВОЙТИ");
        enterText(loginField, info.getLogin());
        enterText(passField, info.getPass());
        clickButton(signInButton);
    }

    public void checkLogoDisplayedAfterAuth(boolean expectedResult) {
        step(expectedResult ? "Открытие главной страницы" : "Главная страница не открылась");
        boolean isDisplayed = isViewDisplayedAfterAction(waitDisplayed(logo, 2000), expectedResult);
        if (expectedResult && !isDisplayed) {
            throw new AssertionError("Logo is not displayed after authentication.");
        } else if (!expectedResult && isDisplayed) {
            throw new AssertionError("Logo is displayed after authentication, but it should not be.");
        }
    }

    public void checkAuthScreenElements() {
        waitForViewDisplayed(loginField, 6000);
        Allure.step("Отображение заголовка, полей логина и пароля и кнопки ВОЙТИ");
        checkTextIsDisplayed(authorization);
        checkViewIsDisplayed(loginField);
        checkViewIsDisplayed(passField);
        checkViewIsDisplayed(signInButton);
    }
}
