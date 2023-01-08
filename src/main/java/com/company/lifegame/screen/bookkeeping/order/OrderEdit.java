package com.company.lifegame.screen.bookkeeping.order;

import com.company.lifegame.entity.bookkeeping.Currency;
import com.company.lifegame.entity.bookkeeping.OrderItem;
import com.company.lifegame.entity.bookkeeping.Provider;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("lg_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
    @Autowired
    private DataGrid<OrderItem> orderItemsTable;
    @Autowired
    private EntityPicker<Currency> currencyField;
    @Autowired
    private TextField<BigDecimal> valueField;

    @Subscribe("providerField")
    public void onProviderFieldValueChange(HasValue.ValueChangeEvent<Provider> event) {
        currencyField.setValue(event.getValue().getCurrency());
    }

    @Subscribe(id = "orderItemsDc", target = Target.DATA_CONTAINER)
    public void onOrderItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<OrderItem> event) {
        BigDecimal sum = orderItemsTable.getItems().getItems()
                .map(OrderItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valueField.setValue(sum);
    }

}