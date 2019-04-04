package com.example.notes.di;

import com.example.notes.MainActivity;
import com.example.notes.editor.NoteActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivityBinder();

    @ActivityScope
    @ContributesAndroidInjector(modules = NoteModule.class)
    abstract NoteActivity noteActivityBinder();
}
