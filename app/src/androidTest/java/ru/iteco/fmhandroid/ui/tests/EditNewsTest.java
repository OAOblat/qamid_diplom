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
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class EditNewsTest {
    private final MainSteps mainSteps = new MainSteps();
    private final NavSteps navSteps = new NavSteps();
    private final NewsSteps newsSteps = new NewsSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Before
    public void setUp() {
        TestData testData = new TestData();
        AuthHelper authHelper = new AuthHelper(testData);
        AuthSteps authSteps = new AuthSteps(authHelper);

        AuthHelper.User info = authHelper.authInfo();
        try {
            authSteps.authenticate(info);
        } catch (Exception ignored) {
        }
        mainSteps.waitForViewMainScreen();
        navSteps.goToNewsPage();
        navSteps.goToControlPanel();
        newsSteps.checkNewsHaveItemsOrAddNewsIfEmpty();
    }

    @DisplayName("Изменение статуса новости на НЕ АКТИВНА")
    @Feature("Редактирование новости")
    @Test
    public void testSwitchNewsInactive() {
        String title = newsSteps.addNewsWithAdjustedDate(0);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        newsSteps.openEditNewsPage(position);
        newsSteps.switchToNotActive();
        newsSteps.submitChange();
        String actualStatus = newsSteps.getActualStatus(position);
        newsSteps.checkStatus(actualStatus, false);
    }

    @DisplayName("Изменение статуса новости на АКТИВНА")
    @Feature("Редактирование новости")
    @Test
    public void testSwitchNewsActive() {
        String title = newsSteps.addNewsWithAdjustedDate(0);
        int position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        newsSteps.openEditNewsPage(position);
        newsSteps.switchToNotActive();
        newsSteps.submitChange();
        String actualStatus = newsSteps.getActualStatus(position);
        newsSteps.checkStatus(actualStatus, false);
        newsSteps.openEditNewsPage(position);
        newsSteps.switchToActive();
        newsSteps.submitChange();
        actualStatus = newsSteps.getActualStatus(position);
        newsSteps.checkStatus(actualStatus, true);
    }

    @DisplayName("Изменение заголовка новости")
    @Feature("Редактирование новости")
    @Test
    public void testChangeTitleInNews() {
        int position = newsSteps.getRandomItemInNews();
        String initialTitle = newsSteps.getActualTitle(position);
        newsSteps.openEditNewsPage(position);
        String expectedTitle = newsSteps.editTitleInRandomNews();
        newsSteps.submitChange();
        String actualTitle = newsSteps.getActualTitle(position);

        newsSteps.checkSubmitChange(expectedTitle, actualTitle, initialTitle, true);
    }

    @DisplayName("Изменение даты новости")
    @Feature("Редактирование новости")
    @Test
    public void testChangeDateInNews() {
        int position = newsSteps.getRandomItemInNews();
        String title = newsSteps.getActualTitle(position);
        String initialDate = newsSteps.getActualDate(position);
        newsSteps.openEditNewsPage(position);
        String expectedDate = newsSteps.editDateInRandomNews(4);
        newsSteps.submitChange();
        position = newsSteps.getNewsPosition(mActivityScenarioRule, title);
        String actualDate = newsSteps.getActualDate(position);

        newsSteps.checkSubmitChange(expectedDate, actualDate, initialDate, true);
    }

    @DisplayName("Отмена изменения заголовка новости")
    @Feature("Редактирование новости")
    @Test
    public void testCancelChangeTitleInNews() {
        int position = newsSteps.getRandomItemInNews();
        String initialTitle = newsSteps.getActualTitle(position);
        newsSteps.openEditNewsPage(position);
        String expectedTitle = newsSteps.editTitleInRandomNews();
        newsSteps.cancelChange();
        String actualTitle = newsSteps.getActualTitle(position);

        newsSteps.checkSubmitChange(expectedTitle, actualTitle, initialTitle, false);
    }

    @DisplayName("Отмена выхода из редактирования новости")
    @Feature("Редактирование новости")
    @Test
    public void testCancelingExitFromNewsEditing() {
        int position = newsSteps.getRandomItemInNews();
        newsSteps.openEditNewsPage(position);
        newsSteps.cancelingExitFromNewsEditing();

        newsSteps.isEditPageVisible();
    }

    @DisplayName("Изменение описания новости")
    @Feature("Редактирование новости")
    @Test
    public void testChangeDescriptionInNews() {
        int position = newsSteps.getRandomItemInNews();
        String initialTitle = newsSteps.getActualDescription(position);
        newsSteps.openEditNewsPage(position);
        String expectedTitle = newsSteps.editDescriptionInRandomNews();
        newsSteps.submitChange();
        String actualTitle = newsSteps.getActualDescription(position);

        newsSteps.checkSubmitChange(expectedTitle, actualTitle, initialTitle, true);
    }


}