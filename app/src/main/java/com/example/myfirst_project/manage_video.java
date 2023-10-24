package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class manage_video extends AppCompatActivity {
    RecyclerView recview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_video);
        recview=(RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        // Get the current user's ID
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the "myvideos" node in Firebase
        DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference().child("myvideos");

        // Query to fetch videos that match the current user's ID
        FirebaseRecyclerOptions<manage_video_model> options = new FirebaseRecyclerOptions.Builder<manage_video_model>()
                .setQuery(videoRef.orderByChild("userid").equalTo(currentUserId), manage_video_model.class)
                .build();


        FirebaseRecyclerAdapter<manage_video_model,manage_video_viewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<manage_video_model,manage_video_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull manage_video_viewholder holder, int position, @NonNull manage_video_model model) {
                String postkey = getRef(position).getKey();
                holder.prepareexoplayer(getApplication(), model.getTitle(), model.getVurl(),postkey);
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userid = firebaseUser.getUid();

                holder.getlikebuttonstatus2(postkey, userid);




            }



            @NonNull
            @Override
            public manage_video_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_video_singel_row,parent,false);


                return new manage_video_viewholder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);








    }
}