package com.company.lifegame.screen.task;

import io.jmix.ui.component.ButtonsPanel;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
    @Autowired
    private ButtonsPanel buttonsPanel;
}