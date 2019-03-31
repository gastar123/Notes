package com.example.notes.di;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    @Provides
    Realm provideDatabaseHelper() {
        return Realm.getDefaultInstance();
    }
}
