package com.example.notes.dto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServerIdForDelete extends RealmObject {

    @PrimaryKey
    private Long serverId;

    public ServerIdForDelete() {
    }

    public ServerIdForDelete(Long serverId) {
        this.serverId = serverId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
