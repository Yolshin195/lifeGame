package com.company.lifegame.screen.task;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;

@UiController("lg_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
}