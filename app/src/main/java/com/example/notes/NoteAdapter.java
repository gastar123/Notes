package com.example.notes;

import android.content.Context;
import android.opengl.ETC1;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context context;
    private MainPresenter mainPresenter;
    private final List<Note> notesList = new ArrayList<>();

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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int position) {
        Note note = notesList.get(position);
        viewHolder.tvLogin.setText(note.getLogin());
        viewHolder.tvTag.setText(note.getTagsList().get(position).getTag());
        viewHolder.tvDate.setText(note.getDate().toString());
        viewHolder.etHead.setText(note.getHeadNote());
        viewHolder.etBody.setText(note.getBodyNote());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvLogin;
        final TextView tvTag;
        final TextView tvDate;
        final EditText etHead;
        final EditText etBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvDate = itemView.findViewById(R.id.tvDate);
            etHead = itemView.findViewById(R.id.etHead);
            etBody = itemView.findViewById(R.id.etBody);
        }
    }
}
