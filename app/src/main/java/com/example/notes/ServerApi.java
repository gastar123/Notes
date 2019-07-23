package com.example.notes;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {

    @GET("tags")
    Observable<List<Tag>> getTags();

    @GET("notes")
    Observable<List<Note>> getNotes();

    @POST("tags")
    Completable addTag(@Body Tag tag);

    @POST("notes")
    Completable addNote(@Body Note note);
}
