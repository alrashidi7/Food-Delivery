package com.alrshididev.pigapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class RegisterActivity extends AppCompatActivity {


    EditText userName,userPhone,userEmail,userPassword,userConfirmPass;
    Button register;
    String URL = "https://alrashididiv.000webhostapp.com/register.php";


    String pass,confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPass);
        userConfirmPass = findViewById(R.id.ConfirmUserPass);
        register = findViewById(R.id.register);
        pass = userPassword.getText().toString();
        confirmPass = userConfirmPass.getText().toString();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeration();
            }
        });
    }

    private void registeration() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    String success = jsonObject.getString("success");
                    Toast.makeText(RegisterActivity.this, "" + success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation_from_left, R.anim.animation_to_right);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "404" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userName",userName.getText().toString());
                map.put("userPhone",userPhone.getText().toString());
                map.put("userEmail",userEmail.getText().toString());
                map.put("userPassword",userPassword.getText().toString());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }



}
