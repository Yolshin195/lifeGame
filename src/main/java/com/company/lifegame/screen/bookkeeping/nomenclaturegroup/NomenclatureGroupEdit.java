package com.company.lifegame.screen.bookkeeping.nomenclaturegroup;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.NomenclatureGroup;

@UiController("lg_NomenclatureGroup.edit")
@UiDescriptor("nomenclature-group-edit.xml")
@EditedEntityContainer("nomenclatureGroupDc")
public class NomenclatureGroupEdit extends StandardEditor<NomenclatureGroup> {
}