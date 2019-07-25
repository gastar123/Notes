package com.example.notes.editor;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.example.notes.MainModel;
import com.example.notes.MainPresenter;
import com.example.notes.TagAutoCompleteAdapter;
import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.List;

import javax.inject.Inject;

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

    public List<Tag> getTags(String name) {
        return mainModel.getTagsByNameIn(name);
    }
}
