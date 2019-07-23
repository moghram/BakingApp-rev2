package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipeList implements Parcelable {
    private String id;
    private String name;
    private String serving;
    private String image;
    private List<IngredientList> ingredients = null;
    private List<StepList> steps = null;

    public RecipeList (String mID,String mName,String mServing,String mImage, List<IngredientList> mIngredients, List<StepList> mSteps ){
        id = mID;
        name =mName;
        serving=mServing;
        image=mImage;
        ingredients=mIngredients;
        steps = mSteps;

    }
    protected RecipeList(Parcel in) {
        id = in.readString();
        name = in.readString();
        serving = in.readString();
        image = in.readString();
        ingredients=new ArrayList<>();
        in.readList(this.ingredients, (IngredientList.class.getClassLoader()));
        steps = new ArrayList<>();
        in.readList(this.steps, StepList.class.getClassLoader());

    }

    public void setStepList (List<StepList> stepList){
        this.steps = stepList;
    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getServing(){
        return serving;
    }
    public String getImage(){
        return image;
    }
    public List<IngredientList> getIngredients (){
        return ingredients;
    }
    public List<StepList> getSteps(){
        return steps;
    }

    public static final Creator<RecipeList> CREATOR = new Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel in) {
            return new RecipeList(in);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(serving);
        dest.writeString(image);
        dest.writeList(ingredients);
        dest.writeList(steps);

    }
}
