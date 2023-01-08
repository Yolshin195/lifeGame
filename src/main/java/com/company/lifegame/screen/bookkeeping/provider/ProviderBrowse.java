package com.company.lifegame.screen.bookkeeping.provider;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Provider;

@UiController("lg_Provider.browse")
@UiDescriptor("provider-browse.xml")
@LookupComponent("providersTable")
public class ProviderBrowse extends StandardLookup<Provider> {
}