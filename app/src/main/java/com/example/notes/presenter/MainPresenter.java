package com.example.notes.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.notes.dto.Note;
import com.example.notes.model.MainModel;
import com.example.notes.view.IMainView;
import com.example.notes.view.NoteActivity;

import java.util.List;

public class MainPresenter {

    private MainModel mainModel;
    private IMainView view;
    public static final int CHANGE_NOTE = 1;
    public static final int ADD_NOTE = 2;

    public MainPresenter(MainModel mainModel, IMainView view) {
        this.mainModel = mainModel;
        this.view = view;
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
}
