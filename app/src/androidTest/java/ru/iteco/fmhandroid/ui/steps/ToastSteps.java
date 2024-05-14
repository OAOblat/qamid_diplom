package ru.iteco.fmhandroid.ui.steps;

import static org.junit.Assert.fail;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.filterNewsTitle;
import static ru.iteco.fmhandroid.ui.elements.ToastMessage.emptyFields;
import static ru.iteco.fmhandroid.ui.elements.ToastMessage.emptyLoginOrPassword;
import static ru.iteco.fmhandroid.ui.elements.ToastMessage.errorSaving;
import static ru.iteco.fmhandroid.ui.elements.ToastMessage.wrongLoginOrPassword;
import static ru.iteco.fmhandroid.ui.elements.ToastMessage.wrongNewsDatePeriod;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkTextIsVisible;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import ru.iteco.fmhandroid.ui.helper.ToastHelper;

public class ToastSteps {
    private final ToastHelper toastHelper;

    public ToastSteps(ToastHelper toastHelper) {
        this.toastHelper = toastHelper;
    }

    public void checkWrongLoginOrPassword() {
        toastHelper.checkToastMessageText(wrongLoginOrPassword);
    }

    public void checkEmptyLoginOrPassword() {
        toastHelper.checkToastMessageText(emptyLoginOrPassword);
    }

    public void checkEmptyFields() {
        toastHelper.checkToastMessageText(emptyFields);
    }

    public void checkErrorSaving() {
        toastHelper.checkToastMessageText(errorSaving);
    }

    public void checkIncorrectPeriod() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String expectedMessage = context.getString(wrongNewsDatePeriod);
        try {
            checkTextIsVisible(filterNewsTitle);
            toastHelper.checkToastMessageText(wrongNewsDatePeriod);
        } catch (Exception e) {
            fail("Всплывающее сообщение с текстом <" + expectedMessage + "> не появилось.");
        }
    }
}
