package com.company.lifegame.screen.bookkeeping.order;

import com.company.lifegame.entity.bookkeeping.*;
import com.company.lifegame.service.DateService;
import com.company.lifegame.service.bookkeeping.AccountService;
import com.company.lifegame.service.bookkeeping.OperationService;
import com.company.lifegame.service.bookkeeping.RateService;
import io.jmix.ui.Notifications;
import io.jmix.ui.RemoveOperation;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private Notifications notifications;

    @Autowired
    private EntityPicker<Category> categoryField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        event.getEntity().setDate(dateService.now());
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        orderDl.load();

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
                            notifications.create()
                                    .withCaption("Ошибка создания операции")
                                    .withDescription("Не обходимо заполнить поля: Счёт, Валюта, и сумаа чека не должна пыть пустой либо равной нулю.")
                                    .withType(Notifications.NotificationType.HUMANIZED)
                                    .show();
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

    @Install(to = "orderItemsTable.create", subject = "afterCommitHandler")
    private void orderItemsTableCreateAfterCommitHandler(OrderItem orderItem) {
        onChangeOrderItems();
    }

    @Install(to = "orderItemsTable.edit", subject = "afterCommitHandler")
    private void orderItemsTableEditAfterCommitHandler(OrderItem orderItem) {
        onChangeOrderItems();
    }

    @Install(to = "orderItemsTable.remove", subject = "afterActionPerformedHandler")
    private void orderItemsTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<OrderItem> afterActionPerformedEvent) {
        onChangeOrderItems();
    }

    public void onChangeOrderItems() {
        boolean valid = orderItemsTable.getItems() != null
                && orderItemsTable.getItems().size() != 0;

        if (valid) {
            updateValueField(orderItemsTable.getItems().getItems());
            updateCategoryField(orderItemsTable.getItems().getItems());
        }
    }

    @Subscribe(id = "orderItemsDc", target = Target.DATA_CONTAINER)
    public void onOrderItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<OrderItem> event) {
        boolean valid = orderItemsTable.getItems() != null
                && orderItemsTable.getItems().size() != 0;

        if (valid) {
            if (currencyField.getValue() != null) {
                updateValueUSDAndRUB();
            }
        }
    }

    private void updateValueField(Stream<OrderItem> orderItemStream) {
        BigDecimal sum = orderItemStream
                .map(OrderItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valueField.setValue(sum);
    }

    private void updateCategoryField(Stream<OrderItem> orderItemStream) {
        Map<Category, Long> categoryGroupsMap = orderItemStream
                .map(OrderItem::getProviderItem)
                .filter(Objects::nonNull)
                .map(ProviderItem::getNomenclature)
                .filter(Objects::nonNull)
                .filter(nomenclature -> nomenclature.getCategory() != null)
                .collect(Collectors.groupingBy(Nomenclature::getCategory, Collectors.counting()));

        if (categoryGroupsMap.size() > 0) {
            Category category = Collections
                    .max(categoryGroupsMap.entrySet(), Comparator.comparingLong(Map.Entry::getValue))
                    .getKey();


            categoryField.setValue(category);
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