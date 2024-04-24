package com.example.lifesaver;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    TextView mbloodGroup, muserAddress, muserName, mlastDonatedOn, mdonationCount, mphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("My Profile");

        // get textviews by id
        muserName = findViewById(R.id.userName);
        mbloodGroup = findViewById(R.id.bloodGroup);
        muserAddress = findViewById(R.id.userAddress);
        mlastDonatedOn = findViewById(R.id.userLastDonation);
        mdonationCount = findViewById(R.id.userDonationCount);
        mphone = findViewById(R.id.userPhone);

        // Firebase current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        ProgressBar profileLoading = findViewById(R.id.profileLoading);
        profileLoading.setVisibility(View.VISIBLE);

        if(currentUser != null) {
            String phoneNumber = currentUser.getPhoneNumber();

            if(phoneNumber != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("donors")
                        .whereEqualTo("phone", phoneNumber.substring(4))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    for(QueryDocumentSnapshot doc: task.getResult()) {
                                        muserName.setText(doc.getString("name"));
                                        mbloodGroup.setText(doc.getString("bloodGroup"));
                                        muserAddress.setText(doc.getString("address"));
                                        mphone.setText("+880" + doc.getString("phone"));
                                        mlastDonatedOn.setText(doc.getString("lastDonatedOn"));
                                        mdonationCount.setText(doc.get("donationCount").toString());
                                    }

                                    profileLoading.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        }
    }


}
