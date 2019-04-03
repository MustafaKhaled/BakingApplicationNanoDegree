package com.example.bakingapplicationnanodegree.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.ui.RecipeMediaDetailsPhone;
import com.example.bakingapplicationnanodegree.util.MyApplication;
import com.example.bakingapplicationnanodegree.util.VideoPlayerConfig;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeMediaFragment extends Fragment {
    private static final String TAG = "RecipeMediaFragment";
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    private boolean autoPlay = false;
    // used to remember the playback position
    private int currentWindow;
    private long playbackPosition;
    public static final String AUTOPLAY = "autoplay";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";    private long position;
    String mVideoUrl;

    public RecipeMediaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_media_details_fragment, container, false);
        playerView = view.findViewById(R.id.videoFullScreenPlayer);

        setRetainInstance(true);
//        initializePlayer();
        if(getResources().getBoolean(R.bool.isTablet)){

                    initializePlayer();
                    buildMediaSource(Uri.parse(mVideoUrl));


        }
        else {
            RecipeMediaDetailsPhone recipeMediaDetailsPhone = (RecipeMediaDetailsPhone) getActivity();
            if (recipeMediaDetailsPhone != null) {
            setVideo(recipeMediaDetailsPhone.setChosenVideo());
//            buildMediaSource(Uri.parse(mVideoUrl));

            }
//
//            RecipeMediaDetailsPhone recipeMediaDetailsPhone = (RecipeMediaDetailsPhone) getActivity();
//            recipeDesc.setText(recipeMediaDetailsPhone.setChosenData());
        }

//        buildMediaSource(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4"));
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: Orientation Change");
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            playerView.setLayoutParams(params);
        }
    }

    private void initializePlayer() {
        if (simpleExoPlayer == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);

        }
    }

    public void setVideo(String videoUrl){
        this.mVideoUrl = videoUrl;
    }



    private void buildMediaSource( Uri mUri) {
            Log.d(TAG, "buildMediaSource: There is a video for the current step");

            if(mUri!=null){
                playerView.setVisibility(View.VISIBLE);
                // Measures bandwidth during playback. Can be null if not required.
                DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                        Util.getUserAgent(getActivity(), getString(R.string.app_name)), bandwidthMeter);
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mUri);
                // Prepare the player with the source.
                simpleExoPlayer.prepare(videoSource);


            }

        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (simpleExoPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
            outState.putBoolean(AUTOPLAY, autoPlay);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            buildMediaSource(Uri.parse(mVideoUrl));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: onResume Called");
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            // save the player state before releasing its resources
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            autoPlay = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


}