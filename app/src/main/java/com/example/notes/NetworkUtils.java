package com.example.notes;

import android.annotation.SuppressLint;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
    public void loadTags() {
        serverApi.getTags()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Tag>, ObservableSource<Tag>>) tags -> Observable.fromArray(tags.toArray(new Tag[0])))
                .subscribe(tag -> mainModel.insertTag(tag), Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    public void loadNotes(Action action, Consumer<Throwable> throwableConsumer) {
        serverApi.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Note>, ObservableSource<Note>>) notes -> Observable.fromArray(notes.toArray(new Note[0])))
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        mainModel.insertNoteInDB(note);
                    }
                }, throwableConsumer::accept, action::run);
    }

    public void saveToServer(Note note, Consumer<Long> noteConsumer, Consumer<Throwable> throwableConsumer) {
        serverApi.addNote(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteConsumer::accept, throwableConsumer::accept);
    }

    public void deleteNotes(Collection<Long> serverIds) {
        serverApi.deleteNote(serverIds)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
