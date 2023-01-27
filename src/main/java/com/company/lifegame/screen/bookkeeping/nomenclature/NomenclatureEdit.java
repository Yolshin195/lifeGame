package com.company.lifegame.screen.bookkeeping.nomenclature;

import com.company.lifegame.entity.bookkeeping.Category;
import com.company.lifegame.entity.bookkeeping.Nomenclature;
import com.company.lifegame.entity.bookkeeping.UnitEnum;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@UiController("lg_Nomenclature.edit")
@UiDescriptor("nomenclature-edit.xml")
@EditedEntityContainer("nomenclatureDc")
public class NomenclatureEdit extends StandardEditor<Nomenclature> {
    @Autowired
    private EntityPicker<Category> categoryField;

    @Autowired
    private ComboBox<UnitEnum> unitField;

    @Subscribe("parentField")
    public void onParentFieldValueChange(HasValue.ValueChangeEvent<Nomenclature> event) {
        boolean valid = event.getValue() != null;

        if (valid) {
            Category category = event.getValue().getCategory();
            if (category != null && !Objects.equals(categoryField.getValue(), category)) {
                categoryField.setValue(event.getValue().getCategory());
            }

            UnitEnum unit = event.getValue().getUnit();
            if (unit != null && !Objects.equals(unitField.getValue(), unit)) {
                unitField.setValue(unit);
            }
        }
    }

}