package com.company.lifegame.screen.tasktemplate;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.TaskTemplate;

@UiController("lg_TaskTemplate.browse")
@UiDescriptor("task-template-browse.xml")
@LookupComponent("taskTemplatesTable")
public class TaskTemplateBrowse extends StandardLookup<TaskTemplate> {
}