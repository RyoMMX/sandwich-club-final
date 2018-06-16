package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Ingredient;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonUtils {
    private static final String HITS_KEY = "hits";
    private static final String RECIPE_KEY = "recipe";

    private static final String LABEL_KEY = "label";
    private static final String IMAGE_KEY = "image";
    private static final String SOURCE_KEY = "source";
    private static final String URL_KEY = "url";
    private static final String HEALTH_LABELS_KEY = "healthLabels";
    private static final String INGREDIENT_LINES_KEY = "ingredientLines";
    private static final String TOTAL_NUTRIENTS_KEY = "totalNutrients";

    private static final String QUANTITY_KEY = "quantity";
    private static final String UNIT_KEY = "unit";

    public static ArrayList<Sandwich> parseSandwichJson(String json) throws JSONException {
        ArrayList<Sandwich> sandwiches = null;

        if (json != null) {
            JSONObject root = new JSONObject(json);

            if (root.has(HITS_KEY)) {
                sandwiches = parseHist(root.getJSONArray(HITS_KEY));
            }
        }

        return sandwiches;
    }

    private static ArrayList<Sandwich> parseHist(JSONArray hits) throws JSONException {
        ArrayList<Sandwich> sandwiches = new ArrayList<>();

        for (int i = 0; i < hits.length(); i++) {
            sandwiches.add(parseSandwich(hits.getJSONObject(i)));
        }

        return sandwiches;
    }

    private static Sandwich parseSandwich(JSONObject sandwichJO) throws JSONException {
        Sandwich sandwich = null;

        if (sandwichJO.has(RECIPE_KEY)) {
            sandwich = parseRecipe(sandwichJO.getJSONObject(RECIPE_KEY));
        }
        return sandwich;
    }

    private static Sandwich parseRecipe(JSONObject recipe) throws JSONException {

        Sandwich sandwich = new Sandwich();
        if (recipe.has(LABEL_KEY)) {
            sandwich.setMainName(recipe.getString(LABEL_KEY));
        }

        if (recipe.has(HEALTH_LABELS_KEY)) {
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            JSONArray healthLabels = recipe.getJSONArray(HEALTH_LABELS_KEY);

            for (int i = 0; i < healthLabels.length(); i++) {
                alsoKnownAs.add(healthLabels.getString(i));
            }

            sandwich.setAlsoKnownAs(alsoKnownAs);
        }

        if (recipe.has(SOURCE_KEY)) {
            sandwich.setPlaceOfOrigin(recipe.getString(SOURCE_KEY));
        }

        if (recipe.has(INGREDIENT_LINES_KEY)) {
            StringBuilder description = new StringBuilder();
            JSONArray ingredientLines = recipe.getJSONArray(INGREDIENT_LINES_KEY);
            for (int i = 0; i < ingredientLines.length(); i++) {
                description.append("").append(ingredientLines.get(i)).append("\n\n");

            }
            sandwich.setDescription(description.toString());
        }

        if (recipe.has(IMAGE_KEY)) {
            sandwich.setImage(recipe.getString(IMAGE_KEY));
        }

        if (recipe.has(URL_KEY)) {
            sandwich.setUrl(recipe.getString(URL_KEY));
        }

        if (recipe.has(TOTAL_NUTRIENTS_KEY)) {
            sandwich.setIngredients(parseTotalNutrients(recipe.getJSONObject(TOTAL_NUTRIENTS_KEY)));
        }

        return sandwich;
    }

    private static ArrayList<Ingredient> parseTotalNutrients(JSONObject ingredientsJSON) throws JSONException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        Iterator<String> keys = ingredientsJSON.keys();
        while (keys.hasNext()) {
            ingredients.add(parseIngredient(ingredientsJSON.getJSONObject(keys.next())));
        }

        return ingredients;
    }

    private static Ingredient parseIngredient(JSONObject ingredientJSON) throws JSONException {
        Ingredient ingredient = new Ingredient();

        if (ingredientJSON.has(LABEL_KEY)) {
            ingredient.setLabel(ingredientJSON.getString(LABEL_KEY));
        }
        if (ingredientJSON.has(QUANTITY_KEY)) {
            try {
                ingredient.setQuantity(Double.parseDouble(ingredientJSON.getString(QUANTITY_KEY)));

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (ingredientJSON.has(UNIT_KEY)) {
            ingredient.setUnit(ingredientJSON.getString(UNIT_KEY));
        }

        return ingredient;
    }


}
