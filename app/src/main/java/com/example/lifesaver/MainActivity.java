package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lifesaver.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.A;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    Animation splashIconAnimation, appNameAnimation;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        splashIconAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_icon_animation);
        appNameAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_appname_animation);
        binding.appSplashIcon.setAnimation(splashIconAnimation);
        binding.appName.setAnimation(appNameAnimation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(user != null) {
                    intent = new Intent(MainActivity.this, HomepageActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, PhoneAuthActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}