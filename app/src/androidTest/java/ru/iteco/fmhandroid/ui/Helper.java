package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.WaitingUtil.waitDisplayed;

import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matchers;

public class Helper {
    static void enterText(int viewId, String text) {
        onView(allOf(isDescendantOfA(withId(viewId)), isAssignableFrom(EditText.class)))
                .check(matches(isDisplayed()))
                .perform(click(), replaceText(text), closeSoftKeyboard());
    }

    static void clickButton(int viewId) {
        onView(withId(viewId)).perform(click());
    }

    public static void assertViewDisplayed(int viewId) {
        onView(isRoot()).perform(waitDisplayed(viewId, 2000));
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    public static void checkPopupMessageText(String expectedMessage, final View decorView) {
        step("Ожидаемое сообщение: " + expectedMessage);
        onView(withText(expectedMessage))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }
}

