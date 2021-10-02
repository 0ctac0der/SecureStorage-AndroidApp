package com.example.securestorage.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {
    private String username;
    private String password;
    private ArrayList<CardInfo> cardInfoList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<CardInfo> getCardInfoList() {
        return cardInfoList;
    }

    public void setCardInfoList(ArrayList<CardInfo> cardInfoList) {
        this.cardInfoList = cardInfoList;
    }
}
