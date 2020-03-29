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

public class MenuRestourantFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestourantAdapter adapter;
    private String URL_Data = "https://alrashididiv.000webhostapp.com/restourantMenu.php";
     private Context context;
    private List<RestourantsMenu> restourantsMenuList;
    private RestourantsMenu restourantsMenu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_restourant_fragment,container,false);
        context = v.getContext();
        recyclerView = v.findViewById(R.id.recycler_view);
        restourantsMenuList = new ArrayList<>();
        reguest();



        return v;

    }

    private void reguest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("restourantName");
                    for (int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        restourantsMenu = new RestourantsMenu(jsonObject1.getString("name"),
                                jsonObject1.getString("status"),
                                jsonObject1.getString("rate"),
                                jsonObject1.getString("image"));
                        restourantsMenuList.add(restourantsMenu);



                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new RestourantAdapter(context,restourantsMenuList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new RestourantAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                           String nameItem =  restourantsMenuList.get(position).getName();
                            Toast.makeText(context, ""+ nameItem, Toast.LENGTH_SHORT).show();

                           // add if for menu restourant
                            assert getFragmentManager() != null;
                             Fragment fragment = new FragmentItems();
                            Bundle arguments = new Bundle();
                            arguments.putString( "name" , nameItem);
                            fragment.setArguments(arguments);
                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.framelayout, fragment);
                            ft.commit();

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

    }

}
