package com.example.myfirst_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class loginpage extends AppCompatActivity {
    TextInputLayout t1,t2;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        t1=(TextInputLayout) findViewById(R.id.email_login);
        t2=(TextInputLayout) findViewById(R.id.pwd_login);
        mAuth=FirebaseAuth.getInstance();


    }

    public void signinhere(View view){
        String email=t1.getEditText().getText().toString();
        String pass=t2.getEditText().getText().toString();


    }
}