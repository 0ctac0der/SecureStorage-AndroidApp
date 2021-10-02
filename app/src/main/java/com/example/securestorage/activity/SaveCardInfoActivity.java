package com.example.securestorage.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securestorage.R;
import com.example.securestorage.model.CardInfo;
import com.example.securestorage.utils.CommonUtils;
import com.example.securestorage.utils.Constants;
import com.example.securestorage.utils.SaveDataUtils;
import com.example.securestorage.utils.SharedPreferenceKeys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SaveCardInfoActivity extends AppCompatActivity {

    private EditText etCardNumber;
    private EditText etExpirationDate;
    private EditText etCVV;
    private EditText etCardHolderName;
    private String mLastInput;
    private boolean isInEditMode;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card_info);
        etCardNumber = findViewById(R.id.et_card_number);
        etExpirationDate = findViewById(R.id.et_expiration_date);
        etCVV = findViewById(R.id.et_cvv);
        etCardHolderName = findViewById(R.id.et_card_owner_name);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isInEditMode = bundle.getBoolean(Constants.IS_EDIT_CARD_DETAILS);
            itemPosition = bundle.getInt(Constants.ITEM_POSITION);
            if(isInEditMode){
                CardInfo cardInfoAtPosition = SaveDataUtils.getCardInfoAtPosition(itemPosition);
                etCardNumber.setText(SaveDataUtils.getDecryptedData(cardInfoAtPosition.getCardNumber()));
                etExpirationDate.setText(SaveDataUtils.getDecryptedData(cardInfoAtPosition.getCardExpiry()));
                etCVV.setText(SaveDataUtils.getDecryptedData(cardInfoAtPosition.getCardCvv()));
                etCardHolderName.setText(SaveDataUtils.getDecryptedData(cardInfoAtPosition.getCardHolderName()));
                TextView btn = findViewById(R.id.btn_save_card);
                btn.setText(getString(R.string.edit_card_info));
            }
        }

        findViewById(R.id.btn_save_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCardValidations()){
                    if(!isInEditMode){
                        saveCardInfo();
                    }else{
                        updateCardInfo();
                    }
                }
            }
        });

        etExpirationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy", Locale.GERMANY);
                Calendar expiryDateDate = Calendar.getInstance();
                try {
                    expiryDateDate.setTime(formatter.parse(input));
                } catch (java.text.ParseException e) {
                    if (s.length() == 2 && !mLastInput.endsWith("/")) {
                        int month = Integer.parseInt(input);
                        if (month <= 12) {
                            etExpirationDate.setText(etExpirationDate.getText().toString() + "/");
                            etExpirationDate.setSelection(etExpirationDate.getText().toString().length());
                        }
                    } else if (s.length() == 2 && mLastInput.endsWith("/")) {
                        int month = Integer.parseInt(input);
                        if (month <= 12) {
                            etExpirationDate.setText(etExpirationDate.getText().toString().substring(0, 1));
                            etExpirationDate.setSelection(etExpirationDate.getText().toString().length());
                        } else {
                            etExpirationDate.setText("");
                            etExpirationDate.setSelection(etExpirationDate.getText().toString().length());
                        }
                    } else if (s.length() == 1) {
                        int month = Integer.parseInt(input);
                        if (month > 1) {
                            etExpirationDate.setText("0" + etExpirationDate.getText().toString() + "/");
                            etExpirationDate.setSelection(etExpirationDate.getText().toString().length());
                        }
                    }
                    mLastInput = etExpirationDate.getText().toString();
                }
            }
        });
    }

    private void updateCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(SaveDataUtils.getEncryptedData(etCardNumber.getText().toString()));
        cardInfo.setCardExpiry(SaveDataUtils.getEncryptedData(etExpirationDate.getText().toString()));
        cardInfo.setCardCvv(SaveDataUtils.getEncryptedData(etCVV.getText().toString()));
        cardInfo.setCardHolderName(SaveDataUtils.getEncryptedData(etCardHolderName.getText().toString()));
        SaveDataUtils.updateCardInfo(cardInfo, itemPosition);
        CommonUtils.showToast(this, "Card Details Edit Successfully");
    }

    private void saveCardInfo() {
        SaveDataUtils.addCardInfo(etCardNumber.getText().toString(), etExpirationDate.getText().toString(), etCVV.getText().toString(), etCardHolderName.getText().toString());
        etCardNumber.setText("");
        etExpirationDate.setText("");
        etCVV.setText("");
        etCardHolderName.setText("");
        CommonUtils.showToast(this, "Card Details Saved Successfully");
    }

    private boolean checkCardValidations() {
        String creditCardNumber = etCardNumber.getText().toString();
        if(TextUtils.isEmpty(creditCardNumber) || creditCardNumber.length() < 16){
            CommonUtils.showToast(this, getResources().getString(R.string.credit_card_alert));
            return false;
        }
        String expirationDate = etExpirationDate.getText().toString();
        if(TextUtils.isEmpty(expirationDate) || expirationDate.length() < 5){
            CommonUtils.showToast(this, getResources().getString(R.string.expiration_date_alert));
            return false;
        }
        String cvv = etCVV.getText().toString();
        if(TextUtils.isEmpty(cvv) || cvv.length() < 3){
            CommonUtils.showToast(this, getResources().getString(R.string.cvv_alert));
            return false;
        }
        String cardOwnerName = etCardHolderName.getText().toString();
        if(TextUtils.isEmpty(cardOwnerName)){
            CommonUtils.showToast(this, getResources().getString(R.string.card_owner_name_alert));
            return false;
        }
        return true;
    }
}