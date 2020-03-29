package com.alrshididev.pigapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FragmentItems extends Fragment {
    RecyclerView recyclerView;
    ItemsAdapter adapter;
    List<Items> itemsList;
    Items items;
    Context context;
    private String URL_Data = "https://alrashididiv.000webhostapp.com/rosto.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_fragment,container,false);
        context = getContext();

        recyclerView = view.findViewById(R.id.recycler_view1);
        itemsList = new ArrayList<>();


        String NAME  = getArguments().getString("name");
        Toast.makeText(context, ""+ NAME, Toast.LENGTH_LONG).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("restourantName");
                    for (int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        items = new Items(jsonObject1.getString("item_name"),
                                jsonObject1.getString("item_desc"),
                                jsonObject1.getInt("item_price"),
                                jsonObject1.getInt("item_quentity"),
                                jsonObject1.getString("item_image"));

                        itemsList.add(items);



                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new ItemsAdapter(context,itemsList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "e  " + e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error  " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


        return view;





    }
}
