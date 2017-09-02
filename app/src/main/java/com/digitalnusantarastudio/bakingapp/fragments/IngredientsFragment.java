package com.digitalnusantarastudio.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.adapter.IngredientsAdapter;

import org.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment{
    private IngredientsAdapter adapter;
    private static final String TAG = IngredientsFragment.class.getSimpleName();

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);

        //set adapter without data. activity will set data later.
        adapter = new IngredientsAdapter();
        RecyclerView ingredients_recycler_view = view.findViewById(R.id.ingredients_recycler_view);
        ingredients_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredients_recycler_view.setAdapter(adapter);
        return view;
    }

    public void setData(JSONArray ingredients_json_array){
        adapter.setData(ingredients_json_array);
    }

}
