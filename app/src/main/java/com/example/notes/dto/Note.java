package com.example.notes.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose(serialize = false, deserialize = false)
    private Long realmId;

    @SerializedName("id")
    private Long serverId;

    private String user;
    private String title;
    private String text;
    private Date createDate;
    private RealmList<Tag> tags;

    public Note() {
    }

    public Note(String user, String title, String text, Date date, Long id, RealmList<Tag> tags, Long serverId) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.createDate = date;
        this.realmId = id;
        this.tags = tags;
        this.serverId = serverId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return realmId;
    }

    public void setId(Long id) {
        this.realmId = id;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

    @SerializedName("id")
    public Long getServerId() {
        return serverId;
    }

    @SerializedName("id")
    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
