package com.example.notes;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

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

    public void loadTags() {
        serverApi.getTags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainModel::insertTags, Throwable::printStackTrace);
    }

    public void loadNotes(Action action) {
        serverApi.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        mainModel.insertNotes(notes);
                        action.run();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
