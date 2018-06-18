package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static ArrayList<Sandwich> parseSandwichesJson(Context context) throws JSONException {
        ArrayList<Sandwich> sandwiches = new ArrayList<>();
        String[] jsonSandwiches = context.getResources().getStringArray(R.array.sandwich_details);

        for (int i = 0; i < jsonSandwiches.length; i++) {
            sandwiches.add(parseSandwichJson(jsonSandwiches[i]));
            Log.v(TAG, String.format("json for item number %d : %s", i, jsonSandwiches[i]));
        }
        return sandwiches;
    }

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject root = new JSONObject(json);

        if (root.has(NAME)) {
            parseName(root.getJSONObject(NAME), sandwich);
        }

        if (root.has(PLACE_OF_ORIGIN)) {
            sandwich.setPlaceOfOrigin(root.getString(PLACE_OF_ORIGIN));
        }

        if (root.has(DESCRIPTION)) {
            sandwich.setDescription(root.getString(DESCRIPTION));
        }

        if (root.has(IMAGE)) {
            sandwich.setImage(root.getString(IMAGE));
        }

        if (root.has(INGREDIENTS)) {
            sandwich.setIngredients(parseIngredient(root.getJSONArray(INGREDIENTS)));
        }

        return sandwich;
    }

    private static void parseName(JSONObject nameJO, Sandwich sandwich) throws JSONException {

        if (nameJO.has(MAIN_NAME)) {
            sandwich.setMainName(nameJO.getString(MAIN_NAME));
        }

        if (nameJO.has(ALSO_KNOWN_AS)) {
            sandwich.setAlsoKnownAs(parseAlsoKnownAs(nameJO.getJSONArray(ALSO_KNOWN_AS)));
        }
    }

    private static List<String> parseAlsoKnownAs(JSONArray alsoKnownAsJA) throws JSONException {
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsJA.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJA.getString(i));
        }
        return alsoKnownAs;
    }

    private static List<String> parseIngredient(JSONArray ingredientJA) throws JSONException {
        List<String> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientJA.length(); i++) {
            ingredients.add(ingredientJA.getString(i));
        }

        return ingredients;
    }
}
