package com.company.lifegame.screen.fragment.timerfragment;

import com.company.lifegame.entity.Task;
import com.company.lifegame.entity.TaskTimer;
import com.company.lifegame.service.TaskTimerService;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@UiController("lg_TimerFragment")
@UiDescriptor("timer-fragment.xml")
public class TimerFragment extends ScreenFragment {

    @Autowired
    protected Timer timer;

    @Autowired
    private Button toggleTimerBtn;

    @Autowired
    private TaskTimerService taskTimerService;

    private TaskTimer currentTimer;
    private Duration lastTimers;

    private Task task;

    private boolean running = false;

    private void onInit() {
        if (task == null || task.getCreatedDate() == null) return;

        this.toggleTimerBtn.setEnabled(true);

        lastTimers = taskTimerService.getDurationAll(task);

        Optional<TaskTimer> currentTimerOptional = taskTimerService.getCurrentTimer(task);
        if (currentTimerOptional.isPresent()) {
            currentTimer = currentTimerOptional.get();
            onTimerTick(null);
            timer.start();
        } else {
            String value = taskTimerService.format(lastTimers);
            toggleTimerBtn.setCaption(value);
        }

    }

    @Subscribe("toggleTimerBtn")
    public void onTimerToggleBtnClick(Button.ClickEvent event) {
        if (task == null) return;

        if (!running) {
            toggleTimerBtn.setStyleName("danger");
            toggleTimerBtn.setIconFromSet(JmixIcon.STOP);

            currentTimer = taskTimerService.start(task);
            timer.start();
            running = true;
        } else {
            timer.stop();
            currentTimer = taskTimerService.stop(task);
            //TODO Warning:(70, 72) Method invocation 'getBegin' may produce 'NullPointerException'
            lastTimers = lastTimers.plus(Duration.between(currentTimer.getBegin(), currentTimer.getEnd()));

            toggleTimerBtn.setStyleName("");
            toggleTimerBtn.setIconFromSet(JmixIcon.PLAY);
            running = false;
        }
    }

    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        String value = taskTimerService
                .format(lastTimers.plus(Duration.between(currentTimer.getBegin(), LocalDateTime.now())));
        toggleTimerBtn.setCaption(value);
    }

    public void setTask(Task task) {
        this.task = task;
        onInit();
    }
}