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
    private final List<Note> notesList = new ArrayList<>();

    public NoteAdapter(Context context) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
