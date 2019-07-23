package com.example.notes.editor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.MainPresenter;
import com.example.notes.R;
import com.example.notes.dto.Note;

import java.util.Date;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    NotePresenter notePresenter;
    private Note note;
    private TextView tvTag;
    private EditText etHead;
    private EditText etBody;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    public void init() {
        tvTag = findViewById(R.id.tvTag);
        etHead = findViewById(R.id.etHead);
        etBody = findViewById(R.id.etBody);
        btnAdd = findViewById(R.id.btnAdd);

        checkRequestCode();
        btnAdd.setOnClickListener(this);
    }

    public void checkRequestCode() {
        int requestCode = getIntent().getExtras().getInt("requestCode");
        if (requestCode == MainPresenter.CHANGE_NOTE) {
            noteFilling();
        } else if (requestCode == MainPresenter.ADD_NOTE) {
            note = new Note();
        }
    }

    private void noteFilling() {
        note = notePresenter.getNoteFromFirstActivity();
        tvTag.setText(TextUtils.join(",", note.getTags()));
        etHead.setText(note.getTitle());
        etBody.setText(note.getText());
    }

    @Override
    public void onClick(View v) {
        boolean error = false;
        if (etHead.getText().toString().equals("")) {
            etHead.setError("Empty!");
            error = true;
        }
        if (etBody.getText().toString().equals("")) {
            etBody.setError("Empty!");
            error = true;
        }
        if (error) {
            return;
        }
        note.setTitle(etHead.getText().toString());
        note.setText(etBody.getText().toString());
        note.setDate(new Date());
        notePresenter.insertOrUpdateNote(note);
        finish();
    }
}
