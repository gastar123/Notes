package com.example.notes;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter mainPresenter;
    @Inject
    NoteAdapter adapter;
    private RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteApplication.getBuilder().activity(this).build().injectsMainActivity(this);
        init();
    }

    public void init() {
        rvMain = findViewById(R.id.rvMain);
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);
    }

    public void apdateView(List<Note> notesList) {
        adapter.changeData(notesList);
    }
}
