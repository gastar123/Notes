package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.notes.dto.Note;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.AndroidInjection;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    @Inject
    MainPresenter mainPresenter;
    @Inject
    NoteAdapter adapter;
    private RecyclerView rvMain;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter.reload(true);

        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            mainPresenter.reload(true);
        });
    }

    public void init() {
        rvMain = findViewById(R.id.rvMain);
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Add note");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mainPresenter.addNote();
        return super.onOptionsItemSelected(item);
    }

    public void updateView(List<Note> notesList, boolean reloadFromServer) {
        adapter.changeData(notesList);
        if (reloadFromServer) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void closeRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        mainPresenter.reload(false);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.closeResources();
    }
}
