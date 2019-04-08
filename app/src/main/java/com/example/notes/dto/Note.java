package com.example.notes.dto;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject implements Serializable {

    @PrimaryKey
//    @Expose(serialize = false, deserialize = false)
    private Integer id;

//    @SerializedName("id")
//    private Integer serverId;

    private String user;
    private String title;
    private String text;
    private Date date;
    private RealmList<Tag> tags;

    public Note() {
    }

    public Note(String user, String title, String text, Date date, Integer id, RealmList<Tag> tags, Integer serverId) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.date = date;
        this.id = id;
        this.tags = tags;
//        this.serverId = serverId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

//    @SerializedName("id")
//    public Integer getServerId() {
//        return serverId;
//    }
//
//    @SerializedName("id")
//    public void setServerId(Integer serverId) {
//        this.serverId = serverId;
//    }
}
