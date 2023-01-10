package com.company.lifegame.screen.bookkeeping.order;

import com.company.lifegame.entity.bookkeeping.Currency;
import com.company.lifegame.entity.bookkeeping.OrderItem;
import com.company.lifegame.entity.bookkeeping.Provider;
import com.company.lifegame.service.bookkeeping.RateService;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@UiController("lg_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
    @Autowired
    private DataGrid<OrderItem> orderItemsTable;
    @Autowired
    private DateField<LocalDateTime> dateField;
    @Autowired
    private EntityPicker<Currency> currencyField;
    @Autowired
    private TextField<BigDecimal> valueField;
    @Autowired
    private CurrencyField<BigDecimal> valueUSDField;
    @Autowired
    private CurrencyField<BigDecimal> valueRUBField;
    @Autowired
    private RateService rateService;

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

        if (currencyField.getValue() != null) {
            updateValueUSDAndRUB();
        }
    }

    @Subscribe("currencyField")
    public void onCurrencyFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        if (currencyField.getValue() != null) {
            updateValueUSDAndRUB();
        }
    }

    private void updateValueUSDAndRUB() {
        valueUSDField.setValue(rateService.convertToUSD(valueField.getValue(), currencyField.getValue(), dateField.getValue()));
        valueRUBField.setValue(rateService.convertToRUB(valueField.getValue(), currencyField.getValue(), dateField.getValue()));
    }

}