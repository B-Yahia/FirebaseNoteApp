package com.example.secnoteapp.create_new_notes;

public class NewNote {
    String title,content,id,time;

    public NewNote() {}
    public NewNote(String title, String content,String id) {
        this.title = title;
        this.content = content;
        this.id = id;
     //   this.time = time;

    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
