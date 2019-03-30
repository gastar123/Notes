package com.example.notes;

import android.app.Application;

import com.example.notes.di.DaggerMainComponent;
import com.example.notes.di.MainComponent;

public class NoteApplication extends Application {

    private static MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMainComponent.create();
    }

    public static MainComponent getComponent() {
        return component;
    }
}
