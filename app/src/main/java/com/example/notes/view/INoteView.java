package com.example.notes.view;

import android.app.Activity;

public interface INoteView {

    Activity getActivity();

    void makeToast(String s);
}
