package com.alrshididev.pigapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class LogoutFragment extends Fragment {

    Context context;
    String phone,name;
    private SessionManager sessionManager;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.logout_fragment,container,false);
        Button button = view.findViewById(R.id.button__);
        TextView nameUsr = view.findViewById(R.id.person_name);
        TextView phoneUsr = view.findViewById(R.id.person_phone);


        context= getContext();
        assert context != null;
        sessionManager = new SessionManager(context);
        //check if user login or logout
        HashMap<String,String> USER = sessionManager.getUserDetail();
        phone = USER.get(SessionManager.PHONE);
        name = USER.get(SessionManager.NAME);

        nameUsr.setText(name);
        phoneUsr.setText("+2"+ phone);


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {




                assert context != null;


                sessionManager.logout();



                //check if user login or logout


                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);

            }
        });







        return view;
    }
}
