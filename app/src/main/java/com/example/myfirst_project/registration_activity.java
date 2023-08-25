package com.example.myfirst_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration_activity extends AppCompatActivity {

    TextInputLayout t1,t2;
    ProgressBar bar;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "This a toast message", Toast.LENGTH_LONG).show();
        setContentView(R.layout.registration);
        t1=(TextInputLayout) findViewById(R.id.email);
        t2=(TextInputLayout) findViewById(R.id.pwd);
        bar=(ProgressBar) findViewById(R.id.progressBar);

    }
    public void gotosignin(View view){
        startActivity(new Intent(registration_activity.this, email.class));





    }
    public void signuphere(View view){

        bar.setVisibility(View.VISIBLE);
        String email=t1.getEditText().getText().toString();
        String password=t2.getEditText().getText().toString();
        mAuth =FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(registration_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");


                        }
                        else{

                        }
                    }
                });

    }
}