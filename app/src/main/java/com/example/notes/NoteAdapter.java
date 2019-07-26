package com.example.notes;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context context;
    private MainPresenter mainPresenter;
    private final List<Note> notesList = new ArrayList<>();
    private LinearLayout.LayoutParams lParams;
    private int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    public NoteAdapter(Context context, MainPresenter mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
    }

    public void changeData(List<Note> notesList) {
        this.notesList.clear();
        this.notesList.addAll(notesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mainPresenter.editNote(notesList.get(adapterPosition));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int position) {
        Note note = notesList.get(position);
        viewHolder.tvLogin.setText(note.getUser());
        viewHolder.llMain.removeAllViews();
        if (!note.getTags().isEmpty()) {
            createLayout(note, viewHolder);
        }
        if (note.getCreateDate() != null) {
            viewHolder.tvDate.setText(note.getCreateDate().toString());
        } else viewHolder.tvDate.setText("");
        viewHolder.tvHead.setText(note.getTitle());
        viewHolder.tvBody.setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private void createLayout(Note note, ViewHolder viewHolder) {
        RealmList<Tag> tagList = note.getTags();
        for (Tag tag: tagList) {
            lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
            lParams.gravity = Gravity.LEFT;
            TextView tvTag = new TextView(context);
            tvTag.setBackgroundResource(R.drawable.bg_text_view);
            tvTag.setPadding(10, 0, 10, 0);
            tvTag.setText(tag.getName());
            viewHolder.llMain.addView(tvTag, lParams);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLogin;
        private final TextView tvDate;
        private final TextView tvHead;
        private final TextView tvBody;
        private LinearLayout llMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHead = itemView.findViewById(R.id.etHead);
            tvBody = itemView.findViewById(R.id.etBody);
            llMain = itemView.findViewById(R.id.llMain);
        }
    }
}
