package com.company.lifegame.screen.bookkeeping.operation;

import com.company.lifegame.service.DateService;
import io.jmix.ui.component.DateField;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("lg_Operation.edit")
@UiDescriptor("operation-edit.xml")
@EditedEntityContainer("operationDc")
public class OperationEdit extends StandardEditor<Operation> {

    @Autowired
    private DateService dateService;
    @Autowired
    private DateField<LocalDateTime> dateField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        dateField.setValue(dateService.now());
    }

}