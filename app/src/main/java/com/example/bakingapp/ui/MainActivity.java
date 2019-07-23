package com.example.bakingapp.ui;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.bakingapp.GetRecipes;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeAdapter;
import com.example.bakingapp.model.RecipeList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetRecipes.AsyncResponse {

    private ArrayList<RecipeList> recipeList=new ArrayList<>();
    private TextView main_list;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int spanCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount =2;
        }

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        dispalyViews();

    }

    private void dispalyViews(){
        getRecipes();
    }
    private void getRecipes() {
        new GetRecipes(new GetRecipes.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<RecipeList> output) {
                setAdapter(output);
                //trailersInstance =output;
            }
        }).execute();
    }

    @Override
    public void processFinish(ArrayList<RecipeList> output) {

    }
    private void setAdapter (ArrayList<RecipeList> mr){
        adapter = new RecipeAdapter(mr);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
