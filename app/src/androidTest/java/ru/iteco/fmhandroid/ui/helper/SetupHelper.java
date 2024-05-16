package ru.iteco.fmhandroid.ui.helper;

import static ru.iteco.fmhandroid.ui.elements.Navigation.authButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.logOutButton;
import static ru.iteco.fmhandroid.ui.elements.Navigation.logo;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButtonWithText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.isViewVisible;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import ru.iteco.fmhandroid.ui.AppActivity;

public class SetupHelper {
    private View decorView;
    private final ActivityScenarioRule<AppActivity> activityScenarioRule;

    public SetupHelper(ActivityScenarioRule<AppActivity> activityScenarioRule) {
        this.activityScenarioRule = activityScenarioRule;
        initializeDecorView();
    }

    public void initializeDecorView() {
        ActivityScenario<AppActivity> activityScenario = activityScenarioRule.getScenario();
        activityScenario.onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    public View getDecorView() {
        return decorView;
    }

    public void logoutIfLogoVisible() {
        if (isViewVisible(logo)) {
            logout();
        }
    }

    public void logout() {
        clickButton(authButton);
        clickButtonWithText(logOutButton);
    }

}
