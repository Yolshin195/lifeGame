package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum UnitEnum implements EnumClass<String> {

    PIECES("Pieces"),
    KG("Kg");

    private final String id;

    UnitEnum(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static UnitEnum fromId(String id) {
        for (UnitEnum at : UnitEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}