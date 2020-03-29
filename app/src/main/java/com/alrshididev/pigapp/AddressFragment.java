package com.alrshididev.pigapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressFragment extends Fragment {
    private EditText address;
    private String addressString;
    private String name,phone;
    private Context context;
    private String urlAddAddress = "https://alrashididiv.000webhostapp.com/addAddress.php";
    private String urlAddressShow = "https://alrashididiv.000webhostapp.com/addressShow.php";

    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<Address> addressList;
    private SwipeRefreshLayout refreshLayout;
    private JSONObject jsonObject1 ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.address_fragment,container,false);

        context = getContext();
        address = view.findViewById(R.id.addressET);
        Button addAddress = view.findViewById(R.id.addAddress);
        refreshLayout = view.findViewById(R.id.refresh_address);
        SessionManager sessionManager = new SessionManager(context);
        recyclerView = view.findViewById(R.id.recyclerviewAddress);
        addressList = new ArrayList<>();
        HashMap<String,String> USER = sessionManager.getUserDetail();
        phone = USER.get(SessionManager.PHONE);
        name = USER.get(SessionManager.NAME);
         addressString = address.getText().toString();

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAddAddress, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                Toast.makeText(context, ""+ success, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("name",name);
                            map.put("phone",phone);
                            map.put("address",address.getText().toString());

                            return map;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);

                    requestQueue.add(stringRequest);



            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addressList.clear();
                adapter.notifyDataSetChanged();
                getData();
                refreshLayout.setRefreshing(false);

            }
        });

        getData();

        return view;
    }
    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAddressShow, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray =jsonObject.getJSONArray("address");
                    for (int i = 0; i < jsonArray.length();i++){
                        jsonObject1 = jsonArray.getJSONObject(i);
                        Address address = new Address(jsonObject1.getInt("id"),
                                jsonObject1.getString("name"),
                                jsonObject1.getInt("phone"),
                                jsonObject1.getString("address")
                                );

                        addressList.add(address);

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new AddressAdapter(addressList, context);
                    recyclerView.setAdapter(adapter);

                    //convert array to string
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "no address found", Toast.LENGTH_SHORT).show();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error  " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("phone",phone);
                map.put("tableName","addressShow");

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
