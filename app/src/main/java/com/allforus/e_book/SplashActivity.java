package com.allforus.e_book;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.allforus.e_book.OnBoarding.OnBoarding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.firebaseAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(this::checkUser, 4000);
    }

    public void checkUser() {
        FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(getApplicationContext(), OnBoarding.class));
            finish();
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userType = "" + snapshot.child("userType").getValue();
                if (userType.equals("user")) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplicationContext(), DashboardUserActivity.class));
                    SplashActivity.this.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
