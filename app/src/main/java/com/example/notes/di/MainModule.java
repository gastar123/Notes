package com.example.notes.di;

import com.example.notes.DbHelper;
import com.example.notes.MainModel;
import com.example.notes.MainPresenter;
import com.example.notes.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainPresenter provideMainPresenter(MainModel mainModel) {
        return new MainPresenter(mainModel);
    }

    @Provides
    MainModel provideMainModel(DbHelper dbHelper, NetworkUtils networkUtils) {
        return new MainModel(dbHelper, networkUtils);
    }
}
