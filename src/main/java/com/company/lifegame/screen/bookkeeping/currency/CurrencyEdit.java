package com.company.lifegame.screen.bookkeeping.currency;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Currency;

@UiController("lg_Currency.edit")
@UiDescriptor("currency-edit.xml")
@EditedEntityContainer("currencyDc")
public class CurrencyEdit extends StandardEditor<Currency> {
}