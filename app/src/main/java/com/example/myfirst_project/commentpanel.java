package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class commentpanel extends AppCompatActivity {
    EditText commenttext;
    Button commentsubmit;
    DatabaseReference userref,commentref;
    String postkey;
    RecyclerView recview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentpanel);
        postkey=getIntent().getStringExtra("postkey");
        userref= FirebaseDatabase.getInstance().getReference().child("userprofile");
        commentref=FirebaseDatabase.getInstance().getReference().child("myvideos").child(postkey).child("comments");
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId=user.getUid();
        commenttext=(EditText) findViewById(R.id.comment_text);
        commentsubmit=(Button)findViewById(R.id.comment_submit);
        recview=(RecyclerView)findViewById(R.id.comment_recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        commentsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userref.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String username=snapshot.child("uname").getValue().toString();
                            String uimage=snapshot.child("uimage").getValue().toString();
                            processcomment(username,uimage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void processcomment(String username,String uimage){
                String commentpost=commenttext.getText().toString();
                String randompostkey=userId+""+new Random().nextInt(1000);
                Calendar datevalue = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                String cdate = dateFormat.format(datevalue.getTime());
                SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

                String ctime = timeformat.format(datevalue.getTime());

                HashMap<String, Object> cmnt = new HashMap<>();
                cmnt.put("uid", userId);
                cmnt.put("username", username);
                cmnt.put("userimage", uimage);
                cmnt.put("usermsg", commentpost);
                cmnt.put("date", cdate);
                cmnt.put("time", ctime);

                commentref.child(randompostkey).updateChildren(cmnt)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful())
                                    Toast.makeText(getApplicationContext(),"comment Added",Toast.LENGTH_LONG).show();
                                    else
                                    Toast.makeText(getApplicationContext(),task.toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<commentmodel> options=new FirebaseRecyclerOptions.Builder<commentmodel>()
                .setQuery(commentref,commentmodel.class)
                .build();
        FirebaseRecyclerAdapter<commentmodel,commentviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<commentmodel, commentviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull commentviewholder holder, int position, @NonNull commentmodel model) {
                holder.cuname.setText(model.getUsername());
                holder.cumessage.setText(model.getUsermsg());
               // holder.cudt.setText("Date :"+model.getDate()+" Time :"+model.getTime());
                // Assuming model.getDate() and model.getTime() are in "dd-MM-yy" and "HH:mm" format
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                String modelDate = model.getDate();
                String modelTime = model.getTime();

                try {
                    Date commentDateTime = dateFormat.parse(modelDate);
                    Date currentTime = new Date();
                    String[] timeParts = modelTime.split(":");
                    if (timeParts.length == 3) {
                        int hours = Integer.parseInt(timeParts[0]);
                        int minutes = Integer.parseInt(timeParts[1]);
                        int seconds = Integer.parseInt(timeParts[2]);

                        commentDateTime.setHours(hours);
                        commentDateTime.setMinutes(minutes);
                        commentDateTime.setSeconds(seconds);

                        long timeDifference = currentTime.getTime() - commentDateTime.getTime();
                        long seconds2 = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
                        long minutes2 = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
                        long hours2 = TimeUnit.MILLISECONDS.toHours(timeDifference);
                        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

                        if (days > 0) {
                            holder.cudt.setText(days + " days ago");
                        } else if (hours2 > 0) {
                            holder.cudt.setText(hours2 + " hours ago");
                        } else if (minutes2 > 0) {
                            holder.cudt.setText(minutes2 + " minutes ago");
                        } else {
                            if(seconds2==0){
                                holder.cudt.setText( " now");
                            }
                            else {
                                holder.cudt.setText(seconds2 + " seconds ago");
                            }

                        }
                    } else {
                        // Handle invalid time format
                        holder.cudt.setText("Invalid time format");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle any parsing errors
                }



                Glide.with(holder.cuimage.getContext()).load(model.getUserimage()).into(holder.cuimage);
                String cpostkey = getRef(position).getKey();

                holder.commentdeleteprocess(cpostkey,postkey);











            }

            @NonNull
            @Override
            public commentviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_singel_row,parent,false);


                return new commentviewholder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);
    }
}