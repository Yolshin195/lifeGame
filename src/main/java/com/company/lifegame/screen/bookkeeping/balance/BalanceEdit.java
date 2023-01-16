package com.company.lifegame.screen.bookkeeping.balance;

import com.company.lifegame.service.DateService;
import io.jmix.ui.component.DateField;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Balance;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("lg_Balance.edit")
@UiDescriptor("balance-edit.xml")
@EditedEntityContainer("balanceDc")
public class BalanceEdit extends StandardEditor<Balance> {
    @Autowired
    private DateService dateService;
    @Autowired
    private DateField<LocalDateTime> dateField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        dateField.setValue(dateService.now());
    }

}