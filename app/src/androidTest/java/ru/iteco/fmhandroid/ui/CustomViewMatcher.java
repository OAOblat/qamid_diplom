package ru.iteco.fmhandroid.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Objects;

public class CustomViewMatcher {
    public static Matcher<View> recyclerViewSizeMatcher(int size) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            String result;

            @Override
            public void describeTo(Description description) {
                description.appendText("Requested list size: " + size);
                description.appendText("\nReal: ");
                description.appendText(result);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                int items = Objects.requireNonNull(item.getAdapter()).getItemCount();
                result = "List size:" + items;
                return size == items;
            }
        };
    }
}
