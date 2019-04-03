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

    @Provides
    MainPresenter provideMainPresenter(MainModel mainModel, MainActivity activity) {
        return new MainPresenter(mainModel, activity);
    }

    @Provides
    MainModel provideMainModel(Realm realm, NetworkUtils networkUtils) {
        return new MainModel(realm, networkUtils);
    }

    @Provides
    NoteAdapter provideNoteAdapter(Context context) {
        return new NoteAdapter(context);
    }
}
