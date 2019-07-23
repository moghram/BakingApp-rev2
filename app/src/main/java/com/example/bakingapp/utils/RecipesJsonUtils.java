package com.example.bakingapp.utils;

import android.util.Log;

import com.example.bakingapp.model.IngredientList;
import com.example.bakingapp.model.RecipeList;
import com.example.bakingapp.model.StepList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class RecipesJsonUtils {

    public static ArrayList<RecipeList> RecipesDataFromJson(String RecipeDataString) throws JSONException {

        // strings to retrieve JSONdata
        final String Json_id = "id";
        final String Json_name = "name";
        final String Json_serving ="servings";
        final String Json_image ="image";
        final String Json_ingredients ="ingredients";
        final String Json_steps ="steps";
        final String ERROR_CODE = "cod";
        final String recipeData = "{\"results\":"+RecipeDataString+"}";

        //Ingredeints JSON
        final String Json_ing_quantity="quantity";
        final String Json_ing_measure="measure";
        final String Json_ing_ingredient="ingredient";
        // Steps JSON
        final String Json_step_id="id";
        final String Json_step_shortDescription="shortDescription";
        final String Json_step_description="description";
        final String Json_step_videoURL="videoURL";
        final String Json_step_thumbnailURL="thumbnailURL";


        ArrayList<RecipeList> recipeList = new ArrayList<>();
        JSONObject RecipeJson = new JSONObject(recipeData);

        if (RecipeJson.has(ERROR_CODE)) {
            int errorCode = RecipeJson.getInt(ERROR_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray recipesArray = RecipeJson.getJSONArray("results");
        for (int i = 0; i < recipesArray.length(); i++) {
            String id;
            String name;
            String serving;
            String image;
            ArrayList<IngredientList> ingList = new ArrayList<>();
            ArrayList<StepList> stepLists = new ArrayList<>();

            JSONObject itemResults = recipesArray.getJSONObject(i);
            id = itemResults.getString(Json_id);
            name = itemResults.getString(Json_name);
            serving = itemResults.getString(Json_serving);
            image = itemResults.getString(Json_image);

            JSONArray ing_list = itemResults.getJSONArray(Json_ingredients);
            JSONArray step_list = itemResults.getJSONArray(Json_steps);

            for(int j = 0; j<ing_list.length(); j++){
                String ing_quantity;
                String ing_measure;
                String ing_ingredient;

                JSONObject itemIngredient = ing_list.getJSONObject(j);

                ing_quantity = itemIngredient.getString(Json_ing_quantity);
                ing_measure = itemIngredient.getString(Json_ing_measure);
                ing_ingredient = itemIngredient.getString(Json_ing_ingredient);

                ingList.add(new IngredientList(ing_quantity,ing_measure,ing_ingredient));
            }
            for(int k =0; k<step_list.length();k++){
                String step_id;
                String step_shortDesc;
                String step_desc;
                String step_video;
                String step_thumb;

                JSONObject itemStep = step_list.getJSONObject(k);

                step_id = itemStep.getString(Json_step_id);
                step_shortDesc = itemStep.getString(Json_step_shortDescription);
                step_desc = itemStep.getString(Json_step_description);
                step_video = itemStep.getString(Json_step_videoURL);
                step_thumb = itemStep.getString(Json_step_thumbnailURL);
                stepLists.add(new StepList(step_id,step_shortDesc,step_desc,step_video,step_thumb));

            }


            recipeList.add(new RecipeList(id, name,serving,image,ingList,stepLists));
        }
        return recipeList;
    }
}
