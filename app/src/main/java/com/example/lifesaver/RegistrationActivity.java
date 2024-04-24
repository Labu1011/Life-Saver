package com.example.lifesaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText mfullName, maddress;
    Button mregisterBtn, msetDateBtn, mneverBtn;
    ProgressBar progressBar;
    TextView mlastDonatedStatus;
    DatePicker datePicker;

    // phone from previous intent and blood group from spinner
    String phone, bloodGroup;
    String date = "never";

    String[] bloodGroups;
    Spinner mbloodGroupSpinner;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Donor Registration");

        db = FirebaseFirestore.getInstance();

        mfullName = findViewById(R.id.fullName);
        maddress = findViewById(R.id.yourAddress);
        mlastDonatedStatus = findViewById(R.id.lastDonatedStatus);
        datePicker = findViewById(R.id.datePicker);
        progressBar = findViewById(R.id.progressBar4);

        mneverBtn = findViewById(R.id.neverBtn);
        msetDateBtn = findViewById(R.id.setDate);
        mregisterBtn = findViewById(R.id.registerBtn);

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


        // when click on set this date button
        msetDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlastDonatedStatus.setText(selectedDate());
            }
        });

        mneverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlastDonatedStatus.setText("never");
            }
        });

        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mregisterBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String fullName = mfullName.getText().toString().trim();
                String address = maddress.getText().toString().trim();
                phone = getIntent().getStringExtra("phone");
                
                if(fullName.isEmpty() || address.isEmpty() || bloodGroup.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> user = new HashMap<>();
                user.put("name", fullName);
                user.put("address", address);
                user.put("bloodGroup", bloodGroup);
                user.put("phone", phone);
                user.put("lastDonatedOn", mlastDonatedStatus.getText());
                user.put("donationCount", mlastDonatedStatus.getText().equals("never") ? 0 : 1);

                db.collection("donors")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegistrationActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, HomepageActivity.class);
                                intent.putExtra("phoneFromRegistration", phone);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mregisterBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Log.w("RegisterActivity", "Error writing document!", e);
                            }
                        });
            }
        });

    }

    String selectedDate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datePicker.getDayOfMonth() + "/");
        stringBuilder.append((datePicker.getMonth() + 1) + "/");
        stringBuilder.append(datePicker.getYear());

        return stringBuilder.toString();
    }


}