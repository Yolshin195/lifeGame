package com.company.lifegame.screen.bookkeeping.nomenclature;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Nomenclature;

@UiController("lg_Nomenclature.browse")
@UiDescriptor("nomenclature-browse.xml")
@LookupComponent("nomenclaturesTable")
public class NomenclatureBrowse extends StandardLookup<Nomenclature> {
}