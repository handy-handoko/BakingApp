package com.digitalnusantarastudio.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.digitalnusantarastudio.bakingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class StepDetailFragment extends Fragment {
//    @BindView(R.id.txtDescription) TextView txtDescription;
    TextView txtDescription;
    VideoView videoView;
    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);

        return view;
    }

    public void showStep(JSONObject jsonObject){
        try {
            txtDescription.setText(jsonObject.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
