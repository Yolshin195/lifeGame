package com.company.lifegame.screen.logbook;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.Logbook;

@UiController("lg_Logbook.browse")
@UiDescriptor("logbook-browse.xml")
@LookupComponent("table")
public class LogbookBrowse extends MasterDetailScreen<Logbook> {
}