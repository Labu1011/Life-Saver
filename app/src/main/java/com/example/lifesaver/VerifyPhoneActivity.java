package com.example.lifesaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lifesaver.databinding.ActivityVerifyPhoneBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyPhoneActivity extends AppCompatActivity {
    private ActivityVerifyPhoneBinding binding;
    private boolean isNewUser;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // getting new user status through intent extra string
        isNewUser = Boolean.parseBoolean(getIntent().getStringExtra("isNewUser"));

        binding.phoneNumber.setText(String.format(String.format("+880-%s", getIntent().getStringExtra("phone"))));

        verificationId = getIntent().getStringExtra("verificationId");

        binding.verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifyPhoneActivity.this, "OTP send successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar2.setVisibility(View.VISIBLE);
                binding.verifyButton.setVisibility(View.INVISIBLE);

                if(binding.otpEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyPhoneActivity.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                } else {
                    if(verificationId != null) {
                        String code = binding.otpEditText.getText().toString().trim();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent;
                                    if(isNewUser) {
                                        intent = new Intent(VerifyPhoneActivity.this, RegistrationActivity.class);
                                    } else {
                                        intent = new Intent(VerifyPhoneActivity.this, HomepageActivity.class);
                                    }

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    binding.progressBar2.setVisibility(View.GONE);
                                    binding.verifyButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(VerifyPhoneActivity.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}