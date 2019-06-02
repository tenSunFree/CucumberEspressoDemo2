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

package com.home.cucumberespressodemo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Demo Login activity class
 */
public class LoginActivity extends AppCompatActivity {

    private static final String CORRECT_EMAIL = "a0985092384@gmail.com";
    private static final String CORRECT_PASSWORD = "5201314";
    private EditText emailEditText, passwordEditText;
    private TextView successTextView, errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();
    }

    private void initializeView() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        FrameLayout submitFrameLayout = findViewById(R.id.submitFrameLayout);
        submitFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
                if (emailEditText.getError() == null && passwordEditText.getError() == null) {
                    validateAccount();
                }
            }
        });
    }

    /**
     * 驗證字段
     */
    private void validateFields() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            String emailFormat = "請檢查E-mail格式是否正確";
            emailEditText.setError(emailFormat);
        } else {
            emailEditText.setError(null);
        }
        int numberOfPassword = 6;
        String numberOfPasswordCharacters = "請勿使用小於六個字元的密碼";
        if (passwordEditText.getText().length() < numberOfPassword) {
            passwordEditText.setError(numberOfPasswordCharacters);
        } else {
            passwordEditText.setError(null);
        }
    }

    /**
     * 驗證帳戶
     */
    private void validateAccount() {
        if (errorTextView == null) {
            errorTextView = findViewById(R.id.errorTextView);
        }
        if (successTextView == null) {
            successTextView = findViewById(R.id.successTextView);
        }
        if (!emailEditText.getText().toString().equals(CORRECT_EMAIL)
                || !passwordEditText.getText().toString().equals(CORRECT_PASSWORD)) {
            errorTextView.setVisibility(View.VISIBLE);
            successTextView.setVisibility(View.GONE);
        } else {
            errorTextView.setVisibility(View.GONE);
            successTextView.setVisibility(View.VISIBLE);
        }
    }
}
