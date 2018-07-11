package com.sava.friend_manager.util;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

@SuppressLint("ParcelCreator")
public class Suggestion implements SearchSuggestion {
    private String mName;
    private boolean mIsHistory;
    private int mID;

    public int getmID() {
        return mID;
    }

    public void setmID(int id) {
        this.mID = id;
    }

    public  Suggestion (String suggestion,int id){
        this.mName = suggestion;
        this.mIsHistory = false;
        this.mID = id;
    }
    public Suggestion(String s,boolean b){
        this.mName = s;
        this.mIsHistory = b;
    }
    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    @Override
    public String getBody() {
        return this.mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
