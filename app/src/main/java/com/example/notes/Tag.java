package com.example.notes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String tag;

    public Tag() {
    }

    public Tag(String tag, Integer id) {
        this.tag = tag;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
