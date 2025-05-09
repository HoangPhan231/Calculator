package com.example.cal; // Thay bằng package name của project của bạn

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 * Sử dụng Espresso để kiểm thử giao diện người dùng (UI Test).
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorUiTest {

    /**
     * ActivityScenarioRule cung cấp các API nhẹ để tương tác với Activity
     * của ứng dụng đang được kiểm thử.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testComplexCalculationSequence() {
        // Nhấn '8'
        onView(withId(R.id.button8)).perform(click());

        // Nhấn '*'
        onView(withId(R.id.buttonMultiply)).perform(click());

        // Nhấn '5'
        onView(withId(R.id.button5)).perform(click());

        // Nhấn '='
        onView(withId(R.id.buttonEquals)).perform(click());

        // Kiểm tra kết quả sau phép tính đầu tiên (40)
        onView(withId(R.id.textViewResult))
                .check(ViewAssertions.matches(withText("40")));

        // Nhấn '+'
        onView(withId(R.id.buttonAdd)).perform(click());

        // Nhấn '5'
        onView(withId(R.id.button5)).perform(click());

        // Nhấn '0'
        onView(withId(R.id.button0)).perform(click());

        // Nhấn '%'
        onView(withId(R.id.buttonPercent)).perform(click());

        // Nhấn '3'
        onView(withId(R.id.button3)).perform(click());

        // Nhấn '3'
        onView(withId(R.id.button3)).perform(click());

        // Nhấn 'Del'
        onView(withId(R.id.buttonDelete)).perform(click());

        // Nhấn '='
        onView(withId(R.id.buttonEquals)).perform(click());

        // Kiểm tra kết quả cuối cùng (42)
        onView(withId(R.id.textViewResult))
                .check(ViewAssertions.matches(withText("42")));
    }
}
