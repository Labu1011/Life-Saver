package com.example.lifesaver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DonorRecyclerViewAdapter extends RecyclerView.Adapter<DonorRecyclerViewAdapter.MyViewHolder> {
    ArrayList<DonorListItem> donorListItems;
    Context context;
    private int REQUEST_PHONE_CALL = 112;

    public DonorRecyclerViewAdapter(ArrayList<DonorListItem> donorListItems, Context context) {
        this.donorListItems = donorListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DonorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donor_list, parent, false);
        return new MyViewHolder(v);
    }

    private boolean hasPhoneCallPermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorRecyclerViewAdapter.MyViewHolder holder, int position) {
        int redColor = ContextCompat.getColor(context, R.color.red_main);
        int greenColor = ContextCompat.getColor(context, R.color.teal_700);

        // eligibility check
        String lastDonationDateStr = donorListItems.get(position).getLastDonatedOn();
        boolean isEligible = DonorUtils.isDonorEligible(lastDonationDateStr);
        holder.donorBack.setBackgroundColor(isEligible ? greenColor : redColor);

        holder.donorName.setText(donorListItems.get(position).getName());
        holder.donorGroup.setText(donorListItems.get(position).getBloodGroup());
        holder.address.setText(donorListItems.get(position).getAddress());
        holder.date.setText(donorListItems.get(position).getLastDonatedOn());
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasPhoneCallPermission()) {
                    String phoneNumber = donorListItems.get(position).getPhone();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+880" + phoneNumber));
                    context.startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return donorListItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView donorName, donorGroup, address, date;
        LinearLayout donorBack;
        ImageView callBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            donorBack = itemView.findViewById(R.id.donorBack);
            donorName = itemView.findViewById(R.id.donorName);
            donorGroup = itemView.findViewById(R.id.donorGroup);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            callBtn = itemView.findViewById(R.id.callBtn);
        }
    }
}
