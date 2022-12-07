package com.company.lifegame.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum TaskStatus implements EnumClass<String> {

    NEW("New"),
    ANALYSIS("Analysis"),
    WORK("Work"),
    DONE("Done"),
    CANCELED("Canceled");

    private final String id;

    TaskStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TaskStatus fromId(String id) {
        for (TaskStatus at : TaskStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}