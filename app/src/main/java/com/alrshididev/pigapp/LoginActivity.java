package com.alrshididev.pigapp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    CheckBox rememberMe;

    EditText userName;
    EditText password;
    TextView register;
    Button login;
    String URL ="https://alrashididiv.000webhostapp.com/login.php";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName =findViewById(R.id.user);
        password = findViewById(R.id.etPassword);
        register = findViewById(R.id.register);
        login    = findViewById(R.id.login);
        rememberMe = findViewById(R.id.rememberMe);

        sessionManager = new SessionManager(this);

        //login onClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animation_from_left,R.anim.animation_to_right);
            }
        });
    }
   private void login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject =  new JSONObject(response);
                    String success = jsonObject.getString("success");

                    Toast.makeText(LoginActivity.this, "" + success, Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    for (int i = 0; i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name").trim();
                            String phone = object.getString("phone").trim();
                            String email = object.getString("email").trim();
                            if (rememberMe.isChecked()){
                            sessionManager.createSession(name,phone,email);
                            }
                            Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
                            startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "al    " + e.toString(), Toast.LENGTH_SHORT).show();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "404" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Phone",userName.getText().toString().trim());
                map.put("Password",password.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}