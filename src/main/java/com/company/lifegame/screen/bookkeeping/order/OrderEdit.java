package com.company.lifegame.screen.bookkeeping.order;

import com.company.lifegame.entity.bookkeeping.*;
import com.company.lifegame.service.DateService;
import com.company.lifegame.service.bookkeeping.AccountService;
import com.company.lifegame.service.bookkeeping.OperationService;
import com.company.lifegame.service.bookkeeping.RateService;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
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
    private EntityPicker<Account> accountField;
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
    @Autowired
    private DateService dateService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityPicker<Operation> operationField;
    @Autowired
    private OperationService operationService;
    @Autowired
    private InstanceLoader<Order> orderDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        orderDl.load();
        dateField.setValue(dateService.now());

        // Показываем кнопку создания операции, если к чеку ещё не превязанна операция
        if (operationField.getValue() == null) {
            operationField.addAction(new BaseAction("entityCreate")
                    .withCaption(null)
                    .withDescription("Создать операцию")
                    .withIcon(JmixIcon.CREATE_ACTION.source())
                    .withHandler(e -> {
                        if (operationService.validate(getEditedEntity())) {
                            operationField.setValue(operationService.create(getEditedEntity()));
                            operationField.removeAction("entityCreate");
                        } else {

                        }
                    }), 0);
        }
    }

    @Subscribe("providerField")
    public void onProviderFieldValueChange(HasValue.ValueChangeEvent<Provider> event) {
        if (event.getValue() != null) {
            currencyField.setValue(event.getValue().getCurrency());
        }
    }

    @Subscribe(id = "orderItemsDc", target = Target.DATA_CONTAINER)
    public void onOrderItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<OrderItem> event) {
        // TODO Warning:(82, 53) Method invocation 'getItems' may produce 'NullPointerException'
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
        Currency currency = event.getValue();
        if (currency != null) {
            updateValueUSDAndRUB();

            accountService.getDefaultValue(currency)
                    .ifPresent(accountField::setValue);
        }
    }

    private void updateValueUSDAndRUB() {
        valueUSDField.setValue(rateService.convertToUSD(valueField.getValue(), currencyField.getValue(), dateField.getValue()));
        valueRUBField.setValue(rateService.convertToRUB(valueField.getValue(), currencyField.getValue(), dateField.getValue()));
    }

}