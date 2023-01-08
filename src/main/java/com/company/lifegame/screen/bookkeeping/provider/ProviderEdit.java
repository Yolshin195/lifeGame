package com.company.lifegame.screen.bookkeeping.provider;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Provider;

@UiController("lg_Provider.edit")
@UiDescriptor("provider-edit.xml")
@EditedEntityContainer("providerDc")
public class ProviderEdit extends StandardEditor<Provider> {

}