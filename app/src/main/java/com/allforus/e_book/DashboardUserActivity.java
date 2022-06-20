package com.allforus.e_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class DashboardUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        TextView textView = findViewById(R.id.textView3);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailFromSession();

        String fullName = usersDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);
        textView.setText(fullName + "\n" + phoneNumber);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}