package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "LG_RATE")
@Entity(name = "lg_Rate")
public class Rate {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "DATE_")
    private LocalDate date = LocalDate.now();

    @Column(name = "CODE")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_ID")
    private Currency from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_ID")
    private Currency to;

    @Column(name = "VALUE_")
    private BigDecimal value;

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getFrom() {
        return from;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public Currency getTo() {
        return to;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}