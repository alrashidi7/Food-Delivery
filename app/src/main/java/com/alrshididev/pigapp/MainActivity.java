package com.alrshididev.pigapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        //check if user login or logout
        HashMap<String,String> USER = sessionManager.getUserDetail();
        String phone = USER.get(SessionManager.PHONE);

        if (phone.equals("null")) {
            //if user logout go on login

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_from_left, R.anim.animation_to_right);

        }else {
            //if user logon go to activity
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_from_left, R.anim.animation_to_right);
        }
    }
}
