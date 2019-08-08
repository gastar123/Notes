package com.example.notes.di;

import com.example.notes.MainModel;
import com.example.notes.NetworkUtils;
import com.example.notes.ServerApi;
import com.google.gson.GsonBuilder;

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
                .baseUrl("http://45.88.79.176:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        ServerApi serverApi = retrofit.create(ServerApi.class);
        return new NetworkUtils(serverApi);
    }
}
