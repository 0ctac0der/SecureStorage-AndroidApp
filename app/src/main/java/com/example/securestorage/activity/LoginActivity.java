package com.example.securestorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securestorage.R;
import com.example.securestorage.utils.CommonUtils;
import com.example.securestorage.utils.SaveDataUtils;
import com.example.securestorage.utils.SharedPreferenceKeys;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidations()){
                    checkLoginInfo();
                }
            }
        });

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignupActivity();
            }
        });
    }

    private void checkLoginInfo() {
        if (SaveDataUtils.checkLogin(etUsername.getText().toString(), etPassword.getText().toString())) {
            SaveDataUtils.saveDataIntoSharedPref(SharedPreferenceKeys.CURRENT_USER, etUsername.getText().toString());
            startHomeActivity();
        } else {
            CommonUtils.showToast(this, "Username and password does not match");
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startSignupActivity() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkValidations() {
        if(!CommonUtils.isValidUsername(this, etUsername.getText().toString())){
            return false;
        }
        if(!CommonUtils.isValidPassword(this, etPassword.getText().toString())){
            return false;
        }
        return true;
    }
}