package com.example.lifesaver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodBankListAdapter extends RecyclerView.Adapter<BloodBankListAdapter.ViewHolder> {
    ArrayList<BloodBanks> bloodBanksArrayList;
    Context context;
    private int REQUEST_PHONE_CALL = 112;

    public BloodBankListAdapter(ArrayList<BloodBanks> bloodBanksArrayList, Context context) {
        this.bloodBanksArrayList = bloodBanksArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blood_bank_list, parent, false);
        return new ViewHolder(v);
    }

    private boolean hasPhoneCallPermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBindViewHolder(@NonNull BloodBankListAdapter.ViewHolder holder, int position) {
        holder.name.setText(bloodBanksArrayList.get(position).getName());
        holder.address.setText(bloodBanksArrayList.get(position).getAddress());
        holder.phone.setText(bloodBanksArrayList.get(position).getPhone());

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasPhoneCallPermission()) {
                    String phoneNumber = bloodBanksArrayList.get(position).getPhone();
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
        return bloodBanksArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, phone;
        ImageView callBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.bankName);
            address = itemView.findViewById(R.id.bankAddress);
            phone = itemView.findViewById(R.id.bankPhone);
            callBtn = itemView.findViewById(R.id.callBtn);
        }
    }
}
