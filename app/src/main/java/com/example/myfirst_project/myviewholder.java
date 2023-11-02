package com.example.myfirst_project;

import android.annotation.SuppressLint;
import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.SimpleExoPlayerView;

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerView;
//import com.google.android.exoplayer2.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


import com.google.android.exoplayer2.upstream.BandwidthMeter;
import org.w3c.dom.Text;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public class myviewholder extends RecyclerView.ViewHolder {


    StyledPlayerView styledPlayerView;
    SimpleExoPlayer player;
    TextView vtitleview;

    ImageView like_btn;
    ImageView download;
    ImageView u_img;
    TextView u_nam;
    TextView like_text;
    ImageView comment_btn;
    DatabaseReference likereference;
    SimpleExoPlayer simpleExoPlayer;
    //SimpleExoPlayerView simpleExoPlayerView;

    public myviewholder(@NonNull View itemView) {
        super(itemView);
        u_img=itemView.findViewById(R.id.u_img);
        u_nam=itemView.findViewById(R.id.u_nam);
        vtitleview=itemView.findViewById(R.id.vtitle);
        styledPlayerView=itemView.findViewById(R.id.exoplayerview);
        like_btn=(ImageView)itemView.findViewById(R.id.like_btn);
        like_text=(TextView)itemView.findViewById(R.id.like_text);
        download= (ImageView)itemView.findViewById(R.id.download);
        player = new SimpleExoPlayer.Builder(itemView.getContext()).build();
        styledPlayerView.setPlayer(player);
        comment_btn=(ImageView)itemView.findViewById(R.id.comment_btn);
        // Enable player controls
        styledPlayerView.setUseController(true);


    }
      @SuppressLint("SuspiciousIndentation")
    public  void getlikebuttonstatus(final String postkey, final String userid){
                likereference= FirebaseDatabase.getInstance().getReference("likes");
                    likereference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(postkey).hasChild(userid)){
                                int likecount=(int)snapshot.child(postkey).getChildrenCount();
                                like_text.setText(likecount+"likes");
                                like_btn.setImageResource(R.drawable.baseline_favorite_24);

                            }
                            else{
                                int likecount=(int)snapshot.child(postkey).getChildrenCount();
                                like_text.setText(likecount+"likes");
                                like_btn.setImageResource(R.drawable.baseline_favorite_border_24);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
    }

    void prepareexoplayer(Application application,String u_img1,String u_nam1, String videotitle, String videourl){


        try{
//            vtitleview.setText(videotitle);
//           BandwidthMeter bandwidthMeter = null;
//          //  TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//           // simpleExoPlayer =(SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application,trackSelector);
//
//            Uri videoURI = Uri.parse(videourl);
////            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
////                    application,
////                    "exoplayer_video"
////            );
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory("exoplayer_video");
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
//            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(videoURI);
//
//           // styledPlayerView.setPlayer(player);
//
//         //   Toast.makeText(getApplicationContext(), "This a toast message", Toast.LENGTH_LONG).show();
//          //  simpleExoPlayer.prepare(mediaSource);
//            player.setPlayWhenReady(false);
//
//           // Log.d("explayer is unfortunately crashed");
            vtitleview.setText(videotitle);
            u_nam.setText(u_nam1);

            Uri videoURI = Uri.parse(videourl);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                    application,
                    "exoplayer_video"
            );
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoURI));

            // Set the media source to the player
            player.prepare(mediaSource);
            player.setPlayWhenReady(false);

        }catch (Exception ex){
            Log.d("explayer is unfortunately crashed",ex.getMessage().toString());
        }
    }

}
