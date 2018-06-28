package com.codepriest.com.journalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepriest.com.journalapp.R;

public class ViewJournalEntryActivity extends BaseActivity {
    TextView mTitle;
    TextView mContent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal_entry);
        mTitle = findViewById(R.id.journal_entry_title_id);
        mContent = findViewById(R.id.journal_entry_content_id);
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra(getResources().getString(R.string.selected_journal_entry));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText(bundle.getString("title"));
        mContent.setText(bundle.getString("content"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp)
            NavUtils.navigateUpFromSameTask(this);
        if (item.getItemId() == R.id.edit_entry_id) {
            intent.setClass(this, AddOrUpdateJournalActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
