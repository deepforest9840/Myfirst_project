package com.example.myfirst_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

;


public class CreateAccount extends AppCompatActivity {



    boolean isValid(String mail) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return mail.matches(emailRegex);
    }


    EditText user_name;
    EditText user_mail,user_password,confirm_password;
    FirebaseAuth mAuth;
    Button submit;
    boolean passvis1,passvis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        user_name = findViewById(R.id.User);
        user_mail = findViewById(R.id.sign_email);
        user_password = findViewById(R.id.first_password);
        confirm_password = findViewById(R.id.confirm_password);
        submit = findViewById(R.id.submit);

        user_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= user_password.getRight() - user_password.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = user_password.getSelectionEnd();
                        if (passvis1) {
                            user_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visoff, 0);
                            user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passvis1 = false;
                        } else {
                            user_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vison, 0);
                            user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passvis1= true;
                        }
                        user_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });



        confirm_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= confirm_password.getRight() - confirm_password.getCompoundDrawables()[right].getBounds().width()) {
                        int selection = confirm_password.getSelectionEnd();
                        if (passvis2) {
                            confirm_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visoff, 0);
                            confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passvis2 = false;
                        } else {
                            confirm_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vison, 0);
                            confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passvis2= true;
                        }
                        confirm_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = user_name.getText().toString();
                String email = user_mail.getText().toString();
                String password = user_password.getText().toString();
                String confirmPassword = confirm_password.getText().toString();

                if (name.length() == 0 || email.length() == 0 || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the information", Toast.LENGTH_LONG).show();
                } else if (!isValid(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "password length must be greater than 5", Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if(task.isSuccessful())
                            {
                                List<String> signInMethod= task.getResult().getSignInMethods();
                                if(signInMethod!=null && !signInMethod.isEmpty())
                                {
                                    Toast.makeText(CreateAccount.this,"An account with is email already exist. plz log in",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CreateAccount.this, "success", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(CreateAccount.this, Login.class));

                                            } else {
                                                Toast.makeText(CreateAccount.this, "something wrong" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                //startActivity(new Intent(CreateAccount.this, CreateAccount.class));
                                                finish();

                                            }
                                        }
                                    });
                             }

                            }
                            else
                            {
                                Toast.makeText(CreateAccount.this,"Error checking Mail:" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }



            }
        });


    }


}
