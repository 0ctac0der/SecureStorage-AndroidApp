package com.example.securestorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securestorage.R;
import com.example.securestorage.adapter.CardDetailsAdapter;
import com.example.securestorage.utils.CommonUtils;
import com.example.securestorage.utils.Constants;
import com.example.securestorage.utils.SaveDataUtils;
import com.example.securestorage.utils.SharedPreferenceKeys;

public class CardDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        Bundle bundle = getIntent().getExtras();
        boolean isInEditDeleteMode = false;
        if(bundle != null){
            isInEditDeleteMode = bundle.getBoolean(Constants.IS_EDIT_DELETE_CARD_DETAILS);
        }

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardDetailsAdapter(this, SaveDataUtils.getCardInfo(), isInEditDeleteMode);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void deleteCard(int position) {
        SaveDataUtils.deleteCard(position);
        adapter.setCardInfo(SaveDataUtils.getCardInfo());
        adapter.notifyDataSetChanged();
        CommonUtils.showToast(this, "Card Deleted successfully");
    }

    public void editCard(int position) {
        Intent intent = new Intent(CardDetailsActivity.this, SaveCardInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_EDIT_CARD_DETAILS, true);
        bundle.putInt(Constants.ITEM_POSITION, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setCardInfo(SaveDataUtils.getCardInfo());
        adapter.notifyDataSetChanged();
    }
}