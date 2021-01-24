package com.example.notes.presenter;

import android.util.Log;

import com.example.notes.model.MainModel;
import com.example.notes.view.ILoginView;

public class LoginPresenter {

    private MainModel model;
    private ILoginView view;

    public LoginPresenter(MainModel model, ILoginView view) {
        this.model = model;
        this.view = view;
    }

    public void sendUserToServer(String userName, String password) {
        model.sendUserToServer(userName, password, this::logInSuccess, this::onError);
    }

    private void logInSuccess(String textFromServer) {
        view.getActivity().finish();
    }

    private void onError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
    }
}
