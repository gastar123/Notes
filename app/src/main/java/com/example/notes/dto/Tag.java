package com.example.notes.dto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {

    @PrimaryKey
//    @Expose(serialize = false, deserialize = false)
    private Integer id;

//    @SerializedName("id")
//    private Integer serverId;

    private String name;

    public Tag() {
    }

    public Tag(String name, Integer id, Integer serverId) {
        this.name = name;
        this.id = id;
//        this.serverId = serverId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
