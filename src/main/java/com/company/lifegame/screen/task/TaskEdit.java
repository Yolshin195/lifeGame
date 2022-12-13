package com.company.lifegame.screen.task;

import com.company.lifegame.entity.TaskTimer;
import com.company.lifegame.screen.fragment.timerfragment.TimerFragment;
import com.company.lifegame.service.TaskTimerService;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@UiController("lg_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    private Fragments fragments;

    @Autowired
    private ButtonsPanel timerButtonsPanel;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

        if (getEditedEntity().getCreatedDate() != null) {
            timerButtonsPanel.setVisible(true);

            TimerFragment timerFragment = fragments.create(this, TimerFragment.class);
            timerFragment.setTask(getEditedEntity());
            timerButtonsPanel.add(timerFragment.getFragment());
        }
    }
}