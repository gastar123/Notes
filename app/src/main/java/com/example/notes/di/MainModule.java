package com.example.notes.di;

import android.content.Context;

import com.example.notes.MainActivity;
import com.example.notes.MainModel;
import com.example.notes.MainPresenter;
import com.example.notes.NetworkUtils;
import com.example.notes.NoteAdapter;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class MainModule {

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(MainModel mainModel, MainActivity activity) {
        return new MainPresenter(mainModel, activity);
    }

    @ActivityScope
    @Provides
    MainModel provideMainModel(Realm realm, NetworkUtils networkUtils) {
        return new MainModel(realm, networkUtils);
    }

    @ActivityScope
    @Provides
    NoteAdapter provideNoteAdapter(Context context, MainPresenter mainPresenter) {
        return new NoteAdapter(context, mainPresenter);
    }
}
