package com.udacity.sandwichclub.utils;

import android.net.Uri;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    private static final String BASIC_URL = "https://api.edamam.com/search";

    private static final String Q_PARAMETERS = "q";
    private static final String APP_ID_PARAMETER = "app_id";
    private static final String APP_KEY_PARAMETER = "app_key";
    private static final String FROM_PARAMETER = "from";
    private static final String TO_PARAMETER = "to";

    private static final String Q_VALUE = "sandwich";
    private static final String APP_ID_VALUE = "29f6d67d";
    private static final String APP_KEY_VALUE = "6be0713ccafecec7a9ebd3a4b6a744c8";

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static int FROM_VALUE_START = 0;
    private static int TO_VALUE_START = 19;
    private static int fromValue = FROM_VALUE_START;
    private static int toValue = TO_VALUE_START;

    private static URL crateURL() {
        Uri uri = Uri.parse(BASIC_URL).buildUpon()
                .appendQueryParameter(Q_PARAMETERS, Q_VALUE)
                .appendQueryParameter(APP_ID_PARAMETER, APP_ID_VALUE)
                .appendQueryParameter(APP_KEY_PARAMETER, APP_KEY_VALUE)
                .appendQueryParameter(FROM_PARAMETER, String.valueOf(fromValue))
                .appendQueryParameter(TO_PARAMETER, String.valueOf(toValue))
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        fromValue += 20;
        toValue += 20;

        Log.v(TAG, String.format("API request URL : %s", url == null ? "null" : url.toString()));
        return url;
    }

    private static String loadJson(URL url) throws IOException {
        String json = null;
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        if (scanner.hasNext()) {
            json = scanner.next();
        }
        scanner.close();
        httpURLConnection.disconnect();


        Log.v(TAG, String.format("API json result : %s", json));
        return json;
    }

    public static ArrayList<Sandwich> loadSandwiches() throws JSONException, IOException {
        return JsonUtils.parseSandwichJson(loadJson(crateURL()));
    }

    public static void resetPager() {
        fromValue = FROM_VALUE_START;
        toValue = TO_VALUE_START;
    }
}