package com.alrshididev.pigapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

class SessionManager {
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    static final String NAME = "NAME";
    static final String PHONE = "PHONE";
    static final String EMAIL = "EMAIL";


    @SuppressLint("CommitPrefEdits")
    SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    void createSession(String name, String phone, String email){

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(PHONE,phone);
        editor.putString(EMAIL,email);
        editor.apply();

    }

    private boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    void checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((Main2Activity) context).finish();
        }
    }

    HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, "null"));
        user.put(PHONE, sharedPreferences.getString(PHONE, "null"));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, "null"));


        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();




    }

}