package com.codepriest.com.journalapp.Model;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by codepriest on 27/06/2018.
 */

public class JournalEntry {
    @Exclude
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private Date updateDate;


    public JournalEntry(String title, String content, Date createdDate, Date updateDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public JournalEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


}
