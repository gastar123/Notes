package com.example.notes.model;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.Collection;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {

    @GET("tags")
    Observable<List<Tag>> getTags();

    @GET("notes")
    Observable<List<Note>> getNotes(@Query("version") Long version);

    @POST("notes")
    Observable<Long> addNote(@Body Note note);

    @POST("notesBatch")
    Observable<List<Long>> addNotes(@Body List<Note> notesList);

    //    Анотация @DELETE запрещает использовать @Body
    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    Completable deleteNote(@Body Collection<Long> serverIds);

    @FormUrlEncoded
    @POST("login")
    Single<String> postUserToServer(
            @Field("login") String userName,
            @Field("password") String password
    );
}
