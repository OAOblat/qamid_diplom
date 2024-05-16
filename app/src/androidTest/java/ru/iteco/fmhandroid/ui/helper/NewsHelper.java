package ru.iteco.fmhandroid.ui.helper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.addNewsButton;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.deleteNewsImage;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.newsControlPanel;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.retryButton;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.buttonOk;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.categoryField;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.descriptionField;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.hourField;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.minuteField;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.publishDate;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.publishTime;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.saveButton;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.timepickerTextInputModeDescription;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.titleField;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsListRecyclerView;
import static ru.iteco.fmhandroid.ui.elements.Navigation.mainMenu;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemNewsText;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.editNewsButton;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.clickButtonInSelectedItem;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.clickChildViewWithId;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.clickSelectedItemInBlock;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getRandomItemPosition;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.isRecyclerViewEmpty;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButtonWithText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.inputText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.waitForViewDisplayed;
import static ru.iteco.fmhandroid.ui.helper.WaitingUtil.sleep;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matchers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import io.bloco.faker.Faker;
import ru.iteco.fmhandroid.ui.elements.ControlPanel;

public class NewsHelper {
    Faker faker = new Faker();

    public String getAdjustedTime(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public String getAdjustedDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    public String getRandomCategory() {
        String[] categories = {
                "Объявление",
                "День рождения",
                "Зарплата",
                "Профсоюз",
                "Праздник",
                "Массаж",
                "Благодарность",
                "Нужна помощь"
        };

        int randomIndex = new Random().nextInt(categories.length);
        return categories[randomIndex];
    }

    public String getRandomTitle() {
        String randomWord = faker.lorem.word();
        int randomNumber = new Random().nextInt(100); // Генерация случайного числа от 0 до 99
        return "Новое событие " + randomWord + " " + randomNumber;
    }

    public String getIncorrectCategory() {
        return faker.lorem.word();
    }

    public String getRandomDescription() {
        String randomWord = faker.lorem.word();
        int randomNumber = new Random().nextInt(100); // Генерация случайного числа от 0 до 99
        return "Описание " + randomWord + " " + randomNumber;
    }

    public void addNews(String category, String title, String description, int days, int hours) {
        String adjustedDate = getAdjustedDate(days);
        String adjustedTime = getAdjustedTime(hours);
        String[] parts = adjustedDate.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        String[] parts2 = adjustedTime.split(" ");
        String time = parts2[1];
        String[] timeParts = time.split(":");
        String hoursOfTime = timeParts[0];
        String minutesOfTime = timeParts[1];

        navigateToCreateNews();
        enterCategory(category);
        enterTitle(title);
        setPublishDate(year, month, day);
        setPublishTime(hoursOfTime, minutesOfTime);
        enterDescription(description);
        saveNews();
        waitForViewDisplayed(newsControlPanel, 3000);
    }

    public void setPeriod(int viewId, int days) {
        String adjustedDate = getAdjustedDate(days);

        String[] parts = adjustedDate.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        setPublishDatePeriod(viewId, year, month, day);
    }

    private void navigateToCreateNews() {
        clickButton(mainMenu);
        clickButtonWithText(menuItemNewsText);
        clickButton(editNewsButton);
        clickButton(addNewsButton);
    }

    public void enterCategory(String category) {
        inputText(categoryField, category);
    }

    public void enterTitle(String title) {
        inputText(titleField, title);
    }

    public void setPublishTime(String hours, String minutes) {
        clickButton(publishTime);
        clickButton(timepickerTextInputModeDescription);
        inputText(hourField, hours);
        inputText(minuteField, minutes);
        clickButton(buttonOk);
    }

    public void setPublishDate(String year, String month, String day) {
        clickButton(publishDate);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        clickButton(buttonOk);
    }

    private void setPublishDatePeriod(int viewId, String year, String month, String day) {
        clickButton(viewId);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        clickButton(buttonOk);
    }

    public void enterDescription(String description) {
        inputText(descriptionField, description);
    }

    public void saveNews() {
        clickButton(saveButton);
    }

    public int getRandomItemInNewsBlock() {
        return getRandomItemPosition(newsListRecyclerView);
    }

    public void clickButtonInSelectedNews(int viewId, int position) {
        clickButtonInSelectedItem(newsListRecyclerView, viewId, position);
    }

    public void clickSelectedItemInNewsBlock(int position) {
        clickSelectedItemInBlock(position, newsListRecyclerView);
    }

    public void clearNewsInRecyclerView() {
        boolean newsRemoved;
        do {
            newsRemoved = performActionToDeleteNews();
            if (!newsRemoved) {
                if (isRetryButtonDisplayed()) {
                    clickButton(retryButton);
                    newsRemoved = !isRecyclerViewEmpty(ControlPanel.newsListRecyclerView);
                } else {
                    break;
                }
            }
        } while (newsRemoved);
    }

    public boolean isRetryButtonDisplayed() {
        try {
            waitForViewDisplayed(retryButton, 1000);
            onView(withId(retryButton)).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean performActionToDeleteNews() {
        if (isRecyclerViewEmpty(newsListRecyclerView)) {
            return false;
        } else {
            onView(withId(newsListRecyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(deleteNewsImage)));
            sleep(200);
            clickButton(buttonOk);
            sleep(500);
            return true;
        }
    }
}
