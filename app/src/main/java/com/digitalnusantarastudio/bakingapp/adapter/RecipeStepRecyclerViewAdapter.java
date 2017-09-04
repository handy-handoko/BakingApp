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

    public RecipeStepRecyclerViewAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_step_item, parent, false);
        return new RecipeStepRecyclerViewAdapter.RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeStepViewHolder holder, int position) {
        holder.bind(position);
    }

    public JSONArray getData(){
        return recipe_json_array;
    }

    public void setData(JSONArray json_array){
        this.recipe_json_array = json_array;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(recipe_json_array == null)
            return 0;
        else
            return recipe_json_array.length();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_step_textview) TextView recipe_step_textview;

        private RecipeStepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        //function for bind image to adapter
        void bind(int position){
            try {
                recipe_step_textview.setText(recipe_json_array.getJSONObject(position).getString("shortDescription"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            listItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
