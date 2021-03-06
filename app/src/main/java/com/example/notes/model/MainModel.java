package com.example.notes.model;

import android.content.SharedPreferences;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.notes.dto.Note;
import com.example.notes.dto.ServerIdForDelete;
import com.example.notes.dto.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.Sort;

public class MainModel {

    private NetworkUtils networkUtils;
    private Realm realm;
    private SharedPreferences sPref;

    public MainModel(Realm realm, NetworkUtils networkUtils, SharedPreferences sPref) {
        this.realm = realm;
        this.networkUtils = networkUtils;
        this.sPref = sPref;
        networkUtils.setMainModel(this);
    }

    public void loadNotesFromServer(Action action, Consumer<Throwable> throwable) {
        networkUtils.loadNotes(action, throwable);
    }

    public void saveNoteOnServer(Note note, Consumer<Long> noteConsumer, Consumer<Throwable> throwable) {
        networkUtils.saveToServer(note, noteConsumer, throwable);
    }

    public void saveOnServerUnsavedNotes(List<Note> notesList, Consumer<List<Long>> listConsumer,
                                         Consumer<Throwable> throwable) {
        networkUtils.saveToServerUnSavedNotes(notesList, listConsumer, throwable);
    }

    public void deleteNotesFromServer(Collection<Long> serverIds, Action action, Consumer<Throwable> throwable) {
        networkUtils.deleteNotes(serverIds, action, throwable);
    }

    public void insertServerIdForDelete(ServerIdForDelete serverIdForDelete) {
        realm.beginTransaction();
        realm.insertOrUpdate(serverIdForDelete);
        realm.commitTransaction();
    }

    public void sendUserToServer(String userName, String password, Action action, Consumer<Throwable> throwable) {
        networkUtils.sendUserToServer(userName, password, action, throwable);
    }

    public void logout(Action action, Consumer<Throwable> throwable) {
        networkUtils.logout(action, throwable);
    }

    public Collection<Long> getServerIdsListForDelete() {
        Collection<ServerIdForDelete> serverIdsObject =
                realm.copyFromRealm(realm.where(ServerIdForDelete.class).findAll());
        Collection<Long> serverIds = Stream.of(serverIdsObject)
                .map(i -> i.getServerId())
                .collect(Collectors.toList());
        return serverIds;
    }

    public List<Note> getAllNotes() {
        final List<Note> notesList = realm.copyFromRealm(realm.where(Note.class).findAll().sort("createDate",
                Sort.DESCENDING));
        return notesList;
    }

    public Note getNoteFromRealm(Long id) {
        Note note = realm.copyFromRealm(realm.where(Note.class).equalTo("realmId", id).findFirst());
        return note;
    }

    public void editNoteInDB(Note note) {
        if (note.getId() == null) {
            note.setId(getNextNoteKey());
        }
        realm.beginTransaction();
        realm.insertOrUpdate(note);
        realm.commitTransaction();
    }

    public void insertNoteInDB(Note note) {
        Note realmId = realm.where(Note.class).equalTo("serverId", note.getServerId()).findFirst();
        if (realmId == null) {
            note.setId(getNextNoteKey());
            realm.beginTransaction();
            realm.insertOrUpdate(note);
            realm.commitTransaction();
        } else {
            note.setId(realmId.getId());
            realm.beginTransaction();
            realm.insertOrUpdate(note);
            realm.commitTransaction();
        }
    }

    public void checkNoteFromServer(Note note, Long returnedServerId) {
        note.setServerId(returnedServerId);
        realm.beginTransaction();
        realm.insertOrUpdate(note);
        realm.commitTransaction();
    }

    public List<Note> getNotesForSaveOnServer() {
        List<Note> notesList = realm.copyFromRealm(realm.where(Note.class).equalTo("unSaved", true).findAll());
        return notesList;
    }

    public void checkNotesFromServer(List<Note> notesList, List<Long> returnedServerIds) {
        List<Note> notesListWithServerId = new ArrayList<>();
        for (int i = 0; i < notesList.size(); i++) {
            Note note = notesList.get(i);
            note.setUnsaved(false);
            note.setServerId(returnedServerIds.get(i));
            notesListWithServerId.add(note);
        }
        insertNotes(notesListWithServerId);
    }

    public void insertNotes(List<Note> notes) {
        realm.beginTransaction();
        realm.insertOrUpdate(notes);
        realm.commitTransaction();
    }

    public void deleteNoteFromDB(Long serverId) {
        Note note = realm.where(Note.class).equalTo("serverId", serverId).findFirst();
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();
    }

    public Long getNextNoteKey() {
        Number realmId = realm.where(Note.class).max("realmId");
        if (realmId == null) {
            realmId = 0;
        }
        return realmId.longValue() + 1;
    }

    public List<Tag> getTagsByNameIn(String name) {
        List<Tag> tagsList =
                realm.copyFromRealm(realm.where(Tag.class).like("name", name.toLowerCase() + "*").findAll());
        return tagsList;
    }

    public void closeResources() {
        realm.close();
    }

    public void saveVersion(Long version) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong("version", version);
        ed.commit();
    }

    public Long loadVersion() {
        Long version = sPref.getLong("version", 0);
        return version;
    }
}
