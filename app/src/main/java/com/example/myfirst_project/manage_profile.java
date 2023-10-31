package com.example.myfirst_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manage_profile extends AppCompatActivity {
    Button pf,pf3,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
        pf=(Button)findViewById(R.id.pf);
        pf3=(Button) findViewById(R.id.pf3);

        pw=(Button) findViewById(R.id.pw);
        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(manage_profile.this, update_profile.class);
                startActivity(intent);
            }
        });

        pf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(manage_profile.this, manage_video.class);
                startActivity(intent);
            }
        });
        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(manage_profile.this, Reset.class));
            }
        });



    }
}