package com.codepriest.com.journalapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.codepriest.com.journalapp.Adapter.JournalEntryListAdapter;
import com.codepriest.com.journalapp.Model.JournalEntry;
import com.codepriest.com.journalapp.R;
import com.codepriest.com.journalapp.Utils.FireBaseConfiguration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends BaseActivity implements JournalEntryListAdapter.OnListItemClickedListener, ValueEventListener, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private JournalEntryListAdapter mJournalEntryListAdapter;
    private LinearLayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private String TAG = JournalListActivity.class.getSimpleName();
    private int LOADER_ID = 22;
    private String QUERY_PARAMS = "QUERY_PARAMS";
    private ProgressBar progressBar;
    private View journalListView;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent returnToLogin = new Intent(this, LoginActivity.class);
            startActivity(returnToLogin);
        }
        floatingActionButton = findViewById(R.id.add_new_journal_entry_id);
        floatingActionButton.setOnClickListener(this);
        journalListView = findViewById(R.id.journal_list);
        progressBar = findViewById(R.id.list_progress);
        mRecyclerView = findViewById(R.id.journal_list_id);
        mJournalEntryListAdapter = new JournalEntryListAdapter(this);
        mRecyclerView.setAdapter(mJournalEntryListAdapter);
        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        databaseReference = FireBaseConfiguration.getFireBaseDataBaseReferenceForUser(this);
        RetrieveJournalEntryList(null);
    }

    private void showProgress(boolean value) {
        progressBar.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
        journalListView.setVisibility(!value ? View.VISIBLE : View.INVISIBLE);
    }

    private void RetrieveJournalEntryList(String queryParam) {

        Bundle bundle = new Bundle();
        bundle.putString(QUERY_PARAMS, queryParam);
        showProgress(true);
        if (queryParam == null) {
            Query query = databaseReference.orderByChild("updateDate");

            query.addValueEventListener(this);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClickItem(int indexOfItem) {
        JournalEntry journalEntry = mJournalEntryListAdapter.getMjournalEntries()[indexOfItem];
        Intent intent = new Intent(this, ViewJournalEntryActivity.class);
        Bundle journalEntryBundle = new Bundle();
        journalEntryBundle.putString("id", journalEntry.getId());
        journalEntryBundle.putString("title", journalEntry.getTitle());
        journalEntryBundle.putString("content", journalEntry.getContent());
        journalEntryBundle.putLong("createDate", journalEntry.getCreatedDate().getTime());
        journalEntryBundle.putLong("updateDate", journalEntry.getUpdateDate().getTime());
        intent.putExtra(getResources().getString(R.string.selected_journal_entry), journalEntryBundle);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        Intent startAddJournalActivity = new Intent(this, AddOrUpdateJournalActivity.class);
        startActivity(startAddJournalActivity);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<JournalEntry> journalEntries = new ArrayList<>();
        for (DataSnapshot shot : dataSnapshot.getChildren()
                ) {

            String key = shot.getKey();
            JournalEntry journalEntry = shot.getValue(JournalEntry.class);
            journalEntry.setId(key);
            journalEntries.add(journalEntry);

        }
        JournalEntry[] journalEntries1 = journalEntries.toArray(new JournalEntry[journalEntries.size()]);
        mJournalEntryListAdapter.setMjournalEntries(journalEntries1);
        mJournalEntryListAdapter.notifyDataSetChanged();
        showProgress(false);
        Log.d("From Loader", String.valueOf(journalEntries));

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
