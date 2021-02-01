package com.example.notes.view;

import android.content.Context;

import com.example.notes.dto.Note;

import java.util.List;

public interface IMainView {

    Context getContext();

    void editNote(Note note);

    void updateView(List<Note> notesList, boolean reloadFromServer);

    void makeToast(String s);

    void closeRefreshing();

    void changeLoginMenuItems();
}
