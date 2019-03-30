package com.example.notes.di;

import com.example.notes.DbHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    DbHelper provideDatabaseHelper() {
        return new DbHelper();
    }
}
