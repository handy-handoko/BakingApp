package com.digitalnusantarastudio.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnListItemClickListener mCallback;

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment_layout, container, false);

        //set adapter without data. activity will set data later.
        adapter = new RecipeStepRecyclerViewAdapter(this);
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
        mCallback.onItemClickSelected(position);
    }


    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnListItemClickListener { void onItemClickSelected(int position); }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnListItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


}
