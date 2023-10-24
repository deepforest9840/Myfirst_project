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




    public  void commentdeleteprocess(final String cpostkey,final String postkey){

        deleteco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference commentref=FirebaseDatabase.getInstance().getReference().child("myvideos").child(postkey).child("comments");
                DatabaseReference dbr=commentref.child(cpostkey);
                Toast.makeText(itemView.getContext(),"successful to delete that comment",Toast.LENGTH_SHORT).show();
                Task<Void> mTask=dbr.removeValue();
                mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(itemView.getContext(),"success to delete",Toast.LENGTH_SHORT);
                    }
                });

            }
        });





    }







}