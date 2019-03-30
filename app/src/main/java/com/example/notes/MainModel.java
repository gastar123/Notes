package com.example.notes;

import android.util.Log;

public class MainModel {

    DbHelper databaseHelper;
    NetworkUtils networkUtils;

    public MainModel(DbHelper databaseHelper, NetworkUtils networkUtils) {
        this.databaseHelper = databaseHelper;
        this.networkUtils = networkUtils;
    }

    public void test() {
        Log.e("Test!!!!!!", databaseHelper.toString());
    }
}
