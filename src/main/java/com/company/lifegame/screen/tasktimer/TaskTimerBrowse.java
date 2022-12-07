package com.company.lifegame.screen.tasktimer;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.TaskTimer;

@UiController("lg_TaskTimer.browse")
@UiDescriptor("task-timer-browse.xml")
@LookupComponent("taskTimersTable")
public class TaskTimerBrowse extends StandardLookup<TaskTimer> {
}