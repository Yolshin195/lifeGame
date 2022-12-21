package com.company.lifegame.service.job;

import com.company.lifegame.service.TaskTemplateService;
import io.jmix.core.security.Authenticated;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskTemplateJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(TaskTemplateJob.class);

    @Autowired
    TaskTemplateService taskTemplateService;

    @Override
    @Authenticated
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Started job 'Create all task template'");
        taskTemplateService.createAllTask();
        log.info("Ended job 'Create all task template'");
    }
}
