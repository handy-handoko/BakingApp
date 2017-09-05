package com.digitalnusantarastudio.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalnusantarastudio.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long playbackPosition;
    int currentWindow;
    boolean playWhenReady;
    private Uri videoUri;
    private String videoUrl;
    private String thumbnailURL;
    private String desc;
    private boolean setData = false;//flag, will true if data already set by setData()
    @BindView(R.id.txtDescription) TextView txtDescription;
    @BindView(R.id.stepImageView) ImageView stepImageView;
    private Unbinder unbinder;
    private final static String TAG = StepDetailFragment.class.getSimpleName();
    private static final String PLAYBACK_POSITION_KEY = "playback";
    private static final String DESCRIPTION_KEY = "desc";


    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Initialize the player view.
        mPlayerView = view.findViewById(R.id.playerView);
        if(setData){//set data true if device is tablet
            releasePlayer();
            if(videoUri != null){
                initializePlayer();
                Log.d(TAG, "create view"+playbackPosition);
            } else if(thumbnailURL!=null){
                releasePlayer();
                videoUri=null;
                Glide.with(this)
                    .load(thumbnailURL)
                    .fallback(R.drawable.no_image)
                    .into(stepImageView);
                stepImageView.setVisibility(View.VISIBLE);
            }else {
                releasePlayer();
                videoUri=null;
                //image from http://www.freeiconspng.com/img/23494
                stepImageView.setImageResource(R.drawable.no_image);
                stepImageView.setVisibility(View.VISIBLE);
            }
            txtDescription.setText(desc);
        }

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
            Log.d(TAG, "view create "+playbackPosition);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
            txtDescription.setText(savedInstanceState.getString(DESCRIPTION_KEY));
            Log.d(TAG, "activity create "+playbackPosition);
        }
    }

    /**
     * Initialize ExoPlayer with data to internet. thanks to codelabs.
     * https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2
     */
    private void initializePlayer() {
        if(videoUri == null){
            return;
        }
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setVisibility(View.VISIBLE);
        stepImageView.setVisibility(View.INVISIBLE);
        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(playWhenReady);
        mExoPlayer.seekTo(currentWindow, playbackPosition);

        MediaSource mediaSource = buildMediaSource(videoUri);
        mExoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("bakingapp"),
                new DefaultExtractorsFactory(), null, null);
    }

    //public void setData
    public void setData(JSONObject jsonObject){
        try {
            if(!jsonObject.getString("videoURL").equals("")){
                videoUrl = jsonObject.getString("videoURL");
                videoUri = Uri.parse(videoUrl);
            } else if(!jsonObject.getString("thumbnailURL").equals("")){
                thumbnailURL = jsonObject.getString("thumbnailURL");
            }
            desc = jsonObject.getString("description");
            setData = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //for one pane activity, just set data because one pane activity using static fragment
    public void showStep(JSONObject jsonObject){
        releasePlayer();
        try {
            if(!jsonObject.getString("videoURL").equals("")){
                videoUri = Uri.parse(jsonObject.getString("videoURL"));
                initializePlayer();
            } else if(!jsonObject.getString("thumbnailURL").equals("")){
                videoUri=null;
                releasePlayer();
                stepImageView.setVisibility(View.VISIBLE);
                Glide.with(this)
                    .load(jsonObject.getString("thumbnailURL"))
                    .fallback(R.drawable.no_image)
                    .into(stepImageView);
            } else {
                videoUri=null;
                releasePlayer();
                //image from http://www.freeiconspng.com/img/23494
                stepImageView.setImageResource(R.drawable.no_image);
                stepImageView.setVisibility(View.VISIBLE);
            }
            txtDescription.setText(jsonObject.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            Log.d(TAG, "start "+playbackPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mPlayerView == null)) {
            initializePlayer();
            Log.d(TAG, "resume "+playbackPosition);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        playbackPosition = mExoPlayer.getCurrentPosition();
        bundle.putLong(PLAYBACK_POSITION_KEY, playbackPosition);
        bundle.putString(DESCRIPTION_KEY, txtDescription.getText().toString());
        super.onSaveInstanceState(bundle);
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

    private void releasePlayer() {
        mPlayerView.setVisibility(View.GONE);
        if (mExoPlayer != null) {
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
