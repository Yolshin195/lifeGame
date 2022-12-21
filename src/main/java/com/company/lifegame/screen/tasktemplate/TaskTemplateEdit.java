package com.company.lifegame.screen.tasktemplate;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.TaskTemplate;

@UiController("lg_TaskTemplate.edit")
@UiDescriptor("task-template-edit.xml")
@EditedEntityContainer("taskTemplateDc")
public class TaskTemplateEdit extends StandardEditor<TaskTemplate> {
}