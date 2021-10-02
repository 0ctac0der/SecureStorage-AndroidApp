package com.example.securestorage.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.securestorage.R;

public class CommonUtils {

    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        }
        return false;
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean isValidUsername(Context context, String username){
        if(TextUtils.isEmpty(username)){
            showToast(context, context.getResources().getString(R.string.username_not_empty));
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(Context context, String email){
        if(TextUtils.isEmpty(email)){
            showToast(context, context.getResources().getString(R.string.email_not_empty));
            return false;
        }else if(!isValidEmail(email)){
            showToast(context, context.getResources().getString(R.string.email_not_valid));
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(Context context, String password){
        if(TextUtils.isEmpty(password)){
            showToast(context, context.getResources().getString(R.string.password_not_empty));
            return false;
        }
        return true;
    }

    public static boolean isValidSignupPassword(Context context, String password, String confirmPassword){
        if(!isValidPassword(context, password) || !isValidPassword(context, confirmPassword)){
            return false;
        }
        if(!password.equals(confirmPassword)){
            showToast(context, context.getResources().getString(R.string.password_not_match));
            return false;
        }
        return true;
    }

}
