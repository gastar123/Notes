package com.example.notes.editor;

import android.util.Log;
import android.widget.Toast;

import com.example.notes.MainModel;
import com.example.notes.dto.Note;
import com.example.notes.dto.ServerIdForDelete;
import com.example.notes.dto.Tag;

import java.util.Arrays;
import java.util.List;

public class NotePresenter {

    private MainModel mainModel;
    private NoteActivity noteView;

    public NotePresenter(MainModel mainModel, NoteActivity noteView) {
        this.mainModel = mainModel;
        this.noteView = noteView;

    }

    public Note getNoteFromFirstActivity() {
        Note note = mainModel.getNoteFromRealm(noteView.getIntent().getExtras().getLong("realmId"));
        return note;
    }

    public void insertOrUpdateNote(Note note) {
        note.setUnsaved(true);
        mainModel.editNoteInDB(note);
    }

    public void saveNoteOnServer(Note note) {
        mainModel.saveNoteOnServer(note, returnedServerId -> checkNoteFromServer(note, returnedServerId), throwable -> onError(throwable));
    }

    public void deleteNotesFromServer(Long serverId) {
        mainModel.deleteNotesFromServer(Arrays.asList(serverId), () -> onComplete(serverId), throwable -> onErrorForDelete(throwable, serverId));
    }

    public List<Tag> getTags(String name) {
        return mainModel.getTagsByNameIn(name);
    }

    private void checkNoteFromServer(Note note, Long returnedServerId) {
        note.setUnsaved(false);
        mainModel.checkNoteFromServer(note, returnedServerId);
        noteView.finish();
    }

    private void onComplete(Long serverId) {
        mainModel.deleteNoteFromDB(serverId);
        noteView.finish();
    }

    private void onErrorForDelete(Throwable throwable, Long serverId) {
        mainModel.insertServerIdForDelete(new ServerIdForDelete(serverId));
        mainModel.deleteNoteFromDB(serverId);
        onError(throwable);
    }

    private void onError(Throwable throwable) {
        Log.e("My error!!!", throwable.getMessage(), throwable);
        Toast.makeText(noteView, "Нет соединения с сервером", Toast.LENGTH_SHORT).show();
        noteView.finish();
    }

    public void closeResources() {
        mainModel.closeResources();
    }
}
