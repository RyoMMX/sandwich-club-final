package com.udacity.sandwichclub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.DetailActivity;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichHolder> {
    private List<Sandwich> sandwiches;
    private Context context;

    public SandwichAdapter(List<Sandwich> sandwiches, Context context) {
        this.sandwiches = sandwiches;
        this.context = context;
    }


    @NonNull
    @Override
    public SandwichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SandwichHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sandwich_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichHolder holder, int position) {
        final Sandwich sandwich = sandwiches.get(position);
        if (sandwich != null) {
            if (sandwich.getImage() != null) {
                Picasso.with(context).load(sandwich.getImage()).into(holder.imageIV);
            }

            if (sandwich.getMainName() != null) {
                holder.mainNameTV.setText(sandwich.getMainName());
            }

            final int i = position;
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchDetailActivity(i);
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private void launchDetailActivity(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return sandwiches.size();
    }


    class SandwichHolder extends RecyclerView.ViewHolder {
        final ImageView imageIV;
        final TextView mainNameTV;
        final View view;

        SandwichHolder(View itemView) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.image_iv);
            mainNameTV = itemView.findViewById(R.id.main_name_tv);
            view = itemView;
        }
    }
}
