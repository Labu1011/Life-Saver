package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BloodBankListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<BloodBanks> bloodBanksArrayList;
    BloodBankListAdapter bloodBankListAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_list);

        getSupportActionBar().setTitle("Blood Bank List");

        recyclerView = findViewById(R.id.bankRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bloodBanksArrayList = new ArrayList<>();
        bloodBankListAdapter = new BloodBankListAdapter(bloodBanksArrayList, this);
        recyclerView.setAdapter(bloodBankListAdapter);

        LinearLayout progressBarContainer = findViewById(R.id.progressBarContainer);
        progressBarContainer.setVisibility(View.VISIBLE);

        //creating instance of firestore
        db = FirebaseFirestore.getInstance();
        db.collection("banks")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc: list) {
                        BloodBanks obj = doc.toObject(BloodBanks.class);
                        bloodBanksArrayList.add(obj);
                    }
                    // update adapter ======
                    progressBarContainer.setVisibility(View.GONE);
                    bloodBankListAdapter.notifyDataSetChanged();
                });
    }
}