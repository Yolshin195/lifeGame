package com.company.lifegame.screen.bookkeeping.provideritem;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.ProviderItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("lg_ProviderItem.browse")
@UiDescriptor("provider-item-browse.xml")
@LookupComponent("providerItemsTable")
public class ProviderItemBrowse extends StandardLookup<ProviderItem> {
    @Autowired
    private CollectionLoader<ProviderItem> providerItemsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        if (event.getOptions() instanceof MapScreenOptions mapScreenOptions) {
            Map<String, Object> objectMap = mapScreenOptions.getParams();

            if (objectMap.containsKey("provider")) {
                providerItemsDl.setParameter("provider", objectMap.get("provider"));
            }
        }
    }

}