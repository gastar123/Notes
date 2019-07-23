package com.example.notes;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.dto.Note;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter mainPresenter;
    @Inject
    NoteAdapter adapter;
    private RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter.reload();
    }

    public void init() {
        rvMain = findViewById(R.id.rvMain);
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) return;
//        Note note = mainModel.getNoteFromRealm(data.getExtras().getInt("realmId"));
//        mainPresenter.noteEditorReturned(note);
    }

    public void updateView(List<Note> notesList) {
        adapter.changeData(notesList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.closeResources();
    }
}
