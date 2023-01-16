package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "LG_OPERATION")
@Entity(name = "lg_Operation")
public class Operation {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name = "TYPE_")
    private String type;

    @InstanceName
    @Column(name = "DATE_")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ONE_ID")
    private Account accountOne;
    @Column(name = "VALUE_ONE")
    private BigDecimal valueOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TWO_ID")
    private Account accountTwo;
    @Column(name = "VALUE_TWO")
    private BigDecimal valueTwo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "COMMENT_")
    private String comment;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setValueTwo(BigDecimal valueTwo) {
        this.valueTwo = valueTwo;
    }

    public BigDecimal getValueTwo() {
        return valueTwo;
    }

    public void setAccountTwo(Account accountTwo) {
        this.accountTwo = accountTwo;
    }

    public Account getAccountTwo() {
        return accountTwo;
    }

    public void setValueOne(BigDecimal valueOne) {
        this.valueOne = valueOne;
    }

    public BigDecimal getValueOne() {
        return valueOne;
    }

    public void setAccountOne(Account accountOne) {
        this.accountOne = accountOne;
    }

    public Account getAccountOne() {
        return accountOne;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setType(OperationType type) {
        this.type = type.getId();
    }

    public OperationType getType() {
        return OperationType.fromId(type);
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}