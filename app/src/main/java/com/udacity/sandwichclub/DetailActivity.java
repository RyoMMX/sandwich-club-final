package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.adapter.IngredientAdapter;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(binding.actionBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            populateUI();
        } catch (JSONException e) {
            e.printStackTrace();
            closeOnError();
        }
    }

    private void populateUI() throws JSONException {
        Intent intent = getIntent();
        if (intent != null) {
            int postion = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            String sandwichDetail[] = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwichDetail[postion];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich != null) {
                if (sandwich.getMainName() != null) {
                    if (binding.activityDetailContent != null) {
                        binding.activityDetailContent.mainNameTv.setText(sandwich.getMainName());
                        binding.collapsingToolbarLayout.setTitle(sandwich.getMainName());
                    }
                }


                if (sandwich.getAlsoKnownAs() != null) {
                    for (String string : sandwich.getAlsoKnownAs()) {
                        if (binding.activityDetailContent != null) {
                            binding.activityDetailContent.alsoKnownTv.append(string + "\n\n");
                        }
                    }
                }

                if (sandwich.getPlaceOfOrigin() != null) {
                    if (binding.activityDetailContent != null) {
                        binding.activityDetailContent.placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
                    }
                }

                if (sandwich.getDescription() != null) {
                    if (binding.activityDetailContent != null) {
                        binding.activityDetailContent.descriptionTv.setText(sandwich.getDescription());
                    }
                }

                if (sandwich.getImage() != null) {
                    if (URLUtil.isHttpsUrl(sandwich.getImage()) || URLUtil.isHttpUrl(sandwich.getImage()))
                        Picasso.with(this).load(sandwich.getImage()).into(binding.imageIv);
                }

                if (sandwich.getIngredients() != null) {
                    setupIngredientRecylerView(sandwich.getIngredients());
                }

            }

        } else {
            closeOnError();
        }
    }

    private void setupIngredientRecylerView(List<String> ingredients) {
        if (binding.activityDetailContent != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };


            binding.activityDetailContent.ingredientsRV.setLayoutManager(layoutManager);
            binding.activityDetailContent.ingredientsRV.setItemAnimator(new DefaultItemAnimator());
            IngredientAdapter adapter = new IngredientAdapter(ingredients);
            binding.activityDetailContent.ingredientsRV.setAdapter(adapter);
        }
    }

    private void closeOnError() {
        Log.e(TAG, "Intent is null in DetailActivity");
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
