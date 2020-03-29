package com.alrshididev.pigapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemsAdepter  extends RecyclerView.Adapter<OrderItemsAdepter.viewholder> {
    private Context context;
    private List<OrderItems> orderItemsList;
    SessionManager sessionManager;
    String PhoneUser, nameUser;
    private String URL = "https://alrashididiv.000webhostapp.com/deleteItem.php";

    public OrderItemsAdepter(Context context, List<OrderItems> orderItemsList) {
        this.context = context;
        this.orderItemsList = orderItemsList;
        sessionManager = new SessionManager(context);
        HashMap<String, String> USER = sessionManager.getUserDetail();
        PhoneUser = USER.get(SessionManager.PHONE);
        nameUser = USER.get(SessionManager.NAME);


    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_items_cardview, parent, false);
        return new OrderItemsAdepter.viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        final OrderItems items = orderItemsList.get(position);
        holder.name.setText(items.getItem_name());
        holder.price.setText(String.valueOf(items.getItem_price()));
        holder.quntity.setText(String.valueOf(items.getItem_quentity()));
        Picasso.get().load(items.getItem_imgae()).fit().centerCrop()
                .into(holder.image);


        //this to add order to restourant page and delete order item has selected from order list


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //this to delete order item has selected

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                        map.put("id",String.valueOf(items.getId()));
                        map.put("tableName","order_items");
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(stringRequest);
            }
        });





    }
    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }


        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image, deleteItem;
            TextView name, price, quntity;


            public viewholder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.item_image);
                name = itemView.findViewById(R.id.item_name);
                quntity = itemView.findViewById(R.id.item_quentity);
                price = itemView.findViewById(R.id.item_price);
                deleteItem = itemView.findViewById(R.id.deleteItem);
            }
        }
    }

