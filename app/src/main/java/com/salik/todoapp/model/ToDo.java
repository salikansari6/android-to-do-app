package com.salik.todoapp.model;

import androidx.annotation.NonNull;

public class ToDo {
    private int id;
    private String title;
    private boolean completed;

    public ToDo() {
    }

    public ToDo(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return "title: " + this.title;
    }
}
