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
import ru.iteco.fmhandroid.ui.helper.AuthHelper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NavTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final MainSteps mainSteps = new MainSteps();
    private final NavSteps navSteps = new NavSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        AuthHelper.User info = AuthHelper.authInfo();
        try {
            authSteps.authenticate(info);
            mainSteps.waitForViewMainScreen();
        } catch (Exception ignored) {
        }
    }

    @DisplayName("Переход на страницу 'НОВОСТИ' с главной страницы")
    @Feature("Навигация")
    @Story("C главной страницы")
    @Test
    public void testNewsPageOpeningFromHomePage() {
        navSteps.checkOpenNewsPage();
    }

    @DisplayName("Переход на страницу 'О ПРИЛОЖЕНИИ' с главной страницы")
    @Feature("Навигация")
    @Story("C главной страницы")
    @Test
    public void testAboutPageOpeningFromHomePage() {
        navSteps.checkOpenAboutPage();
    }

    @DisplayName("Переход на страницу 'ЦИТАТЫ' с главной страницы")
    @Feature("Навигация")
    @Story("C главной страницы")
    @Test
    public void testOurMissionPageOpeningFromHomePage() {
        navSteps.checkOpenOurMissionPage();
    }

    @DisplayName("Переход на главную со страницы 'НОВОСТИ'")
    @Feature("Навигация")
    @Story("Cо страницы 'НОВОСТИ'")
    @Test
    public void testMainPageOpeningFromNewsPage() {
        navSteps.goToNewsPage();
        navSteps.checkOpenMainPage();
    }

    @DisplayName("Переход на страницу 'О ПРИЛОЖЕНИИ' со страницы 'НОВОСТИ'")
    @Feature("Навигация")
    @Story("Cо страницы 'НОВОСТИ'")
    @Test
    public void testAboutPageOpeningFromNewsPage() {
        navSteps.goToNewsPage();
        navSteps.checkOpenAboutPage();
    }

    @DisplayName("Переход на страницу 'ЦИТАТЫ' со страницы 'НОВОСТИ'")
    @Feature("Навигация")
    @Story("Cо страницы 'НОВОСТИ'")
    @Test
    public void testOurMissionPageOpeningFromNewsPage() {
        navSteps.goToNewsPage();
        navSteps.checkOpenOurMissionPage();
    }

    @DisplayName("Переход на главную со страницы 'ЦИТАТЫ'")
    @Feature("Навигация")
    @Story("Cо страницы 'ЦИТАТЫ'")
    @Test
    public void testMainPageOpeningFromOurMissionPage() {
        navSteps.goToOurMissionPage();
        navSteps.checkOpenMainPage();
    }

    @DisplayName("Переход на страницу 'НОВОСТИ' со страницы 'ЦИТАТЫ'")
    @Feature("Навигация")
    @Story("Cо страницы 'ЦИТАТЫ'")
    @Test
    public void testNewsPageOpeningFromOurMissionPage() {
        navSteps.goToOurMissionPage();
        navSteps.checkOpenNewsPage();
    }

    @DisplayName("Переход на страницу 'О ПРИЛОЖЕНИИ' со страницы 'ЦИТАТЫ'")
    @Feature("Навигация")
    @Story("Cо страницы 'ЦИТАТЫ'")
    @Test
    public void testAboutPageOpeningFromOurMissionPage() {
        navSteps.goToOurMissionPage();
        navSteps.checkOpenAboutPage();
    }

    @DisplayName("Выход из системы по кнопке ВЫХОД в разделе Личный кабинет")
    @Feature("Навигация")
    @Story("C главной страницы")
    @Test
    public void testLogOut() {
        navSteps.clickLogoutButton();
        navSteps.isAuthScreenVisible();
    }
}
