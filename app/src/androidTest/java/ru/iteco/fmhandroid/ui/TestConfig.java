package ru.iteco.fmhandroid.ui;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import java.util.Random;

import ru.iteco.fmhandroid.R;

public class TestConfig {
        // Страница авторизации
        public static final String VALID_LOGIN = "login2"; //логин зарег.пользователя
        public static final String VALID_PASSWORD = "password2"; // пароль зарег.пользователя
        public static String randomLogin = generateRandomString(8);
        public static String randomPassword = generateRandomString(10);
        // View IDs
        public static final int LOGIN_FIELD = R.id.login_text_input_layout; //поле для ввода логина
        public static final int PASSWORD_FIELD = R.id.password_text_input_layout; //поле для ввода пароля
        public static final int SIGNING_BUTTON = R.id.enter_button; // кнопка ВОЙТИ
        public static final int LOGO = R.id.trademark_image_view; //логотип на главной странице
        public static final int authorization_image_button = R.id.authorization_image_button;
        public static final int LOGOUT_ID = android.R.id.title;
        public static final int LOGOUT_TEXT = R.string.log_out;

        //Error message
        public static final String wrong_login_or_password = getErrorMessage(R.string.wrong_login_or_password);
        public static final String empty_login_or_password = getErrorMessage(R.string.empty_login_or_password);

        // Метод для генерации случайной строки
        private static String generateRandomString(int length) {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                StringBuilder sb = new StringBuilder(length);
                Random random = new Random();
                for (int i = 0; i < length; i++) {
                        sb.append(characters.charAt(random.nextInt(characters.length())));
                }
                return sb.toString();
        }

        private static String getErrorMessage(int errorMessageResId) {
                Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
                return context.getString(errorMessageResId);
        }
}
