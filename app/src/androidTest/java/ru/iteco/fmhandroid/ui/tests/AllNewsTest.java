package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.TestData;
import ru.iteco.fmhandroid.ui.helper.AuthHelper;
import ru.iteco.fmhandroid.ui.helper.SetupHelper;
import ru.iteco.fmhandroid.ui.helper.ToastHelper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.ToastSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AllNewsTest {
    private final MainSteps mainSteps = new MainSteps();
    private final NavSteps navSteps = new NavSteps();
    private final NewsSteps newsSteps = new NewsSteps();
    private ToastSteps toastSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Before
    public void setUp() {
        TestData testData = new TestData();
        AuthHelper authHelper = new AuthHelper(testData);
        AuthSteps authSteps = new AuthSteps(authHelper);

        AuthHelper.User info = authHelper.authInfo();
        SetupHelper setupHelper = new SetupHelper(mActivityScenarioRule);
        toastSteps = new ToastSteps(new ToastHelper(setupHelper.getDecorView()));
        try {
            authSteps.authenticate(info);
        } catch (Exception ignored) {
        }
        mainSteps.waitForViewMainScreen();
        navSteps.goToNewsPage();
        newsSteps.checkNewsHaveItemsOrAddNewsInAllNews();
    }

    @DisplayName("Сортировка новостей от новых к старым по умолчанию")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testNewsOrder() {
        navSteps.goToControlPanel();
        newsSteps.deleteNews();
        String olderNews = newsSteps.addNewsWithAdjustedTime(-1);
        String newerNews = newsSteps.addNewsWithAdjustedTime(0);
        navSteps.goToNewsPage();

        newsSteps.assertNewsOrderFromNewerToOlder(mActivityScenarioRule, olderNews, newerNews, true);
    }

    @DisplayName("Сортировка новостей от старых к новым после сортировки")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testNewsOrderAfterSorting() {
        navSteps.goToControlPanel();
        newsSteps.deleteNews();
        String olderNews = newsSteps.addNewsWithAdjustedTime(-1);
        String newerNews = newsSteps.addNewsWithAdjustedTime(0);
        navSteps.goToNewsPage();
        newsSteps.sortNews();

        newsSteps.assertNewsOrderFromNewerToOlder(mActivityScenarioRule, olderNews, newerNews, false);
    }

    @DisplayName("Отображение новости с верно выбранным периодом фильтрации")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testCheckNewsDisplayedAfterFilterPeriod() {
        String title = newsSteps.addNewsWithAdjustedDate(0);
        navSteps.goToNewsPage();
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(0);
        newsSteps.setPublishDateEndPeriod(0);
        newsSteps.filterSubmit();
        newsSteps.checkNewsIsDisplay(title, true);
    }

    @DisplayName("Ошибка фильтрации при неверно выбранном периоде")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testGetErrorMessageIfSetIncorrectPeriod() {
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(0);
        newsSteps.setPublishDateEndPeriod(-1);
        newsSteps.filterSubmit();
        toastSteps.checkIncorrectPeriod();
    }

    @DisplayName("Отсутствие отображения новости при фильтрации с другой датой")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testCheckNewsNotDisplayedAfterFilterWithAnotherPeriod() {
        String title = newsSteps.addNewsWithAdjustedDate(0);
        navSteps.goToNewsPage();
        newsSteps.checkNewsIsDisplay(title, true);
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(1);
        newsSteps.setPublishDateEndPeriod(1);
        newsSteps.filterSubmit();
        newsSteps.checkNewsIsDisplay(title, false);
    }

    @DisplayName("Разворачивание карточки новости в блоке")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testExpandNewsCardInAllNews() {
        int position = newsSteps.getRandomItemInNews();
        newsSteps.expandRandomNews(position);
        String newsDescription = newsSteps.getActualDescription(position);
        newsSteps.checkDescriptionIsDisplay(newsDescription, true);
    }

    @DisplayName("Переход в ПАНЕЛЬ УПРАВЛЕНИЯ")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testGoToControlPanelPageFromAllNewsPage() {
        navSteps.goToControlPanel();
        navSteps.isControlPanelVisible();
    }

    @DisplayName("Отсутствие новости в списке с датой из будущего")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testNotDisplayNewsWithTomorrowDate() {
        String titleNews = newsSteps.addNewsWithAdjustedDate(1);
        navSteps.goToNewsPage();
        newsSteps.checkNewsIsDisplay(titleNews, false);
    }

    @DisplayName("Удаление из списка новости в которой дата изменилась на дату из будущего")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testDeleteNewsWhenDateInNewsChangedOnFutureDate() {
        int position = newsSteps.getRandomItemInNews();
        String title = newsSteps.getActualTitle(position);
        navSteps.goToControlPanel();
        position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        newsSteps.openEditNewsPage(position);
        newsSteps.editDateInRandomNews(1);
        newsSteps.submitChange();
        navSteps.goToNewsPage();

        newsSteps.checkNewsIsDisplay(title, false);
    }

    @DisplayName("Отсутствие неактивной новости в списке")
    @Feature("Страница ВСЕ НОВОСТИ")
    @Test
    public void testNewsNotDisplayWithStatusInactive() {
        String title = newsSteps.addNewsWithAdjustedDate(0);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        newsSteps.openEditNewsPage(position);
        newsSteps.switchToNotActive();
        newsSteps.submitChange();
        navSteps.goToNewsPage();
        newsSteps.checkNewsIsDisplay(title, false);
    }
}
