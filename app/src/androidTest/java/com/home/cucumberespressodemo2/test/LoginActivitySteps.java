/*
 * Copyright (C) 2015 emmasuzuki <emma11suzuki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.home.cucumberespressodemo2.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.home.cucumberespressodemo2.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.internal.matchers.TypeSafeMatcher;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.not;

public class LoginActivitySteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private AppCompatActivity activity;

    /**
     * 每個Test方法開始時都會執行一次
     */
    @Before("@login-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    /**
     * 每個Test方法結束時都會執行一次
     */
    @After("@login-feature")
    public void tearDown() throws InterruptedException {
        int delayMillis = 2000;
        Thread.sleep(delayMillis);
        activityTestRule.finishActivity();
    }

    @Given("^On login screen")
    public void onLoginScreen() {
        assertNotNull(activity); // 查看物件是否不為空
    }

    @When("^Input email (\\S+)$")
    public void inputEmail(final String email) {
        onView(withId(com.home.cucumberespressodemo2.R.id.emailEditText)) // 透過onView取得ViewInteraction物件
                .perform(typeText(email)); // 輸入email
    }

    @When("^Input password \"(.*?)\"$")
    public void inputPassword(final String password) {
        onView(withId(com.home.cucumberespressodemo2.R.id.passwordEditText)).perform(typeText(password), closeSoftKeyboard());
    }

    @When("^Press submit frame layout")
    public void pressSubmitFrameLayout() {
        onView(withId(com.home.cucumberespressodemo2.R.id.submitFrameLayout)).perform(click());
    }

    @Then("^Show error (\\S+)$")
    public void showError(final String viewName) {
        int viewId = (viewName.equals("email")) ?
                com.home.cucumberespressodemo2.R.id.emailEditText
                : com.home.cucumberespressodemo2.R.id.passwordEditText;
        String emailFormat = "請檢查E-mail格式是否正確";
        String numberOfPasswordCharacters = "請勿使用小於六個字元的密碼";
        String message = (viewName.equals("email")) ? emailFormat : numberOfPasswordCharacters;
        onView(withId(viewId)).check(matches(hasErrorText(message)));
    }

    @Then("^Show message (true|false)$")
    public void showMessage(boolean isSuccess) {
        if (isSuccess) {
            onView(withId(com.home.cucumberespressodemo2.R.id.errorTextView)).check(matches(not(isDisplayed())));
        } else {
            onView(withId(com.home.cucumberespressodemo2.R.id.errorTextView)).check(matches(isDisplayed()));
        }
    }

    private static Matcher<? super View> hasErrorText(String expectedError) {
        return new ErrorTextMatcher(expectedError);
    }

    /**
     * Custom matcher to assert equal EditText.setError();
     */
    private static class ErrorTextMatcher extends TypeSafeMatcher<View> {

        private final String mExpectedError;

        private ErrorTextMatcher(String expectedError) {
            mExpectedError = expectedError;
        }

        @Override
        public boolean matchesSafely(View view) {
            if (!(view instanceof EditText)) {
                return false;
            }
            EditText editText = (EditText) view;
            return mExpectedError.equals(editText.getError().toString());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with error: " + mExpectedError);
        }
    }
}
