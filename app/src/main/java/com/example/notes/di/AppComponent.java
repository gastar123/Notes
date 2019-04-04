package com.example.notes.di;

import android.app.Application;

import com.example.notes.NoteApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBindingModule.class,
        RealmModule.class, NetworkModule.class})
public interface AppComponent extends AndroidInjector<NoteApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
