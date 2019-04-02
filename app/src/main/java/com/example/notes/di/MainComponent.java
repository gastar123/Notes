package com.example.notes.di;

import android.app.Application;

import com.example.notes.MainActivity;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {AppModule.class, MainModule.class, RealmModule.class, NetworkModule.class})
public interface MainComponent {
    void injectsMainActivity(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder application(Application application);

        @BindsInstance
        MainComponent.Builder activity(MainActivity mainActivity);

        MainComponent build();
    }
}
