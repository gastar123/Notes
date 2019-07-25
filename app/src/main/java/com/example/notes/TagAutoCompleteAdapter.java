package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.notes.dto.Tag;
import com.example.notes.editor.NoteActivity;

import java.util.ArrayList;
import java.util.List;

public class TagAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private NoteActivity noteActivity;
    private List<Tag> results = new ArrayList<>();

    public TagAutoCompleteAdapter(NoteActivity noteActivity) {
        this.noteActivity = noteActivity;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Tag getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(noteActivity);
            convertView = inflater.inflate(R.layout.drop_down_tags, parent, false);
        }

        Tag tag = getItem(position);
        ((TextView) convertView.findViewById(R.id.tagText)).setText(tag.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
//                if (constraint != null) {
//                    results = noteActivity.getTags(constraint.toString());
//                    filterResults.values = results;
//                    filterResults.count = results.size();
//                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                if (constraint != null) {
                    results = noteActivity.getTags(constraint.toString());
                }

                if (results != null && results.size() > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
