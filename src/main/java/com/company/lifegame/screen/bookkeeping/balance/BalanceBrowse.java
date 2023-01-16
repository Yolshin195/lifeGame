package com.company.lifegame.screen.bookkeeping.balance;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Balance;

@UiController("lg_Balance.browse")
@UiDescriptor("balance-browse.xml")
@LookupComponent("balancesTable")
public class BalanceBrowse extends StandardLookup<Balance> {
}