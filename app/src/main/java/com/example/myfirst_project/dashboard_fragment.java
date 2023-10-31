
package com.example.myfirst_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class dashboard_fragment extends Fragment {
    public dashboard_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);

        // Initialize and configure UI elements within the fragment
        FloatingActionButton addVideoButton = view.findViewById(R.id.addvideo);
        RecyclerView recyclerView = view.findViewById(R.id.recview);

        // Set click listener for the add video button
        addVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the add video button click
                startActivity(new Intent(getActivity(), addvideo.class));
            }
        });

        // Configure and populate the RecyclerView within the fragment
        // You can add your RecyclerView adapter and data here
        // Example: recyclerView.setAdapter(adapter);

        return view;
    }
}
