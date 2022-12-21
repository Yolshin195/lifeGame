package com.company.lifegame.screen.task;

import com.company.lifegame.screen.fragment.timerfragment.TimerFragment;
import com.company.lifegame.service.TaskService;
import io.jmix.ui.Fragments;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ButtonsPanel;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_ToDoTask.browse")
@UiDescriptor("todo-task-browse.xml")
@LookupComponent("tasksTable")
public class ToDoTaskBrowse extends StandardLookup<Task> {
    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private CollectionLoader<Task> tasksDl;

    @Autowired
    private TaskService taskService;

    @Autowired
    private Fragments fragments;

    @Autowired
    private ButtonsPanel buttonsPanel;

    @Autowired
    private GroupTable<Task> tasksTable;

    TimerFragment timerFragment;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        timerFragment = fragments.create(this, TimerFragment.class);
        timerFragment.setTask(null);
        buttonsPanel.add(timerFragment.getFragment());
    }

    @Subscribe("tasksTable.add")
    public void onTasksTableAdd(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(Task.class, this)
                .withSelectHandler(tasks -> {
                    tasks.forEach(task -> taskService.addToTodoList(task));
                    tasksDl.load();
                })
                .build()
                .show();
    }

    @Subscribe("tasksTable")
    public void onTasksTableSelection(Table.SelectionEvent<Task> event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            timerFragment.setTask(task);
        }
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            taskService.cancel(task);
            tasksDl.load();
        }
    }

    @Subscribe("doneBtn")
    public void onDoneBtnClick(Button.ClickEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            taskService.doneAndArchive(task);
            tasksDl.load();
        }
    }

}