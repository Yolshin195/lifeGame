package com.company.lifegame.screen.bookkeeping.rate;

import com.company.lifegame.entity.bookkeeping.Currency;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Rate;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_Rate.edit")
@UiDescriptor("rate-edit.xml")
@EditedEntityContainer("rateDc")
public class RateEdit extends StandardEditor<Rate> {
    @Autowired
    private EntityPicker<Currency> fromField;
    @Autowired
    private EntityPicker<Currency> toField;
    @Autowired
    private TextField<String> codeField;

    @Subscribe("fromField")
    public void onFromFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        setCode();
    }

    @Subscribe("toField")
    public void onToFieldValueChange(HasValue.ValueChangeEvent<Currency> event) {
        setCode();
    }

    private void setCode() {
        if (fromField.getValue() != null && toField.getValue() != null) {
            codeField.setValue(fromField.getValue().getShortName()+toField.getValue().getShortName());
        }
    }
}