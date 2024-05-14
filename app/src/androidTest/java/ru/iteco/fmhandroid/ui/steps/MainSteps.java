package ru.iteco.fmhandroid.ui.steps;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.allNewsText;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsExpandButton;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsListContainer;
import static ru.iteco.fmhandroid.ui.elements.MainScreen.newsListRecyclerView;
import static ru.iteco.fmhandroid.ui.elements.Navigation.logo;
import static ru.iteco.fmhandroid.ui.elements.Navigation.mainMenu;
import static ru.iteco.fmhandroid.ui.elements.Navigation.menuItemMainText;
import static ru.iteco.fmhandroid.ui.elements.NewsScreen.allNewsCardsBlock;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.addNews;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.getRandomCategory;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.getRandomDescription;
import static ru.iteco.fmhandroid.ui.helper.NewsHelper.getRandomTitle;
import static ru.iteco.fmhandroid.ui.helper.RecyclerViewHelper.isRecyclerViewNotEmpty;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkViewIsDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.checkViewIsNotDisplayed;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButton;
import static ru.iteco.fmhandroid.ui.helper.UIActions.clickButtonWithText;
import static ru.iteco.fmhandroid.ui.helper.UIActions.waitForViewDisplayed;

public class MainSteps {
    public void isNewsListContainerVisible() {
        step("Отображение блока новостей на главном экране");
        checkViewIsDisplayed(newsListContainer);
    }

    public void checkNewsRecyclerViewForItemsOrAddNewsIfEmpty() {
        step("Проверка наличия хотя бы одной новости в списке");
        if (!isRecyclerViewNotEmpty(newsListRecyclerView)) {
            step("Добавление новости, так как список пуст");
            addNews(getRandomCategory(), getRandomTitle(), getRandomDescription(), 0, 0);
            clickButton(mainMenu);
            clickButtonWithText(menuItemMainText);
        }
    }

    public void isNewsBlockExpandedVisible() {
        step("Блок новостей развернут");
        checkViewIsDisplayed(allNewsText);
    }

    public void clickOnNewsExpandButton() {
        step("Клик по кнопке сворачивания/разворачивания блока");
        clickButton(newsExpandButton);
    }

    public void isNewsBlockExpandedInvisible() {
        step("Блок новостей свернут");
        checkViewIsNotDisplayed(allNewsText);
    }

    public void waitForViewMainScreen() {
        waitForViewDisplayed(logo, 7000);
    }

    public void goToNewsPageFromHomePage() {
        step("Клик на 'Все новости' на главной странице");
        clickButton(allNewsText);
    }

    public void isNewsPageVisible() {
        step("Открытие страницы новостей");
        checkViewIsDisplayed(allNewsCardsBlock);
    }
}
