package ru.iteco.fmhandroid.ui.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.helper.UIActions.scrollToAndCheckTextIsDisplayed;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import ru.iteco.fmhandroid.ui.AppActivity;

public class RecyclerViewHelper {

    public static void checkRecyclerViewHasMinItems(int recyclerViewId, int childCount) {
        onView(withId(recyclerViewId)).check(matches(hasMinimumChildCount(childCount)));
    }

    public static boolean isRecyclerViewNotEmpty(int recyclerViewId) {
        try {
            checkRecyclerViewHasMinItems(recyclerViewId, 1);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static boolean isRecyclerViewHasTwoItem(int recyclerViewId) {
        try {
            checkRecyclerViewHasMinItems(recyclerViewId, 2);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static boolean isRecyclerViewEmpty(int recyclerViewId) {
        try {
            onView(withId(recyclerViewId)).check(matches(not(hasDescendant(isDisplayed()))));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static int getRandomItemPosition(int recyclerViewId) {
        final int[] randomPosition = {-1};
        onView(allOf(withId(recyclerViewId), isDisplayed())).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "Get position of a random item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                Random random = new Random();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (itemCount > 0) {
                    randomPosition[0] = random.nextInt(itemCount);
                }
            }
        });
        return randomPosition[0];
    }

    public static int getPosition(ActivityScenarioRule<AppActivity> mActivityScenarioRule, int recyclerViewId, int textViewId, String text) {
        AtomicReference<Integer> position = new AtomicReference<>(-1);
        scrollToAndCheckTextIsDisplayed(recyclerViewId, textViewId, text);
        mActivityScenarioRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(recyclerViewId);
            if (recyclerView != null && recyclerView.getAdapter() != null) {
                for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
                    if (viewHolder != null) {
                        TextView titleTextView = viewHolder.itemView.findViewById(textViewId);
                        if (titleTextView != null && titleTextView.getText() != null) {
                            String title = titleTextView.getText().toString().trim(); // Remove leading/trailing spaces
                            if (title.equals(text)) {
                                position.set(i);
                                break;
                            }
                        }
                    }
                }
            }
        });
        return position.get();
    }

    public static String getTextAtPosition(int recyclerViewId, int position, int resourceId) {
        final String[] text = new String[1];

        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, new ViewAction() {
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
                        TextView textView = view.findViewById(resourceId);
                        if (textView != null) {
                            text[0] = textView.getText().toString();
                        }
                    }
                }));
        return text[0];
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

    public static void clickSelectedItemInBlock(int position, int viewId) {
        onView(allOf(withId(viewId), isDisplayed()))
                .perform(actionOnItemAtPosition(position, click()));
    }

    public static void clickButtonInSelectedItem(int recyclerViewId, int buttonViewId, int position) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, new ViewAction() {
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
                        View v = view.findViewById(buttonViewId);
                        if (v != null) {
                            v.performClick();
                        }
                    }
                }));
    }
}
