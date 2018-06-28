package com.codepriest.com.journalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepriest.com.journalapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {
    TextInputEditText mEmail;
    TextInputEditText mPassword;
    TextInputEditText mConfirmPassword;
    Button mRegistrationButton;
    ProgressBar mProgressbar;
    View mRegistrationForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        mRegistrationForm = findViewById(R.id.registration_layout_id);
        mEmail = findViewById(R.id.registeration_email_id);
        mProgressbar = findViewById(R.id.registration_progress_id);
        mPassword = findViewById(R.id.registeration_password_id);
        mConfirmPassword = findViewById(R.id.registeration_confirm_password_id);
        mRegistrationButton = findViewById(R.id.registration_button_id);
        mRegistrationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mProgressbar.setVisibility(View.VISIBLE);
        mRegistrationForm.setVisibility(View.INVISIBLE);
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();
        View focus = new View(this);
        boolean cancel = false;
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("email is required");
            focus = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError("invalid email");
            focus = mEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("password is required");
            focus = mPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPassword.setError("invalid email");
            focus = mPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword.setError("confirm password");
            focus = mConfirmPassword;
            cancel = true;
        } else if (!password.equals(confirmPassword)) {
            mConfirmPassword.setError("password must be the same ");
            focus = mConfirmPassword;
            cancel = true;
        }
        if (cancel) {
            focus.requestFocus();
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(this);
        }

    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {

            Intent intent = new Intent(this, JournalListActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(RegistrationActivity.class.getSimpleName(), task.getException().getMessage());
            mProgressbar.setVisibility(View.INVISIBLE);
            mRegistrationForm.setVisibility(View.VISIBLE);
        }

    }
}
