package com.example.securestorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.securestorage.R;
import com.example.securestorage.activity.CardDetailsActivity;
import com.example.securestorage.model.CardInfo;
import com.example.securestorage.utils.SaveDataUtils;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.CardViewHolder> {

    private List<CardInfo> cardInfoList;
    private boolean isInEditDeleteMode;
    private Context context;

    public CardDetailsAdapter(Context context, List<CardInfo> cardInfo, boolean isInEditDeleteMode) {
        this.context = context;
        this.cardInfoList = cardInfo;
        this.isInEditDeleteMode = isInEditDeleteMode;
    }

    public void setCardInfo(ArrayList<CardInfo> cardInfo) {
        this.cardInfoList = cardInfo;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView cardNumber;
        public TextView cardExpiry;
        public TextView cardCvv;
        public TextView cardName;
        public TextView btnEdit;
        public TextView btnDelete;
        public LinearLayout layoutEditDelete;
        public CardViewHolder(View v) {
            super(v);
            cardNumber = v.findViewById(R.id.txt_card_number);
            cardExpiry = v.findViewById(R.id.txt_card_expiry);
            cardCvv = v.findViewById(R.id.txt_card_cvv);
            cardName = v.findViewById(R.id.txt_card_name);
            btnEdit = v.findViewById(R.id.btn_edit);
            btnDelete = v.findViewById(R.id.btn_delete);
            layoutEditDelete = v.findViewById(R.id.layout_edit_delete);
        }
    }

    @Override
    public CardDetailsAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final CardInfo cardInfo = cardInfoList.get(position);
        if(cardInfo != null){
            holder.cardNumber.setText(SaveDataUtils.getDecryptedData(cardInfo.getCardNumber()));
            holder.cardExpiry.setText(SaveDataUtils.getDecryptedData(cardInfo.getCardExpiry()));
            holder.cardCvv.setText(SaveDataUtils.getDecryptedData(cardInfo.getCardCvv()));
            holder.cardName.setText(SaveDataUtils.getDecryptedData(cardInfo.getCardHolderName()));
            holder.layoutEditDelete.setVisibility(isInEditDeleteMode ? View.VISIBLE:View.GONE);
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardDetailsActivity) context).editCard(position);
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardDetailsActivity) context).deleteCard(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cardInfoList.size();
    }
}
