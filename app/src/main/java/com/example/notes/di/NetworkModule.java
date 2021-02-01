package com.example.notes.di;

import android.content.Context;

import com.example.notes.model.NetworkUtils;
import com.example.notes.model.ServerApi;
import com.example.notes.model.UserInfoProvider;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    UserInfoProvider provideUserInfoProvider(Context ctx) {
        return new UserInfoProvider(ctx);
    }

    @Singleton
    @Provides
    CookieJar provideCookieJar(Context ctx) {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ctx));
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpCLient(CookieJar cookieJar) {
        return new OkHttpClient.Builder().cookieJar(cookieJar).build();
    }

    @Singleton
    @Provides
    NetworkUtils provideNetworkUtils(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gastar.ga/api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();

        ServerApi serverApi = retrofit.create(ServerApi.class);
        return new NetworkUtils(serverApi);
    }
}
