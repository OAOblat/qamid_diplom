package ru.iteco.fmhandroid.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

public class CustomViewAssertion {
    public static ViewAssertion isRecyclerView() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw new IllegalStateException("This is not RecyclerView");

                }
            }
        };
    }
}