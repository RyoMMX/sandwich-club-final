package com.udacity.sandwichclub.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {
    private List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
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
        Ingredient ingredient = ingredients.get(position);
        if (position == 0) {
            holder.labelTV.setTextColor(Color.parseColor("#64DD17"));
            holder.quantityTV.setTextColor(Color.parseColor("#64DD17"));
        } else if (position == 1) {
            holder.labelTV.setTextColor(Color.parseColor("#D50000"));
            holder.quantityTV.setTextColor(Color.parseColor("#D50000"));
        }

        if (ingredient != null) {
            if (ingredient.getLabel() != null) {
                holder.labelTV.setText(ingredient.getLabel());
            }

            if (ingredient.getUnit() != null) {
                holder.quantityTV.setText(String.format("%.2f %s", ingredient.getQuantity(), ingredient.getUnit()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        final TextView labelTV;
        final TextView quantityTV;

        Holder(View itemView) {
            super(itemView);

            labelTV = itemView.findViewById(R.id.label_tv);
            quantityTV = itemView.findViewById(R.id.quantity_tv);
        }
    }
}
