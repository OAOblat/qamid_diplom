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
import ru.iteco.fmhandroid.ui.helper.SetupHelper;
import ru.iteco.fmhandroid.ui.helper.ToastHelper;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.ToastSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreateNewsTest {

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
        newsSteps.openCreateNewsPage();
    }

    @DisplayName("Успешное создание новой новости")
    @Feature("Страница создания новости")
    @Test
    public void testCreatingNewNews() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(true);
        newsSteps.checkNewsIsDisplay(title, true);
    }

    @DisplayName("Отмена создания новой новости")
    @Feature("Страница создания новости")
    @Test
    public void testCancelCreatingNewNews() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.cancelChange();
        newsSteps.isControlPanelDisplayedAfterAction(true);
        newsSteps.checkNewsIsDisplay(title, false);
    }

    @DisplayName("Ошибка сохранения новости при неверно введенной категории")
    @Feature("Страница создания новости")
    @Test
    public void testErrorSavingWithIncorrectCategory() {
        String category = newsSteps.getIncorrectCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkErrorSaving();
    }

    @DisplayName("Соответствие даты публикации новости введенной дате")
    @Feature("Страница создания новости")
    @Test
    public void testMatchingDateOfPublicationOfNews() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        String date = newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(true);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        String actualDate = newsSteps.getActualDate(position);
        newsSteps.assertEqualsString(date, actualDate);
    }

    @DisplayName("Соответствие даты создания новости текущей дате")
    @Feature("Страница создания новости")
    @Test
    public void testMatchingDateOfCreationOfNews() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        String date = newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(true);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        String actualDate = newsSteps.getCreationDate(position);
        newsSteps.assertEqualsString(date, actualDate);
    }

    @DisplayName("Ошибка создания новости при пустом значении категории")
    @Feature("Страница создания новости")
    @Test
    public void testErrorCreatingNewsItemWithEmptyCategory() {
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkEmptyFields();
        newsSteps.checkWarningSignVisibleInCategory();
    }

    @DisplayName("Ошибка создания новости при пустом значении заголовка")
    @Feature("Страница создания новости")
    @Test
    public void testErrorCreatingNewsItemWithEmptyTitle() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkEmptyFields();
        newsSteps.checkWarningSignVisibleInTitle();
    }

    @DisplayName("Ошибка создания новости при невведенной дате")
    @Feature("Страница создания новости")
    @Test
    public void testErrorCreatingNewsItemWithEmptyDate() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterTime(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkEmptyFields();
        newsSteps.checkWarningSignVisibleInDate();
    }

    @DisplayName("Ошибка создания новости при невведенном времени")
    @Feature("Страница создания новости")
    @Test
    public void testErrorCreatingNewsItemWithEmptyTime() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        String description = newsSteps.getRandomDescription();
        newsSteps.enterDescription(description);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkEmptyFields();
        newsSteps.checkWarningSignVisibleInTime();
    }

    @DisplayName("Ошибка создания новости при невведенном описании")
    @Feature("Страница создания новости")
    @Test
    public void testErrorCreatingNewsItemWithEmptyDescription() {
        String category = newsSteps.getRandomCategory();
        newsSteps.enterCategory(category);
        String title = newsSteps.getRandomTitle();
        newsSteps.enterTitle(title);
        newsSteps.enterDate(0);
        newsSteps.enterTime(0);
        newsSteps.saveNews();
        newsSteps.isControlPanelDisplayedAfterAction(false);
        toastSteps.checkEmptyFields();
        newsSteps.checkWarningSignVisibleInDescription();
    }
}