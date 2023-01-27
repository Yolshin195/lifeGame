package com.company.lifegame.screen.bookkeeping.operation;

import com.company.lifegame.entity.bookkeeping.Account;
import com.company.lifegame.entity.bookkeeping.Operation;
import com.company.lifegame.entity.bookkeeping.OperationType;
import com.company.lifegame.service.DateService;
import com.company.lifegame.service.bookkeeping.BalanceService;
import com.company.lifegame.service.bookkeeping.RateService;
import io.jmix.core.EntityStates;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@UiController("lg_Operation.edit")
@UiDescriptor("operation-edit.xml")
@EditedEntityContainer("operationDc")
public class OperationEdit extends StandardEditor<Operation> {

    @Autowired
    private EntityStates entityStates;

    @Autowired
    private DateService dateService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private RateService rateService;

    @Autowired
    private DateField<LocalDateTime> dateField;

    @Autowired
    private ComboBox<OperationType> typeField;

    @Autowired
    private TextField<BigDecimal> valueOneField;

    @Autowired
    private EntityPicker<Account> accountOneField;

    @Autowired
    private TextField<BigDecimal> valueTwoField;

    @Autowired
    private EntityPicker<Account> accountTwoField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        dateField.setValue(dateService.now());
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {

        // Применяем операцию к счетам
        if (entityStates.isNew(getEditedEntity())) {
            balanceService.perform(getEditedEntity());
        }
    }

    @Subscribe("typeField")
    public void onTypeFieldValueChange(HasValue.ValueChangeEvent<OperationType> event) {
        OperationType type = event.getValue();
        if (type == null) {
            setVisibleIncome(true);
            setVisibleExpense(true);
            return;
        }

        switch (type) {
            case INCOME -> {
                setVisibleIncome(true);
                setVisibleExpense(false);
            }
            case EXPENSE -> {
                setVisibleIncome(false);
                setVisibleExpense(true);
            }
            case TRANSFER -> {
                setVisibleIncome(true);
                setVisibleExpense(true);
            }
        }
    }

    void setVisibleIncome(boolean visible) {
        accountTwoField.setVisible(visible);
        valueTwoField.setVisible(visible);
    }

    void setVisibleExpense(boolean visible) {
        accountOneField.setVisible(visible);
        valueOneField.setVisible(visible);
    }

    @Subscribe("valueOneField")
    public void onValueOneFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        boolean valid = event.getValue() != null
                && accountOneField != null
                && accountOneField.getValue() != null
                && accountTwoField != null
                && accountTwoField.getValue() != null;

        if (valid && Objects.equals(typeField.getValue(), OperationType.TRANSFER)) {
            valueTwoField.setValue(rateService.convert(
                    event.getValue(),
                    accountOneField.getValue().getCurrency(),
                    accountTwoField.getValue().getCurrency())
            );
        }
    }

}