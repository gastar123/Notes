package com.example.notes.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.notes.dto.Note;
import com.example.notes.model.MainModel;
import com.example.notes.model.UserInfoProvider;
import com.example.notes.view.IMainView;
import com.example.notes.view.NoteActivity;

import java.util.List;

public class MainPresenter {

    private MainModel mainModel;
    private IMainView view;
    private UserInfoProvider userInfoProvider;
    public static final int CHANGE_NOTE = 1;
    public static final int ADD_NOTE = 2;

    public MainPresenter(MainModel mainModel, IMainView view, UserInfoProvider userInfoProvider) {
        this.mainModel = mainModel;
        this.view = view;
        this.userInfoProvider = userInfoProvider;
    }

    public void reload(boolean load) {
        view.updateView(mainModel.getAllNotes(), false);
        if (load) {
            mainModel.deleteNotesFromServer(mainModel.getServerIdsListForDelete(), this::onCompleteForDelete,
                    this::onError);
        }
    }

    public void addNote() {
        Intent intent = new Intent(view.getContext(), NoteActivity.class);
        intent.putExtra("requestCode", ADD_NOTE);
        view.getContext().startActivity(intent);
    }

    public void editNote(Note note) {
        Intent intent = new Intent(view.getContext(), NoteActivity.class);
        intent.putExtra("realmId", note.getId());
        intent.putExtra("requestCode", CHANGE_NOTE);
        view.getContext().startActivity(intent);
    }

    private void onCompleteForDelete() {
        List<Note> notesList = mainModel.getNotesForSaveOnServer();
        mainModel.saveOnServerUnsavedNotes(notesList, returnedServerIds -> onCompleteForSave(notesList,
                returnedServerIds), this::onError);
    }

    private void onCompleteForSave(List<Note> notesList, List<Long> returnedServerIds) {
        mainModel.checkNotesFromServer(notesList, returnedServerIds);
        // Метод run(): колбэк из observer при загрузке с сервера, вызывается когда загрузка завершится
        mainModel.loadNotesFromServer(() -> view.updateView(mainModel.getAllNotes(), true), this::onError);
    }

    private void onError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        view.makeToast("Нет соединения с сервером");
        view.closeRefreshing();
    }

    public void closeResources() {
        mainModel.closeResources();
        view = null;
    }

    public void logout() {
        mainModel.logout(this::logoutSuccess, this::logoutError);
    }

    private void logoutSuccess() {
        userInfoProvider.setLogin(null);
        view.changeLoginMenuItems();
    }

    private void logoutError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        view.makeToast("Ошибка. " + throwable.getMessage());
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(userInfoProvider.getLogin());
    }

    public String getLogin() {
        return userInfoProvider.getLogin();
    }
}
