package com.company.lifegame.screen.bookkeeping.orderitem;

import com.company.lifegame.entity.bookkeeping.Order;
import com.company.lifegame.entity.bookkeeping.OrderItem;
import com.company.lifegame.entity.bookkeeping.Price;
import com.company.lifegame.entity.bookkeeping.ProviderItem;
import com.company.lifegame.service.bookkeeping.PriceService;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@UiController("lg_OrderItem.edit")
@UiDescriptor("order-item-edit.xml")
@EditedEntityContainer("orderItemDc")
public class OrderItemEdit extends StandardEditor<OrderItem> {
    @Autowired
    private PriceService priceService;
    @Autowired
    private EntityPicker<Order> orderField;
    @Autowired
    private EntityPicker<ProviderItem> providerItemField;
    @Autowired
    private EntityPicker<Price> priceField;
    @Autowired
    private TextField<BigDecimal> valueField;
    @Autowired
    private TextField<BigDecimal> discountField;
    @Autowired
    private TextField<BigDecimal> amountField;

    @Install(to = "providerItemField.entityLookup", subject = "screenOptionsSupplier")
    private ScreenOptions providerItemFieldEntityLookupScreenOptionsSupplier() {
        Order order = orderField.getValue();
        if (order != null && order.getProvider() != null) {
            return new MapScreenOptions(ParamsMap.of("provider", order.getProvider()));
        }

        return new MapScreenOptions(ParamsMap.empty());
    }

    @Install(to = "priceField.entityLookup", subject = "screenOptionsSupplier")
    private ScreenOptions priceFieldEntityLookupScreenOptionsSupplier() {
        Map<String, Object> params = new HashMap<>();

        Order order = orderField.getValue();
        if (order != null && order.getProvider() != null) {
            params.put("provider", order.getProvider());
        }

        if (providerItemField.getValue() != null) {
            params.put("providerItem", providerItemField.getValue());
        }

        return new MapScreenOptions(params);
    }

    @Subscribe("providerItemField")
    public void onProviderItemFieldValueChange(HasValue.ValueChangeEvent<ProviderItem> event) {
        priceService.findPrice(event.getValue())
                .ifPresent(price -> priceField.setValue(price));
    }

    @Subscribe("amountField")
    public void onAmountFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        updateValue();
    }

    @Subscribe("discountField")
    public void onDiscountFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        updateValue();
    }

    private void updateValue() {
        if (providerItemField.getValue() == null) return;
        if (providerItemField.getValue().getNomenclature() == null) return;
        if (priceField.getValue() == null) return;
        if (amountField.getValue() == null) return;

        BigDecimal price = priceField.getValue().getValue();
        BigDecimal discount = discountField.isEmpty() ? BigDecimal.ZERO : discountField.getValue();
        BigDecimal amount = amountField.getValue();

        valueField.setValue(price.subtract(discount).multiply(amount));
    }

}