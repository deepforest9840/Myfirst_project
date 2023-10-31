package com.example.myfirst_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfirst_project.R;
import com.example.myfirst_project.dashboard_fragment;
import com.example.myfirst_project.databinding.ActivityHomeFragementBinding;
import com.google.firebase.auth.FirebaseAuth;

public class home_fragement2 extends AppCompatActivity {

    ActivityHomeFragementBinding binding2;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityHomeFragementBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());

        // Load the DashboardFragment by default
        //loadFragment(new dashboard_fragment());

        binding2.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

//            if (itemId == R.id.home) {
//                // Load DashboardActivity
//                loadFragment(new dashboard_fragment());
//                return true;
//            } else if
          if  (itemId == R.id.manage_profile) {
                // Load ManageProfileActivity
                //loadFragment(manage_profile.class);
               startActivity(new Intent(getApplicationContext(),manage_profile.class));
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
