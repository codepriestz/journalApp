package com.codepriest.com.journalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepriest.com.journalapp.Model.JournalEntry;
import com.codepriest.com.journalapp.R;
import com.codepriest.com.journalapp.Utils.FireBaseConfiguration;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddOrUpdateJournalActivity extends BaseActivity implements DatabaseReference.CompletionListener {
    DatabaseReference databaseReference;
    TextInputEditText mTitleView;
    EditText mContentView;
    Bundle bundle;
    boolean isUpdate = false;
    String title;
    String id;
    String content;
    String updateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_update_journal);
        mTitleView = findViewById(R.id.journal_entry_title_id);
        mContentView = findViewById(R.id.journal_entry_content_id);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        Intent intent = getIntent();
        if (intent != null) {
            bundle = intent.getBundleExtra(getResources().getString(R.string.selected_journal_entry));
            if (bundle != null) {
                isUpdate = true;
                id = bundle.getString("id");
                title = bundle.getString("title");
                content = bundle.getString("content");

            }
        }
        if (isUpdate) {
            mTitleView.setText(title);
            mContentView.setText(content);
        }

        databaseReference = FireBaseConfiguration.getFireBaseDataBaseReferenceForUser(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_new_entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_entry_id:
                View focus = new View(this);
                boolean cancel = false;
                String title = mTitleView.getText().toString();
                String content = mContentView.getText().toString();
                if (title.isEmpty()) {
                    mTitleView.setError("Title cannot be empty");
                    focus = mTitleView;
                    cancel = true;
                }
                if (content.isEmpty()) {
                    mContentView.setError("Content cannot be empty");
                    focus = mContentView;
                    cancel = true;
                }
                if (cancel)
                    focus.requestFocus();
                if (!cancel) {
                    JournalEntry entry = new JournalEntry(title, content, new Date(), new Date());
                    if (!isUpdate) {
                        databaseReference.push().setValue(entry, this);
                    } else {
                        Map<String, Object> updateObject = new HashMap<>();
                        updateObject.put("title", title);
                        updateObject.put("content", content);
                        updateObject.put("updateDate", new Date());
                        databaseReference.child(id).updateChildren(updateObject, this);
                    }
                }
                break;
            case R.id.homeAsUp:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
        if (databaseError != null) {
            Toast.makeText(getApplicationContext(), "Error while trying to Journal ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), isUpdate ? "Journal Entry Editted Successfully" : "Journal Entry Added Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), JournalListActivity.class);
            startActivity(intent);
        }
    }


}
