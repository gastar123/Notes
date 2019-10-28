package com.example.notes.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notes.NoteAdapter;
import com.example.notes.R;
import com.example.notes.dto.Note;
import com.example.notes.presenter.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements IMainView {

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

    @Override
    public void updateView(List<Note> notesList, boolean reloadFromServer) {
        adapter.changeData(notesList);
        if (reloadFromServer) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void makeToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
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
