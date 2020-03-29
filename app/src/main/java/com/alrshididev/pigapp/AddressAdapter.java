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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.viewholder> {
    List<Address> addressList ;
    Context context;
    private String URL = "https://alrashididiv.000webhostapp.com/deleteItem.php";

    public AddressAdapter(List<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_card_view,parent,false);

        return new AddressAdapter.viewholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Address address = addressList.get(position);

        holder.address.setText(address.getAddress());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        map.put("id",String.valueOf(address.getId()));
                        map.put("tableName","userAddress");
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
        return addressList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView address;
        ImageView delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.addressTV);
            delete  = itemView.findViewById(R.id.deleteAddress);
        }
    }
}
