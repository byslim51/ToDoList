package org.toDoList.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    int id;
    String name;
    String task;
    String personalUrl;
    String userName;
    TaskStatus status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}

