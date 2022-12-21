package com.company.lifegame.service;

import com.company.lifegame.entity.Task;
import com.company.lifegame.entity.TaskStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("lg_TaskService")
public class TaskService {
    @Autowired
    private DataManager dataManager;

    public void done(Task task) {
        task.setTodo(false);
        task.setStatus(TaskStatus.DONE);
        dataManager.save(task);
    }

    public void doneAndArchive(Task task) {
        task.setTodo(false);
        task.setArchive(true);
        task.setStatus(TaskStatus.DONE);
        dataManager.save(task);
    }

    public void cancel(Task task) {
        task.setTodo(false);
        task.setStatus(TaskStatus.CANCELED);
        dataManager.save(task);
    }

    public void addToTodoList(Task task) {
        task.setTodo(true);
        task.setStatus(TaskStatus.WORK);
        dataManager.save(task);
    }
}