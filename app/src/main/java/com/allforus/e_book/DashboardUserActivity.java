package com.allforus.e_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DashboardUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}