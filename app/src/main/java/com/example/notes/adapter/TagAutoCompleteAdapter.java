package com.example.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.annimon.stream.function.Function;
import com.example.notes.R;
import com.example.notes.dto.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private Function<String, List<Tag>> tagsProvider;
    private List<Tag> results = new ArrayList<>();

    public TagAutoCompleteAdapter(Function<String, List<Tag>> tagsProvider) {
        this.tagsProvider = tagsProvider;
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
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                if (constraint != null) {
                    results = tagsProvider.apply(constraint.toString());
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
