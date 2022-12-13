package com.company.lifegame.screen.task;

import com.company.lifegame.entity.TaskTimer;
import com.company.lifegame.screen.fragment.timerfragment.TimerFragment;
import com.company.lifegame.service.TaskTimerService;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.InstanceLoader;
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
    private GroupBoxLayout timerGroupBox;

    @Autowired
    private Fragments fragments;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

        if (getEditedEntity().getCreatedDate() != null) {
            timerGroupBox.setVisible(true);

            TimerFragment timerFragment = fragments.create(this, TimerFragment.class);
            timerFragment.setTask(getEditedEntity());
            timerGroupBox.add(timerFragment.getFragment());

        }

    }

}