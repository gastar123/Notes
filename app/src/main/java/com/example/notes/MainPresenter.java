package com.example.notes;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.notes.dto.Note;
import com.example.notes.editor.NoteActivity;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainPresenter {

    private MainModel mainModel;
    private MainActivity view;
    public static final int CHANGE_NOTE = 1;
    public static final int ADD_NOTE = 2;

    public MainPresenter(MainModel mainModel, MainActivity view) {
        this.mainModel = mainModel;
        this.view = view;
    }

    public void reload(boolean load) {
        view.updateView(mainModel.getAllNotes(), false);
        if (load) {
            // Метод run(): колбэк из observer при загрузке с сервера, вызывается когда загрузка завершится
            mainModel.loadNotesFromServer(() -> view.updateView(mainModel.getAllNotes(), true), this::onError);
        }
    }

    public void addNote() {
        Intent intent = new Intent(view, NoteActivity.class);
        intent.putExtra("requestCode", ADD_NOTE);
        view.startActivity(intent);
    }

    public void editNote(Note note) {
        Intent intent = new Intent(view, NoteActivity.class);
        intent.putExtra("realmId", note.getId());
        intent.putExtra("requestCode", CHANGE_NOTE);
        view.startActivity(intent);
    }

    public void closeResources() {
        mainModel.closeResources();
    }

    private void onError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        Toast.makeText(view, "Нет соединения с сервером", Toast.LENGTH_SHORT).show();
        view.closeRefreshing();
    }
}
