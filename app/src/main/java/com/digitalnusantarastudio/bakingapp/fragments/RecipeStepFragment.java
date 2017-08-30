package com.digitalnusantarastudio.bakingapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.adapter.RecipeStepRecyclerViewAdapter;

import org.json.JSONArray;

public class RecipeStepFragment extends Fragment implements RecipeStepRecyclerViewAdapter.ListItemClickListener{
    private RecipeStepRecyclerViewAdapter adapter;
    private JSONArray recipe_json_array;
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment_layout, container, false);

        RecyclerView recipe_recycler_view = view.findViewById(R.id.recipe_recycler_view);
        recipe_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecipeStepRecyclerViewAdapter(recipe_json_array, this);
        recipe_recycler_view.setAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(int position) {

    }
}
