package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.addNewsButton;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.createDate;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.deleteNewsImage;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.editButton;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.publicationDate;
import static ru.iteco.fmhandroid.ui.elements.ControlPanel.statusText;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.buttonCancel;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.buttonOk;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.cancelButton;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.cancellation;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.categoryLayout;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.checkBoxActive;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.checkBoxInactive;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.createNewsTitle;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.dateLayout;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.descriptionLayout;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.editNewsTitle;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.saveButton;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.switcherToActive;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.switcherToNotActive;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.timeLayout;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.titleLayout;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.warningSignInEnd;
import static ru.iteco.fmhandroid.ui.elements.CreateEditNews.warningSignInStart;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsDescriptionTextView;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsListRecyclerView;
import static ru.iteco.fmhandroid.ui.elements.Navigation.mainMenu;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemNewsText;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.editNewsButton;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.filterNews;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.filterSubmit;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.newsTitle;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.publishDateEndLayout;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.publishDateStartLayout;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.sortNews;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.addNews;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.clearNewsInRecyclerView;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.clickButtonInSelectedNews;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.clickSelectedItemInNewsBlock;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.getRandomItemInNewsBlock;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.setPeriod;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getPosition;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.getTextAtPosition;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.isRecyclerViewHasTwoItem;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkParentHasDescendant;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButtonWithText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.isViewDisplayedAfterAction;
import static ru.iteco.fmhandroid.ui.helper.UIActions.scrollToAndCheckTextIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.waitForViewDisplayed;
import static ru.iteco.fmhandroid.ui.helper.WaitingUtil.waitDisplayed;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;

import java.util.List;

import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.elements.ControlPanel;
import ru.iteco.fmhandroid.ui.helper.DateTimeHelper;
import ru.iteco.fmhandroid.ui.helper.NewsHelper;

public class NewsSteps {

    public void sortNews() {
        step("Клик по кнопке 'СОРТИРОВКА'");
        clickButton(sortNews);
    }

    public String addNewsWithAdjustedTime(int hours) {
        String title = getRandomTitle();
        step("Создание новости в временем, отличной от текущей на: " + hours + " ч. Заголовок: <" + title + ">");
        addNews(NewsHelper.getRandomCategory(), title, NewsHelper.getRandomDescription(), 0, hours);
        return title;
    }

    public String addNewsWithAdjustedDate(int date) {
        String title = NewsHelper.getRandomTitle();
        step("Создание новости с датой, отличной от текущей на: " + date + " дн. Заголовок: <" + title + ">");
        addNews(NewsHelper.getRandomCategory(), title, NewsHelper.getRandomDescription(), date, 0);
        return title;
    }

    public String getRandomCategory() {
        return NewsHelper.getRandomCategory();
    }

    public String getIncorrectCategory() {
        return NewsHelper.getIncorrectCategory();
    }

    public String getRandomTitle() {
        return NewsHelper.getRandomTitle();
    }

    public String getRandomDescription() {
        return NewsHelper.getRandomDescription();
    }

    public void enterCategory(String category) {
        step("Ввод <" + category + "> в поле категории");
        NewsHelper.enterCategory(category);
    }

    public void enterTitle(String title) {
        step("Ввод <" + title + "> в поле заголовка");
        NewsHelper.enterTitle(title);
    }

    public String enterDate(int days) {
        step("Ввод даты отличной от текущей на " + days + " дн.");
        String adjustedDate = DateTimeHelper.getAdjustedDate(days);
        String[] parts = adjustedDate.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];
        NewsHelper.setPublishDate(year, month, day);
        return day + "." + month + "." + year;
    }

    public void enterTime(int hours) {
        step("Ввод времени отличной от текущей на " + hours + " ч.");
        String adjustedTime = DateTimeHelper.getAdjustedTime(hours);
        String[] parts2 = adjustedTime.split(" ");
        String time = parts2[1];
        String[] timeParts = time.split(":");
        String hoursOfTime = timeParts[0];
        String minutesOfTime = timeParts[1];
        NewsHelper.setPublishTime(hoursOfTime, minutesOfTime);
    }

    public void enterDescription(String description) {
        step("Ввод <" + description + "> в поле описания");
        NewsHelper.enterDescription(description);
    }

    public void saveNews() {
        step("Сохранение изменений");
        NewsHelper.saveNews();
    }

    public void isControlPanelDisplayedAfterAction(boolean expectedResult) {
        step(expectedResult ? "Открытие панели управления" : "Страница панели управления не открылась");
        isViewDisplayedAfterAction(waitDisplayed(newsListRecyclerView, 2000), expectedResult);
    }

    public void checkWarningSignVisibleInCategory() {
        step("Проверка отображения значка предупреждения в незаполненном поле категории");
        checkParentHasDescendant(categoryLayout, warningSignInStart);
    }

    public void checkWarningSignVisibleInTitle() {
        step("Проверка отображения значка предупреждения в незаполненном поле заголовка");
        checkParentHasDescendant(titleLayout, warningSignInEnd);
    }

    public void checkWarningSignVisibleInDate() {
        step("Проверка отображения значка предупреждения в незаполненном поле даты");
        checkParentHasDescendant(dateLayout, warningSignInEnd);
    }

    public void checkWarningSignVisibleInTime() {
        step("Проверка отображения значка предупреждения в незаполненном поле времени");
        checkParentHasDescendant(timeLayout, warningSignInEnd);
    }

    public void checkWarningSignVisibleInDescription() {
        step("Проверка отображения значка предупреждения в незаполненном поле описания");
        checkParentHasDescendant(descriptionLayout, warningSignInEnd);
    }

    public void filterNews() {
        step("Клик по кнопке ФИЛЬТРАЦИЯ");
        clickButton(filterNews);
    }

    public void deleteRandomNews(int position) {
        step("Удаление новости на позиции: " + position);
        clickButtonInSelectedNews(deleteNewsImage, position);
        clickButton(buttonOk);
    }

    public void expandRandomNews(int position) {
        step("Клик по новости с позицией = " + position);
        clickSelectedItemInNewsBlock(position);
    }

    public void cancelDeleteNewsInRandomNews(int position) {
        step("Клик по кнопке удаления новости на позиции: " + position);
        clickButtonInSelectedNews(deleteNewsImage, position);
        step("Клик по кнопке отмены удаления");
        clickButton(buttonCancel);
    }

    public void setPublishDateStartPeriod(int days) {
        step("Установка даты начала периода с датой отличной от текущей на: " + days + " дн.");
        setPeriod(publishDateStartLayout, days);
    }

    public void setPublishDateEndPeriod(int days) {
        step("Установка даты окончания периода с датой отличной от текущей на: " + days + " дн.");
        setPeriod(publishDateEndLayout, days);
    }

    public void filterSubmit() {
        step("Клик по кнопке ФИЛЬТРОВАТЬ");
        clickButton(filterSubmit);
    }

    public void submitChange() {
        step("Сохранение изменений");
        clickButton(saveButton);
        waitForViewDisplayed(newsListRecyclerView, 2000);
    }

    public void cancelChange() {
        step("Отмена изменений");
        clickButton(cancelButton);
        checkTextIsDisplayed(cancellation);
        clickButton(buttonOk);
    }

    public void cancelingExitFromNewsEditing() {
        step("Отмена выхода из редактирования новости");
        clickButton(cancelButton);
        checkTextIsDisplayed(cancellation);
        clickButton(buttonCancel);
    }

    public void openEditNewsPage(int position) {
        step("Клик по кнопке РЕДАКТИРОВАТЬ в новости с позицией: " + position);
        clickButtonInSelectedNews(editButton, position);
    }

    public String editTitleInRandomNews() {
        String title = getRandomTitle();
        step("Изменение заголовка выбранной новости на: <" + title + ">");
        enterTitle(title);
        return title;
    }

    public String editDateInRandomNews(int days) {
        String date = enterDate(days);
        step("Изменение даты публикации выбранной новости на: <" + date + ">");
        return date;
    }

    public String editDescriptionInRandomNews() {
        String description = getRandomDescription();
        step("Изменение описания выбранной новости на: <" + description + ">");
        enterDescription(description);
        return description;
    }

    public void assertNewsOrderFromNewerToOlder(ActivityScenarioRule<AppActivity> mActivityScenarioRule, String olderNews, String newerNews, boolean ascendingOrder) {
        int olderNewsPosition = getNewsPosition(mActivityScenarioRule, olderNews);
        int newerNewsPosition = getNewsPosition(mActivityScenarioRule, newerNews);

        if (olderNewsPosition != -1 && newerNewsPosition != -1) {
            if (ascendingOrder) {
                Assert.assertTrue(olderNewsPosition > newerNewsPosition);
                step("Более свежая новость  <" + newerNews + "> выше, чем старая новость <" + olderNews + ">");
            } else {
                Assert.assertTrue(olderNewsPosition < newerNewsPosition);
                step("Более свежая новость  <" + newerNews + "> ниже после сортировки, чем старая новость <" + olderNews + ">");

            }
        } else {
            fail("Не удалось найти позицию одной или обеих новостей");
        }
    }

    public String getNewsTitle(int position) {
        List<NewsHelper.News> newsList = NewsHelper.getNewsList();
        if (!newsList.isEmpty()) {
            return newsList.get(position).getTitle();
        } else {
            return null;
        }
    }

    public int getNewsPosition(ActivityScenarioRule<AppActivity> mActivityScenarioRule, String newsTitleText) {
        checkNewsIsDisplay(newsTitleText, true);
        step("Получение позиции новости с заголовком: <" + newsTitleText + ">");
        int position = getPosition(mActivityScenarioRule, newsListRecyclerView, newsTitle, newsTitleText);
        step("Позиция: " + position);
        return position;
    }

    public void checkNewsIsDisplay(String title, boolean expectedDisplayed) {
        step("Проверка, что новость с заголовком: <" + title + "> " + (expectedDisplayed ? "отображается" : "не отображается"));
        boolean found = scrollToAndCheckTextIsDisplayed(newsListRecyclerView, newsTitle, title);
        if (expectedDisplayed) {
            if (!found) {
                throw new AssertionError("News with title \"" + title + "\" not found.");
            }
        } else {
            if (found) {
                throw new AssertionError("News with title \"" + title + "\" is displayed, but it should not be.");
            }
        }
    }

    public void checkDescriptionIsDisplay(String description, boolean expectedDisplayed) {
        step("Проверка, что описание новости: <" + description + "> " + (expectedDisplayed ? "отображается" : "не отображается"));
        boolean found = scrollToAndCheckTextIsDisplayed(newsListRecyclerView, newsDescriptionTextView, description);
        if (expectedDisplayed) {
            if (!found) {
                throw new AssertionError("Description \"" + description + "\" not found.");
            }
        } else {
            if (found) {
                throw new AssertionError("Description \"" + description + "\" is displayed, but it should not be.");
            }
        }
    }

    public void isEditPageVisible() {
        step("Отображение страницы редактирования новости");
        checkTextIsDisplayed(editNewsTitle);
    }

    public void openCreateNewsPage() {
        step("Переход на страницу создания новой новости");
        clickButton(addNewsButton);
    }

    public void isCreateNewsPageVisible() {
        step("Страница создания новой новости отображается");
        checkTextIsDisplayed(createNewsTitle);
    }

    public void switchToNotActive() {
        step("Переключение статуса на НЕАКТИВНА");
        switcherToNotActive.perform(scrollTo(), click());
    }

    public void switchToActive() {
        step("Переключение статуса на АКТИВНА");
        switcherToActive.perform(scrollTo(), click());
    }

    public void checkStatus(String actualStatus, boolean activeStatus) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String statusActive = context.getString(ControlPanel.statusActive);
        String statusNotActive = context.getString(ControlPanel.statusNotActive);
        if (activeStatus) {
            step("Статус новости совпадает");
            assertEquals(statusActive, actualStatus);
        } else {
            step("Статус новости совпадает");
            assertEquals(statusNotActive, actualStatus);
        }
    }

    public void checkSubmitChange(String expected, String actual, String initial, boolean isTrue) {
        if (isTrue) {
            step("Изменения применились");
            assertEquals(expected, actual);
        } else {
            step("Изменения не применились");
            assertEquals(initial, actual);
        }
    }

    public void assertEqualsString(String expected, String actual) {
        step("Проверка на равенство значений: <" + expected + "> и <" + actual + ">");
        assertEquals(expected, actual);
    }

    public String getActualTitle(int position) {
        String title = getTextAtPosition(newsListRecyclerView, position, newsTitle);
        step("Получение заголовка новости на позиции = " + position + ". Заголовок: <" + title + ">");
        return title;
    }

    public String getActualDescription(int position) {
        String description = getTextAtPosition(newsListRecyclerView, position, newsDescriptionTextView);
        step("Получение описания новости на позиции = " + position + ". Описание: <" + description + ">");
        return description;
    }

    public String getActualDate(int position) {
        step("Получение фактической даты публикации новости на позиции: " + position);
        String date = getTextAtPosition(newsListRecyclerView, position, publicationDate);
        step("Дата: " + date);
        return date;
    }

    public String getCreationDate(int position) {
        step("Получение фактической даты создания на позиции: " + position);
        String date = getTextAtPosition(newsListRecyclerView, position, createDate);
        step("Дата: " + date);
        return date;
    }

    public String getActualStatus(int position) {
        String status = getTextAtPosition(newsListRecyclerView, position, statusText);
        step("Получение статуса новости на позиции = " + position + ". Статус: " + status);
        return status;
    }

    public void changeStatusToNotActiveInNews(int position) {
        openEditNewsPage(position);
        switchToNotActive();
        submitChange();
    }

    public void uncheckInactiveCheckBox() {
        step("Снятие флажка в чекбоксе НЕ АКТИВНА");
        clickButton(checkBoxInactive);
    }

    public void uncheckActiveCheckBox() {
        step("Снятие флажка в чекбоксе АКТИВНА");
        clickButton(checkBoxActive);
    }

    public int getRandomItemInNews() {
        int position = getRandomItemInNewsBlock();
        step("Получение рандомной позиции. Позиция = " + position);
        return position;
    }

    public void deleteNews() {
        clearNewsInRecyclerView();
    }

    public void checkNewsHaveItemsOrAddNewsInAllNews() {
        if (!isRecyclerViewHasTwoItem(newsListRecyclerView)) {
            clickButton(editNewsButton);
            deleteNews();
            addNewsWithAdjustedDate(0);
            addNewsWithAdjustedDate(0);
            clickButton(mainMenu);
            clickButtonWithText(menuItemNewsText);
        }
    }

    public void checkNewsHaveItemsOrAddNewsIfEmpty() {
        if (!isRecyclerViewHasTwoItem(newsListRecyclerView)) {
            addNewsWithAdjustedDate(0);
            addNewsWithAdjustedDate(0);
        }
    }

}

