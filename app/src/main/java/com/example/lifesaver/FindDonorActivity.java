package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class FindDonorActivity extends AppCompatActivity {
    String[] bloodGroups;
    String bloodGroup;
    Spinner mbloodGroupSpinner;
    Button msearchBtn;

    RecyclerView recyclerView;
    ArrayList<DonorListItem> donorListItems;
    DonorRecyclerViewAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_donor);

        getSupportActionBar().setTitle("Find Donor");

        msearchBtn = findViewById(R.id.searchBtn);

        recyclerView = findViewById(R.id.donorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorListItems = new ArrayList<>();
        adapter = new DonorRecyclerViewAdapter(donorListItems, this); // ---------
        recyclerView.setAdapter(adapter);

        LinearLayout progressBarContainer = findViewById(R.id.progressBarContainer);



        // blood group select spinner adapter and functionality
        bloodGroups = getResources().getStringArray(R.array.blood_groups);
        mbloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.blood_group_spinner_layout, R.id.sample_bloodgroup_text, bloodGroups);
        mbloodGroupSpinner.setAdapter(arrayAdapter);

        // set spinner value to blood group variable
        bloodGroup = mbloodGroupSpinner.getSelectedItem().toString();

        mbloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                bloodGroup = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        //creating instance of firestore
        db = FirebaseFirestore.getInstance();
        msearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarContainer.setVisibility(View.VISIBLE);
                Query query = db.collection("donors").whereEqualTo("bloodGroup", bloodGroup);
                donorListItems.clear();
                query
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
        });


    }
}