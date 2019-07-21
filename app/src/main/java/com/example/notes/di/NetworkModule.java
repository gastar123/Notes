package com.example.notes.di;

import com.example.notes.MainModel;
import com.example.notes.NetworkUtils;
import com.example.notes.ServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    NetworkUtils provideNetworkUtils() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6:8080")
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        ServerApi serverApi = retrofit.create(ServerApi.class);
        return new NetworkUtils(serverApi);
    }
}
