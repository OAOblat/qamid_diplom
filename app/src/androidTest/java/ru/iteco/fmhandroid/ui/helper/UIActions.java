package ru.iteco.fmhandroid.ui.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.helper.WaitingUtil.waitDisplayed;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;

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

    public static void clickButtonWithText(int buttonText) {
        onView(withText(buttonText))
                .check(matches(isEnabled()))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static class HasLinksMatcher {

        public static Matcher<View> hasLinks(boolean hasLinks) {
            return new BoundedMatcher<View, TextView>(TextView.class) {
                @Override
                public boolean matchesSafely(TextView textView) {
                    boolean textHasLinks = textView.getUrls().length > 0;
                    return textHasLinks == hasLinks;
                }

                @Override
                public void describeTo(Description description) {
                    if (hasLinks) {
                        description.appendText("a TextView with links");
                    } else {
                        description.appendText("a TextView without links");
                    }
                }
            };
        }
    }

    public static void clickButtonWithText(String buttonText) {
        onView(allOf(withClassName(is("com.google.android.material.textview.MaterialTextView")), withText(buttonText)))
                .check(matches(isDisplayed()))
                .perform(click());
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

    public static void checkViewIsDisplayed(ViewInteraction viewInteraction) {
        viewInteraction.check(matches(isDisplayed()));
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

    public static void checkTextIsDisplayed(int viewId, String text) {
        onView(allOf(withId(viewId), withText(text)))
                .check(matches(isDisplayed()));
    }

    public static boolean scrollToAndCheckTextIsDisplayed(int recyclerViewId, int textViewId, String text) {
        boolean found = false;
        try {
            onView(withId(recyclerViewId))
                    .perform(actionOnItem(hasDescendant(allOf(withId(textViewId), withText(text))), scrollTo()))
                    .check(matches(isDisplayed()));

            found = true;
        } catch (Exception e) {
            found = false;
        }
        return found;
    }

    public static boolean scrollToAndCheckTextIsNotDisplayed(int recyclerViewId, int textViewId, String text) {
        boolean notFound = false;
        try {
            onView(withId(recyclerViewId))
                    .perform(actionOnItem(hasDescendant(allOf(withId(textViewId), withText(text))), scrollTo()))
                    .check(doesNotExist());
            notFound = true;
        } catch (Exception e) {
            notFound = false;
        }
        return notFound;
    }

    public static void checkViewIsNotDisplayed(int viewId) {
        onView(withId(viewId)).check(matches(not(isDisplayed())));
    }

    public static void checkParentHasDescendant(int parentId, int childId) {
        onView(allOf(withId(childId), isDescendantOfA(withId(parentId)))).check(matches(isDisplayed()));
    }

    public static void swipeDown(int viewId) {
        onView(withId(viewId)).check(matches(isDisplayed())).perform(ViewActions.swipeDown());
    }

    public static boolean isViewVisible(int viewID) {
        try {
            onView(withId(viewID)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException | AssertionError e) {
            return false;
        }
    }

    public static String getText(ViewInteraction matcher) {
        final String[] text = new String[1];
        ViewAction viewAction = new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "Text of the view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView textView = (TextView) view;
                text[0] = textView.getText().toString();
            }
        };

        matcher.perform(viewAction);
        return text[0];
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
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

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }

}

