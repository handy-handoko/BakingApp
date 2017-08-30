package com.digitalnusantarastudio.bakingapp.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.adapter.RecipeStepRecyclerViewAdapter;

import java.io.IOException;

import butterknife.BindView;

public class StepDetailFragment extends Fragment {
    @BindView(R.id.txtDescription) TextView txtDescription;
    @BindView(R.id.videoView) VideoView videoView;

    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        String url = "http://go.udacity.com/android-baking-app-json";
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);

//        try {
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(getActivity(), uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return view;
    }
}
