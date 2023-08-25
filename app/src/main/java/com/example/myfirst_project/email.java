package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class email extends AppCompatActivity {
    TextInputLayout t1,t2;
    ProgressBar bar;
    FirebaseAuth    mAuth;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        t1=(TextInputLayout)findViewById(R.id.email_login);
        t2=(TextInputLayout)findViewById(R.id.pwd_login);
        bar=(ProgressBar)findViewById(R.id.progressBar2);
        mAuth=FirebaseAuth.getInstance();




    }
    public void success_login(View view){


        bar.setVisibility(View.VISIBLE);
        String email=t1.getEditText().getText().toString();
        String pwd=t2.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(email.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful()){
                            bar.setVisibility(View.INVISIBLE);
                            Intent intent =new Intent(email.this,dashboard.class);
                           startActivity(intent);
                        }
                        else{
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText(" ");
                            Toast.makeText(getApplicationContext(),"invalid email/password",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }



}