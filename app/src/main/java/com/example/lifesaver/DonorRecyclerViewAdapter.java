package com.example.lifesaver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DonorRecyclerViewAdapter extends RecyclerView.Adapter<DonorRecyclerViewAdapter.MyViewHolder> {
    ArrayList<DonorListItem> donorListItems;

    public DonorRecyclerViewAdapter(ArrayList<DonorListItem> donorListItems) {
        this.donorListItems = donorListItems;
    }

    @NonNull
    @Override
    public DonorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donor_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.donorName.setText(donorListItems.get(position).getName());
        holder.donorGroup.setText(donorListItems.get(position).getBloodGroup());
    }

    @Override
    public int getItemCount() {
        return donorListItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView donorName, donorGroup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            donorName = itemView.findViewById(R.id.donorName);
            donorGroup = itemView.findViewById(R.id.donorGroup);
        }
    }
}
