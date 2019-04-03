package com.example.notes.di;

import com.example.notes.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    NetworkUtils provideNetworkUtils() {
        return new NetworkUtils();
    }
}
