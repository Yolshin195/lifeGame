package com.company.lifegame.screen.bookkeeping.rate;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Rate;

@UiController("lg_Rate.browse")
@UiDescriptor("rate-browse.xml")
@LookupComponent("ratesTable")
public class RateBrowse extends StandardLookup<Rate> {
}