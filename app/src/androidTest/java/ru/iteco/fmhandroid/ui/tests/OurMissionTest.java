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
import ru.iteco.fmhandroid.ui.steps.NavSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class OurMissionTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final MainSteps mainSteps = new MainSteps();
    private final NavSteps navSteps = new NavSteps();
    private final OurMissionStep ourMissionStep = new OurMissionStep();

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
        navSteps.goToOurMissionPage();
    }

    @DisplayName("Отображение заголовка и блока цитат")
    @Feature("Цитаты")
    @Test
    public void testVisibilityOfOurMissionElements() {
        navSteps.isOurMissionPageVisible();
        ourMissionStep.isTitleOurMissionTextVisible();
    }

    @DisplayName("Разворачивание карточки цитаты")
    @Feature("Цитаты")
    @Test
    public void testExpandOurMissionItem() {
        int position = ourMissionStep.getRandomItemInOurMissionList();
        ourMissionStep.expandRandomItem(position);
        String ourMissionDescription = ourMissionStep.getActualDescription(position);
        ourMissionStep.checkDescriptionIsDisplay(ourMissionDescription, true);
    }


}
