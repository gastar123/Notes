package com.example.notes;

import android.content.Intent;

import com.example.notes.dto.Note;
import com.example.notes.editor.NoteActivity;

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
        view.updateView(mainModel.getAllNotes());
        if (load) {
            // Метод run(): колбэк из observer при загрузке с сервера, вызывается когда загрузка завершится
            mainModel.loadFromServer(() -> view.updateView(mainModel.getAllNotes()));
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
}
