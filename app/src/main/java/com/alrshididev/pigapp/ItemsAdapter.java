package com.alrshididev.pigapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class ItemsAdapter  extends RecyclerView.Adapter<ItemsAdapter.viewholder>  {
    String URL = "https://alrashididiv.000webhostapp.com/orderIitems.php";
    private Context context;
    private List<Items> itemsList;
    private String nameUser,PhoneUser,price,quintity;
    int quntity_item,price_item;

    SessionManager sessionManager;
    private ItemsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public ItemsAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        sessionManager = new SessionManager(context);
        //check if user login or logout
        HashMap<String,String> USER = sessionManager.getUserDetail();
        PhoneUser = USER.get(SessionManager.PHONE);
        nameUser  = USER.get(SessionManager.NAME);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_card_view,parent,false);

        return new ItemsAdapter.viewholder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        final Items items= itemsList.get(position);
        holder.name.setText(items.getItem_name());
        holder.desc.setText(items.getItem_desc());
        holder.price.setText(String.valueOf(items.getItem_price()));
        holder.quintity.setText(String.valueOf(items.getItem_quentity()));
        Picasso.get().load(items.getItem_image()).fit().centerCrop()
                .into(holder.imageView);

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //edit quntity textView
                quntity_item = Integer.parseInt( holder.quintity.getText().toString());
                 quntity_item = quntity_item + 1 ;
                 holder.quintity.setText(""+ quntity_item );

            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //edit quntity textView
                if (quntity_item > 0){
                    quntity_item = Integer.parseInt( holder.quintity.getText().toString());
                    quntity_item = quntity_item - 1 ;
                    holder.quintity.setText(""+ quntity_item );
                }

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               price = holder.price.getText().toString();
               quintity = holder.quintity.getText().toString();


                price = String.valueOf( Integer.parseInt(price) * Integer.parseInt(quintity) );

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            String success = jsonObject.getString("success");
                            Toast.makeText(context, "" + success, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "error adf" + e.toString(), Toast.LENGTH_SHORT).show();
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
                        map.put("name",nameUser);
                        map.put("phone",PhoneUser);
                        map.put("itemName",items.getItem_name());
                        map.put("itemPrice",String.valueOf(price));
                        map.put("itemQuentity",String.valueOf(quintity));
                        map.put("itemImage",items.getItem_image());
                        map.put("total",String.valueOf(price));
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
        return itemsList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        Button increment,decrement,add;
        TextView name,desc,price,quintity;
        ImageView imageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            name = itemView.findViewById(R.id.item_name);
            desc = itemView.findViewById(R.id.item_desc);
            price = itemView.findViewById(R.id.item_price);
            quintity = itemView.findViewById(R.id.quntity);
            imageView = itemView.findViewById(R.id.item_image);
            add = itemView.findViewById(R.id.add);




        }
    }
}
