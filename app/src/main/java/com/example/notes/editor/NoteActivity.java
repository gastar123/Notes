package com.example.notes.editor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.notes.MainPresenter;
import com.example.notes.R;
import com.example.notes.TagAutoCompleteAdapter;
import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;
import io.realm.RealmList;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    NotePresenter notePresenter;
    private TagAutoCompleteAdapter tagAdapter;
    private Note note;
    private TextView tvTag;
    private EditText etHead;
    private EditText etBody;
    private Button btnAdd;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    public void init() {
        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
//        tvTag = findViewById(R.id.tvTag);
        etHead = findViewById(R.id.etHead);
        etBody = findViewById(R.id.etBody);
        btnAdd = findViewById(R.id.btnAdd);

        checkRequestCode();
        tagAdapter = new TagAutoCompleteAdapter(this);
        multiAutoCompleteTextView.setAdapter(tagAdapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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
        multiAutoCompleteTextView.setText(TextUtils.join(", ", note.getTags()));
        etHead.setText(note.getTitle());
        etBody.setText(note.getText());
    }

    public List<Tag> getTags(String name) {
        return notePresenter.getTags(name);
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
        note.setTags(getTagsList(multiAutoCompleteTextView.getText().toString()));
        note.setCreateDate(new Date());
        notePresenter.insertOrUpdateNote(note);
        notePresenter.saveNoteOnServer(note);
        finish();
    }

    public RealmList<Tag> getTagsList(String s) {
//        RealmList<Tag> tagList = new RealmList<>();
//        String[] subStr = s.split(",");
//        for (String tagStr: subStr) {
//            tagList.add(new Tag(tagStr.trim()));
//        }
//        return tagList;

        RealmList<Tag> list = Stream.of(s.split(","))
                .map(i -> new Tag(i.trim()))
                .filter(i -> !i.getName().isEmpty())
                .collect(Collectors.toCollection(RealmList::new));
        return list;
    }
}
