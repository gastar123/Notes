package com.example.notes.model;

import android.annotation.SuppressLint;

import com.annimon.stream.Stream;
import com.example.notes.dto.Note;

import java.util.Collection;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class NetworkUtils {

    private ServerApi serverApi;
    private MainModel mainModel;

    public NetworkUtils(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @SuppressLint("CheckResult")
    public void loadNotes(Action action, Consumer<Throwable> throwableConsumer) {
        serverApi.getNotes(mainModel.loadVersion())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notesList -> {
                    for (Note note : notesList) {
                        mainModel.insertNoteInDB(note);
                    }
//                    Преобразую стрим заметок в стрим лонгов(версий), достаю максимальную версию и сохраняю ее(если
// она есть)
                    Stream.of(notesList).mapToLong(note -> note.getVersion()).max().executeIfPresent(version -> mainModel.saveVersion(version));
                }, throwableConsumer::accept, action::run);
    }

    public void saveToServer(Note note, Consumer<Long> noteConsumer, Consumer<Throwable> throwableConsumer) {
        serverApi.addNote(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteConsumer::accept, throwableConsumer::accept);
    }

    public void saveToServerUnSavedNotes(List<Note> notesList, Consumer<List<Long>> listConsumer,
                                         Consumer<Throwable> throwableConsumer) {
        serverApi.addNotes(notesList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listConsumer::accept, throwableConsumer::accept);
    }

    public void deleteNotes(Collection<Long> serverIds, Action action, Consumer<Throwable> throwableConsumer) {
        serverApi.deleteNote(serverIds)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action::run, throwableConsumer::accept);
    }

    public void sendUserToServer(String userName, String password, Action action, Consumer<Throwable> throwableConsumer) {
        serverApi.postUserToServer(userName, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, throwableConsumer);
    }

    public void logout(Action action, Consumer<Throwable> throwableConsumer) {
        serverApi.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, throwableConsumer);
    }
}
