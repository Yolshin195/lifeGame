package com.company.lifegame.service;

import com.company.lifegame.entity.Task;
import com.company.lifegame.entity.TaskStatus;
import com.company.lifegame.entity.TaskTemplate;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component("lg_TaskTemplateService")
public class TaskTemplateService {
    private static final Logger log = LoggerFactory.getLogger(TaskTemplateService.class);

    @Autowired
    private DataManager dataManager;

    public void createAllTask() {
        List<TaskTemplate> taskTemplateList = dataManager.load(TaskTemplate.class)
                .query("e.active = true and e.deletedDate is null")
                .list();

        log.info("Task templates found: {}", taskTemplateList.size());

        taskTemplateList.forEach(this::createTask);
    }

    public void createTask(TaskTemplate taskTemplate) {
        log.info("CreateTask name '{}', start", taskTemplate.getName());

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        boolean create = switch (dayOfWeek) {
            case MONDAY -> taskTemplate.getMonday();
            case TUESDAY -> taskTemplate.getTuesday();
            case WEDNESDAY -> taskTemplate.getWednesday();
            case THURSDAY -> taskTemplate.getThursday();
            case FRIDAY -> taskTemplate.getFriday();
            case SATURDAY -> taskTemplate.getSaturday();
            case SUNDAY -> taskTemplate.getSunday();
        };

        if (!create) {
            log.info("CreateTask name '{}', skipped", taskTemplate.getName());
            return;
        }

        Task task = dataManager.create(Task.class);
        task.setCreatedBy(taskTemplate.getCreatedBy());
        task.setCreatedDate(new Date());
        task.setParent(taskTemplate.getParentTask());
        task.setName(taskTemplate.getName());
        task.setDescription(taskTemplate.getDescription());
        task.setStatus(TaskStatus.WORK);
        task.setTodo(true);
        task.setArchive(false);

        dataManager.save(task);

        log.info("CreateTask name '{}', created", taskTemplate.getName());
    }
}