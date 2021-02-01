package com.example.notes.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.adapter.RecyclerNoteAdapter;
import com.example.notes.dto.Note;
import com.example.notes.presenter.MainPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    RecyclerNoteAdapter adapter;
    private RecyclerView rvMain;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.bottom_app_bar));
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
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> mainPresenter.addNote());
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);
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
    public void editNote(Note note) {
        mainPresenter.editNote(note);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.add(0, 1, 0, "Log in");
        menu.add(0, 2, 1, "Log out");
        changeLoginMenuItems();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case 2:
                mainPresenter.logout();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mainPresenter.reload(false);
        changeLoginMenuItems();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.closeResources();
    }

    @Override
    public void changeLoginMenuItems() {
        if (menu == null || !menu.hasVisibleItems()) {
            return;
        }
        if (mainPresenter.isLoggedIn()) {
//            menu.findItem(1).setVisible(false);
//            menu.findItem(2).setVisible(true);
            menu.findItem(1).setTitle("--");
            menu.findItem(2).setTitle("logout");
        } else {
//            menu.findItem(1).setVisible(true);
//            menu.findItem(2).setVisible(false);
            menu.findItem(1).setTitle("login");
            menu.findItem(2).setTitle("--");
        }
    }
}
