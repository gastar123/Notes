package com.example.notes;

import android.app.Application;

import com.example.notes.di.DaggerMainComponent;
import com.example.notes.di.MainComponent;

import io.realm.Realm;

public class NoteApplication extends Application {

    private static MainComponent.Builder builder;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = DaggerMainComponent.builder().application(this);
        Realm.init(this);
    }

    public static MainComponent.Builder getBuilder() {
        return builder;
    }
}
