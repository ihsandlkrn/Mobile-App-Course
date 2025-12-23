package msku.ceng.madlab.testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest {

    // Automatically launches RegisterActivity when the test starts
    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule =
            new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void checkViewsDisplay() {
        // 1. Check if buttons and input fields are displayed when screen opens
        onView(withId(R.id.etName)).check(matches(isDisplayed()));
        onView(withId(R.id.etRegEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.btnDoRegister)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEmptyFieldsValidation() {
        // 2. Test: What happens if we click "Register" with empty fields?

        // Click the button
        onView(withId(R.id.btnDoRegister)).perform(click());

        // Expectation: "Name cannot be empty" error should appear on the Name field
        onView(withId(R.id.etName)).check(matches(hasErrorText("Name cannot be empty")));
    }

    @Test
    public void checkInvalidEmailValidation() {
        // 3. Test: What happens when an invalid email is entered?

        // Enter a valid name (to pass the first validation check)
        onView(withId(R.id.etName)).perform(typeText("Ahmet"), closeSoftKeyboard());

        // Enter an invalid Email format
        onView(withId(R.id.etRegEmail)).perform(typeText("invalid-email-format"), closeSoftKeyboard());

        // Click the button
        onView(withId(R.id.btnDoRegister)).perform(click());

        // Expectation: Error message should appear on the Email field
        onView(withId(R.id.etRegEmail)).check(matches(hasErrorText("Please enter a valid email address")));
    }

    @Test
    public void checkWeakPasswordValidation() {
        // 4. Test: What happens when a weak password is entered? (Visual UI test)

        // Fill in other required fields correctly
        onView(withId(R.id.etName)).perform(typeText("Ahmet"), closeSoftKeyboard());
        onView(withId(R.id.etRegEmail)).perform(typeText("ahmet@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.etRegPhone)).perform(typeText("5551234567"), closeSoftKeyboard());

        // Enter a weak password (Only digits, too short)
        onView(withId(R.id.etRegPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Click the button
        onView(withId(R.id.btnDoRegister)).perform(click());

        // Expectation: Error message regarding password complexity should appear
        onView(withId(R.id.etRegPassword)).check(matches(hasErrorText("Password must be at least 8 chars, contain 1 Number & 1 Uppercase letter.")));
    }
}