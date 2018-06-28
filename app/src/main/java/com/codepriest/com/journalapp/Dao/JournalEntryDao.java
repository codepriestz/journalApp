//package com.codepriest.com.journalapp.Dao;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;
//
//import com.codepriest.com.journalapp.Model.JournalEntry;
//
//import java.util.List;
//
///**
// * Created by codepriest on 27/06/2018.
// */
//@Dao
//public interface JournalEntryDao {
//    @Query("select * from journal_entry")
//    LiveData<List<JournalEntry>> findAll();
//
//    @Query("select * from journal_entry where title like '%:param%' || content like '%:param%' ")
//    LiveData<List<JournalEntry>> findByParameter(String param);
//    @Insert
//    void  insert(JournalEntry journalEntry);
//    @Delete
//    void delete(JournalEntry journalEntry);
//    @Update(onConflict=OnConflictStrategy.REPLACE)
//    void update(JournalEntry journalEntry);
//
//}
