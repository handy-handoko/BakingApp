package com.digitalnusantarastudio.bakingapp.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.adapter.IngredientsAdapter;
import com.digitalnusantarastudio.bakingapp.data.IngredientContract;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment{
    private IngredientsAdapter adapter= new IngredientsAdapter();
    private int recipe_id = 0;
    private static final String TAG = IngredientsFragment.class.getSimpleName();
    private Unbinder unbinder;

    public IngredientsFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);

        RecyclerView ingredients_recycler_view = view.findViewById(R.id.ingredients_recycler_view);
        ingredients_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredients_recycler_view.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.show_ingredients_button)
    public void onButtonIngredientClick(){
        JSONArray data = adapter.getData();

        ContentValues[] cv = new ContentValues[data.length()];
        for (int i = 0; i < data.length(); i++) {
            ContentValues contentValues = new ContentValues();

            try {
                contentValues.put(IngredientContract.IngredientEntry.COLUMN_QUANTITY, data.getJSONObject(i).getString("quantity"));
                contentValues.put(IngredientContract.IngredientEntry.COLUMN_MEASURE, data.getJSONObject(i).getString("measure"));
                contentValues.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NAME, data.getJSONObject(i).getString("ingredient"));
                contentValues.put(IngredientContract.IngredientEntry.COLUMN_RECIPE_ID, recipe_id);
                cv[i] = contentValues;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getActivity().getContentResolver().delete(IngredientContract.IngredientEntry.CONTENT_URI, null, null);
        getActivity().getContentResolver().bulkInsert(IngredientContract.IngredientEntry.CONTENT_URI, cv);
    }

    public void setData(int recipe_id, JSONArray ingredients_json_array){
        this.recipe_id = recipe_id;
        adapter.setData(ingredients_json_array);
    }

}
