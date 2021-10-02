package com.example.securestorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securestorage.R;
import com.example.securestorage.model.CardInfo;
import com.example.securestorage.utils.CommonUtils;
import com.example.securestorage.utils.Constants;
import com.example.securestorage.utils.SaveDataUtils;
import com.example.securestorage.utils.SharedPreferenceKeys;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        findViewById(R.id.btn_save_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSaveCardInfoActivity();
            }
        });

        findViewById(R.id.btn_get_card_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCardDetails();
            }
        });

        findViewById(R.id.btn_edit_card_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCardDetails();
            }
        });
    }

    private void startSaveCardInfoActivity() {
        Intent intent = new Intent(HomeActivity.this, SaveCardInfoActivity.class);
        startActivity(intent);
    }

    private void showCardDetails() {
        ArrayList<CardInfo> cardInfo = SaveDataUtils.getCardInfo();
        if(cardInfo != null && cardInfo.size() > 0){
            Intent intent = new Intent(HomeActivity.this, CardDetailsActivity.class);
            startActivity(intent);
        }else{
            CommonUtils.showToast(this, "No Card Details");
        }
    }

    private void editCardDetails() {
        ArrayList<CardInfo> cardInfo = SaveDataUtils.getCardInfo();
        if(cardInfo != null && cardInfo.size() > 0){
            Intent intent = new Intent(HomeActivity.this, CardDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.IS_EDIT_DELETE_CARD_DETAILS, true);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            CommonUtils.showToast(this, "No Card Details");
        }
    }

    private void logout() {
        SaveDataUtils.saveDataIntoSharedPref(SharedPreferenceKeys.CURRENT_USER, "");
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}