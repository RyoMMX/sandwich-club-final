package com.udacity.sandwichclub.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sandwichclub.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {
    private List<String> ingredients;

    public IngredientAdapter(List<String> ingredients) {
        this.ingredients = ingredients;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false));

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String ingredient = ingredients.get(position);

        if (ingredient != null) {
            holder.labelTV.setText(ingredient);
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        final TextView labelTV;

        Holder(View itemView) {
            super(itemView);
            labelTV = itemView.findViewById(R.id.label_tv);
        }
    }
}
