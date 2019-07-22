package com.example.notes;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.Collection;
import java.util.List;

import io.reactivex.functions.Action;
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

    public void loadFromServer(Action action) {
        networkUtils.loadNotes(action);
    }

    public List<Note> getAllNotes() {
        final List<Note> notesList = realm.copyFromRealm(realm.where(Note.class).findAll().sort("date", Sort.ASCENDING));
        return notesList;
    }

    public void editNote(Note note) {
        if (note.getId() == null) {
            note.setId(getNextNoteKey());
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    public void insertNote(Note note) {
        realm.beginTransaction();
        realm.insertOrUpdate(note);
        realm.commitTransaction();
    }

    public void insertNotes(List<Note> notes) {
        realm.beginTransaction();
        realm.insertOrUpdate(notes);
        realm.commitTransaction();
    }

    public void deleteNote(Collection<Integer> ids) {
        realm.beginTransaction();
        RealmResults<Note> rows = realm.where(Note.class).in("realmId", ids.toArray(new Integer[0])).findAll();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public int getNextNoteKey() {
        Number realmId = realm.where(Note.class).max("realmId");
        if (realmId == null) {
            realmId = 0;
        }
        return realmId.intValue() + 1;
    }

    public List<Tag> getTags() {
        final RealmResults<Tag> tagsList = realm.where(Tag.class).findAll();
        return tagsList;
    }

    public void editTag(Tag tag) {
        if (tag.getId() == null) {
            tag.setId(getNextTagKey());
        }
        realm.beginTransaction();
        final Tag menegedTag = realm.copyToRealmOrUpdate(tag);
        realm.commitTransaction();
    }

    public void insertTags(List<Tag> tags) {
        realm.beginTransaction();
        realm.insertOrUpdate(tags);
        realm.commitTransaction();
    }

    public void deleteTag(Collection<Integer> ids) {
        realm.beginTransaction();
        RealmResults<Tag> rows = realm.where(Tag.class).in("realmId", ids.toArray(new Integer[0])).findAll();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private int getNextTagKey() {
        return realm.where(Tag.class).max("realmId").intValue() + 1;
    }

    public void closeResources() {
        realm.close();
    }
}
