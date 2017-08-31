package com.digitalnusantarastudio.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.fragments.StepDetailFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepDetailActivity extends AppCompatActivity {
    private JSONArray steps_json_array;
    private int current_step = 0;
    private StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();
        String steps_json_string = intent.getStringExtra(getString(R.string.steps_json_key));
        try {
            steps_json_array = new JSONArray(steps_json_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stepDetailFragment = (StepDetailFragment)getSupportFragmentManager().findFragmentById(R.id.step_detail_fragment);
    }

    public void prev(View view){
        if(current_step == 0){
            Toast.makeText(this, "This is first step", Toast.LENGTH_SHORT).show();
        }else{
            current_step -= 1;
            load_data();
        }
    }

    public void next(View view){
        if(current_step == steps_json_array.length()-1){
            Toast.makeText(this, "This is last step", Toast.LENGTH_SHORT).show();
        }else{
            current_step += 1;
            load_data();
        }
    }

    private void load_data(){
        JSONObject step_json_object;
        try {
            step_json_object = steps_json_array.getJSONObject(current_step);
            Log.d("StepDetailFragment", step_json_object.getString("videoURL"));
        } catch (JSONException e) {
            e.printStackTrace();
            //if error occured, show toast message and return to avoid crash
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
            return;
        }
        stepDetailFragment.showStep(step_json_object);
    }
}
