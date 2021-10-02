package com.example.securestorage.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.example.securestorage.model.CardInfo;
import com.example.securestorage.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SaveDataUtils {

    public static void saveDataIntoSharedPref(String key, String data) {
        try {
            byte[] bytes = EncryptionUtils.encryptMsg(data);
            String saveData = Base64.encodeToString(bytes, Base64.DEFAULT);
            SharedPreferences.getInstance().putString(key, saveData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String getEncryptedData(String data) {
        try {
            byte[] bytes = EncryptionUtils.encryptMsg(data);
            String saveData = Base64.encodeToString(bytes, Base64.DEFAULT);
            return saveData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDataFromSharedPref(String key) {
        String data = SharedPreferences.getInstance().getString(key);
        if (!TextUtils.isEmpty(data)) {
            byte[] array = Base64.decode(data, Base64.DEFAULT);
            try {
                return EncryptionUtils.decryptMsg(array);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getDecryptedData(String data) {
        if (!TextUtils.isEmpty(data)) {
            byte[] array = Base64.decode(data, Base64.DEFAULT);
            try {
                return EncryptionUtils.decryptMsg(array);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean checkUsername(String username){
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(username)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLogin(String username, String password){
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(username) &&
                        getDecryptedData(userInfoArrayList.get(i).getPassword()).equals(password)){
                    return true;
                }
            }
        }
        return false;
    }


    public static void addUsername(String username, String password) {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList == null){
            userInfoArrayList = new ArrayList<>();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(getEncryptedData(username));
        userInfo.setPassword(getEncryptedData(password));
        userInfoArrayList.add(userInfo);
        String str = gson.toJson(userInfoArrayList);
        SharedPreferences.getInstance().putString(SharedPreferenceKeys.USERS_DATA, str);
    }

    public static void addCardInfo(String cardNo, String cardExpiry, String cardCVV, String cardHolderName) {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(getDataFromSharedPref(SharedPreferenceKeys.CURRENT_USER))){
                    ArrayList<CardInfo> cardInfoList = userInfoArrayList.get(i).getCardInfoList();
                    if(cardInfoList == null){
                        cardInfoList = new ArrayList<>();
                    }
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setCardNumber(getEncryptedData(cardNo));
                    cardInfo.setCardExpiry(getEncryptedData(cardExpiry));
                    cardInfo.setCardCvv(getEncryptedData(cardCVV));
                    cardInfo.setCardHolderName(getEncryptedData(cardHolderName));
                    cardInfoList.add(cardInfo);
                }
            }
        }
        String str = gson.toJson(userInfoArrayList);
        SharedPreferences.getInstance().putString(SharedPreferenceKeys.USERS_DATA, str);
    }

    public static void updateCardInfo(CardInfo cardInfo, int itemPosition) {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(getDataFromSharedPref(SharedPreferenceKeys.CURRENT_USER))){
                    ArrayList<CardInfo> cardInfoList = userInfoArrayList.get(i).getCardInfoList();
                    if(cardInfoList == null){
                        cardInfoList = new ArrayList<>();
                    }
                    cardInfoList.set(itemPosition, cardInfo);
                }
            }
        }
        String str = gson.toJson(userInfoArrayList);
        SharedPreferences.getInstance().putString(SharedPreferenceKeys.USERS_DATA, str);
    }

    public static ArrayList<CardInfo> getCardInfo() {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(getDataFromSharedPref(SharedPreferenceKeys.CURRENT_USER))){
                    ArrayList<CardInfo> cardInfoList = userInfoArrayList.get(i).getCardInfoList();
                    if(cardInfoList == null){
                        cardInfoList = new ArrayList<>();
                    }
                    return cardInfoList;
                }
            }
        }
        return null;
    }

    public static void deleteCard(int position) {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(getDataFromSharedPref(SharedPreferenceKeys.CURRENT_USER))){
                    ArrayList<CardInfo> cardInfoList = userInfoArrayList.get(i).getCardInfoList();
                    if(cardInfoList == null){
                        cardInfoList = new ArrayList<>();
                    }else{
                        cardInfoList.remove(position);
                    }
                }
            }
        }
        String str = gson.toJson(userInfoArrayList);
        SharedPreferences.getInstance().putString(SharedPreferenceKeys.USERS_DATA, str);
    }

    public static CardInfo getCardInfoAtPosition(int position) {
        String data = SharedPreferences.getInstance().getString(SharedPreferenceKeys.USERS_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        ArrayList<UserInfo> userInfoArrayList = gson.fromJson(data, type);
        if(userInfoArrayList != null){
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                if(getDecryptedData(userInfoArrayList.get(i).getUsername()).equals(getDataFromSharedPref(SharedPreferenceKeys.CURRENT_USER))){
                    ArrayList<CardInfo> cardInfoList = userInfoArrayList.get(i).getCardInfoList();
                    return cardInfoList.get(position);
                }
            }
        }
        return new CardInfo();
    }
}
