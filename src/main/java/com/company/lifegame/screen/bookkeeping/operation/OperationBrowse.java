package com.company.lifegame.screen.bookkeeping.operation;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Operation;

@UiController("lg_Operation.browse")
@UiDescriptor("operation-browse.xml")
@LookupComponent("operationsTable")
public class OperationBrowse extends StandardLookup<Operation> {
}