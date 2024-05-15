package ru.iteco.fmhandroid.ui.steps;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionDescription;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionList;
import static ru.iteco.fmhandroid.ui.elements.OurMissionScreen.ourMissionTitle;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.clickSelectedItemInBlock;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getRandomItemPosition;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getTextAtPosition;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.scrollToAndCheckTextIsDisplayed;

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
        clickSelectedItemInBlock(position, ourMissionList);
    }

    public String getActualDescription(int position) {
        String description = getTextAtPosition(ourMissionList, position, ourMissionDescription);
        step("Получение описания цитаты на позиции = " + position + ". Описание: " + description);
        return description;
    }

    public void checkDescriptionIsDisplay(String description, boolean expectedDisplayed) {
        step("Проверка, что описание цитаты: <" + description + "> " + (expectedDisplayed ? "отображается" : "не отображается"));
        boolean found = scrollToAndCheckTextIsDisplayed(ourMissionList, ourMissionDescription, description);
        if (expectedDisplayed) {
            if (!found) {
                throw new AssertionError("Description \"" + description + "\" not found.");
            }
        } else {
            if (found) {
                throw new AssertionError("Description \"" + description + "\" is displayed, but it should not be.");
            }
        }
    }
}

