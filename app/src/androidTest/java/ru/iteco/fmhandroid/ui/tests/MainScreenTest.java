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
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final MainSteps mainSteps = new MainSteps();
    private final NewsSteps newsSteps = new NewsSteps();

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

    @DisplayName("Отображение блока новостей на главном экране")
    @Feature("Главная страница")
    @Test
    public void testVisibilityOfNewsContainerOnMainScreen() {
        mainSteps.isNewsListContainerVisible();
    }

    @DisplayName("Сворачивание и разворачивание блока новостей")
    @Feature("Главная страница")
    @Test
    public void testCollapseAndExpandNewsBlock() {
        mainSteps.isNewsBlockExpandedVisible();
        mainSteps.clickOnNewsExpandButton();
        mainSteps.isNewsBlockExpandedInvisible();
        mainSteps.clickOnNewsExpandButton();
        mainSteps.isNewsBlockExpandedVisible();
    }

    @DisplayName("Разворачивание карточки новости в блоке новостей")
    @Feature("Главная страница")
    @Test
    public void testExpandNewsCardInBlockNews() {
        mainSteps.checkNewsRecyclerViewForItemsOrAddNewsIfEmpty();
        int position = newsSteps.getRandomItemInNews();
        newsSteps.expandRandomNews(position);
        String newsDescription = newsSteps.getActualDescription(position);

        newsSteps.checkDescriptionIsDisplay(newsDescription, true);

    }

    @DisplayName("Переход на страницу новостей через 'Все новости' на главной")
    @Feature("Главная страница")
    @Test
    public void testGoToNewsPageFromHomePage() {
        mainSteps.goToNewsPageFromHomePage();
        mainSteps.isNewsPageVisible();
    }
}
