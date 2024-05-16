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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;
import ru.iteco.fmhandroid.ui.elements.ControlPanel;

public class NewsHelper {
    public static String getRandomCategory() {
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

    public static String getRandomTitle() {
        Faker faker = new Faker();
        String randomWord = faker.lorem.word();
        int randomNumber = new Random().nextInt(100); // Генерация случайного числа от 0 до 99
        return "Новое событие " + randomWord + " " + randomNumber;
    }

    public static String getIncorrectCategory() {
        Faker faker = new Faker();
        return faker.lorem.word();
    }

    public static String getRandomDescription() {
        Faker faker = new Faker();
        String randomWord = faker.lorem.word();
        int randomNumber = new Random().nextInt(100); // Генерация случайного числа от 0 до 99
        return "Описание " + randomWord + " " + randomNumber;
    }

    private static final List<News> newsList = new ArrayList<>();

    public static News addNews(String category, String title, String description, int days, int hours) {
        String adjustedDate = DateTimeHelper.getAdjustedDate(days);
        String adjustedTime = DateTimeHelper.getAdjustedTime(hours);
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

        News news = new News(title, category, description);
        newsList.add(news);
        return news;
    }

    public static void setPeriod(int viewId, int days) {
        String adjustedDate = DateTimeHelper.getAdjustedDate(days);

        String[] parts = adjustedDate.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        setPublishDatePeriod(viewId, year, month, day);
    }

    private static void navigateToCreateNews() {
        clickButton(mainMenu);
        clickButtonWithText(menuItemNewsText);
        clickButton(editNewsButton);
        clickButton(addNewsButton);
    }

    public static void enterCategory(String category) {
        inputText(categoryField, category);
    }

    public static void enterTitle(String title) {
        inputText(titleField, title);
    }

    public static void setPublishTime(String hours, String minutes) {
        clickButton(publishTime);
        clickButton(timepickerTextInputModeDescription);
        inputText(hourField, hours);
        inputText(minuteField, minutes);
        clickButton(buttonOk);
    }

    public static void setPublishDate(String year, String month, String day) {
        clickButton(publishDate);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        clickButton(buttonOk);
    }

    private static void setPublishDatePeriod(int viewId, String year, String month, String day) {
        clickButton(viewId);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        clickButton(buttonOk);
    }

    public static void enterDescription(String description) {
        inputText(descriptionField, description);
    }

    public static void saveNews() {
        clickButton(saveButton);
    }

    public static List<News> getNewsList() {
        return newsList;
    }

    public static void clearNewsList() {
        newsList.clear();
    }

    public static class News {
        private final String title;
        private final String category;
        private final String description;

        public News(String title, String category, String description) {
            this.title = title;
            this.category = category;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }
    }

    public static int getRandomItemInNewsBlock() {
        return getRandomItemPosition(newsListRecyclerView);
    }

    public static void clickButtonInSelectedNews(int viewId, int position) {
        clickButtonInSelectedItem(newsListRecyclerView, viewId, position);
    }

    public static void clickSelectedItemInNewsBlock(int position) {
        clickSelectedItemInBlock(position, newsListRecyclerView);
    }

    public static void clearNewsInRecyclerView() {
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

    public static boolean isRetryButtonDisplayed() {
        try {
            waitForViewDisplayed(retryButton, 1000);
            onView(withId(retryButton)).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean performActionToDeleteNews() {
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
