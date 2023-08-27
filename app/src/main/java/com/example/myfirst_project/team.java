package com.example.myfirst_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class team extends AppCompatActivity {

    TextView click1,click2,click3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        click1=findViewById(R.id.click1);
        click2=findViewById(R.id.click2);
        click3=findViewById(R.id.click3);

        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(team.this,sakib.class));
            }
        });

        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(team.this,ferdous.class));
            }
        });

        click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(team.this,sourove.class));
            }
        });
    }
}