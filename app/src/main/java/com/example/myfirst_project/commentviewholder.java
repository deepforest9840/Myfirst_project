package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class commentviewholder extends RecyclerView.ViewHolder {
    ImageView cuimage;
    ImageView deleteco;
    TextView cuname,cumessage,cudt;
    public commentviewholder(@NonNull View itemView) {
        super(itemView);
        cuimage=itemView.findViewById(R.id.cuimage);
        cuname=itemView.findViewById(R.id.cuname);
        cumessage=itemView.findViewById(R.id.cumessage);
        cudt=itemView.findViewById(R.id.cudt);
        deleteco=itemView.findViewById(R.id.deletecom);

    }



    public void commentdeleteprocess(final String cpostkey, final String postkey) {
        deleteco.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference().child("myvideos").child(postkey).child("comments");
                DatabaseReference dbr = commentRef.child(cpostkey);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String userId = user.getUid();
                //Toast.makeText(itemView.getContext(),"succese",Toast.LENGTH_SHORT).show();

                // Retrieve the 'userid' under the comment post key
                DatabaseReference userIdRef = dbr.child("uid");
                userIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String commentUserId = dataSnapshot.getValue(String.class);

                            // Check if the comment's 'userid' matches the current user's 'userId'
                            if (commentUserId != null && commentUserId.equals(userId)) {
                                // If they match, it's the user's own comment, so you can delete it
                                Task<Void> mTask = dbr.removeValue();
                                mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(itemView.getContext(), "Successfully deleted the comment", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // The comment does not belong to the current user
                                Toast.makeText(itemView.getContext(), "You can only delete your own comments", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors here
                    }
                });
            }
        });
    }







}