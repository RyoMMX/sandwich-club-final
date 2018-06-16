package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.adapter.IngredientAdapter;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Ingredient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String MAIN_NAME_KEY = "mainName";
    public static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    public static final String DESCRIPTION_KEY = "description";
    public static final String IMAGE_KEY = "image";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String URL_KEY = "url";

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

        populateUI();
    }

    private void populateUI() {
        Intent intent = getIntent();
        if (intent != null) {

            String mainName = intent.getStringExtra(MAIN_NAME_KEY);
            String[] alsoKnownAs = intent.getStringArrayExtra(ALSO_KNOWN_AS_KEY);
            String placeOfOrigin = intent.getStringExtra(PLACE_OF_ORIGIN_KEY);
            String description = intent.getStringExtra(DESCRIPTION_KEY);
            String image = intent.getStringExtra(IMAGE_KEY);
            String JSONIngredients = intent.getStringExtra(INGREDIENTS_KEY);
            final String url = intent.getStringExtra(URL_KEY);


            if (mainName != null) {
                if (binding.activityDetailContent != null) {
                    binding.activityDetailContent.mainNameTv.setText(mainName);
                    binding.collapsingToolbarLayout.setTitle(mainName);
                }
            }
            if (alsoKnownAs != null) {
                for (String string : alsoKnownAs) {
                    if (binding.activityDetailContent != null) {
                        binding.activityDetailContent.alsoKnownTv.append(string + "\n\n");
                    }
                }
            }
            if (placeOfOrigin != null) {
                if (binding.activityDetailContent != null) {
                    binding.activityDetailContent.placeOfOrigin.setText(placeOfOrigin);
                }
            }
            if (description != null) {
                if (binding.activityDetailContent != null) {
                    binding.activityDetailContent.descriptionTv.setText(description);
                }
            }
            if (image != null) {
                Picasso.with(this).load(image).into(binding.imageIv);
            }
            if (JSONIngredients != null) {
                Gson gson = new Gson();
                Type ingredientListType = new TypeToken<ArrayList<Ingredient>>() {
                }.getType();
                List<Ingredient> ingredients = gson.fromJson(JSONIngredients, ingredientListType);
                Log.v(TAG, "JSON Ingredients" + JSONIngredients);
                if (ingredients != null) {
                    setupIngredientRecylerView(ingredients);
                }
            }
            if (url != null) {
                binding.openUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW);
                        webIntent.setData(Uri.parse(url));
                        startActivity(webIntent);
                    }
                });
            }

        } else {
            finish();
            Log.e(TAG, "Intent is null in DetailActivity");
            closeOnError();
        }
    }

    private void setupIngredientRecylerView(List<Ingredient> ingredients) {
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
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
