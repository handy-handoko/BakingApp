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
import com.digitalnusantarastudio.bakingapp.fragments.IngredientsFragment;
import com.digitalnusantarastudio.bakingapp.fragments.RecipeStepFragment;
import com.digitalnusantarastudio.bakingapp.fragments.StepDetailFragment;

import org.json.JSONArray;
import org.json.JSONException;

public class RecipeStepActivity extends AppCompatActivity implements
        RecipeStepFragment.OnListItemClickListener,
        RecipeStepFragment.OnIngredientsClickListener{
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
//    private boolean mIsStepFragment = false;//set to true if user using tab and access step fragment
    private StepDetailFragment stepDetailFragment = null;
    private JSONArray steps_json_array;
    private JSONArray ingredients_json_array;
    private int recipe_id;
    private static final String TAG = RecipeStepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intent = getIntent();
        String steps_json_string = intent.getStringExtra(getString(R.string.steps_json_key));
        String ingredients_json_string = intent.getStringExtra(getString(R.string.ingredients_json_key));
        recipe_id = intent.getIntExtra(getString(R.string.recipe_id_json_key), 0);

        try {
            steps_json_array = new JSONArray(steps_json_string);
            ingredients_json_array = new JSONArray(ingredients_json_string);
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

            //set ingredients fragment as default.
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setData(recipe_id, ingredients_json_array);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ingredientsFragment)
                .commit();
        }

        getSupportActionBar().setTitle(intent.getStringExtra(getString(R.string.recipe_name_key)));
    }

    @Override
    public void onItemClickSelected(int position) {
        if (mTwoPane) {
            //for fragment
            if(stepDetailFragment == null){//if detail fragment null mean active fragment is ingredients fragment
                this.stepDetailFragment = new StepDetailFragment();

                try {
                    stepDetailFragment.setData(steps_json_array.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
                    return;
                }

                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, stepDetailFragment)
                    .commit();
            }
        } else {
            //for phone
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(getString(R.string.steps_json_key), steps_json_array.toString());
            intent.putExtra(getString(R.string.position_key), position);
            startActivity(intent);
        }
    }

    @Override
    public void onIngredientsClick() {
        if (mTwoPane) {
            //for fragment
            if(stepDetailFragment != null){//if active fragment is stepDetailFragment
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setData(recipe_id, ingredients_json_array);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ingredientsFragment)
                        .commit();
                stepDetailFragment = null;//empty stepDetailFragment
            }
            //if current active fragment is IngredientsFragment, so nothing to do
        } else {
            //for phone
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra(getString(R.string.ingredients_json_key), ingredients_json_array.toString());
            startActivity(intent);
        }
    }
}
