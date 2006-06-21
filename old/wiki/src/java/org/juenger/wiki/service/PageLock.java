package org.juenger.wiki.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.juenger.wiki.item.Page;


public final class PageLock {

    private String editor;
    private Date acquiredTime;
    private Date expiryTime;
    private Page lockedPage;
    
    public PageLock(String editor, int seconds, Page pageToLock) {
        super();
        if (editor == null) {
            throw new IllegalArgumentException("editor is null");
        }
        editor = editor.trim();
        if (editor.length() == 0) {
            throw new IllegalArgumentException("editor is empty string");
        }
        if (seconds <= 0) {
            throw new IllegalArgumentException("cannot hold lock for less than zero seconds");
        }
        if (pageToLock == null) {
            throw new IllegalArgumentException("pageToLock is null");
        }
        this.editor = editor;
        GregorianCalendar gc = new GregorianCalendar();
        acquiredTime = gc.getTime();
        gc.add(Calendar.SECOND, seconds);
        expiryTime = gc.getTime();
        this.lockedPage = pageToLock;
    }

    public Date getAcquiredTime() {
        return acquiredTime;
    }
    
    public String getEditor() {
        return editor;
    }
    
    public Date getExpiryTime() {
        return expiryTime;
    }
    
    public Page getLockedPage() {
        return lockedPage;
    }
    
    public boolean isExpired() {
        return new Date().after(expiryTime);
    }
    
    public synchronized void expire(String editor) {
        if (editor == null) {
            throw new IllegalArgumentException("editor is null");
        }
        if (editor.equals(this.editor)) {
            expiryTime = new Date();
        }
        else throw new IllegalArgumentException("editor " + editor + " did hold lock");
    }

    
}
