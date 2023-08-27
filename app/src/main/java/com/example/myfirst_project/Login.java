package com.example.myfirst_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    boolean isValid(String mail) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return mail.matches(emailRegex);
    }
    EditText em, password;
    TextView form,create;
    Button sub,changepass;
    FirebaseAuth fAuth;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        em = findViewById(R.id.email);
        password = findViewById(R.id.editTextTextPassword);
        fAuth=FirebaseAuth.getInstance();
        create = findViewById(R.id.create_ac);
        sub = findViewById(R.id.submit);
        form = findViewById(R.id.forgot);
        changepass=findViewById(R.id.changepass);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, CreateAccount.class));
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Reset.class));
            }
        });



        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=password.getRight()-password.getCompoundDrawables()[right].getBounds().width())
                    {
                        int selection=password.getSelectionEnd();
                        if(passwordVisible)
                        {
                            password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visoff,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;

                        }
                        else{
                            password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vison,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;

                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail=new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to received reset link");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this,"Reset link sent to your email",Toast.LENGTH_LONG).show();
                            }

                        });fAuth.sendPasswordResetEmail(mail).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error ! Reset link is not sent" + e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordResetDialog.create().show();

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_mail = em.getText().toString();
                String pass = password.getText().toString();

                if (user_mail.length() == 0 || pass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the information", Toast.LENGTH_LONG).show();
                } else if (isValid(user_mail) == false) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
                }

                fAuth.signInWithEmailAndPassword(user_mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this,"log in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), dashboard.class));
                        }
                        else {
                            Toast.makeText(Login.this,"Forget something" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    }
                });

            }
        });

    }
}