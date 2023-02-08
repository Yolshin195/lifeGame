package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum DiscountTypeEnum implements EnumClass<String> {

    PERCENTAGE("Percentage"),
    CURRENCY("Currency");

    private final String id;

    DiscountTypeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DiscountTypeEnum fromId(String id) {
        for (DiscountTypeEnum at : DiscountTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}