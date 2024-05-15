package ru.iteco.fmhandroid.ui.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.helper.WaitingUtil.waitDisplayed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class UIActions {
    public static void enterText(int viewId, String text) {
        onView(allOf(isDescendantOfA(withId(viewId)), isAssignableFrom(EditText.class)))
                .check(matches(isDisplayed()))
                .perform(click(), replaceText(text), closeSoftKeyboard());
    }

    public static void inputText(int viewId, String text) {
        onView(withId(viewId))
                .check(matches(isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public static void inputText(ViewInteraction viewInteraction, String text) {
        viewInteraction.check(matches(isDisplayed()));
        viewInteraction.perform(replaceText(text), closeSoftKeyboard());
    }

    public static void clickButton(int viewId) {
        onView(withId(viewId))
                .check(matches(isEnabled()))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static void clickButton(ViewInteraction viewInteraction) {
        viewInteraction
                .check(matches(isEnabled()))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static void clickButtonWithText(int buttonTextId) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String buttonText = context.getString(buttonTextId);
        try {
            onView(withText(buttonText))
                    .check(matches(isDisplayed()))
                    .check(matches(isTextColor(-570425344)))
                    .perform(click());
        } catch (AssertionError e) {
            throw new AssertionError("Не удалось нажать на кнопку с текстом: <" + buttonText + "> - кнопка не кликабельна", e);
        }
    }

    public static Matcher<View> isTextColor(int expectedColor) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("text color is " + expectedColor);
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                int currentColor = textView.getCurrentTextColor();
                return currentColor == expectedColor;
            }
        };
    }

    public static void waitForViewDisplayed(int viewId, long timeoutMillis) {
        onView(isRoot()).perform(waitDisplayed(viewId, timeoutMillis));
    }

    public static boolean isViewDisplayedAfterAction(ViewAction action, boolean expectedResult) {
        try {
            onView(isRoot()).perform(action);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void checkViewIsDisplayed(int viewId) {
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    public static void checkTextIsDisplayed(int viewId) {
        onView(withText(viewId)).check(matches(isDisplayed()));
    }

    public static boolean checkTextIsVisible(int viewId) {
        onView(withText(viewId)).check(matches(isDisplayed()));
        return true;
    }

    public static boolean scrollToAndCheckTextIsDisplayed(int recyclerViewId, int textViewId, String text) {
        try {
            onView(withId(recyclerViewId))
                    .perform(actionOnItem(hasDescendant(allOf(withId(textViewId), withText(text))), scrollTo()))
                    .check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void checkViewIsNotDisplayed(int viewId) {
        onView(withId(viewId)).check(matches(not(isDisplayed())));
    }

    public static void checkParentHasDescendant(int parentId, int childId) {
        onView(allOf(withId(childId), isDescendantOfA(withId(parentId)))).check(matches(isDisplayed()));
    }

    public static boolean isViewVisible(int viewID) {
        try {
            onView(withId(viewID)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException | AssertionError e) {
            return false;
        }
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

