package ru.iteco.fmhandroid.ui.elements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.helper.UIActions.childAtPosition;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class CreateEditNews {
    public static final int categoryField = R.id.news_item_category_text_auto_complete_text_view;
    public static final int categoryLayout = R.id.news_item_category_text_input_layout;
    public static final int titleField = R.id.news_item_title_text_input_edit_text;
    public static final int titleLayout = R.id.news_item_title_text_input_layout;
    public static final int dateLayout = R.id.news_item_create_date_text_input_layout;
    public static final int timeLayout = R.id.news_item_publish_time_text_input_layout;
    public static final int descriptionLayout = R.id.news_item_description_text_input_layout;
    public static final int publishDate = R.id.news_item_publish_date_text_input_edit_text;
    public static final int buttonOk = android.R.id.button1;
    public static final int buttonCancel = android.R.id.button2;
    public static final int publishTime = R.id.news_item_publish_time_text_input_edit_text;
    public static final int descriptionField = R.id.news_item_description_text_input_edit_text;
    public static final int saveButton = R.id.save_button;
    public static final int cancelButton = R.id.cancel_button;
    public static final ViewInteraction timepickerTextInputModeDescription = onView(
            allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Чтобы ввести время, перейдите в режим ввода текста."),
                    childAtPosition(
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    4),
                            0),
                    isDisplayed()));;
    public static final ViewInteraction hourField = onView(
            allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                    childAtPosition(
                            allOf(withClassName(is("android.widget.RelativeLayout")),
                                    childAtPosition(
                                            withClassName(is("android.widget.TextInputTimePickerView")),
                                            1)),
                            0),
                    isDisplayed()));

    public static final ViewInteraction minuteField = onView(
            allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                    childAtPosition(
                            allOf(withClassName(is("android.widget.RelativeLayout")),
                                    childAtPosition(
                                            withClassName(is("android.widget.TextInputTimePickerView")),
                                            1)),
                            3),
                    isDisplayed()));

    public static final int editNewsTitle = R.string.editing;
    public static final int createNewsTitle = R.string.creating;
    public static ViewInteraction switcherToNotActive = onView(allOf(withId(R.id.switcher), withText(R.string.news_item_active)));
    public static ViewInteraction switcherToActive = onView(allOf(withId(R.id.switcher), withText(R.string.news_item_not_active)));
    public static final int checkBoxActive = R.id.filter_news_active_material_check_box;
    public static final int checkBoxInactive = R.id.filter_news_inactive_material_check_box;
    public static final int warningSignInStart = R.id.text_input_start_icon;
    public static final int warningSignInEnd = R.id.text_input_end_icon;
    public static final int cancellation = R.string.cancellation;
}
