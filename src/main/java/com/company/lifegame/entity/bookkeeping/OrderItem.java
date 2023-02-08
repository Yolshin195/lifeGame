package com.company.lifegame.entity.bookkeeping;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "LG_ORDER_ITEM")
@Entity(name = "lg_OrderItem")
public class OrderItem {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDER_ITEM_ID")
    private ProviderItem providerItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICE_ID")
    private Price price;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DISCOUNT_TYPE")
    private String discountType;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    @Column(name = "VALUE_")
    private BigDecimal value;

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscountType(DiscountTypeEnum discountType) {
        this.discountType = (discountType == null) ? null : discountType.getId();
    }

    public DiscountTypeEnum getDiscountType() {
        return DiscountTypeEnum.fromId(discountType);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setProviderItem(ProviderItem providerItem) {
        this.providerItem = providerItem;
    }

    public ProviderItem getProviderItem() {
        return providerItem;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
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