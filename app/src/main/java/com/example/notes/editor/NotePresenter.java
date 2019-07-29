package com.example.notes.editor;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.notes.MainModel;
import com.example.notes.MainPresenter;
import com.example.notes.TagAutoCompleteAdapter;
import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class NotePresenter {

    private MainModel mainModel;
    private NoteActivity noteView;

    public NotePresenter(MainModel mainModel, NoteActivity noteView) {
        this.mainModel = mainModel;
        this.noteView = noteView;

    }

    public Note getNoteFromFirstActivity() {
        Note note = mainModel.getNoteFromRealm(noteView.getIntent().getExtras().getInt("realmId"));
        return note;
    }

    public void insertOrUpdateNote(Note note) {
        mainModel.editNoteInDB(note);
    }

    public void saveNoteOnServer(Note note) {
        mainModel.saveNoteOnServer(note, returnedNote -> checkNoteFromServer(note, returnedNote), this::onError);
    }

    public List<Tag> getTags(String name) {
        return mainModel.getTagsByNameIn(name);
    }

    private void checkNoteFromServer(Note note, Note returnedNote) {
        mainModel.checkNoteFromServer(note, returnedNote);
        noteView.finish();
    }

    private void onError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        Toast.makeText(noteView, "Нет соединения с сервером", Toast.LENGTH_SHORT).show();
        noteView.finish();
    }
}
