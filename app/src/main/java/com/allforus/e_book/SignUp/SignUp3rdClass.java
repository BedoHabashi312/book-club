package com.allforus.e_book.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ScrollView;

import com.allforus.e_book.R;
import com.allforus.e_book.VerifyOTP;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class SignUp3rdClass extends AppCompatActivity {

    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);

        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        phoneNumber = findViewById(R.id.signup_phone_number);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        countryCodePicker = findViewById(R.id.country_code_picker);

    }

    public void callVerifyOTPScreen(View view) {

        if (!validatePhoneNumber()){
            return;
        }

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String checkspaces = "^\\S*$";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

}