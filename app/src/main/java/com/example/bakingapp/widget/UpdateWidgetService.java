package com.example.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bakingapp.Constants;
import com.example.bakingapp.model.RecipeList;
import com.google.gson.Gson;

import java.util.List;

public class UpdateWidgetService {
    public static void updateWidget(Context context, RecipeList recipe) {

        String json = recipe == null ? null : new Gson().toJson(recipe);

        SharedPreferences.Editor prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(Constants.Widget_r_key, json);
        prefs.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidget.class));
        AppWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

}
