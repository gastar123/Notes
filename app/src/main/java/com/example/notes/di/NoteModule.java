package com.example.notes.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.MainModel;
import com.example.notes.NetworkUtils;
import com.example.notes.editor.NoteActivity;
import com.example.notes.editor.NotePresenter;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class NoteModule {

    @ActivityScope
    @Provides
    NotePresenter provideNotePresenter(MainModel mainModel, NoteActivity noteActivity) {
        return new NotePresenter(mainModel, noteActivity);
    }

    @ActivityScope
    @Provides
    MainModel provideMainModel(Realm realm, NetworkUtils networkUtils, SharedPreferences sPref) {
        return new MainModel(realm, networkUtils, sPref);
    }

    @ActivityScope
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("version", Context.MODE_PRIVATE);
    }
}
