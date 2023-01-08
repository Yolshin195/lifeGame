package com.company.lifegame.screen.bookkeeping.currency;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Currency;

@UiController("lg_Currency.browse")
@UiDescriptor("currency-browse.xml")
@LookupComponent("currenciesTable")
public class CurrencyBrowse extends StandardLookup<Currency> {
}