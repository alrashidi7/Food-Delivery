package com.alrshididev.pigapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestourantAdapter extends RecyclerView.Adapter<RestourantAdapter.viewholder>  {


    private Context context;
    private List<RestourantsMenu> restourantsMenuList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    RestourantAdapter(Context context, List<RestourantsMenu> restourantsMenuList) {
        this.context = context;
        this.restourantsMenuList = restourantsMenuList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restourant_cardview,parent,false);
        return new viewholder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        RestourantsMenu restourantsMenu = restourantsMenuList.get(position);

        holder.name.setText(restourantsMenu.getName());
        holder.status.setText(restourantsMenu.getStatus());
        holder.rate.setText(restourantsMenu.getRate());
        Picasso.get()
                .load(restourantsMenu.getImage())
                .fit().centerCrop()
                .placeholder(R.drawable.image_frame)
                .into(holder.imageView);
        holder.ratingBar.setRating(Float.parseFloat(restourantsMenu.getRate()));




    }

    @Override
    public int getItemCount() {
        return restourantsMenuList.size();
    }




    public class viewholder extends RecyclerView.ViewHolder {
        TextView name,status,rate;
        RatingBar ratingBar;
        ImageView imageView;

        public viewholder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.restouran_name);
            status =itemView.findViewById(R.id.restouran_status);
            rate = itemView.findViewById(R.id.restouran_rate);
            imageView = itemView.findViewById(R.id.restouran_image);
            ratingBar = itemView.findViewById(R.id.rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){

                        int position =getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

}
