package com.example.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.dto.Note;
import com.example.notes.dto.Tag;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmList;

public class RecyclerNoteAdapter extends RecyclerView.Adapter<RecyclerNoteAdapter.NoteHolder> {

    private OnItemClickListener clickListener;
    private final List<Note> notesList = new ArrayList<>();
    private FlexboxLayout.LayoutParams fParams;
    private int wrapContent = FlexboxLayout.LayoutParams.WRAP_CONTENT;

    public RecyclerNoteAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void changeData(List<Note> notesList) {
        this.notesList.clear();
        this.notesList.addAll(notesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note, viewGroup, false);
        final NoteHolder noteHolder = new NoteHolder(view);
        return noteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        Note note = notesList.get(position);
        noteHolder.bindHolder(note);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private final TextView tvLogin;
        private final TextView tvDate;
        private final TextView tvHead;
        private final TextView tvBody;
        private FlexboxLayout llMain;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHead = itemView.findViewById(R.id.etHead);
            tvBody = itemView.findViewById(R.id.etBody);
            llMain = itemView.findViewById(R.id.llMain);

            itemView.setOnClickListener(v -> clickListener.onItemClick(notesList.get(getAdapterPosition())));
        }

        private void bindHolder(Note note) {
            tvLogin.setText(note.getUser());
            llMain.removeAllViews();
            if (!note.getTags().isEmpty()) {
                createLayout(note);
            }
            if (note.getCreateDate() != null) {
                tvDate.setText(note.getCreateDate().toString());
            } else tvDate.setText("");
            tvHead.setText(note.getTitle());
            tvBody.setText(note.getText());
        }

        private void createLayout(Note note) {
            RealmList<Tag> tagList = note.getTags();
            for (Tag tag : tagList) {
                fParams = new FlexboxLayout.LayoutParams(wrapContent, wrapContent);
                fParams.setMargins(5, 5, 5, 5);
                TextView tvTag = new TextView(itemView.getContext());
                tvTag.setBackgroundResource(R.drawable.bg_text_view);
                tvTag.setPadding(10, 0, 10, 0);
                tvTag.setText(tag.getName());
                llMain.addView(tvTag, fParams);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
