package com.example.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.Constants;
import com.example.bakingapp.R;
import com.example.bakingapp.model.StepList;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment {

    private StepList stepList;
    private Unbinder unbinder;
    private SimpleExoPlayer simpleExoPlayer;
    long currentPosition = 0;
    boolean play = true;

    @BindView(R.id.step_description)
    TextView descView;

    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.step_image)
    ImageView image;

    private static final String VIDEO_POSITION_KEY = "VIDEO_POSITION_KEY";
    private static final String PLAY = "PLAY";

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constants.STEP_FRAGMENT_KEY)) {
            stepList = getArguments().getParcelable(Constants.STEP_FRAGMENT_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        unbinder = ButterKnife.bind(this,rootView);

        if (stepList != null) {
            descView.setText(stepList.getDescription());

            if(!TextUtils.isEmpty(stepList.getThumbnailURL())){
                image.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(stepList.getThumbnailURL())
                        .into(image);
            }
        }
        return rootView;
    }

    private void initializePlayer(Uri uri) {
        if (simpleExoPlayer == null) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            playerView.setPlayer(simpleExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource source = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            simpleExoPlayer.prepare(source);
            if (currentPosition != 0)
                simpleExoPlayer.seekTo(currentPosition);

            simpleExoPlayer.setPlayWhenReady(play);
            playerView.setVisibility(View.VISIBLE);
        }
    }


    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            play = simpleExoPlayer.getPlayWhenReady();
            currentPosition = simpleExoPlayer.getCurrentPosition();

            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(stepList.getVideoURL()))
            initializePlayer(Uri.parse(stepList.getVideoURL()));

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(VIDEO_POSITION_KEY, currentPosition);
        outState.putBoolean(PLAY, play);
    }
}
