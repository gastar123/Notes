package com.example.notes.editor;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.dto.Note;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    @Inject
    NotePresenter notePresenter;
    private Note note;
    private TextView tvLogin;
    private TextView tvTag;
    private TextView tvDate;
    private EditText etHead;
    private EditText etBody;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    public void init() {
        tvLogin = findViewById(R.id.tvLogin);
        tvTag = findViewById(R.id.tvTag);
        tvDate = findViewById(R.id.tvDate);
        etHead = findViewById(R.id.etHead);
        etBody = findViewById(R.id.etBody);
        btnAdd = findViewById(R.id.btnAdd);

        noteFilling();
        btnAdd.setOnClickListener(v -> notePresenter.returnIntent(note));
    }

    public void noteFilling() {
        note = (Note) getIntent().getSerializableExtra("note");
        tvLogin.setText(note.getLogin());
        tvTag.setText(TextUtils.join(",", note.getTagsList()));
        tvDate.setText(note.getDate().toString());
        etHead.setText(note.getHeadNote());
        etBody.setText(note.getBodyNote());
    }
}
