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

/**
 * Created by luqman on 29/08/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final ListItemClickListener listItemClickListener;
    JSONArray recipe_json_array;
    private Context context;

    //initialize adapter with listener
    public RecipeAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    //listener for click event. used when user click on movie poster to see about movie detail
    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(recipe_json_array == null)
            return 0;
        else
            return recipe_json_array.length();
    }

    public JSONArray getData(){
        return recipe_json_array;
    }

    public void setData(JSONArray json_array){
        this.recipe_json_array = json_array;
        notifyDataSetChanged();
    }

    //Holder class for movie list
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipe_text_view) TextView recipe_text_view;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //recipe_text_view = (TextView) itemView.findViewById(R.id.recipe_text_view);
            itemView.setOnClickListener(this);
        }

        //function for bind image to adapter
        void bind(int position){
            try {
                recipe_text_view.setText(recipe_json_array.getJSONObject(position).getString("name"));
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
