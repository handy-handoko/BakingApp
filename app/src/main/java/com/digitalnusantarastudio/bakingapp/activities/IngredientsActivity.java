package com.digitalnusantarastudio.bakingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.fragments.IngredientsFragment;
import com.digitalnusantarastudio.bakingapp.fragments.RecipeStepFragment;

import org.json.JSONArray;
import org.json.JSONException;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent = getIntent();
        String ingredients_json_string = intent.getStringExtra(getString(R.string.ingredients_json_key));
        try {
            JSONArray ingredients_json_array = new JSONArray(ingredients_json_string);
            IngredientsFragment ingredientsFragment = (IngredientsFragment)getSupportFragmentManager().findFragmentById(R.id.ingredients_fragment);
            ingredientsFragment.setData(ingredients_json_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
