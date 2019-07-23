package com.example.bakingapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.model.RecipeList;
import com.example.bakingapp.ui.StepListActivity;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<RecipeList> recipeList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("recipeList", recipeList.get(this.getAdapterPosition()));
            v.getContext().startActivity(intent);
            */
            Intent intent = new Intent(v.getContext(), StepListActivity.class);
            intent.putExtra(Constants.recipe_content, recipeList.get(this.getAdapterPosition()));
            v.getContext().startActivity(intent);

        }
    }

    public RecipeAdapter(ArrayList<RecipeList> data) {
        this.recipeList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_list, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView recipeName = holder.recipeName;
        recipeName.setText(recipeList.get(listPosition).getName());
    }

    @Override
    public int getItemCount() {
        //return 1;
        return recipeList.size();
    }
}