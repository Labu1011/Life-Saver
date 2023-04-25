package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity {
    Button mfindDonor, mdonorList, maboutUs, mfaq, mlogout, mbloodBankList;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        getSupportActionBar().hide();

        mfindDonor = findViewById(R.id.findDonor);
        mdonorList = findViewById(R.id.donorList);
        mfaq = findViewById(R.id.faq);
        maboutUs = findViewById(R.id.aboutUs);
        mbloodBankList = findViewById(R.id.bloodBankList);

        // logout button click event
        mlogout = findViewById(R.id.logout);
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(HomepageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // button click intent change
        mfindDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, FindDonorActivity.class);
                startActivity(intent);
            }
        });
        mdonorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, DonorListActivity.class);
                startActivity(intent);
            }
        });

        mbloodBankList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, BloodBankListActivity.class);
                startActivity(intent);
            }
        });

        mfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        maboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

    }
}