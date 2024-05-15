package ru.iteco.fmhandroid.ui.steps;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.AboutScreen.aboutVersion;
import static ru.iteco.fmhandroid.ui.elements.AuthScreen.loginField;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.newsControlPanel;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsListContainer;
import static ru.iteco.fmhandroid.ui.elements.Navigation.aboutBackButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.authButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.logOutButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.mainMenu;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemAboutText;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemMainText;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemNewsText;
import static ru.iteco.fmhandroid.ui.elements.Navigation.ourMissionButton;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.allNewsCardsBlock;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.editNewsButton;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionList;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkViewIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButtonWithText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.waitForViewDisplayed;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import io.qameta.allure.kotlin.Allure;

public class NavSteps {
    public void openMainMenu() {
        step("Открытие главного меню");
        clickButton(mainMenu);
    }

    public void goToPage(int viewId) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String text = context.getString(viewId);
        step("Переход на страницу: '" + text + "'");
        clickButton(mainMenu);
        clickButtonWithText(viewId);
    }

    public void clickMenuItem(int menuItem) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String text = context.getString(menuItem);
        step("Клик по пункту меню '" + text + "'");
        clickButtonWithText(menuItem);
    }

    public void isMainPageVisible() {
        step("Открытие главной страницы");
        checkViewIsDisplayed(newsListContainer);
    }

    public void isNewsPageVisible() {
        step("Открытие страницы 'НОВОСТИ'");
        checkViewIsDisplayed(allNewsCardsBlock);
    }

    public void isControlPanelVisible() {
        step("Страница 'ПАНЕЛЬ УПРАВЛЕНИЯ' отображается");
        checkViewIsDisplayed(newsControlPanel);
    }

    public void isAboutPageVisible() {
        step("Открытие страницы 'О ПРИЛОЖЕНИИ'");
        checkViewIsDisplayed(aboutVersion);
    }

    public void isOurMissionPageVisible() {
        step("Открытие страницы 'ЦИТАТЫ'");
        checkViewIsDisplayed(ourMissionList);
    }

    public void clickMainMenuItem() {
        clickMenuItem(menuItemMainText);
    }

    public void clickNewsMenuItem() {
        clickMenuItem(menuItemNewsText);
    }

    public void clickAboutMenuItem() {
        clickMenuItem(menuItemAboutText);
    }

    public void goToNewsPage() {
        goToPage(menuItemNewsText);
    }

    public void goToOurMissionPage() {
        step("Переход на страницу: 'ЦИТАТЫ'");
        clickButton(ourMissionButton);
    }

    public void goToControlPanel() {
        step("Переход на страницу 'ПАНЕЛЬ УПРАВЛЕНИЯ'");
        clickButton(editNewsButton);
    }

    public void returnFromAboutPage() {
        clickButton(aboutBackButton);
    }

    public void checkOpenMainPage() {
        openMainMenu();
        clickMainMenuItem();
        isMainPageVisible();
    }

    public void checkOpenNewsPage() {
        openMainMenu();
        clickNewsMenuItem();
        isNewsPageVisible();
    }

    public void checkOpenAboutPage() {
        openMainMenu();
        clickAboutMenuItem();
        isAboutPageVisible();
        returnFromAboutPage();
    }

    public void checkOpenOurMissionPage() {
        goToOurMissionPage();
        isOurMissionPageVisible();
    }

    public void clickLogoutButton() {
        step("Нажатие на кнопку 'ВЫХОД'");
        clickButton(authButton);
        clickButtonWithText(logOutButton);
    }

    public void isAuthScreenVisible() {
        Allure.step("Открытие страницы авторизации");
        waitForViewDisplayed(loginField, 6000);
        checkViewIsDisplayed(loginField);
    }
}
