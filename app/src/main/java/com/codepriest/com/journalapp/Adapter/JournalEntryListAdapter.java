package com.codepriest.com.journalapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepriest.com.journalapp.Model.JournalEntry;
import com.codepriest.com.journalapp.R;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created by codepriest on 27/06/2018.
 */

public class JournalEntryListAdapter extends RecyclerView.Adapter {
    private OnListItemClickedListener onListItemClickedListener;
    private JournalEntry[] mjournalEntries;

    public JournalEntryListAdapter(OnListItemClickedListener onClickListener) {

        this.onListItemClickedListener = onClickListener;
    }

    public JournalEntry[] getMjournalEntries() {
        return mjournalEntries;
    }

    public void setMjournalEntries(JournalEntry[] mjournalEntries) {
        this.mjournalEntries = mjournalEntries;
    }

    @NonNull
    @Override
    public JournalEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_entry_list_item, parent, false);
        return new JournalEntryViewHolder(view, this.onListItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        JournalEntryViewHolder journalEntryViewHolder = (JournalEntryViewHolder) holder;
        JournalEntry journalEntry = mjournalEntries[position];
        journalEntryViewHolder.mContent.setText(journalEntry.getContent().length() > 10 ? String.format("%s ...", journalEntry.getContent().substring(0, 10)) : journalEntry.getContent());
        PrettyTime prettyTime = new PrettyTime();
        journalEntryViewHolder.mDateEditted.setText(prettyTime.format(journalEntry.getUpdateDate()));
        journalEntryViewHolder.mTitle.setText(journalEntry.getTitle());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mjournalEntries == null ? 0 : mjournalEntries.length;
    }

    public interface OnListItemClickedListener {
        void onClickItem(int indexOfItem);
    }

    public static class JournalEntryViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mDateEditted;
        private TextView mTitle;
        private TextView mContent;

        private JournalEntryViewHolder(View v, final OnListItemClickedListener listener) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItem(getAdapterPosition());
                }
            });
            mDateEditted = v.findViewById(R.id.date_edited);
            mTitle = v.findViewById(R.id.journal_entry_title_id);
            mContent = v.findViewById(R.id.journal_entry_content_id);

        }
    }
}
