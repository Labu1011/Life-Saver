package com.example.lifesaver;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateDonationDateActivity extends AppCompatActivity {
    DatePicker mdatePicker;
    Button mUpdate;
    String phoneNumber;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_donation_date);

        getSupportActionBar().setTitle("Update Donation Date");

        mdatePicker = findViewById(R.id.datePickeronUpdate);
        mUpdate = findViewById(R.id.updateDonation);

        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phone", "");

        Log.d("Res", "phone: " + phoneNumber);


        Query query = db.collection("donors").whereEqualTo("phone", phoneNumber);

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            DocumentReference docRef = documentSnapshot.getReference();

                            if(documentSnapshot.getString("lastDonatedOn") != selectedDate()) {
                                docRef.update("lastDonatedOn", selectedDate()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(UpdateDonationDateActivity.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                int updatedCount = Integer.parseInt(documentSnapshot.get("donationCount").toString()) + 1;
                                docRef.update("donationCount", updatedCount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(UpdateDonationDateActivity.this, "+1 donation", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateDonationDateActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    String selectedDate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mdatePicker.getDayOfMonth() + "/");
        stringBuilder.append((mdatePicker.getMonth() + 1) + "/");
        stringBuilder.append(mdatePicker.getYear());

        return stringBuilder.toString();
    }
}