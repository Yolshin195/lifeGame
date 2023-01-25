package com.company.lifegame.screen.bookkeeping.operation;

import com.company.lifegame.entity.bookkeeping.Account;
import com.company.lifegame.entity.bookkeeping.Operation;
import com.company.lifegame.entity.bookkeeping.OperationType;
import com.company.lifegame.service.DateService;
import com.company.lifegame.service.bookkeeping.BalanceService;
import io.jmix.core.EntityStates;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private DateField<LocalDateTime> dateField;

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

}