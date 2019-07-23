package com.example.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bakingapp.model.IngredientList;
import com.example.bakingapp.model.RecipeList;
import com.example.bakingapp.model.StepList;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RecipeList recipeList;
    private List<IngredientList> ingredientList;
    private List<StepList> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView mainText = (TextView) findViewById(R.id.main_text);
        Intent intent = getIntent();
        recipeList = intent.getParcelableExtra(Constants.recipe_content);
        //String SSSS  = intent.getStringExtra("recipeList");
        //String name = recipeList.getName();
        ingredientList = recipeList.getIngredients();
        stepList =recipeList.getSteps();
        String steps ="";
        mainText.setText(recipeList.getName()+"/n");
        mainText.append(stepList.size()+"/n");
        mainText.append(ingredientList.size()+"/n");
        /*
        for(int i =0; i<stepList.size();i++){
            mainText.append(stepList.get(i).getDescription()+"/n");
        }
        */



    }
}
