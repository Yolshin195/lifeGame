package com.company.lifegame.screen.task;

import com.company.lifegame.screen.fragment.timerfragment.TimerFragment;
import com.company.lifegame.service.TaskService;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ButtonsPanel;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.TreeTable;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
    @Autowired
    private TaskService taskService;

    @Autowired
    private CollectionLoader<Task> tasksDl;

    @Autowired
    private Fragments fragments;

    @Autowired
    private ButtonsPanel buttonsPanel;
    @Autowired
    private TreeTable<Task> tasksTable;

    @Autowired
    private Button doneBtn;

    @Autowired
    private Button todoBtn;

    TimerFragment timerFragment;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        timerFragment = fragments.create(this, TimerFragment.class);
        timerFragment.setTask(null);
        buttonsPanel.add(timerFragment.getFragment());
    }

    @Subscribe("tasksTable")
    public void onTasksTableSelection(Table.SelectionEvent<Task> event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            timerFragment.setTask(task);
            doneBtn.setEnabled(true);
            todoBtn.setEnabled(true);
        }
    }

    @Subscribe("todoBtn")
    public void onTodoBtnClick(Button.ClickEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            taskService.addToTodoList(task);
            tasksDl.load();
        }
    }

    @Subscribe("doneBtn")
    public void onDoneBtnClick(Button.ClickEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task != null) {
            taskService.done(task);
            tasksDl.load();
        }
    }

}