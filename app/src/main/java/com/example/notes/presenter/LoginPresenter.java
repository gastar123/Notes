package com.example.notes.presenter;

import android.util.Log;

import com.example.notes.model.MainModel;
import com.example.notes.model.UserInfoProvider;
import com.example.notes.view.ILoginView;

public class LoginPresenter {

    private MainModel model;
    private ILoginView view;
    private UserInfoProvider userInfoProvider;
    private String tmpLogin;

    public LoginPresenter(MainModel model, ILoginView view, UserInfoProvider userInfoProvider) {
        this.model = model;
        this.view = view;
        this.userInfoProvider = userInfoProvider;
    }

    public void sendUserToServer(String userName, String password) {
        model.sendUserToServer(userName, password, () -> logInSuccess(userName), this::onLogInError);
    }

    private void logInSuccess(String userName) {
        userInfoProvider.setLogin(userName);
        view.getActivity().finish();
    }

    private void onLogInError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        view.makeToast("Ошибка входа. " + throwable.getMessage());
    }
}
