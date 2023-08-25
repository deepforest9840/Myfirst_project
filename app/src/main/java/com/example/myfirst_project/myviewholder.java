package com.example.myfirst_project;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
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
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public class myviewholder extends RecyclerView.ViewHolder {


    StyledPlayerView styledPlayerView;
    SimpleExoPlayer player;
    TextView vtitleview;
    SimpleExoPlayer simpleExoPlayer;
    //SimpleExoPlayerView simpleExoPlayerView;

    public myviewholder(@NonNull View itemView) {
        super(itemView);

        vtitleview=itemView.findViewById(R.id.vtitle);
        styledPlayerView=itemView.findViewById(R.id.exoplayerview);
        player = new SimpleExoPlayer.Builder(itemView.getContext()).build();
        styledPlayerView.setPlayer(player);

        // Enable player controls
        styledPlayerView.setUseController(true);

    }

    void prepareexoplayer(Application application, String videotitle, String videourl){


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
