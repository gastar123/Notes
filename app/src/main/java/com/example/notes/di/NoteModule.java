package com.example.notes.di;

import com.example.notes.editor.NoteActivity;
import com.example.notes.editor.NotePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteModule {

    @ActivityScope
    @Provides
    NotePresenter provideNotePresenter(NoteActivity noteActivity) {
        return new NotePresenter(noteActivity);
    }
}
