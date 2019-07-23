package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientList implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;

    public IngredientList (String mQuantity, String mMesure, String mIngredient){
        quantity=mQuantity;
        measure = mMesure;
        ingredient=mIngredient;
    }

    protected IngredientList(Parcel in) {
        quantity =in.readString();
        measure = in.readString();
        ingredient= in.readString();
    }

    public String getQuantity (){
        return quantity;
    }
    public String getMeasure(){
        return measure;
    }
    public String getIngredient(){
        return ingredient;
    }

    public static final Creator<IngredientList> CREATOR = new Creator<IngredientList>() {
        @Override
        public IngredientList createFromParcel(Parcel in) {
            return new IngredientList(in);
        }

        @Override
        public IngredientList[] newArray(int size) {
            return new IngredientList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}

