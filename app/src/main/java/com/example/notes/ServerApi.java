package com.example.notes;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {

    @GET("tags")
    Observable<List<Tag>> getTags();

    @GET("notes")
    Observable<List<Note>> getNotes();

    @POST("tags")
    Observable<Void> addTags(Tag tag);

    @POST("notes")
    Observable<Void> addNotes(Note note);
}
