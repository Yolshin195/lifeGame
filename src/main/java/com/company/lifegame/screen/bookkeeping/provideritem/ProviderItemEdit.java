package com.company.lifegame.screen.bookkeeping.provideritem;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.ProviderItem;

@UiController("lg_ProviderItem.edit")
@UiDescriptor("provider-item-edit.xml")
@EditedEntityContainer("providerItemDc")
public class ProviderItemEdit extends StandardEditor<ProviderItem> {
}