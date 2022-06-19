package com.allforus.e_book;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SetNewPassword extends AppCompatActivity {

    RelativeLayout progressbar;

    //Get Data Variables
    TextInputLayout confirmPassword, newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        progressbar = findViewById(R.id.progress_bar_password);
        newpassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);

    }

    public void goToHomeFromSetNewPassword(View view) {
    }

    public void setNewPasswordBtn(View view) {

        //Validate Password
        if (!validatePassword() | !validateConfirmPassword()) {
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        //Get data from fields
        String _newPassword = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        //Update Data in firebase and in Sessions
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class));
        finish();

    }

    private boolean validatePassword() {
        String val = Objects.requireNonNull(newpassword.getEditText()).getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        String Letter = "^" +
                "(?=.*[a-zA-Z])" +  //any letter
                ".{4,}" +
                "$";

        if (val.isEmpty()) {
            newpassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            newpassword.setError("Password should contain 4 characters!");
            return false;
        } else if (!val.matches(Letter)) {
            newpassword.setError("Password should contain at least one letter");
            return false;
        } else {
            newpassword.setError(null);
            newpassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString().trim();
        String confirm = Objects.requireNonNull(newpassword.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            confirmPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(confirm)) {
            confirmPassword.setError("Password doesn't match");
            return false;
        } else {
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }
    }

}