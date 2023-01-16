package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum OperationType implements EnumClass<String> {

    EXPENSE("Expense"),
    INCOME("Income"),
    TRANSFER("Transfer");

    private final String id;

    OperationType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static OperationType fromId(String id) {
        for (OperationType at : OperationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}