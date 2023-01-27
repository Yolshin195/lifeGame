package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@JmixEntity
@Table(name = "LG_ORDER", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_LG_ORDER_UNIQ_OPERATION", columnNames = "OPERATION_ID")
})
@Entity(name = "lg_Order")
public class Order {

    @InstanceName
    @DependsOnProperties({"date", "category", "value"})
    public String getDisplayName() {
        return String.format("%s(%s) - %s",
                (category != null) ? category.getName() : null,
                (date != null) ? date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null, value);
    }

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "DATE_")
    private LocalDateTime date;

    @Column(name = "YEAR_")
    private Integer year;

    @Column(name = "MONTH_")
    private Integer month;

    @Column(name = "DAY_")
    private Integer day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDER_ID")
    private Provider provider;

    @Composition
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "VALUE_")
    private BigDecimal value;

    @Column(name = "VALUE_USD")
    private BigDecimal valueUSD;

    @Column(name = "VALUE_RUB")
    private BigDecimal valueRUB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setValueRUB(BigDecimal valueRUB) {
        this.valueRUB = valueRUB;
    }

    public BigDecimal getValueRUB() {
        return valueRUB;
    }

    public void setValueUSD(BigDecimal valueUSD) {
        this.valueUSD = valueUSD;
    }

    public BigDecimal getValueUSD() {
        return valueUSD;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        if (date != null) {
            this.year = date.getYear();
            this.month = date.getMonth().getValue();
            this.day = date.getDayOfMonth();
        }
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

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

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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