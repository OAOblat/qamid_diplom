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
import ru.iteco.fmhandroid.ui.helper.AuthHelper;
import ru.iteco.fmhandroid.ui.helper.NewsHelper;
import ru.iteco.fmhandroid.ui.helper.SetupHelper;
import ru.iteco.fmhandroid.ui.helper.ToastHelper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.ToastSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ControlPanelTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final MainSteps mainSteps = new MainSteps();
    private final NavSteps navSteps = new NavSteps();
    private final NewsSteps newsSteps = new NewsSteps();
    private ToastSteps toastSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Before
    public void setUp() {
        AuthHelper.User info = AuthHelper.authInfo();
        SetupHelper setupHelper = new SetupHelper(mActivityScenarioRule);
        toastSteps = new ToastSteps(new ToastHelper(setupHelper.getDecorView()));
        try {
            authSteps.authenticate(info);
        } catch (Exception ignored) {
        }
        mainSteps.waitForViewMainScreen();
        navSteps.goToNewsPage();
        navSteps.goToControlPanel();
        NewsHelper.clearNewsList();
        newsSteps.checkNewsHaveItemsOrAddNewsIfEmpty();

    }

    @DisplayName("Сортировка новостей от новых к старым по умолчанию")
    @Feature("Панель управления")
    @Test
    public void testNewsOrderInControlPanel() {
        newsSteps.deleteNews();
        String olderNews = newsSteps.addNewsWithAdjustedDate(0);
        String newerNews = newsSteps.addNewsWithAdjustedDate(1);

        newsSteps.assertNewsOrderFromNewerToOlder(mActivityScenarioRule, olderNews, newerNews, true);
    }

    @DisplayName("Сортировка новостей от старых к новым после сортировки")
    @Feature("Панель управления")
    @Test
    public void testNewsOrderInControlPanelAfterSorting() {
        newsSteps.deleteNews();
        String olderNews = newsSteps.addNewsWithAdjustedDate(0);
        String newerNews = newsSteps.addNewsWithAdjustedDate(1);
        newsSteps.sortNews();

        newsSteps.assertNewsOrderFromNewerToOlder(mActivityScenarioRule, olderNews, newerNews, false);
    }

    @DisplayName("Отображение новости с верно выбранным периодом фильтрации")
    @Feature("Панель управления")
    @Test
    public void testCheckNewsDisplayedAfterCorrectFilterPeriod() {
        newsSteps.deleteNews();
        String yesterdayNews = newsSteps.addNewsWithAdjustedDate(-1);
        String todayNews = newsSteps.addNewsWithAdjustedDate(0);
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(-1);
        newsSteps.setPublishDateEndPeriod(-1);
        newsSteps.filterSubmit();
        newsSteps.checkNewsIsDisplay(yesterdayNews, true);
        newsSteps.checkNewsIsDisplay(todayNews, false);
    }

    @DisplayName("Отсутствие отображения новости при фильтрации с другой датой")
    @Feature("Панель управления")
    @Test
    public void testCheckNewsNotDisplayedAfterFilterAnotherPeriod() {
        String yesterdayNews = newsSteps.addNewsWithAdjustedDate(-1);
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(0);
        newsSteps.setPublishDateEndPeriod(0);
        newsSteps.filterSubmit();
        newsSteps.checkNewsIsDisplay(yesterdayNews, false);
    }

    @DisplayName("Ошибка фильтрации при неверно выбранном периоде")
    @Feature("Панель управления")
    @Test
    public void testGetErrorMessageIfSetIncorrectPeriod() {
        newsSteps.filterNews();
        newsSteps.setPublishDateStartPeriod(0);
        newsSteps.setPublishDateEndPeriod(-1);
        newsSteps.filterSubmit();
        toastSteps.checkIncorrectPeriod();
    }

    @DisplayName("Фильтрация новости по статусу АКТИВНА")
    @Feature("Панель управления")
    @Test
    public void testFilterNewsByStatusActive() {
        String activeNews = newsSteps.addNewsWithAdjustedDate(0);
        newsSteps.filterNews();
        newsSteps.uncheckInactiveCheckBox();
        newsSteps.filterSubmit();

        newsSteps.checkNewsIsDisplay(activeNews, true);
    }

    @DisplayName("Фильтрация новости по статусу НЕ АКТИВНА")
    @Feature("Панель управления")
    @Test
    public void testFilterNewsByStatusInactive() {
        String inactiveNews = newsSteps.addNewsWithAdjustedDate(0);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, inactiveNews);
        newsSteps.changeStatusToNotActiveInNews(position);
        newsSteps.filterNews();
        newsSteps.uncheckActiveCheckBox();
        newsSteps.filterSubmit();

        newsSteps.checkNewsIsDisplay(inactiveNews, true);
    }

    @DisplayName("Отсутствие неактивной новости при фильтре АКТИВНА")
    @Feature("Панель управления")
    @Test
    public void testNotDisplayNewsWhenFilterByStatusActive() {
        String inactiveNews = newsSteps.addNewsWithAdjustedDate(0);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, inactiveNews);
        newsSteps.changeStatusToNotActiveInNews(position);
        newsSteps.filterNews();
        newsSteps.uncheckInactiveCheckBox();
        newsSteps.filterSubmit();

        newsSteps.checkNewsIsDisplay(inactiveNews, false);
    }

    @DisplayName("Отсутствие активной новости при фильтре НЕ АКТИВНА")
    @Feature("Панель управления")
    @Test
    public void testNotDisplayNewsWhenFilterByStatusInactive() {
        String activeNews = newsSteps.addNewsWithAdjustedDate(0);
        newsSteps.filterNews();
        newsSteps.uncheckActiveCheckBox();
        newsSteps.filterSubmit();

        newsSteps.checkNewsIsDisplay(activeNews, false);
    }

    @DisplayName("Разворачивание карточки новости в блоке")
    @Feature("Панель управления")
    @Test
    public void testExpandNewsCardInControlPanel() {
        int position = newsSteps.getRandomItemInNews();
        newsSteps.expandRandomNews(position);
        String newsDescription = newsSteps.getActualDescription(position);
        newsSteps.checkDescriptionIsDisplay(newsDescription, true);
    }

    @DisplayName("Удаление новости")
    @Feature("Панель управления")
    @Test
    public void testDeleteNews() {
        int position = newsSteps.getRandomItemInNews();
        String title = newsSteps.getActualTitle(position);
        newsSteps.deleteRandomNews(position);
        newsSteps.checkNewsIsDisplay(title, false);
    }

    @DisplayName("Отмена удаления новости")
    @Feature("Панель управления")
    @Test
    public void testCancelDeleteNews() {
        int position = newsSteps.getRandomItemInNews();
        String title = newsSteps.getActualTitle(position);
        newsSteps.cancelDeleteNewsInRandomNews(position);
        newsSteps.checkNewsIsDisplay(title, true);
    }

    @DisplayName("Открытие страницы редактирования новости")
    @Feature("Панель управления")
    @Test
    public void testOpenEditNewsPage() {
        int position = newsSteps.getRandomItemInNews();
        newsSteps.openEditNewsPage(position);
        newsSteps.isEditPageVisible();
    }

    @DisplayName("Переход на страницу создания новой новости")
    @Feature("Панель управления")
    @Test
    public void testGoToCreateNewsPageFromControlPanel() {
        newsSteps.openCreateNewsPage();
        newsSteps.isCreateNewsPageVisible();
    }

}