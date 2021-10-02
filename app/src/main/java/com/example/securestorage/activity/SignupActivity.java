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

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        findViewById(R.id.txt_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidations()) {
                    saveSignupInfo();
                }
            }
        });

        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
    }

    private void saveSignupInfo() {
        if(SaveDataUtils.checkUsername(etUsername.getText().toString())){
            CommonUtils.showToast(this, "Username already there");
            resetUI();
            return;
        }
        SaveDataUtils.addUsername(etUsername.getText().toString(), etPassword.getText().toString());
        SaveDataUtils.saveDataIntoSharedPref(SharedPreferenceKeys.CURRENT_USER, etUsername.getText().toString());
        resetUI();
        CommonUtils.showToast(this, "Signup Successfully");
        startHomeActivity();
    }

    private void resetUI(){
        etUsername.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkValidations() {
        if (!CommonUtils.isValidUsername(this, etUsername.getText().toString())) {
            return false;
        }
        if (!CommonUtils.isValidSignupPassword(this, etPassword.getText().toString(), etConfirmPassword.getText().toString())) {
            return false;
        }
        return true;
    }
}