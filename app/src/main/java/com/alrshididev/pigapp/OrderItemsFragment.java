package com.alrshididev.pigapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

public class OrderItemsFragment extends Fragment {
 private    RecyclerView recyclerView;
    private    OrderItemsAdepter adapter;
    private    AddressSpinnerAdapter addressAdapter;
    private  List<OrderItems> orderItemsList;
    private List<AddressSpinner> arrayAddress;
    private String phone,name;
    private OrderItems orderItems;
    private int price;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private TextView textView;
    private List<String> array ;
    private String urlShowItems = "https://alrashididiv.000webhostapp.com/orderItemsShow.php";
    private String urlAddressShow = "https://alrashididiv.000webhostapp.com/addressShow.php";
    RecyclerView recyclerViewSpinner;
    private String urlDelete =  "https://alrashididiv.000webhostapp.com/deleteItemFrom.php";
    private String urlAddItems = "https://alrashididiv.000webhostapp.com/rosto_orders.php";
    private JSONObject jsonObject1;
    private String str;
    private int id;
    private String addressName;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_items_fragment,container,false);
        context = getContext();
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        textView = view.findViewById(R.id.total_cost);
        Button button = view.findViewById(R.id.order_btn);
         recyclerViewSpinner = view.findViewById(R.id.recyclerviewAddressSpinner);
        array = new ArrayList<>();
        assert context != null;
        SessionManager sessionManager = new SessionManager(context);
        //check if user login or logout
        HashMap<String,String> USER = sessionManager.getUserDetail();
        phone = USER.get(SessionManager.PHONE);
        name = USER.get(SessionManager.NAME);
        recyclerView = view.findViewById(R.id.recycler_view2);
        orderItemsList = new ArrayList<>();
        arrayAddress = new ArrayList<>();

        adapter = new OrderItemsAdepter(context,orderItemsList);


        getAddress();
        getData();

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {



                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Confirm Order");
                alert.setMessage("this`s your order: "+"\n" + str +"\n"+addressName);
                alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //send order after edit
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAddItems, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject =  new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    Toast.makeText(context, "" + success, Toast.LENGTH_LONG).show();
                                    //TODO...DELETE ITEM AFTER ADD IT IN RESTOURANT DB
                                    //this to delete order item has selected
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");
                                                Toast.makeText(context, ""+success, Toast.LENGTH_SHORT).show();
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
                                            map.put("id",String.valueOf(phone));
                                            map.put("tableName","order_items");
                                            return map;
                                        }
                                    };

                                    RequestQueue queue = Volley.newRequestQueue(context);
                                    queue.add(stringRequest);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "error" + e.toString(), Toast.LENGTH_SHORT).show();
                                }}
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "404" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map = new HashMap<>();
                                map.put("total",String.valueOf(price));
                                map.put("phone",String.valueOf(phone));
                                map.put("itemName",String.valueOf(str));
                                map.put("name",name);
                                map.put("address",addressName);
                                return map;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(stringRequest);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderItemsList.clear();
                arrayAddress.clear();
                array.clear();
                adapter.notifyDataSetChanged();
                getData();
                getAddress();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return view;


    }



    // get order items has user select it from restourant items
    private void getData(){
        orderItemsList.clear();
        array.clear();
        adapter.notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlShowItems, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    price = jsonObject.getInt("total");

                    JSONArray jsonArray =jsonObject.getJSONArray("orderItems");
                    for (int i = 0; i < jsonArray.length();i++){
                        jsonObject1 = jsonArray.getJSONObject(i);
                        orderItems = new OrderItems(jsonObject1.getInt("id"),
                                jsonObject1.getString("name"),
                                jsonObject1.getInt("phone"),
                                jsonObject1.getString("item_name"),
                                jsonObject1.getInt("item_price"),
                                jsonObject1.getInt("item_quentity"),
                                jsonObject1.getString("item_image"),
                                jsonObject1.getInt("total"));

                        orderItemsList.add(orderItems);
                        id = jsonObject1.getInt("id");
                        // add allitems has been selected to array to can by used
                        array.add(" itemName= "+jsonObject1.getString("item_name") + "\n"+
                                "itemQuentity= " + jsonObject1.getInt("item_quentity") + "\n"+
                                "itemPrice= "+ jsonObject1.getString("item_price"));
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new OrderItemsAdepter(context,orderItemsList);
                    recyclerView.setAdapter(adapter);

                    //convert array to string
                    str = String.join(" \n", array);

                    //set total price in text view
                    textView.setText("total = " + price);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "no item selected", Toast.LENGTH_SHORT).show();
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

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void getAddress(){
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
                                jsonObject1.getString("address"));

                        AddressSpinner addressSpinner = new AddressSpinner(jsonObject1.getString("address"));

                        arrayAddress.add(addressSpinner);
                    }

                    addressName = AddressSpinnerAdapter.EXTRA_ADDRESS;
                    recyclerViewSpinner.setLayoutManager(new LinearLayoutManager(context));
                    recyclerViewSpinner.setHasFixedSize(true);
                    addressAdapter = new AddressSpinnerAdapter(arrayAddress, context);
                    recyclerViewSpinner.setAdapter(addressAdapter);




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
