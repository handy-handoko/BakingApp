package com.digitalnusantarastudio.bakingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.fragments.RecipeStepFragment;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;

public class RecipeStepActivity extends AppCompatActivity {
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    JSONArray steps_json_array;
    private static final String TAG = RecipeStepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intent = getIntent();
        String steps_json_string = intent.getStringExtra(getString(R.string.steps_json_key));
        Log.d(TAG, steps_json_string);
        try {
            steps_json_array = new JSONArray(steps_json_string);
        } catch (JSONException e) {
            //TODO an error occured redirect back to Main Activity
            e.printStackTrace();
        }

        RecipeStepFragment recipeStepFragment = (RecipeStepFragment)getSupportFragmentManager().findFragmentById(R.id.step_list_fragment);
        recipeStepFragment.setData(steps_json_array);

        // Determine if two-pane or single-pane display
        if(findViewById(R.id.recipe_linear_layout) != null) {
            mTwoPane = true;

            LinearLayout navigation_linear_layout = (LinearLayout)findViewById(R.id.navigation_linear_layout);
            navigation_linear_layout.setVisibility(View.GONE);
        }
    }
}
