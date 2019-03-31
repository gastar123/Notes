package com.example.notes;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String login;
    private String headNote;
    private String bodyNote;
    private Date date;
    private RealmList<Tag> tagsList;

    public Note() {
    }

    public Note(String login, String headNote, String bodyNote, Date date, int id, RealmList<Tag> tagsList) {
        this.login = login;
        this.headNote = headNote;
        this.bodyNote = bodyNote;
        this.date = date;
        this.id = id;
        this.tagsList = tagsList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHeadNote() {
        return headNote;
    }

    public void setHeadNote(String headNote) {
        this.headNote = headNote;
    }

    public String getBodyNote() {
        return bodyNote;
    }

    public void setBodyNote(String bodyNote) {
        this.bodyNote = bodyNote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RealmList<Tag> getTagsList() {
        return tagsList;
    }

    public void setTagsList(RealmList<Tag> tagsList) {
        this.tagsList = tagsList;
    }
}
