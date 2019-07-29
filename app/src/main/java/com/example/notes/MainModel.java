package com.example.notes;

import android.widget.Toast;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainModel {

    private NetworkUtils networkUtils;
    private Realm realm;

    public MainModel(Realm realm, NetworkUtils networkUtils) {
        this.realm = realm;
        this.networkUtils = networkUtils;
        networkUtils.setMainModel(this);
    }

    public void loadNotesFromServer(Action action, Consumer<Throwable> throwable) {
        networkUtils.loadNotes(action, throwable);
    }

    public void saveNoteOnServer(Note note, Consumer<Long> noteConsumer, Consumer<Throwable> throwable) {
        networkUtils.saveToServer(note, noteConsumer, throwable);
    }

    public List<Note> getAllNotes() {
        final List<Note> notesList = realm.copyFromRealm(realm.where(Note.class).findAll().sort("createDate", Sort.DESCENDING));
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

    public void insertNotes(List<Note> notes) {
        realm.beginTransaction();
        realm.insertOrUpdate(notes);
        realm.commitTransaction();
    }

    public void deleteNote(Long serverId) {
        Note note = realm.where(Note.class).equalTo("serverId", serverId).findFirst();
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();
        networkUtils.deleteNotes(Arrays.asList(serverId));
    }

    public void deleteNotes(Collection<Long> serverIds) {
        realm.beginTransaction();
        RealmResults<Note> rows = realm.where(Note.class).in("realmId", serverIds.toArray(new Long[0])).findAll();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public Long getNextNoteKey() {
        Number realmId = realm.where(Note.class).max("realmId");
        if (realmId == null) {
            realmId = 0;
        }
        return realmId.longValue() + 1;
    }


    public void loadTagsFromServer() {
        networkUtils.loadTags();
    }

    public List<Tag> getAllTags() {
        final List<Tag> tagsList = realm.copyFromRealm(realm.where(Tag.class).findAll());
        return tagsList;
    }

    public List<Tag> getTagsByNameIn(String name) {
        List<Tag> tagsList = realm.copyFromRealm(realm.where(Tag.class).like("name", name.toLowerCase() + "*").findAll());
        return tagsList;
    }

    public void editTag(Tag tag) {
        realm.beginTransaction();
        realm.insertOrUpdate(tag);
        realm.commitTransaction();
    }

    public void insertTag(Tag tag) {
        realm.beginTransaction();
        realm.insertOrUpdate(tag);
        realm.commitTransaction();
    }

    public void deleteTag(Collection<Integer> ids) {
        realm.beginTransaction();
        RealmResults<Tag> rows = realm.where(Tag.class).in("name", ids.toArray(new Integer[0])).findAll();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void closeResources() {
        realm.close();
    }
}
