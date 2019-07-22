package com.example.notes;

import android.content.Intent;

import com.example.notes.dto.Note;
import com.example.notes.editor.NoteActivity;

import io.reactivex.functions.Action;

public class MainPresenter {

    private MainModel mainModel;
    private MainActivity view;

    public MainPresenter(MainModel mainModel, MainActivity view) {
        this.mainModel = mainModel;
        this.view = view;
    }

    public void reload() {
        view.updateView(mainModel.getAllNotes());
        // Метод run(): колбэк из observer при загрузке с сервера, вызывается когда загрузка завершится
        mainModel.loadFromServer(() -> view.updateView(mainModel.getAllNotes()));
    }

    public void noteEditor(Note note) {
        Intent intent = new Intent(view, NoteActivity.class);
        intent.putExtra("note", note);
        view.startActivityForResult(intent, 1);
    }

    public void noteEditorReturned(Note note) {
        mainModel.editNote(note);
    }

    public void closeResources() {
        mainModel.closeResources();
    }
}
