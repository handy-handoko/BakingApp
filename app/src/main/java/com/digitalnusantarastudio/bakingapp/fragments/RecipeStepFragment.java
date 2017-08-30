package com.digitalnusantarastudio.bakingapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.activities.RecipeStepActivity;
import com.digitalnusantarastudio.bakingapp.adapter.RecipeStepRecyclerViewAdapter;

import org.json.JSONArray;

public class RecipeStepFragment extends Fragment implements RecipeStepRecyclerViewAdapter.ListItemClickListener{
    private RecipeStepRecyclerViewAdapter adapter;
    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment_layout, container, false);

        //set adapter without data. activity will set data later.
        adapter = new RecipeStepRecyclerViewAdapter( this);
        RecyclerView recipe_recycler_view = view.findViewById(R.id.recipe_recycler_view);
        recipe_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipe_recycler_view.setAdapter(adapter);

        return view;
    }

    public void setData(JSONArray recipe_json_array){
        adapter.setData(recipe_json_array);
    }

    @Override
    public void onListItemClick(int position) {

    }
}
