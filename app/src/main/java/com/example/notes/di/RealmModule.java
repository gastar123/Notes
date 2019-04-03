package com.example.notes.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    @Singleton
    @Provides
    Realm provideDatabaseHelper() {
        return Realm.getDefaultInstance();
    }
}
