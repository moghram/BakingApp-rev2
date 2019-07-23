package com.example.bakingapp;

import android.os.AsyncTask;

import com.example.bakingapp.model.RecipeList;
import com.example.bakingapp.utils.NetworkUtils;
import com.example.bakingapp.utils.RecipesJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class GetRecipes extends AsyncTask<String,String, ArrayList<RecipeList>>{
    public interface AsyncResponse {
        void processFinish(ArrayList<RecipeList> output);
    }

    public AsyncResponse asyncResponse = null;

    public GetRecipes(AsyncResponse asyncResponse){
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<RecipeList> doInBackground(String... strings) {
        ArrayList<RecipeList> recipeData;

        URL tmdbRequestUrl = NetworkUtils.buildUrl();
        try {
            String jsonRespond = NetworkUtils.getResponseFromHttpUrl(tmdbRequestUrl);
            recipeData = RecipesJsonUtils.RecipesDataFromJson(jsonRespond);
            return recipeData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<RecipeList> output) {
        super.onPostExecute(output);
        asyncResponse.processFinish(output);
    }
}
