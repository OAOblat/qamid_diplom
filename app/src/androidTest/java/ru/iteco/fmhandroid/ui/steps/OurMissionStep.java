package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionDescription;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionList;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionTitle;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getRandomItemPosition;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getTextAtPosition;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.scrollToAndCheckTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.scrollToAndCheckTextIsNotDisplayed;

public class OurMissionStep {
    public void isTitleOurMissionTextVisible() {
        step("Отображение текста в заголовке: 'ГЛАВНОЕ - ЖИТЬ ЛЮБЯ'");
        checkTextIsDisplayed(ourMissionTitle);
    }

    public int getRandomItemInOurMissionList() {
        int position = getRandomItemPosition(ourMissionList);
        step("Получение рандомной позиции:" + position);
        return position;
    }

    public void expandRandomItem(int position) {
        step("Клик по цитате с позицией = " + position);
        clickSelectedItemInOurMission(position);
    }

    public static void clickSelectedItemInOurMission(int position) {
        onView(allOf(withId(ourMissionList), isDisplayed()))
                .perform(actionOnItemAtPosition(position, click()));
    }

    public String getActualDescription(int position) {
        String description = getTextAtPosition(ourMissionList, position, ourMissionDescription);
        step("Получение описания цитаты на позиции = " + position + ". Описание: " + description);
        return description;
    }

    public void checkDescriptionIsDisplay(String description, boolean expectedDisplayed) {
        if (expectedDisplayed) {
            step("Проверка, что описание цитаты: <" + description + "> отображается");
            scrollToAndCheckTextIsDisplayed(ourMissionList, ourMissionDescription, description);
        } else {
            step("Проверка, что описание цитаты: <" + description + "> не отображается");
            scrollToAndCheckTextIsNotDisplayed(ourMissionList, ourMissionDescription, description);
        }
    }
}

