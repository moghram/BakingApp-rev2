package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StepList implements Parcelable
{
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public StepList (String mId, String mShortDescription,String mDescription,String mVideoURL,String mThumbnailURL ){
        id = mId;
        shortDescription=mShortDescription;
        description=mDescription;
        videoURL=mVideoURL;
        thumbnailURL=mThumbnailURL;
    }

    protected StepList(Parcel in) {
        id = in.readString();
        shortDescription= in.readString();
        description= in.readString();
        videoURL= in.readString();
        thumbnailURL= in.readString();
    }
    public String getId(){
        return id;
    }
    public String getShortDescription(){
        return shortDescription;
    }
    public String getDescription(){
        return description;
    }
    public String getVideoURL(){
        return videoURL;
    }
    public String getThumbnailURL(){
        return thumbnailURL;
    }

    public static final Creator<StepList> CREATOR = new Creator<StepList>() {
        @Override
        public StepList createFromParcel(Parcel in) {
            return new StepList(in);
        }

        @Override
        public StepList[] newArray(int size) {
            return new StepList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        if(videoURL==null){
            dest.writeString("empty");
        }else{
            dest.writeString(videoURL);
        }
        if(thumbnailURL==null){
            dest.writeString("empty");
        }else{
            dest.writeString(thumbnailURL);
        }

    }
}
