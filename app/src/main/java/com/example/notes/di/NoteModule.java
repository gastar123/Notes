package com.example.notes.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.TagAutoCompleteAdapter;
import com.example.notes.dto.Tag;
import com.example.notes.model.MainModel;
import com.example.notes.model.NetworkUtils;
import com.example.notes.view.INoteView;
import com.example.notes.view.NoteActivity;
import com.example.notes.presenter.NotePresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class NoteModule {

    @ActivityScope
    @Provides
    NotePresenter provideNotePresenter(MainModel mainModel, INoteView view) {
        return new NotePresenter(mainModel, view);
    }

    @ActivityScope
    @Provides
    INoteView provideNoteView(NoteActivity view) {
        return view;
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
