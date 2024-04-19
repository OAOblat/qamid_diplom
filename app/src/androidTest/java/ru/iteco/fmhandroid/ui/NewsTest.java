package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.AuthSteps.enterLoginAndPasswordAndClickSignInButton;
import static ru.iteco.fmhandroid.ui.Helper.assertViewDisplayed;
import static ru.iteco.fmhandroid.ui.Helper.clickButton;
import static ru.iteco.fmhandroid.ui.TestConfig.VALID_LOGIN;
import static ru.iteco.fmhandroid.ui.TestConfig.VALID_PASSWORD;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Test
    public void testRecyclerViewItemCount() {
        enterLoginAndPasswordAndClickSignInButton(VALID_LOGIN, VALID_PASSWORD);
        assertViewDisplayed(R.id.all_news_text_view);
        clickButton(R.id.all_news_text_view);
        assertViewDisplayed(R.id.edit_news_material_button);
        clickButton(R.id.edit_news_material_button);
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(CustomViewMatcher.recyclerViewSizeMatcher(10)))
                .check(CustomViewAssertion.isRecyclerView())
                .check(matches(isDisplayed()));
    }
}
