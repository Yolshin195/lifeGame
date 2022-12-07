package com.company.lifegame.screen.task;

import com.company.lifegame.entity.TaskTimer;
import com.company.lifegame.service.TaskTimerService;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import io.jmix.ui.component.Timer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@UiController("lg_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    protected Timer timer;

    @Autowired
    private TaskTimerService taskTimerService;
    @Autowired
    private GroupBoxLayout timerGroupBox;
    @Autowired
    private Label<String> timerLabel;

    private TaskTimer currentTimer;
    private Duration lastTimers;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (getEditedEntity().getCreatedDate() != null) {
            timerGroupBox.setVisible(true);
            lastTimers = taskTimerService.getDurationAll(getEditedEntity());

            Optional<TaskTimer> currentTimerOptional = taskTimerService.getCurrentTimer(getEditedEntity());
            if (currentTimerOptional.isPresent()) {
                currentTimer = currentTimerOptional.get();
                onTimerTick(null);
                timer.start();
            } else {
                timerLabel.setValue(taskTimerService.format(lastTimers));
            }

        }
    }

    @Subscribe("timerStartBtn")
    public void onTimerStartBtnClick(Button.ClickEvent event) {
        currentTimer = taskTimerService.start(getEditedEntity());
        timer.start();
    }

    @Subscribe("timerStopBtn")
    public void onTimerStopBtnClick(Button.ClickEvent event) {
        timer.stop();
        currentTimer = taskTimerService.stop(getEditedEntity());
        lastTimers = lastTimers.plus(Duration.between(currentTimer.getBegin(), currentTimer.getEnd()));
    }

    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        timerLabel.setValue(taskTimerService
                .format(lastTimers.plus(Duration.between(currentTimer.getBegin(), LocalDateTime.now()))));
    }

}