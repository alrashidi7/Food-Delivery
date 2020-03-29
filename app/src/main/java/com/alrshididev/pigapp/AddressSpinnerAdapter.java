package com.alrshididev.pigapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressSpinnerAdapter extends RecyclerView.Adapter<AddressSpinnerAdapter.viewholder> {
   static String EXTRA_ADDRESS = "";
    List<AddressSpinner> addressListSpinner ;
    Context context;
    public AddressSpinnerAdapter(List<AddressSpinner> addressListSpinner, Context context) {
        this.addressListSpinner = addressListSpinner;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_spinner_cardview,parent,false);

        return new AddressSpinnerAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final AddressSpinner address = addressListSpinner.get(position);

        holder.address.setText(address.getAddress());
        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EXTRA_ADDRESS = address.getAddress();

                Toast.makeText(context, ""+EXTRA_ADDRESS, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressListSpinner.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView address,textView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title_tv);
            address = itemView.findViewById(R.id.addressTV);
        }
    }
}
