package com.company.lifegame.service;

import com.company.lifegame.entity.Task;
import com.company.lifegame.entity.TaskTimer;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component("lg_TaskTimerService")
public class TaskTimerService {
    @Autowired
    private DataManager dataManager;

    public TaskTimer start(Task task) {
        // Удаляем текущий таймер если он небыл остановлен
        getCurrentTimer(task).ifPresent(taskTimer ->
                dataManager.remove(taskTimer));

        TaskTimer timer = dataManager.create(TaskTimer.class);
        timer.setBegin(LocalDateTime.now());
        timer.setCurrent(true);
        timer.setTask(task);

        return dataManager.save(timer);
    }

    @Nullable
    public TaskTimer stop(Task task) {
        Optional<TaskTimer> currentTimerOptional = getCurrentTimer(task);
        if (currentTimerOptional.isPresent()) {
            TaskTimer currentTimer = currentTimerOptional.get();
            currentTimer.setCurrent(false);
            currentTimer.setEnd(LocalDateTime.now());
            return dataManager.save(currentTimer);
        }

        return null;
    }

    public Optional<TaskTimer> getCurrentTimer(Task task) {
        return dataManager.load(TaskTimer.class)
                .query("e.task = :task and e.current = :current")
                .parameter("task", task)
                .parameter("current", true)
                .optional();
    }

    public Duration getDurationAll(Task task) {
        Duration duration = Duration.ZERO;

        List<TaskTimer> taskTimerList =  dataManager.load(TaskTimer.class)
                .query("e.current = false")
                .list();
        for (TaskTimer timer : taskTimerList) {
            duration = duration.plus(Duration.between(timer.getBegin(), timer.getEnd()));
        }

        return duration;
    }

    public String format(Duration duration) {
        long hours = duration.toHours();
        long mins = duration.minusHours(hours).toMinutes();
        long seconds = (duration.minusMinutes(mins).toMillis() / 1000) %60;
        return String.format("%02d:%02d:%02ds", hours, mins, seconds);
    }
}