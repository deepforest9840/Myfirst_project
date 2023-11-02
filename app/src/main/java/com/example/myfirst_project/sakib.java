package com.example.myfirst_project;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sakib extends AppCompatActivity {




    void sendEmail() {
        String[] recipientEmails = {"sakib.cse.du@gmail.com"}; // Replace with the recipient's email address
        String subject = "subject"; // Set the email subject
        String message = "Your email message here"; // Set the email message

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientEmails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (ActivityNotFoundException e) {
            // Handle errors if no email client is installed
            Toast.makeText(this, "No email client installed on this device.", Toast.LENGTH_SHORT).show();
        }
    }



    TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakib);

        TextView emailTextView = findViewById(R.id.mail);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });



    }
}