package com.digitalnusantarastudio.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.digitalnusantarastudio.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepRecyclerViewAdapter extends RecyclerView.Adapter<RecipeStepRecyclerViewAdapter.RecipeStepViewHolder> {

    private final ListItemClickListener listItemClickListener;
    private JSONArray recipe_json_array;
    private Context context;

    public RecipeStepRecyclerViewAdapter(JSONArray recipe_json_array, ListItemClickListener listItemClickListener) {
        this.recipe_json_array = recipe_json_array;
        this.listItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeStepRecyclerViewAdapter.RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeStepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipe_json_array.length();
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step) TextView step_text_view;

        private RecipeStepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            step_text_view = view.findViewById(R.id.recipe_text_view);
        }

        //function for bind image to adapter
        void bind(int position){
            try {
                step_text_view.setText(recipe_json_array.getJSONObject(position).getString("shortDescription"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
