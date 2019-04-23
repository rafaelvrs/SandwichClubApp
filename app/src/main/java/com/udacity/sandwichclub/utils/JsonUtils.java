package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject jsonSandwichName = jsonSandwich.getJSONObject("name");

            sandwich.setMainName(jsonSandwichName.getString("mainName"));
            sandwich.setAlsoKnownAs(jsonArrayToListString(jsonSandwichName.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));
            sandwich.setDescription(jsonSandwich.getString("description"));
            sandwich.setImage(jsonSandwich.getString("image"));
            sandwich.setIngredients(jsonArrayToListString(jsonSandwich.getJSONArray("ingredients")));

        } catch (JSONException e) {
            Log.e(JsonUtils.class.getName(),"Parse json error: " + "[" + json + "]");
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> jsonArrayToListString(JSONArray array) {
        List<String> stringList = new ArrayList<>();
        if (array != null) {
            for (int i=0;i<array.length();i++){
                try {
                    stringList.add(array.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringList;
    }
}
