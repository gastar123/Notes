package com.example.notes.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmModule {

    // Если оставить Singleton - проблема с закрытием. Хз когда и как корректно закрывать. А так жизненный цикл привязан к Activity
//    @Singleton
    @Provides
    Realm provideDatabaseHelper() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default2")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config);
    }
}
