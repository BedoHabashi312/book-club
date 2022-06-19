package com.allforus.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allforus.e_book.SignUp.VerifyOTP;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    private RelativeLayout progressbar;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        //Hooks
        ImageView screenIcon = findViewById(R.id.forget_password_icon);
        TextView title = findViewById(R.id.forget_password_title);
        TextView description = findViewById(R.id.forget_password_description);
        countryCodePicker = findViewById(R.id.country_code_picker);
        Button nextBtn = findViewById(R.id.forget_password_next_btn);
        phoneNumber = findViewById(R.id.forget_password_phone_number);
        progressbar = findViewById(R.id.progress_bar);
    }

    public void verifyPhoneNumber(View view) {
        //Validate username and password
        if (!validateFields()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        //Get data
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        String _completePhoneNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + _phoneNumber;

        //Check weather User exists on not in database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDO", "updateData");
                    startActivity(intent);
                    finish();
                    progressbar.setVisibility(View.GONE);

                } else {
                    progressbar.setVisibility(View.GONE);
                    phoneNumber.setError("No such user exist!");
                    phoneNumber.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields() {
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}