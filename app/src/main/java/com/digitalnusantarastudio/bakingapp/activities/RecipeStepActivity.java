package com.digitalnusantarastudio.bakingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.fragments.RecipeStepFragment;
import com.digitalnusantarastudio.bakingapp.fragments.StepDetailFragment;

import org.json.JSONArray;
import org.json.JSONException;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepFragment.OnListItemClickListener{
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    private int current_step = 0;
    private StepDetailFragment stepDetailFragment;
    JSONArray steps_json_array;
    private static final String TAG = RecipeStepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intent = getIntent();
        String steps_json_string = intent.getStringExtra(getString(R.string.steps_json_key));
        try {
            steps_json_array = new JSONArray(steps_json_string);
        } catch (JSONException e) {
            e.printStackTrace();

            // if string cannot parsed to json, there is something wrong.
            // redirect user back to main activity
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Opss.. Sorry");

            // set dialog message
            alertDialogBuilder
                .setMessage("An error occured. You will redirect to main page.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, redirect to main Activity
                        startActivity(new Intent(RecipeStepActivity.this, MainActivity.class));
                    }
                });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        //find static fragment to set data.
        //got from here https://stackoverflow.com/questions/27293195/pass-data-to-statically-included-fragment-from-fragment-activity
        RecipeStepFragment recipeStepFragment = (RecipeStepFragment)getSupportFragmentManager().findFragmentById(R.id.step_list_fragment);
        recipeStepFragment.setData(steps_json_array);

        // Determine if two-pane or single-pane display
        if(findViewById(R.id.recipe_linear_layout) != null) {
            mTwoPane = true;

            LinearLayout navigation_linear_layout = (LinearLayout)findViewById(R.id.navigation_linear_layout);
            navigation_linear_layout.setVisibility(View.GONE);
        }

        stepDetailFragment = (StepDetailFragment)getSupportFragmentManager().findFragmentById(R.id.step_detail_fragment);
        getSupportActionBar().setTitle(intent.getStringExtra(getString(R.string.recipe_name_key)));
    }

    @Override
    public void onItemClickSelected(int position) {
        if (mTwoPane) {
            //for fragment
            try {
                stepDetailFragment.showStep(steps_json_array.getJSONObject(current_step));
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            //for phone
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(getString(R.string.steps_json_key), steps_json_array.toString());
            intent.putExtra(getString(R.string.position_key), position);
            startActivity(intent);
        }
    }
}
