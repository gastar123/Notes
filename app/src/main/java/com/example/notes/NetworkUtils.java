package com.example.notes;

import android.annotation.SuppressLint;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

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
                .subscribe(tags -> mainModel.insertTags(tags), Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    public void loadNotes(Action action) {
        serverApi.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Note>, ObservableSource<Note>>) notes -> Observable.fromArray(notes.toArray(new Note[0])))
                .subscribe(mainModel::editNote, Throwable::printStackTrace, action::run);
    }
}
