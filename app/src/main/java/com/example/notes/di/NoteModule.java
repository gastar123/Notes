package com.example.notes.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.adapter.TagAutoCompleteAdapter;
import com.example.notes.model.MainModel;
import com.example.notes.model.NetworkUtils;
import com.example.notes.view.INoteView;
import com.example.notes.view.NoteActivity;
import com.example.notes.presenter.NotePresenter;

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
    TagAutoCompleteAdapter provideTagAdapter(NotePresenter notePresenter) {
        return new TagAutoCompleteAdapter(name -> notePresenter.getTags(name));
    }

    @ActivityScope
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("version", Context.MODE_PRIVATE);
    }
}
