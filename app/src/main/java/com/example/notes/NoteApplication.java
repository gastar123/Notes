package com.example.notes;

import com.example.notes.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;

public class NoteApplication extends DaggerApplication {

//    private static AppComponent.Builder builder;
//
    @Override
    public void onCreate() {
        super.onCreate();
//        builder = DaggerAppComponent.builder().application(this);
        Realm.init(this);
    }
//
//    public static AppComponent.Builder getBuilder() {
//        return builder;
//    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
        return DaggerAppComponent.builder().application(this).build();
    }
}
