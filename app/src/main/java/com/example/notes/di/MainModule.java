package com.example.notes.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.adapter.RecyclerNoteAdapter;
import com.example.notes.model.MainModel;
import com.example.notes.model.NetworkUtils;
import com.example.notes.model.UserInfoProvider;
import com.example.notes.presenter.MainPresenter;
import com.example.notes.view.IMainView;
import com.example.notes.view.MainActivity;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class MainModule {

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(MainModel mainModel, IMainView view, UserInfoProvider userInfoProvider) {
        return new MainPresenter(mainModel, view, userInfoProvider);
    }

    @ActivityScope
    @Provides
    IMainView provideMainView(MainActivity view) {
        return view;
    }

    @ActivityScope
    @Provides
    MainModel provideMainModel(Realm realm, NetworkUtils networkUtils, SharedPreferences sPref) {
        return new MainModel(realm, networkUtils, sPref);
    }

    @ActivityScope
    @Provides
    RecyclerNoteAdapter provideNoteAdapter(IMainView view) {
        return new RecyclerNoteAdapter(note -> view.editNote(note));
    }

    @ActivityScope
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("version", Context.MODE_PRIVATE);
    }
}
