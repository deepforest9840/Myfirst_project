    package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.myfirst_project.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

    public class dashboard extends AppCompatActivity {

FloatingActionButton addvideo;
        DatabaseReference dbreference;
RecyclerView recview;
String abc1,abc2;
DatabaseReference likereference;
        DatabaseReference userRef;
        Boolean testclick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("videostreaming");
        likereference=FirebaseDatabase.getInstance().getReference("likes");
        dbreference= FirebaseDatabase.getInstance().getReference().child("userprofile");
        addvideo=(FloatingActionButton) findViewById(R.id.addvideo);
        addvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        startActivity(new Intent(getApplicationContext(),addvideo.class));

            }
        });

        recview=(RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<filemodel> options=
//                new FirebaseRecyclerOptions.Builder<filemodel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myvideos"),filemodel.class)
//                        .build();
        // Query to fetch user data from userprofile
// Query to fetch user data from userprofile
        DatabaseReference videoRef = FirebaseDatabase.getInstance().getReference().child("myvideos");
        FirebaseRecyclerOptions<filemodel2> options = new FirebaseRecyclerOptions.Builder<filemodel2>()
                .setQuery(videoRef, snapshot -> {
                    if (snapshot.exists()) {
                        String title = snapshot.child("title").getValue(String.class);
                        String vurl = snapshot.child("vurl").getValue(String.class);
                        String uname = snapshot.child("u_nam").getValue(String.class);
                        String uimg=snapshot.child("u_img").getValue(String.class);
                        String userid=snapshot.child("userid").getValue(String.class);

                        // Create a filemodel2 object with only vtitle and vurl for now
                        filemodel2 fileModel2 = new filemodel2(title,  uimg, uname,userid,vurl);

                        // Now, fetch missing values from userprofile



                        return fileModel2;
                    } else {
                        return null;
                    }
                })
                .build();

        FirebaseRecyclerAdapter<filemodel2, myviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<filemodel2, myviewholder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull filemodel2 model) {
                            holder.prepareexoplayer(getApplication(), model.getU_img(), model.getU_nam(), model.getTitle(), model.getVurl());
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userid = firebaseUser.getUid();
                            String postkey = getRef(position).getKey();
                            holder.getlikebuttonstatus(postkey, userid);
                            //holder.u_nam.setText(model.getU_nam());


                            Glide.with(holder.u_img.getContext()).load(model.getU_img()).into(holder.u_img);
                            holder.like_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    testclick = true;
                                    likereference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (testclick) {
                                                if (snapshot.child(postkey).hasChild(userid)) {
                                                    likereference.child(postkey).removeValue();
                                                    testclick = false;
                                                } else {
                                                    likereference.child(postkey).child(userid).setValue(true);
                                                    testclick = false;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });

                            holder.comment_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), commentpanel.class);
                                    intent.putExtra("postkey", postkey);
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singelrow, parent, false);
                            return new myviewholder(view);
                        }
                    };

                    firebaseRecyclerAdapter.startListening();
                    recview.setAdapter(firebaseRecyclerAdapter);

            }




        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.appmenu,menu);
            return super.onCreateOptionsMenu(menu);
        }

       // @SuppressLint("NonConstantResourceId")
       @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            int itemId = item.getItemId();

            if (itemId == R.id.logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(dashboard.this, MainActivity.class));
                finish();
                return true;
            }
            if(itemId==R.id.chat){
                startActivity(new Intent(dashboard.this,chat_activity.class));
            }
           if (itemId == R.id.manage_profile) {

               startActivity(new Intent(dashboard.this, manage_profile.class));

           }

            return super.onOptionsItemSelected(item);
        }
    }