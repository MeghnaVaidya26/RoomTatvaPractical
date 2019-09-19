package com.e.roomdemo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    @ColumnInfo(name = "note")
    private String note;

    public Note(@NonNull String id, @NonNull String note) {
        this.id = id;
        this.note = note;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }
}
