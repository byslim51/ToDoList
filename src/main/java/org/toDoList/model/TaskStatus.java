package org.toDoList.model;

public enum TaskStatus {
    NEW("Новый"),
    IN_PROGRESS("В работе"),
    COMPLETE("Выполнен");

    private String title;

    TaskStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
