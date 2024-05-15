# Дипломный проект по профессии «Инженер по тестированию»

---

## Тестирование мобильного приложения «Мобильный хоспис»

[Ссылка на задание](https://github.com/netology-code/qamid-diplom)

---

## Описание приложения

Приложение дает функционал по работе с новостями хосписа и включает в себя:
- информацию о новостях и функционал для работы с ними;
- тематические цитаты;
- информацию о приложении.

---

## Документация

- [План тестирования](https://github.com/OAOblat/qamid_diplom/blob/main/QA%20Documentation/Plan.md)
- [Чек-лист](https://github.com/OAOblat/qamid_diplom/blob/main/QA%20Documentation/Check.xlsx)
- [Тест-кейсы](https://github.com/OAOblat/qamid_diplom/blob/main/QA%20Documentation/Cases.xlsx)
- [Отчет о тестировании](https://github.com/OAOblat/qamid_diplom/blob/main/QA%20Documentation/Result.md)

---

## Процедура запуска приложения и автотестов

---

- Клонировать репозиторий командой `git clone git@github.com:OAOblat/qamid_diplom.git`;
- Открыть проект в Android Studio;
- Запустить приложение на эмуляторе (Android API 29);
- Запустить тесты из командной строки`./gradlew connectedAndroidTest` и дождаться окончания прогона автотестов;


## Формирование отчета AllureReport

---

- Перейти к папке fmh-android_15_03_24\app, нажать ПКМ и выбрать Run 'All Tests';
- Выгрузить каталог `/data/data/ru.iteco.fmhandroid/files/allure-results` с эмулятора;
- Выполнить команду `allure serve`в терминале, находясь на уровень выше allure-results;
- Подождать генерации отчета и посмотреть его в открывшемся браузере.
