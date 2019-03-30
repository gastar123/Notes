package com.example.notes.di;

import com.example.notes.MainActivity;

import dagger.Component;

@Component(modules = {MainModule.class, DbModule.class, NetworkModule.class})
public interface MainComponent {
    void injectsMainActivity(MainActivity mainActivity);
}
