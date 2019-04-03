package com.example.notes;

public class MainPresenter {

    private MainModel mainModel;
    private MainActivity view;

    public MainPresenter(MainModel mainModel, MainActivity view) {
        this.mainModel = mainModel;
        this.view = view;
    }

    public void reload() {
        view.updateView(mainModel.getAllNotes());
    }
}
