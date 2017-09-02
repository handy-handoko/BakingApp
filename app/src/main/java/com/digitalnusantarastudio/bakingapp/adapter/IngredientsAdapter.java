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
 * Created by luqman on 01/09/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private JSONArray ingredients_json_array;
    private Context context;

    @Override
    public IngredientsAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_list_item, parent, false);

        return new IngredientsAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(ingredients_json_array == null)
            return 0;
        else
            return ingredients_json_array.length();
    }

    public JSONArray getData(){
        return ingredients_json_array;
    }

    public void setData(JSONArray json_array){
        this.ingredients_json_array = json_array;
        notifyDataSetChanged();
    }

    //Holder class for movie list
    class IngredientViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ingredient_name) TextView ingredient_name_text_view;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //function for bind image to adapter
        void bind(int position){
            try {
                ingredient_name_text_view.setText(
                        ingredients_json_array.getJSONObject(position).getString("quantity")+" "+
                        ingredients_json_array.getJSONObject(position).getString("measure")+" "+
                        ingredients_json_array.getJSONObject(position).getString("ingredient")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
