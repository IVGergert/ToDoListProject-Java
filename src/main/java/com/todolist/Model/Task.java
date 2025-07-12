package com.todolist.Model;

public class Task {
    private int id;
    private int userId;
    private String title;
    private boolean isDone;

    public Task() {
    }

    public Task(int id, int userId, String title, boolean isDone) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return title + (isDone ? " ✔" : "");
    }
}
