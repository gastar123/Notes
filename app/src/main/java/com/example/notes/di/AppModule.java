package com.example.notes.di;

import android.app.Application;
import android.content.Context;

import com.example.notes.MainActivity;
import com.example.notes.Note;
import com.example.notes.NoteApplication;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);
}
