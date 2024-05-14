package ru.iteco.fmhandroid.ui.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;
import static io.qameta.allure.kotlin.Allure.step;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.platform.app.InstrumentationRegistry;


public class ToastHelper {
    private final View decorView;

    public ToastHelper(View decorView) {
        this.decorView = decorView;
    }

    public void checkToastMessageText(int expectedMessageResId) {
        if (decorView == null) {
            throw new IllegalStateException("DecorView is not set. Call setDecorView() before using checkToastMessageText()");
        }
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String expectedMessage = context.getString(expectedMessageResId);
        try {
            step("Проверка на соответствие текста всплывающего сообщения. Ожидаемое сообщение: <" + expectedMessage + ">");
            onView(withText(expectedMessage))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            fail("Ожидаемое сообщение не соответствует фактическому. " + e);
        }
    }
}


