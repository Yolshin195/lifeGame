package com.company.lifegame.screen.bookkeeping.nomenclature;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Nomenclature;

@UiController("lg_Nomenclature.edit")
@UiDescriptor("nomenclature-edit.xml")
@EditedEntityContainer("nomenclatureDc")
public class NomenclatureEdit extends StandardEditor<Nomenclature> {
}