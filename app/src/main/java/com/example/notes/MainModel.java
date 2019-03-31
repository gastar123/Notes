package com.example.notes;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainModel {

    NetworkUtils networkUtils;
    Realm realm;

    public MainModel(Realm realm, NetworkUtils networkUtils) {
        this.realm = realm;
        this.networkUtils = networkUtils;
    }

    public List<Note> getAllNotes() {
        final RealmResults<Note> notesList = realm.where(Note.class).findAll();
        return notesList;
    }

    public void editNote(Note note) {
        if (note.getId() == null) {
            note.setId(getNextNoteKey());
        }
        realm.beginTransaction();
        final Note menegedNote = realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    public void deleteNote(Collection<Integer> ids) {
        realm.beginTransaction();

        RealmResults<Note> rows = realm.where(Note.class).in("id", ids.toArray(new Integer[0])).findAll();
        rows.deleteAllFromRealm();

        realm.commitTransaction();
    }

    private int getNextNoteKey() {
        return realm.where(Note.class).max("id").intValue() + 1;
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

    public void deleteTag(Collection<Integer> ids) {
        realm.beginTransaction();

        RealmResults<Tag> rows = realm.where(Tag.class).in("id", ids.toArray(new Integer[0])).findAll();
        rows.deleteAllFromRealm();

        realm.commitTransaction();
    }

    private int getNextTagKey() {
        return realm.where(Tag.class).max("id").intValue() + 1;
    }
}
