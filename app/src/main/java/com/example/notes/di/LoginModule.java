package com.example.notes.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.model.MainModel;
import com.example.notes.model.NetworkUtils;
import com.example.notes.model.UserInfoProvider;
import com.example.notes.presenter.LoginPresenter;
import com.example.notes.view.ILoginView;
import com.example.notes.view.LoginActivity;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class LoginModule {

    @ActivityScope
    @Provides
    ILoginView provideLoginView(LoginActivity view) {
        return view;
    }

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(MainModel mainModel, ILoginView view, UserInfoProvider userInfoProvider) {
        return new LoginPresenter(mainModel, view, userInfoProvider);
    }

    @ActivityScope
    @Provides
    MainModel provideMainModel(Realm realm, NetworkUtils networkUtils, SharedPreferences sPref) {
        return new MainModel(realm, networkUtils, sPref);
    }

    @ActivityScope
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("version", Context.MODE_PRIVATE);
    }
}
