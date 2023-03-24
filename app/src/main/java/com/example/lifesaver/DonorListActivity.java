package com.example.lifesaver;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DonorListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DonorListItem> donorListItems;
    DonorRecyclerViewAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        getSupportActionBar().setTitle("Donor List");

        recyclerView = findViewById(R.id.donorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorListItems = new ArrayList<>();
        adapter = new DonorRecyclerViewAdapter(donorListItems);
        recyclerView.setAdapter(adapter);

        LinearLayout progressBarContainer = findViewById(R.id.progressBarContainer);
        progressBarContainer.setVisibility(View.VISIBLE);

        //creating instance of firestore
        db = FirebaseFirestore.getInstance();
        db.collection("donors")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc: list) {
                        DonorListItem obj = doc.toObject(DonorListItem.class);
                        donorListItems.add(obj);
                    }
                    // update adapter ======
                    progressBarContainer.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                });
    }

}