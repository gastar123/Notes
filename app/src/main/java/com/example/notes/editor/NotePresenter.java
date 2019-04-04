package com.example.notes.editor;

import android.app.Activity;
import android.content.Intent;

import com.example.notes.dto.Note;

public class NotePresenter {

    private NoteActivity noteView;

    public NotePresenter(NoteActivity noteView) {
        this.noteView = noteView;
    }

    public void returnIntent(Note note) {
        Intent intent = noteView.getIntent();
        intent.putExtra("note", note);
        noteView.setResult(Activity.RESULT_OK, intent);
        noteView.finish();
    }
}
