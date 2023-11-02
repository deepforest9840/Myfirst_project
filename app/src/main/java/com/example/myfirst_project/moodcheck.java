package com.example.myfirst_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class moodcheck extends AppCompatActivity {
    Switch switcher;
    boolean nightmode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodcheck);
       // getSupportActionBar().hide();
        switcher = findViewById(R.id.switch1);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
// Set the default night mode to false (day mode)
        nightmode = sharedPreferences.getBoolean("night", false);

// Initialize the night mode based on the default value
        AppCompatDelegate.setDefaultNightMode(nightmode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        switcher.setChecked(nightmode);

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle between day and night mode
                nightmode = !nightmode;
                AppCompatDelegate.setDefaultNightMode(nightmode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

                // Save the current night mode in SharedPreferences
                editor = sharedPreferences.edit();
                editor.putBoolean("night", nightmode);
                editor.apply();
            }
        });


    }
}