package com.example.myfirst_project;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Reset extends AppCompatActivity {

    private EditText emailEditText;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button submitButton;
    boolean is_visible1,is_visible2,isIs_visible3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        emailEditText = findViewById(R.id.emailEditText); // Add this line
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        submitButton = findViewById(R.id.submitPasswordChangeButton);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = emailEditText.getText().toString(); // Retrieve the entered email
//                String oldPassword = oldPasswordEditText.getText().toString();
//                String newPassword = newPasswordEditText.getText().toString();
//                String confirmPassword = confirmPasswordEditText.getText().toString();
//
//                // Check if new password matches the confirm password
//                if (!newPassword.equals(confirmPassword)) {
//                    // Display an error message to the user
//                    Toast.makeText(Reset.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(newPassword.length()<6)
//                {
//                    Toast.makeText(Reset.this,"Password length is minimum 6",Toast.LENGTH_LONG).show();
//                }
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user!=null) {
//
//                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
//
//                    user.reauthenticate(credential)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        user.updatePassword(newPassword)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            Toast.makeText(Reset.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
//                                                        } else {
//                                                            Toast.makeText(Reset.this, "Password change failed", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                });
//                                    } else {
//                                        Toast.makeText(Reset.this, "Reauthentication failed. Please check your email and old password", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else {
//                    Toast.makeText(Reset.this, "User is not authenticated", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//        oldPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                final int right=2;
//                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
//                    if(motionEvent.getRawX()>=oldPasswordEditText.getRight()-oldPasswordEditText.getCompoundDrawables()[right].getBounds().width())
//                    {
//                        int selection=oldPasswordEditText.getSelectionEnd();
//                        if(is_visible1)
//                        {
//                            oldPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visoff,0);
//                            oldPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            is_visible1=false;
//
//                        }
//                        else{
//                            oldPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vison,0);
//                            oldPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            is_visible1=true;
//
//                        }
//                        oldPasswordEditText.setSelection(selection);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//        newPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                final int right=2;
//                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
//                    if(motionEvent.getRawX()>=newPasswordEditText.getRight()-newPasswordEditText.getCompoundDrawables()[right].getBounds().width())
//                    {
//                        int selection=newPasswordEditText.getSelectionEnd();
//                        if(is_visible2)
//                        {
//                            newPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visoff,0);
//                            newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            is_visible2=false;
//
//                        }
//                        else{
//                            newPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vison,0);
//                            newPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            is_visible2=true;
//
//                        }
//                        newPasswordEditText.setSelection(selection);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//
//        confirmPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                final int right=2;
//                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
//                    if(motionEvent.getRawX()>=confirmPasswordEditText.getRight()-confirmPasswordEditText.getCompoundDrawables()[right].getBounds().width())
//                    {
//                        int selection=confirmPasswordEditText.getSelectionEnd();
//                        if(isIs_visible3)
//                        {
//                            confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visoff,0);
//                            confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            isIs_visible3=false;
//
//                        }
//                        else{
//                            confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vison,0);
//                            confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            isIs_visible3=true;
//
//                        }
//                        confirmPasswordEditText.setSelection(selection);
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//
//
//




    }
}
