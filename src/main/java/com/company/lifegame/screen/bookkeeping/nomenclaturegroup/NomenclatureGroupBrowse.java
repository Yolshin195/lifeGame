package com.company.lifegame.screen.bookkeeping.nomenclaturegroup;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.NomenclatureGroup;

@UiController("lg_NomenclatureGroup.browse")
@UiDescriptor("nomenclature-group-browse.xml")
@LookupComponent("nomenclatureGroupsTable")
public class NomenclatureGroupBrowse extends StandardLookup<NomenclatureGroup> {
}