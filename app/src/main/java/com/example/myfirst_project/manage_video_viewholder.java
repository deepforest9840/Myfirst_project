package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class manage_video_viewholder   extends RecyclerView.ViewHolder {

    StyledPlayerView styledPlayerView;
    SimpleExoPlayer player;
    TextView vtitleview;

    ImageView delete;


    TextView like_text;

    DatabaseReference likereference;
    SimpleExoPlayer simpleExoPlayer;
    //SimpleExoPlayerView simpleExoPlayerView;

    public manage_video_viewholder(@NonNull View itemView) {
        super(itemView);
        vtitleview=itemView.findViewById(R.id.vtitle2);

        delete=itemView.findViewById(R.id.delete2);

        styledPlayerView=itemView.findViewById(R.id.exoplayerview);
        //like_btn=(ImageView)itemView.findViewById(R.id.like_btn);
        like_text=(TextView)itemView.findViewById(R.id.like_c);
        player = new SimpleExoPlayer.Builder(itemView.getContext()).build();
        styledPlayerView.setPlayer(player);
       // comment_btn=(ImageView)itemView.findViewById(R.id.comment_btn);
        // Enable player controls
        styledPlayerView.setUseController(true);








    }
    public  void getlikebuttonstatus2(final String postkey, final String userid){
        likereference= FirebaseDatabase.getInstance().getReference("likes");
        likereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postkey).hasChild(userid)){
                    int likecount=0;
                            likecount=(int)snapshot.child(postkey).getChildrenCount();
                    like_text.setText(likecount+" likes");
                  //  like_btn.setImageResource(R.drawable.baseline_favorite_24);

                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    void prepareexoplayer(Application application, String videotitle, String videourl, String postkey) {
        try {
            Uri videoURI = Uri.parse(videourl);

            delete.setOnClickListener(new View.OnClickListener() { //////
                @Override
                public void onClick(View view) {
                    // Create an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Confirm Deletion");
                    builder.setMessage("Are you sure you want to delete this video?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("myvideos").child(postkey);
                            // Toast.makeText(itemView.getContext(), postkey, Toast.LENGTH_SHORT).show();
                            Task<Void> mTask = dbr.removeValue();
                            mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(itemView.getContext(), "Video deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(), "Video not deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            vtitleview.setText(videotitle);

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(application, "exoplayer_video");
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoURI));

            player.prepare(mediaSource);
            player.setPlayWhenReady(false);
        } catch (Exception ex) {
            Log.d("exoplayer is unfortunately crashed", ex.getMessage().toString());
        }
    }

}